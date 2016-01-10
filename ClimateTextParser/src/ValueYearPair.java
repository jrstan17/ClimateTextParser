
public class ValueYearPair {
	private int year = 0;
	private double value = 0;
	
	public ValueYearPair(double value, int year){
		this.year = year;
		this.value = value;
	}

	public int year() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double value() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	
}
