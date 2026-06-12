package Helaper;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import constants.BaseUrl;
import constants.EndPoint;
import io.qameta.allure.Allure;

import java.util.Map;

public class GetUserApiHelper {

    private final Playwright playwright = Playwright.create();
    private final APIRequest request = playwright.request();
    private final APIRequestContext apiRequestContext = request.newContext();

    public APIResponse getUserApiHelper() throws Exception {
        String url = BaseUrl.BASE_URL + EndPoint.GET_USER;

        // Print URL
        System.out.println("API URL: " + url);

        // Print curl format
        System.out.println("Curl Command: curl -X GET '" + url + "'");

        APIResponse response = apiRequestContext.get(url);
        Map<String, String> headers = response.headers();
        System.out.println("Response Headers: " + headers);

        // Print response details
        System.out.println("Response Status: " + response.status());
        System.out.println("Response Status Text: " + response.statusText());
        System.out.println("Response Body: " + new String(response.body()));

        // Allure reporting
        Allure.step("API Request: GET " + url);
        Allure.addAttachment("Request URL", "text/plain", url);
        Allure.addAttachment("Curl Command", "text/plain", "curl -X GET '" + url + "'");
        Allure.addAttachment("Response Status", "text/plain", String.valueOf(response.status()));
        Allure.addAttachment("Response Status Text", "text/plain", response.statusText());
        Allure.addAttachment("Response Body", "application/json", new String(response.body()));

        return response;
    }

    public APIResponse getUserApiQueryParameterHelper(int id, String status) throws Exception {

       RequestOptions requestOptions = RequestOptions.create()
                .setQueryParam("id", id)
                .setQueryParam("status", status);
        String url = BaseUrl.BASE_URL + EndPoint.GET_USER;

        // Print URL
        System.out.println("API URL with Query Parameter: " + url + "?id=" + id + "&status=" + status);

        // Print curl format
        System.out.println("Curl Command: curl -X GET '" + url + "?id=" + id + "&status=" + status + "'");

        APIResponse response = apiRequestContext.get(url, requestOptions);
        Map<String, String> headers = response.headers();
        System.out.println("Response Headers: " + headers);

        // Print response details
        System.out.println("Response Status: " + response.status());
        System.out.println("Response Status Text: " + response.statusText());
        System.out.println("Response Body: " + new String(response.body()));

        // Allure reporting
        Allure.step("API Request with Query Parameter: GET " + url);
        Allure.addAttachment("Request URL", "text/plain", url);
        Allure.addAttachment("Curl Command", "text/plain", "curl -X GET '" + url + "'");
        Allure.addAttachment("Response Status", "text/plain", String.valueOf(response.status()));
        Allure.addAttachment("Response Status Text", "text/plain", response.statusText());
        Allure.addAttachment("Response Body", "application/json", new String(response.body()));

        return response;
    }

}
