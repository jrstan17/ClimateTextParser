import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Set;

public class StatisticFactory {
	public static final int START_CLIMATE_YEAR = 1981;
	public static final int STOP_CLIMATE_YEAR = 2010;

	private ClimateDataCreator cdc;
	private ClimateData cd;

	public ArrayList<Entry> entries;
	public ArrayList<Entry> entriesNarrowed;
	private int numOfValues = 0;
	private StatIndex statIndex;

	public StatisticFactory() {
		cdc = new ClimateDataCreator();
		cd = cdc.getClimateData();

		entries = cd.getEntries();
	}

	public Entry getExtremeForDate(Calendar date, StatIndex stat, boolean isMax) {
		this.statIndex = stat;

		double extremeValue = 0;
		Entry extremeEntry = null;

		if (!isMax) {
			extremeValue = 9999;
		}

		for (Entry e : entries) {
			if (e.month() == date.get(Calendar.MONTH) && e.day() == date.get(Calendar.DATE)
					&& e.year() < date.get(Calendar.YEAR)) {
				if (statIndex == StatIndex.MAX_TEMP && e.maxTemp() != null) {
					if (isMax && e.maxTemp() >= extremeValue || !isMax && e.maxTemp() <= extremeValue) {
						extremeValue = e.maxTemp();
						extremeEntry = e;
					}
				} else if (statIndex == StatIndex.MIN_TEMP && e.minTemp() != null) {
					if (isMax && e.minTemp() >= extremeValue || !isMax && e.minTemp() <= extremeValue) {
						extremeValue = e.minTemp();
						extremeEntry = e;
					}
				} else if (statIndex == StatIndex.AVG_TEMP && e.avgTemp() != null) {
					if (isMax && e.avgTemp() >= extremeValue || !isMax && e.avgTemp() <= extremeValue) {
						extremeValue = e.avgTemp();
						extremeEntry = e;
					}
				} else if (statIndex == StatIndex.AVG_TEMP_DEPART && e.departTemp() != null) {
					if (isMax && e.departTemp() >= extremeValue || !isMax && e.departTemp() <= extremeValue) {
						extremeValue = e.departTemp();
						extremeEntry = e;
					}
				} else if (statIndex == StatIndex.PRECIP && e.precip() != null) {
					if (isMax && e.precip() >= extremeValue || !isMax && e.precip() <= extremeValue) {
						extremeValue = e.precip();
						extremeEntry = e;
					}
				} else if (statIndex == StatIndex.SNOWFALL && e.snowfall() != null) {
					if (isMax && e.snowfall() >= extremeValue || !isMax && e.snowfall() <= extremeValue) {
						extremeValue = e.snowfall();
						extremeEntry = e;
					}
				} else if (statIndex == StatIndex.SNOW_DEPTH && e.snowDepth() != null) {
					if (isMax && e.snowDepth() >= extremeValue || !isMax && e.snowDepth() <= extremeValue) {
						extremeValue = e.snowDepth();
						extremeEntry = e;
					}
				} // end if
			} // end if
		} // end for

		return extremeEntry;
	}

	public double getAverageMonthToDate(Calendar date, StatIndex stat) {
		double sum = 0;
		int count = 0;

		for (Entry e : entries) {
			if (date.get(Calendar.MONTH) == e.month() && date.get(Calendar.YEAR) == e.year()
					&& date.get(Calendar.DATE) < e.day()) {
				if (stat == StatIndex.MAX_TEMP && e.maxTemp() != null) {
					sum += e.maxTemp();
					count++;
				} else if (stat == StatIndex.MIN_TEMP && e.minTemp() != null) {
					sum += e.minTemp();
					count++;
				} else if (stat == StatIndex.AVG_TEMP && e.avgTemp() != null) {
					sum += e.avgTemp();
					count++;
				} else if (stat == StatIndex.PRECIP && e.precip() != null) {
					sum += e.precip();
					count++;
				} else if (stat == StatIndex.SNOWFALL && e.snowfall() != null) {
					sum += e.snowfall();
					count++;
				} else if (stat == StatIndex.SNOW_DEPTH && e.snowDepth() != null) {
					sum += e.snowDepth();
					count++;
				}
			}
		}

		return sum / count;
	}

