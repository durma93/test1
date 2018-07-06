package rs.org.amss.shell;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import rs.org.amss.R;
import rs.org.amss.core.Variables;
import rs.org.amss.model.GetFonts;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewsInfoActivity extends BaseActivity {

	public Context context = this;
	public ProgressDialog progressDialog;
	String selectedDate;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news);
		setHomeAction(R.drawable.ic_vesti, R.string.header_news,
				NewsActivity.class);
		checkIsLogIn(NewsInfoActivity.this);

		try {
			Date _date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
					.parse(Variables.singleNews.date);
			selectedDate = new SimpleDateFormat("dd.MM.yyyy").format(_date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		TextView newsTitle = (TextView) findViewById(R.id.newsTitle);
		TextView newsText = (TextView) findViewById(R.id.newsText);

		newsTitle.setText(selectedDate + " " + Variables.singleNews.title);
		newsText.setText(Variables.singleNews.newsText);

		newsTitle.setTypeface(GetFonts.getBoldTypeface(context));
		newsText.setTypeface(GetFonts.getTypeface(context));

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
