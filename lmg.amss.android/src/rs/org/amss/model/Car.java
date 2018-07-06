package rs.org.amss.model;

public class Car {
	public String manufacturer;
	public String model;
	public String kilometers = "0";
	public String year;
	
	public Car(String manufacturer, String model, String kilometers,
			String year) {
		super();
		this.manufacturer = manufacturer;
		this.model = model;
		this.kilometers = kilometers;
		this.year = year;
	}
	
	public Car() {
		// TODO Auto-generated constructor stub
	}
	
	
}
