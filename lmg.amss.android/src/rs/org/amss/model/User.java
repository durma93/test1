package rs.org.amss.model;

public class User {
	
	private int membershipId;	
	private String membershipCardId;		
	private String registrationPlate;	

	public User() {};
	
	public User(int membershipId){
		setMembershipId(membershipId);
	}
	
	public User(String membershipCardId){
		setMembershipCardId(membershipCardId);
	}

	public User(String membershipCardId, String registrationPlate){
		setMembershipCardId(membershipCardId);
		setRegistrationPlate(registrationPlate);
	}

	public User(int membershipId, String membershipCardId, String registrationPlate){
		setMembershipId(membershipId);
		setMembershipCardId(membershipCardId);
		setRegistrationPlate(registrationPlate);
	}
	
	public void setMembershipId(int membershipId){
		this.membershipId = membershipId;
	}
	
	public void setMembershipCardId(String membershipCardId){
		this.membershipCardId = membershipCardId;
	}

	public void setRegistrationPlate(String registrationPlate){
		this.registrationPlate = registrationPlate;
	}

	public int getMembershipId(){
		return this.membershipId;
	}
	
	public String getMembershipCardId(){
		return this.membershipCardId;
	}
	
	public String getMembershipCardSeries() {
		return this.membershipCardId.split("_")[1];
	}
	
	public String getMembershipCardNumber() {
		String id = membershipCardId.split("_")[0];
		if (id.length() > 6)
			id = id.substring(id.length()-6, id.length());

		return id;
	}

	public String getRegistrationPlate(){
		return this.registrationPlate;
	}
}
