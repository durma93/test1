package rs.org.amss.shell;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import rs.org.amss.R;

public class AdvicesInfoActivity extends BaseActivity implements OnClickListener{

	public final static String INTENT_EXTRA = "position";

	private TextView text;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Button showOffices;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text);
		showOffices = (Button) findViewById(R.id.poslovnice);
		showOffices.setVisibility(View.GONE);

		final int position = (Integer) getIntentExtra(INTENT_EXTRA);

		setHomeAction(R.drawable.ic_main_info, AdvicesForVehicleKeepingActivity.TextIds[position], InfoActivity.class);

		((TextView)findViewById(R.id.title)).setText(R.string.info_for_vehicle_owners_title);
		((TextView)findViewById(R.id.subtitle)).setText("/ " + getString(R.string.advices_for_vehicle_owners_title) + " / " + getString(AdvicesForVehicleKeepingActivity.TextIds[position]));

		findViewById(R.id.back_button).setOnClickListener(this);
		checkIsLogIn(AdvicesInfoActivity.this);
		text = (TextView) findViewById(R.id.text);
		text.setText(AdvicesForVehicleKeepingActivity.TextInfoIds[position]);
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
