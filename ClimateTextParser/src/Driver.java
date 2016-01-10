import java.util.Calendar;
import java.util.GregorianCalendar;

public class Driver {

	public static void main(String[] args) {
		System.out.println("Calculating...");

		DailyAlmanac da = new DailyAlmanac();

//		for (int year = 1893; year <= 1926; year++) {
//			for (int month = 0; month <= 11; month++) {
//				ms.setDate(month, year);
//				ms.calculate();
//			}
//		}
		
		da.setDate(new GregorianCalendar(1983, Calendar.JANUARY, 8));
		da.calculate();

//		NumberCruncher nc = new AveragesPerMonth();
//		nc.calculate();

		System.out.println("Calculations complete!");
	}
}
