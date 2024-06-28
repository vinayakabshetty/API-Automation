package files;

public class Payload {

	public static String addPlace() {
		String body = "{\r\n" + "    \"location\": {\r\n" + "        \"lat\": -38.383494,\r\n"
				+ "        \"lng\": 33.427362\r\n" + "    },\r\n" + "    \"accuracy\": 50,\r\n"
				+ "    \"name\": \"Vinayaka B Shetty\",\r\n" + "    \"phone_number\": \"(+91) 1234567890\",\r\n"
				+ "    \"address\": \"308, Bhanu elegance apartments, shankarnag road, gottigere, Bengaluru - 560083\",\r\n"
				+ "    \"types\": [\r\n" + "        \"Home\"\r\n" + "    ],\r\n"
				+ "    \"website\": \"http://google.com\",\r\n" + "    \"language\": \"Kannada-IN\"\r\n" + "}";
		return body;
	}

	public static String updatePlaceAddress() {
		String body = "{\r\n" + "    \"place_id\": \"c8fba3916d389e08996e379217d124b7\",\r\n"
				+ "    \"address\": \"112/1 NTB Road Jannapura Bhadravathi, Shivamogga - 577301\",\r\n"
				+ "    \"key\": \"qaclick123\"\r\n" + "}";
		return body;
	}

	public static String mockResponse_ComplexJsonResponse() {
		String response = "{\r\n" + "  \"dashboard\": {\r\n" + "    \"purchaseAmount\": 910,\r\n"
				+ "    \"website\": \"rahulshettyacademy.com\"\r\n" + "  },\r\n" + "  \"courses\": [\r\n" + "    {\r\n"
				+ "      \"title\": \"Selenium Python\",\r\n" + "      \"price\": 50,\r\n" + "      \"copies\": 6\r\n"
				+ "    },\r\n" + "    {\r\n" + "      \"title\": \"Cypress\",\r\n" + "      \"price\": 40,\r\n"
				+ "      \"copies\": 4\r\n" + "    },\r\n" + "    {\r\n" + "      \"title\": \"RPA\",\r\n"
				+ "      \"price\": 45,\r\n" + "      \"copies\": 10\r\n" + "    }\r\n" + "  ]\r\n" + "}";
		return response;
	}

	public static String addBook(String nameOfBook, String isbn, String aisle, String author) {
		String payload = "{\r\n" + "    \"name\": \"" + nameOfBook + "\",\r\n" + "    \"isbn\": \"" + isbn + "\",\r\n"
				+ "    \"aisle\": \"" + aisle + "\",\r\n" + "    \"author\": \"" + author + "\"\r\n" + "}";
		return payload;
	}

	public static String createIssue(String projectKey, String issueType, String issueSummary) {
		String payload = "{\r\n" + "    \"fields\": {\r\n" + "        \"project\": {\r\n" + "            \"key\": \""
				+ projectKey + "\"\r\n" + "        },\r\n"
				+ "        \"summary\": \"Selector dropdown is not workig in landing page\",\r\n"
				+ "        \"issuetype\": {\r\n" + "            \"name\": \"" + issueType + "\"\r\n" + "        }\r\n"
				+ "    }\r\n" + "}";
		return payload;
	}
}
