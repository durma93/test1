package rs.org.amss.shell;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rs.org.amss.R;
import rs.org.amss.core.Constants;
import rs.org.amss.core.LayoutManager;
import rs.org.amss.core.MemoryManager;
import rs.org.amss.core.Variables;
import rs.org.amss.model.GetFonts;
import rs.org.amss.model.User;
import rs.org.amss.view.ChangeSMSTextDialog;
import net.lmggroup.utility.ActivityHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.IntentCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.ActionBar.IntentAction;

import static android.view.Gravity.RIGHT;

public class BaseMapActivity extends FragmentActivity implements LocationListener {

	public static final String TAG = "BaseMapActivity";	

	public static final long LOCATION_MANAGER_MIN_TIME = 400;
	public static final float LOCATION_MANAGER_MIN_DISTANCE = 1;
	public static final int LOCATION_MANAGER_CAMERA_ZOOM_FACTOR = 15;
	public static final int MULTI_MARKER_CAMERA_ZOOM_FACTOR = 6;
	public static final String MY_LOCATION_MARKER_ID = "myLocation";
	//	public static float previousZoomLevel = -1.0f;
	public static int previousZoomLevel = 6;
	public Marker mPositionMarker;
	protected ActivityHelper activityManager;
	protected MemoryManager memoryManager;
	protected GoogleMap mMap;
	protected MapLoadedListener mapLoadedListener;

	protected boolean locationListenerEnabled = true;
	protected double latitude = 0.0;
	protected double longitude = 0.0;
	protected float accuracy;
	protected String address;
	protected String provider;
	protected boolean markersInitialized;
	protected LatLng currentLocation;
	protected LocationManager locationManager;
	protected LayoutManager layoutManager;
	protected List<Marker> currentMarkers = new ArrayList<Marker>();
	protected ActionBar actionBar;

	protected int myLocationMarkerDrawable = R.drawable.pin;
	protected boolean clearMarkersOnLocationChanged = true;

	protected ProgressDialog progressDialog;


	private ViewGroup rootView;
	private DrawerLayout drawerLayout;
	private LinearLayout sideMenu;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		activityManager = new ActivityHelper(this);
		memoryManager = new MemoryManager(this);
		layoutManager = new LayoutManager(this);
		super.onCreate(savedInstanceState);

