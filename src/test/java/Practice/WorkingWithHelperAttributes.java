package Practice;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class WorkingWithHelperAttributes {

	//@Test(priority = -10, invocationCount = 5, threadPoolSize = 2, enabled = false)
	@Test
	public void productCreation() throws InterruptedException { //ascii value of p 112
	System.out.println("productCreation");
	WebDriver driver = new ChromeDriver();
	Thread.sleep(2000);
	driver.close();
	}
	
	@Test(dependsOnMethods ={"productCreation", "updateProduct"})
	public void deleteProduct() { //ascii value of d 100
		System.out.println("deleteProduct");
	}
	
	@Test(dependsOnMethods ="productCreation")
	public void updateProduct() {
		System.out.println("updateProduct");
	}
}
