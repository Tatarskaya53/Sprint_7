import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class CreateCourierTest {

    Courier courier;
    CourierClient courierClient = new CourierClient();
    private int courierId;

    @Before
    public void setup() {
        courier = Courier.createRandomCourier();

    }
    @After
    public void teardown() {
        if (courierId != 0) {
            courierClient.delete(courierId);
        }
    }
    @Test
    @DisplayName("Creating a courier")
    public void createCourierTest() {
        courierClient.createCourier(courier)
                .then()
                .statusCode(SC_CREATED);

        CourierCredentials credentials = CourierCredentials.from(courier);
        courierId = courierClient.loginCourier(credentials)
                .then()
                .extract().path("id");
        assertNotEquals(0, courierId);
    }
    @Test
    @DisplayName("Creating two identical couriers")
    public void createIdenticalCouriersTest() {
        courierClient.createCourier(courier);
        courierClient.createCourier(courier)
                .then().log().all()
                .statusCode(SC_CONFLICT)
                .assertThat()
                .body("message", equalTo("Ётот логин уже используетс€. ѕопробуйте другой."));

        CourierCredentials credentials = CourierCredentials.from(courier);
        courierId = courierClient.loginCourier(credentials)
                .then()
                .extract().path("id");
        assertNotEquals(0, courierId);
    }
    @Test
    @DisplayName("Creating a courier with an empty password")
    public void createWithEmptyPasswordTest() {
        courier.setPassword("");
        courierClient.createCourier(courier)
                .then().log().all()
                .statusCode(SC_BAD_REQUEST)
                .assertThat()
                .body("message", equalTo("Ќедостаточно данных дл€ создани€ учетной записи"));
    }
    @Test
    @DisplayName("Creating a courier with an empty login")
    public void createWithEmptyLoginTest() {
        courier.setLogin("");
        courierClient.createCourier(courier)
                .then().log().all()
                .statusCode(SC_BAD_REQUEST)
                .assertThat()
                .body("message", equalTo("Ќедостаточно данных дл€ создани€ учетной записи"));
    }
}