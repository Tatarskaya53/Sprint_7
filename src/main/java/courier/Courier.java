package courier;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

@Data
public class Courier {
    private String login;
    private String password;
    private String firstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public static Courier createRandomCourier() {
        return new Courier(
                RandomStringUtils.randomAlphanumeric(7),
                RandomStringUtils.randomNumeric(7),
                RandomStringUtils.randomAlphabetic(7)
        );
    }
}