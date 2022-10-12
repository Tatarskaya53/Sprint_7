import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import org.junit.After;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;


public class CreateCourierTest {

    Courier courier;
    CourierClient courierClient = new CourierClient();
    private int courierId;

    @After
    public void teardown() {
        if (courierId != 0) {
            courierClient.delete(courierId);
        }
    }

    @Test
    @DisplayName("Creating a courier")
    public void createCourierTest() {
        courier = Courier.createRandomCourier();
        boolean isOk = courierClient.createCourier(courier)
                .extract().path("ok");

        CourierCredentials credentials = CourierCredentials.from(courier);
        courierId = courierClient.login(credentials)
                .extract().path("id");

        assertTrue(isOk);
        assertNotEquals(0, courierId);
    }

    @Test
    @DisplayName("Creating two identical couriers")
    public void createIdenticalCouriersTest() {
        courier = Courier.createRandomCourier();
        courierClient.createIdenticalCouriers(courier)
                .assertThat()
                .body("message", equalTo("Ётот логин уже используетс€. ѕопробуйте другой."));

        CourierCredentials creds = CourierCredentials.from(courier);
        courierId = courierClient.login(creds)
                .extract().path("id");

        assertNotEquals(0, courierId);
    }

    @Test
    @DisplayName("Creating a courier with an empty password")
    public void createWithEmptyPasswordTest() {
        courier = Courier.createCourierWithoutPassword();
        courierClient.createWithEmptyField(courier)
                .assertThat()
                .body("message", equalTo("Ќедостаточно данных дл€ создани€ учетной записи"));
    }

    @Test
    @DisplayName("Creating a courier with an empty login")
    public void createWithEmptyLoginTest() {
        courier = Courier.createCourierWithoutLogin();
        courierClient.createWithEmptyField(courier)
                .assertThat()
                .body("message", equalTo("Ќедостаточно данных дл€ создани€ учетной записи"));
    }
}