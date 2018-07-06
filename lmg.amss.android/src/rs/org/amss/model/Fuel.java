package rs.org.amss.model;

public class Fuel {
	public String amount;
	public String price;
	public String kilometers;
	public String date;
	public String gasStation;
	public String fuelType;

	public Fuel(String amount, String price, String kilometers, String date,
			String gasStation, String fuelType) {
		super();
		this.amount = amount;
		this.price = price;
		this.kilometers = kilometers;
		this.date = date;
		this.gasStation = gasStation;
		this.fuelType = fuelType;
	}

	public Fuel() {
		// TODO Auto-generated constructor stub
	}	
}

