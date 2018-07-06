package rs.org.amss.shell;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.client.ClientProtocolException;

import rs.org.amss.R;
import rs.org.amss.core.TypeOfRateAdapter;
import rs.org.amss.core.WebMethods;
import rs.org.amss.core.WebResponseParser;
import rs.org.amss.model.GetFonts;
import rs.org.amss.model.TypeOfRate;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class AMSOTravelInsuranceCalculatorActivity extends BaseActivity{
	Spinner typeOfRate, age, coverPeriod, dangerLevel, participation, tripPurpose, coverPeriodDays;
	public EditText duration;
	TextView dateBegining_text, dateEnding_text,
	typeOfRate_txt, age_txt, coverPeriod_txt, dangerLevel_txt, participation_txt, tripPurpose_txt, coverPeriodDays_txt;
	public Date startDate = new Date(2013, 9, 6);
	public Date endDate = new Date(2013, 9, 6);
	public String vrsta_tarife;
	String period_pokrica;
	public String days = "";
	ProgressDialog progressDialog;
	TypeOfRateAdapter typeOfRateAdapter, ageAdapter, 
	coverPeriodAdapter, dangerLevelAdapter, participationAdapter,
	tripPurposeAdapter, coverPeriodDaysAdapter;
	protected Date minDate=new Date(2013, 9, 6);
	protected Date maxDate=new Date(2013, 9, 6);


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.amso_travel_insurance);

		String title = getString(R.string.AMSO_healthy_insurance_text);

		findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
		((TextView) findViewById(R.id.title)).setText("INFORMACIJE");
		((TextView) findViewById(R.id.subtitle)).setText("ZOBS");

		checkIsLogIn(AMSOTravelInsuranceCalculatorActivity.this);

		typeOfRate = (Spinner)findViewById(R.id.typeOfRate);
		age = (Spinner)findViewById(R.id.age);
		coverPeriod = (Spinner)findViewById(R.id.cover_period);
		coverPeriodDays = (Spinner)findViewById(R.id.cover_period_days);
		dangerLevel = (Spinner)findViewById(R.id.danger_level);
		participation = (Spinner)findViewById(R.id.participation);
		tripPurpose = (Spinner)findViewById(R.id.trip_purpose);

		typeOfRate_txt = (TextView)findViewById(R.id.typeOfRate_txt);
		age_txt = (TextView)findViewById(R.id.age_txt);
		coverPeriod_txt = (TextView)findViewById(R.id.cover_period_txt);
		coverPeriodDays_txt = (TextView)findViewById(R.id.cover_period_days_txt);
		dangerLevel_txt = (TextView)findViewById(R.id.danger_level_txt);
		participation_txt = (TextView)findViewById(R.id.participation_txt);
		tripPurpose_txt = (TextView)findViewById(R.id.trip_purpose_txt);

		typeOfRate_txt.setTypeface(GetFonts.getTypeface(AMSOTravelInsuranceCalculatorActivity.this));
		age_txt.setTypeface(GetFonts.getTypeface(AMSOTravelInsuranceCalculatorActivity.this));
		coverPeriod_txt.setTypeface(GetFonts.getTypeface(AMSOTravelInsuranceCalculatorActivity.this));
		coverPeriodDays_txt.setTypeface(GetFonts.getTypeface(AMSOTravelInsuranceCalculatorActivity.this));
		dangerLevel_txt.setTypeface(GetFonts.getTypeface(AMSOTravelInsuranceCalculatorActivity.this));
		participation_txt.setTypeface(GetFonts.getTypeface(AMSOTravelInsuranceCalculatorActivity.this));
		tripPurpose_txt.setTypeface(GetFonts.getTypeface(AMSOTravelInsuranceCalculatorActivity.this));

		duration=(EditText)findViewById(R.id.duration);
		duration.setFocusable(false);
		duration.setTypeface(GetFonts.getTypeface(AMSOTravelInsuranceCalculatorActivity.this));
		duration.setFocusableInTouchMode(false);

		Button getDate = (Button)findViewById(R.id.choose_date);
		Button calculate = (Button)findViewById(R.id.calculate);
		Button i = (Button)findViewById(R.id.danger_level_info);
		getDate.setTypeface(GetFonts.getBoldTypeface(AMSOTravelInsuranceCalculatorActivity.this));
		calculate.setTypeface(GetFonts.getBoldTypeface(AMSOTravelInsuranceCalculatorActivity.this));
		i.setTypeface(GetFonts.getBoldTypeface(AMSOTravelInsuranceCalculatorActivity.this));

        getDate.setText(getDate.getText().toString().toUpperCase());
        calculate.setText(calculate.getText().toString().toUpperCase());

		setSpinners();

		typeOfRate.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				vrsta_tarife = getTypeOfRate().id;
				Log.v("Vrsta tarife", vrsta_tarife+" "+getTypeOfRate().type);
				if(vrsta_tarife.equals("02")){
					age.setVisibility(View.GONE);
					age_txt.setVisibility(View.GONE);
				}
				else{
					age.setVisibility(View.VISIBLE);
					age_txt.setVisibility(View.VISIBLE);
				}
				if(vrsta_tarife.equals("03")){
					duration.setVisibility(View.GONE);
					coverPeriod.setVisibility(View.VISIBLE);
					coverPeriodDays.setVisibility(View.VISIBLE);
					coverPeriod_txt.setVisibility(View.VISIBLE);
					coverPeriodDays_txt.setVisibility(View.VISIBLE);
					//										setCoverPeriodSpinner();
					initializeCarTypeSpinner(getCoverPeriodDays().id);
				}else{
					duration.setVisibility(View.VISIBLE);
					coverPeriod.setVisibility(View.GONE);
					coverPeriodDays.setVisibility(View.GONE);
					coverPeriod_txt.setVisibility(View.GONE);
					coverPeriodDays_txt.setVisibility(View.GONE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		LinearLayout background = (LinearLayout)findViewById(R.id.background);

		// Checks the orientation of the screen  
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			background.setBackgroundResource(R.drawable.walpaper_light_land);
		}
		else 
			background.setBackgroundResource(R.drawable.walpaper_light_copy);
	}
	private void setSpinners() {
		typeOfRateAdapter = new TypeOfRateAdapter(AMSOTravelInsuranceCalculatorActivity.this, TypeOfRate.getTypeOfRate(AMSOTravelInsuranceCalculatorActivity.this));
		typeOfRate.setAdapter(typeOfRateAdapter);

		ageAdapter = new TypeOfRateAdapter(AMSOTravelInsuranceCalculatorActivity.this, TypeOfRate.getAgesList());
		age.setAdapter(ageAdapter);

		coverPeriodDaysAdapter = new TypeOfRateAdapter(AMSOTravelInsuranceCalculatorActivity.this, TypeOfRate.getCoverPeriodDays());
		coverPeriodDays.setAdapter(coverPeriodDaysAdapter);

		dangerLevelAdapter = new TypeOfRateAdapter(AMSOTravelInsuranceCalculatorActivity.this, TypeOfRate.getLevelOfDangerous(AMSOTravelInsuranceCalculatorActivity.this));
		dangerLevel.setAdapter(dangerLevelAdapter);

		participationAdapter = new TypeOfRateAdapter(AMSOTravelInsuranceCalculatorActivity.this, TypeOfRate.getParticipationList(AMSOTravelInsuranceCalculatorActivity.this));
		participation.setAdapter(participationAdapter);

		tripPurposeAdapter = new TypeOfRateAdapter(AMSOTravelInsuranceCalculatorActivity.this, TypeOfRate.getTravelPurposeList(AMSOTravelInsuranceCalculatorActivity.this));
		tripPurpose.setAdapter(tripPurposeAdapter);
	}

	private TypeOfRate getTripPurpose() {
		return (TypeOfRate)tripPurpose.getSelectedItem();		
	}

	private TypeOfRate getParticipation() {
		return (TypeOfRate)participation.getSelectedItem();		
	}

	private TypeOfRate getDangerLevel() {
		return (TypeOfRate)dangerLevel.getSelectedItem();		
	}

	private String getCoverPeriod() {
		if(vrsta_tarife.equals("03"))
			return getCoverPeriodDays().id;
		else{
			String result = "";
			if(days!=null || days.length()>0){
				result = "00"+days;
				if(result.length()==4)
					result=result.substring(1);
			}
			return result;
		}
	}

	private TypeOfRate getAge() {
		return (TypeOfRate)age.getSelectedItem();		
	}

	private TypeOfRate getTypeOfRate() {
		return (TypeOfRate)typeOfRate.getSelectedItem();		
	}
	private TypeOfRate getCoverPeriodDays() {
		return (TypeOfRate)coverPeriodDays.getSelectedItem();		
	}

	public void showDatePickerDialog(View v) {
		getDatePickerDialog(AMSOTravelInsuranceCalculatorActivity.this);
	}

	public int get_days_between_dates(Date date1, Date date2)
	{       
		return (int)((date2.getTime() - date1.getTime()) / (1000*60*60*24l));
	}

	private void initializeCarTypeSpinner(String numberOfDays){
		progressDialog = ProgressDialog.show(AMSOTravelInsuranceCalculatorActivity.this, getResources().getString(R.string.AMSO_healthy_insurance_text), getResources().getString(R.string.NetworkActivity_ProgressBar_Message));
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					progressDialog.cancel();
					AMSOTravelInsuranceCalculatorActivity.this.finish();
					return true;
				}
				return false;
			}
		});
		new GetCoveragePeriodTask().execute(numberOfDays);
	}

	private class GetCoveragePeriodTask extends AsyncTask<String, Void, ArrayList<TypeOfRate>> {			

		protected ArrayList<TypeOfRate> doInBackground(String... parameters) {
			ArrayList<TypeOfRate> items = new ArrayList<TypeOfRate>();
			if (items == null || items.size() == 0){
				String response;
				try {
					response = WebMethods.getPeriodsOfCoverage(parameters[0]); 				
					items = WebResponseParser.getCoveragePeriod(response);

				} catch (ClientProtocolException e) {
					e.printStackTrace();
					AMSOTravelInsuranceCalculatorActivity.this.getLayoutManager().showError(R.string.Loading_ClientProtocolException);
				} catch (IOException e) {
					e.printStackTrace();
					AMSOTravelInsuranceCalculatorActivity.this.getLayoutManager().showError(R.string.Loading_IOException);
				} catch (Exception e) {
					e.printStackTrace();
					AMSOTravelInsuranceCalculatorActivity.this.getLayoutManager().showError(R.string.Loading_Exception);
				}
			}
			return items;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}
		protected void onPostExecute(ArrayList<TypeOfRate> result) {

			if (result != null && result.size() > 0){ 
				coverPeriodAdapter = new TypeOfRateAdapter(AMSOTravelInsuranceCalculatorActivity.this, result);
				coverPeriod.setAdapter(coverPeriodAdapter);
				progressDialog.cancel();
			}
		}
	}
	public void Calculate(View view){
		if(getCoverPeriod()==null || getCoverPeriod().length()==0 || getCoverPeriod().equals("00") || getCoverPeriod().equals("000"))
			layoutManager.showError(R.string.AMSO_kasko_travel_insurance_no_cover_period_text);
		else{
			progressDialog = ProgressDialog.show(AMSOTravelInsuranceCalculatorActivity.this, getResources().getString(R.string.AMSO_healthy_insurance_text), getResources().getString(R.string.NetworkActivity_ProgressBar_Message));
			progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_BACK) {
						progressDialog.cancel();
						AMSOTravelInsuranceCalculatorActivity.this.finish();
						return true;
					}
					return false;
				}
			});

			new CalculateTravelInsuranceTask().execute(getTypeOfRate().id, getAge().id,
					getCoverPeriod(), getDurationOfInsurance(),getDangerLevel().id,
					getParticipation().id, getTripPurpose().id);
		}
	}

	private String getDurationOfInsurance() {
		if(vrsta_tarife.equals("03"))
			return getCoverPeriod();
		else{
			String result = "00"+days;
			if(result.length()==4)
				result=result.substring(1);
			return result;
		}
	}
	public Date getMaxDate(Date minDate){
		Calendar c = Calendar.getInstance();
		c.setTime(minDate);
		c.add(Calendar.DATE, 90);//insert the number of days you want to be added to the current date
		Date resultdate = new Date(c.get(Calendar.YEAR)-1900, c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
		resultdate.setYear(resultdate.getYear()-1900);
		Log.v("MAX date - getmaxdate", ""+resultdate);
		return resultdate;
	}
	public Calendar getCalendarDate(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;	
	}

	private class CalculateTravelInsuranceTask extends AsyncTask<String, Void, String> {			

		protected String doInBackground(String... parameters) {
			String items = new String();
			if (items == null || items.length() == 0){
				String response;
				try {
					response = WebMethods.calculateTravelInsurance(parameters[0], parameters[1],
							parameters[2], parameters[3], parameters[4], parameters[5], parameters[6]); 				
					items = WebResponseParser.parseSingleResponse(response);

				} catch (ClientProtocolException e) {
					e.printStackTrace();
					AMSOTravelInsuranceCalculatorActivity.this.getLayoutManager().showError(R.string.Loading_ClientProtocolException);
				} catch (IOException e) {
					e.printStackTrace();
					AMSOTravelInsuranceCalculatorActivity.this.getLayoutManager().showError(R.string.Loading_IOException);
				} catch (Exception e) {
					e.printStackTrace();
					AMSOTravelInsuranceCalculatorActivity.this.getLayoutManager().showError(R.string.Loading_Exception);
				}
			}
			return items;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}
		protected void onPostExecute(String result) {

			if (result != null && result.length() > 0){ 
				String message = getString(R.string.AMSO_kasko_travel_insurance_result_text)+" "+result+",00 dinara.";
				getNotificationDialog(AMSOTravelInsuranceCalculatorActivity.this, message);
			}

			Log.v("Putno osiguranje - parametri", "Vrsta tarife: "+getTypeOfRate().id);
			Log.v("Putno osiguranje - parametri", "Starost: "+getAge().id);
			Log.v("Putno osiguranje - parametri", "Period pokrica: "+getCoverPeriod());
			Log.v("Putno osiguranje - parametri", "Trajanje osiguranja: "+getDurationOfInsurance());
			Log.v("Putno osiguranje - parametri", "Razred opasnosti: "+getDangerLevel().id);
			Log.v("Putno osiguranje - parametri", "Ucescee: "+getParticipation().id);
			Log.v("Putno osiguranje - parametri", "Namena: "+getTripPurpose().id);
			progressDialog.dismiss();
		}
	}
	public void ShowInfo(View v){
		getNotificationDialog(AMSOTravelInsuranceCalculatorActivity.this, getString(R.string.amso_travel_insurance_level_of_danger_info_text));
	}
}