	public double getSumMonthToDate(Calendar date, StatIndex stat) {
		double sum = 0;

		for (Entry e : entries) {
			if (date.get(Calendar.MONTH) == e.month() && date.get(Calendar.YEAR) == e.year()
					&& date.get(Calendar.DATE) < e.day()) {
				if (stat == StatIndex.MAX_TEMP && e.maxTemp() != null) {
					sum += e.maxTemp();
				} else if (stat == StatIndex.MIN_TEMP && e.minTemp() != null) {
					sum += e.minTemp();
				} else if (stat == StatIndex.AVG_TEMP && e.avgTemp() != null) {
					sum += e.avgTemp();
				} else if (stat == StatIndex.PRECIP && e.precip() != null) {
					sum += e.precip();
				} else if (stat == StatIndex.SNOWFALL && e.snowfall() != null) {
					sum += e.snowfall();
				} else if (stat == StatIndex.SNOW_DEPTH && e.snowDepth() != null) {
					sum += e.snowDepth();
				}
			}
		}

		return sum;
	}

	public ValueYearPair getExtremeForMonthToDate(Calendar date, StatIndex stat, boolean isMax) {
		HashMap<Integer, Double> map = new HashMap<>();
		double extremeValue = 0;
		int extremeYear = 0;

		if (!isMax) {
			extremeValue = 9999;
			extremeYear = 9999;
		}

		for (Entry e : entries) {
			if (e.date().compareTo(date) <= 0 && e.month() <= date.get(Calendar.MONTH)) {
				if (e.month() == date.get(Calendar.MONTH) && e.day() <= date.get(Calendar.DATE)) {
					Double initValue = map.getOrDefault(e.year(), 0.0);
					if (e.get(stat) != null) {
						map.put(e.year(), initValue + e.get(stat));
					}
				}
			}
		}

		for (java.util.Map.Entry<Integer, Double> e : map.entrySet()) {
			if (isMax && e.getValue() >= extremeValue || !isMax && e.getValue() <= extremeValue) {
				extremeValue = e.getValue();
				extremeYear = e.getKey();
			}
		}

		return new ValueYearPair(extremeValue, extremeYear);
	}

	public double getAverageYearToDate(Calendar date, StatIndex stat) {
		double sum = 0;
		int count = 0;

		for (Entry e : entries) {
			if ((date.get(Calendar.MONTH) == e.month() && date.get(Calendar.YEAR) == e.year()
					&& date.get(Calendar.DATE) < e.day())
					|| (date.get(Calendar.MONTH) < e.month() && date.get(Calendar.YEAR) == e.year())) {
				if (stat == StatIndex.MAX_TEMP && e.maxTemp() != null) {
					sum += e.maxTemp();
					count++;
				} else if (stat == StatIndex.MIN_TEMP && e.minTemp() != null) {
					sum += e.minTemp();
					count++;
				} else if (stat == StatIndex.AVG_TEMP && e.avgTemp() != null) {
					sum += e.avgTemp();
					count++;
				} else if (stat == StatIndex.PRECIP && e.precip() != null) {
					sum += e.precip();
					count++;
				} else if (stat == StatIndex.SNOWFALL && e.snowfall() != null) {
					sum += e.snowfall();
					count++;
				} else if (stat == StatIndex.SNOW_DEPTH && e.snowDepth() != null) {
					sum += e.snowDepth();
					count++;
				}
			}
		}

		return sum / count;
	}

