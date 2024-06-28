import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class BooksAPI {

	@Test(dataProvider = "book details")
	public void AddBook(String nameOfBook, String isbn, String aisle, String author) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		given().headers("Content-Type", "application/json").body(Payload.addBook(nameOfBook, isbn, aisle, author)).when()
				.post("Library/Addbook.php").then().log().all().statusCode(200)
				.body("Msg", equalTo("successfully added"));
	}

	@DataProvider(name = "book details", parallel=true)
	public Object[][] book_details() {
		return new Object[][] { { "Z", "testZ", "5", "Vin" }, { "Z", "testZ", "6", "Vin" },
				{ "Z", "testZ", "7", "Vin" }, { "Z", "testZ", "8", "Vin" }, };
	}
}
