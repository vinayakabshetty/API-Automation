import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class JiraAPI {

	@Test
	public void createIssue() {

		RestAssured.baseURI = "https://vinayakasjce.atlassian.net";
		String accessTokenForJiraUser = "dmluYXlha2FzamNlQGdtYWlsLmNvbTpBVEFUVDN4RmZHRjA3T012VnB2Q0V3LW1TRGVTcWNuXy1qR0x3R2ZjQUxQSXZjekt3UFZGQllkbk5HYjBCemY3QjVyTWhnQ2VhYzBTRVZ3VXVqWDFUdkpXQ1BJc1ctT2Vqb2VCMTMwUkNHVUxMTnF5MjRkOGwxeEN0N1EtQWVtVHpnR21DZFZOLTZiRENONmhoMFYxeGRBQ00tTDJ0c19QSkFVeFRfMmVPejFjNVRrQlRKbGx0WnM9RjQ4RDBENjc=";
		String projectKey = "VIN";
		String issueType = "Bug";
		String issueSummary = "Automate API Testing 1";

		String response = given().log().all().header("Accept", "application/json")
				.header("Authorization", "Basic " + accessTokenForJiraUser).header("Content-Type", "application/json")
				.body(Payload.createIssue(projectKey, issueType, issueSummary)).when().log().all()
				.post("rest/api/3/issue").then().assertThat().statusCode(201).extract().asString();

		JsonPath js = new JsonPath(response);
		String bugId = js.get("id");
		System.out.println("Bug id created : " + bugId);

		// Attach bug evidence to jira bug id
		String response2 = given().log().all().header("Authorization", "Basic " + accessTokenForJiraUser)
				.header("X-Atlassian-Token", "no-check").pathParam("Bug id", bugId)
				.multiPart(new File("C:\\Users\\lenovo\\Downloads\\Library+API.docx")).log().all()
				.post("https://vinayakasjce.atlassian.net/rest/api/3/issue/{Bug id}/attachments").then().log().all()
				.assertThat().statusCode(200).extract().asString();

		js = new JsonPath(response2);
		String attachedFileName = js.getString("filename");
		System.out.println("Attached file name is : " + attachedFileName);

		// Get issue details
		given().log().all().header("Authorization", "Basic " + accessTokenForJiraUser)
				.header("Accept", "application/json").pathParam("Bug id", bugId).log().all()
				.get("https://vinayakasjce.atlassian.net/rest/api/3/issue/{Bug id}").then().log().all().assertThat()
				.statusCode(200);
		
		System.out.println("Simply for git");
	}

}
