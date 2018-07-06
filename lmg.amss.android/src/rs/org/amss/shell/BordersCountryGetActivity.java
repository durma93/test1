package rs.org.amss.shell;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import rs.org.amss.R;
import rs.org.amss.core.ListAdapter;
import rs.org.amss.core.Variables;
import rs.org.amss.core.WebMethods;
import rs.org.amss.core.WebResponseParser;

import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class BordersCountryGetActivity extends BaseActivity {

    public Context context = this;
    public ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_pages);
        setHomeAction(R.drawable.ic_granice, R.string.roads_borders_text, InfoActivity.class);

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ((TextView)findViewById(R.id.title)).setText(R.string.info_for_vehicle_owners_title);
        ((TextView)findViewById(R.id.subtitle)).setText("/ " + getString(R.string.roads_borders_text).toUpperCase(Locale.GERMANY));

        checkIsLogIn(BordersCountryGetActivity.this);

        initializeBorderActivity();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LinearLayout background = (LinearLayout) findViewById(R.id.background);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            background.setBackgroundResource(R.drawable.walpaper_light_land);
        } else
            background.setBackgroundResource(R.drawable.walpaper_light_copy);
    }

    private void initializeBorderActivity() {
        progressDialog = ProgressDialog.show(BordersCountryGetActivity.this, getResources().getString(R.string.roads_borders_text), getResources().getString(R.string.NetworkActivity_ProgressBar_Message));
        progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    progressDialog.cancel();
                    BordersCountryGetActivity.this.finish();
                    return true;
                }
                return false;
            }
        });
        new GetBordersTask().execute();
    }

    protected class GetBordersTask extends AsyncTask<Object, Void, ArrayList<String>> {

        protected ArrayList<String> doInBackground(Object... parameters) {
            ArrayList<String> items = Variables.borderCountries;
            if (items == null || items.size() == 0) {
                String response;
                try {
                    response = WebMethods.getBorderCountries();
                    items = WebResponseParser.getStringValues(response);

                    Variables.borderCountries = items;

                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                    BordersCountryGetActivity.this.getLayoutManager().showError(R.string.Loading_ClientProtocolException);
                } catch (IOException e) {
                    e.printStackTrace();
                    BordersCountryGetActivity.this.getLayoutManager().showError(R.string.Loading_IOException);
                } catch (Exception e) {
                    e.printStackTrace();
                    BordersCountryGetActivity.this.getLayoutManager().showError(R.string.Loading_Exception);
                }
            }
            return items;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        protected void onPostExecute(ArrayList<String> result) {

            if (result != null && result.size() > 0) {
                ListView info = (ListView) findViewById(R.id.itemList);
                Integer[] imageIds = new Integer[result.size()];
                for(int i = 0; i < imageIds.length; i++) {
                    if(result.get(i).equalsIgnoreCase("HRVATSKA")) {
                        imageIds[i] = R.drawable.gp_cro;
                    } else if(result.get(i).equalsIgnoreCase("MAĐARSKA")) {
                        imageIds[i] = R.drawable.gp_hu;
                    }  else if(result.get(i).equalsIgnoreCase("BUGARSKA")) {
                        imageIds[i] = R.drawable.gp_bulg;
                    }  else if(result.get(i).equalsIgnoreCase("MAKEDONIJA")) {
                        imageIds[i] = R.drawable.gp_ma;
                    }  else if(result.get(i).equalsIgnoreCase("CRNA GORA")) {
                        imageIds[i] = R.drawable.gp_mne;
                    } else if(result.get(i).equalsIgnoreCase("BOSNA I HERCEGOVINA")) {
                        imageIds[i] = R.drawable.gp_bih;
                    }  else if(result.get(i).equalsIgnoreCase("RUMUNIJA")) {
                        imageIds[i] = R.drawable.gp_ro;
                    }  else {
                        imageIds[i] = R.drawable.infogranicniprelzai;
                    }
                }
                ListAdapter adapter = new ListAdapter(BordersCountryGetActivity.this, imageIds,result.toArray(new String[result.size()]));
                info.setAdapter(adapter);
                info.setClickable(false);
                info.setOnItemClickListener(new OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1,
                                            int arg2, long arg3) {
                        activityManager.startActivityWithParam(BordersActivity.class, BordersActivity.INTENT_EXTRA, Variables.borderCountries.get(arg2));
                    }
                });
            }
            progressDialog.cancel();
        }
    }

}

