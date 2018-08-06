package rs.org.amss.shell;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.client.ClientProtocolException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rs.org.amss.R;
import rs.org.amss.core.ImageStreamingTask;
import rs.org.amss.core.StaticStrings;
import rs.org.amss.core.Variables;
import rs.org.amss.core.WebMethods;
import rs.org.amss.core.WebResponseParser;
import rs.org.amss.model.Camera;
import rs.org.amss.model.CameraNew;

public class CameraActivity extends BaseActivity implements OnMapReadyCallback {
    public static final String TAG = "CameraActivity";

    private static final String CAMERAS_CACHE_PREFIX = "cameras_cache_";

    private ImageStreamingTask imageStreamingTask;

    ProgressBar progressBar;

    int rotation;
    LatLng latLng;
    VideoView videoView;

    private GoogleMap mMap;

    TextView title, subtitle, subSubtitle;
    ImageView cameraImageView;

    Double latitude, longetude;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_layout);
        checkIsLogIn(this);
        Bundle data = getIntent().getExtras();

        title = (TextView)findViewById(R.id.title);



        subtitle = (TextView)findViewById(R.id.subtitle);

        subSubtitle = (TextView)findViewById(R.id.subSubtitle);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Bundle bundle = getIntent().getExtras();

        cameraImageView = (ImageView) findViewById(R.id.camera_image_view);

        imageStreamingTask = new ImageStreamingTask(cameraImageView);


        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        try {
            for (CameraNew c : getCameras()) {
                if (c.getNaziv().equals(bundle.getString("url")) && c.getOpis().equalsIgnoreCase((bundle.getString("opis")))) {
                    latitude= Double.valueOf(c.getGeografska_duzina());
                    longetude = Double.valueOf(c.getGeografska_sirina());
                    latLng = new LatLng(longetude,latitude);


                    subtitle.setText(" / "+c.getGrupa());
                    subSubtitle.setText(c.getNaziv());
                    rotation = Integer.parseInt(c.getUgaoKamere());
                    imageStreamingTask.execute(c.getUrl());
                    hideDialog();


                    Log.d(TAG, "onCreate: URL " +c.getUrl());

                    Log.d(TAG, "onCreate: LAT LANG" + latLng);
                    Log.d(TAG, "onCreate:imageStreamingTask " + imageStreamingTask.toString());
                }
                else {
                    Log.e(TAG, "prazan je url");
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        CameraPosition cameraPosition = new CameraPosition.Builder().
                target(new LatLng(longetude,latitude)).zoom(15).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        drawMarker();


        /*mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));*/
    }


    public void drawMarker() {
        Drawable circleDrawable = getResources().getDrawable(R.drawable.camera_new);
        BitmapDescriptor markerIcon = getMarkerIconFromDrawable(circleDrawable);

        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("My Marker")
                .icon(markerIcon);

        options.rotation(rotation);
        mMap.addMarker(options);
    }
    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    protected void onPause() {
        imageStreamingTask.cancel(true);
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
            editor.putString(CAMERAS_CACHE_PREFIX + Variables.camera, reponse);
            editor.commit();

        } catch (Exception e) {
            e.printStackTrace();

            // Try to pull data from cache
            String cachedZones = prefs.getString(CAMERAS_CACHE_PREFIX + Variables.camera, null);

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
        return (java.util.ArrayList<rs.org.amss.model.CameraNew>) cameras;
    }

    public void showDialog(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideDialog(){
        progressBar.setVisibility(View.GONE);
    }

}
