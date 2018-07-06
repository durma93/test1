package rs.org.amss.shell;

import rs.org.amss.R;
import rs.org.amss.core.Variables;
import rs.org.amss.model.GetFonts;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoyaltyActivity extends BaseActivity {

	public Context context = this;
//	private Integer[] ImageIds = {
//		R.drawable.osnovna_kartica,
//		R.drawable.super_clanska_kartica,
//		R.drawable.popusti_i_ustede,
//		R.drawable.postanite_clan
//	};
//
//	private Integer[] TextIds = {
//		R.string.loyalty_basic_text,
//		R.string.loyalty_super_text,
//		R.string.loyalty_sales_text,
//		R.string.loyalty_become_partner_text
//	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clanstvonovo);
		checkIsLogIn(this);

		if (Variables.getUser() != null && Variables.getUser().getMembershipId() > 0) {
			activityManager.startActivity(MembershipActivity.class);
			finish();
		}

		((TextView) findViewById(R.id.title)).setText("AMSS ČLANSTVO");
		findViewById(R.id.subtitle).setVisibility(View.INVISIBLE);

		Button postaniclanButton = (Button) findViewById(R.id.postaniclan);
		postaniclanButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				activityManager.startActivity(LoyaltyBecomeActivity.class);
			}
		});
		Button loginButton = (Button) findViewById(R.id.loginButton);
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				activityManager.startActivity(LoginActivity.class);
			}
		});

		postaniclanButton.setTypeface(GetFonts.getBoldTypeface(this));
		loginButton.setTypeface(GetFonts.getBoldTypeface(this));

		findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
	}
}
