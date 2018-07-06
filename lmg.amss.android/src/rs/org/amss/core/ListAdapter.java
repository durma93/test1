package rs.org.amss.core;

import rs.org.amss.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	public static Activity activity;
	public Context mContext;
	Integer[] ImageIds;
	Integer[] TextIds;
	String[] Texts;

	public ListAdapter(Context context, Integer[] images, Integer[] texts) {
		mContext = context;
		ImageIds = images;
		TextIds = texts;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public ListAdapter(Context context, Integer[] images, String[] texts) {
		mContext = context;
		ImageIds = images;
		Texts = texts;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		return TextIds != null ? TextIds.length : Texts.length;
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
			convertView = inflater.inflate(R.layout.list_item, null);
			holder = new ListItemHolder();
			holder.image = (ImageView)convertView.findViewById(R.id.image);
			holder.name = (TextView)convertView.findViewById(R.id.text);

			convertView.setTag(holder);
		}
		else
			holder = (ListItemHolder)convertView.getTag();

		if (TextIds != null)
			holder.name.setText(TextIds[position]);
		else if (Texts != null)
			holder.name.setText(Texts[position]);
//		holder.name.setTypeface(GetFonts.getTypeface(mContext));
		if (ImageIds != null && ImageIds.length > position){
			if(ImageIds[position] != null)
				holder.image.setImageResource(ImageIds[position]);
			else
				holder.image.setVisibility(View.GONE);
		}
		else
			holder.image.setLayoutParams(new RelativeLayout.LayoutParams(0,0));

		return convertView;
	}

	public class ListItemHolder{
		public ImageView image;
		public TextView name;
		public Button send;

	}
}

