package rs.org.amss.shell;

import rs.org.amss.R;
import rs.org.amss.core.ListAdapter;
import rs.org.amss.model.GetFonts;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class LoyaltySuperInfoActivity extends BaseActivity {

	public final static String INTENT_EXTRA = "activityExtra";

	public final static String INTENT_EXTRA_BENEFIT_TYPE = "benfitType";

	protected int typeOfMembership;
	ProgressDialog progressDialog;
	private Integer[] ImageIds = { R.drawable.srbija,
			R.drawable.loyalty_slepovanje, R.drawable.loyalty_slepovanje,
			R.drawable.loyalty_pomoc, R.drawable.loyalty_hotel,
			R.drawable.loyalty_refudancija, R.drawable.evropa,
			R.drawable.loyalty_pomoc, R.drawable.loyalty_hotel,
			R.drawable.loyalty_pravni_savet, R.drawable.loyalty_refudancija,
			R.drawable.loyalty_show_card };
	private Integer[] TextIds = { R.string.loyalty_super_srbija,
			R.string.loyalty_super_one, R.string.loyalty_super_two,
			R.string.loyalty_super_three, R.string.loyalty_super_four,
			R.string.loyalty_super_five, R.string.loyalty_super_six,
			R.string.loyalty_super_seven, R.string.loyalty_super_eight,
			R.string.loyalty_super_nine, R.string.loyalty_super_ten,
			R.string.loyalty_super_eleven };

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_pages);
		checkIsLogIn(LoyaltySuperInfoActivity.this);

		setHomeAction(R.drawable.ic_membership, R.string.loyalty_super_text,
				LoyaltyActivity.class);

		ListView info = (ListView) findViewById(R.id.itemList);
		ListAdapter adapter = new ListAdapter(getApplicationContext(),
				ImageIds, TextIds);
		info.setAdapter(adapter);
		info.setClickable(false);

		TextView napomene = (TextView) findViewById(R.id.text_napomene);
		napomene.setTypeface(GetFonts
				.getTypeface(LoyaltySuperInfoActivity.this));
		napomene.setText(R.string.loyalty_napomene_text);
	}
}
