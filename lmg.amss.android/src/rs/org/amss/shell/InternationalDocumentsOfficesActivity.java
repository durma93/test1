package rs.org.amss.shell;

import rs.org.amss.R;
import rs.org.amss.core.Variables;
import rs.org.amss.model.AmssStationFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.LinearLayout;

public class InternationalDocumentsOfficesActivity extends BaseActivity {

	public final static String INTENT_EXTRA = "position";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text);

		int position = (Integer) getIntentExtra(INTENT_EXTRA);

		setHomeAction(
				R.drawable.ic_main_info,
				InternationalDocumentsActivity.TextIds[Variables.international_documents_position],
				InternationalDocumentsActivity.class);
		checkIsLogIn(InternationalDocumentsOfficesActivity.this);
		AmssStationFilter filter = new AmssStationFilter();
		if (position == 0 || position == 1) {
			finish();
			filter.isForInternationalDocuments = true;
			getActivityManager().startActivityWithParam(NetworkActivity.class,
					NetworkActivity.INTENT_EXTRA, filter);

		}
		if (position == 2) {
			finish();
			filter.hasSalesAndComplementOfTAGToll = true;
			getActivityManager().startActivityWithParam(NetworkActivity.class,
					NetworkActivity.INTENT_EXTRA, filter);
		}
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
