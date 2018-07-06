package rs.org.amss.core;

import rs.org.amss.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ActionGridViewAdapter extends BaseAdapter {
	private Context mContext;
	Integer width, height;
	public ActionGridViewAdapter(Context context) {
		mContext = context;
	}

	public int getCount() {
		return TextIds.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	//	public void getSizeOfScreen(){
	//		DisplayMetrics outMetrics = new DisplayMetrics ();
	//
	//		float density  = mContext.getResources().getDisplayMetrics().density;
	//		float dpHeight = outMetrics.heightPixels / density;
	//		float dpWidth  = outMetrics.widthPixels / density;
	//
	//		width=(int) (dpWidth);
	//		height=(int) (dpHeight);
	//	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView == null) {
			view = LayoutInflater.from(mContext).inflate(R.layout.main_action,
					null);
			view.setMinimumHeight((mContext.getResources().getDisplayMetrics().heightPixels/4)-20);
			view.setMinimumWidth((mContext.getResources().getDisplayMetrics().widthPixels/4));
			view.setLayoutParams(new GridView.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		}
		else {
			view = convertView;
		}

		TextView textView = (TextView) view.findViewById(R.id.actionText);
		textView.setText(TextIds[position]);
		textView.setPadding(0, 0, 0, 5);

		ImageView imageView = (ImageView) view.findViewById(R.id.actionImage);
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		//		imageView.setPadding(5, 5, 5, 5);
		imageView.setPadding(5, 5, 5, 0);
		imageView.setImageResource(ImageIds[position]);
		return view;
	}

	// references to resource texts
	private Integer[] TextIds = {
			R.string.main_roadstate_text,          // stanje na putevima
			R.string.main_cameras_text,            // kamere
			R.string.main_parking_text,            // parking
			R.string.main_members_text,            // clanstvo
			R.string.main_helponroad_text,         // pomoc
			R.string.main_insurance_text,          // amss osiguranje
			R.string.main_near,                    // u blizini
			R.string.main_registration_calculator, // kalkulator registracije
			R.string.main_info_text,               // informacije
	};

	// references to resource images
	private Integer[] ImageIds = {
			R.drawable.roads,
			R.drawable.kamere,
			R.drawable.parking,
			R.drawable.membership,
			R.drawable.help,
			R.drawable.amso,
			R.drawable.u_blizini,
			R.drawable.kalkulator_registracija,
			R.drawable.main_info,
	};

}
