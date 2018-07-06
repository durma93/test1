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

public class LoyaltyInfoActivity extends BaseActivity {

	public final static String INTENT_EXTRA = "activityExtra";

	public final static String INTENT_EXTRA_BENEFIT_TYPE = "benfitType";

	protected int typeOfMembership;
	ProgressDialog progressDialog;
	private Integer[] ImageIds = { R.drawable.loyalty_slepovanje,
			R.drawable.loyalty_pomoc, R.drawable.loyalty_pravni_savet,
			R.drawable.loyalty_procena, R.drawable.loyalty_rezervni,
			R.drawable.loyalty_torba, R.drawable.loyalty_popust };
	private Integer[] TextIds = { R.string.loyalty_basic_itinerer,
			R.string.loyalty_basic_advice, R.string.loyalty_basic_free_help,
			R.string.loyalty_basic_free_CMV, R.string.loyalty_basic_repair,
			R.string.loyalty_basic_traffic, R.string.loyalty_basic_discount };

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_pages);
		checkIsLogIn(LoyaltyInfoActivity.this);

		setHomeAction(R.drawable.ic_membership, R.string.loyalty_basic_text,
				LoyaltyActivity.class);

		ListView info = (ListView) findViewById(R.id.itemList);
		ListAdapter adapter = new ListAdapter(getApplicationContext(),
				ImageIds, TextIds);
		info.setAdapter(adapter);
		info.setClickable(false);

		TextView napomene = (TextView) findViewById(R.id.text_napomene);
		napomene.setTypeface(GetFonts.getTypeface(LoyaltyInfoActivity.this));
		napomene.setText(R.string.loyalty_napomene_text);
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
