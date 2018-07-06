package rs.org.amss.shell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rs.org.amss.R;
import rs.org.amss.core.AmsoNetworkGridViewAdapter;
import rs.org.amss.core.AmsoNetworkGridViewAdapter.AmsoNetworkGridAdapterListener;
import rs.org.amss.core.MapPopupAdapter;
import rs.org.amss.core.Variables;
import rs.org.amss.core.WebMethods;
import rs.org.amss.core.WebResponseParser;
import rs.org.amss.model.AmsoStation;
import rs.org.amss.model.AmsoStationFilter;
import rs.org.amss.model.GetFonts;

import org.apache.http.client.ClientProtocolException;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.markupartist.android.widget.ActionBar.AbstractAction;

public class AMSONetworkActivity extends BaseMapActivity {

	public final static String INTENT_EXTRA = "filter";
	protected AmsoNetworkGridViewAdapter adapter;
	protected AdapterListener adapterListener;
	protected MapListener mapListener;
	protected boolean optionsShown;
	protected boolean filterItemsPreselected = false;
	protected AmsoStationFilter filter;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		adapterListener = new AdapterListener();
		mapListener = new MapListener();
		super.onCreate(savedInstanceState);
		setLocationListenerEnabled(false);
		setMapLoadedListener(mapListener);
		setContentView(R.layout.amso_network);
		setHomeAction(R.drawable.ic_amso, R.string.AMSONetworkActivity_Label);
		checkIsLogIn(AMSONetworkActivity.this);
		filter = (AmsoStationFilter)getIntentExtra(INTENT_EXTRA);
		adapter = new AmsoNetworkGridViewAdapter(this, filter);
		Button showAll = (Button)findViewById(R.id.buttonShow);
		showAll.setTypeface(GetFonts.getTypeface(AMSONetworkActivity.this));

