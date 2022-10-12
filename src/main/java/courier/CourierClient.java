package courier;

import config.BaseClient;
import io.restassured.response.ValidatableResponse;


public class CourierClient extends BaseClient {

    private final String ROOT = "/courier";
    private final String LOGIN = ROOT + "/login";
    private final String COURIER = "/courier/{courierId}";


    public ValidatableResponse createCourier(Courier courier) {
        return getSpec()
                .body(courier)
                .when()
                .post(ROOT)
                .then().log().all()
                .statusCode(201);
    }

    public ValidatableResponse createIdenticalCouriers(Courier courier) {
        getSpec()
                .body(courier)
                .when()
                .post(ROOT);

        return getSpec()
                .body(courier)
                .when()
                .post(ROOT)
                .then().log().all()
                .statusCode(409);

    }

    public ValidatableResponse createWithEmptyField(Courier courier) {
        return getSpec()
                .body(courier)
                .when()
                .post(ROOT)
                .then().log().all()
                .statusCode(400);

    }

    public ValidatableResponse login(CourierCredentials credentials) {
        return getSpec()
                .body(credentials)
                .when()
                .post(LOGIN)
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

    public ValidatableResponse loginWithEmptyPassword(CourierCredentials credentials) {
        credentials.setPassword("");
        return getSpec()
                .body(credentials)
                .when()
                .post(LOGIN)
                .then().log().all()
                .assertThat()
                .statusCode(400);
    }

    public ValidatableResponse loginWithEmptyLogin(CourierCredentials credentials) {
        credentials.setLogin("");
        return getSpec()
                .body(credentials)
                .when()
                .post(LOGIN)
                .then().log().all()
                .assertThat()
                .statusCode(400);
    }

    public ValidatableResponse loginWithWrongPassword(CourierCredentials credentials) {
        credentials.setPassword("-");
        return getSpec()
                .body(credentials)
                .when()
                .post(LOGIN)
                .then().log().all()
                .assertThat()
                .statusCode(404);
    }

    public ValidatableResponse loginWithWrongLogin(CourierCredentials credentials) {
        credentials.setLogin("-");
        return getSpec()
                .body(credentials)
                .when()
                .post(LOGIN)
                .then().log().all()
                .assertThat()
                .statusCode(404);
    }

    public void delete(int courierId) {
        getSpec()
                .pathParam("courierId", courierId)
                .when()
                .delete(COURIER)
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }
}

