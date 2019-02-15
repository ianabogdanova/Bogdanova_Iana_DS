import com.jayway.restassured.RestAssured;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.NewUserPage;
import pages.RedirectedPage;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestNewUser extends ParametersLoader{
    private static WebDriver driver;
    private static User user = new User("user_1", "user_1@user.com", "111111", "111111");

    TestNewUser(){
        url = loadProperty("url");
        RestAssured.baseURI = url + "/user/all";
    }

    private void baseTest(User user, String result, String error, String errorText){
        driver.get(url);
        NewUserPage newUserPage = new NewUserPage(driver);
        assertTrue(newUserPage.isInitialized());

        newUserPage.enterName(user.getName());
        newUserPage.enterEmail(user.getEmail());
        newUserPage.enterPassword(user.getPassword());
        newUserPage.confirmPassword(user.getConfirmPassword());
        RedirectedPage redirectedPage = newUserPage.submit();
        assertEquals(result, redirectedPage.confirmationHeader(), "actual " + redirectedPage.confirmationHeader());
        if (result.equals("New User")) assertEquals(redirectedPage.getErrorText(error), errorText);
    }

    @BeforeAll
    public static void setUp(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("disable-gpu");
        options.addArguments("window-size=1200,1100");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);

    }
    @Test
    void testCreateNewUser() {
        baseTest(user, "All User", "", "");
        given().when().get("/json").then()
                .body("name", hasItem(user.getName()))
                .body("email", hasItem(user.getEmail()))
                .body("password",hasItem(user.getPassword()));
    }

    @Nested
    class TestNegatives {
        @ParameterizedTest(name = "''{1}':'{2}''")
        @ArgumentsSource( ExtensionProvider.class )
        void testNegatives(User user, String error, String errorText) {
            baseTest(user, "New User", error, errorText);
        }
    }

    static class ExtensionProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments( ExtensionContext context ) {
            return Stream.of(
                    Arguments.of(new User(user.getName(), "user_2@user.com", "111111","1111111"),
                            "user.name.error", "Must be unique"),
                    Arguments.of(new User("", "user_2@user.com", "111111","1111111"),
                            "user.name.error", "Required"),
                    Arguments.of(new User("user_2", "user_1@user.com", "111111","1111111"),
                            "user.email.error", "Must be unique"),
                    Arguments.of(new User("user_2", "mymail", "111111","1111111"),
                            "user.email.error", "Invalid email address"),
                    Arguments.of(new User("user_2", "", "111111","1111111"),
                            "user.email.error",  "Required"),
                    Arguments.of(new User("user_2", "user_2@user.com", "111111","1111111"),
                            "user.confirmationPassword.error",  "passwords are not the same"),
                    Arguments.of(new User("user_2", "user_2@user.com", "11111","11111"),
                            "user.password.error",  "Minimum size is 6"),
                    Arguments.of(new User("user_2", "user_2@user.com", "", ""),
                            "user.password.error", "Required"));
        }
    }
    @AfterAll
    public static void tierDown(){
        driver.close();
        given().when().delete();
    }
}


