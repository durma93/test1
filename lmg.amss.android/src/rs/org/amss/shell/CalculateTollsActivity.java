package rs.org.amss.shell;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import rs.org.amss.R;
import rs.org.amss.core.TollCityAdapter;
import rs.org.amss.core.Variables;
import rs.org.amss.core.WebMethods;
import rs.org.amss.core.WebResponseParser;
import rs.org.amss.model.GetFonts;
import rs.org.amss.model.Toll;
import rs.org.amss.model.TollCity;

import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CalculateTollsActivity extends BaseActivity {

	private TollCityAdapter spinnerAdapter;
	private Spinner spinnerFrom;
	private Spinner spinnerTo;
	private LinearLayout layoutResult;
	private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_tolls_calculate);
		setHomeAction(R.drawable.ic_main_info, R.string.info_tolls_calculate_text, InfoActivity.class);

		((TextView)findViewById(R.id.title)).setText(R.string.info_for_vehicle_owners_title);
		((TextView)findViewById(R.id.subtitle)).setText("/ " + getString(R.string.info_tolls_text).toUpperCase(Locale.GERMANY));

		checkIsLogIn(CalculateTollsActivity.this);
		spinnerFrom = (Spinner) findViewById(R.id.spinnerCityFrom);
		spinnerTo = (Spinner) findViewById(R.id.spinnerCityTo);
		layoutResult = (LinearLayout) findViewById(R.id.layoutResult);

		initializeCalculate();
		findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
		((Button)findViewById(R.id.buttonCalculate)).setTypeface(GetFonts.getBoldTypeface(this));
	}

	public void Calculate(View view) {

		progressDialog = ProgressDialog.show(CalculateTollsActivity.this,
				getResources().getString(R.string.info_tolls_calculate_text),
				getResources().getString(R.string.Calculation_text));
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					progressDialog.cancel();
					CalculateTollsActivity.this.finish();
					return true;
				}
				return false;
			}
		});
		new CalculateTollPriceTask().execute();

	}

	private void initializeCalculate() {
		progressDialog = ProgressDialog.show(
				CalculateTollsActivity.this,
				getResources().getString(R.string.info_tolls_calculate_text),
				getResources().getString(
						R.string.NetworkActivity_ProgressBar_Message));
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					progressDialog.cancel();
					CalculateTollsActivity.this.finish();
					return true;
				}
				return false;
			}
		});
		new GetTollCitiesTask().execute();
	}

	private TollCity getFromCity() {

		return (TollCity) spinnerFrom.getSelectedItem();
	}

	private TollCity getToCity() {

		return (TollCity) spinnerTo.getSelectedItem();
	}

	private class GetTollCitiesTask extends
	AsyncTask<Object, Void, ArrayList<TollCity>> {

		protected ArrayList<TollCity> doInBackground(Object... parameters) {
			ArrayList<TollCity> items = Variables.tollCities;
			if (items == null || items.size() == 0) {
				String response;
				try {
					response = WebMethods.getTollCities();
					items = WebResponseParser.getTollCityList(response);

					Variables.tollCities = items;

				} catch (ClientProtocolException e) {
					e.printStackTrace();
					CalculateTollsActivity.this.getLayoutManager().showError(
							R.string.Loading_ClientProtocolException);
				} catch (IOException e) {
					e.printStackTrace();
					CalculateTollsActivity.this.getLayoutManager().showError(
							R.string.Loading_IOException);
				} catch (Exception e) {
					e.printStackTrace();
					CalculateTollsActivity.this.getLayoutManager().showError(
							R.string.Loading_Exception);
				}
			}
			return items;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}

		protected void onPostExecute(ArrayList<TollCity> result) {

			if (result != null && result.size() > 0) {

				spinnerAdapter = new TollCityAdapter(
						CalculateTollsActivity.this, result);
				spinnerFrom.setAdapter(spinnerAdapter);
				spinnerTo.setAdapter(spinnerAdapter);
			} else {
				getLayoutManager().showWarning(
						R.string.error_info_tolls_calculate_cities);
			}

			progressDialog.cancel();
		}
	}

	private class CalculateTollPriceTask extends AsyncTask<Object, Void, Toll> {

		protected Toll doInBackground(Object... parameters) {

			Toll result = null;
			String response;
			try {

				TollCity fromCity = getFromCity();
				TollCity toCity = getToCity();

				response = WebMethods.calculateTollPrice(fromCity.id,
						fromCity.group, toCity.id, toCity.group);
				result = WebResponseParser.getToll(response);

			} catch (ClientProtocolException e) {
				e.printStackTrace();
				CalculateTollsActivity.this.getLayoutManager().showError(
						R.string.Loading_ClientProtocolException);
			} catch (IOException e) {
				e.printStackTrace();
				CalculateTollsActivity.this.getLayoutManager().showError(
						R.string.Loading_IOException);
			} catch (Exception e) {
				e.printStackTrace();
				CalculateTollsActivity.this.getLayoutManager().showError(
						R.string.Loading_Exception);
			}
			return result;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}

		protected void onPostExecute(Toll result) {

			if (result != null) {

				TextView textIRSD = (TextView) findViewById(R.id.textCategoryIRSDPrice);
				TextView textIIRSD = (TextView) findViewById(R.id.textCategoryIIRSDPrice);
				TextView textIIIRSD = (TextView) findViewById(R.id.textCategoryIIIRSDPrice);
				TextView textIVRSD = (TextView) findViewById(R.id.textCategoryIVRSDPrice);

				TextView textIEUR = (TextView) findViewById(R.id.textCategoryIEURPrice);
				TextView textIIEUR = (TextView) findViewById(R.id.textCategoryIIEURPrice);
				TextView textIIIEUR = (TextView) findViewById(R.id.textCategoryIIIEURPrice);
				TextView textIVEUR = (TextView) findViewById(R.id.textCategoryIVEURPrice);

				DecimalFormat format = new DecimalFormat("#.##");

				textIRSD.setText(format.format(result.categoryIPriceRSD));
				textIIRSD.setText(format.format(result.categoryIIPriceRSD));
				textIIIRSD.setText(format.format(result.categoryIIIPriceRSD));
				textIVRSD.setText(format.format(result.categoryIVPriceRSD));

				textIEUR.setText(format.format(result.categoryIPriceEUR));
				textIIEUR.setText(format.format(result.categoryIIPriceEUR));
				textIIIEUR.setText(format.format(result.categoryIIIPriceEUR));
				textIVEUR.setText(format.format(result.categoryIVPriceEUR));

				layoutResult.setVisibility(View.VISIBLE);
			} else {
				getLayoutManager().showWarning(
						R.string.error_info_tolls_calculate);
			}

			progressDialog.cancel();
		}
	}
}
