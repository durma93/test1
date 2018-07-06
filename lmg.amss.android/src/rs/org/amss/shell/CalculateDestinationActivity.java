package rs.org.amss.shell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import rs.org.amss.R;
import rs.org.amss.core.TollCityAdapter;
import rs.org.amss.core.Variables;
import rs.org.amss.core.WebMethods;
import rs.org.amss.core.WebResponseParser;
import rs.org.amss.model.CityDistance;
import rs.org.amss.model.GetFonts;
import rs.org.amss.model.TollCity;
import org.apache.http.client.ClientProtocolException;

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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class CalculateDestinationActivity extends BaseActivity {

	private TollCityAdapter spinnerFromAdapter, spinnerToAdapter;
	private Spinner spinnerFrom;
	private Spinner spinnerTo;
	private TextView distance;
	ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_distance_calculate);
		setHomeAction(R.drawable.ic_main_info, R.string.info_destination_text, InfoActivity.class);

		checkIsLogIn(CalculateDestinationActivity.this);
		findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});

		((TextView)findViewById(R.id.title)).setText(R.string.info_for_vehicle_owners_title);
		((TextView)findViewById(R.id.subtitle)).setText("/ " + getString(R.string.info_destination_text).toUpperCase(Locale.GERMANY));

		spinnerFrom = (Spinner) findViewById(R.id.spinnerCityFrom);
		spinnerTo = (Spinner) findViewById(R.id.spinnerCityTo);
		
		distance = (TextView) findViewById(R.id.distance);
		
		Button calculate = (Button)findViewById(R.id.buttonCalculate);
		calculate.setTypeface(GetFonts.getBoldTypeface(CalculateDestinationActivity.this));
		
		initializeFromCities();
	}

	public void Calculate(View view) {

		// showProgress();
		progressDialog = ProgressDialog.show(CalculateDestinationActivity.this,
				getResources().getString(R.string.info_destination_text),
				getResources().getString(R.string.Calculation_text));
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					progressDialog.cancel();
					CalculateDestinationActivity.this.finish();
					return true;
				}
				return false;
			}
		});
		new CalculateTollPriceTask().execute();

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

	private void initializeFromCities() {
		progressDialog = ProgressDialog.show(
				CalculateDestinationActivity.this,
				getResources().getString(R.string.info_destination_text),
				getResources().getString(
						R.string.NetworkActivity_ProgressBar_Message));
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					progressDialog.cancel();
					CalculateDestinationActivity.this.finish();
					return true;
				}
				return false;
			}
		});
		new GetDestinationCitiesTask().execute();
	}

	private void initializeToFromCities() {
		progressDialog = ProgressDialog.show(
				CalculateDestinationActivity.this,
				getResources().getString(R.string.info_destination_text),
				getResources().getString(
						R.string.NetworkActivity_ProgressBar_Message));
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					progressDialog.cancel();
					CalculateDestinationActivity.this.finish();
					return true;
				}
				return false;
			}
		});
		new GetToDestinationCitiesTask().execute();
	}

	private TollCity getFromCity() {

		return (TollCity) spinnerFrom.getSelectedItem();
	}

	private TollCity getToCity() {
		return (TollCity) spinnerTo.getSelectedItem();
	}

	private class GetDestinationCitiesTask extends
			AsyncTask<Object, Void, ArrayList<TollCity>> {

		protected ArrayList<TollCity> doInBackground(Object... parameters) {
			ArrayList<TollCity> items = Variables.destinationCities;
			if (items == null || items.size() == 0) {
				String response;
				try {
					response = WebMethods.getCities();
					items = WebResponseParser.getDestinationCityList(response);
					Variables.destinationCities = items;

				} catch (ClientProtocolException e) {
					e.printStackTrace();
					CalculateDestinationActivity.this
							.getLayoutManager()
							.showError(R.string.Loading_ClientProtocolException);
				} catch (IOException e) {
					e.printStackTrace();
					CalculateDestinationActivity.this.getLayoutManager()
							.showError(R.string.Loading_IOException);
				} catch (Exception e) {
					e.printStackTrace();
					CalculateDestinationActivity.this.getLayoutManager()
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

		protected void onPostExecute(ArrayList<TollCity> result) {

			if (result != null && result.size() > 0) {
				spinnerFromAdapter = new TollCityAdapter(
						CalculateDestinationActivity.this, result);
				spinnerFrom.setAdapter(spinnerFromAdapter);
				spinnerFrom
						.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								initializeToFromCities();
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {

							}
						});
			} else {
				getLayoutManager().showWarning(
						R.string.error_info_tolls_calculate_cities);
			}
			progressDialog.cancel();
			// hideProgress();
		}
	}

	private class GetToDestinationCitiesTask extends
			AsyncTask<Object, Void, ArrayList<TollCity>> {

		protected ArrayList<TollCity> doInBackground(Object... parameters) {
			ArrayList<TollCity> items = new ArrayList<TollCity>();
			if (items == null || items.size() == 0) {
				String response;
				TollCity fromCity = getFromCity();
				try {
					response = WebMethods.getDestinationList(fromCity.id);
					items = WebResponseParser.getDestinationCityList(response);
					Variables.destinationToCities = items;

				} catch (ClientProtocolException e) {
					e.printStackTrace();
					CalculateDestinationActivity.this
							.getLayoutManager()
							.showError(R.string.Loading_ClientProtocolException);
				} catch (IOException e) {
					e.printStackTrace();
					CalculateDestinationActivity.this.getLayoutManager()
							.showError(R.string.Loading_IOException);
				} catch (Exception e) {
					e.printStackTrace();
					CalculateDestinationActivity.this.getLayoutManager()
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

		protected void onPostExecute(ArrayList<TollCity> result) {

			if (result != null && result.size() > 0) {
				spinnerToAdapter = new TollCityAdapter(
						CalculateDestinationActivity.this, result);
				spinnerTo.setAdapter(spinnerToAdapter);
			} else {
				getLayoutManager().showWarning(
						R.string.error_info_tolls_calculate_cities);
			}
			progressDialog.cancel();
			// hideProgress();
		}
	}

	private class CalculateTollPriceTask extends
			AsyncTask<Object, Void, CityDistance> {

		protected CityDistance doInBackground(Object... parameters) {

			CityDistance result = null;
			String response;
			try {

				TollCity toCity = getToCity();

				response = WebMethods.getDistance(toCity.id);
				result = WebResponseParser.getDistance(response);

			} catch (ClientProtocolException e) {
				e.printStackTrace();
				CalculateDestinationActivity.this.getLayoutManager().showError(
						R.string.Loading_ClientProtocolException);
			} catch (IOException e) {
				e.printStackTrace();
				CalculateDestinationActivity.this.getLayoutManager().showError(
						R.string.Loading_IOException);
			} catch (Exception e) {
				e.printStackTrace();
				CalculateDestinationActivity.this.getLayoutManager().showError(
						R.string.Loading_Exception);
			}
			return result;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}

		protected void onPostExecute(CityDistance result) {

			if (result != null) {
				distance.setText("Broj kilometara: " + result.km + "\nRuta: "
						+ result.route);
				Log.v("CalculateDistance", "Distance: " + result.id + " "
						+ result.km + " " + result.route);
			} else {
				getLayoutManager().showWarning(
						R.string.error_info_tolls_calculate);
			}
			progressDialog.cancel();
			// hideProgress();
		}
	}
}
