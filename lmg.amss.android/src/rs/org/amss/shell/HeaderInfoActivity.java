package rs.org.amss.shell;

import java.util.ArrayList;

import rs.org.amss.R;
import rs.org.amss.core.ListAdapter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

public class HeaderInfoActivity extends BaseActivity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_pages);
		setHomeAction(R.drawable.ic_header_info, R.string.header_info,
				MainActivity.class);
		checkIsLogIn(HeaderInfoActivity.this);

		ListView info = (ListView) findViewById(R.id.itemList);

		ArrayList<String> texts = new ArrayList<String>();
			texts.add("Vodič kroz aplikaciju");
			texts.add("Uslovi i prava korišćenja");
			texts.add("O aplikaciji");

		ListAdapter adapter = new ListAdapter(HeaderInfoActivity.this, null, texts.toArray(new String[texts.size()]));
		info.setAdapter(adapter);
		info.setClickable(false);
		info.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				switch (arg2) {
				case 0:
					activityManager.startActivity(UserManualActivity.class);
					break;
				case 1:
					activityManager.startActivity(AplicationGuideActivity.class);
					break;
				case 2:
					activityManager.startActivity(AboutUsActivity.class);
					break;

				default:
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
	public void showSettings(View v){
		
	}
}
