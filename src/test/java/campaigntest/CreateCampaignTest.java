package campaigntest;

import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import GenericUtility.BaseClass;
import GenericUtility.ExcelFileUtility;
import GenericUtility.JavaUtility;
import GenericUtility.PropertiesFileUtility;
import GenericUtility.WebDriverUtility;
import objectrepository.CampaignsPage;
import objectrepository.HomePage;
import objectrepository.LoginPage;
import objectrepository.createCampaignPage;
@Listeners(GenericUtility.ListenerImplementation.class)

public class CreateCampaignTest extends BaseClass {
	
	@Test(groups = {"smoke","regression"})
	public void createCampaignWithMandatoryFieldsTest() throws IOException, InterruptedException {
	
				String CAMPAIGN_NAME = eLib.togetDataFromExcelFile("Campaigns", 1, 2);
				String TARGET_SIZE = eLib.togetDataFromExcelFile("Campaigns", 1, 3);
		
				//Create campaign with Mandatory fields
				CampaignsPage campaignsPage = new CampaignsPage(driver);
				campaignsPage.getAddCreateCampaignBtn().click();
				Thread.sleep(1000);
				createCampaignPage createCampaignPage = new createCampaignPage(driver);
				createCampaignPage.getCampaignNameTF().sendKeys(CAMPAIGN_NAME);
				createCampaignPage.getTargetSizeTF().clear();
				createCampaignPage.getTargetSizeTF().sendKeys(TARGET_SIZE);
				createCampaignPage.getCreateCampaignBtn().click();
				
				//Verification
				HomePage homePage = new HomePage(driver);
				WebElement toastMsg = homePage.getToastMsg();
				wLib.waitForVisibilityOfElement(driver, toastMsg);
				String msg = toastMsg.getText();
				homePage.getCloseToastMsgBtn().click();
				assertTrue(msg.contains("Successfully Added"));
				System.out.println("Hi");
}  
	
	@Test(groups = "regression")
	public void createCampaignWithStatusTest() throws InterruptedException, IOException {
		//FileInputStream fis = new FileInputStream("./src/test/resources/CommnData1.properties");
		String CAMPAIGN_NAME = eLib.togetDataFromExcelFile("Campaigns", 4, 2);
		String TARGET_SIZE = eLib.togetDataFromExcelFile("Campaigns", 4, 3);
		String STATUS = eLib.togetDataFromExcelFile("Campaigns", 4, 4);
		

				//Create campaign with Mandatory fields
				CampaignsPage campaignsPage = new CampaignsPage(driver);
				campaignsPage.getAddCreateCampaignBtn().click();
				Thread.sleep(100);
				createCampaignPage createCampaignPage = new createCampaignPage(driver);
				createCampaignPage.getCampaignNameTF().sendKeys(CAMPAIGN_NAME);
				createCampaignPage.getTargetSizeTF().clear();
				createCampaignPage.getTargetSizeTF().sendKeys(TARGET_SIZE);
				createCampaignPage.getCampaignStatusTF().sendKeys(STATUS);
				createCampaignPage.getCreateCampaignBtn().click();
				
				//Verification
				HomePage homePage = new HomePage(driver);
				WebElement toastMsg = homePage.getToastMsg();
				wLib.waitForVisibilityOfElement(driver, toastMsg);
				String msg = toastMsg.getText();
				homePage.getCloseToastMsgBtn().click();
				assertTrue(msg.contains("Successfully Added"));
	}
	@Test(groups = "regression")
	public void createCampaignWithExpectedCloseDateTest() throws InterruptedException, IOException {
		// Read test script data from excel file
				//ExcelFileUtility eLib = new ExcelFileUtility();
				String CAMPAIGN_NAME = eLib.togetDataFromExcelFile("Campaigns", 7, 2);
				String TARGET_SIZE = eLib.togetDataFromExcelFile("Campaigns", 7, 3);
				
						//Create campaign with Mandatory fields
						CampaignsPage campaignsPage = new CampaignsPage(driver);
						campaignsPage.getAddCreateCampaignBtn().click();
				        Thread.sleep(1000);
						createCampaignPage createCampaignPage = new createCampaignPage(driver);
						createCampaignPage.getCampaignNameTF().sendKeys(CAMPAIGN_NAME);
						createCampaignPage.getExpectedCloseDateTF().sendKeys(jLib.togetRequiredDate(50));
						createCampaignPage.getTargetSizeTF().clear();
						createCampaignPage.getTargetSizeTF().sendKeys(TARGET_SIZE);
						createCampaignPage.getCreateCampaignBtn().click();



				        //Verification
				        HomePage homePage = new HomePage(driver);
						WebElement toastMsg = homePage.getToastMsg();
				        wLib.waitForVisibilityOfElement(driver, toastMsg);
				        String msg = toastMsg.getText();
				        homePage.getCloseToastMsgBtn().click();
				        assertTrue(msg.contains("Successfully Added"));
						
	}

}
