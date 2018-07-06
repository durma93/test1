package rs.org.amss.core;

import java.util.ArrayList;

import rs.org.amss.model.LoginStep2;
import rs.org.amss.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LoginStep2ItemAdapter extends BaseAdapter {

	protected final Activity activity;
	protected final ArrayList<LoginStep2> items;
	protected final LayoutInflater inflater;
	
	public LoginStep2ItemAdapter(Activity activity, ArrayList<LoginStep2> items){
		this.activity = activity;
		this.items = items;
		this.inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		LoginStep2 item = items.get(position);
		if (item == null)
			item = new LoginStep2();
		return item;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final LoginStep2 item = (LoginStep2)getItem(position);
		final LoginStep2ItemHolder holder;
		if (convertView == null){
			convertView = inflater.inflate(R.layout.login_step1_item, null);
			holder = new LoginStep2ItemHolder();
			holder.textView = (TextView)convertView.findViewById(R.id.textLoginStep1Item);
			convertView.setTag(holder);
		}
		else
			holder = (LoginStep2ItemHolder)convertView.getTag();
		
		holder.textView.setText(item.registrationPlate);		
		
		return convertView;
	}

	public class LoginStep2ItemHolder{
		public TextView textView;
	}
	
}