		if (checkGooglePlayServices())
			setUpMapIfNeeded();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (setUpMapIfNeeded()){        	
			if (provider == null)
				setUpLocationManager();
			else
				locationManager.requestLocationUpdates(provider, LOCATION_MANAGER_MIN_TIME, LOCATION_MANAGER_MIN_DISTANCE, this);
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		rootView = (ViewGroup) findViewById(android.R.id.content);
		drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.drawer, rootView, false);
		sideMenu = (LinearLayout) getLayoutInflater().inflate(R.layout.slider_menu, drawerLayout, false);
		DrawerLayout.LayoutParams sideMenuLayoutParams = (DrawerLayout.LayoutParams) sideMenu.getLayoutParams();
		if (sideMenuLayoutParams != null){
			sideMenuLayoutParams.gravity = RIGHT;
			sideMenu.setLayoutParams(sideMenuLayoutParams);
		}
		View originalContent = rootView.getChildAt(0);
		rootView.removeAllViews();
		FrameLayout.LayoutParams layoutParamsContentView = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		rootView.addView(drawerLayout, layoutParamsContentView);
		drawerLayout.setId(R.id.material_drawer_layout);
		drawerLayout.addView(originalContent, 0);
		drawerLayout.addView(sideMenu, 1);
		drawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));
		sideMenu.findViewById(R.id.slideMenu).setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				return true;
			}
		});
		if (Variables.getUser() != null && Variables.getUser().getMembershipId() > 0){
			((TextView)sideMenu.findViewById(R.id.accountMenuItemText)).setText("Izloguj se");
			Variables.loadMemberBasicInfo(getApplicationContext());
			if (Variables.memberName != null) {
				loadUserLabel(Variables.memberName);
			}
		}
		sideMenu.findViewById(R.id.accountMenuItem).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (Variables.getUser() != null && Variables.getUser().getMembershipId() > 0) {
					Variables.setUser(new User());
					Variables.setMember(null);
					Variables.setMembershipInfo(null);
					memoryManager.ClearPreferences();
					Intent i = new Intent(BaseMapActivity.this, MainActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(i);
				} else {
					drawerLayout.closeDrawers();
					activityManager.startActivity(LoginActivity.class);
				}
			}
		});
		sideMenu.findViewById(R.id.aboutMenuItem).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				activityManager.startActivity(AboutUsActivity.class);
			}
		});

        findViewById(R.id.menuButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(RIGHT)) {
                    drawerLayout.closeDrawers();
                } else {
                    drawerLayout.openDrawer(RIGHT);
                }
            }
        });
	}

	public static void checkIsLogIn(Activity activity) {
		Button sign = (Button)activity.findViewById(R.id.buttonSign);
		Button signOut = (Button)activity.findViewById(R.id.buttonSignOut);
//		if (Variables.getUser() != null && Variables.getUser().getMembershipId() > 0) {
//			sign.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.profil));
//			signOut.setVisibility(View.VISIBLE);
//		} else {
//			sign.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.profil));
//		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (locationManager != null)
			locationManager.removeUpdates(this);
	}

	protected void setLocationListenerEnabled(boolean enabled){
		locationListenerEnabled = enabled;
	}

	@Override
	public void onLocationChanged(Location location)
	{	
		if (location == null)
			return;

		boolean hadCurrentLocation = currentLocation != null;

		latitude = location.getLatitude();
		longitude = location.getLongitude();
		currentLocation = new LatLng(latitude, longitude);

		Variables.latitude = latitude;
		Variables.longitude = longitude;
		Log.v(TAG, Variables.latitude + " "+Variables.longitude);
		if (locationListenerEnabled){
			if (mPositionMarker == null) {

				/*mPositionMarker = mMap.addMarker(new MarkerOptions()
				.title(getString(R.string.my_current_location_title))
				.snippet(getString(R.string.my_current_location_snippet))
				.flat(true)
				.icon(BitmapDescriptorFactory.fromResource(myLocationMarkerDrawable))
				.anchor(0.5f, 0.5f)
				.position(
						new LatLng(location.getLatitude(), location
								.getLongitude()))
						);*/
			}

			//animateMarker(mPositionMarker, location); // Helper method for smooth
            if (!hadCurrentLocation) {
                CameraUpdate center= CameraUpdateFactory.newLatLng(currentLocation);
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(LOCATION_MANAGER_CAMERA_ZOOM_FACTOR);
                mMap.moveCamera(center);
                mMap.animateCamera(zoom);
                mMap.setMyLocationEnabled(true);
            }
		}

		if (!markersInitialized){			
			markersInitialized = true;
			fireMapLoadedEvent();
		}		
	}

	public void animateMarker(final Marker marker, final Location location) {
		final Handler handler = new Handler();
		final long start = SystemClock.uptimeMillis();
		final LatLng startLatLng = marker.getPosition();
		final double startRotation = marker.getRotation();
		final long duration = 500;

		final Interpolator interpolator = new LinearInterpolator();

		handler.post(new Runnable() {
			@Override
			public void run() {
				long elapsed = SystemClock.uptimeMillis() - start;
				float t = interpolator.getInterpolation((float) elapsed
						/ duration);

				double lng = t * location.getLongitude() + (1 - t)
						* startLatLng.longitude;
				double lat = t * location.getLatitude() + (1 - t)
						* startLatLng.latitude;

				float rotation = (float) (t * location.getBearing() + (1 - t)
						* startRotation);

				marker.setPosition(new LatLng(lat, lng));
				marker.setRotation(rotation);
				
				if (t < 1.0) {
					handler.postDelayed(this, 16);
				}
			}
		});		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// Log.d(TAG, "Enabled new provider " + provider);
		// Toast.makeText(this, "Enabled new provider " + provider, Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onProviderEnabled(String provider) {
		// Log.d(TAG, "Disabled provider " + provider);
		// Toast.makeText(this, "Disabled provider " + provider, Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// Log.d(TAG, "Provider " + provider + " status: " + status);
		// Toast.makeText(this, "Provider " + provider + " status: " + status, Toast.LENGTH_SHORT).show();
	}

	public void showMain(View view) {
		activityManager.startActivityWithFlag(MainActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
	}

	public void showSettings(View view){

	}

	public void showSignIn(View view){
		if (Variables.getUser() != null && Variables.getUser().getMembershipId() > 0)
			activityManager.startActivity(MembershipActivity.class);
		else
			activityManager.startActivity(LoginActivity.class);
	}

	public void showSignOut(View view) {
		activityManager.startActivity(LoginActivatedActivity.class);
	}
	
	public void showNews(View view){
		activityManager.startActivity(NewsActivity.class);
	}

//	@SuppressLint("Override")
//	public ActionBar getActionBar(){
//		if (actionBar == null){
//			View actionBarView = findViewById(R.id.actionbar);
//			if (actionBarView != null)
//				actionBar = (ActionBar)actionBarView;
//		}
//		return actionBar;
//	}

	public void setTitle(){
		if (getActionBar() != null)
			actionBar.setTitle(getTitle());
	}

	public void setTitle(String title){
		if (getActionBar() != null)
			actionBar.setTitle(title);
	}

	public void setTitle(int titleId){
		if (getActionBar() != null)
			actionBar.setTitle(getString(titleId));
	}

	public void setHomeIcon(int drawableId){
		if (getActionBar() != null){
			actionBar.setHomeLogo(drawableId);
		}
	}

	/*public void setHomeAction(int drawableId){
		if (getActionBar() != null){
			actionBar.setHomeAction(new IntentAction(this, createHomeIntent(), drawableId));
			//			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}

	public void setHomeAction(int drawableId, int titleId){
		if (getActionBar() != null){
			actionBar.setHomeAction(new IntentAction(this, createHomeIntent(), drawableId));
			//			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setTitle(getString(titleId));
		}
	}*/
	public void setHomeAction(int drawableId){
		if (getActionBar() != null){
			actionBar.setHomeAction(new IntentAction(this, createHomeIntent(), drawableId));
			//			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}

	public void setHomeAction(int drawableId, int titleId){
		if (getActionBar() != null){
			actionBar.setHomeAction(new IntentAction(this, createHomeIntent(), drawableId));
			//			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setTitle(getString(titleId));
		}
	}
	

	public void setHomeAction(int drawableId, int titleId, Class<?> activityClass){
		if (getActionBar() != null){
			actionBar.setHomeAction(new IntentAction(this, getBackToChooserIntent(activityClass), drawableId));
			actionBar.setTitle(getString(titleId));
		}
	}

	public Intent getBackToChooserIntent(Class<?> activityClass) {
		final Intent intent = new Intent(this, activityClass);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		return intent;
	}
	
	public Serializable getIntentExtra(String key){
		Bundle extras = getIntent().getExtras();
		return extras != null ? extras.getSerializable(key) : null;
	}

	public Intent createHomeIntent() {
		final Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		return intent;
	}

	public Intent createShareIntent(String title, String extraText) {
		final Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, extraText);
		return Intent.createChooser(intent, title);
	}

	public void addAction(Action action){
		if (getActionBar() != null)
			actionBar.addAction(action);
	}

	public void showProgress(){
		if (getActionBar() != null)
			actionBar.setProgressBarVisibility(View.VISIBLE);		
	}

	public void hideProgress(){
		if (getActionBar() != null)
			actionBar.setProgressBarVisibility(View.GONE);		
	}	

	public ActivityHelper getActivityManager(){
		return activityManager;
	}

	public MemoryManager getMemoryManager(){
		return memoryManager;
	}

	public LayoutManager getLayoutManager(){
		return layoutManager;
	}

	protected boolean checkGooglePlayServices(){
		int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (result != ConnectionResult.SUCCESS) {
			GooglePlayServicesUtil.getErrorDialog(result, this, 10).show();
		}

		Log.d(TAG, "checkGooglePlayServices() result: " + result);

		return result == ConnectionResult.SUCCESS;

	}

	protected void loadUserLabel(String value) {
		TextView userLabel = (TextView) drawerLayout.findViewById(R.id.userLabel);
		userLabel.setVisibility(View.VISIBLE);
		userLabel.setText(value);
		userLabel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				activityManager.startActivity(MembershipActivity.class);
			}
		});
	}

	private boolean setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			// mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			FragmentManager fragmentManager = getSupportFragmentManager();
			if (fragmentManager != null){
				Fragment fragment = fragmentManager.findFragmentById(R.id.map);
				if (fragment != null){
					mMap = ((SupportMapFragment)fragment).getMap();
					if (mMap != null) {
						mMap.setOnCameraChangeListener(getCameraChangeListener());
					}
				}

				if (mMap != null){
					mMap.setOnCameraChangeListener(getCameraChangeListener());
					setUpLocationManager();
				}

			}
		}

		Log.d(TAG, "setUpMapIfNeeded() result: " + (mMap != null));

		return mMap != null;
	}

	private void setUpLocationManager(){

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		boolean enabledGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		//		 boolean enabledWiFi = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		if (!enabledGPS) {
			Log.d(TAG, "GPS provider not enabled");
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle(R.string.MapsActivity_GpsDisabled_AlertDialog_Title);
			dialog.setMessage(R.string.MapsActivity_GpsDisabled_AlertDialog_Message);
			dialog.setPositiveButton(R.string.global_ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					startActivity(intent);
				}
			});
			dialog.setNegativeButton(R.string.global_cancel, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});
			dialog.show();
		}


		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		provider = locationManager.getBestProvider(criteria, false);
		Log.v(TAG, "Provider: "+provider);
		if(currentLocation==null){
			Location location = locationManager.getLastKnownLocation(provider);
			if (location != null) {
				onLocationChanged(location);
			} else {
				provider = LocationManager.NETWORK_PROVIDER;
				location = locationManager.getLastKnownLocation(provider);
				if (location != null) {
					onLocationChanged(location);
				} else
					Toast.makeText(this, "Nije selektovan ni jedan provajder za GPS navigaciju", Toast.LENGTH_SHORT).show();
			}
		}
	}

	protected void setInfoAdapter(InfoWindowAdapter adapter){
		mMap.setInfoWindowAdapter(adapter);
	}


	protected void addMarker(double latitude, double longitude, String title, String snippet, int drawable, boolean removePrevious) {
		addMarker(new MarkerOptions()
		.position(new LatLng(latitude, longitude))
		.title(title)
		.snippet(snippet)
		.icon(BitmapDescriptorFactory.fromResource(drawable)), removePrevious);
	}

	protected void addMarkerInvisible(double latitude, double longitude, String title, String snippet, int drawable, boolean removePrevious) {
		addMarker(new MarkerOptions()
		.position(new LatLng(latitude, longitude))
		.title(title)
		.snippet(snippet)
		.icon(BitmapDescriptorFactory.fromResource(drawable))
		.visible(false), removePrevious);
	}

	protected void addMarker(LatLng location, String title, String snippet, int drawable, boolean removePrevious) {

		if (removePrevious)
			removeMarker(title);

		addMarker(new MarkerOptions()
		.position(location)
		.title(title)
		.snippet(snippet)
		.icon(BitmapDescriptorFactory.fromResource(drawable)), false);
	}

	protected void addMarker(LatLng location) {

		addMarker(location, clearMarkersOnLocationChanged);
	}

	protected void addMarker(LatLng location, boolean removePrevious) {

		if (removePrevious)
			removeMarker(getString(R.string.my_current_location_title));

		addMarker(new MarkerOptions()
		.position(location)
		.title(getString(R.string.my_current_location_title))
		.snippet(getString(R.string.my_current_location_snippet))
		.icon(BitmapDescriptorFactory.fromResource(myLocationMarkerDrawable)), false);
	}


	protected void addMarker(LatLng location, boolean removePrevious, String title, String snippet, int drawableId) {

		addMarker(new MarkerOptions()
		.position(location)
		.title(title)
		.snippet(snippet)
		.icon(BitmapDescriptorFactory.fromResource(drawableId)), removePrevious);
	}

	protected void addMarker(MarkerOptions marker, boolean removePrevious) {

		if (removePrevious)
			clearMarkers();

		if (mMap != null) {
			Log.d(TAG, "addMarker: " + marker.getTitle()  + ", " + marker.getPosition());
			Marker markerObject = mMap.addMarker(marker);
			currentMarkers.add(markerObject);
		}
	}

	protected void removeMarker(String title){
		Marker found = null;
		for (Marker marker : currentMarkers)
			if (marker.getTitle().equals(title)){
				found = marker;
				break;
			}

		if (found != null){
			currentMarkers.remove(found);
			Log.d(TAG, "removeMarker: " + found.getTitle()  + ", " + found.getPosition() + " ( " + currentMarkers.size() + " )");
			found.remove();
		}

	}

	protected void clearMarkers(){
		for (Marker marker : currentMarkers){
			Log.d(TAG, "clearMarkers: " + marker.getTitle()  + ", " + marker.getPosition() + " ( " + currentMarkers.size() + " )");
			marker.remove();
		}

		currentMarkers.clear();
	}

	protected void hideMarkers(){
		for (Marker marker : currentMarkers){
			Log.d(TAG, "hideMarkers: " + marker.getTitle() + " ( " + currentMarkers.size() + " )");
			marker.setVisible(false);
		}
	}

	protected void setMarkerVisible(String title, boolean visible) {
	    setMarkerVisible(title, visible, 0);
    }

	protected void setMarkerVisible(String title, boolean visible, int iconDrawableId){
		Marker found = null;
		for (Marker marker : currentMarkers)
			if (marker.getTitle().equals(title)){
				found = marker;
				break;
			}

		if (found != null){
			Log.d(TAG, "setMarkerVisible: " + found.getTitle()  + " - " + visible + " ( " + currentMarkers.size() + " )");
			if (iconDrawableId != 0)
			    found.setIcon(BitmapDescriptorFactory.fromResource(iconDrawableId));
			if (found.isVisible() != visible)
				found.setVisible(visible);
		}
	}

	protected void showMarker(String title) {
		Marker found = null;
		for (Marker marker : currentMarkers)
			if (marker.getTitle().equals(title)){
				found = marker;
				break;
			}

		if (found != null){
			Log.d(TAG, "showMarker: " + found.getTitle());

			final Marker toShow = found;
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toShow.showInfoWindow();
                }
            }, 100);

		}
	}

	protected void animateToCurrentLocation(){
		animateToCurrentLocation(LOCATION_MANAGER_CAMERA_ZOOM_FACTOR);
	}

	protected void animateToCurrentLocation(int zoomFactor){
		animateTo(currentLocation, zoomFactor);
	}

	public OnCameraChangeListener getCameraChangeListener()
	{
		return new OnCameraChangeListener()
		{
			@Override
			public void onCameraChange(CameraPosition position)
			{
				Log.d("Zoom", "Zoom: " + position.zoom);
				if(position.zoom<MULTI_MARKER_CAMERA_ZOOM_FACTOR)
					previousZoomLevel = MULTI_MARKER_CAMERA_ZOOM_FACTOR;
				else
					previousZoomLevel = (int) position.zoom;
			}
		};
	}

	protected void animateTo(LatLng location, int zoomFactor){
		if (mMap != null && location != null){
			CameraUpdate center= CameraUpdateFactory.newLatLng(location);
			CameraUpdate zoom = CameraUpdateFactory.zoomTo(zoomFactor);
			if (center != null) {
				mMap.moveCamera(center);
				mMap.animateCamera(zoom);
			}
		}
	}

	protected void animateTo(double latitude, double longitude, int zoomFactor){
		if (mMap != null){
			CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude));
			CameraUpdate zoom = CameraUpdateFactory.zoomTo(zoomFactor);
			if (center != null) {
				mMap.moveCamera(center);
				mMap.animateCamera(zoom);
			}
		}
	}

	protected void setZoom(int zoomFactor){
		if (mMap != null){
			CameraUpdate zoom = CameraUpdateFactory.zoomTo(zoomFactor);
			mMap.animateCamera(zoom);
		}
	}

	private synchronized void fireMapLoadedEvent() {
		if (mapLoadedListener != null)
			mapLoadedListener.mapLoadedReceived();
	}

	public synchronized void setMapLoadedListener(MapLoadedListener listener) {
		mapLoadedListener = listener;
	}

	public interface MapLoadedListener {
		public void mapLoadedReceived();
	}

	public void setUpAMSOActionBar(String title, int imageResourceID){
		setUpAMSOActionBar(title, imageResourceID, false);
	}

	public void setUpAMSOActionBar(String title, int imageResourceID, boolean hideCallCenter){
		TextView titleView = (TextView)findViewById(R.id.amso_title);
		titleView.setText(title);

		ImageView activityIcon = (ImageView)findViewById(R.id.activity_icon);
		activityIcon.setImageResource(imageResourceID);

		if (hideCallCenter){
			ImageView callCenterIcon = (ImageView)findViewById(R.id.call_center);
			callCenterIcon.setVisibility(View.GONE);
		}
	}

	public void setUpAMSOFooter(){
		TextView footer = (TextView)findViewById(R.id.textView1);
		footer.setTypeface(GetFonts.getTypeface(BaseMapActivity.this));
	}


	public void callAmsoCallCenter(View view){
		getActivityManager().makeCall(Constants.amsoCallCenterPhoneNumber);
	}

	public void getEditTextDialog(Activity activity){
		FragmentManager fragmentManager = getSupportFragmentManager();
		new ChangeSMSTextDialog(activity).show(fragmentManager, "Activity");
	}
	public void getErrorToast(){
		layoutManager.showError(R.string.SMS_text_error_message);
	}
}
