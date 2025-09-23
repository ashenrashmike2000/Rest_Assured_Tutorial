package examples;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Chapter2Test {

    private final String url = "http://zippopotam.us/us/90210";

    @Test
    public void requestUsZipCode90210_checkStatusCode_expectHttp200() {

        given().
                when().
                get(url).
                then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void requestUsZipCode90210_checkContentType_expectApplicationJson(){
        given().
                when().
                get(url).
                then().
                assertThat().
                contentType(ContentType.JSON);//ContentType.JSON = "application/json"
    }

    @Test
    public void requestUsZipCode90210_logRequestAndResponseDetails(){
        given().
                log().all().
        when().
                get(url).
        then().
                log().body();
    }

    @Test
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills(){
        given().
        when().
                get(url).
        then().
                assertThat().
                body("places[0].'place name'",equalTo("Beverly Hills"));
    }

    @Test
    public void requestUsZipCode90210_checkStateNameInResponseBody_expectCalifornia(){
        given().
                when().
                get(url).
                then().
                assertThat().
                body("places[0].state",equalTo("California"));
    }

    @Test
    public void requestUsZipCode90210_checkListOfPlaceNamesInResponseBody_expectContainsBeverlyHills(){
        given().
                when().
                get(url).
                then().
                assertThat().
                body("places.'place name'",hasItem("Beverly Hills"));
    }

    @Test
    public void requestUsZipCode90210_checkNumberOfPlaceNamesInResponseBody_expectOne(){
        given().
                when().
                get(url).
                then().
                assertThat().
                body("places.'place name'",hasSize(1));
    }


}
