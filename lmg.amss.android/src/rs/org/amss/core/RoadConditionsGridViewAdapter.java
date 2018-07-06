package rs.org.amss.core;

import rs.org.amss.model.RoadConditionsFilter;
import rs.org.amss.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class RoadConditionsGridViewAdapter extends BaseAdapter {
	private Context mContext;
	private RoadConditionsFilter adapterFilter;
	private RoadConditionsGridAdapterListener adapterListener;

	public RoadConditionsGridViewAdapter(Context context) {
		mContext = context;
		adapterFilter = new RoadConditionsFilter();
	}

	public int getCount() {
		return mTextIds.length;
	}

	public Object getItem(int position) {
		return null;
	}
	public void setFilter(RoadConditionsFilter filter){
		adapterFilter = filter;
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final RoadConditionsGridViewAdapterHolder holder;
		if(convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.network_item,null);
			holder = new RoadConditionsGridViewAdapterHolder();
			holder.checkBox = (CheckBox)convertView.findViewById(R.id.checkBoxNetworkItem);
			holder.position = position;
			convertView.setTag(holder);			
		} else {
			holder = (RoadConditionsGridViewAdapterHolder)convertView.getTag();
		}
		
		holder.checkBox.setText(mTextIds[position]);
		holder.checkBox.setTag(position);		
		holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Integer position = (Integer)buttonView.getTag();
				switch (position) {
				case 0:
					adapterFilter.vremenskePrilike = isChecked;
					break;
				case 1:
					adapterFilter.radoviNaPutu = isChecked;
					break;
				case 2:
					adapterFilter.obaveznaZimskaOprema = isChecked;
					break;
				case 3:
					adapterFilter.granicniPrelaz = isChecked;
					break;
				case 4:
					adapterFilter.ogranicenjaZaTeretnaVozila = isChecked;
					break;
				case 5:
					adapterFilter.obustaveSaobracaja = isChecked;
					break;
				case 6:
					adapterFilter.informacijeOStanju = isChecked;
					break;
				case 7:
					adapterFilter.crneTacke = isChecked;
					break;
				case 8:
					adapterFilter.klizavKolovoz = isChecked;
					break;
				case 9:
					adapterFilter.opasnostOdOdrona = isChecked;
					break;
				case 10:
					adapterFilter.naizmenicnoPropustanje = isChecked;
					break;
				case 11:
					adapterFilter.jakVetar = isChecked;
					break;
				case 12:
					adapterFilter.opasnostNaPutu = isChecked;
					break;
				}
				fireFilterChangedEvent();
			}
		});

		return convertView;
	}

	public RoadConditionsFilter getFilter() {
		return adapterFilter;
	}

	public synchronized void setListener(RoadConditionsGridAdapterListener listener) {
		adapterListener = listener;
	}

	protected synchronized void fireFilterChangedEvent() {
		if (adapterListener != null)
			adapterListener.filterChangedReceived();
	}

	protected Integer[] mTextIds = {
			R.string.road_condition_1_vremenske_prilike,
			R.string.road_condition_2_radovi_na_putu,
			R.string.road_condition_3_obavezna_zimska_oprema,
			R.string.road_condition_4_granicni_prelazi,
			R.string.road_condition_5_ogranicenja_za_teretna_vozila,	
			R.string.road_condition_6_obustave_saobracaja,
			R.string.road_condition_7_informacije_o_stanju,
			R.string.road_condition_8_crne_tacke,
			R.string.road_condition_9_klizav_kolovoz,
			R.string.road_condition_10_opasnost_od_odrona,	
			R.string.road_condition_11_naizmenicno_propustanje,	
			R.string.road_condition_12_jak_vetar,	
			R.string.road_condition_13_opasnost_na_putu,	
	};

	public interface RoadConditionsGridAdapterListener {
		public void filterChangedReceived();
	}	

	public class RoadConditionsGridViewAdapterHolder {
		public CheckBox checkBox;
		public int position;
	}

}
