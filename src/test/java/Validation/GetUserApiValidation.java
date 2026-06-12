package Validation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunter.utils.CsvFileGeneration;
import com.microsoft.playwright.APIResponse;
import org.testng.Assert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetUserApiValidation {

    public void getUserApiValidation(APIResponse response) throws Exception {
        CsvFileGeneration csvFileGeneration = new CsvFileGeneration();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(response.body());
        Assert.assertNotNull(jsonResponse, "Response should not be null");
        Assert.assertTrue(jsonResponse.isArray(), "Response should be an array");
        Assert.assertEquals(response.status(), 200, "Expected status code 200");
        Assert.assertEquals(response.statusText(), "OK", "Expected response body to be 'OK'");
        System.out.println("Status Code: " + response.status());

        String responseBody = jsonResponse.toPrettyString();
        System.out.println("Response Body: " + responseBody);
        csvFileGeneration.getUserApiResponseCsv(responseBody);

        // Read CSV file
        List<Map<String, String>> csvData = readCsvFile("data/GetUserData.csv");

        // Validate JSON data matches CSV data
        Assert.assertEquals(jsonResponse.size(), csvData.size(), "JSON and CSV row count should match");

        for (int i = 0; i < jsonResponse.size(); i++) {
            JsonNode user = jsonResponse.get(i);
            Map<String, String> csvRow = csvData.get(i);

            Assert.assertEquals(user.get("id").asText(), csvRow.get("id"), "ID should match at row " + i);
            Assert.assertEquals(user.get("name").asText(), csvRow.get("name"), "Name should match at row " + i);
            Assert.assertEquals(user.get("email").asText(), csvRow.get("email"), "Email should match at row " + i);
            Assert.assertEquals(user.get("gender").asText(), csvRow.get("gender"), "Gender should match at row " + i);
            Assert.assertEquals(user.get("status").asText(), csvRow.get("status"), "Status should match at row " + i);

            System.out.println("Validated user at row " + i + ": " + user.get("name").asText());
            System.out.println("Successfully validated the Get User Api response against the CSV data.");
        }
    }

    private List<Map<String, String>> readCsvFile(String filePath) throws Exception {
        List<Map<String, String>> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String headerLine = br.readLine();
            String[] headers = headerLine.split(",");

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Map<String, String> row = new HashMap<>();

                for (int i = 0; i < headers.length; i++) {
                    row.put(headers[i], i < values.length ? values[i] : "");
                }

                data.add(row);
            }
        }

        return data;
    }

    public void getUserApiQueryParameterValidation(APIResponse response, JsonNode jsonResponses) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(response.body());
        Assert.assertNotNull(jsonResponse, "Response should not be null");
        Assert.assertTrue(jsonResponse.isArray(), "Response should be an array");
        Assert.assertEquals(response.status(), 200, "Expected status code 200");
        Assert.assertEquals(response.statusText(), "OK", "Expected response body to be 'OK'");
        System.out.println("Status Code: " + response.status());

        String responseBody = jsonResponse.toPrettyString();
        System.out.println("Response Body: " + responseBody);

        // Validate each user in filtered response exists in full response with matching data
        for (int i = 0; i < jsonResponse.size(); i++) {
            JsonNode filteredUser = jsonResponse.get(i);
            int filteredUserId = filteredUser.get("id").asInt();

            // Find matching user in full response
            JsonNode matchingUser = null;
            for (int j = 0; j < jsonResponses.size(); j++) {
                JsonNode user = jsonResponses.get(j);
                if (user.get("id").asInt() == filteredUserId) {
                    matchingUser = user;
                    break;
                }
            }

            Assert.assertNotNull(matchingUser, "User with ID " + filteredUserId + " should exist in full response");

            // Validate all fields match
            Assert.assertEquals(filteredUser.get("id").asInt(), matchingUser.get("id").asInt(),
                "ID should match for user " + filteredUserId);
            Assert.assertEquals(filteredUser.get("name").asText(), matchingUser.get("name").asText(),
                "Name should match for user " + filteredUserId);
            Assert.assertEquals(filteredUser.get("email").asText(), matchingUser.get("email").asText(),
                "Email should match for user " + filteredUserId);
            Assert.assertEquals(filteredUser.get("gender").asText(), matchingUser.get("gender").asText(),
                "Gender should match for user " + filteredUserId);
            Assert.assertEquals(filteredUser.get("status").asText(), matchingUser.get("status").asText(),
                "Status should match for user " + filteredUserId);

            System.out.println("Successfully validated user ID " + filteredUserId + ": " + filteredUser.get("name").asText());
        }

        System.out.println("All filtered users validated against full response data.");
    }
}
