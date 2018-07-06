package rs.org.amss.core;

import java.util.ArrayList;

import rs.org.amss.model.TollCity;
import rs.org.amss.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TollCityAdapter extends BaseAdapter {

	protected Activity activity;
	protected ArrayList<TollCity> items;
	private LayoutInflater inflater;
	
	public TollCityAdapter(Activity activity, ArrayList<TollCity> items){
		this.activity = activity;
		this.items = items;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return get(position).id;
	}
	
	public TollCity get(int position){
		return (TollCity)getItem(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final SpinnerHolder holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.spinner_item, null);
			holder = new SpinnerHolder();
			holder.text = (TextView)convertView.findViewById(R.id.textSpinner);
			convertView.setTag(holder);
		}
		else
			holder = (SpinnerHolder)convertView.getTag();
		
		TollCity item = get(position);
		holder.text.setText(item.name);
		
		return convertView;
	}
	
	public class SpinnerHolder{
		public TextView text;
	}

}
