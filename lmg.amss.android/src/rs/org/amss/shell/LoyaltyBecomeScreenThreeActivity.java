package rs.org.amss.shell;

import rs.org.amss.R;
import rs.org.amss.core.Variables;
import rs.org.amss.model.GetFonts;
import net.lmggroup.utility.ActivityHelper;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoyaltyBecomeScreenThreeActivity extends BaseActivity {
	EditText edit_address, edit_postal, edit_place, edit_telephone,
	edit_mobile, edit_email;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		memoryManager.loadVariables();

		setContentView(R.layout.become_loyalty_screen_three);
		String title = getString(R.string.loyalty_become_partner_text) + "/"
				+ getString(R.string.become_loyalty_values_text);
		setHomeAction(R.drawable.ic_membership, title,
				LoyaltyActivity.class);
		checkIsLogIn(LoyaltyBecomeScreenThreeActivity.this);

		TextView heading = (TextView) findViewById(R.id.heading);
		heading.setTypeface(GetFonts
				.getBoldTypeface(LoyaltyBecomeScreenThreeActivity.this));

		edit_address = (EditText) findViewById(R.id.edit_address);
		edit_postal = (EditText) findViewById(R.id.edit_postalCode);
		edit_place = (EditText) findViewById(R.id.edit_place);
		edit_telephone = (EditText) findViewById(R.id.edit_telephone);
		edit_mobile = (EditText) findViewById(R.id.edit_mobile_telephone);
		edit_email = (EditText) findViewById(R.id.edit_e_mail);

		edit_address.setTypeface(GetFonts
				.getTypeface(LoyaltyBecomeScreenThreeActivity.this));
		edit_postal.setTypeface(GetFonts
				.getTypeface(LoyaltyBecomeScreenThreeActivity.this));
		edit_place.setTypeface(GetFonts
				.getTypeface(LoyaltyBecomeScreenThreeActivity.this));
		edit_telephone.setTypeface(GetFonts
				.getTypeface(LoyaltyBecomeScreenThreeActivity.this));
		edit_mobile.setTypeface(GetFonts
				.getTypeface(LoyaltyBecomeScreenThreeActivity.this));
		edit_email.setTypeface(GetFonts
				.getTypeface(LoyaltyBecomeScreenThreeActivity.this));

	}

	private String getAddress() {
		if (edit_address.getText().toString().length() > 0)
			return edit_address.getText().toString();
		return null;
	}

	private String getPostalCode() {
		if (edit_postal.getText().toString().length() > 0)
			return edit_postal.getText().toString();
		return null;
	}

	private String getPlace() {
		if (edit_place.getText().toString().length() > 0)
			return edit_place.getText().toString();
		return null;
	}

	private String getTelephone() {
		if (edit_telephone.getText().toString().length() > 0)
			return edit_telephone.getText().toString();
		return null;
	}

	private String getMobile() {
		if (edit_mobile.getText().toString().length() > 0)
			return edit_mobile.getText().toString();
		return null;
	}

	private String getEmail() {
		if (edit_email.getText().toString().length() > 0)
			return edit_email.getText().toString();
		return null;
	}

	private boolean checkIfFieldIsEmpty() {
		if (getAddress() == null) {
			layoutManager.showError(R.string.become_loyalty_adresa_error_text);
			return true;
		}
		if (getPostalCode() == null) {
			layoutManager.showError(R.string.become_loyalty_postanski_broj_error_text);
		}
		if (getPlace() == null){
			layoutManager.showError(R.string.become_loyalty_mesto_error_text);
			return true;
		}
		if (getTelephone() == null) {
			layoutManager.showError(R.string.become_loyalty_telefon_error_text);
			return true;
		}
		if (getMobile() == null) {
			layoutManager.showError(R.string.become_loyalty_mobilni_telefon_error_text);
			return true;
		}
		if (getEmail() == null) {
			layoutManager.showError(R.string.become_loyalty_email_error_text);
			return true;
		}
		if (!isValidEmail(getEmail())) {
			layoutManager
			.showError(R.string.become_loyalty_wrong_email_format_error_text);
			return true;
		}
		return false;
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

	public void GoNext(View v) {
		if (!checkIfFieldIsEmpty()) {
			Variables.getMember().address = getAddress();
			Variables.getMember().postCode = getPostalCode();
			Variables.getMember().city = getPlace();
			Variables.getMember().telephone = getTelephone();
			Variables.getMember().mobilePhone = getMobile();
			Variables.getMember().email = getEmail();
			new ActivityHelper(LoyaltyBecomeScreenThreeActivity.this).startActivity(LoyaltyBecomeScreenFourActivity.class);
		}
	}

	public final static boolean isValidEmail(CharSequence target) {
		if (target == null)
			return false;
		return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();

	}
}
