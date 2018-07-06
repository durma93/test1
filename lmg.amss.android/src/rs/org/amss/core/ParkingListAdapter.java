package rs.org.amss.core;

import java.util.ArrayList;

import rs.org.amss.model.GetFonts;
import rs.org.amss.model.ParkingCityNew;
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
	/*ArrayList<String> zones;
	String[] numbers;
	Integer[] ImageIds;
    String[] info;
    String[] price;
    String[] maxTime;
*/
    ArrayList<ParkingCityNew> newListParking = new ArrayList<>();

    public ParkingListAdapter(Context context) {
    	this.context = context;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	/*public ParkingListAdapter(Context context, ArrayList<String> zones2, String[] numbers, Integer[] images, String[] info, String[] price, String[] maxTime){
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
	}*/

    public ParkingListAdapter(Context context, ArrayList<ParkingCityNew> newListParking) {
        this.context = context;
        this.newListParking = newListParking;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void setData(ArrayList<ParkingCityNew> list ) {
        newListParking = list;
    }
    @Override
	public int getCount() {
		if (newListParking == null)
			return 0;
		return newListParking.size();
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

		ParkingCityNew zone = newListParking.get(position);
		holder.name.setText(zone.getCity());
		holder.send.setText(zone.getSmsNumber());
		holder.send.setTypeface(GetFonts.getBoldTypeface(context));
//		holder.name.setTypeface(GetFonts.getBoldTypeface(context));

        String infoText = "";
		if (!zone.getMaxTime().isEmpty())
			infoText += zone.getMaxTime() + ". ";
		infoText += zone.getFromUntil();
		if(!zone.getPrice().isEmpty())
			infoText += " (" +zone.getPrice()+ ")";
		holder.info.setText(infoText);

		if (zone.getIcon().equals("crvena")){
            holder.image.setImageResource(R.drawable.zona1);
        }else if (zone.getIcon().equals("zuta")){
            holder.image.setImageResource(R.drawable.zona2);
        }else if (zone.getIcon().equals("zelena")){
            holder.image.setImageResource(R.drawable.zona3);
        }else if (zone.getIcon().equals("plava")){
        holder.image.setImageResource(R.drawable.dnevna_karta);
    }
		//holder.image.setImageResource(ImageIds[position]);
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
