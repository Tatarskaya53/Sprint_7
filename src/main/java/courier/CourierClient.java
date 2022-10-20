package courier;

import config.BaseClient;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;


public class CourierClient extends BaseClient {

    private final String ROOT = "/courier";
    private final String LOGIN = ROOT + "/login";
    private final String COURIER = "/courier/{courierId}";


    public Response createCourier(Courier courier) {
          return getSpec()
                .body(courier)
                .when()
                .post(ROOT);
    }

    public Response loginCourier(CourierCredentials credentials) {
        return getSpec()
                .body(credentials)
                .when()
                .post(LOGIN);
    }
    public void delete(int courierId) {
        getSpec()
                .pathParam("courierId", courierId)
                .when()
                .delete(COURIER)
                .then().log().all();
    }
}