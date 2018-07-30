package rs.org.amss.shell;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;

import rs.org.amss.R;
import rs.org.amss.core.Variables;
import rs.org.amss.core.WebMethods;
import rs.org.amss.core.WebResponseParser;
import rs.org.amss.model.GetFonts;
import rs.org.amss.model.Member;
import net.lmggroup.utility.ActivityHelper;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class LoyaltyBecomeScreenFourActivity extends BaseActivity{
	EditText edit_vehicle_trademark, edit_vehicle_type, edit_reg_plate, edit_vehicle_color, edit_year_of_production, edit_insurance_company;
	RadioGroup loyalty_type;
	TextView heading, type_of_loyalty;
	RadioButton osnovno, osnovno_super;
	ProgressDialog progressDialog;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		memoryManager.loadVariables();

		setContentView(R.layout.become_loyalty_screen_four);
		String title = getString(R.string.loyalty_become_partner_text) + "/"
				+ getString(R.string.become_loyalty_values_text);
		setHomeAction(R.drawable.ic_membership, title,
				LoyaltyActivity.class);
		checkIsLogIn(LoyaltyBecomeScreenFourActivity.this);

		edit_vehicle_trademark = (EditText) findViewById(R.id.edit_vehicle_trademark);
		edit_vehicle_type = (EditText) findViewById(R.id.edit_vehicle_type);
		edit_reg_plate = (EditText) findViewById(R.id.edit_reg);
		edit_vehicle_color = (EditText) findViewById(R.id.edit_vehicle_color);
		edit_year_of_production = (EditText) findViewById(R.id.edit_year);
		edit_insurance_company = (EditText) findViewById(R.id.edit_insurance_company);

		edit_vehicle_trademark.setTypeface(GetFonts.getTypeface(LoyaltyBecomeScreenFourActivity.this));
		edit_vehicle_type.setTypeface(GetFonts.getTypeface(LoyaltyBecomeScreenFourActivity.this));
		edit_reg_plate.setTypeface(GetFonts.getTypeface(LoyaltyBecomeScreenFourActivity.this));
		edit_vehicle_color.setTypeface(GetFonts.getTypeface(LoyaltyBecomeScreenFourActivity.this));
		edit_year_of_production.setTypeface(GetFonts.getTypeface(LoyaltyBecomeScreenFourActivity.this));
		edit_insurance_company.setTypeface(GetFonts.getTypeface(LoyaltyBecomeScreenFourActivity.this));

		heading = (TextView)findViewById(R.id.heading);
		type_of_loyalty = (TextView)findViewById(R.id.loyalty_type);

		heading.setTypeface(GetFonts.getBoldTypeface(LoyaltyBecomeScreenFourActivity.this));
		type_of_loyalty.setTypeface(GetFonts.getBoldTypeface(LoyaltyBecomeScreenFourActivity.this));

		loyalty_type = (RadioGroup)findViewById(R.id.type);
		osnovno = (RadioButton)findViewById(R.id.basic);
		osnovno_super = (RadioButton)findViewById(R.id.basic_super);

		osnovno.setTypeface(GetFonts.getTypeface(LoyaltyBecomeScreenFourActivity.this));
		osnovno_super.setTypeface(GetFonts.getTypeface(LoyaltyBecomeScreenFourActivity.this));


	}

	private String getVehicleTrademark(){
		if(edit_vehicle_trademark.getText().toString().length()>0)
			return edit_vehicle_trademark.getText().toString();
		return null;
	}

	private String getVehicleType(){
		if(edit_vehicle_type.getText().toString().length()>0)
			return edit_vehicle_type.getText().toString();
		return null;
	}

	private String getRegistrationPlate(){
		if(edit_reg_plate.getText().toString().length()>0)
			return edit_reg_plate.getText().toString();
		return null;
	}

	private String getVehicleColor(){
		if(edit_vehicle_color.getText().toString().length()>0)
			return edit_vehicle_color.getText().toString();
		return null;
	}

	private String getVehicleYear(){
		if(edit_year_of_production.getText().toString().length()>0)
			return edit_year_of_production.getText().toString();
		return null;
	}

	private String getVehicleInsuranceCompany(){
		if(edit_insurance_company.getText().toString().length()>0)
			return edit_insurance_company.getText().toString();
		return null;
	}

	private String getLoyaltyTypeSelected(){
		int selectedID = loyalty_type.getCheckedRadioButtonId();
		RadioButton selectedButton = (RadioButton)findViewById(selectedID);
		if(selectedButton!=null)
			return selectedButton.getText().toString();
		return null;
	}

	public void SendValues(View v){
		if(!checkIfFieldsAreEmpty()){
			Variables.getMember().vehicleBrand = getVehicleTrademark();
			Variables.getMember().vehicleType = getVehicleType();
			Variables.getMember().registrationPlate = getRegistrationPlate();
			Variables.getMember().vehicleColor = getVehicleColor();
			Variables.getMember().yearOfProduction = getVehicleYear();
			Variables.getMember().insuranceCompany = getVehicleInsuranceCompany();
			if(getLoyaltyTypeSelected()!=null){
				if(getLoyaltyTypeSelected().equalsIgnoreCase("osnovno"))
					Variables.getMember().isSuperMembership = false;
				else
					Variables.getMember().isSuperMembership = true;
				progressDialog = ProgressDialog
						.show(LoyaltyBecomeScreenFourActivity.this,
								getResources().getString(
										R.string.loyalty_label),
										getResources()
										.getString(
												R.string.NetworkActivity_ProgressBar_Message));
				progressDialog
				.setOnKeyListener(new DialogInterface.OnKeyListener() {

					@Override
					public boolean onKey(DialogInterface dialog,
							int keyCode, KeyEvent event) {
						if (keyCode == KeyEvent.KEYCODE_BACK) {
							progressDialog.cancel();
							LoyaltyBecomeScreenFourActivity.this
							.finish();
							return true;
						}
						return false;
					}
				});
				new AddNewMemberTask().execute(Variables.getMember());
				Log.v("Poslednja strana", Variables.getMember().vehicleBrand + " "+Variables.getMember().vehicleType+" "+Variables.getMember().registrationPlate+" "+Variables.getMember().vehicleColor+" "+
						Variables.getMember().yearOfProduction+" "+Variables.getMember().insuranceCompany+" "+Variables.getMember().isSuperMembership);
			}
			else
				layoutManager.showError(R.string.become_loyalty_pick_type_of_loyalty_error_text);
			}
	}
	private boolean checkIfFieldsAreEmpty(){
		if(getVehicleTrademark()==null){
			layoutManager.showError(R.string.become_loyalty_marka_vozila_error_text);
			return true;
		}
		if(getVehicleType()==null){
			layoutManager.showError(R.string.become_loyalty_tip_vozila_error_text);
			return true;
		}
		if(getRegistrationPlate()==null){
			layoutManager.showError(R.string.become_loyalty_registarska_oznaka_error_text);
			return true;
		}
		if(getVehicleColor()==null){
			layoutManager.showError(R.string.become_loyalty_boja_vozila_error_text);
			return true;
		}
		if(getVehicleYear()==null){
			layoutManager.showError(R.string.become_loyalty_godina_proizvodnje_error_text);
			return true;
		}
		if(getVehicleInsuranceCompany()==null){
			layoutManager.showError(R.string.become_loyalty_osiguravajuce_drustvo_error_text);
			return true;
		}
		return false;
	}
	private class AddNewMemberTask extends
	AsyncTask<Member, Void, Boolean> {

		protected Boolean doInBackground(Member... member) {
			Boolean success = false;
			String response;
			try {
				response = WebMethods.addNewMember(member[0]);
				success = Boolean.parseBoolean(WebResponseParser.parseSingleResponse(response));

			} catch (ClientProtocolException e) {
				e.printStackTrace();
				LoyaltyBecomeScreenFourActivity.this.getLayoutManager()
				.showError(R.string.Loading_ClientProtocolException);
			} catch (IOException e) {
				e.printStackTrace();
				LoyaltyBecomeScreenFourActivity.this.getLayoutManager()
				.showError(R.string.Loading_IOException);
			} catch (Exception e) {
				e.printStackTrace();
				LoyaltyBecomeScreenFourActivity.this.getLayoutManager()
				.showError(R.string.Loading_Exception);
			}
			return success;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}

		protected void onPostExecute(Boolean result) {

			if (result){ 
				layoutManager.showInfo(R.string.become_loyalty_successffull_text);
				new ActivityHelper(LoyaltyBecomeScreenFourActivity.this).startActivityWithFlag(LoyaltyActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
			}else 
				getLayoutManager().showWarning(R.string.become_loyalty_sending_data_error_text);
			progressDialog.cancel();
		}
	}

}
