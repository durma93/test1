package rs.org.amss.shell;

import rs.org.amss.R;
import rs.org.amss.core.ListAdapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class AdvicesForVehicleKeepingActivity extends BaseActivity implements View.OnClickListener {

	public Context context = this;
	public static final Integer[] TextIds = {
			R.string.advices_document_loss_label,
			R.string.advices_vehicle_maintanance_label,
			R.string.advices_winter_conditions_label,
            R.string.advices_checking_used_vehicles_label,
            R.string.advices_holes_damages_label,
            R.string.advices_ecological_driving_label
	};
	public static final Integer[] TextInfoIds = {
			R.string.advices_document_loss_text,
			R.string.advices_vehicle_maintanance_text,
			R.string.advices_winter_conditions_text,
            R.string.advices_checking_used_vehicles_text,
            R.string.advices_holes_damages_text,
            R.string.advices_ecological_driving_ltext
	};
	public static final Integer[] ImageIds = {
			R.drawable.infosaveti,
			R.drawable.infosaveti,
            R.drawable.infosaveti,
            R.drawable.infosaveti,
            R.drawable.infosaveti,
            R.drawable.infosaveti
	};

//	private TextView text;

//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.text);
//		findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				finish();
//			}
//		});
//		((TextView) findViewById(R.id.title)).setText("INFORMACIJE");
//		((TextView) findViewById(R.id.subtitle)).setText("SAVETI ZA ODRŽAVANJE VOZILA");
//		setHomeAction(R.drawable.ic_main_info, R.string.information_advices_text, InfoActivity.class);
//		checkIsLogIn(AdvicesForVehicleKeepingActivity.this);
//		text = (TextView)findViewById(R.id.text);
//		text.setText(R.string.information_advices_long_text);
//	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_pages);
		setHomeAction(R.drawable.ic_main_info, R.string.info_documents_text,
				InfoActivity.class);
		((TextView)findViewById(R.id.title)).setText(R.string.info_for_vehicle_owners_title);
		((TextView)findViewById(R.id.subtitle)).setText("/ " + getString(R.string.advices_for_vehicle_owners_title));

		checkIsLogIn(AdvicesForVehicleKeepingActivity.this);
		ListView info = (ListView) findViewById(R.id.itemList);
		findViewById(R.id.back_button).setOnClickListener(this);

		ListAdapter adapter = new ListAdapter(context, ImageIds, TextIds);
		info.setAdapter(adapter);
		info.setClickable(true);
		info.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				activityManager.startActivityWithParam(AdvicesInfoActivity.class, AdvicesInfoActivity.INTENT_EXTRA, position);
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
