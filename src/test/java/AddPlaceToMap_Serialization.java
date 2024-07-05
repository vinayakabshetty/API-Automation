import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import pojo.GoogleMaps_AddPlace;
import pojo.Location;

public class AddPlaceToMap_Serialization {
	@Test
	public void addPlace() {

		RestAssured.baseURI = "https://rahulshettyacademy.com";

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

		String response = given().log().all()
				.queryParam("key", "qaclick123").header("Content-Type", "application/json").body(addPlaceDetails)
				.when().post("/maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract().response()
				.asString();

		JsonPath js = new JsonPath(response);
		String place_Id = js.getString("place_id");
		System.out.println("Place id : " + place_Id);
	}
}
