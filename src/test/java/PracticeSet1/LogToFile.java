package PracticeSet1;

import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.Test;

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
}
