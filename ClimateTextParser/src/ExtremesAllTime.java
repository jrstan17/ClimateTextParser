import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class ExtremesAllTime extends NumberCruncher {

	private GregorianCalendar[] dates = new GregorianCalendar[DAYS_IN_LEAP_YEAR];

	public ExtremesAllTime() {
		fillArrayWithDates();
	}

	@Override
	public void calculate() {
		// finds the extremes of each day of the year for all time

		int[] maxs = new int[DAYS_IN_LEAP_YEAR];
		int[] maxYears = new int[DAYS_IN_LEAP_YEAR];
		int[] mins = new int[DAYS_IN_LEAP_YEAR];
		mins = initialize(mins);
		int[] minYears = new int[DAYS_IN_LEAP_YEAR];
		double[] precips = new double[DAYS_IN_LEAP_YEAR];
		int[] precipYears = new int[DAYS_IN_LEAP_YEAR];
		double[] snowfalls = new double[DAYS_IN_LEAP_YEAR];
		int[] snowfallYears = new int[DAYS_IN_LEAP_YEAR];
		int[] snowDepths = new int[DAYS_IN_LEAP_YEAR];
		int[] snowDepthYears = new int[DAYS_IN_LEAP_YEAR];

		ClimateDataCreator cdc = new ClimateDataCreator();
		ClimateData cd = cdc.getClimateData();

		for (Entry e : cd.getEntries()) {
			int i = findDateIndex(e.date());

			if (e.maxTemp() != null && e.maxTemp() >= maxs[i]) {
//				maxs[i] = e.maxTemp();
//				maxYears[i] = e.year();
//			}
//			if (e.minTemp() != null && e.minTemp() <= mins[i]) {
//				mins[i] = e.minTemp();
//				minYears[i] = e.year();
//			}
//			if (e.precip() != null && e.precip() >= precips[i]) {
//				precips[i] = e.precip();
//				precipYears[i] = e.year();
//			}
//			if (e.snowfall() != null && e.snowfall() >= snowfalls[i]) {
//				snowfalls[i] = e.snowfall();
//				snowfallYears[i] = e.year();
//			}
//			if (e.snowDepth() != null && e.snowDepth() >= snowDepths[i]) {
//				snowDepths[i] = e.snowDepth();
				snowDepthYears[i] = e.year();
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append("Date");
		sb.append(Parser.TAB);
		sb.append("Max Temp");
		sb.append(Parser.TAB);
		sb.append("Year");
		sb.append(Parser.TAB);
		sb.append("Min Temp");
		sb.append(Parser.TAB);
		sb.append("Year");
		sb.append(Parser.TAB);
		sb.append("Precip");
		sb.append(Parser.TAB);
		sb.append("Year");
		sb.append(Parser.TAB);
		sb.append("Snowfall");
		sb.append(Parser.TAB);
		sb.append("Year");
		sb.append(Parser.TAB);
		sb.append("Snow Depth");
		sb.append(Parser.TAB);
		sb.append("Year");
		sb.append(Parser.NEW_LINE);

		SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd");

		for (int i = 0; i < DAYS_IN_LEAP_YEAR; i++) {
			sdf.setCalendar(dates[i]);
			sb.append(sdf.format(dates[i].getTime()));

			sb.append(Parser.TAB);
			sb.append(maxs[i]);
			sb.append(Parser.TAB);
			sb.append(maxYears[i]);

			sb.append(Parser.TAB);
			sb.append(mins[i]);
			sb.append(Parser.TAB);
			sb.append(minYears[i]);

			sb.append(Parser.TAB);
			sb.append(precips[i]);
			sb.append(Parser.TAB);
			sb.append(precipYears[i]);

			sb.append(Parser.TAB);

			if (snowfalls[i] != 0) {
				sb.append(snowfalls[i]);
			}

			sb.append(Parser.TAB);

			if (snowfalls[i] != 0) {
				sb.append(snowfallYears[i]);
			}

			sb.append(Parser.TAB);

			if (snowDepths[i] != 0) {
				sb.append(snowDepths[i]);
			}

			sb.append(Parser.TAB);

			if (snowDepths[i] != 0) {
				sb.append(snowDepthYears[i]);
			}

			sb.append(Parser.NEW_LINE);
		}

		FileHandler.writeToFile(sb, Parser.OUTPUT_DIRECTORY, "output.txt");
	}

	private void fillArrayWithDates() {
		GregorianCalendar date = new GregorianCalendar(2004, Calendar.JANUARY, 1);

		for (int i = 0; i < DAYS_IN_LEAP_YEAR; i++) {
			dates[i] = (GregorianCalendar) date.clone();
			date.add(Calendar.DATE, 1);
		}
	}

	private int findDateIndex(GregorianCalendar toFind) {

		int monthToFind = toFind.get(Calendar.MONTH);
		int dayToFind = toFind.get(Calendar.DATE);

		for (int i = 0; i < dates.length; i++) {
			GregorianCalendar current = dates[i];

			int monthCurrent = current.get(Calendar.MONTH);
			int dayCurrent = current.get(Calendar.DATE);

			if (monthCurrent == monthToFind && dayCurrent == dayToFind) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public void fillSpreadsheet() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void formatSpreadsheet() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void outputToFile() {
		// TODO Auto-generated method stub
		
	}
}
