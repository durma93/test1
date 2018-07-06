package rs.org.amss.model;

public class MyCar {
	public Car car;
	public Service service;
	public Fuel fuel;
	
	public MyCar() {
		// TODO Auto-generated constructor stub
	}

	public MyCar(Car car, Service service, Fuel fuel) {
		super();
		this.car = car;
		this.service = service;
		this.fuel = fuel;
	}
	
}
