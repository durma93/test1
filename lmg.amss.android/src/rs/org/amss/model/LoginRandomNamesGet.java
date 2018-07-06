package rs.org.amss.model;

import java.util.ArrayList;

public class LoginRandomNamesGet{
	public ArrayList<LoginRandomNamesGet> randomNameList;
	public String firstName;
	public String lastName;

	public LoginRandomNamesGet(){
		this.randomNameList = new ArrayList<LoginRandomNamesGet>();
	}
	public LoginRandomNamesGet(String firstName, String lastName){
		this.firstName = firstName;
		this.lastName = lastName;
		this.randomNameList = new ArrayList<LoginRandomNamesGet>();
	}


}

