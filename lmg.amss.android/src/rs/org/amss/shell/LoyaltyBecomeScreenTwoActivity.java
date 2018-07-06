package rs.org.amss.shell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.http.client.ClientProtocolException;

import rs.org.amss.R;
import rs.org.amss.core.Variables;
import rs.org.amss.core.WebMethods;
import rs.org.amss.core.WebResponseParser;
import rs.org.amss.model.GetFonts;
import net.lmggroup.utility.ActivityHelper;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class LoyaltyBecomeScreenTwoActivity extends BaseActivity {
	public final static boolean INTENT_EXTRA = false;
	boolean isIndividual;
	private ProgressDialog progressDialog;

	EditText edit_name, edit_surname, edit_jmbg, edit_companyName,
	edit_memberName, edit_memberSurname, edit_pib, edit_membershipID;
	LinearLayout individual, company, membership;
	CheckBox oldMember_cb;
	Spinner serial;
	TextView serial_text, heading;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		memoryManager.loadVariables();

		setContentView(R.layout.become_loyalty_screen_two);
		String title = getString(R.string.loyalty_become_partner_text) + "/"
				+ getString(R.string.become_loyalty_values_text);
		setHomeAction(R.drawable.ic_membership, title,
				LoyaltyActivity.class);
		checkIsLogIn(LoyaltyBecomeScreenTwoActivity.this);

		isIndividual = (Boolean) getIntentExtra("isIndividual");

		TextView oldMember = (TextView) findViewById(R.id.old_member_text);
		Button next = (Button)findViewById(R.id.next);
		next.setTypeface(GetFonts.getTypeface(LoyaltyBecomeScreenTwoActivity.this));
		
		heading = (TextView) findViewById(R.id.heading);
		serial_text = (TextView) findViewById(R.id.serial_text);

		oldMember.setTypeface(GetFonts
				.getTypeface(LoyaltyBecomeScreenTwoActivity.this));
		heading.setTypeface(GetFonts
				.getBoldTypeface(LoyaltyBecomeScreenTwoActivity.this));
		serial_text.setTypeface(GetFonts
				.getTypeface(LoyaltyBecomeScreenTwoActivity.this));

		individual = (LinearLayout) findViewById(R.id.individual);
		company = (LinearLayout) findViewById(R.id.company);
		membership = (LinearLayout) findViewById(R.id.membership);
		oldMember_cb = (CheckBox) findViewById(R.id.old_member);
		
		if (isIndividual) {
			individual.setVisibility(View.VISIBLE);
			company.setVisibility(View.GONE);
			edit_name = (EditText) findViewById(R.id.edit_name);
			edit_surname = (EditText) findViewById(R.id.edit_surname);
			edit_jmbg = (EditText) findViewById(R.id.edit_jmbg);
			edit_name.setTypeface(GetFonts
					.getTypeface(LoyaltyBecomeScreenTwoActivity.this));
			edit_surname.setTypeface(GetFonts
					.getTypeface(LoyaltyBecomeScreenTwoActivity.this));
			edit_jmbg.setTypeface(GetFonts
					.getTypeface(LoyaltyBecomeScreenTwoActivity.this));

		} else {
			individual.setVisibility(View.GONE);
			company.setVisibility(View.VISIBLE);
			edit_companyName = (EditText) findViewById(R.id.edit_company_name);
			edit_memberName = (EditText) findViewById(R.id.edit_member_name);
			edit_memberSurname = (EditText) findViewById(R.id.edit_member_surname);
			edit_pib = (EditText) findViewById(R.id.edit_pib);
			edit_companyName.setTypeface(GetFonts
					.getTypeface(LoyaltyBecomeScreenTwoActivity.this));
			edit_memberName.setTypeface(GetFonts
					.getTypeface(LoyaltyBecomeScreenTwoActivity.this));
			edit_memberSurname.setTypeface(GetFonts
					.getTypeface(LoyaltyBecomeScreenTwoActivity.this));
			edit_pib.setTypeface(GetFonts
					.getTypeface(LoyaltyBecomeScreenTwoActivity.this));
		}

		oldMember_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					membership.setVisibility(View.VISIBLE);
					serial = (Spinner) findViewById(R.id.serial);
					edit_membershipID = (EditText) findViewById(R.id.membership_id);
					edit_membershipID.setTypeface(GetFonts
							.getTypeface(LoyaltyBecomeScreenTwoActivity.this));
					progressDialog = ProgressDialog
							.show(LoyaltyBecomeScreenTwoActivity.this,
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
								LoyaltyBecomeScreenTwoActivity.this
								.finish();
								return true;
							}
							return false;
						}
					});
					new GetSerialsTask().execute();
				} else
					membership.setVisibility(View.GONE);
			}
		});

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		LinearLayout background = (LinearLayout) findViewById(R.id.background);

		// Checks the orientation of the screen
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			background.setBackgroundResource(R.drawable.walpaper_light_land);
		} else
			background.setBackgroundResource(R.drawable.walpaper_light_copy);
	}

	private class GetSerialsTask extends
	AsyncTask<String, Void, ArrayList<String>> {

		protected ArrayList<String> doInBackground(String... membershipCardId) {
			ArrayList<String> items = new ArrayList<String>();
			String response;
			try {
				response = WebMethods.getSerials();
				items = WebResponseParser.getStringValues(response);
				Collections.reverse(items);

			} catch (ClientProtocolException e) {
				e.printStackTrace();
				LoyaltyBecomeScreenTwoActivity.this.getLayoutManager()
				.showError(R.string.Loading_ClientProtocolException);
			} catch (IOException e) {
				e.printStackTrace();
				LoyaltyBecomeScreenTwoActivity.this.getLayoutManager()
				.showError(R.string.Loading_IOException);
			} catch (Exception e) {
				e.printStackTrace();
				LoyaltyBecomeScreenTwoActivity.this.getLayoutManager()
				.showError(R.string.Loading_Exception);
			}
			return items;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}

		protected void onPostExecute(ArrayList<String> result) {

			if (result != null && result.size() > 0) {

				ArrayAdapter<String> serialsAdapter = new ArrayAdapter<String>(
						LoyaltyBecomeScreenTwoActivity.this,
						android.R.layout.simple_spinner_item,
						result.toArray(new String[result.size()]));
				serial.setAdapter(serialsAdapter);
			} else {
				getLayoutManager().showWarning(R.string.error_login_serials);
			}

			progressDialog.cancel();
		}
	}

	private String getSpinnerValue() {
		return (String) serial.getSelectedItem();
	}

	private String getMembershipId() {
		if (edit_membershipID.getText().toString().length() > 0)
			return edit_membershipID.getText().toString();
		return null;
	}

	private String getName() {
		if (edit_name.getText().toString().length() > 0)
			return edit_name.getText().toString();
		return null;
	}

	private String getSurname() {
		if (edit_surname.getText().toString().length() > 0)
			return edit_surname.getText().toString();
		return null;
	}

	private String getJMBG() {
		if (edit_jmbg.getText().toString().length() > 0)
			return edit_jmbg.getText().toString();
		return null;
	}

	private String getCompanyName() {
		if (edit_companyName.getText().toString().length() > 0)
			return edit_companyName.getText().toString();
		return null;
	}

	private String getCompanyNameMember() {
		if (edit_memberName.getText().toString().length() > 0)
			return edit_memberName.getText().toString();
		return null;
	}

	private String getCompanySurnameMember() {
		if (edit_memberSurname.getText().toString().length() > 0)
			return edit_memberSurname.getText().toString();
		return null;
	}

	private String getCompanyPIB() {
		if (edit_pib.getText().toString().length() > 0)
			return edit_pib.getText().toString();
		return null;
	}

	public void GoNext(View v) {
		if (isIndividual && !checkIfIndividualsFieldIsEmpty()) {
			if(oldMember_cb.isChecked()){
				Variables.getMember().oldMembershipID = getMembershipId();
				Variables.getMember().oldMembershipSerial = getSpinnerValue();
			}
			Variables.getMember().isIndividual = true;
			Variables.getMember().firstName = getName();
			Variables.getMember().lastName = getSurname();
			Variables.getMember().personalNumber = getJMBG();
			new ActivityHelper(LoyaltyBecomeScreenTwoActivity.this).startActivity(LoyaltyBecomeScreenThreeActivity.class);
		}
		if (!isIndividual && !checkIfCompanyFieldsIsEmpty()) {
			if(oldMember_cb.isChecked()){
				Variables.getMember().oldMembershipID = getMembershipId();
				Variables.getMember().oldMembershipSerial = getSpinnerValue();
			}
			Variables.getMember().isIndividual = false;
			Variables.getMember().companyName = getCompanyName();
			Variables.getMember().firstName = getCompanyNameMember();
			Variables.getMember().lastName = getCompanySurnameMember();
			Variables.getMember().pib = getCompanyPIB();
			new ActivityHelper(LoyaltyBecomeScreenTwoActivity.this).startActivity(LoyaltyBecomeScreenThreeActivity.class);
		}
	}

	private boolean checkIfIndividualsFieldIsEmpty() {
		if (oldMember_cb.isChecked() && getMembershipId() == null) {
			layoutManager.showError(R.string.become_loyalty_unesite_broj_kartice_error_text);
			return true;
		}
		if (getName() == null) {
			layoutManager.showError(R.string.become_loyalty_name_error_text);
			return true;
		}
		if (getSurname() == null) {
			layoutManager.showError(R.string.become_loyalty_prezime_error_text);
			return true;
		}
		if (getJMBG() == null) {
			layoutManager.showError(R.string.become_loyalty_jmbg_error_text);
			return true;
		}
		if (getJMBG().length() < 13) {
			layoutManager.showError("Nedovoljan broj karaktera u polju JMBG!");
			return true;
		}
		return false;
	}

	private boolean checkIfCompanyFieldsIsEmpty() {
		if (getCompanyName() == null) {
			layoutManager.showError(R.string.become_loyalty_company_name_error_text);
			return true;
		}
		if (getCompanyNameMember() == null) {
			layoutManager.showError(R.string.become_loyalty_company_person_name_error_text);
			return true;
		}
		if (getCompanySurnameMember() == null) {
			layoutManager
			.showError(R.string.become_loyalty_company_person_surname_error_text);
			return true;
		}
		if (getCompanyPIB() == null) {
			layoutManager.showError(R.string.become_loyalty_company_pib_error_text);
			return true;
		}
		if (getCompanyPIB().length() < 9) {
			layoutManager.showError("Nedovoljan broj karaktera u polju PIB!");
			return true;
		}
		return false;
	}

}
