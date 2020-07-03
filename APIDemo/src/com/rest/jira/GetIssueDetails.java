package com.rest.jira;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class GetIssueDetails {
	
	@Test
	public void issueDetails() {
		
		RestAssured.baseURI="http://localhost:8080";
		SessionFilter sf=new SessionFilter();
		//to handle https websites we need to give relaxHttpsvalidation() method after given method.
		given().relaxedHTTPSValidation().log().all().header("Content-Type","application/json").body("{\r\n" + 
				"        \"username\": \"ArnikaArnik\",\r\n" + 
				"        \"password\": \"ArnikaArnik\"\r\n" + 
				"    }").filter(sf)
		.when().post("/rest/auth/1/session")
		.then().log().all().assertThat().statusCode(200);
		
		
		String issueDetails= given().log().all().filter(sf).pathParam("key", "10300").queryParam("fields", "comment")
		.when().get("/rest/api/2/issue/{key}")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(issueDetails);
		
		JsonPath js=new JsonPath(issueDetails);
		
	}

}
