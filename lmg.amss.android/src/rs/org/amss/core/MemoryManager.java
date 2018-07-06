package rs.org.amss.core;

import java.util.Iterator;
import java.util.Map;

import rs.org.amss.model.Car;
import rs.org.amss.model.Fuel;
import rs.org.amss.model.Service;
import rs.org.amss.model.User;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

public class MemoryManager {

	public static final String TAG = "MemoryManager";
	private static final String FILE_NAME = "amss";
	private static final String NEW_CAR = "newCar";
	private static final String NEW_SERVICE = "service";
	private static final String NEW_FUEL = "fuel";

	private static final String VARIABLE_MEMBERSHIPID = "membershipId";
	private static final String VARIABLE_MEMBERSHIPCARDID = "membershipCardId";
	private static final String VARIABLE_REGISTRATIONPLATE = "registrationPlate";
	private static final String VARIABLE_MYCARLOCATION = "carLocation";
	private static final String VARIABLE_PARKING_CITY = "parkingCity";

	private static SharedPreferences settings;

	Activity activity;

	public MemoryManager(Activity activity) {
		this.activity = activity;
		settings = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
	}

	public void clearPref(Context context, String key) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = prefs.edit();
		editor.clear();
		editor.commit();
	}

	public void SaveNewCar(String object){
		SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
		Editor prefsEditor = appSharedPrefs.edit();
		prefsEditor.putString(NEW_CAR, object);
		prefsEditor.commit();
		Log.v(TAG, "New Car saved. "+object);
	}

	public void SaveCarService(String object){
		SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
		Editor prefsEditor = appSharedPrefs.edit();
		prefsEditor.putString(NEW_SERVICE, object);
		prefsEditor.commit();
		Log.v(TAG, "New Car Service saved. "+object);
	}
	
	public void SaveCarFuel(String object){
		SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
		Editor prefsEditor = appSharedPrefs.edit();
		prefsEditor.putString(NEW_FUEL, object);
		prefsEditor.commit();
		Log.v(TAG, "New Car Service saved. "+object);
	}

	public Car GetNewCar(){
		Car result = null;
		Gson gson = new Gson();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
		String res = prefs.getString(NEW_CAR, "");
		 result = gson.fromJson(res, Car.class);
		Log.v(TAG, "Get Car saved. "+res);
		return result;
	}
	
	public Service GetCarService(){
		Service result = null;
		Gson gson = new Gson();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
		String res = prefs.getString(NEW_SERVICE, "");
		result = gson.fromJson(res, Service.class);
		Log.v(TAG, "Get Car Service. "+res);
		return result;
	}
	
	public Fuel GetCarFuel(){
		Fuel result = null;
		Gson gson = new Gson();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
		String res = prefs.getString(NEW_FUEL, "");
		result = gson.fromJson(res, Fuel.class);
		Log.v(TAG, "Get Car Fuel. "+res);
		return result;
	}

	public void ClearPreferences() {
		SharedPreferences ClientPreferences = settings;
		SharedPreferences.Editor client = ClientPreferences.edit();
		client.clear();
		client.commit();
	}


	@SuppressWarnings("rawtypes")
	public void loadVariables(){
		Map<String,?> variables = settings.getAll();
		Iterator iterator = variables.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry pair = (Map.Entry)iterator.next();
			if (pair.getKey().toString().equals(VARIABLE_MEMBERSHIPID))
				saveMembershipId((Integer)pair.getValue());
			else if (pair.getKey().toString().equals(VARIABLE_MEMBERSHIPCARDID))
				saveMembershipCardId(pair.getValue().toString());
			else if (pair.getKey().toString().equals(VARIABLE_REGISTRATIONPLATE))
				saveRegistrationPlate(pair.getValue().toString());
			else if (pair.getKey().toString().equals(VARIABLE_MYCARLOCATION))
				saveMyCarLocation(pair.getValue().toString());

			iterator.remove();
		}
	}

	public void saveMembershipId(int membershipId) {
		saveVariable(VARIABLE_MEMBERSHIPID, membershipId);
		if (Variables.getUser() == null)
			Variables.setUser(new User(membershipId));
		else
			Variables.getUser().setMembershipId(membershipId);
	}

	public void saveMembershipCardId(String membershipCardId) {
		saveVariable(VARIABLE_MEMBERSHIPCARDID, membershipCardId);
		if (Variables.getUser() == null)
			Variables.setUser(new User(membershipCardId));
		else
			Variables.getUser().setMembershipCardId(membershipCardId);
	}

	public void saveRegistrationPlate(String registrationPlate) {
		saveVariable(VARIABLE_REGISTRATIONPLATE, registrationPlate);
		if (Variables.getUser() == null)
			Variables.setUser(new User());
		Variables.getUser().setRegistrationPlate(registrationPlate);
	}

	public void saveMyCarLocation(String location) {
		saveVariable(VARIABLE_MYCARLOCATION, location);
		if (location != null && location.trim().length() > 0){
			double latitude = 0d;
			double longitude = 0d;

			String[] locationParts = location.split(";");
			latitude = Double.parseDouble(locationParts[0]);
			longitude = Double.parseDouble(locationParts[1]);

			Variables.myCarLocation = new LatLng(latitude, longitude);
		}
		else
			Variables.myCarLocation = null;

	}

	public void saveVariable(String name, Object value) {
		Log.d(TAG, name + "=" + value);
		SharedPreferences.Editor settingsEditor = settings.edit();
		if (value instanceof String) {
			settingsEditor.putString(name, String.valueOf(value));
		} else if (value instanceof Integer) {
			settingsEditor.putInt(name, (Integer) value);
		} else if (value instanceof Long) {
			settingsEditor.putLong(name, (Long) value);
		} else if (value instanceof Boolean) {
			settingsEditor.putBoolean(name, (Boolean) value);
		}
		settingsEditor.commit();
	}

	public String getStringVariable(String name){
		return settings.getString(name, null);
	}

	public int getIntVariable(String name){
		return settings.getInt(name, -1);
	}

	public long getLongVariable(String name){
		return settings.getLong(name, -1);
	}

	public boolean getBooleanVariable(String name){
		return settings.getBoolean(name, false);
	}

	public void saveParkingCity(String parking_city_choosed) {
		saveVariable(VARIABLE_PARKING_CITY, parking_city_choosed);
		Variables.parking_city_choosed = parking_city_choosed;
	}
}
