package rs.org.amss.core;

import java.util.ArrayList;

import rs.org.amss.model.GetFonts;
import rs.org.amss.model.PaymentInvoice;
import rs.org.amss.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PaymentInvoiceAdapter extends BaseAdapter {

	protected Activity activity;
	protected ArrayList<PaymentInvoice> items;
	private LayoutInflater inflater;
	
	public PaymentInvoiceAdapter(Activity activity, ArrayList<PaymentInvoice> items){
		this.activity = activity;
		this.items = items;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return -1;
	}
	
	public PaymentInvoice get(int position){
		return (PaymentInvoice)getItem(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.payment_invoice_item, null);
			holder = new ViewHolder();
			holder.payee = (TextView)convertView.findViewById(R.id.textPayee);
			holder.purpose = (TextView)convertView.findViewById(R.id.textPurpose);
			holder.amount = (TextView)convertView.findViewById(R.id.textAmount);
			holder.payee_txt = (TextView)convertView.findViewById(R.id.primalac);
			holder.purpose_txt = (TextView)convertView.findViewById(R.id.svrha_placanja);
			holder.amount_txt = (TextView)convertView.findViewById(R.id.iznos);
			holder.payee.setTypeface(GetFonts.getTypeface(activity));
			holder.amount.setTypeface(GetFonts.getBoldTypeface(activity));
			holder.purpose.setTypeface(GetFonts.getTypeface(activity));
			holder.payee_txt.setTypeface(GetFonts.getTypeface(activity));
			holder.amount_txt.setTypeface(GetFonts.getBoldTypeface(activity));
			holder.purpose_txt.setTypeface(GetFonts.getTypeface(activity));
			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder)convertView.getTag();
		
		PaymentInvoice item = get(position);
		holder.payee.setText(item.payee);
		holder.purpose.setText(item.purpose);
		holder.amount.setText(String.valueOf((int)item.amount) + ",00 rsd");
		
		return convertView;
	}
	
	public class ViewHolder{
		public TextView payee;
		public TextView purpose;
		public TextView amount;
		public TextView payee_txt;
		public TextView purpose_txt;
		public TextView amount_txt;
		
	}

}
