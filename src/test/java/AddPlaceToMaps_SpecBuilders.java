import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.GoogleMaps_AddPlace;
import pojo.Location;

public class AddPlaceToMaps_SpecBuilders {

	@Test
	public void addPlaceToMap() {

		// Body - Serialization concept
		GoogleMaps_AddPlace addPlaceDetails = new GoogleMaps_AddPlace();
		Location location = new Location();
		location.setLat(-38.383494);
		location.setLng(33.427362);
		addPlaceDetails.setLocation(location);
		addPlaceDetails.setAccuracy(50);
		addPlaceDetails.setName("Frontline house");
		addPlaceDetails.setPhone_number("(+91) 983 893 3937");
		addPlaceDetails.setAddress("29, side layout, cohen 09");

		List<String> types = new ArrayList<>();
		types.add("shoe park");
		types.add("shop");
		addPlaceDetails.setTypes(null);

		addPlaceDetails.setWebsite("http://google.com");
		addPlaceDetails.setLanguage("French-IN");

		// Add, Get, Delete place - Request spec builders
		RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").setContentType("application/json").build();

		// Response - Response spec builder
		ResponseSpecification responseSpec = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();

		String res = given().log().all().spec(requestSpec).body(addPlaceDetails).when().log().all()
				.post("/maps/api/place/add/json").then().log().all().spec(responseSpec).extract().asString();

		JsonPath js = new JsonPath(res);
		String place_Id = js.getString("place_id");
		System.out.println("Place id : " + place_Id);
	}
}
