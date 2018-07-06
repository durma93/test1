package rs.org.amss.model;

import android.widget.ImageView;
import rs.org.amss.core.ImageStreamingTask;

public class Camera {
	public Camera(String title, String url) {
		this.title = title;
		this.url = url;
	}
	
	public String title;
	public String url;
	public ImageStreamingTask imageStreamingTask;
	public ImageView cameraImageView;
}
