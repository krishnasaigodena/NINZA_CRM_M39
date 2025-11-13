package Practice;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class WorkingWithAssertions {
	
	@Test
	public void test() {
		
//		String s= "Krishna";
//		System.out.println("Start");
//		SoftAssert soft = new SoftAssert();
//		soft.assertNotNull(s);
//		System.out.println("End");
//		soft.assertAll();
		
		String s= null;
		System.out.println("Start");
		SoftAssert soft = new SoftAssert();
		soft.assertNull(s);
		System.out.println("End");
		soft.assertAll();
		
//		System.out.println("Start");
//		SoftAssert soft = new SoftAssert();
//		soft.assertFalse("hdfc".equals("hfdc"));
//		System.out.println("End");
//		soft.assertAll();
		
//		System.out.println("Start");
//		SoftAssert soft = new SoftAssert();
//		soft.assertTrue("hdfc".equals("hfdc"));
//		System.out.println("End");
//		soft.assertAll();
		
//		System.out.println("Start");
//		SoftAssert soft = new SoftAssert();
//		soft.assertNotEquals("hdfc", "hdfc");
//		System.out.println("End");
//		soft.assertAll();
		
//		if ("hdfc".equals("hfdc"))
//			System.out.println("Pass");
//		else
//			System.out.println("Fail");
	}

}
