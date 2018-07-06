package rs.org.amss.shell;

import rs.org.amss.R;
import rs.org.amss.model.ActivityExtra;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.PluginState;
import android.widget.TextView;

public class WebViewActivity extends BaseActivity {
	public static final String INTENT_EXTRA = "activityExtra";
	ProgressDialog progressDialog;
	WebView webScreen;
	TextView title;
	TextView subtitle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		ActivityExtra activityData = (ActivityExtra) getIntentExtra(INTENT_EXTRA);
		int homeDrawableId = activityData.get(ActivityExtra.HOME_DRAWABLE_ID);
		int homeTextId = activityData.get(ActivityExtra.HOME_TEXT_ID);
		int textId = activityData.get(ActivityExtra.TEXT_ID);

		title = (TextView)findViewById(R.id.title);
		subtitle = (TextView)findViewById(R.id.subtitle);

		title.setText(homeTextId);
		subtitle.setText(textId);

		String link = activityData.get(ActivityExtra.LINK);
		setHomeAction(homeDrawableId, homeTextId, InfoActivity.class);
		checkIsLogIn(WebViewActivity.this);

		progressDialog = ProgressDialog.show(WebViewActivity.this, getString(homeTextId), getResources().getString(
						R.string.NetworkActivity_ProgressBar_Message));
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					progressDialog.cancel();
					WebViewActivity.this.finish();
					return true;
				}
				return false;
			}
		});
		webScreen = (WebView) findViewById(R.id.webview);
		CookieManager.getInstance().setAcceptCookie(true);
		webScreen.loadUrl(link);

		WebSettings webSettings = webScreen.getSettings();
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setUseWideViewPort(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setPluginState(PluginState.ON);
		webSettings.setBuiltInZoomControls(false);
		webSettings.setSupportZoom(true);
		webScreen.setWebViewClient(new MyWebViewClient());

		findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

	}

	private class MyWebViewClient extends WebViewClient {
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			progressDialog.cancel();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				if (webScreen.canGoBack() == true) {
					webScreen.goBack();
				} else {
					finish();
				}
				return true;
			}

		}
		return super.onKeyDown(keyCode, event);
	}
}
