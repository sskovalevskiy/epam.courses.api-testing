package core;

import beans.YandexGeocodingAnswer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static core.Constants.*;
import static org.hamcrest.Matchers.lessThan;

public class YandexGeocodingApi {

    private YandexGeocodingApi() {
    }

    private HashMap<String, String> params = new HashMap<String, String>();

    public static class ApiBuilder {
        YandexGeocodingApi spellerApi;

        private ApiBuilder(YandexGeocodingApi gcApi) {
            spellerApi = gcApi;
        }

        public ApiBuilder format(String format) {
            spellerApi.params.put(PARAM_FORMAT, format);
            return this;
        }

        public ApiBuilder address(String address) {
            spellerApi.params.put(PARAM_GEOCODE, address.replaceAll(" ","+"));
            return this;
        }

        public Response callApi() {
            return RestAssured.with()
                    .queryParams(spellerApi.params)
                    .log().all()
                    .get(YANDEX_GEOCODING_API_URI).prettyPeek();
        }
    }


    public static ApiBuilder with() {
        YandexGeocodingApi api = new YandexGeocodingApi();
        return new ApiBuilder(api);
    }


    //get ready Speller answers list form api response
//    public static List<YandexGeocodingAnswer> getYandexGeocodingAnswer(Response response) {
//        return new Gson().fromJson(response.asString().trim(), new TypeToken<List<YandexGeocodingAnswer>>() {
//        }.getType());
//    }

    public static YandexGeocodingAnswer getYandexGeocodingAnswer(Response response) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<YandexGeocodingAnswer>() {
        }.getType());
    }

    //set base request and response specifications tu use in tests
    public static ResponseSpecification successResponse() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectHeader("Connection", "keep-alive")
                .expectResponseTime(lessThan(20000L))
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static RequestSpecification baseRequestConfiguration() {
        return new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .addHeader("custom header2", "header2.value")
                .addQueryParam("requestID", new Random().nextLong())
                .setBaseUri(YANDEX_GEOCODING_API_URI)
                .build();
    }
}
