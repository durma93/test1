package rs.org.amss.shell;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import rs.org.amss.R;
import rs.org.amss.model.GetFonts;

public class InfoTagActivity extends BaseActivity {
	public final static String INTENT_EXTRA = "position";

	private TextView textAboveTable;
	private TextView textBelowTable;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_tag_text);

		findViewById(R.id.back_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
		setHomeAction(R.drawable.ic_main_info, "", InfoActivity.class);
		checkIsLogIn(InfoTagActivity.this);

		((TextView)findViewById(R.id.title)).setText(R.string.info_for_vehicle_owners_title);
		((TextView)findViewById(R.id.subtitle)).setText("/ " + getString(R.string.info_tag_text));

		textAboveTable = (TextView) findViewById(R.id.text_above_table);
		textAboveTable.setTypeface(GetFonts.getTypeface(getApplicationContext()));
		textBelowTable = (TextView) findViewById(R.id.text_below_table);
		textBelowTable.setTypeface(GetFonts.getTypeface(getApplicationContext()));
	}
}
