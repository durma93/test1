package rs.org.amss.shell;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import rs.org.amss.R;
import rs.org.amss.core.LoginStep1ItemAdapter;
import rs.org.amss.core.LoginStep2ItemAdapter;
import rs.org.amss.core.WebMethods;
import rs.org.amss.core.WebResponseParser;
import rs.org.amss.model.GetFonts;
import rs.org.amss.model.LoginStep1;
import rs.org.amss.model.LoginStep2;

import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class LoginActivity extends BaseActivity {

	public static final String TAG = "SignInActivity";
	protected String membershipCardId;
	protected String name;
	protected String registrationPlate;
	private int currentStep = 1;
	private int cardImageId = R.drawable.login_card_default;

	private Spinner spinnerSerials;
	private EditText textMembershipCardId;
	private LinearLayout layoutStep2;
	private LinearLayout layoutStep3;
	private ProgressDialog progressDialog;
	private ImageView card;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		card = (ImageView) findViewById(R.id.card);
		layoutStep2 = (LinearLayout) findViewById(R.id.layoutStep2);
		layoutStep3 = (LinearLayout) findViewById(R.id.layoutStep3);	
		spinnerSerials = (Spinner)findViewById(R.id.spinnerSerials);
		textMembershipCardId = (EditText) findViewById(R.id.textMembershipCardId);
		textMembershipCardId.setTypeface(GetFonts.getTypeface(LoginActivity.this));

		findViewById(R.id.button_hint_card_series).setOnClickListener(showHintClickListener);
		findViewById(R.id.button_hint_card_number).setOnClickListener(showHintClickListener);

		Button confirm = (Button)findViewById(R.id.buttonSubmit);
		confirm.setTypeface(GetFonts.getBoldTypeface(LoginActivity.this));

		findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});

		initiateLogin();
	}

	private void initiateLogin(){
		progressDialog = ProgressDialog.show(LoginActivity.this, getResources().getString(R.string.LoginActivity_Label), getResources().getString(R.string.NetworkActivity_ProgressBar_Message));
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					progressDialog.cancel();
					LoginActivity.this.finish();
					return true;
				}
				return false;
			}
		});
		new GetSerialsTask().execute("");
	}

	public void Submit(View view){
		switch (currentStep){
		case 1:ProcessStep1(view);
		break;
		case 2:ProcessStep2(view);
		break;
		case 3:ProcessStep3(view);
		break;
		}		
	}

	protected String getMembershipCardId(){

		String serial = (String)spinnerSerials.getSelectedItem();
		String id = textMembershipCardId.getText().toString().trim();
		for (int i = id.length(); i < 8; i++) {
			id = "0" + id;
		}

		return String.format("%s_%s", id, serial);
	}

	protected String getName(){
		return getLoginStep1().getName();
	}

	protected String getRegistrationPlate(){
		return getLoginStep2().registrationPlate;
	}

	protected LoginStep1 getLoginStep1(){
		Spinner spinnerLoginStep1 = (Spinner) findViewById(R.id.spinnerLoginStep1);
		return (LoginStep1)spinnerLoginStep1.getSelectedItem();
	}

	protected LoginStep2 getLoginStep2(){
		Spinner spinnerLoginStep2 = (Spinner) findViewById(R.id.spinnerLoginStep2);
		return (LoginStep2)spinnerLoginStep2.getSelectedItem();
	}

	private void ProcessStep1(View view){

		String membershipCardId = getMembershipCardId();
		progressDialog = ProgressDialog.show(LoginActivity.this, getResources().getString(R.string.LoginActivity_Label), getResources().getString(R.string.NetworkActivity_ProgressBar_Message));
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					progressDialog.cancel();
					LoginActivity.this.finish();
					return true;
				}
				return false;
			}
		});
		new LoginStep1Task().execute(membershipCardId);

	}

	private void ProcessStep2(View view){

		if (getLoginStep1().isValid){
			progressDialog = ProgressDialog.show(LoginActivity.this, getResources().getString(R.string.LoginActivity_Label), getResources().getString(R.string.NetworkActivity_ProgressBar_Message));
			progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_BACK) {
						progressDialog.cancel();
						LoginActivity.this.finish();
						return true;
					}
					return false;
				}
			});
			String membershipCardId = getMembershipCardId();
			new LoginStep2Task().execute(membershipCardId);
		}
		else{
			handleUserNotAuthorized();
			getLayoutManager().showWarning(R.string.error_login_step2);
		}

	}

	private void ProcessStep3(View view){

		if (getLoginStep2().isValid){
			progressDialog = ProgressDialog.show(LoginActivity.this, getResources().getString(R.string.LoginActivity_Label), getResources().getString(R.string.SafetyActivity_Loading_Text));
			progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_BACK) {
						progressDialog.cancel();
						LoginActivity.this.finish();
						return true;
					}
					return false;
				}
			});
			String membershipCardId = getMembershipCardId();
			new LoginStep3Task().execute(membershipCardId);
		}
		else {
			handleUserNotAuthorized();
			getLayoutManager().showWarning(R.string.error_login_step3);
		}
	}

	private void handleUserNotAuthorized(){

		currentStep = 1;
		textMembershipCardId.setText("");
		layoutStep2.setVisibility(View.GONE);
		layoutStep3.setVisibility(View.GONE);
	}

	private View.OnClickListener showHintClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			((ImageView)findViewById(R.id.button_hint_card_number)).setImageResource(R.drawable.show_hint_off);
			((ImageView)findViewById(R.id.button_hint_card_series)).setImageResource(R.drawable.show_hint_off);

			if (view.getId() == R.id.button_hint_card_number) {
				if (cardImageId != R.drawable.login_card_number_highlighted) {
					cardImageId = R.drawable.login_card_number_highlighted;
					((ImageView)view).setImageResource(R.drawable.show_hint_on);
				} else {
					cardImageId = R.drawable.login_card_default;
					((ImageView)view).setImageResource(R.drawable.show_hint_off);
				}
			} else if (view.getId() == R.id.button_hint_card_series) {
				if (cardImageId != R.drawable.login_card_type_highlighted) {
					cardImageId = R.drawable.login_card_type_highlighted;
					((ImageView)view).setImageResource(R.drawable.show_hint_on);
				} else {
					cardImageId = R.drawable.login_card_default;
					((ImageView)view).setImageResource(R.drawable.show_hint_off);
				}
			}

			card.setImageResource(cardImageId);
		}
	};

	private class GetSerialsTask extends AsyncTask<String, Void, ArrayList<String>> {			

		protected ArrayList<String> doInBackground(String... membershipCardId) {
			ArrayList<String> items = new ArrayList<String>();
			String response;
			try {
				response = WebMethods.getSerials((membershipCardId[0])); 				
				items = WebResponseParser.getStringValues(response);
				Collections.reverse(items);

			} catch (ClientProtocolException e) {
				e.printStackTrace();
				LoginActivity.this.getLayoutManager().showError(R.string.Loading_ClientProtocolException);
			} catch (IOException e) {
				e.printStackTrace();
				LoginActivity.this.getLayoutManager().showError(R.string.Loading_IOException);
			} catch (Exception e) {
				e.printStackTrace();
				LoginActivity.this.getLayoutManager().showError(R.string.Loading_Exception);
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

				ArrayAdapter<String> serialsAdapter = new ArrayAdapter<String>(LoginActivity.this, R.layout.spinner_item, result.toArray(new String[result.size()]));
				spinnerSerials.setAdapter(serialsAdapter);

				textMembershipCardId.setVisibility(View.VISIBLE);
			}
			else{
				getLayoutManager().showWarning(R.string.error_login_serials);
			}

			progressDialog.cancel();
		}
	}

	private class LoginStep1Task extends AsyncTask<String, Void, ArrayList<LoginStep1>> {			

		protected ArrayList<LoginStep1> doInBackground(String... membershipCardId) {
			ArrayList<LoginStep1> items = new ArrayList<LoginStep1>();
			String response;
			try {
				response = WebMethods.getLoginStep1(membershipCardId[0]); 				
				items = WebResponseParser.getLoginStep1List(response);

			} catch (ClientProtocolException e) {
				e.printStackTrace();
				LoginActivity.this.getLayoutManager().showError(R.string.Loading_ClientProtocolException);
			} catch (IOException e) {
				e.printStackTrace();
				LoginActivity.this.getLayoutManager().showError(R.string.Loading_IOException);
			} catch (Exception e) {
				e.printStackTrace();
				LoginActivity.this.getLayoutManager().showError(R.string.Loading_Exception);
			}
			return items;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}
		protected void onPostExecute(ArrayList<LoginStep1> result) {

			if (result != null && result.size() > 0){ 
				Spinner spinnerLoginStep1 = (Spinner) findViewById(R.id.spinnerLoginStep1);

				final LoginStep1ItemAdapter step1Adapter = new LoginStep1ItemAdapter(LoginActivity.this, result);
				spinnerLoginStep1.setAdapter(step1Adapter);

				layoutStep2.setVisibility(View.VISIBLE);

				currentStep = 2;
			}
			else{
				handleUserNotAuthorized();
				getLayoutManager().showWarning(R.string.error_login_step1);
			}

			progressDialog.cancel();
		}
	}

	private class LoginStep2Task extends AsyncTask<String, Void, ArrayList<LoginStep2>> {			

		protected ArrayList<LoginStep2> doInBackground(String... membershipCardId) {
			ArrayList<LoginStep2> items = new ArrayList<LoginStep2>();
			String response;
			try {
				response = WebMethods.getLoginStep2(membershipCardId[0]);
				items = WebResponseParser.getLoginStep2List(response);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				LoginActivity.this.getLayoutManager().showError(R.string.Loading_ClientProtocolException);
			} catch (IOException e) {
				e.printStackTrace();
				LoginActivity.this.getLayoutManager().showError(R.string.Loading_IOException);
			} catch (Exception e) {
				e.printStackTrace();
				LoginActivity.this.getLayoutManager().showError(R.string.Loading_Exception);
			}
			return items;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}
		protected void onPostExecute(ArrayList<LoginStep2> result) {
			if (result != null && result.size() > 0){
				Spinner spinnerLoginStep2 = (Spinner) findViewById(R.id.spinnerLoginStep2);
				final LoginStep2ItemAdapter step2Adapter = new LoginStep2ItemAdapter(LoginActivity.this, result);
				spinnerLoginStep2.setAdapter(step2Adapter);

				layoutStep3.setVisibility(View.VISIBLE);

				currentStep = 3;

			}
			else
				handleUserNotAuthorized();	

			progressDialog.cancel();
		}
	}

	private class LoginStep3Task extends AsyncTask<String, Void, Integer> {			

		protected Integer doInBackground(String... membershipCardId) {
			int membershipId = -1;
			String response;
			try {
				response = WebMethods.getMembershipId(membershipCardId[0]);
				membershipId = WebResponseParser.getMembershipId(response);					

			} catch (ClientProtocolException e) {
				e.printStackTrace();
				LoginActivity.this.getLayoutManager().showError(R.string.Loading_ClientProtocolException);
			} catch (IOException e) {
				e.printStackTrace();
				LoginActivity.this.getLayoutManager().showError(R.string.Loading_IOException);
			} catch (Exception e) {
				e.printStackTrace();
				LoginActivity.this.getLayoutManager().showError(R.string.Loading_Exception);
			}
			return membershipId;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}
		protected void onPostExecute(Integer result) {

			if (result > 0){
				memoryManager.saveMembershipId(result);
				memoryManager.saveMembershipCardId(getMembershipCardId());
				memoryManager.saveRegistrationPlate(getRegistrationPlate());

				Intent intent = new Intent(LoginActivity.this, MembershipActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				LoginActivity.this.startActivity(intent);
				finish();
			}
			else
				handleUserNotAuthorized();				

			progressDialog.cancel();
		}
	}
	
	public void showSignIn(View view){
	}

}
