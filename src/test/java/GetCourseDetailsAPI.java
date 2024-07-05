import static io.restassured.RestAssured.given;

import java.util.List;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import pojo.GetCourseDetails;
import pojo.Mobile;
import pojo.WebAutomation;

public class GetCourseDetailsAPI {

	@Test
	public void getCourseDetails() {
		String response1 = given()
				.formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParams("grant_type", "client_credentials")
				.formParams("scope", "trust").when()
				.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").then().log().all()
				.assertThat().statusCode(200).extract().asString();

		JsonPath js = new JsonPath(response1);
		String accessToken = js.getString("access_token");

		// Get course details
		GetCourseDetails getCourseDetails = given().log().all().queryParam("access_token", accessToken)
				.when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
				.then().log().all().extract().as(GetCourseDetails.class);

		System.out.println("Instructor details is " + getCourseDetails.getInstructor());
		System.out.println("URL is " + getCourseDetails.getUrl());
		System.out.println("Services is " + getCourseDetails.getServices());
		System.out.println("Expertise is " + getCourseDetails.getExpertise());
		System.out.println("Linked In is " + getCourseDetails.getLinkedIn());

		List<WebAutomation> webAutomationDetails = getCourseDetails.getCourses().getWebAutomation();
		for (int i = 0; i < webAutomationDetails.size(); i++) {
			if (webAutomationDetails.get(i).getCourseTitle().equals("Cypress")) {
				System.out.println("Price of course with title Cypress is : "+webAutomationDetails.get(i).getPrice());
				break;
			}
		}

		List<Mobile> mobileDetails = getCourseDetails.getCourses().getMobile();
		for (int i = 0; i < mobileDetails.size(); i++) {
			if (mobileDetails.get(i).getCourseTitle().equals("Appium-Mobile Automation using Java")) {
				System.out.println("Price of course with title \"Appium-Mobile Automation using Java\" is : "+mobileDetails.get(i).getPrice());
				break;
			}
		}
	}
}
