package Practice;

import java.io.IOException;
import java.time.Duration;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import GenericUtility.ExcelFileUtility;
import objectrepository.HomePage;
import objectrepository.LoginPage;

public class WorkingWithDataProvider {
	
	@Test(dataProvider = "loginDetails")
	public void login(String username, String password) {
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		LoginPage lp = new LoginPage(driver);
		lp.login("http://49.249.28.218:8098/", username, password);
		
		HomePage hp = new HomePage(driver);
		hp.logout();
		driver.quit();
	}
	
	@DataProvider
	public Object[][] loginDetails() throws EncryptedDocumentException, IOException {
		ExcelFileUtility eLib = new ExcelFileUtility();
		int rowCount = eLib.togetRowCount("DataProvider");
		
		Object[][] objArr = new Object[rowCount][2];
		
//		objArr[0][0] = "rmgyantra";
//		objArr[0][1] = "rmgy@9999";
//		objArr[1][0] = "rmgyantra";
//		objArr[1][1] = "rmgy@9999";
//		objArr[2][0] = "rmgyantra";
//		objArr[2][1] = "rmgy@9999";
		
		
		for(int r=1;r<=rowCount;r++) {
			objArr[r-1][0]=eLib.togetDataFromExcelFile("DataProvider", r,0);
			objArr[r-1][1]=eLib.togetDataFromExcelFile("DataProvider", r,1);
		}
		
		return objArr;
	}

}
