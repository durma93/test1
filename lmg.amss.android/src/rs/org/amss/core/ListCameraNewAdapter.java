package rs.org.amss.core;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import rs.org.amss.R;
import rs.org.amss.shell.CameraActivity;
import rs.org.amss.shell.CameraActivityNew;
import rs.org.amss.shell.CameraActivityNew2;

public class ListCameraNewAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> listaKateogrija;

    public ListCameraNewAdapter(Context context, ArrayList<String> listaKateogrija) {
        this.context = context;
        this.listaKateogrija = listaKateogrija;
    }

    @Override
    public int getCount() {
        return listaKateogrija.size();
    }

    @Override
    public Object getItem(int position) {
        return getItemId(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater;
        if (view ==null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.camera_new_content, null);
        }
        final TextView textContentIspis = (TextView) view.findViewById(R.id.textContentIspis);
        textContentIspis.setText(listaKateogrija.get(position));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CameraActivityNew cameraActivityNew = (CameraActivityNew) context;
                Intent i = new Intent(cameraActivityNew, CameraActivityNew2.class);
                i.putExtra("naziv",textContentIspis.getText());
                Log.e("ispis", "onClick: "+textContentIspis.getText());
                context.startActivity(i);
            }
        });



        return view;
    }

}
