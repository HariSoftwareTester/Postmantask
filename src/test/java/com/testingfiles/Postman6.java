package com.testingfiles;

import static io.restassured.RestAssured.given;

import java.util.List;

import org.testng.annotations.Test;

import Pojo.GetCoursesDetails;
import Pojo.mobile;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Postman6 {
	
	@Test
	private void tc6() {
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
		System.out.println("4.Print expected coursetitle price in mobile ?");

		String expected = "Appium-Mobile Automation using Java";

		 List<mobile> li = as.getCourses().getMobile();

		for(int i = 0;i<li.size();i++){

			 mobile a = li.get(i);

			String actual = a.getCourseTitle();

			if (expected.equalsIgnoreCase(actual)) {
				String price = a.getPrice();
				System.out.println(actual+" : "+price);

				break;
			}
		
		}

	}

}
