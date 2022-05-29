package PracticeSet1;

import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class FIlters {

    @Test
    public void TestFilters(){
        /***
         * The RequestLoggingFilter and ResponseLoggingFiler class can help us to filter the log details.
         * Both the constructors receives the LogDetail Enum. Some of the important values of the Enum is -
         * LogDetail.ALL
         * LogDetail.BODY
         * LogDetail.METHOD
         * LogDetail.URI
         * LogDetail.STATUS
         */
        given().
                baseUri("https://postman-echo.com").
                filter(new RequestLoggingFilter(LogDetail.URI)).
                filter(new ResponseLoggingFilter(LogDetail.STATUS)).
        when().
                get("/get").
        then().
                assertThat().
                statusCode(200);
    }
}
