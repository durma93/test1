package rs.org.amss.shell;

import rs.org.amss.R;
import rs.org.amss.core.StaticStrings;
import rs.org.amss.model.ActivityExtra;
import rs.org.amss.model.GetFonts;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoyaltySavingsActivity extends BaseActivity {

	public final static String INTENT_EXTRA = "activityExtra";

	private TextView textView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.savings);
		checkIsLogIn(LoyaltySavingsActivity.this);
		ActivityExtra activityData = (ActivityExtra) getIntentExtra(INTENT_EXTRA);
		int homeDrawableId = activityData.get(ActivityExtra.HOME_DRAWABLE_ID);
		int homeTextId = activityData.get(ActivityExtra.HOME_TEXT_ID);
		int textId = activityData.get(ActivityExtra.TEXT_ID);

		String text = getString(textId).replace("%26", "&");
		
		Button poslovnice = (Button)findViewById(R.id.poslovnice);
		poslovnice.setTypeface(GetFonts.getTypeface(LoyaltySavingsActivity.this));
		
		setHomeAction(homeDrawableId, homeTextId, LoyaltyActivity.class);

		textView = (TextView) findViewById(R.id.text);
		textView.setText(text);
		textView.setTypeface(GetFonts.getTypeface(getApplicationContext()));
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

	public void StartWebViewActivity(View v) {
		ActivityExtra activityData = new ActivityExtra();
		activityData.add(ActivityExtra.HOME_DRAWABLE_ID,
				R.drawable.ic_membership);
		activityData.add(ActivityExtra.HOME_TEXT_ID,
				R.string.loyalty_sales_text);
		activityData
				.add(ActivityExtra.TEXT_ID, R.string.loyalty_discounts_text);
		activityData.add(ActivityExtra.LINK, StaticStrings.showYourCardLink);
		activityManager.startActivityWithParam(WebViewActivity.class,
				WebViewActivity.INTENT_EXTRA, activityData);
	}
}
