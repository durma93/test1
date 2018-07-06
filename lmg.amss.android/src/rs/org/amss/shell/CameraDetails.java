package rs.org.amss.shell;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.client.ClientProtocolException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rs.org.amss.R;
import rs.org.amss.core.StaticStrings;
import rs.org.amss.core.Variables;
import rs.org.amss.core.WebMethods;
import rs.org.amss.core.WebResponseParser;
import rs.org.amss.model.CameraNew;

public class CameraDetails extends BaseActivity implements OnMapReadyCallback {

    private static final String CAMERAS_CACHE_PREFIX = "cameras_cache_";

    private static final String TAG = "CameraDetails";

    ProgressBar progressBar;

    int rotation;
    LatLng latLng;
    VideoView videoView;

    private GoogleMap mMap;

    TextView textView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_new_details);
        checkIsLogIn(this);
        setHomeAction(R.drawable.ic_cameras, R.string.main_cameras_text, MainActivity.class);

        textView = (TextView)findViewById(R.id.nazivKamere);

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        progressBar = (ProgressBar) findViewById(R.id.progressBar);



        Bundle bundle = getIntent().getExtras();
        videoView = (VideoView) findViewById(R.id.videoView);

        MediaController mc = new MediaController(this);

        videoView.setMediaController(mc);


        try {
            videoView.setVideoURI(Uri.parse(getUrl()));
            videoView.start();
            showDialog();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();

                    mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                        @Override
                        public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {
                            hideDialog();
                            mediaPlayer.start();
                        }
                    });
                }
            });

        } catch (XmlPullParserException e) {
            e.getMessage();
        }


        try {
            for (CameraNew c : getCameras()) {
                if (c.getNaziv().equals(bundle.getString("url")) && c.getOpis().equalsIgnoreCase((bundle.getString("opis")))) {
                    Double latitude= Double.valueOf(c.getGeografska_duzina());
                    Double longetude = Double.valueOf(c.getGeografska_sirina());
                    latLng = new LatLng(longetude,latitude);
                    textView.setText(c.getNaziv()+" - "+c.getOpis());
                    Log.e("ispisUrl-a", "prazan je url");
                }
                else {
                    Log.e("ispisUrl-a", "prazan je url");
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public String getUrl() throws XmlPullParserException {
        Bundle bundle = getIntent().getExtras();
        String url="";

        for (CameraNew c : getCameras()) {
            if (c.getNaziv().equals(bundle.getString("url")) && c.getOpis().equalsIgnoreCase((bundle.getString("opis")))) {
                url = c.getUrl();
                rotation = Integer.parseInt(c.getUgaoKamere());
                Log.e("ispisUrl-a", "prazan je url");
            }
            else {
                Log.e("ispisUrl-a", "prazan je url");
            }
        }
        return url;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        CameraUpdate center=
                CameraUpdateFactory.newLatLng(latLng);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
        drawMarker();
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
