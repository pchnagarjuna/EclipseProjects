package com.rest.jira;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

public class CommentsJira {
	
	
	@Test 
	public void logInToJira() {
		
		RestAssured.baseURI="http://localhost:8080";
		SessionFilter sf=new SessionFilter();
		String response=given().log().all().header("Content-Type","application/json").body("{\r\n" + 
				"        \"username\": \"ArnikaArnik\",\r\n" + 
				"        \"password\": \"ArnikaArnik\"\r\n" + 
				"    }").filter(sf)
		.when().post("/rest/auth/1/session")
		
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		//there are two ways doing of using session
		
		/*//1. we can do using below format. we can set the the sessionValue in further API's
		JsonPath js=new JsonPath(response);
		String sessionValue=js.getString("session.value");*/
		
		//2. create reference for Session filter class use that in "given()" method
		
		//update comment
		given().pathParam("key", "10300").log().all().header("Content-Type","application/json").body("{\r\n" + 
				"    \"body\": \"updating comment for my reference 06 30 2020.\",\r\n" + 
				"    \"visibility\": {\r\n" + 
				"        \"type\": \"role\",\r\n" + 
				"        \"value\": \"Administrators\"\r\n" + 
				"    }\r\n" + 
				"}").filter(sf)
		.when().post("/rest/api/2/issue/{key}/comment")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		
		// add attachment
		
		/*curl -D- -u admin:admin -X POST -H "X-Atlassian-Token: no-check" -F "file=@myfile.txt" http://myhost/rest/api/2/issue/TEST-123/attachments

		 -D: Different paramenters 
		 -X: which thhp method
		 -H: header
		 -F: file
		 */		
		given().log().all().header("X-Atlassian-Token","no-check").pathParam("key", "10300").filter(sf)
		.header("Content-Type","multipart/form-data").multiPart("file",new File("C:\\Users\\home\\Downloads\\Projects\\APIDemo\\Files\\jiraUPload.txt"))
		.when().post("/rest/api/2/issue/{key}/attachments")
		.then().log().all().assertThat().statusCode(200);
		
		
		
		
		
		
	}

}
