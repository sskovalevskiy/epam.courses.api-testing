import beans.YandexGeocodingAnswer;
import core.YandexGeocodingApi;
import io.restassured.RestAssured;
import org.junit.Test;

import static core.Constants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TestYandexGeocodeJSON {

    @Test
    public void geocodeApiCallsWithDifferentMethods() {
        //GET
        RestAssured
                .given()
                .param(PARAM_FORMAT, JSON)
                .param(PARAM_GEOCODE, ADDRESS)
                .log().everything()
                .get(YANDEX_GEOCODING_API_URI)
                .prettyPeek();
    }

    // use base request and response specifications to form request and validate response.
    @Test
    public void useBaseRequestAndResponseSpecifications() {
        RestAssured
                .given(YandexGeocodingApi.baseRequestConfiguration())
                .param(PARAM_FORMAT, JSON)
                .param(PARAM_GEOCODE, ADDRESS)
                .get().prettyPeek()
                .then().specification(YandexGeocodingApi.successResponse());
    }

    @Test
    public void reachBuilderUsage(){
        YandexGeocodingApi.with()
                .format(JSON)
                .address(ADDRESS)
                .callApi()
                .then().specification(YandexGeocodingApi.successResponse());
    }

    //validate an object we've got in API response
    @Test
    public void validateSpellerAnswerAsAnObject() {
        YandexGeocodingAnswer answer =
                YandexGeocodingApi.getYandexGeocodingAnswer(
                        YandexGeocodingApi.with().format(JSON).address(ADDRESS).callApi());

        String coordinates = answer.response.geoObjectCollection.featureMember.get(0).geoObject.point.pos;
        assertThat(coordinates, equalTo(COORDINATES));
    }
}
