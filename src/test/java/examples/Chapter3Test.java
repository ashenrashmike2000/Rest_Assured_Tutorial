package examples;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Chapter3Test {

    public static Stream<Arguments> zipCodesAndPlaces(){
        return Stream.of(
                Arguments.of("us", "90210", "Beverly Hills"),
                Arguments.of("us", "12345", "Schenectady"),
                Arguments.of("ca", "B2R", "Waverley")
        );
    }

    @ParameterizedTest
    @MethodSource("zipCodesAndPlaces")
    public void requestZipCodesFromCollection_checkPlaceNameInResponseBody_expectSpecifiedPlaceName(String countryCode, String zipCode, String expectedPlaceName) {

        given().
                pathParams("countryCode",countryCode).
                pathParams("zipCode",zipCode).
        when().
                get("http://zippopotam.us/{countryCode}/{zipCode}").
        then().
                assertThat().
                body("places[0].'place name'", equalTo(expectedPlaceName));
    }




}
