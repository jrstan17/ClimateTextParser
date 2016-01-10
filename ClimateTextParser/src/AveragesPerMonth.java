
public class AveragesPerMonth extends NumberCruncher {

	@Override
	public void calculate() {
		// gives the averaged average temp of all years by month

		double[] maxTempSums = new double[Utility.MONTHS_IN_YEAR];
		double[] maxTempAvgs = new double[Utility.MONTHS_IN_YEAR];
		int[] maxTempDays = new int[Utility.MONTHS_IN_YEAR];
		
		double[] minTempSums = new double[Utility.MONTHS_IN_YEAR];
		double[] minTempAvgs = new double[Utility.MONTHS_IN_YEAR];
		int[] minTempDays = new int[Utility.MONTHS_IN_YEAR];
		
		double[] avgTempSums = new double[Utility.MONTHS_IN_YEAR];
		double[] avgTempAvgs = new double[Utility.MONTHS_IN_YEAR];
		int[] avgTempDays = new int[Utility.MONTHS_IN_YEAR];
		
		double[] precipSums = new double[Utility.MONTHS_IN_YEAR];
		double[] precipAvgs = new double[Utility.MONTHS_IN_YEAR];
		int[] precipDays = new int[Utility.MONTHS_IN_YEAR];
		
		double[] snowfallSums = new double[Utility.MONTHS_IN_YEAR];
		double[] snowfallAvgs = new double[Utility.MONTHS_IN_YEAR];
		int[] snowfallDays = new int[Utility.MONTHS_IN_YEAR];

		ClimateDataCreator cdc = new ClimateDataCreator();
		ClimateData cd = cdc.getClimateData();

		for (Entry e : cd.getEntries()) {
			if (e.maxTemp() != null) {
				maxTempSums[e.month()] = maxTempSums[e.month()] + e.maxTemp();
				maxTempDays[e.month()]++;
			}
			if (e.minTemp() != null) {
				minTempSums[e.month()] = minTempSums[e.month()] + e.minTemp();
				minTempDays[e.month()]++;
			}
			if (e.avgTemp() != null) {
				avgTempSums[e.month()] = avgTempSums[e.month()] + e.avgTemp();
				avgTempDays[e.month()]++;
			}
			if (e.precip() != null) {
				precipSums[e.month()] = precipSums[e.month()] + e.precip();
				precipDays[e.month()]++;
			}
			if (e.snowfall() != null) {
				snowfallSums[e.month()] = snowfallSums[e.month()] + e.snowfall();
				snowfallDays[e.month()]++;
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append("Month");
		sb.append(Parser.TAB);
		sb.append("Max Temp");
		sb.append(Parser.TAB);
		sb.append("Min Temp");
		sb.append(Parser.TAB);
		sb.append("Avg Temp");
		sb.append(Parser.TAB);
		sb.append("Precip");
		sb.append(Parser.TAB);
		sb.append("Snowfall");
		sb.append(Parser.NEW_LINE);

		for (int i = 0; i < Utility.MONTHS_IN_YEAR; i++) {
			maxTempAvgs[i] = maxTempSums[i] / maxTempDays[i];
			minTempAvgs[i] = minTempSums[i] / minTempDays[i];
			avgTempAvgs[i] = avgTempSums[i] / avgTempDays[i];
			precipAvgs[i] = multiplyByDaysInMonth(precipSums[i] / precipDays[i], i);
			snowfallAvgs[i] = multiplyByDaysInMonth(snowfallSums[i] / snowfallDays[i], i);
			
			sb.append(Utility.getAbbreviateMonthName(i));
			sb.append(Parser.TAB);
			sb.append(maxTempAvgs[i]);
			sb.append(Parser.TAB);
			sb.append(minTempAvgs[i]);
			sb.append(Parser.TAB);
			sb.append(avgTempAvgs[i]);
			sb.append(Parser.TAB);
			sb.append(precipAvgs[i]);
			sb.append(Parser.TAB);
			sb.append(snowfallAvgs[i]);
			sb.append(Parser.NEW_LINE);
		}

		FileHandler.writeToFile(sb, Parser.OUTPUT_DIRECTORY, "output.txt");
	}
	
	private double multiplyByDaysInMonth(double value, int month){
		switch (month){
		case 0:
			value *= 31;
			break;
		case 1:
			value *= 28.25;
			break;
		case 2:
			value *= 31;
			break;
		case 3:
			value *= 30;
			break;
		case 4:
			value *= 31;
			break;
		case 5:
			value *= 30;
			break;
		case 6:
			value *= 31;
			break;
		case 7:
			value *= 31;
			break;
		case 8:
			value *= 30;
			break;
		case 9:
			value *= 31;
			break;
		case 10:
			value *= 30;
			break;
		case 11:
			value *= 31;
			break;
		}
		
		return value;
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
