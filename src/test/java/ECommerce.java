import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.Ecommerce_Login;

public class ECommerce {
	@Test
	public void login_addProduct_addToCart_deleteProduct() {
		// Login to ecommerce website and get token
		Ecommerce_Login login = new Ecommerce_Login();
		login.setUserEmail("vinayakasjce@gmail.com");
		login.setUserPassword("Enigma@089");

		RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();
		ResponseSpecification responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();

		String response = given().spec(requestSpec).body(login).when().post("/api/ecom/auth/login").then()
				.spec(responseSpec).extract().asString();

		JsonPath js = new JsonPath(response);
		String accessToken = js.getString("token");
		String userId = js.getString("userId");
		System.out.println("accessToken is " + accessToken);
		System.out.println("user id  is " + userId);

		RequestSpecification requestSpec2 = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", accessToken).build();

		ResponseSpecification responseSpec2 = new ResponseSpecBuilder().expectStatusCode(201).build();

		String addProductResponse = given().spec(requestSpec2).param("productName", "Men vests")
				.param("productAddedBy", userId).param("productCategory", "fashion")
				.param("productSubCategory", "Shirts").param("productPrice", "2000")
				.param("productDescription", "Baniyan").param("productFor", "Men")
				.multiPart("productImage", new File("C:\\Users\\lenovo\\Downloads\\Baniyan.jpg")).when()
				.post("/api/ecom/product/add-product").then().spec(responseSpec2).extract().asString();

		JsonPath js2 = new JsonPath(addProductResponse);
		String productId = js2.getString("productId");
		System.out.println("product id is " + productId);

		// Add product to cart
		RequestSpecification requestSpec3 = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", accessToken).build();

		ResponseSpecification responseSpec3 = new ResponseSpecBuilder().expectStatusCode(201).build();

		String addProductToCartResponse = given().spec(requestSpec3).param("_id", productId)
				.param("productName", "Men vests").param("productCategory", "fashion")
				.param("productSubCategory", "Shirts").param("productPrice", "2000")
				.param("productDescription", "Baniyan")
				.multiPart("productImage", new File("C:\\Users\\lenovo\\Downloads\\Baniyan.jpg"))
				.param("productRating", "0").param("productTotalOrders", "0").param("productStatus", true)
				.param("productFor", "Men").param("productAddedBy", "vinayakasjce@gmail.com").param("__v", 0).when()
				.post("/api/ecom/product/add-product").then().spec(responseSpec3).extract().asString();

		JsonPath js3 = new JsonPath(addProductToCartResponse);
		String message = js3.getString("message");
		Assert.assertTrue(message.equalsIgnoreCase("Product Added Successfully"));

		// Delete product
		RequestSpecification requestSpec4 = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).addHeader("Authorization", accessToken).build();

		ResponseSpecification responseSpec4 = new ResponseSpecBuilder().expectStatusCode(200).build();

		String response4 = given().spec(requestSpec4).pathParam("productId", productId).when()
				.delete("/api/ecom/product/delete-product/{productId}").then().spec(responseSpec4).extract().asString();

		JsonPath js4 = new JsonPath(response4);
		String success_message = js4.getString("message");
		Assert.assertTrue(success_message.equals("Product Deleted Successfully"));

	}
}
