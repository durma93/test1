package rs.org.amss.model;


public class AmssStation extends AmssStationFilter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4339181458923778144L;
	public int id;
	public String name;
	public String shortName;
	public String amssName;
	public double latitude;
	public double longitude;
	public String city;
	public String address;
	public String workingTime;
	public String phone;
	public int iconDrawableId;
	public AmssStation() {}
	public AmssStation(int id, String name, String shortName, String amssName,
			double latitude, double longitude, String city, String address,
			String workingTime, String phone,
			boolean isForInternationalDocuments, boolean isForTechnicalReview,
			boolean isForAmssMembership, boolean hasDrivingSchool,
			boolean hasCarWash, boolean hasCarService, boolean hasRestaurant,
			boolean isForRegistration, boolean isForVenchileInspectionAttest,
			boolean isForHelpOnRoad, boolean isForSpedition,
			boolean isForTourism, boolean hasCurrencyExchange,
			boolean isForInsurance, boolean isForTowing, boolean hasTNGPump,
			boolean hasShop, boolean hasSalesAndComplementOfTAGToll) {
		this.id = id;
		this.name = name;
		this.shortName = shortName;
		this.amssName = amssName;
		this.latitude = latitude;
		this.longitude = longitude;
		this.city = city;
		this.address = address;
		this.workingTime = workingTime;
		this.phone = phone;
		this.isForInternationalDocuments = isForInternationalDocuments;
		this.isForTechnicalReview = isForTechnicalReview;
		this.isForAmssMembership = isForAmssMembership;
		this.hasDrivingSchool = hasDrivingSchool;
		this.hasCarWash = hasCarWash;
		this.hasCarService = hasCarService;
		this.hasRestaurant = hasRestaurant;
		this.isForRegistration = isForRegistration;
		this.isForVehicleInspectionAttest = isForVenchileInspectionAttest;
		this.isForHelpOnRoad = isForHelpOnRoad;
		this.isForSpedition = isForSpedition;
		this.isForTourism = isForTourism;
		this.hasCurrencyExchange = hasCurrencyExchange;
		this.isForInsurance = isForInsurance;
		this.isForTowing = isForTowing;
		this.hasTNGPump = hasTNGPump;
		this.hasShop = hasShop;
		this.hasSalesAndComplementOfTAGToll = hasSalesAndComplementOfTAGToll;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getAmssName() {
		return amssName;
	}
	public void setAmssName(String amssName) {
		this.amssName = amssName;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getWorkingTime() {
		return workingTime;
	}
	public void setWorkingTime(String workingTime) {
		this.workingTime = workingTime;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public boolean isForInternationalDocuments() {
		return isForInternationalDocuments;
	}
	public void setForInternationalDocuments(boolean isForInternationalDocuments) {
		this.isForInternationalDocuments = isForInternationalDocuments;
	}
	public boolean isForTechnicalReview() {
		return isForTechnicalReview;
	}
	public void setForTechnicalReview(boolean isForTechnicalReview) {
		this.isForTechnicalReview = isForTechnicalReview;
	}
	public boolean isForAmssMembership() {
		return isForAmssMembership;
	}
	public void setForAmssMembership(boolean isForAmssMembership) {
		this.isForAmssMembership = isForAmssMembership;
	}
	public boolean isHasDrivingSchool() {
		return hasDrivingSchool;
	}
	public void setHasDrivingSchool(boolean hasDrivingSchool) {
		this.hasDrivingSchool = hasDrivingSchool;
	}
	public boolean isHasCarWash() {
		return hasCarWash;
	}
	public void setHasCarWash(boolean hasCarWash) {
		this.hasCarWash = hasCarWash;
	}
	public boolean isHasCarService() {
		return hasCarService;
	}
	public void setHasCarService(boolean hasCarService) {
		this.hasCarService = hasCarService;
	}
	public boolean isHasRestaurant() {
		return hasRestaurant;
	}
	public void setHasRestaurant(boolean hasRestaurant) {
		this.hasRestaurant = hasRestaurant;
	}
	public boolean isForRegistration() {
		return isForRegistration;
	}
	public void setForRegistration(boolean isForRegistration) {
		this.isForRegistration = isForRegistration;
	}
	public boolean isForVenchileInspectionAttest() {
		return isForVehicleInspectionAttest;
	}
	public void setForVenchileInspectionAttest(boolean isForVenchileInspectionAttest) {
		this.isForVehicleInspectionAttest = isForVenchileInspectionAttest;
	}
	public boolean isForHelpOnRoad() {
		return isForHelpOnRoad;
	}
	public void setForHelpOnRoad(boolean isForHelpOnRoad) {
		this.isForHelpOnRoad = isForHelpOnRoad;
	}
	public boolean isForSpedition() {
		return isForSpedition;
	}
	public void setForSpedition(boolean isForSpedition) {
		this.isForSpedition = isForSpedition;
	}
	public boolean isForTourism() {
		return isForTourism;
	}
	public void setForTourism(boolean isForTourism) {
		this.isForTourism = isForTourism;
	}
	public boolean isHasCurrencyExchange() {
		return hasCurrencyExchange;
	}
	public void setHasCurrencyExchange(boolean hasCurrencyExchange) { this.hasCurrencyExchange = hasCurrencyExchange;}
	public boolean isForInsurance() {
		return isForInsurance;
	}
	public void setForInsurance(boolean isForInsurance) {
		this.isForInsurance = isForInsurance;
	}
	public boolean isForTowing() {
		return isForTowing;
	}
	public void setForTowing(boolean isForTowing) {
		this.isForTowing = isForTowing;
	}
	public boolean isHasTNGPump() {
		return hasTNGPump;
	}
	public void setHasTNGPump(boolean hasTNGPump) {
		this.hasTNGPump = hasTNGPump;
	}
	public boolean isHasShop() {
		return hasShop;
	}
	public void setHasShop(boolean hasShop) {
		this.hasShop = hasShop;
	}
	public boolean isHasSalesAndComplementOfTAGToll() {
		return hasSalesAndComplementOfTAGToll;
	}
	public void setHasSalesAndComplementOfTAGToll(boolean hasSalesAndComplementOfTAGToll) { this.hasSalesAndComplementOfTAGToll = hasSalesAndComplementOfTAGToll; }
}