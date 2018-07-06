package rs.org.amss.shell;

import rs.org.amss.R;
import rs.org.amss.core.ListAdapter;
import rs.org.amss.core.StaticStrings;
import rs.org.amss.model.ActivityExtra;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AMSOActivity extends BaseActivity {
	public final static String INTENT_EXTRA = "position";

	public Context context = this;
	private Integer[] ImageIds = { R.drawable.autoodgovornost, R.drawable.kasko_osiguranje, R.drawable.kaskolight,
			R.drawable.zdravstveno, R.drawable.osiguranje_domacinsta, null};
	private Integer[] TextIds = { R.string.AMSO_safety_text, R.string.AMSO_kasko_text, R.string.AMSO_kasko_light_text, R.string.AMSO_healthy_insurance_text,
			R.string.AMSO_accidents_text};
	public static final Integer[] TextInfoIds = { R.string.AMSO_safety_long_text, R.string.AMSO_kasko_long_text, R.string.AMSO_kasko_light_long_text, R.string.AMSO_healthy_insurance_long_text,
		R.string.AMSO_accidents_long_text, R.string.AMSO_sales_text};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.amso_listview_pages);
		setHomeAction(R.drawable.ic_amso, R.string.AMSO_label, MainActivity.class);
		findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});

		checkIsLogIn(AMSOActivity.this);

		((TextView) findViewById(R.id.trending1)).setText(R.string.aktuelno1);
        findViewById(R.id.trending1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityExtra activityData = new ActivityExtra();
                activityData.add(ActivityExtra.HOME_DRAWABLE_ID, R.drawable.ic_main_info);
                activityData.add(ActivityExtra.HOME_TEXT_ID, R.string.main_insurance_text);
                activityData.add(ActivityExtra.TEXT_ID, R.string.web_shop_subtitle);
                activityData.add(ActivityExtra.LINK, StaticStrings.webShopLink);
                activityManager.startActivityWithParam(WebViewActivity.class, WebViewActivity.INTENT_EXTRA, activityData);
            }
        });

		((TextView) findViewById(R.id.trending2)).setText(R.string.aktuelno2);
        findViewById(R.id.trending2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityExtra activityData = new ActivityExtra();
                activityData.add(ActivityExtra.HOME_DRAWABLE_ID, R.drawable.ic_main_info);
                activityData.add(ActivityExtra.HOME_TEXT_ID, R.string.main_insurance_text);
                activityData.add(ActivityExtra.TEXT_ID, R.string.web_shop_subtitle);
                activityData.add(ActivityExtra.LINK, StaticStrings.webShopLink);
                activityManager.startActivityWithParam(WebViewActivity.class, WebViewActivity.INTENT_EXTRA, activityData);
            }
        });

		ListView amso = (ListView)findViewById(R.id.itemList);
		ListAdapter adapter = new ListAdapter(context, ImageIds, TextIds);
		amso.setAdapter(adapter);
		amso.setClickable(true);
		amso.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(arg2==5) {
					WebView webView  = new WebView(AMSOActivity.this);
					webView.getSettings().setJavaScriptEnabled(true);
					webView.setWebViewClient(new WebViewClient() {
						public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
							Toast.makeText(AMSOActivity.this, description, Toast.LENGTH_SHORT).show();
						}
					});
					webView .loadUrl("https://webshop.ams.co.rs");
					setContentView(webView);
				} else
					activityManager.startActivityWithParam(AMSODocumentsInfoActivity.class, AMSOActivity.INTENT_EXTRA, arg2);
			}

		});
	}

}

