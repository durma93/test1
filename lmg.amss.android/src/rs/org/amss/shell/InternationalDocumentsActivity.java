package rs.org.amss.shell;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import rs.org.amss.R;
import rs.org.amss.core.ListAdapter;

public class InternationalDocumentsActivity extends BaseActivity implements View.OnClickListener {

    public Context context = this;
    public static final Integer[] TextIds = {
            R.string.info_international_documents_MVD_label,
            R.string.info_international_documents_DTV_label,
            R.string.info_international_documents_ZELENA_KARTA_label
    };
    public static final Integer[] TextInfoIds = {
            R.string.info_international_documents_text,
            R.string.info_international_documents_DTV_text,
            R.string.info_international_documents_ZELENA_KARTA_text
    };
    public static final Integer[] ImageIds = {
            R.drawable.infodokumenta,
            R.drawable.infodokumenta,
            R.drawable.infodokumenta
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_pages);
        setHomeAction(R.drawable.ic_main_info, R.string.info_documents_text,
                InfoActivity.class);
        ((TextView)findViewById(R.id.title)).setText(R.string.info_for_vehicle_owners_title);
        ((TextView)findViewById(R.id.subtitle)).setText("/ " + getString(R.string.international_documents_title));

        checkIsLogIn(InternationalDocumentsActivity.this);
        ListView info = (ListView) findViewById(R.id.itemList);
        findViewById(R.id.back_button).setOnClickListener(this);

        ListAdapter adapter = new ListAdapter(context, ImageIds, TextIds);
        info.setAdapter(adapter);
        info.setClickable(true);
        info.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                activityManager.startActivityWithParam(InternationalDocumentsInfoActivity.class, InternationalDocumentsInfoActivity.INTENT_EXTRA, position);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                onBackPressed();
                break;
        }
    }
}
