package rs.org.amss.shell;

import java.io.IOException;
import java.util.ArrayList;

import rs.org.amss.R;
import rs.org.amss.core.MunicipalityAdapter;
import rs.org.amss.core.Variables;
import rs.org.amss.core.VehicleTypeAdapter;
import rs.org.amss.core.WebMethods;
import rs.org.amss.core.WebResponseParser;
import rs.org.amss.model.CalculateRegistrationRequest;
import rs.org.amss.model.GetFonts;
import rs.org.amss.model.Municipality;
import rs.org.amss.model.PaymentInvoice;
import rs.org.amss.model.VehicleType;

import org.apache.http.client.ClientProtocolException;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class CalculateRegistrationActivity extends BaseActivity {

	protected Spinner spinnerMunicipality;
	protected Spinner spinnerVehicleType;
	protected EditText textKw;
	protected EditText textCcm;
	protected EditText textYear;
	protected ProgressDialog progressDialog;
	protected MunicipalityAdapter municipalityAdapter;
	protected VehicleTypeAdapter vehicleTypeAdapter;
	protected CheckBox checkNewDrivingLicence;
	protected CheckBox checkNewPlates;
	protected boolean loadingDone;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_registration_calculate);
		setHomeAction(R.drawable.ic_main_info, R.string.info_calculator_text,
				InfoActivity.class);

		checkIsLogIn(CalculateRegistrationActivity.this);

		findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
		((TextView) findViewById(R.id.title)).setText("KALKULATOR REGISTRACIJE");
		((TextView) findViewById(R.id.subtitle)).setText("");

		spinnerMunicipality = (Spinner) findViewById(R.id.spinnerMunicipality);
		spinnerVehicleType = (Spinner) findViewById(R.id.spinnerVehicleType);
		textKw = (EditText) findViewById(R.id.textKw);
		textCcm = (EditText) findViewById(R.id.textCcm);
		textYear = (EditText) findViewById(R.id.textYear);
		checkNewDrivingLicence = (CheckBox) findViewById(R.id.checkNewDrivingLicence);
		checkNewPlates = (CheckBox) findViewById(R.id.checkNewPlates);
		setTypefaces();
		initializeCalculate();
	}

	private void setTypefaces() {
//		textKw.setTypeface(GetFonts.getTypeface(CalculateRegistrationActivity.this));
//		textCcm.setTypeface(GetFonts.getTypeface(CalculateRegistrationActivity.this));
//		textYear.setTypeface(GetFonts.getTypeface(CalculateRegistrationActivity.this));
//		checkNewDrivingLicence.setTypeface(GetFonts.getTypeface(CalculateRegistrationActivity.this));
//		checkNewPlates.setTypeface(GetFonts.getTypeface(CalculateRegistrationActivity.this));
		Button buttonCalculate = (Button)findViewById(R.id.buttonCalculate);
		buttonCalculate.setTypeface(GetFonts.getBoldTypeface(CalculateRegistrationActivity.this));
	}


	private void initializeCalculate() {
		progressDialog = ProgressDialog.show(
				CalculateRegistrationActivity.this,
				getResources().getString(R.string.info_calculator_text),
				getResources().getString(
						R.string.NetworkActivity_ProgressBar_Message));
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					progressDialog.cancel();
					CalculateRegistrationActivity.this.finish();
					return true;
				}
				return false;
			}
		});
		new GetMunicipalitiesTask().execute();
		new GetVehicleTypesTask().execute();
	}

	private Municipality getMunicipality() {

		return (Municipality) spinnerMunicipality.getSelectedItem();
	}

	private VehicleType getVehicleType() {

		return (VehicleType) spinnerVehicleType.getSelectedItem();
	}

	private int getKw() {
		String text = textKw.getText().toString().trim();
		try {
			return Integer.parseInt(text);
		} catch (final NumberFormatException e) {
			return 0;
		}
	}

	private int getCcm() {
		String text = textCcm.getText().toString().trim();
		try {
			return Integer.parseInt(text);
		} catch (final NumberFormatException e) {
			return 0;
		}
	}

	private int getYear() {
		String text = textYear.getText().toString().trim();
		try {
			return Integer.parseInt(text);
		} catch (final NumberFormatException e) {
			return 0;
		}
	}

	private class GetMunicipalitiesTask extends
			AsyncTask<Object, Void, ArrayList<Municipality>> {

		protected ArrayList<Municipality> doInBackground(Object... parameters) {
			ArrayList<Municipality> items = Variables.municipalities;
			if (items == null || items.size() == 0) {
				String response;
				try {
					response = WebMethods.getMunicipalities();
					items = WebResponseParser.getMunicipalityList(response);

					Variables.municipalities = items;

				} catch (ClientProtocolException e) {
					e.printStackTrace();
					CalculateRegistrationActivity.this
							.getLayoutManager()
							.showError(R.string.Loading_ClientProtocolException);
				} catch (IOException e) {
					e.printStackTrace();
					CalculateRegistrationActivity.this.getLayoutManager()
							.showError(R.string.Loading_IOException);
				} catch (Exception e) {
					e.printStackTrace();
					CalculateRegistrationActivity.this.getLayoutManager()
							.showError(R.string.Loading_Exception);
				}
			}
			return items;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}

		protected void onPostExecute(ArrayList<Municipality> result) {

			if (result != null && result.size() > 0) {

				municipalityAdapter = new MunicipalityAdapter(
						CalculateRegistrationActivity.this, result);
				spinnerMunicipality.setAdapter(municipalityAdapter);
			} else {
				getLayoutManager()
						.showWarning(
								R.string.error_info_registration_calculate_municipalities);
			}

			if (loadingDone)
				progressDialog.cancel();
		}
	}

	private class GetVehicleTypesTask extends
			AsyncTask<Object, Void, ArrayList<VehicleType>> {

		protected ArrayList<VehicleType> doInBackground(Object... parameters) {
			ArrayList<VehicleType> items = Variables.vehicleTypes;
			if (items == null || items.size() == 0) {
				String response;
				try {
					response = WebMethods.getVehicleTypes();
					items = WebResponseParser.getVehicleTypeList(response);

					Variables.vehicleTypes = items;

				} catch (ClientProtocolException e) {
					e.printStackTrace();
					CalculateRegistrationActivity.this
							.getLayoutManager()
							.showError(R.string.Loading_ClientProtocolException);
				} catch (IOException e) {
					e.printStackTrace();
					CalculateRegistrationActivity.this.getLayoutManager()
							.showError(R.string.Loading_IOException);
				} catch (Exception e) {
					e.printStackTrace();
					CalculateRegistrationActivity.this.getLayoutManager()
							.showError(R.string.Loading_Exception);
				}
			}
			return items;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}

		protected void onPostExecute(ArrayList<VehicleType> result) {

			if (result != null && result.size() > 0) {
				vehicleTypeAdapter = new VehicleTypeAdapter(
						CalculateRegistrationActivity.this, result);
				spinnerVehicleType.setAdapter(vehicleTypeAdapter);

				spinnerVehicleType.setSelection(6);
				spinnerVehicleType.setEnabled(false);
			} else {
				getLayoutManager()
						.showWarning(
								R.string.error_info_registration_calculate_vehicletypes);
			}

			if (loadingDone)
				hideProgress();

			progressDialog.cancel();
		}
	}

	// public void onNextClicked(View v){
	// try{
	// Variables.registrationInfo.municipalId = getMunicipality().id;
	// Variables.registrationInfo.vehicleTypeId = getVehicleType().id;
	// Variables.registrationInfo.enginePower = getKw();
	// Variables.registrationInfo.engineVolume = getCcm();
	// Variables.registrationInfo.productionYear = getYear();
	// }catch (Exception e){
	// Variables.registrationInfo = new CalculateRegistrationRequest();
	// e.printStackTrace();
	// CalculateRegistrationActivity.this.getLayoutManager().showWarning(R.string.error_info_registration_calculate_data);
	// }
	// if(Variables.registrationInfo.enginePower!=0 &&
	// Variables.registrationInfo.engineVolume!=0 &&
	// Variables.registrationInfo.productionYear!=0){
	// activityManager.startActivity(CalculateRegistrationScreenTwoActivity.class);
	// }
	// }
	public void Calculate(View view) {
		progressDialog = ProgressDialog.show(
				CalculateRegistrationActivity.this,
				getResources().getString(R.string.info_calculator_text),
				getResources().getString(R.string.SafetyActivity_Loading_Text));
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					progressDialog.cancel();
					CalculateRegistrationActivity.this.finish();
					return true;
				}
				return false;
			}
		});
		new CalculateRegistrationTask().execute();
	}

	private class CalculateRegistrationTask extends
			AsyncTask<Object, Void, ArrayList<PaymentInvoice>> {

		protected ArrayList<PaymentInvoice> doInBackground(Object... parameters) {

			ArrayList<PaymentInvoice> result = null;
			String response;

			try {
				Variables.registrationInfo.municipalId = getMunicipality().id;
				Variables.registrationInfo.vehicleTypeId = getVehicleType().id;
				Variables.registrationInfo.enginePower = getKw();
				Variables.registrationInfo.engineVolume = getCcm();
				Variables.registrationInfo.productionYear = getYear();
				Variables.registrationInfo.levelOfPreviousPremiumPolicy = 2;
				Variables.registrationInfo.numberOfDamages = 0;
				Variables.registrationInfo.newDrivingLicense = checkNewDrivingLicence
						.isChecked();
				Variables.registrationInfo.newRegistrationPlates = checkNewPlates
						.isChecked();
				CalculateRegistrationRequest request = Variables.registrationInfo;
				try {
					if (request.levelOfPreviousPremiumPolicy < 1
							|| request.levelOfPreviousPremiumPolicy > 12
							|| request.enginePower == 0
							|| request.engineVolume == 0) {
						CalculateRegistrationActivity.this
								.getLayoutManager()
								.showWarning(
										R.string.error_info_registration_calculate_data);
						request = null;
					}

				} catch (Exception e) {
					request = null;
					e.printStackTrace();
					CalculateRegistrationActivity.this
							.getLayoutManager()
							.showWarning(
									R.string.error_info_registration_calculate_data);
				}

				if (request != null) {
					response = WebMethods.calculateRegistration(request);
					result = WebResponseParser.getPaymentInvoiceList(response);
				}

			} catch (ClientProtocolException e) {
				e.printStackTrace();
				CalculateRegistrationActivity.this.getLayoutManager()
						.showError(R.string.Loading_ClientProtocolException);
			} catch (IOException e) {
				e.printStackTrace();
				CalculateRegistrationActivity.this.getLayoutManager()
						.showError(R.string.Loading_IOException);
			} catch (Exception e) {
				e.printStackTrace();
				CalculateRegistrationActivity.this.getLayoutManager()
						.showError(R.string.Loading_Exception);
			}
			return result;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}

		protected void onPostExecute(ArrayList<PaymentInvoice> result) {

			if (result != null) {
				CalculateRegistrationActivity.this
						.getActivityManager()
						.startActivityWithParam(
								CalculateRegistrationResultActivity.class,
								CalculateRegistrationResultActivity.INTENT_EXTRA,
								result);
			}
			progressDialog.cancel();
		}
	};
}
