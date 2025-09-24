package examples;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Chapter4Test {

    private static RequestSpecification requestSpec;

    @BeforeAll
    public static void createRequestSpecification(){

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://api.zippopotam.us").
                build();
    }

    @Test
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills_withRequestSpec(){

        given().
                spec(requestSpec).
        when().
                get("us/90210").
        then().
                assertThat().
                statusCode(200);
    }

    private static ResponseSpecification responseSepec;

    @BeforeAll
    public static void createResponseSpecification(){

        responseSepec = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                build();
    }


    @Test
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills_withResponseSpec(){

        given().
                spec(requestSpec).
        when().
                get("https://zippopotam.us/us/90210").
        then().
                spec(responseSepec).
        and().
                assertThat().
                body("places[0].'place name'", equalTo("Beverly Hills"));
    }

    @Test
    public void requestUsZipCode90210_extractPlaceNameFromResponseBody_assertEqualToBeverlyHills(){

        String placeName =
                given().
                        spec(requestSpec).
                when().
                        get("http://zippopotam.us/us/90210").
                then().
                        extract().
                        path("places[0].'place name'");

        Assertions.assertEquals("Beverly Hills", placeName);
    }
}
