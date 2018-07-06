package rs.org.amss.core;

import java.util.ArrayList;

import rs.org.amss.model.AmssStation;
import rs.org.amss.model.GetFonts;
import rs.org.amss.R;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NetworkLocationsListAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	public static Activity activity;
	public Context mContext;
	private ArrayList<AmssStation> stations;

	public NetworkLocationsListAdapter(Context context, ArrayList<AmssStation> stations) {
		mContext = context;
		this.stations = stations;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return stations != null ? stations.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return stations.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ListItemHolder holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.location_item, null);
			holder = new ListItemHolder();
			holder.icon = (ImageView)convertView.findViewById(R.id.icon);
			holder.firstLine = (TextView)convertView.findViewById(R.id.firstLine);
			holder.secondLine = (TextView)convertView.findViewById(R.id.secondLine);
			holder.distance = (TextView)convertView.findViewById(R.id.distance);

			holder.firstLine.setTypeface(GetFonts.getTypeface(mContext));
			holder.secondLine.setTypeface(GetFonts.getTypeface(mContext));
			holder.distance.setTypeface(GetFonts.getTypeface(mContext));
			
			convertView.setTag(holder);
		} else {
			holder = (ListItemHolder)convertView.getTag();			
		}

		AmssStation s = stations.get(position);
		holder.icon.setImageResource(s.iconDrawableId != 0 ? s.iconDrawableId : R.drawable.marker_novi);
		holder.firstLine.setText(s.name);
		holder.secondLine.setText(s.address + ", " + s.city);
		
		float[] distance = new float[3];
		Location.distanceBetween(Variables.latitude, Variables.longitude, s.latitude, s.longitude, distance);
		
		if (distance[0] < 1000.0) 
			holder.distance.setText((int)distance[0]+"m");
		else if (distance[0] < 3000000.0) // Show distance in km if distance > 1km and < 3000km
			holder.distance.setText((int)(distance[0]/1000) + "km");
		else
			holder.distance.setText("/");

		return convertView;
	}

	public class ListItemHolder{
		public ImageView icon;
		public TextView firstLine;
		public TextView secondLine;
		public TextView distance;
	}
	
	public interface NetworkGridAdapterListener {
		public void filterChangedReceived();
	}
}

