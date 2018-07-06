package rs.org.amss.shell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import rs.org.amss.R;
import rs.org.amss.core.StaticStrings;
import rs.org.amss.core.WebMethods;
import rs.org.amss.core.WebResponseParser;
import rs.org.amss.model.GetFonts;

import org.apache.http.client.ClientProtocolException;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class AMSOKaskoActivity extends BaseActivity {
	public final static String INTENT_EXTRA = "position";

	public Context context = this;
	ProgressDialog progressDialog;
	int amount = 0;
	double percent = 0;
	protected Button sendEmail;
	protected Spinner typeOfVehicle, vehicleBrands, vehicleTypes, formOfBody, lising, teritory, rateForPayment, year, purpose;
	protected EditText vehicleModel, volume, power, doorsNumber, name, email, telephone;

	protected CheckBox check;
	private ArrayAdapter<String> vehicleTypesAdapter, vehicleFormsAdapter, lisingAdapter, teritoryAdapter, yearsAdapter, paymentsAdapter, purposeAdapter;
	private String[] types = {"putničko vozilo"};
	private String[] forms = {"kupe", "limuzina", "karavan", "produžena limuzina", "višenamenski zatvoreni putnički automobil",
			"specijalni zatvoreni putnički automobil", "kupe kabriolet", "limuzina kabriolet", "sportski automobil",
			"višenamenski otvoreni putnički automobil", "kombi"};
	private String[] lising_rent = {"ne", "da"};
	private String[] territoryList = {"Srbija", "Srbija i Evropa"};
	private String[] payments={"mesečno", "kvartalno", "godišnje"};
	private String[] purposes = {"nije navedeno", "rent a car", "taxi"};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.amso_kasko);

		String title = getString(R.string.AMSO_label);

		findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
		((TextView) findViewById(R.id.title)).setText(title);
		((TextView) findViewById(R.id.subtitle)).setText("/  Kasko osiguranje / Kalkulator premije");

		checkIsLogIn(AMSOKaskoActivity.this);

		typeOfVehicle = (Spinner)findViewById(R.id.typeOfVehicle);
		vehicleBrands = (Spinner)findViewById(R.id.vehicleBrands);
		vehicleTypes = (Spinner)findViewById(R.id.vehicleTypes);
		formOfBody = (Spinner)findViewById(R.id.formOFBody);
		lising = (Spinner)findViewById(R.id.lising);
		teritory = (Spinner)findViewById(R.id.teritory);
		year = (Spinner)findViewById(R.id.year);
		rateForPayment = (Spinner)findViewById(R.id.rateForPayment);
		purpose = (Spinner)findViewById(R.id.purpose);

		vehicleModel = (EditText)findViewById(R.id.vehicleModel);
		volume = (EditText)findViewById(R.id.volume);
		power = (EditText)findViewById(R.id.power);
		doorsNumber = (EditText)findViewById(R.id.doorsNumber);
		name = (EditText)findViewById(R.id.name);
		email = (EditText)findViewById(R.id.email);
		telephone = (EditText)findViewById(R.id.telephone);

		check = (CheckBox)findViewById(R.id.stealingRisk);
		check.setChecked(true);
		sendEmail = (Button)findViewById(R.id.send_email);
        sendEmail.setTypeface(GetFonts.getBoldTypeface(this));
        sendEmail.setText(sendEmail.getText().toString().toUpperCase());

