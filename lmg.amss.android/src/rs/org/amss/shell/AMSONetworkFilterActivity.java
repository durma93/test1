package rs.org.amss.shell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.http.client.ClientProtocolException;

import rs.org.amss.R;
import rs.org.amss.core.Variables;
import rs.org.amss.core.WebMethods;
import rs.org.amss.core.WebResponseParser;
import rs.org.amss.model.AmsoStationFilter;
import rs.org.amss.model.GetFonts;
import rs.org.amss.view.ListViewDialog;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

public class AMSONetworkFilterActivity extends BaseActivity {

	private TextView spinnerRegions;
	private TextView spinnerMunicipalities;
	protected final static String EMPTY_SELECTED_VALUE = "-";
	private ArrayList<String> regions;
	private ArrayList<String> municipalities;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.amso_network_filter);
		checkIsLogIn(AMSONetworkFilterActivity.this);
		String title = getString(R.string.AMSONetworkActivity_Label);
		
		spinnerRegions = (TextView)findViewById(R.id.spinnerRegions);
		spinnerMunicipalities = (TextView) findViewById(R.id.spinnerMunicipalities);

		spinnerRegions.setText(EMPTY_SELECTED_VALUE);
		spinnerMunicipalities.setText(EMPTY_SELECTED_VALUE);

		SetUpTypefaces();
		setUpRegions();

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ((TextView) findViewById(R.id.title)).setText("PRODAJNA MREŽA");
        ((TextView) findViewById(R.id.subtitle)).setText("/ Obavezno osiguranje vozila / Prodajna mreža");

        spinnerRegions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListViewDialog dialog = new ListViewDialog(regions, AMSONetworkFilterActivity.this, regionSelectedListener);
                FragmentManager fragmentManager = getSupportFragmentManager();
                dialog.show(fragmentManager, "Activity");
            }
        });

        spinnerMunicipalities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (municipalities == null) {
                    AMSONetworkFilterActivity.this.getLayoutManager().showError("Odaberite prvo region");
                    return;
                }

                ListViewDialog dialog = new ListViewDialog(municipalities, AMSONetworkFilterActivity.this, municipalitySelectedListener);
                FragmentManager fragmentManager = getSupportFragmentManager();
                dialog.show(fragmentManager, "Activity");
            }
        });
	}

	private AdapterView.OnItemClickListener regionSelectedListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			String regionName = regions.get(position);
			spinnerRegions.setText(regionName);
			spinnerMunicipalities.setText(EMPTY_SELECTED_VALUE);
			municipalities = null;
			if (!regionName.equals(EMPTY_SELECTED_VALUE)){
				loadMunicipalities(regionName);
			}
		}
	};

	private AdapterView.OnItemClickListener municipalitySelectedListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            String municipalityName = municipalities.get(position);
            spinnerMunicipalities.setText(municipalityName);
        }
    };

	private void SetUpTypefaces(){
		Button buttonShow = (Button)findViewById(R.id.buttonShow);
		TextView textRegions = (TextView)findViewById(R.id.textRegions);
		TextView textMunicipalities = (TextView)findViewById(R.id.textMunicipalities);

		buttonShow.setTypeface(GetFonts.getBoldTypeface(AMSONetworkFilterActivity.this));
		buttonShow.setText(buttonShow.getText().toString().toUpperCase());
//		textRegions.setTypeface(GetFonts.getTypeface(AMSONetworkFilterActivity.this));
//		textMunicipalities.setTypeface(GetFonts.getTypeface(AMSONetworkFilterActivity.this));
	}

	public void showStations(View view){
		String region = getRegion();
		if (!region.equals(EMPTY_SELECTED_VALUE)){
			AmsoStationFilter filter = new AmsoStationFilter();
			filter.regionName = region;
			String municipality = getMunicipality();
			if (!municipality.equals(EMPTY_SELECTED_VALUE))
				filter.municipalityName = municipality;
			getActivityManager().startActivityWithParam(AMSONetworkActivity.class, AMSONetworkActivity.INTENT_EXTRA, filter);
		}
		else
			getLayoutManager().showWarning(R.string.warning_amso_regions);
	}

	protected String getRegion() {
		return spinnerRegions.getText().toString();
	}

	protected String getMunicipality(){
	    return spinnerMunicipalities.getText().toString();
	}

	private void loadMunicipalities(String regionName){
		progressDialog = ProgressDialog.show(AMSONetworkFilterActivity.this, getResources().getString(R.string.amso_network_municipalities_prompt), getResources().getString(R.string.NetworkActivity_ProgressBar_Message));
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					progressDialog.cancel();
					AMSONetworkFilterActivity.this.finish();
					return true;
				}
				return false;
			}
		});

		new GetMunicipalitiesTask().execute(regionName);
	}

	private void setUpRegions(){
		progressDialog = ProgressDialog.show(AMSONetworkFilterActivity.this, getResources().getString(R.string.amso_network_regions_prompt), getResources().getString(R.string.NetworkActivity_ProgressBar_Message));
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					progressDialog.cancel();
					Variables.regions = null;
					AMSONetworkFilterActivity.this.finish();
					return true;
				}
				return false;
			}
		});

		new GetRegionsTask().execute();
	}

	private class GetRegionsTask extends AsyncTask<Object, Void, ArrayList<String>> {			

		protected ArrayList<String> doInBackground(Object... params) {
			ArrayList<String> items = Variables.regions;
			if (items == null || items.size() == 0){
				items = new ArrayList<String>();
				String response;
				try {
					response = WebMethods.getAllRegions();
					items = WebResponseParser.getStringValues(response);
					Collections.sort(items);						
					items.add(0, EMPTY_SELECTED_VALUE);
					Variables.regions = items;
				} catch (ClientProtocolException e) {
					e.printStackTrace();
					AMSONetworkFilterActivity.this.getLayoutManager().showError(R.string.error_connection);
				} catch (IOException e) {
					e.printStackTrace();
					AMSONetworkFilterActivity.this.getLayoutManager().showError(R.string.error_connection);
				} catch (Exception e) {
					e.printStackTrace();
					AMSONetworkFilterActivity.this.getLayoutManager().showError(R.string.error_connection);
				}
			}
			return items;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}

		protected void onPostExecute(final ArrayList<String> result) {
			regions = result;
			progressDialog.cancel();
		}
	}

	private class GetMunicipalitiesTask extends AsyncTask<String, Void, ArrayList<String>> {			

		protected ArrayList<String> doInBackground(String... parameters) {
			ArrayList<String> items = new ArrayList<String>();
			String response;
			try {
				response = WebMethods.getRegionMunicipalities(parameters[0]); 				
				items = WebResponseParser.getStringValues(response);
				Collections.sort(items);						
				items.add(0, EMPTY_SELECTED_VALUE);

			} catch (ClientProtocolException e) {
				e.printStackTrace();
				AMSONetworkFilterActivity.this.getLayoutManager().showError(R.string.error_connection);
			} catch (IOException e) {
				e.printStackTrace();
				AMSONetworkFilterActivity.this.getLayoutManager().showError(R.string.error_connection);
			} catch (Exception e) {
				e.printStackTrace();
				AMSONetworkFilterActivity.this.getLayoutManager().showError(R.string.error_connection);
			}
			return items;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}

		protected void onPostExecute(ArrayList<String> result) {
            municipalities = result;
			//ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AMSONetworkFilterActivity.this, android.R.layout.simple_spinner_item, result);
			//dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			//spinnerMunicipalities.setAdapter(dataAdapter);
			progressDialog.cancel();
		}
	}
}
