package rs.org.amss.core;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import rs.org.amss.R;
import rs.org.amss.shell.CameraActivity;
import rs.org.amss.shell.CameraActivityNew2;
import rs.org.amss.shell.CameraDetails;

public class ListCameraNewAdapter2 extends BaseAdapter {

    Context context;
    ArrayList<String> listaKateogrija;
    ArrayList<String> listaOpisa;
    ArrayList<String> listaTehnologija;

    public ListCameraNewAdapter2(Context context, ArrayList<String> listaKateogrija, ArrayList<String> listaOpisa, ArrayList<String> listaTehnologija) {
        this.context = context;
        this.listaKateogrija = listaKateogrija;
        this.listaOpisa = listaOpisa;
        this.listaTehnologija = listaTehnologija;
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

            view = inflater.inflate(R.layout.camera_new_content2, null);
        }
        final TextView textContentIspis = (TextView) view.findViewById(R.id.textContentIspis2);
        final TextView textOpis = (TextView)view.findViewById(R.id.textOpisIspis2);
        final TextView crtica = (TextView)view.findViewById(R.id.crtica);
        textContentIspis.setText(listaKateogrija.get(position));

        textOpis.setText(listaOpisa.get(position));

        if (listaOpisa.get(position).trim().equals("")){
            crtica.setVisibility(View.GONE);
        }
        /*if (listaOpisa.get(position).trim().equals("")){

        }
        else {
            textOpis.setText("  -  " + listaOpisa.get(position));
        }
*/
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getTehnologija = listaTehnologija.get(position);

                if (getTehnologija.equals("JPG")) {
                    CameraActivityNew2 cameraActivityNew = (CameraActivityNew2) context;
                    Intent i = new Intent(cameraActivityNew, CameraActivity.class);
                    i.putExtra("url", textContentIspis.getText());
                    i.putExtra("opis", listaOpisa.get(position));
                    Log.e("ispis", "onClick: " + textContentIspis.getText());
                    context.startActivity(i);

                }else if (getTehnologija.equals("HLS")){

                    CameraActivityNew2 cameraActivityNew = (CameraActivityNew2) context;
                    Intent i = new Intent(cameraActivityNew, CameraDetails.class);
                    i.putExtra("url", textContentIspis.getText());
                    i.putExtra("opis", textOpis.getText());
                    Log.e("ispis", "onClick: " + textOpis.getText());
                    context.startActivity(i);
                }else {
                    Toast.makeText(context, "Ne postoji format za dati stream!!", Toast.LENGTH_SHORT).show();
                }
            }
        });




        return view;
    }

}
