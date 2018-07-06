package rs.org.amss.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import rs.org.amss.model.GetFonts;
import rs.org.amss.R;

public class ImportantNumbersListAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	public static Activity activity;
	public Context mContext;
	Integer[] what;
    Integer[] when;
    Integer[] call;


	public ImportantNumbersListAdapter(Context context, Integer[] what, Integer[] when, Integer[] call) {
		mContext = context;
		this.call = call;
        this.what = what;
        this.when = when;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		return when != null ? when.length : 0;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ListItemHolder holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.important_numbers_list_item, null);
			holder = new ListItemHolder();
            holder.what = (TextView) convertView.findViewById(R.id.what);
            holder.when= (TextView) convertView.findViewById(R.id.when);
            holder.call = (Button) convertView.findViewById(R.id.call);

			convertView.setTag(holder);
		}
		else {
            holder = (ListItemHolder) convertView.getTag();
        }

        holder.what.setText(what[position]);
        holder.when.setText(when[position]);

        holder.call.setText(call[position]);
        holder.call.setTypeface(GetFonts.getBoldTypeface(mContext));
        if (position%2 == 1){
            convertView.setBackgroundColor(holder.what.getContext().getResources().getColor(R.color.light_gray_v2));
        }else{
            convertView.setBackgroundColor(holder.what.getContext().getResources().getColor(R.color.white));
        }

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                CharSequence number = ((Button) view).getText();
                if (number.toString().contains("opcija")){
                    number = number.toString().replaceAll("[^\\d.]", "");
                    number = number.toString().substring(0, number.length() - 1);
                }
                intent.setData(Uri.parse("tel:" + number));
                mContext.startActivity(intent);
            }
        });

		return convertView;
	}

	public class ListItemHolder{
		public TextView what;
		public TextView when;
		public Button call;

	}
}

