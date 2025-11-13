package objectrepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	WebDriver driver;
	public LoginPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id = "username") private WebElement usernameTF;
	@FindBy(id = "inputPassword") private WebElement PasswordTF;
	@FindBy(xpath = "//button[text()='Sign In']") private WebElement signInBtn;
	
	public WebElement getUsernameTF() {
		return usernameTF;
	}
	
	public WebElement getPasswordTF() {
		return PasswordTF;
	}
	
	public WebElement getSignInTF() {
		return signInBtn;
	}
	
	public void login(String url, String username, String password)
	{
		driver.get(url);
		usernameTF.sendKeys(username);
		PasswordTF.sendKeys(password);
		signInBtn.click();
	}
}
