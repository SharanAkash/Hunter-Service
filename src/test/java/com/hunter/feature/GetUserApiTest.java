package com.hunter.feature;

import Helaper.GetUserApiHelper;
import Validation.GetUserApiValidation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import container.VariableContainer;
import org.testng.annotations.Test;

import static constants.UserConstants.STATUS;

public class GetUserApiTest {

    private GetUserApiValidation getUserApiValidation = new GetUserApiValidation();
    private GetUserApiHelper getUserApiHelper = new GetUserApiHelper();
    private APIResponse response;
    private VariableContainer variableContainer = new VariableContainer();

    @Test(testName = "Get User API Test", description = "Test to validate the Get User API response")
    public void getUserApiTest() throws Exception{
        response = getUserApiHelper.getUserApiHelper();
        getUserApiValidation.getUserApiValidation(response);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(response.body());

        for (int i = 0; i < jsonResponse.size(); i++) {
            JsonNode user = jsonResponse.get(i);
            variableContainer.setUserId(Integer.parseInt(user.get("id").asText()));
            variableContainer.setEmail(user.get("email").asText());
            variableContainer.setName(user.get("name").asText());
            variableContainer.setGender(user.get("gender").asText());
            variableContainer.setStatus(user.get("status").asText());

            System.out.println("Variable Container User ID: " + variableContainer.getUserId());
            System.out.println("Variable Container Name: " + variableContainer.getName());
            System.out.println("Variable Container Email: " + variableContainer.getEmail());
            System.out.println("Variable Container Gender: " + variableContainer.getGender());
            System.out.println("Variable Container Status: " + variableContainer.getStatus());
        }
    }

    @Test
    public void getUserApiQueryParameterTest() throws Exception {

        response = getUserApiHelper.getUserApiHelper();
        getUserApiValidation.getUserApiValidation(response);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(response.body());
        for (int i = 0; i < jsonResponse.size(); i++) {
            JsonNode user = jsonResponse.get(i);
            int userId = Integer.parseInt(user.get("id").asText());
            String status = user.get("status").asText();
            response = getUserApiHelper.getUserApiQueryParameterHelper(userId, status);
            getUserApiValidation.getUserApiQueryParameterValidation(response, jsonResponse);
        }

    }
}
