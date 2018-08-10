package rs.org.amss.shell;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

import io.fabric.sdk.android.Fabric;

import rs.org.amss.BuildConfig;
import rs.org.amss.R;
import rs.org.amss.core.ActionGridViewAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

public class MainActivity extends BaseActivity {
	public static final String TAG = "MainActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Fabric.with(this, new Crashlytics.Builder().core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()).build());
		setContentView(R.layout.main);
		setActionMenu();
		memoryManager.loadVariables();
		checkIsLogIn(MainActivity.this);

        try {
            ProviderInstaller.installIfNeeded(getApplicationContext());
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            SSLEngine engine = sslContext.createSSLEngine();

        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

	}

    @Override
    protected void onResume() {
        super.onResume();
        checkIsLogIn(MainActivity.this);
    }

	private void setActionMenu() {
		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new ActionGridViewAdapter(this));
		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					showRoadState(view);
					break;

				case 1:
					showCameras(view);
					break;

				case 2:
					showParking(view);
					break;

				case 3:
					showMembers(view);
					break;

				case 4:
					showHelp(view);
					break;

				case 5:
					showInsurance(view);
					break;

				case 6:
					showNetwork(view);
					break;

				case 7:
					showCalculateRegistrationActivity(view);
					break;

				case 8:
					showInfo(view);
					break;
				}
			}
		});

//        // Disable gridView scrolling
//		gridview.setOnTouchListener(new View.OnTouchListener(){
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				return event.getAction() == MotionEvent.ACTION_MOVE;
//			}
//
////		});
	}

	public void showHelp(View view) {
		activityManager.startActivity(CallHelpActivity.class);
	}

	public void showRoadState(View view) {
		//activityManager.startActivity(RoadActivity.class);
		activityManager.startActivity(RoadConditionsActivity.class);
	}

	public void showParking(View view) {
		activityManager.startActivity(ParkingActivity.class);
	}

	public void showNetwork(View view) {
		activityManager.startActivity(NetworkActivity.class);
	}

	public void showSafety(View view) {
		activityManager.startActivity(SafetyActivity.class);
	}

	public void showMyAuto(View view) {
		activityManager.startActivity(MyCarActivity.class);
	}

	public void showInfo(View view) {
		activityManager.startActivity(InfoActivity.class);
	}

	public void showInsurance(View view) {
		activityManager.startActivity(AMSOActivity.class);
	}

	public void showMembers(View view) {
		activityManager.startActivity(LoyaltyActivity.class);
	}

	public void showCalculateRegistrationActivity(View view) {
		activityManager.startActivity(CalculateRegistrationActivity.class);
	}

	public void showMain(View v){

	}
	/* Old menu items

	public void showTv(View view) {
		activityManager.startActivity(TvActivity.class);
	}

	public void showCalculator(View view) {
		activityManager.startActivity(CalculatorActivity.class);
	}

	public void showMap(View view) {
		activityManager.startActivity(MapsActivity.class);
	}

	public void showAbout(View view) {
	}

	 */
	
	public void showCameras(View view) {
		activityManager.startActivity(CameraActivityNew.class);
	}
}
