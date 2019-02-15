
package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RedirectedPage extends PageObject {

    @FindBy(tagName = "h1")
    private WebElement header;

    @FindBy(id = "user.name.error")
    private WebElement userNameError;

    @FindBy(id = "user.email.error")
    private WebElement userEmailError;

    @FindBy(id = "user.password.error")
    private WebElement userPasswordError;

    @FindBy(id = "user.confirmationPassword.error")
    private WebElement userConfirmationPasswordError;

    public RedirectedPage(WebDriver driver) {
        super(driver);
    }

    public String confirmationHeader(){
        return header.getText();
    }

    public String getErrorText(String error) {
        switch(error){
            case "user.name.error": return userNameError.getText();
            case "user.email.error": return userEmailError.getText();
            case "user.password.error": return userPasswordError.getText();
            case "user.confirmationPassword.error": return userConfirmationPasswordError.getText();
        }
        return userNameError.getText();
    }
}
