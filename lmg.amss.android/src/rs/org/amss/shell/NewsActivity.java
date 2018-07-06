package rs.org.amss.shell;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import rs.org.amss.R;
import rs.org.amss.core.ListAdapter;
import rs.org.amss.core.Variables;
import rs.org.amss.core.WebMethods;
import rs.org.amss.core.WebResponseParser;
import rs.org.amss.model.News;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class NewsActivity extends BaseActivity {

	public Context context = this;
	public ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_pages);
		setHomeAction(R.drawable.ic_vesti, R.string.header_news,
				MainActivity.class);
		checkIsLogIn(NewsActivity.this);

		initializeNewsActivity();
	}

	private void initializeNewsActivity() {
		progressDialog = ProgressDialog.show(
				NewsActivity.this,
				getResources().getString(R.string.header_news),
				getResources().getString(
						R.string.NetworkActivity_ProgressBar_Message));
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					progressDialog.cancel();
					NewsActivity.this.finish();
					return true;
				}
				return false;
			}
		});
		new GetNewsTask().execute();
	}

	protected class GetNewsTask extends
			AsyncTask<Object, Void, ArrayList<News>> {

		protected ArrayList<News> doInBackground(Object... parameters) {
			ArrayList<News> items = new ArrayList<News>();
			if (items == null || items.size() == 0) {
				String response;
				try {
					response = WebMethods.getNews();
					items = WebResponseParser.getNewsList(response);

					Variables.news = items;

				} catch (ClientProtocolException e) {
					e.printStackTrace();
					NewsActivity.this.getLayoutManager().showError(
							R.string.Loading_ClientProtocolException);
				} catch (IOException e) {
					e.printStackTrace();
					NewsActivity.this.getLayoutManager().showError(
							R.string.Loading_IOException);
				} catch (Exception e) {
					e.printStackTrace();
					NewsActivity.this.getLayoutManager().showError(
							R.string.Loading_Exception);
				}
			}
			return items;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}

		protected void onPostExecute(ArrayList<News> result) {

			if (result != null && result.size() > 0) {
				ListView info = (ListView) findViewById(R.id.itemList);

				ArrayList<String> texts = new ArrayList<String>();
				for (News news : result)
					texts.add(news.title);

				ListAdapter adapter = new ListAdapter(NewsActivity.this, null,
						texts.toArray(new String[texts.size()]));
				info.setAdapter(adapter);
				info.setClickable(false);
				info.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Variables.singleNews = Variables.news.get(arg2);
						activityManager.startActivity(NewsInfoActivity.class);
					}
				});
			}
			progressDialog.cancel();
		}
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
	public void showNews(View view){
	}
}
