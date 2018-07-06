package rs.org.amss.core;



import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

public class GpsManager extends Service implements LocationListener {
	
	private final Context mContext;
	
	boolean isGPSEnabled=false;
	boolean isNetworkEnabled=false;
	boolean canGetLocation=false;
	
	Location location;
	double latitude;
	double longitude;
	
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;   //10 metara
	private static final long MIN_TIME_BW_UPDATES=1000*60*1;		 //1 minut
	
	protected LocationManager locationManager;
	
	public GpsManager(Context context){
		this.mContext=context;
		getLocation();
	}
	
	public Location getLocation(){
		try{
			
			locationManager=(LocationManager) mContext.getSystemService(LOCATION_SERVICE);
			
			isGPSEnabled=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			isNetworkEnabled=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			
			if(!isGPSEnabled && !isNetworkEnabled){
				showAlert();
			}else{
				this.canGetLocation=true;
				
				
				//Ako idemo preko neta
				if(isNetworkEnabled){
					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_DISTANCE_CHANGE_FOR_UPDATES, MIN_TIME_BW_UPDATES, this);
					Log.d("Network", "Network");
					if(locationManager!=null){
						location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if(location!=null){
							latitude=location.getLatitude();
							longitude=location.getLongitude();
						}
					}
				}
				
				//Ako idemo preko GPSa
				
				if(isGPSEnabled){
					if(location==null){
						locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						Log.d("GPS Enabled","GPS Enabled");
						if(locationManager!=null){
							location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
								if(location!=null){
									latitude=location.getLatitude();
									longitude=location.getLongitude();
								}
					}
				  }	
				}
				
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return location;
	}
	
	
	public void stopUsingGPS(){
		if(locationManager!=null){
			locationManager.removeUpdates(GpsManager.this);
		}
	}
	
	public double getLatitude(){
		if(location!=null){
			latitude=location.getLatitude();
		}
		return latitude;
	}
	
	public double getLongitude(){
		if(location!=null){
			longitude=location.getLongitude();
		}
		return longitude;
	}
	
	//Provera da li je GPS / WiFI ukljucen
	
	public boolean canGetLocation(){
		return this.canGetLocation;
	}
	
	public void showAlert(){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
		
		alertDialog.setTitle("GPS nije ukljucen");
		alertDialog.setMessage("GPS nije ukljucen. Da li zelite da ga ukljucite iz 'Settings' menija?");
		
		alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				mContext.startActivity(intent);
				
			}
		});
		
		alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				
			}
		});
		alertDialog.show();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
