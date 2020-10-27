package actions;

import io.restassured.response.Response;
import pages.InterzoidPage;
import utils.RestUtils;

import static utils.SeleniumUtils.loadFromPropertiesFile;

public class InterzoidAction {

    private static int statusCode;
    private static String description;

    private final InterzoidPage interzoidPage = new InterzoidPage();


    public void generateRequistion(String city, String state) {
        String url = loadFromPropertiesFile("dell.properties",
                "INTERZOID_PAGE");
        String license = loadFromPropertiesFile("dell.properties",
                "INTERZOID_LICENSE");
        Response response = RestUtils.getApi(url, license, city, state);
        statusCode = response.getStatusCode();
        description = response.getStatusLine();
    }

    public boolean ValidateApiStatus(String expectedOutcome) {
        return interzoidPage.validateResponseStatus(Integer.parseInt(expectedOutcome), statusCode);
    }

    public boolean ValidateApiDescription(String expectedOutcome) {
        return interzoidPage.validateApiResponse(expectedOutcome, description);
    }
}
