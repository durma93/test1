package rs.org.amss.core;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import rs.org.amss.R;

public class TextStringListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    public static Activity activity;
    public Context mContext;
    String[] TextIds;

    public TextStringListAdapter(Context context, String[] texts) {
        mContext = context;
        TextIds = texts;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return TextIds != null ? TextIds.length : 0;
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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.text_list_item, null);
            holder = new ListItemHolder();
            holder.text = (TextView) convertView.findViewById(R.id.text);

            convertView.setTag(holder);
        } else {
            holder = (ListItemHolder) convertView.getTag();
        }

        holder.text.setText(TextIds[position]);

        return convertView;
    }

    public class ListItemHolder {
        public TextView text;

    }
}

