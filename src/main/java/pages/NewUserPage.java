package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewUserPage extends PageObject {


    @FindBy(id = "name")
    private WebElement name;

    @FindBy(id = "email")
    private WebElement email;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id = "confirmationPassword")
    private WebElement confirmationPassword;

    @FindBy(css = "[type=Submit]")
    private WebElement submitButton;



    public NewUserPage(WebDriver driver) {
        super(driver);
    }
    public boolean isInitialized() {
        return name.isDisplayed();
    }

    public void enterName(String name) {
        this.name.clear();
        this.name.sendKeys(name);
    }

    public void enterEmail(String email) {
        this.email.clear();
        this.email.sendKeys(email);
    }

    public void enterPassword(String password) {
        this.password.clear();
        this.password.sendKeys(password);
    }

    public void confirmPassword(String confirmationPassword) {
        this.confirmationPassword.clear();
        this.confirmationPassword.sendKeys(confirmationPassword);
    }


    public RedirectedPage submit() {
        submitButton.click();
        return new RedirectedPage(driver);

    }
}