	public double getSumYearToDate(Calendar date, StatIndex stat) {
		double sum = 0;

		for (Entry e : entries) {
			if ((date.get(Calendar.MONTH) == e.month() && date.get(Calendar.YEAR) == e.year()
					&& date.get(Calendar.DATE) < e.day())
					|| (date.get(Calendar.MONTH) < e.month() && date.get(Calendar.YEAR) == e.year())) {
				if (stat == StatIndex.MAX_TEMP && e.maxTemp() != null) {
					sum += e.maxTemp();
				} else if (stat == StatIndex.MIN_TEMP && e.minTemp() != null) {
					sum += e.minTemp();
				} else if (stat == StatIndex.AVG_TEMP && e.avgTemp() != null) {
					sum += e.avgTemp();
				} else if (stat == StatIndex.PRECIP && e.precip() != null) {
					sum += e.precip();
				} else if (stat == StatIndex.SNOWFALL && e.snowfall() != null) {
					sum += e.snowfall();
				} else if (stat == StatIndex.SNOW_DEPTH && e.snowDepth() != null) {
					sum += e.snowDepth();
				}
			}
		}

		return sum;
	}

	public Entry getExtremeForDateRange(Calendar start, Calendar stop, StatIndex stat, boolean isMax) {
		ArrayList<Entry> entryList = new ArrayList<Entry>();
		this.statIndex = stat;

		double extremeValue = 0;
		Entry extremeEntry = null;

		if (!isMax) {
			extremeValue = 9999;
		}

		// make the dates inclusive
		start.add(Calendar.DATE, -1);
		stop.add(Calendar.DATE, 1);

		for (Entry e : entries) {
			if (e.date().after(start) && e.date().before(stop)) {
				if (statIndex == StatIndex.MAX_TEMP && e.maxTemp() != null) {
					if (isMax && e.maxTemp() >= extremeValue || !isMax && e.maxTemp() <= extremeValue) {
						extremeValue = e.maxTemp();
						extremeEntry = e;
					}
				} else if (statIndex == StatIndex.MIN_TEMP && e.minTemp() != null) {
					if (isMax && e.minTemp() >= extremeValue || !isMax && e.minTemp() <= extremeValue) {
						extremeValue = e.minTemp();
						extremeEntry = e;
					}
				} else if (statIndex == StatIndex.AVG_TEMP && e.avgTemp() != null) {
					if (isMax && e.avgTemp() >= extremeValue || !isMax && e.avgTemp() <= extremeValue) {
						extremeValue = e.avgTemp();
						extremeEntry = e;
					}
				} else if (statIndex == StatIndex.AVG_TEMP_DEPART && e.departTemp() != null) {
					if (isMax && e.departTemp() >= extremeValue || !isMax && e.departTemp() <= extremeValue) {
						extremeValue = e.departTemp();
						extremeEntry = e;
					}
				} else if (statIndex == StatIndex.PRECIP && e.precip() != null) {
					if (isMax && e.precip() >= extremeValue || !isMax && e.precip() <= extremeValue) {
						extremeValue = e.precip();
						extremeEntry = e;
					}
				} else if (statIndex == StatIndex.SNOWFALL && e.snowfall() != null) {
					if (isMax && e.snowfall() >= extremeValue || !isMax && e.snowfall() <= extremeValue) {
						extremeValue = e.snowfall();
						extremeEntry = e;
					}
				} else if (statIndex == StatIndex.SNOW_DEPTH && e.snowDepth() != null) {
					if (isMax && e.snowDepth() >= extremeValue || !isMax && e.snowDepth() <= extremeValue) {
						extremeValue = e.snowDepth();
						extremeEntry = e;
					}
				} // end if
			} // end if
		} // end for

		return extremeEntry;
	}

	public double getNormalForDate(Calendar date, StatIndex stat) {
		ArrayList<Entry> entryList = new ArrayList<Entry>();
		this.statIndex = stat;

		for (Entry e : entries) {
			if (e.month() == date.get(Calendar.MONTH) && e.day() == date.get(Calendar.DATE)
					&& e.year() >= START_CLIMATE_YEAR && e.year() <= STOP_CLIMATE_YEAR) {
				entryList.add(e);
			}
		}

		entriesNarrowed = entryList;

		return avg(entryList);
	}

