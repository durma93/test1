package rs.org.amss.shell;

import java.util.ArrayList;

import rs.org.amss.R;
import rs.org.amss.core.PaymentInvoiceAdapter;
import rs.org.amss.core.Variables;
import rs.org.amss.model.GetFonts;
import rs.org.amss.model.PaymentInvoice;
import net.lmggroup.utility.ActivityHelper;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CalculateRegistrationResultActivity extends BaseActivity {

	public static final String INTENT_EXTRA = "calculateResult";

	protected ListView listRegistrationResult;
	protected PaymentInvoiceAdapter listAdapter;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_registration_calculate_result);
		setHomeAction(R.drawable.ic_main_info, R.string.info_calculator_result_text, InfoActivity.class);

		checkIsLogIn(CalculateRegistrationResultActivity.this);
		RelativeLayout infoBox = (RelativeLayout) findViewById(R.id.info_box);
		listRegistrationResult = (ListView) findViewById(R.id.listRegistrationResult);
		@SuppressWarnings("unchecked")
		ArrayList<PaymentInvoice> items = (ArrayList<PaymentInvoice>) getIntentExtra(INTENT_EXTRA);

		listAdapter = new PaymentInvoiceAdapter(this, items);
		listRegistrationResult.setAdapter(listAdapter);
		if (Variables.registrationInfo.levelOfPreviousPremiumPolicy == 2 && Variables.registrationInfo.numberOfDamages == 0)
			infoBox.setVisibility(View.VISIBLE);
		else
			infoBox.setVisibility(View.GONE);

		double sum = 0;
		for (PaymentInvoice invoice : items)
			sum += invoice.amount;

		setTitle(String.format("%s (%d rsd)", getString(R.string.info_calculator_result_text), (int)sum));

		((TextView) findViewById(R.id.title)).setText("KALKULATOR REGISTRACIJE");
		((TextView) findViewById(R.id.subtitle)).setText("/ REZULTAT");
		((TextView) findViewById(R.id.totalAmount)).setText("Ukupno: " + (int)sum + ",00");

		setTypefaces();

		findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
	}

	private void setTypefaces() {
		TextView title = (TextView)findViewById(R.id.title);
		TextView notice_text = (TextView)findViewById(R.id.notice_text);
		TextView totalAmount = (TextView)findViewById(R.id.totalAmount);
		Button calculate = (Button)findViewById(R.id.calculate);

		title.setTypeface(GetFonts.getTypeface(CalculateRegistrationResultActivity.this));
		notice_text.setTypeface(GetFonts.getTypeface(CalculateRegistrationResultActivity.this));
		calculate.setTypeface(GetFonts.getBoldTypeface(CalculateRegistrationResultActivity.this));
		totalAmount.setTypeface(GetFonts.getBoldTypeface(CalculateRegistrationResultActivity.this));
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

	public void CalculatePrecisely(View v) {
		new ActivityHelper(CalculateRegistrationResultActivity.this).startActivity(CalculateRegistrationScreenTwoActivity.class);
	}
}
