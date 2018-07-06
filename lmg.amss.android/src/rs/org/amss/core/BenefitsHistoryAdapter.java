package rs.org.amss.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import rs.org.amss.model.History;
import rs.org.amss.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BenefitsHistoryAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    public static Activity activity;
    public Context mContext;
    List<History> mHistory;

    public BenefitsHistoryAdapter(Context context, List<History> history) {
        mContext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mHistory = history;
    }


    @Override
    public int getCount() {
        return mHistory != null ? mHistory.size() : 0;
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
        History h = null;

        if (convertView == null) {
            holder = new ListItemHolder();

//			if (mHistory.get(position) instanceof String) {
//				convertView = inflater.inflate(R.layout.membership_history_group_header, null);
//				holder.groupTitle = (TextView) convertView.findViewById(R.id.membership_history_group_titke);
//			} else {
            convertView = inflater.inflate(R.layout.membership_history_item, null);
            holder.location = (TextView) convertView.findViewById(R.id.membership_history_location);
            holder.date = (TextView) convertView.findViewById(R.id.membership_history_date);
            holder.intervention = (TextView) convertView.findViewById(R.id.membership_history_intervention);
            holder.type = (TextView) convertView.findViewById(R.id.membership_history_type);
            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
//			}

            convertView.setTag(holder);
        } else {
            holder = (ListItemHolder) convertView.getTag();
        }

//		if (mHistory.get(position) instanceof String) {
//			holder.groupTitle.setText((String)mHistory.get(position));
//		} else {
        h = (History) mHistory.get(position);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = h.datum_usluge;
        try {
            Date date = format.parse(dateString);
            format = new SimpleDateFormat("dd.MM.yyyy");
            dateString = format.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.date.setText(dateString);
        holder.intervention.setText(h.lokacija);
        holder.location.setText(h.vrsta_intervencije);
        holder.type.setText(h.pogodnost_id.split("-")[1].trim());

        if (h.pogodnost_id.contains("SPI") || h.pogodnost_id.contains("spi")){
            holder.imageView.setImageResource(R.drawable.spiicon);
        }else{
            holder.imageView.setImageResource(R.drawable.slepicon);
        }

//        holder.date.setTypeface(GetFonts.getTypeface(mContext));
//        holder.intervention.setTypeface(GetFonts.getTypeface(mContext));
//        holder.location.setTypeface(GetFonts.getTypeface(mContext));
//		}

        return convertView;
    }

    public class ListItemHolder {
        public TextView date;
        public TextView location;
        public TextView intervention;
        public TextView type;
        public ImageView imageView;
    }
}

