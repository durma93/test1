package rs.org.amss.core;

import android.util.Log;

import net.lmggroup.utility.ConnectionHelper;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import rs.org.amss.model.CalculateRegistrationRequest;
import rs.org.amss.model.Member;


public class WebMethods {

	public static final String TAG = "WebMethods";

	private static  HttpClient getHttpClient() {
        try {
            return new AmssHttpClient();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return getHttpClient();
        }
	}

	public static String getParkingCities() throws ClientProtocolException, IOException  {
		HttpClient client = getHttpClient();
		HttpPost post = new HttpPost(StaticStrings.noviServis);
		String body = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:mob=\"https://www.ams.co.rs/mobileServices/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <mob:ParkingCitiesRequest>\n" +
                "         <parking>?</parking>\n" +
                "      </mob:ParkingCitiesRequest>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
		post.setEntity(new StringEntity(body));
		post.setHeader("content-type", "application/xml");

		Log.d("SOAP", "POST " + StaticStrings.noviServis + "\n" + body);

		HttpResponse response = client.execute(post);

		String result = EntityUtils.toString(response.getEntity());
		Log.d("SOAP", result);
		return result;
	}

	public static String getParkingZones(String city) throws ClientProtocolException, IOException  {
		HttpClient client = getHttpClient();
		HttpPost post = new HttpPost(StaticStrings.noviServis);
		String body = "" +
				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <grad>"+city+"</grad>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
		post.setEntity(new StringEntity(body, "UTF-8"));
		post.setHeader("content-type", "application/xml");

		Log.d("SOAP", "POST " + StaticStrings.noviServis + "\n" + body);

		HttpResponse response = client.execute(post);

		String result = EntityUtils.toString(response.getEntity());
		Log.d("SOAP", result);
		return result;
	}

