package rs.org.amss.model;


public class LoginStep1 {
	public String fullName;
	public String companyName;
	public String typeOfMember;
	public boolean isValid;
	
	public String getName(){
		if (typeOfMember.equals(TypeOfMember.INDIVIDUAL))
			return fullName;
		else // "legal entity"
			return companyName;
	}
	
}
