package order;

import config.BaseClient;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
public class OrderClient extends BaseClient {

    private final String ORDER = "/orders";
    private final String ORDER_CANCEL = ORDER + "/cancel";



    public Response createOrder(Order order) {
        return getSpec()
                .body(order)
                .when()
                .post(ORDER);
    }

    public Response cancelOrder(OrderTrack orderTrack) {
        return getSpec()
                .body(orderTrack)
                .when()
                .put(ORDER_CANCEL);
    }
    public Response getOrders() {
        return getSpec()
                .get(ORDER);
    }
}