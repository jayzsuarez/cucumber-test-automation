package steps.profile_management;

import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.Assert;
import response.ApiResponse;
import response.ProfileEntity;
import utils.FileUtil;
import utils.LogUtil;
import utils.RequestHttpUtils;

import java.util.Locale;

public class ProfileManagementSteps {
    String baseUri = "";
    String basePath = "";
    int statusCode;
    Response response;
    String gajiGesaToken = FileUtil.getSetting("gaji.gesa.profile.token");
    String name;


    public void createAUser() {
        baseUri = FileUtil.getSetting("gaji.gesa.url");
        basePath = FileUtil.getSetting("gaji.gesa.user");

    }

    public void submitProfileCreate() {
        Faker faker = new Faker();
        String fName = faker.name().firstName();
        String lName = faker.name().lastName();

        name =  fName + " " + lName;
        System.out.println("hoho " + name);
        try {
            String myRequest = "{\n" +
                    "    \"name\": \"" + name + "\",\n" +
                    "    \"email\": \"" + lName.toLowerCase(Locale.ROOT) + "_" + fName.toLowerCase(Locale.ROOT) + "@jayzsuarez.info\",\n" +
                    "    \"gender\": \"male\",\n" +
                    "    \"status\": \"inactive\"\n" +
                    "}";
            System.out.println(myRequest);
            response = ApiResponse.getRestAssuredPostResponse(baseUri, basePath, RequestHttpUtils.getBearerTokenHeaders(gajiGesaToken), myRequest);
            //responseBody = response.getBody().asString();
            statusCode = response.getStatusCode();

        } catch (Exception e) {
            LogUtil.error(this, "Response error message: " + e);
        }
    }

    public void checkProfileCreated() {
        try {
            ProfileEntity responseEntity = (ProfileEntity) ApiResponse.getResponse(response, new ProfileEntity());
            if (statusCode == 201) {

                /**
                 * {
                 *     "id": 4746,
                 *     "name": "Jaycee",
                 *     "email": "jaycee@huel.info",
                 *     "gender": "female",
                 *     "status": "inactive"
                 * }
                 */
                LogUtil.info(this, "Profile Id: " + responseEntity.getId());
                LogUtil.info(this, "name: " + responseEntity.getName());
                LogUtil.info(this, "email: " + responseEntity.getEmail());
                LogUtil.info(this, "gender: " + responseEntity.getGender());
                LogUtil.info(this, "status: " + responseEntity.getStatus());

                Assert.assertNotEquals(responseEntity.getId(), new Integer(0));
                Assert.assertEquals(responseEntity.getName(), name);
                Assert.assertNotEquals(responseEntity.getEmail(), "");
                Assert.assertNotEquals(responseEntity.getGender(), "");
                Assert.assertEquals(responseEntity.getStatus(), "inactive");

            }
        }catch (Exception e) {
            LogUtil.error(this, "Error checking person response: " + e);
        }
    }
}
