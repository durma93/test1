package rs.org.amss.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import rs.org.amss.model.GetFonts;
import rs.org.amss.shell.BaseActivity;
import rs.org.amss.R;

import java.util.ArrayList;

public class ListViewDialog extends DialogFragment {
	protected BaseActivity activityContext;
	protected ArrayList<String> items;
    protected ListView listView;
    protected AdapterView.OnItemClickListener itemClickListener;

	@SuppressLint("ValidFragment")
	public ListViewDialog(ArrayList<String> items, BaseActivity context, AdapterView.OnItemClickListener itemClickListener){
		this.activityContext = context;
		this.items = items;
		this.itemClickListener = itemClickListener;
	}
	public ListViewDialog(){}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Dialog dialogView = new Dialog(getActivity());
		dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		dialogView.setContentView(R.layout.list_view_dialog);

		ItemsAdapter adapter = new ItemsAdapter(activityContext, items);

		listView = (ListView) dialogView.findViewById(R.id.list_view);
		listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (itemClickListener != null)
                    itemClickListener.onItemClick(adapterView, view, i, l);

                dismiss();
            }
        });

		dialogView.show();
		return dialogView;
	}

	public ListView getListView() {
	    return listView;
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	public class ItemsAdapter extends ArrayAdapter<String> {
		public ItemsAdapter(Context context, ArrayList<String> items) {
			super(context, 0, items);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// Get the data item for this position
			String item = getItem(position);
			// Check if an existing view is being reused, otherwise inflate the view
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_simple, parent, false);
			}
			// Lookup view for data population
			TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
			// Populate the data into the template view using the data object
			textView.setTypeface(GetFonts.getTypeface(activityContext));
			textView.setText(item);
			// Return the completed view to render on screen
			return convertView;
		}
	}
}
