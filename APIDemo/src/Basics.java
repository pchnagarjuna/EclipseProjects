import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*; 
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.PayLoad;
import files.ReUsableMethods;

public class Basics {
	
public static void main(String[] args) {
	
	//Validate if Add place API is working as expected
	
	//given-all inputs are given- Query Paramas, Http header, Body	
	//when-submit the API-resources and http method	
	//Then-validate the response
	RestAssured.baseURI="https://rahulshettyacademy.com";
	String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
	.body(PayLoad.addPlace()).when().post("maps/api/place/add/json")
	.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
	.header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
	//Add place->update place with new address-> get place to validate if new address present in response
	System.out.println(response);
	JsonPath js=new JsonPath(response);
	String place_id=js.getString("place_id");
	System.out.println(place_id);
	String newAddress="70 winter walk, USA";
	given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
	.body("{\r\n" + 
			"\"place_id\":\""+place_id+"\",\r\n" + 
			"\"address\":\""+newAddress+"\",\r\n" + 
			"\"key\":\"qaclick123\"\r\n" + 
			"}\r\n" + 
			"")
	.when().put("maps/api/place/update/json")
	.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
	
	//get place 
	given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_id)
	.when().get("maps/api/place/get/json")
	.then().log().all().assertThat().statusCode(200).body("address", equalTo(newAddress));
	String response1=given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_id)
			.when().get("maps/api/place/get/json")
			.then().log().all().assertThat().statusCode(200).body("address", equalTo(newAddress)).extract().response().asString();
			//Add place->update place with new address-> get place to validate if new address present in response
			System.out.println(response1);
			js=ReUsableMethods.rawToJson(response1);	
			String address=js.getString("address");			
	Assert.assertEquals(address, newAddress);
	
}


}
