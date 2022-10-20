import order.OrderClient;
import org.junit.Test;
import java.util.List;
import io.qameta.allure.junit4.DisplayName;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertTrue;

public class GetOrderTest {

    private List orders;

    @Test
    @DisplayName("Get a list of orders")
    public void GetOrderTest() {
        OrderClient orderClient = new OrderClient();
        orders = orderClient.getOrders()
                .then().log().all()
                .extract().path("orders");
        orderClient.getOrders()
                .then()
                .statusCode(SC_OK);

        assertTrue(orders.size() != 0);
    }

}