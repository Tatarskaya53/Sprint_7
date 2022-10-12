import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotEquals;

public class LoginCourierTest {
    Courier courier;
    CourierClient courierClient = new CourierClient();
    private int courierId;

    @Before
    public void setup() {
        courier = Courier.createRandomCourier();
        courierClient.createCourier(courier);
    }

    @After
    public void teardown() {
        if (courierId != 0) {
            courierClient.delete(courierId);
        }
    }

    @Test
    @DisplayName("Courier authorization")
    public void loginCourierTest() {
        CourierCredentials credentials = CourierCredentials.from(courier);
        courierId = courierClient.login(credentials)
                .extract().path("id");

        assertNotEquals(0, courierId);
    }

    @Test
    @DisplayName("Courier authorization with empty password")
    public void loginWithEmptyPasswordTest() {
        CourierCredentials credentials = CourierCredentials.from(courier);
        courierId = courierClient.login(credentials)
                .extract().path("id");
        courierClient.loginWithEmptyPassword(credentials)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));

    }

    @Test
    @DisplayName("Courier authorization with empty login")
    public void loginWithEmptyLoginTest() {
        CourierCredentials credentials = CourierCredentials.from(courier);
        courierId = courierClient.login(credentials)
                .extract().path("id");
        courierClient.loginWithEmptyLogin(credentials)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));

    }

    @Test
    @DisplayName("Courier authorization with wrong password")
    public void loginWithWrongPasswordTest() {
        CourierCredentials credentials = CourierCredentials.from(courier);
        courierId = courierClient.login(credentials)
                .extract().path("id");
        courierClient.loginWithWrongPassword(credentials)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));

    }

    @Test
    @DisplayName("Courier authorization with wrong login")
    public void loginWithWrongLoginTest() {
        CourierCredentials credentials = CourierCredentials.from(courier);
        courierId = courierClient.login(credentials)
                .extract().path("id");
        courierClient.loginWithWrongLogin(credentials)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));

    }
}