	public static String getSerials(String membershipCardId)
			throws ClientProtocolException, IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalGetSerialsMethod);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}
	public static String getSerials()
			throws ClientProtocolException, IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalGetSerialsMethod);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}

	public static String getLoginStep1(String memebershipCardId)
			throws ClientProtocolException, IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalGetLoginStep1Method);
		query.append("?");
		query.append(StaticStrings.contentMembershipCardId);
		query.append("=");
		query.append(memebershipCardId);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}

	public static String getLoginStep2(String memebershipCardId)
			throws ClientProtocolException, IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalGetLoginStep2Method);
		query.append("?");
		query.append(StaticStrings.contentMembershipCardId);
		query.append("=");
		query.append(memebershipCardId);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}

	public static String getMembershipId(String memebershipCardId)
			throws ClientProtocolException, IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalGetMembershipId);
		query.append("?");
		query.append(StaticStrings.contentMembershipCardId);
		query.append("=");
		query.append(memebershipCardId);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}

	public static String getMemberInfo(int membershipId) throws ClientProtocolException, IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalGetMemberInfoMethod);
		query.append("?membershipID=");
		query.append(membershipId);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}
	
	public static String getMembershipInfo(int membershipId) throws ClientProtocolException, IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalMembershipInfoGet);
		query.append("?membershipID=");
		query.append(membershipId);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}
	
	public static String getServicesInfo(String membershipSeries, String membershipNumber) throws ClientProtocolException, IOException {
		HttpClient client = getHttpClient();
		HttpPost post = new HttpPost(StaticStrings.phpApiUserGetInfo);
		String body = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
			          "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">" +
			          "<soap12:Body>" +
			              "<getServices xmlns=\"http://tempuri.org/\">" +
			                   "<clanska_serija>" + membershipSeries + "</clanska_serija>" +
			                   "<clanski_broj>" + membershipNumber + "</clanski_broj>" +
			              "</getServices>" +
			          "</soap12:Body>" +
			          "</soap12:Envelope>";
		post.setEntity(new StringEntity(body));
		post.setHeader("content-type", "application/xml");
		
		Log.d("SOAP", "POST " + StaticStrings.phpApiUserGetInfo + "\n" + body);
		
		HttpResponse response = client.execute(post);
		
		String result = EntityUtils.toString(response.getEntity());
		Log.d("SOAP", result);
		return result;
	}

    public static String getCamera() throws ClientProtocolException, IOException {
        HttpClient client = getHttpClient();
        HttpPost post = new HttpPost(StaticStrings.noviServis);
        String body = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:mob=\"https://www.ams.co.rs/mobileServices/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <mob:CamerasRequest>\n" +
                "         <cameras>?</cameras>\n" +
                "      </mob:CamerasRequest>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        post.setEntity(new StringEntity(body));
        post.setHeader("content-type", "application/xml");

        Log.d("SOAP", "POST " + StaticStrings.noviServis + "\n" + body);

        HttpResponse response = client.execute(post);

        String result = EntityUtils.toString(response.getEntity());
        Log.d("SOAP", result);
        return result;
    }
	
	public static String getHistory(String membershipSeries, String membershipNumber) throws ClientProtocolException, IOException {
		HttpClient client = getHttpClient();
		HttpPost post = new HttpPost(StaticStrings.phpApiUserGetInfo);
		String body = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
			          "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">" +
			          "<soap12:Body>" +
			              "<getHistory xmlns=\"http://tempuri.org/\">" +
			                   "<clanska_serija>" + membershipSeries + "</clanska_serija>" +
			                   "<clanski_broj>" + membershipNumber + "</clanski_broj>" +
			              "</getHistory>" +
			          "</soap12:Body>" +
			          "</soap12:Envelope>";
		post.setEntity(new StringEntity(body));
		post.setHeader("content-type", "application/xml");
		
		Log.d("SOAP", "POST " + StaticStrings.phpApiUserGetInfo + "\n" + body);
		
		HttpResponse response = client.execute(post);
		
		String result = EntityUtils.toString(response.getEntity());
		Log.d("SOAP", result);
		return result;
	}	
	public static String getAllSubstationsForMap() 
			throws ClientProtocolException,	IOException {
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + StaticStrings.globalGetAllSubstationsForMap);
	}

	public static String getAllAmsoSubstationsForMap() 
			throws ClientProtocolException,	IOException {
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + StaticStrings.globalGetAllAmsoSubstationsForMap);
	}

	public static String getAllRegions() 
			throws ClientProtocolException,	IOException {
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + StaticStrings.globalGetAllRegions);
	}

	public static String getRegionMunicipalities(String regionName) 
			throws ClientProtocolException,	IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalGetRegionMunicipalities);
		query.append("?");
		query.append("regionName");
		query.append("=");
		query.append(regionName);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString().replaceAll(" ", "%20"));
	}

	public static String getKaskoCarBrands() 
			throws ClientProtocolException,	IOException {
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + StaticStrings.globalGetKaskoCarBrandsMethod);
	}

	public static String getKaskoCarTypes(String carBrand)
			throws ClientProtocolException, IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalGetKaskoCarTypesMethod);
		query.append("?");
		query.append("carBrand");
		query.append("=");
		query.append(carBrand);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}

	public static String sendKaskoEmailForOffer(String vehicleType, String carBrand, String carType, String carModel,
			String engineVolume, String enginePower, String numberOfDoors, String yearOfProduction, String carBodyType,
			String onLeasing, String onRent, String territorialCoverage, String coverRiskOfThief, String paymentDynamic,
			String fullName, String email, String phone, String purpose)
					throws ClientProtocolException, IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalSendKaskoEmailForOffer);
		query.append("?");
		query.append("vehicleType");
		query.append("=");
		query.append(vehicleType);
		query.append("&");
		query.append("carBrand");
		query.append("=");
		query.append(carBrand);
		query.append("&");
		query.append("carType");
		query.append("=");
		query.append(carType);
		query.append("&");
		query.append("carModel");
		query.append("=");
		query.append(carModel);
		query.append("&");
		query.append("engineVolume");
		query.append("=");
		query.append(engineVolume);
		query.append("&");
		query.append("enginePower");
		query.append("=");
		query.append(enginePower);
		query.append("&");
		query.append("numberOfDoors");
		query.append("=");
		query.append(numberOfDoors);
		query.append("&");
		query.append("yearOfProduction");
		query.append("=");
		query.append(yearOfProduction);
		query.append("&");
		query.append("carBodyType");
		query.append("=");
		query.append(carBodyType);
		query.append("&");
		query.append("OnLeasing");
		query.append("=");
		query.append(onLeasing);
		query.append("&");
		query.append("OnRent");
		query.append("=");
		query.append(onRent);
		query.append("&");
		query.append("territorialCoverage");
		query.append("=");
		query.append(territorialCoverage);
		query.append("&");
		query.append("coverRiskOfTheft");
		query.append("=");
		query.append(coverRiskOfThief);
		query.append("&");
		query.append("paymentDynamic");
		query.append("=");
		query.append(paymentDynamic);
		query.append("&");
		query.append("fullName");
		query.append("=");
		query.append(fullName);
		query.append("&");
		query.append("email");
		query.append("=");
		query.append(email);
		query.append("&");
		query.append("phone");
		query.append("=");
		query.append(phone);
		query.append("&");
		query.append("purpose");
		query.append("=");
		query.append(purpose);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString().replaceAll(" ", "%20"));
	}

	public static String getAllSubstationsByAttributesForMap(Map<String,Boolean> parameters) 
			throws ClientProtocolException,	IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalGetAllSubstationsByAttributesForMap);
		query.append("?");

		Iterator<Entry<String, Boolean>> iterator = parameters.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Boolean> pairs = (Entry<String, Boolean>)iterator.next();
			String key = pairs.getKey();
			Boolean value = pairs.getValue();
			query.append(key);
			query.append("=");
			query.append(value);

			Log.d(TAG, key + " = " + value);
		}

		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}

	public static String getAtributesOfStationOffice(boolean isForInternationalDocuments,
			boolean isForTechnicalReview,
			boolean isForAMSSMembership,
			boolean hasDrivingSchool,
			boolean hasCarWash,
			boolean hasCarService,
			boolean hasRestaurant,
			boolean isForRegistration,
			boolean isForVehicleInspectionAttest,
			boolean isForHelpOnRoad,
			boolean isForSpecition,
			boolean isForTourism,
			boolean hasCurrencyExchange,
			boolean isForInsurance,
			boolean isForTowing,
			boolean hasTNGPump,
			boolean hasShop,
			boolean hasSalesAndcomplementOfTAGToll
			)
					throws ClientProtocolException, IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalGetAllSubstationsByAttributesForMap);
		query.append("?");
		query.append("isForInternationalDocuments");
		query.append("=");
		query.append(isForInternationalDocuments);
		query.append("&");
		query.append("isForTechnicalReview");
		query.append("=");
		query.append(isForTechnicalReview);
		query.append("&");
		query.append("isForAMSSMembership");
		query.append("=");
		query.append(isForAMSSMembership);
		query.append("&");
		query.append("hasDrivingSchool");
		query.append("=");
		query.append(hasDrivingSchool);
		query.append("&");
		query.append("hasCarWash");
		query.append("=");
		query.append(hasCarWash);
		query.append("&");
		query.append("hasCarService");
		query.append("=");
		query.append(hasCarService);
		query.append("&");
		query.append("hasRestaurant");
		query.append("=");
		query.append(hasRestaurant);
		query.append("&");
		query.append("isForRegistration");
		query.append("=");
		query.append(isForRegistration);
		query.append("&");
		query.append("isForVehicleInspectionAttest");
		query.append("=");
		query.append(isForVehicleInspectionAttest);
		query.append("&");
		query.append("isForHelpOnRoad");
		query.append("=");
		query.append(isForHelpOnRoad);
		query.append("&");
		query.append("isForSpecition");
		query.append("=");
		query.append(isForSpecition);
		query.append("&");
		query.append("isForTourism");
		query.append("=");
		query.append(isForTourism);
		query.append("&");
		query.append("hasCurrencyExchange");
		query.append("=");
		query.append(hasCurrencyExchange);
		query.append("&");
		query.append("isForInsurance");
		query.append("=");
		query.append(isForInsurance);
		query.append("&");
		query.append("isForTowing");
		query.append("=");
		query.append(isForTowing);
		query.append("&");
		query.append("hasTNGPump");
		query.append("=");
		query.append(hasTNGPump);
		query.append("&");
		query.append("hasShop");
		query.append("=");
		query.append(hasShop);
		query.append("&");
		query.append("hasSalesAndcomplementOfTAGToll");
		query.append("=");
		query.append(hasSalesAndcomplementOfTAGToll);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}

	public static String getRandomNamesList(String firstName, String lastName)
			throws ClientProtocolException, IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalGetLoginRandomNamesMethod);
		query.append("?");
		query.append(StaticStrings.contentGetRandomLoginFirstName);
		query.append("=");
		query.append(firstName);
		query.append("&");
		query.append(StaticStrings.contentGetRandomLoginLastName);
		query.append("=");
		query.append(lastName);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}


	public static String getDamageTypeList()
			throws ClientProtocolException, IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalGetDamageTypesMethod);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}

	public static String getRoadConditions()
			throws ClientProtocolException, IOException {
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + StaticStrings.globalGetRoadConditionsMethod);
	}

	public static String getBordersConditions()
			throws ClientProtocolException, IOException {
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + StaticStrings.globalConditionOnBordersGetMethod);
	}

	public static String getRoadConditionsVideo()
			throws ClientProtocolException, IOException {
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + StaticStrings.globalGetRoadConditionsVideoMethod);
	}

	public static String getTollCities()
			throws ClientProtocolException, IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalGetTollCitiesMethod);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}
	public static String calculateTravelInsurance(String typeOfTariff, String age, String durationOfInsurance, String periodOfCoverage,
			String hazardClass, String involvement, String travelPurpose)
					throws ClientProtocolException, IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalCalculateTravelInsurance);
		query.append("?");
		query.append("typeOfTariff");
		query.append("=");
		query.append(typeOfTariff);
		query.append("&");
		query.append("age");
		query.append("=");
		query.append(age);
		query.append("&");
		query.append("durationOfInsurance");
		query.append("=");
		query.append(durationOfInsurance);
		query.append("&");
		query.append("periodOfCoverage");
		query.append("=");
		query.append(periodOfCoverage);
		query.append("&");
		query.append("hazardClass");
		query.append("=");
		query.append(hazardClass);		
		query.append("&");
		query.append("involvement");
		query.append("=");
		query.append(involvement);	
		query.append("&");
		query.append("travelPurpose");
		query.append("=");
		query.append(travelPurpose);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}

	public static String calculateTollPrice(int cityFromId, int cityFromGroup, int cityToId, int cityToGroup)
			throws ClientProtocolException, IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalCalculateTollPriceMethod);
		query.append("?");
		query.append("cityFromID");
		query.append("=");
		query.append(cityFromId);
		query.append("&");
		query.append("cityFromGroup");
		query.append("=");
		query.append(cityFromGroup);
		query.append("&");
		query.append("cityToID");
		query.append("=");
		query.append(cityToId);
		query.append("&");
		query.append("cityToGroup");
		query.append("=");
		query.append(cityToGroup);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}

	public static String monasteriesGet()
			throws ClientProtocolException, IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalMonasteriesGetMethod);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}

	public static String monasteriesDistanceGet(int monasteryID)
			throws ClientProtocolException, IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalMonasteriesDistanceGetMethod);
		query.append("?");
		query.append("monasteryID");
		query.append("=");
		query.append(monasteryID);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}

	public static String getBenefits()
			throws ClientProtocolException, IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalGetBenefitsMethod);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}

	public static String getMunicipalities()
			throws ClientProtocolException, IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalGetMunicipalitiesMethod);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}

	public static String getVehicleTypes()
			throws ClientProtocolException, IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalGetVehicleTypesMethod);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}

	public static String getCities()
			throws ClientProtocolException, IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalCitiesGetMethod);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}

	public static String getNews()
			throws ClientProtocolException, IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalNewsGetMethod);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}

	public static String getDestinationList(int id)
			throws ClientProtocolException, IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalDestinationListGetMethod);
		query.append("?");
		query.append("startCityID");
		query.append("=");
		query.append(id);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}

	public static String getDistance(int id)
			throws ClientProtocolException, IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalDistanceGetMethod);
		query.append("?");
		query.append("destinationID");
		query.append("=");
		query.append(id);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}
	public static String getBorderCountries()
			throws ClientProtocolException, IOException {
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + StaticStrings.globalBorderCountriesGetMethod);
	}

	public static String getRoadConditionsByCountry(String country)
			throws ClientProtocolException, IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalRoadConditionsOnBordersByCountryGetMethod);
		query.append("?");
		query.append("countryName");
		query.append("=");
		query.append(country);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}

	public static String reportDamage(String damageID, String comment, String latitude, String longitude, String imageURL)
			throws ClientProtocolException, IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalDamageAddMethod);
		query.append("?");
		query.append("typeOfDamage");
		query.append("=");
		query.append(damageID);
		query.append("&");
		query.append("comment");
		query.append("=");
		query.append(comment);
		query.append("&");
		query.append("latitude");
		query.append("=");
		query.append(latitude);
		query.append("&");
		query.append("longitude");
		query.append("=");
		query.append(longitude);
		query.append("&");
		query.append("imageUrl");
		query.append("=");
		query.append(imageURL);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}

	public static String calculateRegistration(CalculateRegistrationRequest request)
			throws ClientProtocolException, IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalCalculateRegistrationMethod);
		query.append("?");
		query.append("municipalID");
		query.append("=");
		query.append(request.municipalId);
		query.append("&");
		query.append("enginePower");
		query.append("=");
		query.append(request.enginePower);
		query.append("&");
		query.append("enginevolume");
		query.append("=");
		query.append(request.engineVolume);
		query.append("&");
		query.append("productionYear");
		query.append("=");
		query.append(request.productionYear);
		query.append("&");
		query.append("vehicleType");
		query.append("=");
		query.append(request.vehicleTypeId);
		query.append("&");
		query.append("premiumGroup");
		query.append("=");
		query.append(request.premiumGroup);
		query.append("&");
		query.append("levelOfPreviousPremiumPolicy");
		query.append("=");
		query.append(request.levelOfPreviousPremiumPolicy);
		query.append("&");
		query.append("numberOfDamages");
		query.append("=");
		query.append(request.numberOfDamages);
		query.append("&");
		query.append("newDrivingLicense");
		query.append("=");
		query.append(request.newDrivingLicense);
		query.append("&");
		query.append("newRegistrationPlates");
		query.append("=");
		query.append(request.newRegistrationPlates);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}
	public static String getPeriodsOfCoverage(String numberOfDays) 
			throws ClientProtocolException,	IOException {
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalGetPeriodsOfCoverage);
		query.append("?");
		query.append("numberOfDays");
		query.append("=");
		query.append(numberOfDays);
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}
	public static String addNewMember(Member member) throws ClientProtocolException, IOException{
		StringBuffer query = new StringBuffer();
		query.append(StaticStrings.globalAddNewMember);
		query.append("?");
		if(member.oldMembershipID!=null){
			query.append("oldMembershipID");
			query.append("=");
			query.append(URLEncoder.encode(member.oldMembershipID));
			query.append("&");
		}
		if(member.oldMembershipSerial!=null){
			query.append("oldMembershipSerial");
			query.append("=");
			query.append(URLEncoder.encode(member.oldMembershipSerial));
			query.append("&");
		}
		if(member.firstName!=null){
			query.append("firstName");
			query.append("=");
			query.append(URLEncoder.encode(member.firstName));
			query.append("&");
		}
		if(member.lastName!=null){
			query.append("lastName");
			query.append("=");
			query.append(URLEncoder.encode(member.lastName));
			query.append("&");
		}
		if(member.personalNumber!=null){
			query.append("personalNumber");
			query.append("=");
			query.append(member.personalNumber);
			query.append("&");
		}
		if(member.companyName!=null){
			query.append("companyName");
			query.append("=");
			query.append(URLEncoder.encode(member.companyName));
			query.append("&");
		}
		if(member.pib!=null){
			query.append("pib");
			query.append("=");
			query.append(member.pib);
			query.append("&");
		}
		if(member.address!=null){
			query.append("address");
			query.append("=");
			query.append(URLEncoder.encode(member.address));
			query.append("&");
		}
		if(member.postCode!=null){
			query.append("postCode");
			query.append("=");
			query.append(member.postCode);
			query.append("&");
		}
		if(member.city!=null){
			query.append("city");
			query.append("=");
			query.append(URLEncoder.encode(member.city));
			query.append("&");
		}
		if(member.telephone!=null){
			query.append("telephone");
			query.append("=");
			query.append(member.telephone);
			query.append("&");
		}
		if(member.telephone!=null){
			query.append("mobilePhone");
			query.append("=");
			query.append(member.mobilePhone);
			query.append("&");
		}
		if(member.email!=null){
			query.append("email");
			query.append("=");
			query.append(member.email);
			query.append("&");
		}
		if(member.vehicleBrand!=null){
			query.append("vehicleBrand");
			query.append("=");
			query.append(URLEncoder.encode(member.vehicleBrand));
			query.append("&");
		}
		if(member.vehicleType!=null){
			query.append("vehicleType");
			query.append("=");
			query.append(URLEncoder.encode(member.vehicleType));
			query.append("&");
		}
		if(member.chassisName!=null){
			query.append("chassisNumber");
			query.append("=");
			query.append(URLEncoder.encode(member.chassisName));
			query.append("&");
		}
		if(member.registrationPlate!=null){
			query.append("registrationPlate");
			query.append("=");
			query.append(URLEncoder.encode(member.registrationPlate));
			query.append("&");
		}
		if(member.vehicleColor!=null){
			query.append("vehicleColor");
			query.append("=");
			query.append(URLEncoder.encode(member.vehicleColor));
			query.append("&");
		}
		if(member.yearOfProduction!=null){
			query.append("yearOfProduction");
			query.append("=");
			query.append(URLEncoder.encode(member.yearOfProduction));
			query.append("&");
		}
		if(member.policyNumber!=null){
			query.append("policyNumber");
			query.append("=");
			query.append(URLEncoder.encode(member.policyNumber));
			query.append("&");
		}
		if(member.insuranceCompany!=null){
			query.append("insuranceCompany");
			query.append("=");
			query.append(URLEncoder.encode(member.insuranceCompany));
			query.append("&");
		}
		query.append("isSuperMembership");
		query.append("=");
		query.append(member.isSuperMembership);
		query.append("&");
		query.append("isIndividual");
		query.append("=");
		query.append(member.isIndividual);
		Log.v("Add member", StaticStrings.globalServiceAddress + URLEncoder.encode(query.toString()));
		return ConnectionHelper.callWebService(StaticStrings.globalServiceAddress + query.toString());
	}
}
