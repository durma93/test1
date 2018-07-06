package rs.org.amss.core;

import java.util.ArrayList;

import rs.org.amss.model.RoadCondition;
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

public class RoadConditionLocationsListAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	public static Activity activity;
	public Context mContext;
	private ArrayList<RoadCondition> roadConditions;

	public RoadConditionLocationsListAdapter(Context context, ArrayList<RoadCondition> roadConditions) {
		mContext = context;
		this.roadConditions = roadConditions;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return roadConditions != null ? roadConditions.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return roadConditions.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ListItemHolder holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.stanje_na_putevima_location_list_view, null);
			holder = new ListItemHolder();
			holder.icon = (ImageView)convertView.findViewById(R.id.image);
			holder.firstLine = (TextView)convertView.findViewById(R.id.text);
			holder.secondLine = (TextView)convertView.findViewById(R.id.details);
			holder.distance = (TextView)convertView.findViewById(R.id.distance);
			
			convertView.setTag(holder);
		} else {
			holder = (ListItemHolder)convertView.getTag();			
		}

		RoadCondition rc = roadConditions.get(position);
		if (rc.iconResourceId > 0)
			holder.icon.setImageResource(rc.iconResourceId);
		holder.firstLine.setText(rc.name);
		holder.secondLine.setText(rc.text);
		
		float[] distance = new float[3];
		Location.distanceBetween(Variables.latitude, Variables.longitude, rc.latitude, rc.longitude, distance);
		
		if (distance[0] < 1000.0) 
			holder.distance.setText((int)distance[0]+"m");
		else 
			holder.distance.setText((int)(distance[0]/1000) + "km");

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