	public double getNormalMonthToDate(Calendar date, StatIndex stat) {
		ArrayList<Entry> entryList = new ArrayList<Entry>();
		this.statIndex = stat;

		for (Entry e : entries) {
			if (e.month() == date.get(Calendar.MONTH) && e.day() <= date.get(Calendar.DATE)
					&& e.year() >= START_CLIMATE_YEAR && e.year() <= STOP_CLIMATE_YEAR) {
				entryList.add(e);
			}
		}

		double avg = avg(entryList);

		if (statIndex == StatIndex.PRECIP || statIndex == StatIndex.SNOWFALL) {
			avg *= date.get(Calendar.DATE);
		}

		entriesNarrowed = entryList;

		return avg;
	}

	public double getNormalForMonth(int month, StatIndex stat) {
		GregorianCalendar calendar = new GregorianCalendar(2001, month, 1);
		int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);

		return getNormalMonthToDate(calendar, stat);
	}

	public double getNormalYearToDate(Calendar date, StatIndex stat) {
		ArrayList<Entry> entryList = new ArrayList<Entry>();
		this.statIndex = stat;

		for (Entry e : entries) {
			if (e.year() >= START_CLIMATE_YEAR && e.year() <= STOP_CLIMATE_YEAR) {
				if (e.month() < date.get(Calendar.MONTH)
						|| (e.month() == date.get(Calendar.MONTH) && e.day() <= date.get(Calendar.DATE))) {
					entryList.add(e);
				}
			}
		}

		double avg = avg(entryList);

		if (statIndex == StatIndex.PRECIP || statIndex == StatIndex.SNOWFALL) {
			avg *= date.get(Calendar.DAY_OF_YEAR);
		}

		entriesNarrowed = entryList;

		return avg;
	}

	private double avg(ArrayList<Entry> entryList) {
		double sum = 0;

		numOfValues = entryList.size();

		if (statIndex == StatIndex.MAX_TEMP) {
			for (Entry e : entryList) {
				if (e.maxTemp() == null) {
					numOfValues--;
				} else {
					sum += e.maxTemp();
				}
			}
		} else if (statIndex == StatIndex.MIN_TEMP) {
			for (Entry e : entryList) {
				if (e.minTemp() == null) {
					numOfValues--;
				} else {
					sum += e.minTemp();
				}
			}
		} else if (statIndex == StatIndex.AVG_TEMP) {
			for (Entry e : entryList) {
				if (e.avgTemp() == null) {
					numOfValues--;
				} else {
					sum += e.avgTemp();
				}
			}
		} else if (statIndex == StatIndex.AVG_TEMP_DEPART) {
			for (Entry e : entryList) {
				if (e.departTemp() == null) {
					numOfValues--;
				} else {
					sum += e.departTemp();
				}
			}
		} else if (statIndex == StatIndex.PRECIP) {
			for (Entry e : entryList) {
				if (e.precip() == null) {
					numOfValues--;
				} else {
					sum += e.precip();
				}
			}
		} else if (statIndex == StatIndex.SNOWFALL) {
			for (Entry e : entryList) {
				if (e.snowfall() == null) {
					numOfValues--;
				} else {
					sum += e.snowfall();
				}
			}
		} else if (statIndex == StatIndex.SNOW_DEPTH) {
			for (Entry e : entryList) {
				if (e.snowDepth() == null) {
					numOfValues--;
				} else {
					sum += e.snowDepth();
				}
			}
		}

		return sum / numOfValues;
	}

	public static void main(String[] args) {
		StatisticFactory s = new StatisticFactory();
		GregorianCalendar date = new GregorianCalendar(1989, Calendar.FEBRUARY, 3);

		ValueYearPair vyp = s.getExtremeForMonthToDate(date, StatIndex.MIN_TEMP, true);

		System.out.println(vyp.value() + " in " + vyp.year());
	}
}