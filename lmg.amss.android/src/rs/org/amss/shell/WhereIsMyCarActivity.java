package rs.org.amss.shell;

import com.google.android.gms.maps.model.LatLng;

import rs.org.amss.R;
import rs.org.amss.core.Variables;
import android.os.Bundle;
import android.view.View;

public class WhereIsMyCarActivity extends BaseMapActivity {

	public static final String TAG = "WhereIsMyCarActivity";	
	protected MapListener mapListener;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		myLocationMarkerDrawable = R.drawable.pin_mylocation;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.where_is_my_car);
		checkIsLogIn(WhereIsMyCarActivity.this);
		setHomeAction(R.drawable.ic_mycar, R.string.mycar_whereismycar_text);
		mapListener = new MapListener();
		setMapLoadedListener(mapListener);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	public void clear(View view)
	{
		getMemoryManager().saveMyCarLocation(null);
		removeMarker(getString(R.string.mycar_current_location_title));
	}	

	public void save(View view)
	{
		addMyCarMarker(currentLocation);
		
		String location = String.format("%s;%s", currentLocation.latitude, currentLocation.longitude);
		getMemoryManager().saveMyCarLocation(location);
	}
	
	protected void addMyCarMarker(LatLng location){
		addMarker(location, getString(R.string.mycar_current_location_title), getString(R.string.mycar_current_location_snippet), R.drawable.pin, true);
	}

	private class MapListener implements MapLoadedListener{

		@Override
		public void mapLoadedReceived() {

			if (Variables.myCarLocation != null)
				addMyCarMarker(Variables.myCarLocation);
		}
	}
}
