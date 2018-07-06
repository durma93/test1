package rs.org.amss.core;

import java.util.ArrayList;

import rs.org.amss.model.GetFonts;
import rs.org.amss.shell.BaseActivity;
import rs.org.amss.R;
import rs.org.amss.view.SendParkingSmsDialog;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ParkingListAdapter extends BaseAdapter{
	private LayoutInflater inflater;
	public Context context;
	String phoneNo, message;
	ArrayList<String> zones;
	String[] numbers;
	Integer[] ImageIds;
    String[] info;
    String[] price;
    String[] maxTime;

    public ParkingListAdapter(Context context) {
    	this.context = context;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public ParkingListAdapter(Context context, ArrayList<String> zones2, String[] numbers, Integer[] images, String[] info, String[] price, String[] maxTime){
		this.context = context;
		this.zones = zones2;
		this.numbers = numbers;
		this.ImageIds = images;
        this.info = info;
        this.price = price;
        this.maxTime = maxTime;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setData(ArrayList<String> zones2, String[] numbers, Integer[] images, String[] info, String[] price, String[] maxTime) {
		this.zones = zones2;
		this.numbers = numbers;
		this.ImageIds = images;
		this.info = info;
		this.price = price;
		this.maxTime = maxTime;
	}

	@Override
	public int getCount() {
		if (zones == null)
			return 0;
		return zones.size();
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
		final ParkingHolder holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.parking_item, null);
			holder = new ParkingHolder();
			holder.image = (ImageView)convertView.findViewById(R.id.imageView1);
			holder.name = (TextView)convertView.findViewById(R.id.textView1);
			holder.send = (Button)convertView.findViewById(R.id.sendMessageBt);
			holder.container = convertView.findViewById(R.id.container);
			holder.info = (TextView) convertView.findViewById(R.id.textViewInfo);

			convertView.setTag(holder);
		}
		else
			holder = (ParkingHolder)convertView.getTag();

		holder.name.setText(zones.get(position));
		holder.send.setText(numbers[position]);
		holder.send.setTypeface(GetFonts.getBoldTypeface(context));
//		holder.name.setTypeface(GetFonts.getBoldTypeface(context));

        String infoText = "";
		if (!maxTime[position].isEmpty())
			infoText += maxTime[position] + ". ";
		infoText += info[position];
		if(!price[position].isEmpty())
			infoText += " (" +price[position]+ ")";
		holder.info.setText(infoText);

		holder.image.setImageResource(ImageIds[position]);
		if (position%2 == 1){
			holder.container.setBackgroundColor(holder.container.getContext().getResources().getColor(R.color.light_gray_v2));
		}else{
			holder.container.setBackgroundColor(holder.container.getContext().getResources().getColor(R.color.white));
		}
		phoneNo = holder.send.getText().toString();
		message = Variables.registrationPlate;

		holder.send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String number = holder.send.getText().toString();
				sendSms(v, number, Variables.registrationPlate);
				Log.v("msg", "button pressed" );
//				Toast.makeText(context, "Poruka poslata na" + number +"!", Toast.LENGTH_LONG).show();
			}
		});
		return convertView;
	}

	public class ParkingHolder{
		public View container;
		public ImageView image;
		public TextView name;
		public Button send;
		public TextView info;

	}
	public void sendSms(View view, String number, String message)
	{
		FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
		new SendParkingSmsDialog((BaseActivity) context, number, message).show(fragmentManager, "MSG");
	}	
}
