package rs.org.amss.core;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class ImageStreamingTask extends AsyncTask<String, Void, Void> {
	private static final String TAG = "ImageStreamingTask";
	private ImageView _imageView;
	private DownloadImageTask _downloadImageTask;
	private String _url;
	private boolean _stopped = false;
	
	public ImageStreamingTask(ImageView imageView) {
		_imageView = imageView;
	}
	
	@Override
	protected Void doInBackground(String... urls) {
		while (!_stopped) {
			try {
				_url = urls[0];
				_downloadImageTask = new DownloadImageTask(_imageView);
				_downloadImageTask.execute(_url);
                Log.d(TAG, "doInBackground: doing");
				if (isCancelled())
					break;
				
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Log.d(TAG, "Stoping sleep for task which streams url: " + _url);
			}
		}
		
		return null;
	}
	
	@Override
	protected void onCancelled() {
		Log.d(TAG, "Stopping image streaming for url: " + _url);
		_stopped = true;
		if (_downloadImageTask != null) {
			_downloadImageTask.cancel(true);
		}
		super.onCancelled();
	}
}