package com.jrstan17.climate.etc;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.jrstan17.climate.date.ClimateDate;

public class Entry {
	private ClimateDate date = new ClimateDate();
	private Double maxTemp = 0.0;
	private Double minTemp = 0.0;
	private Double avgTemp = 0.0;
	private Double departTemp = 0.0;
	private Double precip = 0.0;
	private Double snowfall = 0.0;
	private Double snowDepth = 0.0;

	public Entry(String[] pieces) {
		pieces = validatePieces(pieces);

		setDate(pieces[0]);
		setMaxTemp(pieces[1]);
		setMinTemp(pieces[2]);
		setAvgTemp(pieces[4]);
		setDepartTemp(pieces[5]);
		setPrecip(pieces[6]);
		setSnowfall(pieces[7]);
		setSnowDepth(pieces[8]);
	}

	public String[] validatePieces(String[] pieces) {
		for (int i = 0; i < pieces.length; i++) {
			if (pieces[i].equals("-")) {
				pieces[i] = "";
			} else if (pieces[i].equals("Trace")) {
				pieces[i] = "0";
			}
		}

		return pieces;
	}

	public ClimateDate date() {
		return date;
	}

	public int month() {
		return date.get(Calendar.MONTH);
	}

	public int day() {
		return date.get(Calendar.DATE);
	}

	public int year() {
		return date.get(Calendar.YEAR);
	}

	public void setDate(String date) {
		// assuming string is in format "MMM DD YYYY"
		String[] pieces = date.split(" ");

		switch (pieces[0]) {
		case "Jan":
			this.date.set(Calendar.MONTH, 0);
			break;
		case "Feb":
			this.date.set(Calendar.MONTH, 1);
			break;
		case "Mar":
			this.date.set(Calendar.MONTH, 2);
			break;
		case "Apr":
			this.date.set(Calendar.MONTH, 3);
			break;
		case "May":
			this.date.set(Calendar.MONTH, 4);
			break;
		case "Jun":
			this.date.set(Calendar.MONTH, 5);
			break;
		case "Jul":
			this.date.set(Calendar.MONTH, 6);
			break;
		case "Aug":
			this.date.set(Calendar.MONTH, 7);
			break;
		case "Sep":
			this.date.set(Calendar.MONTH, 8);
			break;
		case "Oct":
			this.date.set(Calendar.MONTH, 9);
			break;
		case "Nov":
			this.date.set(Calendar.MONTH, 10);
			break;
		case "Dec":
			this.date.set(Calendar.MONTH, 11);
			break;
		}

		int day = Integer.parseInt(pieces[1]);
		this.date.set(Calendar.DATE, day);

		int year = Integer.parseInt(pieces[2]);
		this.date.set(Calendar.YEAR, year);
	}

	public Double maxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(String maxTemp) {
		if (maxTemp.equals("")) {
			this.maxTemp = null;
		} else {
			this.maxTemp = Double.parseDouble(maxTemp);
		}
	}

	public Double minTemp() {
		return minTemp;
	}

	public void setMinTemp(String minTemp) {
		if (minTemp.equals("")) {
			this.minTemp = null;
		} else {
			this.minTemp = Double.parseDouble(minTemp);
		}
	}

	public Double avgTemp() {
		return avgTemp;
	}

	public void setAvgTemp(String avgTemp) {
		if (avgTemp.equals("")) {
			this.avgTemp = null;
		} else {
			this.avgTemp = Double.parseDouble(avgTemp);
		}
	}

	public Double departTemp() {
		return departTemp;
	}

	public void setDepartTemp(String departTemp) {
		if (departTemp.equals("")) {
			this.departTemp = null;
		} else {
			this.departTemp = Double.parseDouble(departTemp);
		}
	}

	public Double precip() {
		return precip;
	}

	public void setPrecip(String precip) {
		if (precip.equals("")) {
			this.precip = null;
		} else {
			this.precip = Double.parseDouble(precip);
		}
	}

	public Double snowfall() {
		return snowfall;
	}

	public void setSnowfall(String snowfall) {
		if (snowfall.equals("")) {
			this.snowfall = null;
		} else {
			this.snowfall = Double.valueOf(snowfall);
		}
	}

	public Double snowDepth() {
		return snowDepth;
	}

	public void setSnowDepth(String snowDepth) {
		if (snowDepth.equals("")) {
			this.snowDepth = null;
		} else {
			this.snowDepth = Double.parseDouble(snowDepth);
		}
	}

	public Double get(StatIndex statIndex) {
		if (statIndex == StatIndex.MAX_TEMP) {
			return maxTemp();
		} else if (statIndex == StatIndex.MIN_TEMP) {
			return minTemp();
		} else if (statIndex == StatIndex.AVG_TEMP) {
			return avgTemp();
		} else if (statIndex == StatIndex.AVG_TEMP_DEPART) {
			return departTemp();
		} else if (statIndex == StatIndex.PRECIP) {
			return precip();
		} else if (statIndex == StatIndex.SNOWFALL) {
			return snowfall();
		} else if (statIndex == StatIndex.SNOW_DEPTH) {
			return snowDepth();
		} else {
			return 0.0;
		}
	}
}
