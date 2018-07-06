package rs.org.amss.shell;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.http.client.ClientProtocolException;

import com.markupartist.android.widget.ActionBar.AbstractAction;

import rs.org.amss.R;
import rs.org.amss.core.MapPopupAdapter;
import rs.org.amss.core.NetworkLocationsListAdapter;
import rs.org.amss.core.Variables;
import rs.org.amss.core.WebMethods;
import rs.org.amss.core.WebResponseParser;
import rs.org.amss.model.AmssStation;
import rs.org.amss.model.AmssStationFilter;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class NetworkActivity extends BaseMapActivity implements View.OnClickListener{
	public final static String INTENT_EXTRA = "filter";
	public final static String INTERNATIONAL_DOCUMENTS_FILTER = "INTERNATIONAL_DOCUMENTS_FILTER";

	View mapButton;
    View listButton;

	protected ProgressDialog progressDialog;
	protected NetworkLocationsListAdapter adapter;
	protected MapListener mapListener;
	protected boolean filterItemsPreselected = false;
	public AmssStationFilter filter;
	private int currentCategoryDrawableId = -1;
	private ListView categories;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		mapListener = new MapListener();
		super.onCreate(savedInstanceState);
		setMapLoadedListener(mapListener);
		setContentView(R.layout.network);
		setHomeAction(R.drawable.ic_network, R.string.NetworkActivity_Label);
		checkIsLogIn(NetworkActivity.this);

        mapButton = findViewById(R.id.mapButton);
        listButton = findViewById(R.id.listButton);

        findViewById(R.id.back_button).setOnClickListener(this);
        mapButton.setOnClickListener(this);
        listButton.setOnClickListener(this);
		listButton.setBackgroundResource(R.drawable.primary_button);
        mapButton.setBackgroundColor(0x00000000);
		//filter = (AmssStationFilter)getIntentExtra(INTENT_EXTRA);

		categories = (ListView)findViewById(R.id.listViewCategories);
		categories.setAdapter(new NetworkCategoriesListAdapter());
		categories.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if (Variables.stationList == null) {
					NetworkActivity.this.getLayoutManager().showError(R.string.amso_network_locations_unavailable);
					return;
				}

				categories.setVisibility(View.GONE);
				ListView locations = (ListView)findViewById(R.id.listViewLocations);
				locations.setVisibility(View.VISIBLE);

				ArrayList<AmssStation> stations = new ArrayList<AmssStation>();

				Integer[] imageIds = (new NetworkCategoriesListAdapter()).mImageIds;

				for (AmssStation s : Variables.stationList) {
					s.iconDrawableId = imageIds[position];
					if (position == 0) stations.add(s);
					else if (position == 1 && s.isForInternationalDocuments) stations.add(s);
					else if (position == 2 && s.isForTechnicalReview) stations.add(s);
					else if (position == 3 && s.isForAmssMembership) stations.add(s);
					else if (position == 4 && s.isForRegistration) stations.add(s);
					else if (position == 5 && s.isForInsurance) stations.add(s);
					else if (position == 6 && s.isForHelpOnRoad) stations.add(s);
					else if (position == 7 && s.isForTowing) stations.add(s);
					else if (position == 8 && s.hasDrivingSchool) stations.add(s);
					else if (position == 9 && s.isForVehicleInspectionAttest) stations.add(s);
					else if (position == 10 && s.hasCarService) stations.add(s);
					else if (position == 11 && s.hasCurrencyExchange) stations.add(s);
					else if (position == 12 && s.hasTNGPump) stations.add(s);
					else if (position == 13 && s.hasCarWash) stations.add(s);
					else if (position == 14 && s.isForTourism) stations.add(s);
					else if (position == 15 && s.hasSalesAndComplementOfTAGToll) stations.add(s);
					else if (position == 16 && s.hasShop) stations.add(s);
					else if (position == 17 && s.hasRestaurant) stations.add(s);
					else if (position == 18 && s.isForSpedition) stations.add(s);
				}
				
				NetworkLocationsListAdapter adapter = new NetworkLocationsListAdapter(NetworkActivity.this, stations);
				locations.setAdapter(adapter);
				
				hideMarkers();
				for (AmssStation station : stations) {
					setMarkerVisible(station.name, true, station.iconDrawableId);
				}
			}
		});
		
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		ListView categories = (ListView) findViewById(R.id.listViewCategories);
		ListView locations = (ListView) findViewById(R.id.listViewLocations);
		if (keyCode == KeyEvent.KEYCODE_BACK && locations.getVisibility() == View.VISIBLE) {
			locations.setVisibility(View.GONE);
			categories.setVisibility(View.VISIBLE);
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	public void setUpStations(){

		progressDialog = ProgressDialog.show(NetworkActivity.this, getResources().getString(R.string.NetworkActivity_ProgressBar_Title), getResources().getString(R.string.NetworkActivity_ProgressBar_Message));
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					progressDialog.cancel();
					NetworkActivity.this.finish();
					return true;
				}
				return false;
			}
		});

		new GetStationsTask().execute();
	}

    @Override
    public void onClick(View view) {
        ListView categories = (ListView) findViewById(R.id.listViewCategories);
        ListView locations = (ListView) findViewById(R.id.listViewLocations);
        switch (view.getId()){
            case R.id.mapButton:
				view.setBackgroundResource(R.drawable.primary_button);
				setLocationListenerEnabled(true);
                animateToCurrentLocation();
                locations.setVisibility(View.GONE);
                categories.setVisibility(View.GONE);
                findViewById(R.id.listButton).setBackgroundColor(0x00000000);
                break;
            case R.id.listButton:
				view.setBackgroundResource(R.drawable.primary_button);
				locations.setVisibility(View.VISIBLE);
                categories.setVisibility(View.VISIBLE);
                findViewById(R.id.mapButton).setBackgroundColor(0x00000000);
                break;
            case R.id.back_button:
				if (locations.getVisibility() == View.VISIBLE) {
					locations.setVisibility(View.GONE);
					categories.setVisibility(View.VISIBLE);
					break;
				}

                onBackPressed();
                break;
        }
    }

	public int getDrawableId(AmssStation station) {
//        if (station.isForInternationalDocuments) return R.drawable.medjdokumenta;
//        else if (station.isForTechnicalReview) return R.drawable.tehnickipregled;
//        else if (station.isForAmssMembership) return R.drawable.clanstvo;
//        else if (station.isForRegistration) return R.drawable.registracija;
//        else if (station.isForInsurance) return R.drawable.osiguranje;
//        else if (station.isForHelpOnRoad) return R.drawable.pomocnaputu;
//        else if (station.isForTowing) return R.drawable.slepovanje;
//        else if (station.hasDrivingSchool) return R.drawable.autoskola;
//        else if (station.isForVehicleInspectionAttest) return R.drawable.atesti;
//        else if (station.hasCarService) return R.drawable.servis;
//        else if (station.hasCurrencyExchange) return R.drawable.menjacnica;
//        else if (station.hasTNGPump) return R.drawable.autogas;
//        else if (station.hasCarWash) return R.drawable.perionica;
//        else if (station.isForTourism) return R.drawable.turizam;
//        else if (station.hasSalesAndComplementOfTAGToll) return R.drawable.tag;
//        else if (station.hasShop) return R.drawable.prodavnica;
//        else if (station.hasRestaurant) return R.drawable.ressoran;
//        else if (station.isForSpedition) return R.drawable.spedicija;
		return R.drawable.pin_station;
	}

	private class ToggleOptionsAction extends AbstractAction {
		public ToggleOptionsAction(int arrow) {
			super(arrow);
		}
		@Override
		public void performAction(View view) {
			ListView categories = (ListView) findViewById(R.id.listViewCategories);
			ListView locations = (ListView) findViewById(R.id.listViewLocations);
			if (locations.getVisibility() == View.VISIBLE || categories.getVisibility() == View.VISIBLE){
				locations.setVisibility(View.GONE);
				categories.setVisibility(View.GONE);
				actionBar.removeAction(ToggleOptionsAction.this);
				addAction(new ToggleOptionsAction(android.R.drawable.ic_menu_search));
			} else {
				locations.setVisibility(View.VISIBLE);
				categories.setVisibility(View.VISIBLE);
				actionBar.removeAction(ToggleOptionsAction.this);
				addAction(new ToggleOptionsAction(android.R.drawable.ic_menu_mapmode));
			}
		}
	}

	private class GetStationsTask extends AsyncTask<Object, Void, ArrayList<AmssStation>> {			

		protected ArrayList<AmssStation> doInBackground(Object... params) {
			ArrayList<AmssStation> items = Variables.stationList;

			if (items == null || items.size() == 0){
				items = new ArrayList<AmssStation>();
				String response;
				try {
					response = WebMethods.getAllSubstationsForMap();
					items = WebResponseParser.getStationList(response);

					Variables.stationList = items;	

				} catch (ClientProtocolException e) {
					e.printStackTrace();
					NetworkActivity.this.getLayoutManager().showError(R.string.Loading_ClientProtocolException);
				} catch (IOException e) {
					e.printStackTrace();
					NetworkActivity.this.getLayoutManager().showError(R.string.Loading_IOException);
				} catch (Exception e) {
					e.printStackTrace();
					NetworkActivity.this.getLayoutManager().showError(R.string.Loading_Exception);
				}
			}
			Collections.sort(items, new DistanceComparator());
			return items;	
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}

		protected void onPostExecute(final ArrayList<AmssStation> result) {
			if (result != null && result.size() > 0) {
				for (AmssStation station : result) {
                    int drawableId = getDrawableId(station);
                    Log.d("MILOS", "ID: "+drawableId);
					addMarker(station.latitude, station.longitude, station.name, station.address + ", " + station.city + "\nTelefon: " + station.phone + "\nRadno vreme: " + station.workingTime, drawableId, false);
					animateTo(station.latitude, station.longitude, MULTI_MARKER_CAMERA_ZOOM_FACTOR);
				}
			}

			adapter = new NetworkLocationsListAdapter(NetworkActivity.this, result);
			final ListView locations = (ListView) findViewById(R.id.listViewLocations);
			locations.setAdapter(adapter);
			locations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
                    setLocationListenerEnabled(false);
                    AmssStation station = (AmssStation) ((NetworkLocationsListAdapter) locations.getAdapter()).getItem(position);
					animateTo(station.latitude, station.longitude, 16);
					locations.setVisibility(View.GONE);
					addAction(new ToggleOptionsAction(android.R.drawable.ic_menu_search));
					showMarker(station.name);
				}
			});
			setInfoAdapter(new MapPopupAdapter(NetworkActivity.this));
			//adapterListener.filterChangedReceived();
			addAction(new ToggleOptionsAction(android.R.drawable.ic_menu_mapmode));

			if (getIntentExtra(INTENT_EXTRA) != null && getIntentExtra(INTENT_EXTRA) instanceof String && INTERNATIONAL_DOCUMENTS_FILTER.equals(getIntentExtra(INTENT_EXTRA))) {
				categories.getOnItemClickListener().onItemClick(null, null, 1, 0); // position 1 means International Documents
			}

			progressDialog.cancel();
		}
	}

	private class MapListener implements MapLoadedListener{

		@Override
		public void mapLoadedReceived() {
		}
	}
	
	public class NetworkCategoriesListAdapter extends BaseAdapter {
		protected Integer[] mTextIds = {
				R.string.network_all_categories,
				R.string.network_international_documents,
				R.string.network_tehnical_review,
				R.string.network_amss_membership,
				R.string.network_registration,
				R.string.network_insurance,	
				R.string.network_help_on_road,
				R.string.network_towing,
				R.string.network_driving_school,
				R.string.network_atest,
				R.string.network_auto_service,	
				R.string.network_exchange_office,	
				R.string.network_tng_pump,	
				R.string.network_car_wash,	
				R.string.network_tourism,	
				R.string.network_tag,	
				R.string.network_shop,	
				R.string.network_restaurant,
				R.string.network_spedition,			
		};
		public Integer[] mImageIds = {
                R.drawable.pin_station,
                R.drawable.medjdokumenta,
                R.drawable.tehnickipregled,
                R.drawable.clanstvo,
                R.drawable.registracija,
                R.drawable.osiguranje,
                R.drawable.pomocnaputu,
                R.drawable.slepovanje,
                R.drawable.autoskola,
                R.drawable.atesti,
                R.drawable.servis,
                R.drawable.menjacnica,
                R.drawable.autogas,
                R.drawable.perionica,
                R.drawable.turizam,
                R.drawable.tag,
                R.drawable.prodavnica,
                R.drawable.ressoran,
                R.drawable.spedicija
        };
		
		@Override
		public int getCount() {
			return mTextIds.length;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(NetworkActivity.this);
            convertView = inflater.inflate(R.layout.stanje_na_putevima_category_list_view, null);
            ImageView iv = (ImageView) convertView.findViewById(R.id.image);
            TextView tv = (TextView) convertView.findViewById(R.id.text);
            tv.setText(NetworkActivity.this.getResources().getString(mTextIds[position]));

            int imageResId = mImageIds[position];
            if (imageResId != -1 ) {
                iv.setImageResource(imageResId);
            }
            return convertView;
		}
	}
	
	public class DistanceComparator implements Comparator<AmssStation> {

		@Override
		public int compare(AmssStation s1, AmssStation s2) {
			float[] d1 = new float[3], d2 = new float[3];
			Location.distanceBetween(Variables.latitude, Variables.longitude, s1.latitude, s1.longitude, d1);
			Location.distanceBetween(Variables.latitude, Variables.longitude, s2.latitude, s2.longitude, d2);
			
			return d2[0] < d1[0] ? 1 : -1;
		}
		
	}
}
