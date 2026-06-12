package com.hunter.feature;

import Helaper.GetUserApiHelper;
import Validation.GetUserApiValidation;
import com.microsoft.playwright.APIResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UserDataSteps {

    private GetUserApiValidation getUserApiValidation = new GetUserApiValidation();
    private GetUserApiHelper getUserApiHelper = new GetUserApiHelper();
    private APIResponse response;

    @Given("I have the user API endpoint")
    public void i_have_the_user_api_endpoint() {
        System.out.println("User API endpoint is configured");
    }

    @When("I send a GET request to fetch all users")
    public void i_send_a_get_request_to_fetch_all_users() throws Exception {
        response = getUserApiHelper.getUserApiHelper();
        getUserApiValidation.getUserApiValidation(response);
    }

    @Then("I should receive a valid response")
    public void i_should_receive_a_valid_response() throws Exception {
        getUserApiValidation.getUserApiValidation(response);
    }
}
