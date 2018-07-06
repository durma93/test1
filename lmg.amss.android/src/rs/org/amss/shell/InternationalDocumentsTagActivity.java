package rs.org.amss.shell;

import rs.org.amss.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class InternationalDocumentsTagActivity extends BaseActivity {
	public final static String INTENT_EXTRA = "position";

	private TextView text;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Button showOffices;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tag_text);
		showOffices = (Button) findViewById(R.id.poslovnice);
//		showOffices.setTypeface(GetFonts.getTypeface(InternationalDocumentsTagActivity.this));
		
		final int position = (Integer) getIntentExtra(INTENT_EXTRA);


		findViewById(R.id.back_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
		setHomeAction(R.drawable.ic_main_info,
				InternationalDocumentsActivity.TextIds[position],
				InfoActivity.class);
		checkIsLogIn(InternationalDocumentsTagActivity.this);
		TextView text1 = (TextView) findViewById(R.id.text1);
		TextView text2 = (TextView) findViewById(R.id.text2);
		TextView text3 = (TextView) findViewById(R.id.text3);
		TextView text4 = (TextView) findViewById(R.id.text4);
		TextView text5 = (TextView) findViewById(R.id.text5);
		TextView text6 = (TextView) findViewById(R.id.text6);
		TextView text7 = (TextView) findViewById(R.id.text7);
		TextView text8 = (TextView) findViewById(R.id.text8);

//		text1.setTypeface(GetFonts
//				.getTypeface(InternationalDocumentsTagActivity.this));
//		text2.setTypeface(GetFonts
//				.getTypeface(InternationalDocumentsTagActivity.this));
//		text3.setTypeface(GetFonts
//				.getTypeface(InternationalDocumentsTagActivity.this));
//		text4.setTypeface(GetFonts
//				.getTypeface(InternationalDocumentsTagActivity.this));
//		text5.setTypeface(GetFonts
//				.getTypeface(InternationalDocumentsTagActivity.this));
//		text6.setTypeface(GetFonts
//				.getTypeface(InternationalDocumentsTagActivity.this));
//		text7.setTypeface(GetFonts
//				.getTypeface(InternationalDocumentsTagActivity.this));
//		text8.setTypeface(GetFonts
//				.getTypeface(InternationalDocumentsTagActivity.this));
		TextView link = (TextView) findViewById(R.id.link);
//		link.setTypeface(GetFonts
//				.getTypeface(InternationalDocumentsTagActivity.this));

		text = (TextView) findViewById(R.id.text);
		text.setText(InternationalDocumentsActivity.TextInfoIds[position]);
//		text.setTypeface(GetFonts.getTypeface(getApplicationContext()));
		showOffices.setVisibility(View.VISIBLE);
		showOffices.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				activityManager.startActivityWithParam(
						InternationalDocumentsOfficesActivity.class,
						InternationalDocumentsOfficesActivity.INTENT_EXTRA,
						position);
			}

		});
	}
}
