package com.testingfiles;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.testng.annotations.Test;
import Pojo.GetCoursesDetails;
import Pojo.webAutomation;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Postman8 {
	
	@Test
	private void tc8() {
		
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
		System.out.println("8.Client has given expected list of courses, you should match with actual list of courses under webautomation ?");
		
		
		
		String [] l = {"Selenium Webdriver Java","Cypress","Protractor"};
		
		List<webAutomation> li = as.getCourses().getWebAutomation();
		
		List<String> actual = new ArrayList<String>();
		
		for(int i=0;i<li.size();i++) {
			webAutomation w = li.get(i);
			
			String courseTitle = w.getCourseTitle();
			actual.add(courseTitle);
		}
			
		
	List<String> expected = Arrays.asList(l);
		
		boolean x = expected.equals(actual);
		assertTrue(x ,"Check the List");
		
		System.out.println("PASSED");
		
		
		
		
	}
	
	
	

}
