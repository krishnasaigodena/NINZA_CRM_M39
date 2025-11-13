package Practice;

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

import GenericUtility.JavaUtility;
import GenericUtility.WebDriverUtility;
import objectrepository.AddProductsPage;
import objectrepository.HomePage;
import objectrepository.LoginPage;
import objectrepository.ProductPage;

public class CreateProductMobile {

	public static void main(String[] args) throws IOException, InterruptedException {
		
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
		String ProductName = wb.getSheet("Products").getRow(1).getCell(2).getStringCellValue();
		String CategoryDD_Value = wb.getSheet("Products").getRow(1).getCell(3).getStringCellValue();
		String Quantity = wb.getSheet("Products").getRow(1).getCell(4).getStringCellValue();
		String Price = wb.getSheet("Products").getRow(1).getCell(5).getStringCellValue();
		String VendorDD_Value = wb.getSheet("Products").getRow(1).getCell(6).getStringCellValue();
		wb.close();
		
		JavaUtility jLib = new JavaUtility();
		
		WebDriverUtility wLib = new WebDriverUtility();
		
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
		        HomePage homePage = new HomePage(driver);
		        homePage.getproductLink().click();
		        ProductPage productsPage = new ProductPage(driver);
		        productsPage.getAddProductBtn().click();
		        AddProductsPage addProductPage = new AddProductsPage(driver);
		        addProductPage.getProductNameTF().sendKeys(ProductName+jLib.togetRandomNumber());
		        WebElement productCategoryDD = addProductPage.getProductCategoryDD();
		        
		      //Create product with Mobile
				Thread.sleep(2000);
				/*
				 * driver.findElement(By.linkText("Products")).click();
				 * driver.findElement(By.xpath("//span[text() = 'Add Product']")).click();
				 * driver.findElement(By.name("productName")).sendKeys(ProductName+jLib.
				 * togetRandomNumber()); WebElement productCategoryDD =
				 * driver.findElement(By.name("productCategory"));
				 * wLib.select(productCategoryDD, CategoryDD_Value);
				 */
				Select obj = new Select(productCategoryDD);
				obj.selectByValue(CategoryDD_Value); //Electronics
				WebElement quantityTF = addProductPage.getQuantityTF();
				
				//WebElement quantity = driver.findElement(By.name("quantity"));
				quantityTF.clear();
				quantityTF.sendKeys(Quantity);
				WebElement priceTF = addProductPage.getPriceTF();
				//WebElement price = driver.findElement(By.name("price"));
				priceTF.clear();
				priceTF.sendKeys(Price);
				WebElement vendorIdDD = addProductPage.getVendorIdDD();
				//WebElement vendorIdDD = driver.findElement(By.name("vendorId"));
				//wLib.select(vendorIdDD, VendorDD_Value);
				Select obj1 = new Select(vendorIdDD);
				obj1.selectByValue(VendorDD_Value); //VID_447
				addProductPage.addBtn().click();
				//driver.findElement(By.xpath("//button[text() = 'Add']")).click();
				
				//Verification
				WebElement toastMsg = homePage.getToastMsg();
				//WebElement toastMsg = driver.findElement(By.xpath("//div[@role='alert']"));
				//wLib.waitForVisibilityOfElement(driver, toastMsg);
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
				wait.until(ExpectedConditions.visibilityOf(toastMsg));
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
				//HomePage homePage = new HomePage(driver);
				homePage.logout();
				/*
				 * WebElement userIcon = driver.findElement(By.className("user-icon"));
				 * wLib.mouseHoverOnWebElement(driver, userIcon); //Actions action = new
				 * Actions(driver); //action.moveToElement(userIcon).perform(); WebElement
				 * logoutBtn = driver.findElement(By.xpath("//div[text()='Logout ']"));
				 * logoutBtn.click();
				 */
				
				//Close the browser
				driver.quit();
				
	}

}
