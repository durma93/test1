package rs.org.amss.shell;

import rs.org.amss.R;
import rs.org.amss.core.ListAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class InfoActivity extends BaseActivity implements View.OnClickListener{

	public Context context = this;
	private Integer[] ImageIds = {
			R.drawable.infotelefoni,
			R.drawable.infodokumenta,
			R.drawable.infopropisi,
			R.drawable.infosaveti,
			R.drawable.infoputarine,
			R.drawable.infodaljinari,
			R.drawable.infogranicniprelzai,
			R.drawable.infoogranicenja,
			R.drawable.tag
	};
	private Integer[] TextIds = {
			R.string.info_telephones_text,
			R.string.info_documents_text,
			R.string.info_orders_text,
			R.string.info_advices_and_recommends_text,
			R.string.info_tolls_text,
			R.string.info_destination_text,
			R.string.roads_borders_text,
			R.string.roads_bounders_text,
			R.string.info_tag_text
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_pages);
		setHomeAction(R.drawable.ic_main_info, R.string.info_label,
				MainActivity.class);
		checkIsLogIn(InfoActivity.this);

		findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		((TextView) findViewById(R.id.title)).setText("INFORMACIJE ZA VOZAČE");
		((TextView) findViewById(R.id.subtitle)).setVisibility(View.GONE);

		ListView info = (ListView) findViewById(R.id.itemList);
		ListAdapter adapter = new ListAdapter(context, ImageIds, TextIds);

        findViewById(R.id.back_button).setOnClickListener(this);

		info.setAdapter(adapter);
		info.setClickable(true);
		info.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					activityManager
							.startActivity(ImportantNumbersActivity.class);
					break;

				case 1:
					activityManager
							.startActivity(InternationalDocumentsActivity.class);
					break;

				case 2:
					activityManager.startActivity(RegulationsActivity.class);
					break;

				case 3:
					activityManager
							.startActivity(AdvicesForVehicleKeepingActivity.class);
					break;

				case 4:
					activityManager.startActivity(TollsActivity.class);
					break;

				case 5:
					activityManager.startActivity(CalculateDestinationActivity.class);
					break;
					
				case 6:
					activityManager.startActivity(BordersCountryGetActivity.class);
					break;
					
				case 7:
					activityManager.startActivity(RoadConditionLimitsActivity.class);
					break;

				case 8:
					activityManager.startActivity(InfoTagActivity.class);
					break;
				}
			}

		});
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

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.back_button:
				onBackPressed();
				break;
		}
	}
}
