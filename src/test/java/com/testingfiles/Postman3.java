package com.testingfiles;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import Pojo.GetCoursesDetails;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Postman3 {
	
	@Test
	private void tc3() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		String authRes = given().log().all()
		.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParam("grant_type", "client_credentials")
		.formParam("scope", "trust").when().post("/oauthapi/oauth2/resourceOwner/token").then().log().all()
		.assertThat().statusCode(200).extract().response().asString();

		 JsonPath js = new JsonPath(authRes);
		 String token = js.getString("access_token");
		 System.out.println(token);

		 GetCoursesDetails as = given().log().all().queryParam("access_token", token).when()
		.get("/oauthapi/getCourseDetails").then().log().all().extract().response()
		.as(GetCoursesDetails.class);
		 
		 System.out.println("\n");
		 System.out.println("3.Print the webautomation course title and price present in 2nd index position ?");
       
		 System.out.println(as.getCourses().getWebAutomation().get(2).getCourseTitle()+" : "+as.getCourses().getWebAutomation().get(2).getPrice());

		 
	}

}
