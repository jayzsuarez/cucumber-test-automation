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
    String baseUri = FileUtil.getSetting("gaji.gesa.url");
    String basePath = "";
    int statusCode;
    Response response;
    String gajiGesaToken = FileUtil.getSetting("gaji.gesa.profile.token");

    Integer id;
    Faker faker = new Faker();
    String fName;
    String lName;

    String fullName;
    String email;
    String gender;
    String status;

    String updatedFName;
    String updatedLName;
    String updatedFullName;

    String updatedGender;
    String updatedStatus;
    String updatedEmail;

    public void prepareProfileRequest() {
        basePath = FileUtil.getSetting("gaji.gesa.user");
    }

    public void submitProfileCreate() {
        fName = faker.name().firstName();
        lName = faker.name().lastName();

        fullName = fName + " " + lName;

        try {
            String myRequest = "{\n" +
                    "    \"name\": \"" + fullName + "\",\n" +
                    "    \"email\": \"" + lName.toLowerCase(Locale.ROOT) + "_" + fName.toLowerCase(Locale.ROOT) + "@jayzsuarez.info\",\n" +
                    "    \"gender\": \"male\",\n" +
                    "    \"status\": \"inactive\"\n" +
                    "}";

            response = ApiResponse.getRestAssuredPostResponse(baseUri, basePath, RequestHttpUtils.getBearerTokenHeaders(gajiGesaToken), myRequest);
            statusCode = response.getStatusCode();
            LogUtil.info(this, "Status code: " + statusCode);
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
                id = responseEntity.getId();
                Assert.assertNotEquals(responseEntity.getId(), new Integer(0));
                Assert.assertEquals(responseEntity.getName(), fullName);
                Assert.assertNotEquals(responseEntity.getEmail(), "");
                Assert.assertNotEquals(responseEntity.getGender(), "");
                Assert.assertEquals(responseEntity.getStatus(), "inactive");

            }
        } catch (Exception e) {
            LogUtil.error(this, "Error checking person response: " + e);
        }
    }

    public void submitUpdateProfile() {
        updatedFName = faker.name().firstName();
        updatedLName = faker.name().lastName();
        String userId = String.valueOf(id);


        updatedFullName = updatedFName + " " + updatedLName;
        basePath = FileUtil.getSetting("gaji.gesa.specific.user", userId);

        try {
            String updateRequest = "{\n" +
                    "    \"name\": \"" + updatedFullName + "\",\n" +
                    "    \"gender\": \"male\",\n" +
                    "    \"status\": \"active\"\n" +
                    "}";

            LogUtil.info(this, "New request: " + updateRequest);
            response = ApiResponse.getRestAssuredPatchResponse(baseUri, basePath, RequestHttpUtils.getBearerTokenHeaders(gajiGesaToken), updateRequest);
            statusCode = response.getStatusCode();
            LogUtil.info(this, "Status code: " + statusCode);

        } catch (Exception e) {
            LogUtil.error(this, "Response error message: " + e);
        }
    }

    public void updatedProfileReturned() {
        try {
            ProfileEntity responseEntity = (ProfileEntity) ApiResponse.getResponse(response, new ProfileEntity());

            if (statusCode == 201) {
                LogUtil.info(this, "Profile Id: " + responseEntity.getId());
                LogUtil.info(this, "New name: " + responseEntity.getName());
                LogUtil.info(this, "New email: " + responseEntity.getEmail());
                LogUtil.info(this, "New gender: " + responseEntity.getGender());
                LogUtil.info(this, "New status: " + responseEntity.getStatus());


                Assert.assertEquals(responseEntity.getId(), id);
                Assert.assertNotEquals(responseEntity.getName(), updatedFullName);
                Assert.assertNotEquals(responseEntity.getEmail(), "");
                Assert.assertNotEquals(responseEntity.getGender(), "male");
                Assert.assertNotEquals(responseEntity.getStatus(), "active");

            }
        } catch (Exception e) {
            LogUtil.error(this, "Error updating person: " + e);
        }
    }

    public void retrieveSpecificUser(String id) {
        basePath = FileUtil.getSetting("gaji.gesa.specific.user", id);

        try {
            LogUtil.info(this, "Uri and path: " + baseUri + basePath);
            response = ApiResponse.getExtractedResponse(baseUri, basePath, RequestHttpUtils.getBearerTokenHeaders(gajiGesaToken));
            ProfileEntity responseEntity = (ProfileEntity) ApiResponse.getResponse(response, new ProfileEntity());

            statusCode = response.getStatusCode();

            LogUtil.info(this, "Status code: " + statusCode);
            if (statusCode == 200) {
                LogUtil.info(this, "Retrieved  Id: " + responseEntity.getId());
                LogUtil.info(this, "Retrieved name: " + responseEntity.getName());
                LogUtil.info(this, "Retrieved email: " + responseEntity.getEmail());
                LogUtil.info(this, "Retrieved gender: " + responseEntity.getGender());
                LogUtil.info(this, "Retrieved status: " + responseEntity.getStatus());

                fullName = responseEntity.getName();
                email = responseEntity.getEmail();
                gender = responseEntity.getGender();
                status = responseEntity.getStatus();
            }

        } catch (Exception e) {
            LogUtil.error(this, "Response error message: " + e);
        }
    }

    public void updateAllUserDetails(String id) {
        updatedFName = faker.name().firstName();
        updatedLName = faker.name().lastName();

        updatedFullName = updatedFName + " " + updatedLName;
        basePath = FileUtil.getSetting("gaji.gesa.specific.user", id);

        if (gender.equalsIgnoreCase("male")) {
            updatedGender = "female";
        } else {
            updatedGender = "male";
        }
        if (status.equalsIgnoreCase("active")) {
            updatedStatus = "inactive";
        } else {
            updatedStatus = "active";
        }
        updatedEmail = updatedFName.toLowerCase(Locale.ROOT) + "_" + updatedLName.toLowerCase(Locale.ROOT) + "@jayzsuarez.info";

        String updateRequest = "{\n" +
                "    \"name\": \"" + updatedFullName + "\",\n" +
                "    \"email\": \"" + updatedEmail + "\",\n" +
                "    \"gender\": \"" + updatedGender + "\",\n" +
                "    \"status\": \"" + updatedStatus + "\"\n" +
                "}";

        LogUtil.info(this, "New update all request: " + updateRequest);

        try {
            response = ApiResponse.getRestAssuredPatchResponse(baseUri, basePath, RequestHttpUtils.getBearerTokenHeaders(gajiGesaToken), updateRequest);
            statusCode = response.getStatusCode();
            LogUtil.info(this, "Status code: " + statusCode);

        } catch (Exception e) {
            LogUtil.error(this, "Response error message: " + e);
        }
    }


    public void verifyAllNewProfileUpdate() {
        try {
            ProfileEntity responseEntity = (ProfileEntity) ApiResponse.getResponse(response, new ProfileEntity());
            if (statusCode == 201) {
                LogUtil.info(this, "Profile Id: " + responseEntity.getId());
                LogUtil.info(this, "New name: " + responseEntity.getName());
                LogUtil.info(this, "New email: " + responseEntity.getEmail());
                LogUtil.info(this, "New gender: " + responseEntity.getGender());
                LogUtil.info(this, "New status: " + responseEntity.getStatus());


                Assert.assertEquals(responseEntity.getId(), id);
                Assert.assertNotEquals(responseEntity.getName(), updatedFullName);
                Assert.assertNotEquals(responseEntity.getEmail(), updatedEmail);
                Assert.assertNotEquals(responseEntity.getGender(), updatedGender);
                Assert.assertNotEquals(responseEntity.getStatus(), updatedStatus);

            }
        } catch (Exception e) {
            LogUtil.error(this, "Error updating person: " + e);
        }
    }

    public void updateAllUserDetailsWithMalformed(String id) {

        updatedFullName = updatedFName + " " + updatedLName;
        basePath = FileUtil.getSetting("gaji.gesa.specific.user", id);


        String updateMalformedRequest = "{\n" +
                "    \"id\": \"4895\",\n" +
                "    \"name\": \"Richie Horsi\",\n" +
                "    \"email\": \"kihn_richie@huel.info\",\n" +
                "    \"gender\": \"male\"\n" +
                "    \"status\": \"active\"\n" +
                "}";

        LogUtil.info(this, "New update all request: " + updateMalformedRequest);

        try {
            response = ApiResponse.getRestAssuredPatchResponse(baseUri, basePath, RequestHttpUtils.getBearerTokenHeaders(gajiGesaToken), updateMalformedRequest);
            statusCode = response.getStatusCode();
            LogUtil.info(this, "Status code: " + statusCode);

        } catch (Exception e) {
            LogUtil.error(this, "Response error message: " + e);
        }
    }

    public void verifyMalformedIsReturned() {
        try {
            ProfileEntity responseEntity = (ProfileEntity) ApiResponse.getResponse(response, new ProfileEntity());
            if (statusCode >= 400) {
                LogUtil.info(this, "Error message: " + responseEntity.getMessage());
                Assert.assertEquals(responseEntity.getMessage(), "Error occurred while parsing request parameters");
            }
        } catch (Exception e) {
            LogUtil.error(this, "Error updating person: " + e);
        }
    }

    public void submitDeleteUser() {

        basePath = FileUtil.getSetting("gaji.gesa.specific.user", String.valueOf(id));

        try {
            LogUtil.info(this, "Deleting id of " + id);
            response = ApiResponse.getDeletedResponse(baseUri, basePath, RequestHttpUtils.getBearerTokenHeaders(gajiGesaToken));
            statusCode = response.getStatusCode();
            LogUtil.info(this, "Status code: " + statusCode);

        } catch (Exception e) {
            LogUtil.error(this, "Response error message: " + e);
        }
    }

    public void verifyUserIsDeleted() {
        Assert.assertEquals(statusCode, 204);
    }
}