		ImageView backButton = (ImageView) findViewById(R.id.back_button);
		backButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});

		setOptionsMenu();
		setUpStations();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	public void showAllStations(View view){
		filter = new AmsoStationFilter();
		adapter = new AmsoNetworkGridViewAdapter(this, filter);
		adapter.setListener(adapterListener);
		GridView gridView = (GridView) findViewById(R.id.gridVewNetworkOptions);
		gridView.setAdapter(adapter);
		adapterListener.filterChangedReceived();
		setFilterViewVisibility(View.GONE);
	}

	private void setOptionsMenu()
	{
		GridView gridView = (GridView) findViewById(R.id.gridVewNetworkOptions);
		adapter.setListener(adapterListener);
		gridView.setAdapter(adapter);
	}

	private void setUpStations(){
		progressDialog = ProgressDialog.show(AMSONetworkActivity.this, getResources().getString(R.string.AMSO_sales_text), getResources().getString(R.string.NetworkActivity_ProgressBar_Message));
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					progressDialog.cancel();
					AMSONetworkActivity.this.finish();
					return true;
				}
				return false;
			}
		});

		new GetStationsTask().execute();
	}

	private void setFilterViewVisibility(int visibility){
		LinearLayout filterView = (LinearLayout) findViewById(R.id.layoutNetworkOptions);
		filterView.setVisibility(visibility);
		optionsShown = visibility == View.VISIBLE;
	}

	private class ToggleOptionsAction extends AbstractAction {

		public ToggleOptionsAction(int arrow) {
			super(arrow);
		}

		@Override
		public void performAction(View view) {

			LinearLayout filterView = (LinearLayout) findViewById(R.id.layoutNetworkOptions);
			if (optionsShown){
				filterView.setVisibility(View.GONE);
				actionBar.removeAction(ToggleOptionsAction.this);
				addAction(new ToggleOptionsAction(android.R.drawable.arrow_down_float));
			}
			else{
				filterView.setVisibility(View.VISIBLE);
				actionBar.removeAction(ToggleOptionsAction.this);
				addAction(new ToggleOptionsAction(android.R.drawable.arrow_up_float));
			}
			optionsShown = !optionsShown;
		}
	}

	private class GetStationsTask extends AsyncTask<Object, Void, ArrayList<AmsoStation>> {			

		protected ArrayList<AmsoStation> doInBackground(Object... params) {
			ArrayList<AmsoStation> items = Variables.insuranceStationList;
			if (items == null || items.size() == 0){
				items = new ArrayList<AmsoStation>();
				String response;
				try {
					response = WebMethods.getAllAmsoSubstationsForMap();
					items = WebResponseParser.getAmsoStationList(response);
					Variables.insuranceStationList = items;
				} catch (ClientProtocolException e) {
					e.printStackTrace();
					AMSONetworkActivity.this.getLayoutManager().showError(R.string.error_connection);
				} catch (IOException e) {
					e.printStackTrace();
					AMSONetworkActivity.this.getLayoutManager().showError(R.string.error_connection);
				} catch (Exception e) {
					e.printStackTrace();
					AMSONetworkActivity.this.getLayoutManager().showError(R.string.error_connection);
				}
			}
			return items;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}

		protected void onPostExecute(ArrayList<AmsoStation> result) {
			if (result != null && result.size() > 0){
				for (AmsoStation station : result){
					if(station.houseNumber==null || station.houseNumber.length()==0)
						station.houseNumber = "";
					if(station.hasAutomobileLiability)
						station.automobileLiability = getString(R.string.amso_network_auto_liability)+",";
					if(station.hasGreenCard)
						station.greenCard =  getString(R.string.amso_network_green_card)+",";
					if(station.hasCarAccident)
						station.carAccident =  getString(R.string.amso_network_car_accident)+",";
					if(station.hasKasko)
						station.kasko =  getString(R.string.amso_network_kasko)+",";
					if(station.hasKaskoLight)
						station.kaskoLight =  getString(R.string.amso_network_kasko_light)+",";
					if(station.hasMembership)
						station.membership =  getString(R.string.amso_network_membership)+",";
					if(station.hasVoluntaryHealthInsurance)
						station.healthInsurance =  getString(R.string.amso_network_voluntary_health_insurance)+",";
					if(station.hasInternationalDocuments)
						station.internationalDocuments =  getString(R.string.amso_network_international_documents)+",";
					String possibilities = station.automobileLiability+" "+station.greenCard+" "+station.carAccident+" "+
							station.kasko+" "+station.kaskoLight+" "+station.membership+" "+
							station.healthInsurance+" "+station.internationalDocuments;
					String possibilities_tmp = possibilities.replaceAll("  ", "");
					possibilities = possibilities_tmp.substring(0, possibilities_tmp.length()-1);
					addMarkerInvisible(station.latitude, station.longitude, station.name, station.street + " " + station.houseNumber + ", " + station.townName + "\nTelefon: "+station.phone + "\nRadno vreme: "+station.workingTime+"\n"+
							possibilities, R.drawable.amso_small, false);
				}
			}

			setInfoAdapter(new MapPopupAdapter(AMSONetworkActivity.this));
			adapterListener.filterChangedReceived();
			addAction(new ToggleOptionsAction(android.R.drawable.arrow_down_float));
			progressDialog.cancel();
		}
	}

	private class MapListener implements MapLoadedListener{

		@Override
		public void mapLoadedReceived() {
			Log.d(TAG, "Maps loaded");
		}
	}

	private class AdapterListener implements AmsoNetworkGridAdapterListener{

		protected List<AmsoStation> filterList;

		protected Handler finishedHandler = new Handler() {
			@Override public void handleMessage(Message msg) {

				hideMarkers();
				filterList.removeAll(Collections.singleton(null)); 
				for (AmsoStation station : filterList){
					setMarkerVisible(station.name, true);
					animateTo(station.latitude, station.longitude, MULTI_MARKER_CAMERA_ZOOM_FACTOR);
				}
				setZoom(previousZoomLevel);

				//				setZoom(MULTI_MARKER_CAMERA_ZOOM_FACTOR);
				hideProgress();
			}
		};

		@Override
		public void filterChangedReceived() {
			showProgress();
			new Thread(new Runnable() {
				@SuppressWarnings("unchecked")
				public void run() {

					filterList = (List<AmsoStation>) Variables.insuranceStationList.clone();
					AmsoStationFilter filter = adapter.getFilter();
					if (filter != null){

						for (AmsoStation item : Variables.insuranceStationList){
							boolean removeFromFilter = checkRegion(item);
							removeFromFilter |= checkMunicipality(item);
							removeFromFilter |= checkAutomobileLiability(item, filter);
							removeFromFilter |= checkGreenCard(item, filter);
							removeFromFilter |= checkCarAccident(item, filter);
							removeFromFilter |= checkKasko(item, filter);
							removeFromFilter |= checkKaskoLight(item, filter);
							removeFromFilter |= checkVoluntaryHealthInsurance(item, filter);
							removeFromFilter |= checkMembership(item, filter);
							removeFromFilter |= checkInternationalDocuments(item, filter);

							if (removeFromFilter)
								filterList.remove(item);

						}
					}

					finishedHandler.sendEmptyMessage(0);
				}
			}).start();		

		}

		private boolean checkRegion(AmsoStation item){

			boolean result = false;
			String region = filter.regionName;
			if (region != null && region.trim() != "" && !region.equals(AMSONetworkFilterActivity.EMPTY_SELECTED_VALUE)){
				result = !item.regionName.equals(region);
			}
			return result;
		}

		private boolean checkMunicipality(AmsoStation item){
			boolean result = false;
			String municipality = filter.municipalityName;
			if (municipality != null && municipality.trim() != "" && !municipality.equals(AMSONetworkFilterActivity.EMPTY_SELECTED_VALUE)){
				result = !item.municipalityName.equals(municipality);
			}
			return result;
		}

		private boolean checkAutomobileLiability(AmsoStation item, AmsoStationFilter filter){
			boolean result = false;
			if (filter.hasAutomobileLiability){
				result = filter.hasAutomobileLiability != item.hasAutomobileLiability;
			}
			return result;
		}

		private boolean checkGreenCard(AmsoStation item, AmsoStationFilter filter){
			boolean result = false;
			if (filter.hasGreenCard){
				result = filter.hasGreenCard != item.hasGreenCard;
			}
			return result;
		}

		private boolean checkCarAccident(AmsoStation item, AmsoStationFilter filter){
			boolean result = false;
			if (filter.hasCarAccident){
				result = filter.hasCarAccident != item.hasCarAccident;
			}
			return result;
		}

		private boolean checkKasko(AmsoStation item, AmsoStationFilter filter){
			boolean result = false;
			if (filter.hasKasko){
				result = filter.hasKasko != item.hasKasko;
			}
			return result;
		}

		private boolean checkKaskoLight(AmsoStation item, AmsoStationFilter filter){
			boolean result = false;
			if (filter.hasKaskoLight){
				result = filter.hasKaskoLight != item.hasKaskoLight;
			}
			return result;
		}

		private boolean checkVoluntaryHealthInsurance(AmsoStation item, AmsoStationFilter filter){
			boolean result = false;
			if (filter.hasVoluntaryHealthInsurance){
				result = filter.hasVoluntaryHealthInsurance != item.hasVoluntaryHealthInsurance;
			}
			return result;
		}

		private boolean checkMembership(AmsoStation item, AmsoStationFilter filter){
			boolean result = false;
			if (filter.hasMembership){
				result = filter.hasMembership != item.hasMembership;
			}
			return result;
		}

		private boolean checkInternationalDocuments(AmsoStation item, AmsoStationFilter filter){
			boolean result = false;
			if (filter.hasInternationalDocuments){
				result = filter.hasInternationalDocuments != item.hasInternationalDocuments;
			}
			return result;
		}

		//		private boolean checkAdditionalServices(AmsoStation item, AmsoStationFilter filter){
		//			boolean result = false;
		//			if (filter.hasAdditionalServices){
		//				result = filter.hasAdditionalServices != item.hasAdditionalServices;
		//			}
		//			return result;
		//		}

	}
}
