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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTypeOfMember() {
        return typeOfMember;
    }

    public void setTypeOfMember(String typeOfMember) {
        this.typeOfMember = typeOfMember;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
