package rs.org.amss.shell;

import java.io.IOException;

import rs.org.amss.R;
import rs.org.amss.core.WebMethods;
import rs.org.amss.core.WebResponseParser;
import rs.org.amss.model.RoadConditionVideo;

import org.apache.http.client.ClientProtocolException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;

public class RoadConditionsVideoActivity extends BaseActivity {

	private WebView webView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.road_conditions_video);
		checkIsLogIn(RoadConditionsVideoActivity.this);
		setHomeAction(R.drawable.ic_roads, R.string.roads_tv_text, RoadActivity.class);
		webView = (WebView)findViewById(R.id.webRoadConditionsVideo);
		initializeVideo();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			webView.stopLoading();		
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	private void initializeVideo(){
		showProgress();
		new GetVideoTask().execute();
	}	
	
	private class GetVideoTask extends AsyncTask<Object, Void, RoadConditionVideo> {			

		protected RoadConditionVideo doInBackground(Object... parameters) {
			
			RoadConditionVideo result = null;
			String response;
			try {

				response = WebMethods.getRoadConditionsVideo(); 				
				result = WebResponseParser.getRoadConditionVideo(response);

			} catch (ClientProtocolException e) {
				e.printStackTrace();
				RoadConditionsVideoActivity.this.getLayoutManager().showError(R.string.Loading_ClientProtocolException);
			} catch (IOException e) {
				e.printStackTrace();
				RoadConditionsVideoActivity.this.getLayoutManager().showError(R.string.Loading_IOException);
			} catch (Exception e) {
				e.printStackTrace();
				RoadConditionsVideoActivity.this.getLayoutManager().showError(R.string.Loading_Exception);
			}
			return result;
		}

		protected void onPostExecute(RoadConditionVideo result) {

			if (result != null){ 
				setTitle(result.title);
				
				String videoTag = getVideoTag(result.introText);				
				
				// String videoIFrame = String.format("<html><body><iframe class=\"youtube-player\" type=\"text/html\" width=\"560\" height=\"315\" src=\"http://www.youtube.com/embed/%s\" frameborder=\"0\" allowfullscreen></iframe></body></html>", videoTag);
				// webView.loadData(videoIFrame, "text/html", "utf-8");
				
				// String videoUrl = String.format("http://www.youtube.com/watch?v=%s", videoTag);
				// webView.loadUrl(videoUrl);
				
				webView.getSettings().setJavaScriptEnabled(true);
				webView.getSettings().setPluginState(PluginState.ON);
				webView.loadUrl("http://www.youtube.com/embed/" + videoTag + "?autoplay=1&vq=small");
				WebChromeClient webClient = new WebChromeClient();
				webView.setWebChromeClient(webClient);
			}
			
			else{
				getLayoutManager().showWarning(R.string.error_info_tolls_calculate);
			}

			hideProgress();
		}

		private String getVideoTag(String introText){
			String result = introText;
			int index = result.indexOf('}');
			result = result.substring(index + 1);
			index = result.indexOf('{');
			result = result.substring(0, index);
			return result;
		}
	}
}
