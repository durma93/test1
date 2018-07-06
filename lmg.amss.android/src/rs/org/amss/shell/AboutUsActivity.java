package rs.org.amss.shell;

import rs.org.amss.R;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AboutUsActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		memoryManager.loadVariables();

		setContentView(R.layout.about);
		setHomeAction(R.drawable.ic_header_info, R.string.header_info,
				HeaderInfoActivity.class);
		checkIsLogIn(AboutUsActivity.this);
		TextView title = (TextView)findViewById(R.id.title);
		TextView app_ver = (TextView)findViewById(R.id.app_ver);
		TextView lmg_text = (TextView)findViewById(R.id.lmg_text);
		
//		title.setTypeface(GetFonts.getTypeface(AboutUsActivity.this));
//		app_ver.setTypeface(GetFonts.getTypeface(AboutUsActivity.this));
//		lmg_text.setTypeface(GetFonts.getTypeface(AboutUsActivity.this));

		((TextView) findViewById(R.id.title)).setText("O aplikaciji");
		((TextView) findViewById(R.id.subtitle)).setText("");

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
		
	}

	//	public void goToLMG(View v){
	//		ActivityExtra activityData = new ActivityExtra();
	//		activityData.add(ActivityExtra.HOME_DRAWABLE_ID, R.drawable.header_info);
	//		activityData.add(ActivityExtra.HOME_TEXT_ID, R.string.header_info_about_app);
	//		activityData.add(ActivityExtra.LINK, StaticStrings.lmgWebAddress);
	//		activityManager.startActivityWithParam(WebViewActivity.class, WebViewActivity.INTENT_EXTRA, activityData);
	//	}
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
