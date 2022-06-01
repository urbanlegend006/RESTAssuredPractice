package FiltersJSONSchemaLogToFile;

import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.RestAssured.given;

public class JSONSchema {
    /***
    JSON schema:
        1. Describes the existing data format for the JSON object
        2. It provides the human and machine readable format of the data
        3. It helps in test automation
     */

    @Test
    public void testJsonSchema(){
        given().
                baseUri("https://postman-echo.com").
                log().all().
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200).
                body(matchesJsonSchemaInClasspath("echo-get.json"));
    }

    @Test
    public void testJsonSchemaWithResourcesFromTestPath(){
        /***
         * The JSON schema file should be located at class path.
         * The class path could be the resources folder either from the src/main/resources or from src/test/resources.
         * Here we have added the JSON schema file to the resources folder under the src/test/resources folder which is
         * also the class-path.
         */
        given().
                baseUri("https://postman-echo.com").
                log().all().
                when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200).
                body(matchesJsonSchemaInClasspath("test-resource-echo-get.json"));
    }
}
