import order.OrderClient;
import order.Order;
import order.OrderTrack;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import java.util.List;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Issue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    Order order;
    OrderClient orderClient = new OrderClient();

    @Parameters
    public static Object[][] OrderParameters() {
        return new Object[][]{
                {"Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", null},
                {"Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", List.of("BLACK")},
                {"Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", List.of("GREY")},
                {"Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", List.of("BLACK", "GREY")},
        };
    }

    @Test
    @DisplayName("Creating an order")
    @Issue("BUG")
    public void OrderParametersTest() {
        order = Order.createOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        int track = orderClient.createOrder(order)
                .extract()
                .path("track");

        orderClient.cancelOrder(new OrderTrack(track));

    }

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final List<String> color;

    public CreateOrderTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<String> color) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }
}
