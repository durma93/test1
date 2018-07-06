package rs.org.amss.model;

import java.io.Serializable;

public class AmsoStationFilter implements Serializable {
	private static final long serialVersionUID = 1748443848934567516L;
	public int regionId;
	public int municipalityId;
	public int townId;
	public String regionName;
	public String municipalityName;
	public String townName;
	public String automobileLiability = "";
	public String greenCard = "";
	public String carAccident = "";
	public String kasko = "";
	public String kaskoLight = "";
	public String healthInsurance = "";
	public String membership = "";
	public String internationalDocuments = "";
	public boolean hasAutomobileLiability;
	public boolean hasGreenCard;
	public boolean hasCarAccident;
	public boolean hasKasko;
	public boolean hasKaskoLight;
	public boolean hasVoluntaryHealthInsurance;
	public boolean hasMembership;
	public boolean hasInternationalDocuments;
	public boolean hasAdditionalServices;
}
