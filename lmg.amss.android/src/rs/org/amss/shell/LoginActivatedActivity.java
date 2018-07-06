package rs.org.amss.shell;

import rs.org.amss.R;
import rs.org.amss.core.Variables;
import rs.org.amss.model.GetFonts;
import rs.org.amss.model.User;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginActivatedActivity extends BaseActivity {
	public static final String TAG = "SignInActivity";
	protected TextView membershipCardId;
	protected TextView seria;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activated);
		setHomeAction(R.drawable.katanac, R.string.LoginActivity_Label_upper, MainActivity.class);
		checkIsLogIn(LoginActivatedActivity.this);

		TextView membeship_text = (TextView)findViewById(R.id.textView2);
		TextView membership_seria_text = (TextView)findViewById(R.id.textView3);

		membeship_text.setTypeface(GetFonts.getBoldTypeface(getApplicationContext()));
		membership_seria_text.setTypeface(GetFonts.getBoldTypeface(getApplicationContext()));

		membershipCardId = (TextView)findViewById(R.id.memeber_id);
		seria = (TextView)findViewById(R.id.member_card_seria);

		String memberID = Variables.getUser().getMembershipCardId().substring(0, Variables.getUser().getMembershipCardId().length()-2);
		char serial = Variables.getUser().getMembershipCardId().charAt(Variables.getUser().getMembershipCardId().length()-1);
		
		membershipCardId.setText(memberID);
		seria.setText(""+serial);
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		LinearLayout background = (LinearLayout)findViewById(R.id.background);

		// Checks the orientation of the screen  
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			background.setBackgroundResource(R.drawable.walpaper_light_land);
		}
		else 
			background.setBackgroundResource(R.drawable.walpaper_light_copy);
	}
	
	public void ChangeUser(View view) {
		finish();
		Variables.setUser(new User());
		memoryManager.ClearPreferences();
		activityManager.startActivity(LoginActivity.class);
	}
	
	public void showSignIn(View view){
	}


}