package rs.org.amss.shell;

import java.io.IOException;
import java.util.ArrayList;

import rs.org.amss.R;
import rs.org.amss.core.ParkingListAdapter;
import rs.org.amss.core.StaticStrings;
import rs.org.amss.core.Variables;
import rs.org.amss.core.WebMethods;
import rs.org.amss.core.WebResponseParser;
import rs.org.amss.model.GetFonts;
import rs.org.amss.model.ParkingCity;
import rs.org.amss.model.ParkingCityNew;
import rs.org.amss.view.ListViewDialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.client.ClientProtocolException;

public class ParkingActivity extends BaseActivity implements OnClickListener {
    private static final String PARKING_ZONES_CACHE_PREFIX = "parking_zones_cache_";
    private static final String PARKING_CITIES_CACHE = "parking_cities_cache";

    public ListView parking;
    public ParkingListAdapter adapter;
    public Context context = this;
    public String TAG = "ParkingActivity";
    public TextView plate;
    public View backButton;
    public TextView cityLabel;
    public TextView cityPlateLabel;
    public TextView digitsPlateLabel;
    public TextView lettersPlateLabel;

    String city = "";
    String[] numbers = new String[]{};
    ArrayList<String> zones = new ArrayList<String>();
    Integer[] ImageIds = new Integer[]{};
    ArrayList<String> cities = new ArrayList<String>();

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking);
        Variables.loadChosenParkingCity(getApplicationContext());
        city = Variables.parking_city_choosed;
        String title = getString(R.string.parking_label);
        if (city != null && city.length() > 0)
            title += " / " + city;
        setHomeAction(R.drawable.ic_parking, title, MainActivity.class);
        checkIsLogIn(ParkingActivity.this);

        cityLabel = (TextView) findViewById(R.id.city);
        backButton = findViewById(R.id.back_button);
        cityPlateLabel = (TextView) findViewById(R.id.cityPlateLabel);
        digitsPlateLabel = (TextView) findViewById(R.id.digitsPlateLabel);
        lettersPlateLabel = (TextView) findViewById(R.id.lettersPlateLabel);
        parking = (ListView) findViewById(R.id.zonesList);

        backButton.setOnClickListener(this);
        plate = (TextView) findViewById(R.id.numberOfPlate);
        plate.setTypeface(GetFonts.getTypeface(ParkingActivity.this));
        cityPlateLabel.setTypeface(GetFonts.getPlaceTypeface(this));
        lettersPlateLabel.setTypeface(GetFonts.getPlaceTypeface(this));
        digitsPlateLabel.setTypeface(GetFonts.getPlaceTypeface(this));
        adapter = new ParkingListAdapter(context);

        parking.setAdapter(adapter);
        parking.setClickable(true);

        if (Variables.parking_city_choosed != null && Variables.parking_city_choosed.length() > 0)
            getZones();

        if (Variables.registrationPlate == null || Variables.registrationPlate.length() == 0) {
            if (Variables.getUser() != null && Variables.getUser().getRegistrationPlate() != null && Variables.getUser().getRegistrationPlate().length() > 0) {
                Variables.registrationPlate = Variables.getUser().getRegistrationPlate();
                Variables.registrationPlate = Variables.registrationPlate.replaceAll("[^A-Za-z0-9 ]", "");
                String pString = Variables.registrationPlate.toUpperCase();
                String pCity = pString.substring(0, 2);
                String pLetters = pString.substring(pString.length() - 2).toUpperCase();
                String pDigits = pString.substring(2, pString.length() - 2).toUpperCase();
                cityPlateLabel.setText(pCity);

                lettersPlateLabel.setText(pLetters);
                digitsPlateLabel.setText(pDigits);
                plate.setText(Variables.registrationPlate.toUpperCase());
            } else
                getDialog(ParkingActivity.this);
            // snima poslednju unetu tablicu; kasnije omoguciti snimanje vise
            // njih
        } else {
            plate.setText(Variables.registrationPlate.toUpperCase());
            String pString = Variables.registrationPlate.toUpperCase();
            String pCity = pString.substring(0, 2);
            String pLetters = pString.substring(pString.length() - 2).toUpperCase();
            String pDigits = pString.substring(2, pString.length() - 2).toUpperCase();
            cityPlateLabel.setText(pCity);

            lettersPlateLabel.setText(pLetters);
            digitsPlateLabel.setText(pDigits);
        }


        memoryManager.saveRegistrationPlate(Variables.registrationPlate);
        memoryManager.saveParkingCity(Variables.parking_city_choosed);
        findViewById(R.id.licencePlate).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                getDialog(ParkingActivity.this);
            }
        });

        SharedPreferences prefs = getSharedPreferences(StaticStrings.CACHE_PREFERENCE, MODE_PRIVATE);
        try {
            final String reponse = WebMethods.getParkingCities();
            cities = WebResponseParser.getParkingCities(reponse);
            // Put cities data into cache
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(PARKING_CITIES_CACHE, reponse);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();

            // Try to pull data from cache
            String cachedCities = prefs.getString(PARKING_CITIES_CACHE, null);
            if (cachedCities == null) {
                int errorResourceId = R.string.Loading_Exception;
                if (e instanceof ClientProtocolException) {
                    errorResourceId = R.string.Loading_ClientProtocolException;
                } else if (e instanceof IOException) {
                    errorResourceId = R.string.Loading_IOException;
                }
                getLayoutManager().showError(errorResourceId);
            } else {
                cities = WebResponseParser.getParkingCities(cachedCities);
            }
        }

        cityLabel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cities == null || cities.size() == 0) {
                    getLayoutManager().showError(R.string.parking_no_cities_error);
                    return;
                }

                ListViewDialog dialog = new ListViewDialog(cities, ParkingActivity.this, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Variables.parking_city_choosed = cities.get(i);
                        Variables.storeChosenParkingCity(getApplicationContext());
                        getZones();
                    }
                });

                FragmentManager fragmentManager = getSupportFragmentManager();
                dialog.show(fragmentManager, "Activity");
            }
        });
    }

    public void getZones() {
        ArrayList<ParkingCityNew> cities = new ArrayList<>();
        SharedPreferences prefs = getSharedPreferences(StaticStrings.CACHE_PREFERENCE, MODE_PRIVATE);
        String naziv;
        try {
            String reponse = WebMethods.getParkingZones(Variables.parking_city_choosed);
            cities = WebResponseParser.getParkingZones(reponse);
            for (ParkingCityNew city : cities) {
                naziv = city.getCity();
                naziv = Variables.parking_city_choosed;
                cityLabel.setText(city.getCity());
                // Put zones data into cache

            }
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(PARKING_ZONES_CACHE_PREFIX + Variables.parking_city_choosed, reponse);
            editor.commit();

            } catch(Exception e){
                e.printStackTrace();

                // Try to pull data from cache
                String cachedZones = prefs.getString(PARKING_ZONES_CACHE_PREFIX + Variables.parking_city_choosed, null);
                if (cachedZones == null) {
                    int errorResourceId = R.string.Loading_Exception;
                    if (e instanceof ClientProtocolException) {
                        errorResourceId = R.string.Loading_ClientProtocolException;
                    } else if (e instanceof IOException) {
                        errorResourceId = R.string.Loading_IOException;
                    }
                    getLayoutManager().showError(errorResourceId);
                    return;
                }

            cities = WebResponseParser.getParkingZones(cachedZones);
            naziv = Variables.parking_city_choosed;
            }

            /*numbers = city.brojevi;
            zones = city.zone;
            ImageIds = city.icons;
            cityLabel.setText(city.name);
            //ListView parking = (ListView) findViewById(R.id.zonesList);
            //ParkingListAdapter adapter = new ParkingListAdapter(context, zones, numbers, ImageIds, city.info, city.price, city.maxTime);
            adapter.setData(zones, numbers, ImageIds, city.info, city.price, city.maxTime);
            adapter.notifyDataSetChanged();
            //parking.setAdapter(adapter);
            //parking.setClickable(true);
        */
        adapter.setData(cities);

        adapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                goBack();
                break;
        }
    }

}
