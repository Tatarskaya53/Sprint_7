import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

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
        CourierCredentials credentials = CourierCredentials.from(courier);
        courierId = courierClient.loginCourier(credentials)
                .then()
                .extract().path("id");
        courierClient.delete(courierId);
    }
    @Test
    @DisplayName("Courier authorization")
    public void loginCourierTest() {
        CourierCredentials credentials = CourierCredentials.from(courier);
        courierClient.loginCourier(credentials)
                .then()
                .statusCode(SC_OK);
    }
    @Test
    @DisplayName("Courier authorization with empty password")
    public void loginWithEmptyPasswordTest() {
        CourierCredentials credentials = CourierCredentials.from(courier);
        credentials.setPassword("");
        courierClient.loginCourier(credentials)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));

    }
    @Test
    @DisplayName("Courier authorization with empty login")
    public void loginWithEmptyLoginTest() {
        CourierCredentials credentials = CourierCredentials.from(courier);
        credentials.setLogin("");
        courierClient.loginCourier(credentials)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }
    @Test
    @DisplayName("Courier authorization with wrong password")
    public void loginWithWrongPasswordTest() {
        CourierCredentials credentials = CourierCredentials.from(courier);
        credentials.setPassword("-");
        courierClient.loginCourier(credentials)
                .then()
                .statusCode(SC_NOT_FOUND)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));
    }
    @Test
    @DisplayName("Courier authorization with wrong login")
    public void loginWithWrongLoginTest() {
        CourierCredentials credentials = CourierCredentials.from(courier);
        credentials.setLogin("-");
        courierClient.loginCourier(credentials)
                .then()
                .statusCode(SC_NOT_FOUND)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));
    }
}