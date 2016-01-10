package com.jrstan17.climate.datacreator;
import java.util.ArrayList;
import java.util.Calendar;

import com.jrstan17.climate.etc.Entry;

public class ClimateData {
	private ArrayList<Entry> entries = new ArrayList<>();

	public void add(String[] pieces) {
		entries.add(new Entry(pieces));
	}

	public double getAvgTempForMonth(int month, int year) {
		double sum = 0;
		int count = 0;

		for (Entry e : entries) {
			if (e.month() == month && e.year() == year) {
				sum += e.avgTemp();
				count++;
			}
		}

		return sum / count;
	}

	public ArrayList<Entry> getEntries() {
		return entries;
	}

	public Entry getEntry(Calendar date) {
		for (Entry e : entries) {
			if (e.day() == date.get(Calendar.DATE) && e.month() == date.get(Calendar.MONTH)
					&& e.year() == date.get(Calendar.YEAR)) {
				return e;
			}
		}
		
		return null;
	}
}
