package rs.org.amss.shell;

import rs.org.amss.R;
import rs.org.amss.model.GetFonts;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserManualActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		memoryManager.loadVariables();

		setContentView(R.layout.user_manual);
		setHomeAction(R.drawable.ic_header_info, R.string.header_info,
				HeaderInfoActivity.class);
		checkIsLogIn(UserManualActivity.this);

		TextView text1 = (TextView)findViewById(R.id.action_bar_naslov);
		TextView text2 = (TextView)findViewById(R.id.menu_text);
		TextView text3 = (TextView)findViewById(R.id.txt1);
		TextView text4 = (TextView)findViewById(R.id.txt2);
		TextView text5 = (TextView)findViewById(R.id.txt3);
		TextView text6 = (TextView)findViewById(R.id.txt4);
		TextView text7 = (TextView)findViewById(R.id.help_heading);
		TextView text8 = (TextView)findViewById(R.id.help_text);
		TextView text9 = (TextView)findViewById(R.id.condition_heading);
		TextView text10 = (TextView)findViewById(R.id.condition_text);
		TextView text11 = (TextView)findViewById(R.id.membership_heading);
		TextView text12 = (TextView)findViewById(R.id.membership_text);
		TextView text13 = (TextView)findViewById(R.id.network_heading);
		TextView text14 = (TextView)findViewById(R.id.network_text);
		TextView text15 = (TextView)findViewById(R.id.info_naslov);
		TextView text16 = (TextView)findViewById(R.id.info_text);
		TextView text17 = (TextView)findViewById(R.id.text1);
		TextView text18 = (TextView)findViewById(R.id.text2);
		TextView text19 = (TextView)findViewById(R.id.text3);
		TextView text20 = (TextView)findViewById(R.id.text4);
		TextView text21 = (TextView)findViewById(R.id.text5);
		TextView text22 = (TextView)findViewById(R.id.text6);
		TextView text23 = (TextView)findViewById(R.id.text7);
		TextView text24 = (TextView)findViewById(R.id.text8);
		TextView text25 = (TextView)findViewById(R.id.text9);
		TextView text26 = (TextView)findViewById(R.id.amso_heading);
		TextView text27 = (TextView)findViewById(R.id.amso_text);
		TextView text28 = (TextView)findViewById(R.id.safety_heading);
		TextView text29 = (TextView)findViewById(R.id.safety_text);
		TextView text30 = (TextView)findViewById(R.id.my_car_heading);
		TextView text31 = (TextView)findViewById(R.id.my_car_text);
		TextView text32 = (TextView)findViewById(R.id.parking_heading);
		TextView text33 = (TextView)findViewById(R.id.parking_text);

		text1.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text2.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text3.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text4.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text5.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text6.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text7.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text8.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text9.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text10.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text11.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text12.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text13.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text14.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text15.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text16.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text17.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text18.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text19.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text20.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text21.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text22.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text23.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text24.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text25.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text26.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text27.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text28.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text29.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text30.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text31.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text32.setTypeface(GetFonts.getTypeface(UserManualActivity.this));
		text33.setTypeface(GetFonts.getTypeface(UserManualActivity.this));

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
