package rs.org.amss.shell;

import rs.org.amss.R;
import rs.org.amss.core.MemoryManager;
import rs.org.amss.core.Variables;
import rs.org.amss.model.GetFonts;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.markupartist.android.widget.ActionBar.AbstractAction;

public class MyCarBasicInfoActivity extends BaseActivity {

	public Context context = this;
	public TextView manufacturer, model, kilometars, servis_date,
	servis_amount, servis_price, gas_date, gas_amount, gas_price;
	TableLayout basicInfo, servisInfo, gasInfo;
	MemoryManager memoryManager;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.my_car);
		setHomeAction(R.drawable.ic_mycar, R.string.mycar_label,
				MyCarActivity.class);
		checkIsLogIn(MyCarBasicInfoActivity.this);

		memoryManager = new MemoryManager(MyCarBasicInfoActivity.this);

		addAction(new servisButton());
		addAction(new addGasAction());
		addAction(new changeInfoAction());
		hideProgress();

		setBasicInfo();
		setServisInfo();
		setGasInfo();
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

	private void setGasInfo() {
		Variables.fuel = memoryManager.GetCarFuel();
		if(Variables.fuel != null && Variables.fuel.amount != null){
			gasInfo = (TableLayout) findViewById(R.id.my_car_gas);
			gasInfo.setVisibility(View.VISIBLE);
			gas_date = (TextView) findViewById(R.id.gas_date_text);
			gas_amount = (TextView) findViewById(R.id.gas_amount_text);
			gas_price = (TextView) findViewById(R.id.gas_price_text);

			gasInfo.setVisibility(View.VISIBLE);
			gas_date.setText(Variables.fuel.date);
			gas_amount.setText(Variables.fuel.amount + " l");
			gas_price.setText(Variables.fuel.price + " din");

			TextView heading = (TextView) findViewById(R.id.heading_gas);
			TextView tv1 = (TextView) findViewById(R.id.gas_date_label);
			TextView tv2 = (TextView) findViewById(R.id.gas_amount_label);
			TextView tv3 = (TextView) findViewById(R.id.gas_price_label);

			heading.setTypeface(GetFonts.getBoldTypeface(context));
			tv1.setTypeface(GetFonts.getTypeface(context));
			tv2.setTypeface(GetFonts.getTypeface(context));
			tv3.setTypeface(GetFonts.getTypeface(context));

			gas_date.setTypeface(GetFonts.getSlimTypeface(context));
			gas_amount.setTypeface(GetFonts.getSlimTypeface(context));
			gas_price.setTypeface(GetFonts.getSlimTypeface(context));
			
			Button gasDetailsChangeBt = (Button)findViewById(R.id.gasDetailsChangeBt);
			gasDetailsChangeBt.setTypeface(GetFonts.getTypeface(context));

		}
	}

	private void setServisInfo() {
		Variables.servis = memoryManager.GetCarService();
		if(Variables.servis!=null){
			servisInfo = (TableLayout) findViewById(R.id.my_car_info);			
			servisInfo.setVisibility(View.VISIBLE);
			servis_date = (TextView) findViewById(R.id.servis_date_text);
			servis_amount = (TextView) findViewById(R.id.servis_amount_text);
			servis_price = (TextView) findViewById(R.id.servis_price_text);

			servis_date.setText(Variables.servis.date);
			servis_amount.setText(Variables.servis.description);
			servis_price.setText("" + Variables.servis.price + " din");

			TextView heading = (TextView) findViewById(R.id.heading_sevis);
			TextView tv1 = (TextView) findViewById(R.id.servis_date_label);
			TextView tv2 = (TextView) findViewById(R.id.servis_amount_label);
			TextView tv3 = (TextView) findViewById(R.id.servis_price_label);

			heading.setTypeface(GetFonts.getBoldTypeface(context));
			tv1.setTypeface(GetFonts.getTypeface(context));
			tv2.setTypeface(GetFonts.getTypeface(context));
			tv3.setTypeface(GetFonts.getTypeface(context));

			servis_date.setTypeface(GetFonts.getSlimTypeface(context));
			servis_amount.setTypeface(GetFonts.getSlimTypeface(context));
			servis_price.setTypeface(GetFonts.getSlimTypeface(context));
			Button servisDetailsChangeBt = (Button)findViewById(R.id.servisDetailsChangeBt);
			servisDetailsChangeBt.setTypeface(GetFonts.getTypeface(context));
		}
	}

	private void setBasicInfo() {
		basicInfo = (TableLayout) findViewById(R.id.my_car);

		if(Variables.car!=null){
			basicInfo.setVisibility(View.VISIBLE);
			manufacturer = (TextView) findViewById(R.id.manufacturer_text);
			model = (TextView) findViewById(R.id.model);
			kilometars = (TextView) findViewById(R.id.kilometers);
			manufacturer.setText(Variables.car.manufacturer);
			model.setText(Variables.car.model);
			kilometars.setText(Variables.car.kilometers+ " km");
		}else
			activityManager.startActivity(MyCarOptionsActivity.class);

		setBasicLayoutFonts();

	}

	@Override
	public void onBackPressed() {
		finish();
		super.onBackPressed();
	}

	private void setBasicLayoutFonts() {
		TextView heading = (TextView) findViewById(R.id.heading);
		TextView tv1 = (TextView) findViewById(R.id.tw1);
		TextView tv2 = (TextView) findViewById(R.id.tw2);
		TextView tv3 = (TextView) findViewById(R.id.tw3);

		heading.setTypeface(GetFonts.getBoldTypeface(context));
		tv1.setTypeface(GetFonts.getTypeface(context));
		tv2.setTypeface(GetFonts.getTypeface(context));
		tv3.setTypeface(GetFonts.getTypeface(context));

		manufacturer.setTypeface(GetFonts.getSlimTypeface(context));
		model.setTypeface(GetFonts.getSlimTypeface(context));
		kilometars.setTypeface(GetFonts.getSlimTypeface(context));
		Button basicInfoChangeBt = (Button)findViewById(R.id.basicInfoChangeBt);
		basicInfoChangeBt.setTypeface(GetFonts.getTypeface(context));
	}

	private class servisButton extends AbstractAction {

		public servisButton() {
			super(R.drawable.moj_auto);
		}

		@Override
		public void performAction(View view) {
			activityManager.startActivity(MyCarOptionsActivity.class);
		}
	}

	private class addGasAction extends AbstractAction {

		public addGasAction() {
			super(R.drawable.gas_change);
		}

		@Override
		public void performAction(View view) {
			activityManager.startActivity(MyCarGasReminderActivity.class);
		}
	}

	private class changeInfoAction extends AbstractAction {

		public changeInfoAction() {
			super(R.drawable.info_change);
		}

		@Override
		public void performAction(View view) {
			activityManager.startActivity(MyCarServisReminderActivity.class);
		}
	}

	public void gasDetailsChange(View v) {
		activityManager.startActivityWithParam(MyCarGasReminderActivity.class,
				"FORCHANGE", true);
	}

	public void servisDetailsChange(View v) {
		activityManager.startActivityWithParam(
				MyCarServisReminderActivity.class, "FORCHANGE", true);
	}

	public void basicInfoChange(View v) {
		activityManager.startActivityWithParam(MyCarOptionsActivity.class,
				"FORCHANGE", true);
	}
}
