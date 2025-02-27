package com.testingfiles;

import static io.restassured.RestAssured.given;

import java.util.List;

import org.testng.annotations.Test;

import Pojo.GetCoursesDetails;
import Pojo.api;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Postman2 {

	@Test
	private void tc2() {
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
	    .get("/oauthapi/getCourseDetails").then().log().all().extract().response().as(GetCoursesDetails.class);

		
		
		System.out.println("\n");
		System.out.println("2.print expected coursetitle price in API ?");

		String expected = "Rest Assured Automation using Java";

	   List<api> li = as.getCourses().getApi();

		for(
		int i = 0;i<li.size();i++)
		{

			api a = li.get(i);
			
			String actual = a.getCourseTitle();

			if (expected.equalsIgnoreCase(actual)) {
				String price = a.getPrice();
				System.out.println(price);

				break;
			}
		
		}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
