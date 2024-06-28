import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class BooksAPI_CallFromExternalJSONFile {

	@Test
	public void addBookFromDetailsMentionedInJson() throws IOException {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		given().headers("Content-Type", "application/json")
				.body(new String(Files.readAllBytes(Path.of(
						"C:\\Users\\lenovo\\eclipse-workspace\\API_Automation\\src\\test\\java\\files\\AddBook.json"))))
				.when().post("Library/Addbook.php").then().log().all().statusCode(200)
				.body("Msg", equalTo("successfully added"));
	}
}
