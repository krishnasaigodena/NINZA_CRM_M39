package GenericUtility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class JavaUtility {

	public int togetRandomNumber()
	{
		Random ran = new Random();
		int randomCount = ran.nextInt(500);
		return randomCount;
	}
	
	public String getCurrentDate()
	{
		Date d = new Date();
		String date = d.toString().replace(" ", "_").replace(":", "_");
		return date;
	}
	
	public String togetRequiredDate(int days) {
		Date date = new Date();
		SimpleDateFormat sim = new SimpleDateFormat("dd-MM-yyyy");
		sim.format(date);
		Calendar cal = sim.getCalendar();
		cal.add(Calendar.DAY_OF_MONTH, days);
		String daterequired = sim.format(cal.getTime());
		return daterequired;
	}
}
