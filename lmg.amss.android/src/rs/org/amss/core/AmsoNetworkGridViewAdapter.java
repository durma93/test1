package rs.org.amss.core;

import rs.org.amss.model.AmsoStationFilter;
import rs.org.amss.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class AmsoNetworkGridViewAdapter extends BaseAdapter {
	private Context mContext;
	private AmsoStationFilter adapterFilter;
	private AmsoNetworkGridAdapterListener adapterListener;

	public AmsoNetworkGridViewAdapter(Context context) {
		mContext = context;
		adapterFilter = new AmsoStationFilter();
	}

	public AmsoNetworkGridViewAdapter(Context context, AmsoStationFilter filter) {
		mContext = context;
		adapterFilter = filter;
	}

	public int getCount() {
		return mTextIds.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	public void setFilter(AmsoStationFilter filter){
		adapterFilter = filter;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		final NetworkGridViewAdapterHolder holder;
		if(convertView==null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.network_item,null);
			holder = new NetworkGridViewAdapterHolder();
			holder.checkBox = (CheckBox)convertView.findViewById(R.id.checkBoxNetworkItem);
			holder.position = position;
			holder.checkBox.setChecked(getChecked(adapterFilter, position));
			convertView.setTag(holder);			
		}
		else
			holder = (NetworkGridViewAdapterHolder)convertView.getTag();

		holder.checkBox.setText(mTextIds[position]);
		holder.checkBox.setTag(position);		
		holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				Integer position = (Integer)buttonView.getTag();
				switch (position){
				case 0:
					adapterFilter.hasAutomobileLiability = isChecked;
					break;
				case 1:
					adapterFilter.hasGreenCard = isChecked;
					break;
				case 2:
					adapterFilter.hasCarAccident = isChecked;
					break;
				case 3:
					adapterFilter.hasKasko = isChecked;
					break;
				case 4:
					adapterFilter.hasKaskoLight = isChecked;
					break;
				case 5:
					adapterFilter.hasVoluntaryHealthInsurance = isChecked;
					break;
				case 6:
					adapterFilter.hasMembership = isChecked;
					break;
				case 7:
					adapterFilter.hasInternationalDocuments = isChecked;
					break;
				}
				fireFilterChangedEvent();
			}
		});

		return convertView;
	}

	public AmsoStationFilter getFilter(){
		return adapterFilter;
	}

	public synchronized void setListener(AmsoNetworkGridAdapterListener listener) {
		adapterListener = listener;
	}

	protected synchronized void fireFilterChangedEvent() {
		if (adapterListener != null)
			adapterListener.filterChangedReceived();
	}

	protected Integer[] mTextIds = {
			R.string.amso_network_auto_liability,
			R.string.amso_network_green_card,
			R.string.amso_network_car_accident,
			R.string.amso_network_kasko,	
			R.string.amso_network_kasko_light,
			R.string.amso_network_voluntary_health_insurance,
			R.string.amso_network_membership,
			R.string.amso_network_international_documents,
	};

	public interface AmsoNetworkGridAdapterListener {
		public void filterChangedReceived();
	}	

	public class NetworkGridViewAdapterHolder{
		public CheckBox checkBox;
		public int position;
	}

	private static boolean getChecked(AmsoStationFilter filter, int position)
	{
		boolean result = (position == 0 && filter.hasAutomobileLiability) ||
				(position == 1 && filter.hasGreenCard) ||
				(position == 2 && filter.hasCarAccident) ||
				(position == 3 && filter.hasKasko) ||
				(position == 4 && filter.hasKaskoLight) ||
				(position == 5 && filter.hasVoluntaryHealthInsurance) ||
				(position == 6 && filter.hasMembership) ||
				(position == 7 && filter.hasInternationalDocuments);
		return result;
	}
}
