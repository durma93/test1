package rs.org.amss.shell;

import rs.org.amss.R;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class RegulationsInfoActivity extends BaseActivity {

	public final static String INTENT_EXTRA = "position";

	private TextView textBefore3;
	private TextView textBefore2;
	private TextView textBefore1;

	private TextView text;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text);
		int position = (Integer) getIntentExtra(INTENT_EXTRA);
		setHomeAction(R.drawable.ic_main_info, RegulationsActivity.TextIds[position], InfoActivity.class);

		checkIsLogIn(RegulationsInfoActivity.this);

		((TextView)findViewById(R.id.title)).setText(R.string.info_for_vehicle_owners_title);
		((TextView)findViewById(R.id.subtitle)).setText("/ " + getString(R.string.info_orders_text).toUpperCase(Locale.GERMANY) + " / " + getString(RegulationsActivity.TextIds[position]));

		textBefore1 = (TextView) findViewById(R.id.text_before_1);
		textBefore2 = (TextView) findViewById(R.id.text_before_2);
		textBefore3 = (TextView) findViewById(R.id.text_before_3);

		findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});

		text = (TextView) findViewById(R.id.text);
		text.setText(RegulationsActivity.TextInfoIds[position]);
//		text.setTypeface(GetFonts.getTypeface(getApplicationContext()));

		if (position == 2) {
			textBefore1.setVisibility(View.VISIBLE);
			textBefore2.setVisibility(View.VISIBLE);
			textBefore3.setVisibility(View.VISIBLE);

			textBefore1.setText(R.string.info_regulations_mk_text_before_1);
			textBefore2.setText(R.string.info_regulations_mk_text_before_2);
			textBefore3.setText(R.string.info_regulations_mk_text_before_3);

			View.OnClickListener linkClickListener = new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					activityManager.startActivityWithParam(InternationalDocumentsInfoActivity.class, InternationalDocumentsInfoActivity.INTENT_EXTRA, Integer.parseInt(view.getTag().toString()));
				}
			};

			textBefore1.setTag(1);
			textBefore1.setOnClickListener(linkClickListener);
			textBefore2.setTag(2);
			textBefore2.setOnClickListener(linkClickListener);
		}
	}
}
