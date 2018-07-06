package rs.org.amss.model;

public class CalculateRegistrationRequest {
	public int municipalId;
	public int enginePower;
	public int engineVolume;
	public int productionYear;
	public int vehicleTypeId;
	public int premiumGroup;
	public int levelOfPreviousPremiumPolicy;
	public int numberOfDamages;
	public boolean newDrivingLicense;
	public boolean newRegistrationPlates;
	
	public CalculateRegistrationRequest(){
		premiumGroup = 2;
		vehicleTypeId = 7;
	}
}
