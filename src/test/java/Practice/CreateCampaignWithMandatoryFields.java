package Practice;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import GenericUtility.WebDriverUtility;
import objectrepository.CampaignsPage;
import objectrepository.HomePage;
import objectrepository.LoginPage;
import objectrepository.createCampaignPage;

public class CreateCampaignWithMandatoryFields {



	public static void main(String[] args) throws InterruptedException, IOException {
		//Read data from properties file
				FileInputStream fis = new FileInputStream("C:\\Users\\goden\\OneDrive\\Documents\\BasicSeleniumNotes\\CommnData1.properties");
				Properties prop = new Properties();
				prop.load(fis);
				String BROWSER = prop.getProperty("Browser");
				String URL = prop.getProperty("URL");
				String USERNAME = prop.getProperty("Username");
				String PASSWORD = prop.getProperty("Password");
				
				//read test script data from excel
				FileInputStream fis1 = new FileInputStream("C:\\Users\\goden\\OneDrive\\Documents\\BasicSeleniumNotes\\NINZA_CRM_M39.xlsx");
				Workbook wb = WorkbookFactory.create(fis1);
				String CAMPAIGN_NAME = wb.getSheet("Campaigns").getRow(1).getCell(2).getStringCellValue();
				String TARGET_SIZE = wb.getSheet("Campaigns").getRow(1).getCell(3).getStringCellValue();
				
				WebDriverUtility wLib = new WebDriverUtility();
				
					//launch the browser
				ChromeOptions settings = new ChromeOptions();
				Map<String, Object> prefs = new HashMap<>();
				prefs.put("profile.password_manager_leak_detection", false);
				settings.setExperimentalOption("prefs", prefs);
				
				        WebDriver driver = null;
				        if(BROWSER.equalsIgnoreCase("Chrome"))
				        	driver = new ChromeDriver(settings);
				        else if(BROWSER.equalsIgnoreCase("Edge"))
				        		driver = new EdgeDriver();
				        else if(BROWSER.equalsIgnoreCase("Safari"))
				        		driver = new SafariDriver();	
				        else if(BROWSER.equalsIgnoreCase("Firefox"))
				        		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		wLib.waitForPageLoad(driver);
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		
		//Login
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(URL, USERNAME, PASSWORD);
		/*
		 * driver.get("http://49.249.28.218:8098/");
		 * driver.findElement(By.id("username")).sendKeys(USERNAME);
		 * driver.findElement(By.id("inputPassword")).sendKeys(PASSWORD);
		 * driver.findElement(By.xpath("//button[text()='Sign In']")).click();
		 */
		
		//Create campaign with Mandatory fields
		CampaignsPage campaignsPage = new CampaignsPage(driver);
		campaignsPage.getAddCreateCampaignBtn().click();
		Thread.sleep(3000);
		//driver.findElement(By.xpath("//span[text()='Create Campaign']")).click();
		createCampaignPage createCampaignPage = new createCampaignPage(driver);
		createCampaignPage.getCampaignNameTF().sendKeys(CAMPAIGN_NAME);
		//driver.findElement(By.name("campaignName")).sendKeys(CAMPAIGN_NAME);
		WebElement targetSize = createCampaignPage.getTargetSizeTF();

		//WebElement targetSize = driver.findElement(By.name("targetSize"));
		targetSize.clear();
		targetSize.sendKeys(TARGET_SIZE);
		createCampaignPage.getCreateCampaignBtn().click();
		//driver.findElement(By.xpath("//button[text()='Create Campaign']")).click();
		
		//Verification
		HomePage homePage = new HomePage(driver);
		WebElement toastMsg = homePage.getToastMsg();
		//WebElement toastMsg = driver.findElement(By.xpath("//div[@role='alert']"));
		wLib.waitForVisibilityOfElement(driver, toastMsg);
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		//wait.until(ExpectedConditions.visibilityOf(toastMsg));
		String msg = toastMsg.getText();
		System.out.println(msg);
		if(msg.contains("Successfully Added")) {
			System.out.println("Campaign Created");
		} else{
			System.out.println("Campaign Not Created");
		}
		homePage.getCloseToastMsgBtn().click();
		//driver.findElement(By.xpath("//button[@aria-label='close']")).click();
		
		//Logout
		//HomePage homePage1 = new HomePage(driver);
		homePage.logout();
		//WebElement userIcon = driver.findElement(By.className("user-icon"));
		//wLib.mouseHoverOnWebElement(driver, userIcon);
		//Actions action = new Actions(driver);
		//action.moveToElement(userIcon).perform();
		//WebElement logoutBtn = driver.findElement(By.xpath("//div[text()='Logout ']"));
		//logoutBtn.click();
		
		//Close the browser
		driver.quit();
		 
		}  
}