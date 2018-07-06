package rs.org.amss.shell;

import rs.org.amss.R;
import rs.org.amss.core.StaticStrings;
import rs.org.amss.model.ActivityExtra;
import rs.org.amss.model.GetFonts;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AMSODocumentsInfoActivity extends BaseActivity {

	public final static String INTENT_EXTRA = "position";

	private TextView text; 
	public Context context = this;
	public Integer[] ImageIds = {
			R.drawable.autoodgovornost,
			R.drawable.kasko_osiguranje,
			R.drawable.kaskolight,
			R.drawable.zdravstveno,
			R.drawable.nezgode};
	public Integer[] PiktIds = {
			R.drawable.pikt_obavezno,
			R.drawable.pikt_kasko,
			R.drawable.pikt_kasko_light,
			R.drawable.pikt_putno,
			R.drawable.pikt_domacinstva};
	private Integer[] TextIds = {
			R.string.AMSO_safety_text,
			R.string.AMSO_kasko_text,
			R.string.AMSO_kasko_light_text,
			R.string.AMSO_healthy_insurance_text,
			R.string.AMSO_accidents_text};

	private Integer[] TextTitleHtmlIds = {
			R.string.title_obavezno,
			R.string.title_kasko,
			R.string.title_kasko_light,
			R.string.title_putno,
			R.string.title_domacinstva};

//	private String[] TextTitleHtml = new String[]{
//			getString(R.string.title_obavezno),
//			getString(R.string.title_kasko),
//			getString(R.string.title_kasko_light),
//			getString(R.string.title_putno),
//			getString(R.string.title_domacinstva)
//	};

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.amso_text);

		final int position = (Integer)getIntentExtra(INTENT_EXTRA);

		Button calculator = (Button)findViewById(R.id.kalkulator);
		Button sellOffices = (Button)findViewById(R.id.poslovnice);

//		((TextView)findViewById(R.id.textTitle2)).setText(Html.fromHtml("<b>KASKO</b> <br/> LIGHT"));

		String title = getString(TextIds[position]);
		String htmlTitle = getString(TextTitleHtmlIds[position]);

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ((TextView) findViewById(R.id.title)).setText("AMS OSIGURANJE");
        ((TextView) findViewById(R.id.subtitle)).setText(title);
        //((TextView) findViewById(R.id.textTitle)).setText(title);
		((TextView) findViewById(R.id.textTitle2)).setText(Html.fromHtml(htmlTitle));
		((ImageView) findViewById(R.id.pikt)).setImageResource(PiktIds[position]);

		checkIsLogIn(AMSODocumentsInfoActivity.this);
		text = (TextView)findViewById(R.id.text);
		text.setText(AMSOActivity.TextInfoIds[position]);

		if(position==0){
			calculator.setVisibility(View.VISIBLE);
            String buttonText = getResources().getString(R.string.AMSO_safety_button);
			calculator.setText(buttonText.toUpperCase());
            calculator.setTypeface(GetFonts.getBoldTypeface(getApplicationContext()));

			calculator.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					activityManager.startActivity(AMSONetworkFilterActivity.class);
				}
			});
			sellOffices.setVisibility(View.VISIBLE);
            buttonText = getResources().getString(R.string.CalculateActivity);
			sellOffices.setText(buttonText.toUpperCase());
            sellOffices.setTypeface(GetFonts.getBoldTypeface(getApplicationContext()));
			sellOffices.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					activityManager.startActivity(CalculateRegistrationActivity.class);
				}
			});
		}
		if(position==1){
			calculator.setVisibility(View.VISIBLE);
			calculator.setText(R.string.AMSO_kasko_light_heading);
			calculator.setTypeface(GetFonts.getTypeface(AMSODocumentsInfoActivity.this));
			calculator.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					activityManager.startActivity(AMSOKaskoActivity.class);
				}
			});

		}

		if(position==2){
			calculator.setVisibility(View.VISIBLE);
			calculator.setText(R.string.AMSO_kasko_light_heading);
			calculator.setTypeface(GetFonts.getTypeface(AMSODocumentsInfoActivity.this));
			calculator.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					activityManager.startActivity(AMSOKaskoLightActivity.class);
				}
			});
		}
		if(position==3 || position==4){
			calculator.setVisibility(View.VISIBLE);
			calculator.setText(R.string.kupite_polisu);
			calculator.setTypeface(GetFonts.getTypeface(AMSODocumentsInfoActivity.this));
			calculator.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ActivityExtra activityData = new ActivityExtra();
					activityData.add(ActivityExtra.HOME_DRAWABLE_ID, R.drawable.ic_main_info);
					activityData.add(ActivityExtra.HOME_TEXT_ID, R.string.main_insurance_text);
					activityData.add(ActivityExtra.TEXT_ID, position == 3 ? R.string.AMSO_healthy_insurance_subtitle : R.string.AMSO_accidents_subtitle);
					activityData.add(ActivityExtra.LINK, StaticStrings.webShopLink);
					activityManager.startActivityWithParam(WebViewActivity.class, WebViewActivity.INTENT_EXTRA, activityData);
				}
			});
		}
        calculator.setTypeface(GetFonts.getBoldTypeface(getApplicationContext()));
        calculator.setText(calculator.getText().toString().toUpperCase());
	}
}
