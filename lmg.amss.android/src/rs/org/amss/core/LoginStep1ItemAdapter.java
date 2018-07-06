package rs.org.amss.core;

import java.util.ArrayList;

import rs.org.amss.model.LoginStep1;
import rs.org.amss.model.TypeOfMember;
import rs.org.amss.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LoginStep1ItemAdapter extends BaseAdapter {

	protected final Activity activity;
	protected final ArrayList<LoginStep1> items;
	protected final LayoutInflater inflater;
	
	public LoginStep1ItemAdapter(Activity activity, ArrayList<LoginStep1> items){
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
		LoginStep1 item = items.get(position);
		if (item == null)
			item = new LoginStep1();
		return item;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final LoginStep1 item = (LoginStep1)getItem(position);
		final LoginStep1ItemHolder holder;
		if (convertView == null){
			convertView = inflater.inflate(R.layout.login_step1_item, null);
			holder = new LoginStep1ItemHolder();
			holder.textView = (TextView)convertView.findViewById(R.id.textLoginStep1Item);
			convertView.setTag(holder);
		}
		else
			holder = (LoginStep1ItemHolder)convertView.getTag();
		
		if (item.typeOfMember.equals(TypeOfMember.INDIVIDUAL))
			holder.textView.setText(item.fullName);
		else // "legal entity"
			holder.textView.setText(item.companyName);
			
		
		return convertView;
	}

	public class LoginStep1ItemHolder{
		public TextView textView;
	}
	
}
