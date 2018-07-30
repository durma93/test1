package rs.org.amss.core;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.google.android.gms.maps.model.LatLng;

import rs.org.amss.model.AmsoStation;
import rs.org.amss.model.AmssStation;
import rs.org.amss.model.Benefit;
import rs.org.amss.model.CalculateRegistrationRequest;
import rs.org.amss.model.Camera;
import rs.org.amss.model.Car;
import rs.org.amss.model.DamageType;
import rs.org.amss.model.Fuel;
import rs.org.amss.model.Member;
import rs.org.amss.model.MemberNew;
import rs.org.amss.model.Membership;
import rs.org.amss.model.Municipality;
import rs.org.amss.model.News;
import rs.org.amss.model.RoadCondition;
import rs.org.amss.model.Service;
import rs.org.amss.model.TollCity;
import rs.org.amss.model.User;
import rs.org.amss.model.VehicleType;

public class Variables {

	public static ArrayList<TollCity> destinationCities = new ArrayList<TollCity>();
	public static double latitude = 1.0;
	public static double longitude = 0.1;
	private static User user;
	public static ArrayList<AmssStation> stationList;
	public static ArrayList<AmsoStation> insuranceStationList;
	public static ArrayList<RoadCondition> roadConditionList;
	public static ArrayList<TollCity> tollCities;
	public static ArrayList<Benefit> benefits = new ArrayList<Benefit>();
	public static ArrayList<Municipality> municipalities;
	public static ArrayList<VehicleType> vehicleTypes;
	public static ArrayList<String> regions;
	public static ArrayList<Membership> membershipInfo;
	public static String path;
	public static String registrationPlate;
	public static String parking_city_choosed;
	public static String camera;
	public static LatLng myCarLocation;
	public static String damageID;
	public static ArrayList<DamageType> damageList = new ArrayList<DamageType>();
	public static ArrayList<TollCity> destinationToCities = new ArrayList<TollCity>();
	public static ArrayList<TollCity> monasteryList = new ArrayList<TollCity>();
	public static ArrayList<Car> cars = new ArrayList<Car>();
	public static Fuel fuel = new Fuel();
	public static Service servis = new Service();
	public static ArrayList<News> news = new ArrayList<News>();
	public static News singleNews = new News();
	public static ArrayList<RoadCondition> borders = new ArrayList<RoadCondition>();
	public static RoadCondition singleBorder = new RoadCondition();
	public static String imageName;
	private static ArrayList<Camera> cameras;
	private static MemberNew member;

	public static String memberName;
	public static String vehicleColor;
	public static String vehicleVendor;


	public static String sendSmsVehicleVendor = "";
	public static String sendSmsVehicleColor = "";
	public static String sendSmsIntervention = "";
	public static String sendSmsRegistration = "";
	
	public static double getLatitude() {
		return latitude;
	}

	public static void setLatitude(double latitude) {
		Variables.latitude = latitude;
	}

	public static double getLongitude() {
		return longitude;
	}

	public static void setLongitude(double longitude) {
		Variables.longitude = longitude;
	}

	public static User getUser() {
		return user;
	}
	
	public static void setUser(User user) {
		Variables.user = user;
	}
	
	public static MemberNew getMember(){
		return member;
	}
	public static void setMember(MemberNew member) { Variables.member = member; }

	public static ArrayList<Membership> getMembershipInfo() { return membershipInfo; }
	public static void setMembershipInfo(ArrayList<Membership> membershipInfo) { Variables.membershipInfo = membershipInfo; }

	public static CalculateRegistrationRequest registrationInfo = new CalculateRegistrationRequest();
	public static ArrayList<AmssStation> mvdOffices = new ArrayList<AmssStation>();
	public static ArrayList<String> borderCountries = new ArrayList<String>();
	public static Integer international_documents_position = 0;
	public static Car car = new Car();

	public static TollCity getTollCity(int id) {
		TollCity result = null;
		for (TollCity city : tollCities) {
			if (city.id == id) {
				result = city;
				break;
			}
		}
		return result;
	}
	
