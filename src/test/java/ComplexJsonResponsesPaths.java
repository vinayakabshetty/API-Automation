import org.testng.Assert;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonResponsesPaths {

	public static void main(String[] args) {
		JsonPath js = new JsonPath(Payload.mockResponse_ComplexJsonResponse());

		// Print No of courses returned by API
		int numberOfCourses = js.getInt("courses.size()");
		System.out.println("Total number of courses " + numberOfCourses);

		// Print Purchase Amount
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase amount " + purchaseAmount);

		// Print Title of the first course
		String titleOfFirstCourse = js.getString("courses[0].title");
		System.out.println("Title of first course " + titleOfFirstCourse);

		// Print All course titles and their respective Prices
		int totalCourses = js.getInt("courses.size()");
		System.out.println("\nAll the courses");
		for (int i = 0; i < totalCourses; i++) {
			String course = js.getString("courses[" + i + "].title");
			String price = js.getString("courses[" + i + "].price");
			System.out.println("Course " + (i + 1) + " title is " + course);
			System.out.println("Course " + (i + 1) + " price is " + price);
		}

		// Print no of copies sold by RPA Course
		for (int i = 0; i < totalCourses; i++) {
			String course = js.getString("courses[" + i + "].title");
			if (course.equals("RPA")) {
				String copies = js.getString("courses[" + i + "].copies");
				System.out.println("Number of sold copies of RPA Course : " + copies);
				break;
			}
		}

		// Verify if Sum of all Course prices matches with Purchase Amount
		int actual_totalPurchaseAmount = 0;
		for (int i = 0; i < totalCourses; i++) {
			String copies = js.getString("courses[" + i + "].copies");
			int copies_int = Integer.parseInt(copies);
			String price = js.getString("courses[" + i + "].price");
			int price_int = Integer.parseInt(price);
			actual_totalPurchaseAmount = actual_totalPurchaseAmount + (copies_int * price_int);
		}
		System.out.println("Actual computed Total purchase amount is " + actual_totalPurchaseAmount);

		String actual_totalPurchaseAmount_str = String.valueOf(actual_totalPurchaseAmount);
		String expected_totalPurchaseAmount_str = String.valueOf(js.getInt("dashboard.purchaseAmount"));
		Assert.assertTrue(actual_totalPurchaseAmount_str.equals(expected_totalPurchaseAmount_str));
	}
}