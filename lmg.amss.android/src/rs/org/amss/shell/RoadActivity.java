package rs.org.amss.shell;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import rs.org.amss.R;
import rs.org.amss.core.ListAdapter;
import rs.org.amss.core.WebMethods;
import rs.org.amss.core.WebResponseParser;
import rs.org.amss.model.RoadConditionVideo;
import android.content.Context;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class RoadActivity extends BaseActivity {

	public Context context = this;
	private Integer[] ImageIds = { R.drawable.stanje_tv, R.drawable.stanje_putevi, R.drawable.stanje_granice, R.drawable.stanje_ogranicenja };
	private Integer[] TextIds = { R.string.roads_tv_text, R.string.roads_info_text, R.string.roads_borders_text, R.string.roads_bounders_text };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_pages);
		setHomeAction(R.drawable.ic_roads, R.string.roads_label, MainActivity.class);
		checkIsLogIn(RoadActivity.this);
		ListView road = (ListView)findViewById(R.id.itemList);
		ListAdapter adapter = new ListAdapter(context, ImageIds, TextIds);
		road.setAdapter(adapter);
		road.setClickable(true);
		road.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0:
					// activityManager.startActivity(RoadConditionsVideoActivity.class);
					showProgress();
					new GetVideoTask().execute();
					break;

				case 1:
					activityManager.startActivity(RoadConditionsActivity.class);
					break;

				case 2:
					activityManager.startActivity(BordersCountryGetActivity.class);
					break;

				case 3:
					activityManager.startActivity(RoadConditionLimitsActivity.class);
					break;
				}
			}

		});
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		LinearLayout background = (LinearLayout)findViewById(R.id.background);

		// Checks the orientation of the screen  
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			background.setBackgroundResource(R.drawable.walpaper_light_land);
		}
		else 
			background.setBackgroundResource(R.drawable.walpaper_light_copy);
	}
	private class GetVideoTask extends AsyncTask<Object, Void, RoadConditionVideo> {			

		protected RoadConditionVideo doInBackground(Object... parameters) {

			RoadConditionVideo result = null;
			String response;
			try {

				response = WebMethods.getRoadConditionsVideo(); 				
				result = WebResponseParser.getRoadConditionVideo(response);

			} catch (ClientProtocolException e) {
				e.printStackTrace();
				RoadActivity.this.getLayoutManager().showError(R.string.Loading_ClientProtocolException);
			} catch (IOException e) {
				e.printStackTrace();
				RoadActivity.this.getLayoutManager().showError(R.string.Loading_IOException);
			} catch (Exception e) {
				e.printStackTrace();
				RoadActivity.this.getLayoutManager().showError(R.string.Loading_Exception);
			}
			return result;
		}

		protected void onPostExecute(RoadConditionVideo result) {

			if (result != null){ 			
				String videoTag = getVideoTag(result.introText);				
				String videoUrl = String.format("http://www.youtube.com/watch?v=%s", videoTag);				
				showVideo(videoUrl);				
			}
			else{
				getLayoutManager().showWarning(R.string.error_info_tolls_calculate);
			}

			hideProgress();
		}

		private String getVideoTag(String introText){
			String result = introText;
			int index = result.indexOf('}');
			result = result.substring(index + 1);
			index = result.indexOf('{');
			result = result.substring(0, index);
			return result;
		}
	}

}

