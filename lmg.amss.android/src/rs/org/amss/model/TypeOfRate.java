package rs.org.amss.model;

import java.util.ArrayList;

import android.content.Context;

import rs.org.amss.R;
public class TypeOfRate {
	public String id;
	public String type;

	public static ArrayList<TypeOfRate> getTypeOfRate(Context context){

		String[] ids = {"01", "02", "03"};
		int[] types = {R.string.typeOfRate_types_01, R.string.typeOfRate_types_02, R.string.typeOfRate_types_03};
		ArrayList<TypeOfRate> result = new ArrayList<TypeOfRate>();
		for(int i=0; i<ids.length; i++){
			TypeOfRate type = new TypeOfRate();
			type.id = ids[i];
			type.type = context.getResources().getString(types[i]);
			result.add(type);
		}
		return result;
	}
	public static ArrayList<TypeOfRate> getAgesList(){

		String[] ids = {"01", "02", "03"};
		String[] types = {"0-17 godina", "18-70 godina", "preko 70 godina"};
		ArrayList<TypeOfRate> result = new ArrayList<TypeOfRate>();
		for(int i=0; i<ids.length; i++){
			TypeOfRate type = new TypeOfRate();
			type.id = ids[i];
			type.type = types[i];
			result.add(type);
		}
		return result;
	}

	public static ArrayList<TypeOfRate> getCoveragePeriodList(){
		ArrayList<TypeOfRate> result = new ArrayList<TypeOfRate>();
		for(int i=0; i<90; i++){
			TypeOfRate type = new TypeOfRate();
			type.id = "00"+i;
			if(type.id.length()==4)
				type.id = type.id.substring(1);
			type.type = ""+i;
			result.add(type);
		}
		return result;
	}

	public static ArrayList<TypeOfRate> getLevelOfDangerous(Context context){

		String[] ids = {"01", "02", "03"};
		int[] types = {R.string.typeOfRate_levelOfDangerous_01, R.string.typeOfRate_levelOfDangerous_02, R.string.typeOfRate_levelOfDangerous_03};
		ArrayList<TypeOfRate> result = new ArrayList<TypeOfRate>();
		for(int i=0; i<ids.length; i++){
			TypeOfRate type = new TypeOfRate();
			type.id = ids[i];
			type.type = context.getResources().getString(types[i]);
			result.add(type);
		}
		return result;
	}

	public static ArrayList<TypeOfRate> getParticipationList(Context context){

		String[] ids = {"01", "02"};
		int[] types = {R.string.typeOfRate_participation_01, R.string.typeOfRate_participation_02};
		ArrayList<TypeOfRate> result = new ArrayList<TypeOfRate>();
		for(int i=0; i<ids.length; i++){
			TypeOfRate type = new TypeOfRate();
			type.id = ids[i];
			type.type = context.getResources().getString(types[i]);
			result.add(type);
		}
		return result;
	}
	public static ArrayList<TypeOfRate> getCoverPeriodDays(){

		String[] ids = {"030", "060", "090", "180", "365"};
		String[] types = {"30", "60", "90", "180", "365"};
		ArrayList<TypeOfRate> result = new ArrayList<TypeOfRate>();
		for(int i=0; i<ids.length; i++){
			TypeOfRate type = new TypeOfRate();
			type.id = ids[i];
			type.type = types[i];
			result.add(type);
		}
		return result;
	}
	public static ArrayList<TypeOfRate> getTravelPurposeList(Context context){

		String[] ids = {"0", "108972", "108983", "108984", "108985", "108986"};
		int[] types = {R.string.typeOfRate_travel_purpose_01, R.string.typeOfRate_travel_purpose_02, R.string.typeOfRate_travel_purpose_03,
				R.string.typeOfRate_travel_purpose_04, R.string.typeOfRate_travel_purpose_05, R.string.typeOfRate_travel_purpose_06};
		ArrayList<TypeOfRate> result = new ArrayList<TypeOfRate>();
		for(int i=0; i<ids.length; i++){
			TypeOfRate type = new TypeOfRate();
			type.id = ids[i];
			type.type = context.getResources().getString(types[i]);
			result.add(type);
		}
		return result;
	}
}