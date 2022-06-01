package FiltersJSONSchemaLogToFile;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import static io.restassured.RestAssured.given;

public class LogToFile {
    /***
     * The PrintStream class can be used to write the API logs to an external log file.
     * The PrintStream instance can be passed through the Request and Response logging Filter's constructors along with
     * the LogDetail Enum and boolean value to represent the prettyPrint.
     * If passed true then the output will be pretty formatted.
     * Here in this below example we are writing the logs to the external file "rest-assured.log" file.
     * We can use two separate log file to write the logs of Request and Response separately by defining two instances
     * of PrintStream class, each for Request and Response Logging Filter classes.
     */
    @Test
    public void testLogToFile(){
        try (PrintStream printStream = new PrintStream("rest-assured.log")){
            given().
                    baseUri("https://postman-echo.com").
                    filter(new RequestLoggingFilter(LogDetail.COOKIES, true , printStream)).
                    filter(new ResponseLoggingFilter(LogDetail.STATUS, true , printStream)).
            when().
                    get("/get").
            then().
                    assertThat().
                    statusCode(200);
        } catch (FileNotFoundException e) {
            System.out.println("EXCEPTION: " + e.getMessage() );
        }
    }

    /***
     * We can set global loggers as well by adding filters to the RequestSpecBuilder and ResponseSpecBuilder class.
     * The below example shows the way we can set up the gloabl loggers.
     * We have used @BeforeClass annotation to set up the logging filters
     * to the requestspecbuilder and responsespecbuilder.
     */

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeClass
    public void setUpGlobalLogging(){

        try {
            PrintStream printStream = new PrintStream(new File(("logs/rest-assured-global.log")));
            RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                    .addFilter(new RequestLoggingFilter(printStream))
                    .addFilter(new ResponseLoggingFilter(printStream));

            requestSpecification = requestSpecBuilder.build();
            responseSpecification = new ResponseSpecBuilder().build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void globalLoggingReqRes(){
        given(requestSpecification).
                baseUri("https://postman-echo.com").
        when().
                get("/get").
        then().spec(responseSpecification).
                assertThat().
                statusCode(200);
    }
}
