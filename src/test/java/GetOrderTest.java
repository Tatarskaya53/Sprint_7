import order.OrderClient;
import org.junit.Test;
import java.util.List;
import io.qameta.allure.junit4.DisplayName;

import static org.junit.Assert.assertTrue;

public class GetOrderTest {

    private List orders;

    @Test
    @DisplayName("Get a list of orders")
    public void GetOrderTest() {
        OrderClient orderClient = new OrderClient();
        orders = orderClient.getOrders()
                .extract().path("orders");

        assertTrue(orders.size() != 0);
    }

}