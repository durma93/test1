package rs.org.amss.shell;

import rs.org.amss.R;
import rs.org.amss.core.StaticStrings;
import rs.org.amss.model.ActivityExtra;
import rs.org.amss.model.GetFonts;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InternationalDocumentsInfoActivity extends BaseActivity implements OnClickListener{

	public final static String INTENT_EXTRA = "position";

	private TextView text;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Button showOffices;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text);
		showOffices = (Button) findViewById(R.id.poslovnice);
		showOffices.setTypeface(GetFonts.getBoldTypeface(InternationalDocumentsInfoActivity.this));
		
		final int position = (Integer) getIntentExtra(INTENT_EXTRA);

		setHomeAction(R.drawable.ic_main_info,
				InternationalDocumentsActivity.TextIds[position],
				InfoActivity.class);


		((TextView)findViewById(R.id.title)).setText(R.string.info_for_vehicle_owners_title);
        ((TextView)findViewById(R.id.subtitle)).setText("/ " + getString(R.string.international_documents_title) + " / " + getString(InternationalDocumentsActivity.TextIds[position]));

		findViewById(R.id.back_button).setOnClickListener(this);
		checkIsLogIn(InternationalDocumentsInfoActivity.this);
		text = (TextView) findViewById(R.id.text);
		text.setText(InternationalDocumentsActivity.TextInfoIds[position]);

		showOffices.setVisibility(View.VISIBLE);
		if (position == 3)
			showOffices.setText(getString(R.string.camping_text_button_offices_text));
		showOffices.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (position == 3) {
					ActivityExtra activityData = new ActivityExtra();
					activityData.add(ActivityExtra.HOME_DRAWABLE_ID,
							R.drawable.ic_main_info);
					activityData.add(ActivityExtra.HOME_TEXT_ID,
							R.string.info_cci_kartice_label);
					activityData.add(ActivityExtra.LINK,
							StaticStrings.campingLocationAddress);
					activityManager.startActivityWithParam(
							WebViewActivity.class,
							WebViewActivity.INTENT_EXTRA, activityData);
				} else {
					activityManager.startActivityWithParam(NetworkActivity.class, NetworkActivity.INTENT_EXTRA, NetworkActivity.INTERNATIONAL_DOCUMENTS_FILTER);
				}
//					activityManager.startActivityWithParam(
//							InternationalDocumentsOfficesActivity.class,
//							InternationalDocumentsOfficesActivity.INTENT_EXTRA,
//							position);
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
        onBackPressed();
    }
}
