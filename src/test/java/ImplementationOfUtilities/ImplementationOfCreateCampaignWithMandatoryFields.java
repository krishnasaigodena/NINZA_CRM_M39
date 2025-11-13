package ImplementationOfUtilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import GenericUtility.ExcelFileUtility;
import GenericUtility.PropertiesFileUtility;

public class ImplementationOfCreateCampaignWithMandatoryFields {

	public static void main(String[] args) throws InterruptedException, IOException {
		PropertiesFileUtility putil = new PropertiesFileUtility();
		ExcelFileUtility eutil = new ExcelFileUtility();
		
		String BROWSER = putil.togetDataFromPropertiesFile("Browser");
		String URL = putil.togetDataFromPropertiesFile("Url");
		String USERNAME = putil.togetDataFromPropertiesFile("Username");
		String PASSWORD = putil.togetDataFromPropertiesFile("Password");
				
				//read test script data from excel
				FileInputStream fis1 = new FileInputStream("C:\\Users\\goden\\OneDrive\\Documents\\BasicSeleniumNotes\\NINZA_CRM_M39.xlsx");
				Workbook wb = WorkbookFactory.create(fis1);
				String CAMPAIGN_NAME = wb.getSheet("Campaigns").getRow(1).getCell(2).getStringCellValue();
				String TARGET_SIZE = wb.getSheet("Campaigns").getRow(1).getCell(3).getStringCellValue();
				
					//launch the browser
				        WebDriver driver = null;
				        if(BROWSER.equalsIgnoreCase("Chrome"))
				        	driver = new ChromeDriver();
				        else if(BROWSER.equalsIgnoreCase("Edge"))
				        		driver = new EdgeDriver();
				        else if(BROWSER.equalsIgnoreCase("Safari"))
				        		driver = new SafariDriver();	
				        else if(BROWSER.equalsIgnoreCase("Firefox"))
				        		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		
		//Login
		driver.get("http://49.249.28.218:8098/");
		driver.findElement(By.id("username")).sendKeys(USERNAME);
		driver.findElement(By.id("inputPassword")).sendKeys(PASSWORD);
		driver.findElement(By.xpath("//button[text()='Sign In']")).click();
		
		 String CAMP_NAME = eutil.togetDataFromExcelFile("Campaigns", 1, 2);
	     String SIZE = eutil.togetDataFromExcelFile("Campaigns", 1, 3);
		
		//Create campaign with Mandatory fields
		Thread.sleep(3000);
		driver.findElement(By.xpath("//span[text()='Create Campaign']")).click();
		driver.findElement(By.name("campaignName")).sendKeys(CAMPAIGN_NAME);
		WebElement targetSize = driver.findElement(By.name("targetSize"));
		targetSize.clear();
		targetSize.sendKeys(TARGET_SIZE);
		driver.findElement(By.xpath("//button[text()='Create Campaign']")).click();
		
		//Verification
		WebElement toastMsg = driver.findElement(By.xpath("//div[@role='alert']"));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOf(toastMsg));
		String msg = toastMsg.getText();
		System.out.println(msg);
		if(msg.contains("Successfully Added")) {
			System.out.println("Campaign Created");
		} else{
			System.out.println("Campaign Not Created");
		}
		driver.findElement(By.xpath("//button[@aria-label='close']")).click();
		
		//Logout
		WebElement userIcon = driver.findElement(By.className("user-icon"));
		Actions action = new Actions(driver);
		action.moveToElement(userIcon).perform();
		WebElement logoutBtn = driver.findElement(By.xpath("//div[text()='Logout ']"));
		action.moveToElement(logoutBtn).click().perform();
		
		//Close the browser
		driver.quit();
		 
		}  

	}


