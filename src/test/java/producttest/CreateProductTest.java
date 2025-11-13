package producttest;

import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import GenericUtility.BaseClass;
import GenericUtility.JavaUtility;
import GenericUtility.WebDriverUtility;
import objectrepository.AddProductsPage;
import objectrepository.HomePage;
import objectrepository.LoginPage;
import objectrepository.ProductPage;

public class CreateProductTest extends BaseClass {

	@Test(groups = {"smoke","regression"})
	public void createProductMobile() throws InterruptedException, IOException {
		
		String PRODUCT_NAME = eLib.togetDataFromExcelFile("Products", 1, 2);
		String CATEGORY_DD = eLib.togetDataFromExcelFile("Products", 1, 3);
		String QUANTITY = eLib.togetDataFromExcelFile("Products", 1, 4);
		String PRICE = eLib.togetDataFromExcelFile("Products", 1, 5);
		String VENDOR_ID_DD = eLib.togetDataFromExcelFile("Products", 1, 6);
		
						HomePage homePage = new HomePage(driver);
				        homePage.getproductLink().click();
				        ProductPage productsPage = new ProductPage(driver);
				        productsPage.getAddProductBtn().click();
				        AddProductsPage addProductPage = new AddProductsPage(driver);
				        addProductPage.getProductNameTF().sendKeys(PRODUCT_NAME + jLib.togetRandomNumber());
				        WebElement productCategoryDD = addProductPage.getProductCategoryDD();
				        
				      //Create product with Mobile
						Thread.sleep(2000);
						Select obj = new Select(productCategoryDD);
						obj.selectByValue(CATEGORY_DD); //Electronics
						WebElement quantityTF = addProductPage.getQuantityTF();
						quantityTF.clear();
						quantityTF.sendKeys(QUANTITY);
						WebElement priceTF = addProductPage.getPriceTF();
						priceTF.clear();
						priceTF.sendKeys(PRICE);
						WebElement vendorIdDD = addProductPage.getVendorIdDD();
						Select obj1 = new Select(vendorIdDD);
						obj1.selectByValue(VENDOR_ID_DD); //VID_447
						addProductPage.addBtn().click();
						
						//Verification
						WebElement toastMsg = homePage.getToastMsg();
						WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
						wait.until(ExpectedConditions.visibilityOf(toastMsg));
						String msg = toastMsg.getText();
						System.out.println(msg);
						homePage.getCloseToastMsgBtn().click();
						assertTrue(msg.contains("Successfully Added"));
						
						
	}
}
