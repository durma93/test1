package rs.org.amss.shell;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.client.ClientProtocolException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rs.org.amss.R;
import rs.org.amss.core.ListCameraNewAdapter2;
import rs.org.amss.core.StaticStrings;
import rs.org.amss.core.Variables;
import rs.org.amss.core.WebMethods;
import rs.org.amss.core.WebResponseParser;
import rs.org.amss.model.CameraNew;

public class CameraActivityNew2 extends BaseActivity{

    private static final String CAMERAS_CACHE_PREFIX = "cameras_cache_";

    private static final String TAG = "CameraActivityNew2";

    ListView listView;

    TextView subtitle, title;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_new2);
        checkIsLogIn(this);
        setHomeAction(R.drawable.ic_cameras, R.string.main_cameras_text, MainActivity.class);

        subtitle = (TextView)findViewById(R.id.subtitle);

        title = (TextView)findViewById(R.id.title);

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        listView = (ListView) findViewById(R.id.listView2);

        try {
            listView.setAdapter(new ListCameraNewAdapter2(this, getNaziv(),getOpis(),getTehonologija()));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getNaziv() throws XmlPullParserException {
        Bundle bundle = getIntent().getExtras();

        ArrayList<String> a1 = new ArrayList<>();

            for (CameraNew c : getCameras()) {
                if (c.getGrupa().equals(bundle.getString("naziv"))) {
                    a1.add(c.getNaziv());
                    subtitle.setText(" / "+c.getGrupa());
                    Log.e("ispis", "Naziv: "+c.getNaziv());
                }
                else {
                    Log.e("ispis", "prazan je naziv");
                }
            }

        return a1;
    }

    public ArrayList<String> getOpis() throws XmlPullParserException {
        Bundle bundle = getIntent().getExtras();

        ArrayList<String> a1 = new ArrayList<>();

        for (CameraNew c : getCameras()) {

            if (c.getGrupa().equals(bundle.getString("naziv"))) {
                a1.add(c.getOpis());
                Log.e("ispis", "Naziv: "+c.getNaziv());
            }
            else {
                Log.e("ispis", "prazan je naziv");
            }

        }

        return a1;
    }

    public ArrayList<String> getTehonologija() throws XmlPullParserException {
        Bundle bundle = getIntent().getExtras();

        ArrayList<String> a1 = new ArrayList<>();

        for (CameraNew c : getCameras()) {
            if (c.getGrupa().equals(bundle.getString("naziv"))) {
                a1.add(c.getTehnologija());
                Log.e("ispis", "Naziv: "+c.getNaziv());
            }
            else {
                Log.e("ispis", "prazan je naziv");
            }
        }

        return a1;
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

}