//		setTypefaces();
		initializeCarBrandSpinner();
		addItemsToSpinners();

		sendEmail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!checkIfFieldIsEmpty()){
					initializeSendingMail();
				}
			}
		});

	}
	private void setTypefaces() {
		TextView typeOfVehicle_txt = (TextView)findViewById(R.id.typeOfVehicle_txt);
		TextView vehicleBrands_txt = (TextView)findViewById(R.id.vehicleBrands_txt);
		TextView vehicleTypes_txt = (TextView)findViewById(R.id.vehicleTypes_txt);
		TextView formOfBody_txt = (TextView)findViewById(R.id.formOFBody_txt);
		TextView lising_txt = (TextView)findViewById(R.id.lising_txt);
		TextView teritory_txt = (TextView)findViewById(R.id.teritory_txt);
		TextView year_txt = (TextView)findViewById(R.id.year_txt);
		TextView rateForPayment_txt = (TextView)findViewById(R.id.rateForPayment_txt);
		TextView purpose_txt = (TextView)findViewById(R.id.purpose_txt);

		typeOfVehicle_txt.setTypeface(GetFonts.getTypeface(context));
		vehicleBrands_txt.setTypeface(GetFonts.getTypeface(context));
		vehicleTypes_txt.setTypeface(GetFonts.getTypeface(context));
		formOfBody_txt.setTypeface(GetFonts.getTypeface(context));
		formOfBody_txt.setTypeface(GetFonts.getTypeface(context));
		lising_txt.setTypeface(GetFonts.getTypeface(context));
		teritory_txt.setTypeface(GetFonts.getTypeface(context));
		year_txt.setTypeface(GetFonts.getTypeface(context));
		rateForPayment_txt.setTypeface(GetFonts.getTypeface(context));
		purpose_txt.setTypeface(GetFonts.getTypeface(context));
		
		vehicleModel.setTypeface(GetFonts.getTypeface(context));
		volume.setTypeface(GetFonts.getTypeface(context));
		power.setTypeface(GetFonts.getTypeface(context));
		doorsNumber.setTypeface(GetFonts.getTypeface(context));
		name.setTypeface(GetFonts.getTypeface(context));
		email.setTypeface(GetFonts.getTypeface(context));
		telephone.setTypeface(GetFonts.getTypeface(context));
		sendEmail.setTypeface(GetFonts.getTypeface(context));
		check.setTypeface(GetFonts.getTypeface(context));		
	}

	protected void initializeSendingMail() {
//		progressDialog = ProgressDialog.show(AMSOKaskoActivity.this, getResources().getString(R.string.AMSO_kasko_text), getResources().getString(R.string.NetworkActivity_ProgressBar_Message));
//		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//
//			@Override
//			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//				if (keyCode == KeyEvent.KEYCODE_BACK) {
//					progressDialog.cancel();
//					AMSOKaskoActivity.this.finish();
//					return true;
//				}
//				return false;
//			}
//		});
//
//		new SendEmailTask().execute(getCarType(), getCarBrand(), getCarModel(), getVehicleModel(), ""+getVolume(),
//				""+getPower(), ""+getDoorsNumber(), getYear(), getFormOfBody(), ""+getLising(),
//				""+getRent(), getTerritory(), ""+check.isChecked(), getPaymentsRate(), getName(),
//				getEmail(), getPhone(), getCarPurpose());

		String body = "";
		String nl = "\n";
		body += "Premijska grupa (vrsta vozila): " + getCarType() + nl;
		body += "Marka vozila: " + getCarBrand() + nl;
		body += "Tip vozila: " + getCarModel() + nl;
		body += "Model vozila: " + getVehicleModel() + nl;
		body += "Zapremina motora (ccm): " + getVolume() + nl;
		body += "Snaga (kW): " + getPower() + nl;
		body += "Broj vrata: " + getDoorsNumber() + nl;
		body += "Godina proizvodnje: " + getYear() + nl;
		body += "Oblik i namena karoserije: " + getFormOfBody() + nl;
		body += "Da li se vozilo vodi na Lizing/Rent: " + (getLising() ? "da" : "ne") + nl;
		body += "Namena vozila: " + getCarPurpose() + nl;
		body += "Teritorijalno pokriće: " + getTerritory() + nl;
		body += "Uključi i rizik od krađe: da" + nl;
		body += "Željena dinamika plaćanja: " + getPaymentsRate() + nl;
		body += "Ime: " + getName() + nl;
		body += "E-mail: " + getEmail() + nl;
		body += "Telefon: " + getPhone() + nl;

		Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", StaticStrings.kaskoEmail, null));
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Zahtev za obračun kasko osiguranja");
		emailIntent.putExtra(Intent.EXTRA_TEXT, body);
		startActivity(Intent.createChooser(emailIntent, "Pošalji zahtev..."));
	}
	protected boolean checkIfFieldIsEmpty() {
		if(getVehicleModel().length()==0){
			AMSOKaskoActivity.this.getLayoutManager().showError("Polje MODEL VOZILA je prazno!");
			return true;
		}
		if(getVolume()==0){
			AMSOKaskoActivity.this.getLayoutManager().showError("Polje ZAPREMINA MOTORA je prazno!");
			return true;
		}
		if(getPower()==0){
			AMSOKaskoActivity.this.getLayoutManager().showError("Polje SNAGA MOTORA je prazno!");
			return true;
		}
		if(getDoorsNumber()==0){
			AMSOKaskoActivity.this.getLayoutManager().showError("Polje BROJ VRATA je prazno!");
			return true;
		}
		if(getEmail().length()==0){
			AMSOKaskoActivity.this.getLayoutManager().showError("Polje E-MAIL je prazno!");
			return true;
		}
		return false;
	}

	private void addItemsToSpinners() {
		vehicleTypesAdapter = new ArrayAdapter<String>(AMSOKaskoActivity.this, android.R.layout.simple_spinner_item, types);
		vehicleTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeOfVehicle.setAdapter(vehicleTypesAdapter);

		vehicleFormsAdapter = new ArrayAdapter<String>(AMSOKaskoActivity.this, android.R.layout.simple_spinner_item, forms);
		vehicleFormsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		formOfBody.setAdapter(vehicleFormsAdapter);

		lisingAdapter = new ArrayAdapter<String>(AMSOKaskoActivity.this, android.R.layout.simple_spinner_item, lising_rent);
		lisingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		lising.setAdapter(lisingAdapter);

		teritoryAdapter = new ArrayAdapter<String>(AMSOKaskoActivity.this, android.R.layout.simple_spinner_item, territoryList);
		teritoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		teritory.setAdapter(teritoryAdapter);
		teritory.setSelection(0);


		yearsAdapter = new ArrayAdapter<String>(AMSOKaskoActivity.this, android.R.layout.simple_spinner_item, getYears());
		yearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		year.setAdapter(yearsAdapter);

		paymentsAdapter = new ArrayAdapter<String>(AMSOKaskoActivity.this, android.R.layout.simple_spinner_item, payments);
		paymentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		rateForPayment.setAdapter(paymentsAdapter);
		rateForPayment.setSelection(0);

		purposeAdapter = new ArrayAdapter<String>(AMSOKaskoActivity.this, android.R.layout.simple_spinner_item, purposes);
		purposeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		purpose.setAdapter(purposeAdapter);
	}

	private String getCarBrand(){
		return (String)vehicleBrands.getSelectedItem();		
	}

	private String getCarPurpose(){
		return (String)purpose.getSelectedItem();		
	}

	private String getCarModel(){
		return (String)vehicleTypes.getSelectedItem();		
	}

	private String getCarType(){
		return (String)typeOfVehicle.getSelectedItem();		
	}

	private String getFormOfBody(){
		return (String)formOfBody.getSelectedItem();		
	}

	private boolean getLising(){
		String text = (String)lising.getSelectedItem();		
		if(text.equals("lizing"))
			return true;
		return false;
	}
	private boolean getRent(){
		String text = (String)lising.getSelectedItem();		
		if(text.equals("rent"))
			return true;
		return false;
	}

	private String getTerritory(){
		return (String)teritory.getSelectedItem();		
	}
	private String getPaymentsRate(){
		return (String)rateForPayment.getSelectedItem();		
	}
	private double getVolume(){
		String text = volume.getText().toString().trim();
		try{
			return Double.parseDouble(text);
		}catch(final NumberFormatException e){
			return 0;			
		}
	}
	private double getPower(){
		String text = power.getText().toString().trim();		
		try{
			return Double.parseDouble(text);
		}catch(final NumberFormatException e){
			return 0;			
		}
	}

	private int getDoorsNumber(){
		String text = doorsNumber.getText().toString().trim();		
		try{
			return Integer.parseInt(text);
		}catch(final NumberFormatException e){
			return 0;			
		}
	}
	private String getYear(){
		return (String)year.getSelectedItem();
	}

	private String getVehicleModel(){
		return vehicleModel.getText().toString();
	}

	private String getName(){
		return name.getText().toString();
	}

	private String getEmail(){
		return email.getText().toString();
	}

	private String getPhone(){
		return telephone.getText().toString();
	}
	private void initializeCarBrandSpinner(){
		progressDialog = ProgressDialog.show(AMSOKaskoActivity.this, getResources().getString(R.string.AMSO_kasko_text), getResources().getString(R.string.NetworkActivity_ProgressBar_Message));
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					progressDialog.cancel();
					AMSOKaskoActivity.this.finish();
					return true;
				}
				return false;
			}
		});
		new GetCarBrandsTask().execute();
	}

	private void initializeCarTypeSpinner(String carType){
		progressDialog = ProgressDialog.show(AMSOKaskoActivity.this, getResources().getString(R.string.AMSO_kasko_text), getResources().getString(R.string.NetworkActivity_ProgressBar_Message));
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					progressDialog.cancel();
					AMSOKaskoActivity.this.finish();
					return true;
				}
				return false;
			}
		});
		new GetCarTypesTask().execute(carType);
	}

	private class GetCarBrandsTask extends AsyncTask<Object, Void, ArrayList<String>> {			

		protected ArrayList<String> doInBackground(Object... parameters) {
			ArrayList<String> items = new ArrayList<String>();
			if (items == null || items.size() == 0){
				String response;
				try {
					response = WebMethods.getKaskoCarBrands(); 				
					items = WebResponseParser.getStringValues(response);

				} catch (ClientProtocolException e) {
					e.printStackTrace();
					AMSOKaskoActivity.this.getLayoutManager().showError(R.string.Loading_ClientProtocolException);
				} catch (IOException e) {
					e.printStackTrace();
					AMSOKaskoActivity.this.getLayoutManager().showError(R.string.Loading_IOException);
				} catch (Exception e) {
					e.printStackTrace();
					AMSOKaskoActivity.this.getLayoutManager().showError(R.string.Loading_Exception);
				}
			}
			return items;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}
		protected void onPostExecute(ArrayList<String> result) {

			if (result != null && result.size() > 0){ 

				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AMSOKaskoActivity.this,
						android.R.layout.simple_spinner_item, result);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				vehicleBrands.setAdapter(dataAdapter);

				vehicleBrands.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						initializeCarTypeSpinner(getCarBrand().replaceAll(" ", "%20"));
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}

				});
			}
			else{
				getLayoutManager().showWarning(R.string.error_info_registration_calculate_municipalities);
			}

			progressDialog.cancel();
		}
	}
	private class GetCarTypesTask extends AsyncTask<String, Void, ArrayList<String>> {			

		protected ArrayList<String> doInBackground(String... parameters) {
			ArrayList<String> items = new ArrayList<String>();
			if (items == null || items.size() == 0){
				String response;
				try {
					response = WebMethods.getKaskoCarTypes(parameters[0]); 				
					items = WebResponseParser.getStringValues(response);

				} catch (ClientProtocolException e) {
					e.printStackTrace();
					AMSOKaskoActivity.this.getLayoutManager().showError(R.string.Loading_ClientProtocolException);
				} catch (IOException e) {
					e.printStackTrace();
					AMSOKaskoActivity.this.getLayoutManager().showError(R.string.Loading_IOException);
				} catch (Exception e) {
					e.printStackTrace();
					AMSOKaskoActivity.this.getLayoutManager().showError(R.string.Loading_Exception);
				}
			}
			return items;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}
		protected void onPostExecute(ArrayList<String> result) {

			if (result != null && result.size() > 0){ 

				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AMSOKaskoActivity.this,
						android.R.layout.simple_spinner_item, result);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				vehicleTypes.setAdapter(dataAdapter);
			}
			else{
				getLayoutManager().showWarning(R.string.error_info_registration_calculate_municipalities);
			}

			progressDialog.cancel();
		}
	}
	private ArrayList<String> getYears(){
		ArrayList<String> result = new ArrayList<String>();
		Calendar c = Calendar.getInstance(); 
		int year = c.get(Calendar.YEAR);
		result.add(""+year);
		for(int i=0; i<7; i++){
			int temp = year-1;
			result.add(""+temp);
			year=temp;
		}
		return result;
	}

	private class SendEmailTask extends AsyncTask<String, Void, Boolean> {			

		protected Boolean doInBackground(String... parameters) {
			boolean result = false;
			String response;
			try {
				response = WebMethods.sendKaskoEmailForOffer(parameters[0], parameters[1], parameters[2],
						parameters[3], parameters[4], parameters[5], parameters[6],
						parameters[7], parameters[8], parameters[9], parameters[10],
						parameters[11], parameters[12], parameters[13], parameters[14], 
						parameters[15], parameters[16], parameters[17]); 				
				result = Boolean.parseBoolean(WebResponseParser.parseSingleResponse(response));

			} catch (ClientProtocolException e) {
				e.printStackTrace();
				AMSOKaskoActivity.this.getLayoutManager().showError(R.string.Loading_ClientProtocolException);
			} catch (IOException e) {
				e.printStackTrace();
				AMSOKaskoActivity.this.getLayoutManager().showError(R.string.Loading_IOException);
			} catch (Exception e) {
				e.printStackTrace();
				AMSOKaskoActivity.this.getLayoutManager().showError(R.string.Loading_Exception);
			}
			return result;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}
		protected void onPostExecute(Boolean result) {

			if (result){ 
				finish();
				getLayoutManager().showInfo(getResources().getString(R.string.amso_kasko_successfful_msg));
			}
			else{
				getLayoutManager().showWarning(getResources().getString(R.string.amso_kasko_failure_msg));
			}
			progressDialog.cancel();
		}
	}
}

