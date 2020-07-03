package com.rest.jira;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

public class OAuthTest {

	public static void main(String[] args) {
		
		RestAssured.baseURI="";
		
		String response=given().queryParam("access-token", "")
		.when()
		.get("https://rahulshettyacademy.com/getCourse.php").asString();
		
		System.out.println(response);
	}
}
