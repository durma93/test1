package rs.org.amss.shell;

import rs.org.amss.R;
import rs.org.amss.model.GetFonts;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AplicationGuideActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		memoryManager.loadVariables();

		setContentView(R.layout.text);
		setHomeAction(R.drawable.ic_header_info, R.string.header_info,
				HeaderInfoActivity.class);
		checkIsLogIn(AplicationGuideActivity.this);
		TextView text = (TextView)findViewById(R.id.text);
		text.setText(getString(R.string.usage_conditions_text));
		text.setTypeface(GetFonts.getTypeface(AplicationGuideActivity.this));
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