	public static ArrayList<Camera> getCameras() {
		if (cameras == null) {
			cameras = new ArrayList<Camera>();
			
			cameras.add(new Camera("Autokomanda".toUpperCase(), "http://109.206.96.249:8080/cam_3.jpg"));
			cameras.add(new Camera("Bogoslovija".toUpperCase(), "http://109.206.96.249:8080/cam_5.jpg"));
			cameras.add(new Camera("Kružni tok Novi Beograd".toUpperCase(), "http://109.206.96.98:8080/cam_21.jpg"));
			cameras.add(new Camera("Trg Nikola Pašića".toUpperCase(), "http://109.206.96.98:8080/cam_20.jpg"));
			cameras.add(new Camera("Vukov spomenik".toUpperCase(), "http://109.206.96.249:8080/cam_6.jpg"));
			cameras.add(new Camera("Železnička stanica".toUpperCase(), "http://109.206.96.98:8080/cam_19.jpg"));
			cameras.add(new Camera("Autoput Novi Beograd".toUpperCase(), "http://109.206.96.230:8080/cam_10.jpg"));
			cameras.add(new Camera("Beogradski sajam - Mostarska petlja".toUpperCase(), "http://109.206.96.96:8080/cam_11.jpg"));
			cameras.add(new Camera("Brankov most - Terazijski tunel".toUpperCase(), "http://109.206.96.247:8080/cam_1.jpg"));
			cameras.add(new Camera("Bulevar Despota Stefana".toUpperCase(), "http://109.206.96.96:8080/cam_17.jpg"));
			cameras.add(new Camera("Bulevar Mihaila Pupina - Milentija Popovića".toUpperCase(), "http://109.206.96.230:8080/cam_9.jpg"));
			cameras.add(new Camera("Bulevar Milutina Milankovića - Most na Adi".toUpperCase(), "http://109.206.96.247:8080/cam_4.jpg"));
			cameras.add(new Camera("Jurija Gagarina".toUpperCase(), "http://109.206.96.96:8080/cam_12.jpg"));
			cameras.add(new Camera("Most Gazela".toUpperCase(), "http://109.206.96.247:8080/cam_2.jpg"));
			cameras.add(new Camera("Pančevački most".toUpperCase(), "http://109.206.96.247:8080/cam_7.jpg"));
			cameras.add(new Camera("Slavija".toUpperCase(), "http://109.206.96.230:8080/cam_8.jpg"));
			cameras.add(new Camera("Takovska".toUpperCase(), "http://109.206.96.96:8080/cam_13.jpg"));
			cameras.add(new Camera("Trg Republike".toUpperCase(), "http://109.206.96.96:8080/cam_18.jpg"));
		}

		Collections.sort(cameras, new Comparator<Camera>() {
			@Override
			public int compare(Camera a, Camera b) {
				return a.title.compareToIgnoreCase(b.title);
			}
		});

		return cameras;
	}

	public static void loadChosenParkingCity(Context context) {
		SharedPreferences preferences = context.getSharedPreferences("choseParkingCity", Context.MODE_PRIVATE);
		parking_city_choosed =  preferences.getString("city", null);
	}

	public static void storeChosenParkingCity(Context context) {
		SharedPreferences.Editor preferences = context.getSharedPreferences("choseParkingCity", Context.MODE_PRIVATE).edit();
		preferences.putString("choseParkingCity", parking_city_choosed);
		preferences.apply();
	}


    public static void loadCameras(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("camera", Context.MODE_PRIVATE);
        camera =  preferences.getString("camera", null);
    }

    public static void StoreCameras(Context context) {
        SharedPreferences.Editor preferences = context.getSharedPreferences("camera", Context.MODE_PRIVATE).edit();
        preferences.putString("camera", camera);
        preferences.apply();
    }

	public static void loadMemberBasicInfo(Context context) {
		SharedPreferences preferences = context.getSharedPreferences("memberBasicInfo", Context.MODE_PRIVATE);
		memberName =  preferences.getString("memberName", null);
		vehicleColor =  preferences.getString("vehicleColor", null);
		vehicleVendor =  preferences.getString("vehicleVendor", null);
	}

	public static void storeMemberBasicInfo(Context context) {
		SharedPreferences.Editor preferences = context.getSharedPreferences("memberBasicInfo", Context.MODE_PRIVATE).edit();
		preferences.putString("memberName", memberName);
		preferences.putString("vehicleColor", vehicleColor);
		preferences.putString("vehicleVendor", vehicleVendor);
		preferences.apply();
	}
}
