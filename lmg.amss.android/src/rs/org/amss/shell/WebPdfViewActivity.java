package rs.org.amss.shell;

import rs.org.amss.R;
import rs.org.amss.core.StaticStrings;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.PluginState;
import android.widget.TextView;

import java.util.Locale;

public class WebPdfViewActivity extends BaseActivity {
	public static final String INTENT_EXTRA = "address";
	ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);

		findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
		String address = (String)getIntentExtra(INTENT_EXTRA);
		if(address.equals(StaticStrings.zobsLinkAddress)) {
			((TextView)findViewById(R.id.title)).setText(R.string.info_for_vehicle_owners_title);
			((TextView)findViewById(R.id.subtitle)).setText("/ " + getString(R.string.info_orders_text).toUpperCase(Locale.GERMANY) + " / " + getString(RegulationsActivity.TextIds[5]));
		}
		if(address.equals(StaticStrings.prekrsajiZobsAddress)) {
			((TextView)findViewById(R.id.title)).setText(R.string.info_for_vehicle_owners_title);
			((TextView)findViewById(R.id.subtitle)).setText("/ " + getString(R.string.info_orders_text).toUpperCase(Locale.GERMANY) + " / " + getString(RegulationsActivity.TextIds[6]));
		}
		checkIsLogIn(WebPdfViewActivity.this);

		progressDialog = ProgressDialog.show(WebPdfViewActivity.this, "Propisi", getResources().getString(R.string.NetworkActivity_ProgressBar_Message));
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					progressDialog.cancel();
					WebPdfViewActivity.this.finish();
					return true;
				}
				return false;
			}
		});
		WebView webScreen = (WebView)findViewById(R.id.webview);

		webScreen.loadUrl("http://docs.google.com/gview?embedded=true&url="+address);

		WebSettings webSettings = webScreen.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setPluginState(PluginState.ON);

		webSettings.setBuiltInZoomControls(true);
		webSettings.setSupportZoom(true);
		webScreen.setWebViewClient(new MyWebViewClient());


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
}
