package rs.org.amss.core;

import java.util.ArrayList;

import rs.org.amss.model.Camera;
import rs.org.amss.model.GetFonts;
import rs.org.amss.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CamerasGridViewAdapter extends BaseAdapter {
	private static String TAG = "CamerasGridViewAdapter";
	private Context mContext;
	private ArrayList<Camera> _cameras;
	
	public CamerasGridViewAdapter(Context context, ArrayList<Camera> cameras) {
		mContext = context;
		_cameras = cameras;
	}

	public int getCount() {
		return _cameras.size();
	}

	public Object getItem(int position) {
		return _cameras.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}


	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.camera, null);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.camera_title);
			holder.title.setTypeface(GetFonts.getTypeface(mContext));

			holder.image = (ImageView) convertView.findViewById(R.id.camera_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.title.setText(_cameras.get(position).title);
		DownloadImageTask dl = new DownloadImageTask(holder.image);
		dl.execute(_cameras.get(position).url);
		
		holder.image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (_cameras.get(position).imageStreamingTask != null) {
					
				} else {
					ImageStreamingTask ist = new ImageStreamingTask((ImageView) view);
					ist.execute(_cameras.get(position).url);
					_cameras.get(position).imageStreamingTask = ist;
				}
			}
		});
		
		return convertView;
	}
	
	private class ViewHolder {
		public TextView title;
		public ImageView image;
	}
}
