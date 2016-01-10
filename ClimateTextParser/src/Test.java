import java.util.Calendar;
import java.util.GregorianCalendar;

public class Test {

	public static void main(String[] args) {
		GregorianCalendar gc = new GregorianCalendar(2000, Calendar.FEBRUARY, 1);
		
		System.out.println(gc.getActualMaximum(Calendar.DAY_OF_MONTH));		
	}
}
