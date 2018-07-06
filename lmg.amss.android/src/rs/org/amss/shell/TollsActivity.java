package rs.org.amss.shell;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import rs.org.amss.R;
import rs.org.amss.core.TextListAdapter;

import java.util.Locale;

public class TollsActivity extends BaseActivity {

    public Context context = this;
    private Integer[] ImageIds = {R.drawable.kalkulator};
    private Integer[] TextIds = {R.string.info_tolls_calculate_text};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityManager.startActivity(CalculateTollsActivity.class);
        finish();
        setContentView(R.layout.listview_pages);
        setHomeAction(R.drawable.ic_main_info, R.string.info_tolls_text, InfoActivity.class);

        checkIsLogIn(TollsActivity.this);

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ((TextView)findViewById(R.id.title)).setText(R.string.info_for_vehicle_owners_title);
        ((TextView)findViewById(R.id.subtitle)).setText("/ " + getString(R.string.info_tolls_text).toUpperCase(Locale.GERMANY));

        ListView info = (ListView) findViewById(R.id.itemList);
        TextListAdapter adapter = new TextListAdapter(context, TextIds);
        info.setAdapter(adapter);
        info.setClickable(true);
        info.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        activityManager.startActivity(CalculateTollsActivity.class);
                        break;
                }
            }
        });
    }

}
