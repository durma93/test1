package rs.org.amss.shell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import org.apache.http.client.ClientProtocolException;

import rs.org.amss.R;
import rs.org.amss.core.ListAdapter;
import rs.org.amss.core.Variables;
import rs.org.amss.core.WebMethods;
import rs.org.amss.core.WebResponseParser;
import rs.org.amss.model.RoadCondition;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class BordersActivity extends BaseActivity {
	public static String INTENT_EXTRA = "country";
	public Context context = this;
	public ProgressDialog progressDialog;
	String country;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_pages);
		country = (String)getIntentExtra(INTENT_EXTRA);
		setHomeAction(R.drawable.ic_granice, R.string.roads_borders_text, BordersCountryGetActivity.class);

		findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});

		((TextView)findViewById(R.id.title)).setText(R.string.info_for_vehicle_owners_title);
		((TextView)findViewById(R.id.subtitle)).setText("/ " + getString(R.string.roads_borders_text).toUpperCase(Locale.GERMANY));

		checkIsLogIn(BordersActivity.this);

		initializeBorderActivity(country);

	}
	private void initializeBorderActivity(String country) {
		progressDialog = ProgressDialog.show(BordersActivity.this, getResources().getString(R.string.roads_borders_text), getResources().getString(R.string.NetworkActivity_ProgressBar_Message));
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					progressDialog.cancel();
					BordersActivity.this.finish();
					return true;
				}
				return false;
			}
		});
		new GetBordersTask().execute(country);		
	}
	protected class GetBordersTask extends AsyncTask<String, Void, ArrayList<RoadCondition>> {			

		protected ArrayList<RoadCondition> doInBackground(String... parameters) {
			ArrayList<RoadCondition> items = new ArrayList<RoadCondition>();
			if (items == null || items.size() == 0){
				String response;
				try {
					response = WebMethods.getRoadConditionsByCountry(parameters[0].replaceAll(" ", "%20")); 				
					items = WebResponseParser.getRoadConditionList(response);
					Variables.borders = items;
				} catch (ClientProtocolException e) {
					e.printStackTrace();
					BordersActivity.this.getLayoutManager().showError(R.string.Loading_ClientProtocolException);
				} catch (IOException e) {
					e.printStackTrace();
					BordersActivity.this.getLayoutManager().showError(R.string.Loading_IOException);
				} catch (Exception e) {
					e.printStackTrace();
					BordersActivity.this.getLayoutManager().showError(R.string.Loading_Exception);
				}
			}
			return items;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}

		protected void onPostExecute(ArrayList<RoadCondition> result) {

			if (result != null && result.size() > 0){ 
				ListView info = (ListView)findViewById(R.id.itemList);

				ArrayList<String> texts = new ArrayList<String>();
				for (RoadCondition border : result)
					texts.add(border.name);

				Integer[] imageIds = new Integer[result.size()];
				for(int i = 0; i < imageIds.length; i++) {
					if(country.equalsIgnoreCase("HRVATSKA")) {
						imageIds[i] = R.drawable.gp_cro;
					} else if(country.equalsIgnoreCase("MAĐARSKA")) {
						imageIds[i] = R.drawable.gp_hu;
					}  else if(country.equalsIgnoreCase("BUGARSKA")) {
						imageIds[i] = R.drawable.gp_bulg;
					}  else if(country.equalsIgnoreCase("MAKEDONIJA")) {
						imageIds[i] = R.drawable.gp_ma;
					}  else if(country.equalsIgnoreCase("CRNA GORA")) {
						imageIds[i] = R.drawable.gp_mne;
					} else if(country.equalsIgnoreCase("BOSNA I HERCEGOVINA")) {
						imageIds[i] = R.drawable.gp_bih;
					}  else if(country.equalsIgnoreCase("RUMUNIJA")) {
						imageIds[i] = R.drawable.gp_ro;
					}  else {
						imageIds[i] = R.drawable.infogranicniprelzai;
					}
				}

				ListAdapter adapter = new ListAdapter(BordersActivity.this, imageIds, texts.toArray(new String[texts.size()]));
				info.setAdapter(adapter);
				info.setClickable(false);
				info.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Variables.singleBorder = Variables.borders.get(arg2);
						activityManager.startActivity(BordersInfoActivity.class);
					}
				});
			}else{
				BordersActivity.this.getLayoutManager().showInfo(R.string.no_result_message);
				finish();
			}
			progressDialog.cancel();
		}
	}

}

