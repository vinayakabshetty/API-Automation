import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class AddPlaceToMaps {

	public static void main(String[] args) {
		// Given - all input fields // log().all()
		// When - hit the API - method
		// Then - validate response
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(Payload.addPlace()).when().post("/maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract().response()
				.asString();

		JsonPath js = new JsonPath(response);
		String place_Id = js.getString("place_id");
		System.out.println("Place id : " + place_Id);

		// Update the address
		String response2 = given().queryParam("key", "qaclick123").queryParam("place_id", place_Id)
				.header("Content-Type", "application/json")
				.body("{\r\n"
						+ "\"place_id\":\""
						+ place_Id
						+ "\",\r\n"
						+ "\"address\":\"70 winter walk, USA\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n"
						+ "}\r\n"
						+ "").when()
				.put("maps/api/place/update/json").then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js2 = new JsonPath(response2);
		String successMessage = js2.getString("msg");
		Assert.assertTrue(successMessage.equals("Address successfully updated"));
		
		// Get the place address and validate it
		String response3 = 
				given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_Id)
				.when().get("maps/api/place/get/json")
				.then().assertThat().statusCode(200)
				.extract().response().asString();
		
		JsonPath js3 = new JsonPath(response3);
		String updatedPlaceAddress = js3.getString("address");
		Assert.assertTrue(updatedPlaceAddress.equals("70 winter walk, USA"));
	}
}