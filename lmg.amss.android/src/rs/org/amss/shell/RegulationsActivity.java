package rs.org.amss.shell;

import rs.org.amss.R;
import rs.org.amss.core.ListAdapter;
import rs.org.amss.core.StaticStrings;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import java.util.Locale;

public class RegulationsActivity extends BaseActivity {

	public Context context = this;
	public static final Integer[] ImageIds = { R.drawable.infopropisi,
			R.drawable.infopropisi, R.drawable.infopropisi, R.drawable.infopropisi,
			R.drawable.infopropisi, R.drawable.infopropisi, R.drawable.infopropisi };
	public static final Integer[] TextIds = {
			R.string.info_regulations_children,
			R.string.info_regulations_felony, R.string.info_regulations_mk,
			R.string.info_regulations_cro, R.string.info_regulations_mne,
			R.string.info_regulations_zobs,
			R.string.info_regulations_prekrsaji_i_kazne };
	public static final Integer[] TextInfoIds = {
			R.string.info_regulations_children_text,
			R.string.info_regulations_felony_text,
			R.string.info_regulations_mk_text,
			R.string.info_regulations_cro_text,
			R.string.info_regulations_mne_text };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_pages);
		setHomeAction(R.drawable.ic_main_info, R.string.info_orders_text, InfoActivity.class);

		((TextView)findViewById(R.id.title)).setText(R.string.info_for_vehicle_owners_title);
		((TextView)findViewById(R.id.subtitle)).setText("/ " + getString(R.string.info_orders_text).toUpperCase(Locale.GERMANY));

		findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

		checkIsLogIn(RegulationsActivity.this);

		ListView info = (ListView) findViewById(R.id.itemList);
		ListAdapter adapter = new ListAdapter(context, ImageIds, TextIds);
		info.setAdapter(adapter);
		info.setClickable(true);
		info.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 5)
					activityManager.startActivityWithParam(
							WebPdfViewActivity.class,
							WebPdfViewActivity.INTENT_EXTRA,
							StaticStrings.zobsLinkAddress);
				else {
					if (position == 6)
						activityManager.startActivityWithParam(
								WebPdfViewActivity.class,
								WebPdfViewActivity.INTENT_EXTRA,
								StaticStrings.prekrsajiZobsAddress);
					else
						activityManager.startActivityWithParam(
								RegulationsInfoActivity.class,
								RegulationsInfoActivity.INTENT_EXTRA, position);
				}
			}
		});
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		LinearLayout background = (LinearLayout) findViewById(R.id.background);

		// Checks the orientation of the screen
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			background.setBackgroundResource(R.drawable.walpaper_light_land);
		} else
			background.setBackgroundResource(R.drawable.walpaper_light_copy);
	}
}
