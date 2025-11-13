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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import GenericUtility.ExcelFileUtility;
import GenericUtility.PropertiesFileUtility;

public class ImplementatinOfCreateProductWithMobile {

public static void main(String[] args) throws IOException, InterruptedException {
	PropertiesFileUtility putil = new PropertiesFileUtility();
	ExcelFileUtility eutil = new ExcelFileUtility();
	
	String BROWSER = putil.togetDataFromPropertiesFile("Browser");
	String URL = putil.togetDataFromPropertiesFile("Url");
	String USERNAME = putil.togetDataFromPropertiesFile("Username");
	String PASSWORD = putil.togetDataFromPropertiesFile("Password");
	
		
		//read test script data from excel
		FileInputStream fis1 = new FileInputStream("C:\\Users\\goden\\OneDrive\\Documents\\BasicSeleniumNotes\\NINZA_CRM_M39.xlsx");
		Workbook wb = WorkbookFactory.create(fis1);
		String ProductName = wb.getSheet("Products").getRow(1).getCell(2).getStringCellValue();
		String CategoryDD_Value = wb.getSheet("Products").getRow(1).getCell(3).getStringCellValue();
		String Quantity = wb.getSheet("Products").getRow(1).getCell(4).getStringCellValue();
		String Price = wb.getSheet("Products").getRow(1).getCell(5).getStringCellValue();
		String VendorDD_Value = wb.getSheet("Products").getRow(1).getCell(6).getStringCellValue();
		wb.close();
		
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

		        
		      //Create product with Mobile
				Thread.sleep(3000);
				driver.findElement(By.linkText("Products")).click();
				driver.findElement(By.xpath("//span[text() = 'Add Product']")).click();
				driver.findElement(By.name("productName")).sendKeys(ProductName);
				WebElement productCategoryDD = driver.findElement(By.name("productCategory"));
				Select obj = new Select(productCategoryDD);
				obj.selectByValue(CategoryDD_Value); //Electronics
				WebElement quantity = driver.findElement(By.name("quantity"));
				quantity.clear();
				quantity.sendKeys(Quantity);
				WebElement price = driver.findElement(By.name("price"));
				price.clear();
				price.sendKeys(Price);
				WebElement vendorIdDD = driver.findElement(By.name("vendorId"));
				Select obj1 = new Select(vendorIdDD);
				obj1.selectByValue(VendorDD_Value); //VID_447
				driver.findElement(By.xpath("//button[text() = 'Add']")).click();
				
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
