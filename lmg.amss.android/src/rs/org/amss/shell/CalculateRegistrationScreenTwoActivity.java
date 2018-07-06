package rs.org.amss.shell;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import rs.org.amss.R;
import rs.org.amss.core.PaymentInvoiceAdapter;
import rs.org.amss.core.Variables;
import rs.org.amss.core.WebMethods;
import rs.org.amss.core.WebResponseParser;
import rs.org.amss.model.CalculateRegistrationRequest;
import rs.org.amss.model.GetFonts;
import rs.org.amss.model.PaymentInvoice;

public class CalculateRegistrationScreenTwoActivity extends BaseActivity {

	protected PaymentInvoiceAdapter listAdapter;
	protected EditText textPolicy;
	protected EditText textClaim;
	protected ProgressDialog progressDialog;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_registration_calculate_screen2);
		setHomeAction(R.drawable.ic_main_info, R.string.info_calculator_text, CalculateRegistrationRequest.class);

		checkIsLogIn(CalculateRegistrationScreenTwoActivity.this);

		((TextView) findViewById(R.id.title)).setText("KALKULATOR REGISTRACIJE");
		((TextView) findViewById(R.id.subtitle)).setText("/ REZULTAT / DETALJI");


		textPolicy = (EditText) findViewById(R.id.textPolicy);
		textClaim = (EditText) findViewById(R.id.textClaim);
		
		Button calculate = (Button)findViewById(R.id.calculate);
		calculate.setTypeface(GetFonts.getBoldTypeface(CalculateRegistrationScreenTwoActivity.this));
		textPolicy.setTypeface(GetFonts.getTypeface(CalculateRegistrationScreenTwoActivity.this));
		textClaim.setTypeface(GetFonts.getTypeface(CalculateRegistrationScreenTwoActivity.this));

		findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
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

	private int getPolicy() {
		String text = textPolicy.getText().toString().trim();
		return Integer.parseInt(text);
	}

	private int getClaimNumber() {
		String text = textClaim.getText().toString().trim();
		if (text.length() == 0)
			return 0;
		return Integer.parseInt(text);
	}

	public void CalculatePrecisely(View view) {
		progressDialog = ProgressDialog.show(
				CalculateRegistrationScreenTwoActivity.this, getResources()
				.getString(R.string.info_calculator_text),
				getResources().getString(R.string.SafetyActivity_Loading_Text));
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					progressDialog.cancel();
					CalculateRegistrationScreenTwoActivity.this.finish();
					return true;
				}
				return false;
			}
		});
		new CalculateRegistrationTask().execute();
	}

	public class CalculateRegistrationTask extends AsyncTask<Object, Void, ArrayList<PaymentInvoice>> {

		protected ArrayList<PaymentInvoice> doInBackground(Object... parameters) {
			ArrayList<PaymentInvoice> result = null;
			String response;

			try {
				Variables.registrationInfo.levelOfPreviousPremiumPolicy = getPolicy();
				Variables.registrationInfo.numberOfDamages = getClaimNumber();
				CalculateRegistrationRequest request = Variables.registrationInfo;
				try {
					if (request.levelOfPreviousPremiumPolicy < 1 || request.levelOfPreviousPremiumPolicy > 12) {
						CalculateRegistrationScreenTwoActivity.this.getLayoutManager().showWarning(R.string.error_info_registration_calculate_policydata);
						request = null;
					}

				} catch (Exception e) {
					request = null;
					e.printStackTrace();
					CalculateRegistrationScreenTwoActivity.this.getLayoutManager().showWarning(R.string.error_info_registration_calculate_data);
				}

				if (request != null) {
					response = WebMethods.calculateRegistration(request);
					result = WebResponseParser.getPaymentInvoiceList(response);
				}

			} catch (ClientProtocolException e) {
				e.printStackTrace();
				CalculateRegistrationScreenTwoActivity.this.getLayoutManager()
				.showError(R.string.Loading_ClientProtocolException);
			} catch (IOException e) {
				e.printStackTrace();
				CalculateRegistrationScreenTwoActivity.this.getLayoutManager()
				.showError(R.string.Loading_IOException);
			} catch (Exception e) {
				e.printStackTrace();
				CalculateRegistrationScreenTwoActivity.this.getLayoutManager()
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
				CalculateRegistrationScreenTwoActivity.this
				.getActivityManager()
				.startActivityWithParam(CalculateRegistrationResultActivity.class, CalculateRegistrationResultActivity.INTENT_EXTRA, result);
			}
			progressDialog.cancel();
		}
	}

}
