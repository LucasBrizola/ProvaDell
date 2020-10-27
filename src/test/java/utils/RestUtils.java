package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestUtils {

    public static Response getApi(String url, String license, String city, String state) {
        RestAssured.baseURI = url;
        RequestSpecification request = RestAssured.given()
                .queryParams("license", license,"city", city, "state", state);
        return request.get(url);
    }
}
