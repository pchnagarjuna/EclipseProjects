package soap.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;

public class CurrencyConverter {
	
	@Test
	public void postMethod() throws IOException {
		
		FileInputStream fileInputStream = new FileInputStream(new File("C:\\Users\\home\\eclipse-workspace\\APIDemo\\Files\\request.xml"));
        RestAssured.baseURI="http://currencyconverter.kowabunga.net";

        Response response=given()
               .header("Content-Type", "text/xml")
               .and()
               .body(IOUtils.toString(fileInputStream,"UTF-8"))
        .when()
           .post("/converter.asmx")
        .then()
               .statusCode(200)
               .and()
               .log().all()
               .extract().response();

       XmlPath jsXpath= new XmlPath(response.asString());//Converting string into xml path to assert
       String rate=jsXpath.getString("GetConversionRateResult");
       System.out.println("rate returned is: " +  rate);

	}

}
