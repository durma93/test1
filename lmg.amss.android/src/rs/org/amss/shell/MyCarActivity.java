package rs.org.amss.shell;

import rs.org.amss.R;
import rs.org.amss.core.ListAdapter;
import rs.org.amss.core.Variables;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MyCarActivity extends BaseActivity {

	public Context context = this;
	private Integer[] ImageIds = { R.drawable.moj_auto_info,
			R.drawable.gde_mi_je_auto };
	private Integer[] TextIds = { R.string.my_car_basic_info_text,
			R.string.mycar_whereismycar_text };
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_pages);
		setHomeAction(R.drawable.ic_mycar, R.string.mycar_label,
				MainActivity.class);
		checkIsLogIn(MyCarActivity.this);
		ListView mycar = (ListView) findViewById(R.id.itemList);
		Variables.car = memoryManager.GetNewCar();
		ListAdapter adapter = new ListAdapter(context, ImageIds, TextIds);
		mycar.setAdapter(adapter);
		mycar.setClickable(true);
		mycar.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					if (Variables.car!= null)
						activityManager
								.startActivity(MyCarBasicInfoActivity.class);
					else
						activityManager
								.startActivity(MyCarOptionsActivity.class);
					break;

				case 1:
					activityManager.startActivity(WhereIsMyCarActivity.class);
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
}
