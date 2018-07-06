package rs.org.amss.shell;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.apache.http.client.ClientProtocolException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rs.org.amss.R;
import rs.org.amss.core.ListCameraNewAdapter;
import rs.org.amss.core.StaticStrings;
import rs.org.amss.core.Variables;
import rs.org.amss.core.WebMethods;
import rs.org.amss.core.WebResponseParser;
import rs.org.amss.model.CameraNew;
import rs.org.amss.model.ParkingCity;

public class CameraActivityNew extends BaseActivity{
    private static final String CAMERAS_CACHE_PREFIX = "cameras_cache_";

    private static final String TAG = "CameraActivityNew";

    ListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_new);
        checkIsLogIn(this);
        setHomeAction(R.drawable.ic_cameras, R.string.main_cameras_text, MainActivity.class);

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        listView = (ListView) findViewById(R.id.listView1);

        try {
            listView.setAdapter(new ListCameraNewAdapter(this, getKategorija()));
        } catch (XmlPullParserException e) {
            e.getMessage();
        }
    }

    public ArrayList<String> getKategorija() throws XmlPullParserException {

        ArrayList<String> a1 = new ArrayList<>();

        for (CameraNew c : getCameras()){
            a1.add(c.getGrupa());
            Set<String> hs = new HashSet<>();
            hs.addAll(a1);
            a1.clear();
            a1.addAll(hs);
        }
        Log.d(TAG, "getKategorija: " + a1.get(0));
        return a1;

    }

    /*public static ArrayList<CameraNew> getListCameras(){


        ArrayList<CameraNew> cam= new ArrayList<>();
        cam.add(new CameraNew(1,"Beograd", "Auto komanda",
                44.792085,20.467352, "", "JPG",200,""));
        cam.add(new CameraNew(2,"Beograd", "Bogoslovija",
                44.815439,20.491433, "", "JPG",""));
        cam.add(new CameraNew(3,"Beograd", "Autoput Novi Beograd",
                44.820363,20.401815, "", "JPG",""));
        cam.add(new CameraNew(4,"Beograd", "Sajam, Mostarska petlja",
                44.797145,20.446272, "", "JPG",""));
        cam.add(new CameraNew(5,"Beograd", "Brankov most, Terazijski tunel, Savski most",
                44.815094,20.454178, "", "JPG",""));

        cam.add(new CameraNew(19,"Granicni prelazi", "Batrovci-Pogled ka Srbiji",
                45.047311,19.107404, "Pogled ka Srbiji", "HLS",
                "https://kamere.amss.org.rs/batrovci1/batrovci1.m3u8"));
        cam.add(new CameraNew(20,"Granicni prelazi", "Batrovci-Pogled ka Hrvatskoj",
                45.047214,19.106631, "Pogled ka Hrvatskoj", "HLS",
                "https://kamere.amss.org.rs/batrovci2/batrovci2.m3u8"));
        cam.add(new CameraNew(21,"Granicni prelazi", "Gradina-Pogled ka Srbiji",
                42.998400,22.830924, "Pogled ka Srbiji", "HLS",
                "https://kamere.amss.org.rs/gradina1/gradina1.m3u8"));
        cam.add(new CameraNew(22,"Granicni prelazi", "Gradina-Pogled ka Bugarskoj",
                42.998891,22.831672, "Pogled ka Bugarskoj", "HLS",
                "https://kamere.amss.org.rs/gradina2/gradina2.m3u8"));
        return cam;
    }*/

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
}
