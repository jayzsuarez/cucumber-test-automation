package response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

import static io.restassured.RestAssured.given;

public class ApiResponse {
    public static Object getResponse(Response response, Object resp) {
        Gson g = new Gson();
        resp = g.fromJson(response.getBody().asString(), resp.getClass());
        return resp;
    }

    public Object getResponseArrayValue(Response response, String responseField) {
        return response.jsonPath().getString(responseField).replaceAll("[\\[ | \\]]", "");
    }

    public static Response getExtractedResponse(String baseUri, String basePath, HttpHeaders header) {
        return given().baseUri(baseUri).basePath(basePath).urlEncodingEnabled(false).headers(header).when().get();
    }

    public static Response getRawResponse(String baseUri, String basePath, HttpHeaders header) {
        return given().urlEncodingEnabled(false).headers(header).when().get(baseUri + basePath);
    }

    public static Response getRawResponse(String baseUri, HttpHeaders header) {
        return given().baseUri(baseUri).headers(header).when().get();
    }

    public static Response getRestAssuredNoAuthGetResponse(String baseUri){
        return given().baseUri(baseUri).when().get();
    }

    public static Response getRestAssuredNoAuthGetFormParam(String baseUri, String query){
        return given().baseUri(baseUri).formParam("query", query).when().get();
    }

    public Response postExtractedResponseAccessToken(String baseUri, HttpHeaders header, String requestUsernameValue, String requestPasswordValue) {
        return given().headers(header).contentType(ContentType.URLENC.withCharset("UTF-8")).formParam("grant_type", "password").formParam("username", requestUsernameValue).formParam("password", requestPasswordValue).post(baseUri);
    }

    public Response postExtractedResponseDisbursementValidate(String baseUri, HttpHeaders header, String fileWithValues, String accessToken) {
        return given().auth().oauth2(accessToken).headers(header).contentType(ContentType.JSON).body(fileWithValues).post(baseUri);
    }

    public static Response getRestAssuredPostResponse(String baseUri, String basePath, HttpHeaders headers, String fileWithValues) {
        return given().baseUri(baseUri).basePath(basePath).headers(headers).body(fileWithValues).when().post();
    }

    public static Response getRestAssuredPutResponse(String baseUri, String basePath, HttpHeaders headers, String fileWithValues) {
        return given().baseUri(baseUri).basePath(basePath).headers(headers).body(fileWithValues).when().put();
    }

    public static Response getRestAssuredPostResponseUri(String baseUri, String basePath, HttpHeaders headers, String fileWithValues) {
        return given().baseUri(baseUri).basePath(basePath).headers(headers).body(fileWithValues).when().post().then().extract().response();
    }

    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    static JsonParser jp = new JsonParser();

    public static String getPrettyPrintedJsonResponse(String responseBody) {

        String jsonResponse = gson.toJson(jp.parse(responseBody));

        if (StringUtils.isEmpty(jsonResponse))
            return "null";
        else {
            return jsonResponse;
        }
    }
}
