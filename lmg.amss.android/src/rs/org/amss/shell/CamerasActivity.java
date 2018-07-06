package rs.org.amss.shell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rs.org.amss.R;
import rs.org.amss.core.DownloadImageTask;
import rs.org.amss.core.ImageStreamingTask;
import rs.org.amss.core.StaticStrings;
import rs.org.amss.core.Variables;
import rs.org.amss.core.WebMethods;
import rs.org.amss.core.WebResponseParser;
import rs.org.amss.model.Camera;
import rs.org.amss.model.CameraNew;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.client.ClientProtocolException;
import org.xmlpull.v1.XmlPullParserException;

public class CamerasActivity extends BaseActivity {
    private static final String CAMERAS_CACHE_PREFIX = "cameras_cache_";


    public static final String TAG = "CamerasActivity";
	
	private ArrayList<Camera> cameras;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cameras_layout);
		checkIsLogIn(this);
		setHomeAction(R.drawable.ic_cameras, R.string.main_cameras_text, MainActivity.class);

		findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
		cameras = Variables.getCameras();
		
		cameras.get(0).cameraImageView = (ImageView) findViewById(R.id.camera_image_0);
		cameras.get(1).cameraImageView = (ImageView) findViewById(R.id.camera_image_1);
		cameras.get(2).cameraImageView = (ImageView) findViewById(R.id.camera_image_2);
		cameras.get(3).cameraImageView = (ImageView) findViewById(R.id.camera_image_3);
		cameras.get(4).cameraImageView = (ImageView) findViewById(R.id.camera_image_4);
		cameras.get(5).cameraImageView = (ImageView) findViewById(R.id.camera_image_5);
		cameras.get(6).cameraImageView = (ImageView) findViewById(R.id.camera_image_6);
		cameras.get(7).cameraImageView = (ImageView) findViewById(R.id.camera_image_7);
		cameras.get(8).cameraImageView = (ImageView) findViewById(R.id.camera_image_8);
		cameras.get(9).cameraImageView = (ImageView) findViewById(R.id.camera_image_9);
		cameras.get(10).cameraImageView = (ImageView) findViewById(R.id.camera_image_10);
		cameras.get(11).cameraImageView = (ImageView) findViewById(R.id.camera_image_11);
		cameras.get(12).cameraImageView = (ImageView) findViewById(R.id.camera_image_12);
		cameras.get(13).cameraImageView = (ImageView) findViewById(R.id.camera_image_13);
		cameras.get(14).cameraImageView = (ImageView) findViewById(R.id.camera_image_14);
		cameras.get(15).cameraImageView = (ImageView) findViewById(R.id.camera_image_15);
		cameras.get(16).cameraImageView = (ImageView) findViewById(R.id.camera_image_16);
		cameras.get(17).cameraImageView = (ImageView) findViewById(R.id.camera_image_17);
		
		((TextView) findViewById(R.id.camera_title_0)).setText(cameras.get(0).title);
		((TextView) findViewById(R.id.camera_title_1)).setText(cameras.get(1).title);
		((TextView) findViewById(R.id.camera_title_2)).setText(cameras.get(2).title);
		((TextView) findViewById(R.id.camera_title_3)).setText(cameras.get(3).title);
		((TextView) findViewById(R.id.camera_title_4)).setText(cameras.get(4).title);
		((TextView) findViewById(R.id.camera_title_5)).setText(cameras.get(5).title);
		((TextView) findViewById(R.id.camera_title_6)).setText(cameras.get(6).title);
		((TextView) findViewById(R.id.camera_title_7)).setText(cameras.get(7).title);
		((TextView) findViewById(R.id.camera_title_8)).setText(cameras.get(8).title);
		((TextView) findViewById(R.id.camera_title_9)).setText(cameras.get(9).title);
		((TextView) findViewById(R.id.camera_title_10)).setText(cameras.get(10).title);
		((TextView) findViewById(R.id.camera_title_11)).setText(cameras.get(11).title);
		((TextView) findViewById(R.id.camera_title_12)).setText(cameras.get(12).title);
		((TextView) findViewById(R.id.camera_title_13)).setText(cameras.get(13).title);
		((TextView) findViewById(R.id.camera_title_14)).setText(cameras.get(14).title);
		((TextView) findViewById(R.id.camera_title_15)).setText(cameras.get(15).title);
		((TextView) findViewById(R.id.camera_title_16)).setText(cameras.get(16).title);
		((TextView) findViewById(R.id.camera_title_17)).setText(cameras.get(17).title);
		
		for (final Camera c : cameras) {
			if (c.cameraImageView != null) {
				DownloadImageTask dit = new DownloadImageTask(c.cameraImageView);
				dit.execute(c.url);
				
				c.cameraImageView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if (c.imageStreamingTask != null) {
							c.imageStreamingTask.cancel(true);
							int index = 0;
							for (index = 0; index < Variables.getCameras().size(); index++) {
								if (c == Variables.getCameras().get(index)) {
									break;
								}
							}
							
							activityManager.startActivityWithParam(CameraActivity.class, "CAMERA_INDEX", index);
							
						} else {
							ImageStreamingTask ist = new ImageStreamingTask((ImageView)view);
							c.imageStreamingTask = ist;
							
							ist.execute(c.url);
						}
					}
				});
			}
		}
	}
	
	@Override
	protected void onPause() {
		for (int i = 0; i < Variables.getCameras().size(); i++) {
			Camera c = Variables.getCameras().get(i);
			
			// Cancel all image streamings
			if (c.imageStreamingTask != null) {
				c.imageStreamingTask.cancel(true);
				c.imageStreamingTask = null;
			}
		}
		
		super.onPause();
	}

    public ArrayList<CameraNew> getCameras() throws XmlPullParserException {
        List<CameraNew> cameras;
        SharedPreferences prefs = getSharedPreferences(StaticStrings.CACHE_PREFERENCE, MODE_PRIVATE);
        try {
            String reponse = WebMethods.getCamera();
            cameras = WebResponseParser.getCameras(reponse);

            Log.d(TAG, "getCameras: " + cameras.get(0).getNaziv());

            // Put zones data into cache
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(CAMERAS_CACHE_PREFIX + Variables.parking_city_choosed, reponse);
            editor.commit();

        } catch (Exception e) {
            e.printStackTrace();

            // Try to pull data from cache
            String cachedZones = prefs.getString(CAMERAS_CACHE_PREFIX + Variables.parking_city_choosed, null);
            if (cachedZones == null) {
                int errorResourceId = R.string.Loading_Exception;
                if (e instanceof ClientProtocolException) {
                    errorResourceId = R.string.Loading_ClientProtocolException;
                } else if (e instanceof IOException) {
                    errorResourceId = R.string.Loading_IOException;
                }
                getLayoutManager().showError(errorResourceId);

            }

            cameras = WebResponseParser.getCameras(cachedZones);

        }
        return (ArrayList<CameraNew>) cameras;
    }
}
