package rs.org.amss.shell;

import rs.org.amss.R;
import rs.org.amss.core.ImportantNumbersListAdapter;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Locale;

public class ImportantNumbersActivity extends BaseActivity implements OnClickListener{

	public Context context = this;

    private ListView listView;
	private Integer[] NameIds = {
			R.string.information_emergency_numbers_police_text,
			R.string.information_emergency_numbers_fireman_text,
			R.string.information_emergency_numbers_hospital_text,
			R.string.information_emergency_numbers_amss_text,
			R.string.information_emergency_numbers_time_text,
			R.string.information_emergency_numbers_telegrams_text,
			R.string.information_emergency_numbers_telegrams_sundays_text,
			R.string.information_emergency_numbers_international_calls_text,
			R.string.information_emergency_numbers_military_emergency_text,
			R.string.information_emergency_numbers_phone_interference_text,
			R.string.information_emergency_numbers_city_info_text,
			R.string.information_emergency_numbers_white_pages_text,
			R.string.information_emergency_numbers_waking_text,
			R.string.information_emergency_numbers_info_text,
			R.string.information_emergency_numbers_phone_center_text,
			R.string.information_emergency_numbers_calendar_text,
			R.string.information_emergency_numbers_weather_text,
			R.string.information_emergency_numbers_loto_text,
			R.string.information_emergency_numbers_military_police_text,
			R.string.information_emergency_numbers_mts_text,
			R.string.information_emergency_numbers_vip_text,
			R.string.information_emergency_numbers_telenor_text,
			R.string.information_emergency_numbers_retirement_text,
			R.string.information_emergency_numbers_ptt_text,
			R.string.information_emergency_numbers_ptt_sundays_text };
	private Integer[] WorkingTimeIds = {
			R.string.information_emergency_numbers_police_time_text,
			R.string.information_emergency_numbers_police_time_text,
			R.string.information_emergency_numbers_police_time_text,
			R.string.information_emergency_numbers_police_time_text,
			R.string.information_emergency_numbers_police_time_text,
			R.string.information_emergency_numbers_telegrams_working_time_text,
			R.string.information_emergency_numbers_telegrams_working_time_sundays_text,
			R.string.information_emergency_numbers_police_time_text,
			R.string.information_emergency_numbers_police_time_text,
			R.string.information_emergency_numbers_police_time_text,
			R.string.information_emergency_numbers_police_time_text,
			R.string.information_emergency_numbers_police_time_text,
			R.string.information_emergency_numbers_police_time_text,
			R.string.information_emergency_numbers_police_time_text,
			R.string.information_emergency_numbers_phone_center_working_time_text,
			R.string.information_emergency_numbers_police_time_text,
			R.string.information_emergency_numbers_police_time_text,
			R.string.information_emergency_numbers_police_time_text,
			R.string.information_emergency_numbers_police_time_text,
			R.string.information_emergency_numbers_police_time_text,
			R.string.information_emergency_numbers_police_time_text,
			R.string.information_emergency_numbers_police_time_text,
			R.string.information_emergency_numbers_police_time_text,
			R.string.information_emergency_numbers_ptt_working_time_text,
			R.string.information_emergency_numbers_ptt_working_time_sundays_text };
	private Integer[] NumberIds = {
			R.string.information_emergency_numbers_police_number_text,
			R.string.information_emergency_numbers_fireman_number_text,
			R.string.information_emergency_numbers_hospital_number_text,
			R.string.information_emergency_numbers_amss_number_text,
			R.string.information_emergency_numbers_time_number_text,
			R.string.information_emergency_numbers_telegrams_number_text,
			R.string.information_emergency_numbers_telegrams_sundays_number_text,
			R.string.information_emergency_numbers_international_calls_number_text,
			R.string.information_emergency_numbers_military_emergency_number_text,
			R.string.information_emergency_numbers_phone_interference_number_text,
			R.string.information_emergency_numbers_city_info_number_text,
			R.string.information_emergency_numbers_white_pages_number_text,
			R.string.information_emergency_numbers_waking_number_text,
			R.string.information_emergency_numbers_info_number_text,
			R.string.information_emergency_numbers_phone_center_number_text,
			R.string.information_emergency_numbers_calendar_number_text,
			R.string.information_emergency_numbers_weather_number_text,
			R.string.information_emergency_numbers_loto_number_text,
			R.string.information_emergency_numbers_military_police_number_text,
			R.string.information_emergency_numbers_mts_number_text,
			R.string.information_emergency_numbers_vip_number_text,
			R.string.information_emergency_numbers_telenor_number_text,
			R.string.information_emergency_numbers_retirement_number_text,
			R.string.information_emergency_numbers_ptt_number_text,
			R.string.information_emergency_numbers_ptt_sundays_number_text };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.emergency_numbers);
		setHomeAction(R.drawable.ic_main_info, R.string.info_telephones_text, InfoActivity.class);
		checkIsLogIn(ImportantNumbersActivity.this);

		((TextView)findViewById(R.id.title)).setText(R.string.info_for_vehicle_owners_title);
		((TextView)findViewById(R.id.subtitle)).setText("/ " + getString(R.string.info_telephones_text).toUpperCase(Locale.GERMANY));

        listView = (ListView) findViewById(R.id.listView);
        ImportantNumbersListAdapter adapter = new ImportantNumbersListAdapter(this, NameIds, WorkingTimeIds, NumberIds);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
		findViewById(R.id.back_button).setOnClickListener(this);

//		TextView tos = (TextView) findViewById(R.id.TOS);
//		TextView tob = (TextView) findViewById(R.id.TOB);
//		TextView toic = (TextView) findViewById(R.id.TOIC);
//		tos.setText(R.string.information_emergancy_numbers_turism_text);
//		tos.setTypeface(GetFonts.getTypeface(context));
//		tob.setText(R.string.information_emergancy_numbers_turism_belgrade_text);
//		tob.setTypeface(GetFonts.getTypeface(context));
//		toic.setText(R.string.information_emergancy_numbers_turism_centers_text);
//		toic.setTypeface(GetFonts.getTypeface(context));
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_button:
                onBackPressed();
                break;
        }
    }
}
