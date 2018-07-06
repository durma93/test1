package rs.org.amss.core;

import rs.org.amss.model.AmssStationFilter;
import rs.org.amss.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class NetworkGridViewAdapter extends BaseAdapter {
	private Context mContext;
	private AmssStationFilter adapterFilter;
	private NetworkGridAdapterListener adapterListener;

	public NetworkGridViewAdapter(Context context) {
		mContext = context;
		adapterFilter = new AmssStationFilter();
	}

	public NetworkGridViewAdapter(Context context,
			AmssStationFilter filter) {
		mContext = context;
		adapterFilter = new AmssStationFilter();
	}

	public int getCount() {
		return mTextIds.length;
	}

	public Object getItem(int position) {
		return null;
	}
	public void setFilter(AmssStationFilter filter){
		adapterFilter = filter;
	}

	public long getItemId(int position) {
		return 0;
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
					adapterFilter.isForInternationalDocuments = isChecked;
					break;
				case 1:
					adapterFilter.isForTechnicalReview = isChecked;
					break;
				case 2:
					adapterFilter.isForAmssMembership = isChecked;
					break;
				case 3:
					adapterFilter.isForRegistration = isChecked;
					break;
				case 4:
					adapterFilter.isForInsurance = isChecked;
					break;
				case 5:
					adapterFilter.isForHelpOnRoad = isChecked;
					break;
				case 6:
					adapterFilter.isForTowing = isChecked;
					break;
				case 7:
					adapterFilter.hasDrivingSchool = isChecked;
					break;
				case 8:
					adapterFilter.isForVehicleInspectionAttest = isChecked;
					break;
				case 9:
					adapterFilter.hasCarService = isChecked;
					break;
				case 10:
					adapterFilter.hasCurrencyExchange = isChecked;
					break;
				case 11:
					adapterFilter.hasTNGPump = isChecked;
					break;
				case 12:
					adapterFilter.hasCarWash = isChecked;
					break;
				case 13:
					adapterFilter.isForTourism = isChecked;
					break;
				case 14:
					adapterFilter.hasSalesAndComplementOfTAGToll = isChecked;
					break;
				case 15:
					adapterFilter.hasShop = isChecked;
					break;
				case 16:
					adapterFilter.hasRestaurant = isChecked;
					break;
				case 17:
					adapterFilter.isForSpedition = isChecked;
					break;
				}
				fireFilterChangedEvent();
			}
		});

		return convertView;
	}

	public AmssStationFilter getFilter(){
		return adapterFilter;
	}

	public synchronized void setListener(NetworkGridAdapterListener listener) {
		adapterListener = listener;
	}

	protected synchronized void fireFilterChangedEvent() {
		if (adapterListener != null)
			adapterListener.filterChangedReceived();
	}

	protected Integer[] mTextIds = {
			R.string.network_international_documents,
			R.string.network_tehnical_review,
			R.string.network_amss_membership,
			R.string.network_registration,
			R.string.network_insurance,	
			R.string.network_help_on_road,
			R.string.network_towing,
			R.string.network_driving_school,
			R.string.network_atest,
			R.string.network_auto_service,	
			R.string.network_exchange_office,	
			R.string.network_tng_pump,	
			R.string.network_car_wash,	
			R.string.network_tourism,	
			R.string.network_tag,	
			R.string.network_shop,	
			R.string.network_restaurant,
			R.string.network_spedition,			
	};

	public interface NetworkGridAdapterListener {
		public void filterChangedReceived();
	}	

	public class NetworkGridViewAdapterHolder{
		public CheckBox checkBox;
		public int position;
	}
	private static boolean getChecked(AmssStationFilter filter, int position)
	{
		boolean result = (position == 0 && filter.isForInternationalDocuments) || 
				(position == 0 && filter.isForInternationalDocuments) ||
				(position == 14 && filter.hasSalesAndComplementOfTAGToll);
		return result;
	}
}
