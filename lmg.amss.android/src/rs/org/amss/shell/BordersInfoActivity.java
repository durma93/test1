package rs.org.amss.shell;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import rs.org.amss.R;
import rs.org.amss.core.Variables;
import rs.org.amss.model.GetFonts;

import java.util.Locale;

public class BordersInfoActivity extends BaseActivity {

	public Context context = this;
	public ProgressDialog progressDialog;
	String selectedDate;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news);
		setHomeAction(R.drawable.ic_granice, R.string.roads_borders_text, BordersCountryGetActivity.class);
		checkIsLogIn(BordersInfoActivity.this);


		findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
		((TextView)findViewById(R.id.title)).setText(R.string.info_for_vehicle_owners_title);
		((TextView)findViewById(R.id.subtitle)).setText("/ " + getString(R.string.roads_borders_text).toUpperCase(Locale.GERMANY));

		TextView borderTitle = (TextView) findViewById(R.id.newsTitle);
		TextView borderText = (TextView) findViewById(R.id.newsText);

		borderTitle.setText(Variables.singleBorder.name);
		borderText.setText(Variables.singleBorder.text);

		borderTitle.setTypeface(GetFonts.getBoldTypeface(context));
	}
}
