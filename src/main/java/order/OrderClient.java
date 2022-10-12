package order;

import config.BaseClient;
import io.restassured.response.ValidatableResponse;
public class OrderClient extends BaseClient {

    private final String ORDER = "/orders";
    private final String ORDER_CANCEL = ORDER + "/cancel";



    public ValidatableResponse createOrder(Order order) {
        return getSpec()
                .body(order)
                .when()
                .post(ORDER)
                .then().log().all()
                .statusCode(201);
    }

    public ValidatableResponse cancelOrder(OrderTrack orderTrack) {
        return getSpec()
                .body(orderTrack)
                .when()
                .put(ORDER_CANCEL)
                .then().log().all()
                .statusCode(200);
    }


    public ValidatableResponse getOrders() {
        return getSpec()
                .get(ORDER)
                .then().log().all()
                .statusCode(200);
    }

}

