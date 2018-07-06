package rs.org.amss.shell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rs.org.amss.R;
import rs.org.amss.core.MapPopupAdapter;
import rs.org.amss.core.RoadConditionLocationsListAdapter;
import rs.org.amss.core.RoadConditionsGridViewAdapter;
import rs.org.amss.core.RoadConditionsGridViewAdapter.RoadConditionsGridAdapterListener;
import rs.org.amss.core.Variables;
import rs.org.amss.core.WebMethods;
import rs.org.amss.core.WebResponseParser;
import rs.org.amss.model.RoadCondition;
import rs.org.amss.model.RoadConditionsFilter;

import org.apache.http.client.ClientProtocolException;

import com.markupartist.android.widget.ActionBar.AbstractAction;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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

public class RoadConditionsActivity extends BaseMapActivity implements View.OnClickListener{

	protected RoadConditionsGridViewAdapter adapter;
	protected AdapterListener adapterListener;
	
	protected ListView categories;
	protected ListView locations;

	protected View listButton;
	protected View mapButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.road_conditions);
		setHomeAction(R.drawable.ic_roads, R.string.roads_label, MainActivity.class);
		checkIsLogIn(RoadConditionsActivity.this);

		mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(this);

        findViewById(R.id.listButton).setOnClickListener(this);
		findViewById(R.id.back_button).setOnClickListener(this);
		adapter = new RoadConditionsGridViewAdapter(this);
		adapterListener = new AdapterListener();
		adapter.setListener(adapterListener);
		
		categories = (ListView)findViewById(R.id.listViewCategories);
		locations = (ListView)findViewById(R.id.listViewLocations);
		
		
		categories.setAdapter(new RoadCategoriesListAdapter());
		categories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				categories.setVisibility(View.GONE);
				locations.setVisibility(View.VISIBLE);

				ArrayList<RoadCondition>  roadConditions = new ArrayList<RoadCondition>();

				if (Variables.roadConditionList != null) {
					for (RoadCondition rc : Variables.roadConditionList) {
						if (position == 0) roadConditions.add(rc);
						else if (position == 1 && rc.iconType.equalsIgnoreCase("cloudy1.png")) roadConditions.add(rc);
						else if (position == 2 && rc.iconType.equalsIgnoreCase("radovi.png")) roadConditions.add(rc);
						else if (position == 3 && rc.iconType.equalsIgnoreCase("zimska_oprema.png")) roadConditions.add(rc);
						else if (position == 4 && (rc.iconType.equalsIgnoreCase("carina1.png") || rc.iconType.equalsIgnoreCase("carina.png"))) roadConditions.add(rc);
						else if (position == 5 && rc.iconType.equalsIgnoreCase("zabrana-teretna.png")) roadConditions.add(rc);
						else if (position == 6 && rc.iconType.equalsIgnoreCase("zabrana.png")) roadConditions.add(rc);
						else if (position == 7 && rc.iconType.equalsIgnoreCase("info.png")) roadConditions.add(rc);
						else if (position == 8 && rc.iconType.equalsIgnoreCase("crna_tacka.png")) roadConditions.add(rc);
						else if (position == 9 && rc.iconType.equalsIgnoreCase("klizavo.png")) roadConditions.add(rc);
						else if (position == 10 && rc.iconType.equalsIgnoreCase("odron.png")) roadConditions.add(rc);
						else if (position == 11 && rc.iconType.equalsIgnoreCase("semafor.png")) roadConditions.add(rc);
						else if (position == 12 && rc.iconType.equalsIgnoreCase("icon")) roadConditions.add(rc); //TODO: Find out which icon is for winds
						else if (position == 13 && rc.iconType.equalsIgnoreCase("opasnost.png")) roadConditions.add(rc);
					}
				}

				RoadConditionLocationsListAdapter adapter = new RoadConditionLocationsListAdapter(RoadConditionsActivity.this, roadConditions);
				locations.setAdapter(adapter);
				
				hideMarkers();
				for (RoadCondition rc : roadConditions) {
					setMarkerVisible(rc.name, true);
				}
			}
		});
		
		locations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                setLocationListenerEnabled(false);
                RoadConditionLocationsListAdapter adapter = (RoadConditionLocationsListAdapter)locations.getAdapter();
				RoadCondition rc = (RoadCondition)adapter.getItem(position);
				animateTo(rc.latitude, rc.longitude, 16);
				locations.setVisibility(View.GONE);
				addAction(new ToggleOptionsAction(android.R.drawable.ic_menu_search));
				showMarker(rc.name);
			}
		});

		mapButton.setBackgroundColor(0x00000000);
		categories.setVisibility(View.VISIBLE);
		locations.setVisibility(View.GONE);
		setUpRoadConditions();
	}

	public void setUpRoadConditions() {

		showProgress();
		new GetRoadConditionsTask().execute();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && locations.getVisibility() == View.VISIBLE) {
			locations.setVisibility(View.GONE);
			categories.setVisibility(View.VISIBLE);
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mapButton:
				view.setBackgroundResource(R.drawable.primary_button);
                setLocationListenerEnabled(true);
                animateToCurrentLocation();
                categories.setVisibility(View.GONE);
                locations.setVisibility(View.GONE);
                findViewById(R.id.listButton).setBackgroundColor(0x00000000);
                break;
            case R.id.listButton:
				view.setBackgroundResource(R.drawable.primary_button);
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

    private class GetRoadConditionsTask extends
			AsyncTask<Object, Void, ArrayList<RoadCondition>> {

		protected ArrayList<RoadCondition> doInBackground(Object... params) {
			ArrayList<RoadCondition> items = Variables.roadConditionList;
			if (items == null || items.size() == 0) {
				items = new ArrayList<RoadCondition>();
				String response;
				try {
					response = WebMethods.getRoadConditions();
					items = WebResponseParser.getRoadConditionList(response);

					Variables.roadConditionList = items;
				} catch (ClientProtocolException e) {
					e.printStackTrace();
					RoadConditionsActivity.this.getLayoutManager().showError(R.string.Loading_ClientProtocolException);
				} catch (IOException e) {
					e.printStackTrace();
					RoadConditionsActivity.this.getLayoutManager().showError(R.string.Loading_IOException);
				} catch (Exception e) {
					e.printStackTrace();
					RoadConditionsActivity.this.getLayoutManager().showError(R.string.Loading_Exception);
				}
			}
			Collections.sort(items, new DistanceComparator());
			return items;
		}

		protected void onPostExecute(ArrayList<RoadCondition> result) {
			Map<String, String> m = new HashMap<String, String>();
			Variables.roadConditionList = result;
			if (Variables.roadConditionList != null && Variables.roadConditionList.size() > 0) {
				for (RoadCondition object : Variables.roadConditionList) {
					int imageResourceId = R.drawable.pin_station;
					m.put(object.iconType, object.iconType);
					try {
						int drawableId = getDrawableId(object.iconType);
						if (drawableId > 0) {
							imageResourceId = drawableId;
							object.iconResourceId = drawableId;
						} else {
							Log.w(TAG, object.iconType + " could not be resolved -> " + "imageResourceId: " + drawableId);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}

					try {
						addMarker(object.latitude, object.longitude, object.name, object.text, imageResourceId, false);
						animateTo(object.latitude, object.longitude, MULTI_MARKER_CAMERA_ZOOM_FACTOR);
					} catch (Exception ex) {
						ex.printStackTrace();
						Log.e(TAG, object.name + " -> " + "imageResourceId: " + imageResourceId);
					}
				}

				setInfoAdapter(new MapPopupAdapter(RoadConditionsActivity.this));
			}
			
			addAction(new ToggleOptionsAction(android.R.drawable.ic_menu_search));
			hideProgress();
			String message = "There are " + m.keySet().size() + " keys: " + TextUtils.join(", ", m.keySet());
			Log.d("RoadConditionsActivity", message);
		}

		public int getDrawableId(String resourceName) {
            if (resourceName.equalsIgnoreCase("radovi.png")) return R.drawable.radnapu;
            if (resourceName.contains("carina")) return R.drawable.granicni;
            if (resourceName.equalsIgnoreCase("zabrana-teretna.png")) return R.drawable.ogranictere;
            if (resourceName.equalsIgnoreCase("zabrana.png")) return R.drawable.obusta;
            if (resourceName.equalsIgnoreCase("info.png")) return R.drawable.informacosta;
            if (resourceName.equalsIgnoreCase("crna_tacka.png")) return R.drawable.crnetac;
            if (resourceName.equalsIgnoreCase("klizavo.png")) return R.drawable.klikol;
            if (resourceName.equalsIgnoreCase("odron.png")) return R.drawable.opasodrao;
            if (resourceName.equalsIgnoreCase("semafor.png")) return R.drawable.naizmeni;
            if (resourceName.equalsIgnoreCase("opasnost.png")) return R.drawable.opasnost2;

			resourceName = resourceName.replace('-', '_');
			if (resourceName.startsWith("/")) {
				resourceName = resourceName.substring(1);
			}

			int index = resourceName.lastIndexOf(".");
			if (index > 0) {
				resourceName = resourceName.substring(0, index);
			}

			return RoadConditionsActivity.this.getResources().getIdentifier(resourceName.toLowerCase(), "drawable", RoadConditionsActivity.this.getPackageName());
		}

	}
	
	private class AdapterListener implements RoadConditionsGridAdapterListener {
		protected List<RoadCondition> filterList;

		protected Handler finishedHandler = new Handler() {
			@Override public void handleMessage(Message msg) {

				hideMarkers();
				filterList.removeAll(Collections.singleton(null)); 
				for (RoadCondition station : filterList){
					setMarkerVisible(station.name, true);

				}
				setZoom(previousZoomLevel);
				hideProgress();
			}
		};

		@Override
		public void filterChangedReceived() {
			showProgress();
			new Thread(new Runnable() {
				@SuppressWarnings("unchecked")
				public void run() {

					filterList = new ArrayList<RoadCondition>();
					RoadConditionsFilter filter = adapter.getFilter();
					if (filter != null) {
						for (RoadCondition item : Variables.roadConditionList){
							boolean addToList = isVremenskaPrilika(item, filter);
							addToList |= isRadoviNaPutu(item, filter);
							addToList |= isObaveznaZimskaOprema(item, filter);
							addToList |= isGranicniPrelaz(item, filter);
							addToList |= isOgranicenjeZaTeretnoVozilo(item, filter);
							addToList |= isObustavaSaobracaja(item, filter);
							addToList |= isInformacijaOStanju(item, filter);
							addToList |= isCrnaTacka(item, filter);
							addToList |= isKlizavKolovoz(item, filter);
							addToList |= isOpasnostOdOdrona(item, filter);
							addToList |= isNaizmenicnoPustanje(item, filter);
							addToList |= isJakVetar(item, filter);
							addToList |= isOpasnostNaPutu(item, filter);
							
							if (!filter.anythingSelected() || addToList)
								filterList.add(item);
						}
					}

					finishedHandler.sendEmptyMessage(0);
				}
			}).start();		

		}

		private boolean isVremenskaPrilika(RoadCondition item, RoadConditionsFilter filter) {
			if (filter.vremenskePrilike)
				return "cloudy1.png".equalsIgnoreCase(item.iconType);
			return false;
		}
		
		private boolean isRadoviNaPutu(RoadCondition item, RoadConditionsFilter filter) {
			if (filter.radoviNaPutu)
				return "radovi.png".equalsIgnoreCase(item.iconType);
			return false;
		}
		
		private boolean isObaveznaZimskaOprema(RoadCondition item, RoadConditionsFilter filter) {
			if (filter.obaveznaZimskaOprema)
				return "zimska_oprema.png".equalsIgnoreCase(item.iconType);
			return false;
		}
		
		private boolean isGranicniPrelaz(RoadCondition item, RoadConditionsFilter filter) {
			if (filter.granicniPrelaz)
				return "carina1.png".equalsIgnoreCase(item.iconType) || "carina.png".equalsIgnoreCase(item.iconType);
			return false;
		}
		
		private boolean isOgranicenjeZaTeretnoVozilo(RoadCondition item, RoadConditionsFilter filter) {
			if (filter.ogranicenjaZaTeretnaVozila)
				return "zabrana-teretna.png".equalsIgnoreCase(item.iconType);
			return false;
		}
		
		private boolean isObustavaSaobracaja(RoadCondition item, RoadConditionsFilter filter) {
			if (filter.obustaveSaobracaja)
				return "zabrana.png".equalsIgnoreCase(item.iconType);
			return false;
		}
		
		private boolean isInformacijaOStanju(RoadCondition item, RoadConditionsFilter filter) {
			if (filter.informacijeOStanju)
				return "info.png".equalsIgnoreCase(item.iconType) || "info-ani.gif".equalsIgnoreCase(item.iconType);
			return false;
		}
		
		private boolean isCrnaTacka(RoadCondition item, RoadConditionsFilter filter) {
			if (filter.crneTacke)
				return "crna_tacka.png".equalsIgnoreCase(item.iconType);
			return false;
		}
		
		private boolean isKlizavKolovoz(RoadCondition item, RoadConditionsFilter filter) {
			if (filter.klizavKolovoz)
				return "klizavo.png".equalsIgnoreCase(item.iconType);
			return false;
		}
		
		private boolean isOpasnostOdOdrona(RoadCondition item, RoadConditionsFilter filter) {
			if (filter.opasnostOdOdrona)
				return "odron.png".equalsIgnoreCase(item.iconType);
			return false;
		}
		
		private boolean isNaizmenicnoPustanje(RoadCondition item, RoadConditionsFilter filter) {
			if (filter.naizmenicnoPropustanje)
				return "semafor.png".equalsIgnoreCase(item.iconType);
			return false;
		}
		
		private boolean isJakVetar(RoadCondition item, RoadConditionsFilter filter) {
			if (filter.jakVetar)
				return "icon".equalsIgnoreCase(item.iconType);
			return false;
		}
		
		private boolean isOpasnostNaPutu(RoadCondition item, RoadConditionsFilter filter) {
			if (filter.opasnostNaPutu)
				return "opasnost.png".equalsIgnoreCase(item.iconType);
			return false;
		}
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
	
	public class RoadCategoriesListAdapter extends BaseAdapter {
		protected Integer[] mTextIds = {
				R.string.road_condition_all,
				R.string.road_condition_1_vremenske_prilike,
				R.string.road_condition_2_radovi_na_putu,
				R.string.road_condition_3_obavezna_zimska_oprema,
				R.string.road_condition_4_granicni_prelazi,
				R.string.road_condition_5_ogranicenja_za_teretna_vozila,
				R.string.road_condition_6_obustave_saobracaja,
				R.string.road_condition_7_informacije_o_stanju,
				R.string.road_condition_8_crne_tacke,
				R.string.road_condition_9_klizav_kolovoz,
				R.string.road_condition_10_opasnost_od_odrona,
				R.string.road_condition_11_naizmenicno_propustanje,
				R.string.road_condition_12_jak_vetar,
				R.string.road_condition_13_opasnost_na_putu,
		};
        protected Integer[] mImageIds = {
                -1,
                R.drawable.vrempril,
                R.drawable.radnapu,
                R.drawable.obavzimoprem,
                R.drawable.granicni,
                R.drawable.ogranictere,
                R.drawable.obusta,
                R.drawable.informacosta,
                R.drawable.crnetac,
                R.drawable.klikol,
                R.drawable.opasodrao,
                R.drawable.naizmeni,
                R.drawable.jvetar,
                R.drawable.opasnost2,
        };
		
		@Override
		public int getCount() {
			return mTextIds.length;
		}

		@Override
		public Object getItem(int position) {
			return mTextIds[position];
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater.from(RoadConditionsActivity.this);
			convertView = inflater.inflate(R.layout.stanje_na_putevima_category_list_view, null);
            ImageView iv = (ImageView) convertView.findViewById(R.id.image);
			TextView tv = (TextView) convertView.findViewById(R.id.text);
			tv.setText(RoadConditionsActivity.this.getResources().getString(mTextIds[position]));

            int imageResId = mImageIds[position];
            if (imageResId != -1 ) {
                iv.setImageResource(imageResId);
            }
			return convertView;
		}
	}
	
	public class DistanceComparator implements Comparator<RoadCondition> {

		@Override
		public int compare(RoadCondition rc1, RoadCondition rc2) {
			float[] d1 = new float[3], d2 = new float[3];
			Location.distanceBetween(Variables.latitude, Variables.longitude, rc1.latitude, rc1.longitude, d1);
			Location.distanceBetween(Variables.latitude, Variables.longitude, rc2.latitude, rc2.longitude, d2);
			
			return d2[0] < d1[0] ? 1 : -1;
		}
		
	}
}
