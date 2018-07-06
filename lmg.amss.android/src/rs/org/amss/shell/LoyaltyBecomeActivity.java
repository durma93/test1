package rs.org.amss.shell;

import rs.org.amss.R;

import net.lmggroup.utility.ActivityHelper;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoyaltyBecomeActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		memoryManager.loadVariables();

		setContentView(R.layout.become_loyalty);
        setHomeAction(R.drawable.ic_membership, R.string.loyalty_become_partner_text, LoyaltyActivity.class);
		checkIsLogIn(LoyaltyBecomeActivity.this);

		((TextView)findViewById(R.id.title)).setText("AMSS ČLANSTVO");
		((TextView)findViewById(R.id.subtitle)).setText("/ POSTANI ČLAN");

		WebView vw = (WebView)findViewById(R.id.webview);
		vw.getSettings().setJavaScriptEnabled(true);
		vw.loadUrl("https://webshop.amss.org.rs");
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
	public void GoToIndividualRegistration(View v){
		new ActivityHelper(LoyaltyBecomeActivity.this).startActivityWithParam(LoyaltyBecomeScreenTwoActivity.class, "isIndividual", true);
	}

	public void GoToCompanyRegistration(View v){
		new ActivityHelper(LoyaltyBecomeActivity.this).startActivityWithParam(LoyaltyBecomeScreenTwoActivity.class, "isIndividual", false);
	}
}
