package rs.org.amss.shell;

import rs.org.amss.R;
import rs.org.amss.core.MemoryManager;
import rs.org.amss.core.Variables;
import rs.org.amss.model.Fuel;
import rs.org.amss.model.GetFonts;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class MyCarGasReminderActivity extends BaseActivity {

	public Context context = this;
	public EditText edit_amount, edit_price, edit_kilometers, edit_date,
			edit_gasStation;
	public Spinner typeOfFuel;
	public Button confirm, cancel;
	MemoryManager memoryManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_car_info);
		setHomeAction(R.drawable.strelica, R.string.my_car_step_two_text,
				MyCarActivity.class);
		checkIsLogIn(MyCarGasReminderActivity.this);
		memoryManager = new MemoryManager(MyCarGasReminderActivity.this);

		edit_amount = (EditText) findViewById(R.id.edit_gas_amount);
		edit_price = (EditText) findViewById(R.id.edit_gas_price);
		edit_kilometers = (EditText) findViewById(R.id.edit_kilometers);
		edit_date = (EditText) findViewById(R.id.edit_gas_date);
		edit_gasStation = (EditText) findViewById(R.id.gas_station);
		typeOfFuel = (Spinner) findViewById(R.id.type_of_fuel);
		confirm = (Button) findViewById(R.id.buttonConfirm);
		cancel = (Button) findViewById(R.id.buttonCancel);

		Intent i = getIntent();
		Boolean forChange = i.getBooleanExtra("FORCHANGE", false);

		ArrayAdapter<String> fuelAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item, getResources()
						.getStringArray(R.array.fuel_list));
		typeOfFuel.setAdapter(fuelAdapter);

		if (forChange) {
			if (Variables.fuel != null) {
				edit_amount.setText(Variables.fuel.amount);
				edit_price.setText(Variables.fuel.price);
				edit_kilometers.setText(Variables.fuel.kilometers);
				edit_date.setText(Variables.fuel.date);
				edit_gasStation.setText(Variables.fuel.gasStation);
			}
		}
		
		setTypefaces();
	}

	private void setTypefaces() {
		TextView text1 = (TextView)findViewById(R.id.text1);
		TextView text2 = (TextView)findViewById(R.id.text2);
		TextView text3 = (TextView)findViewById(R.id.text3);
		TextView text4 = (TextView)findViewById(R.id.text4);
		TextView text5 = (TextView)findViewById(R.id.text5);
		TextView text6 = (TextView)findViewById(R.id.text6);
		
		edit_amount.setTypeface(GetFonts.getTypeface(MyCarGasReminderActivity.this));
		edit_price.setTypeface(GetFonts.getTypeface(MyCarGasReminderActivity.this));
		edit_kilometers.setTypeface(GetFonts.getTypeface(MyCarGasReminderActivity.this));
		edit_date.setTypeface(GetFonts.getTypeface(MyCarGasReminderActivity.this));
		edit_gasStation.setTypeface(GetFonts.getTypeface(MyCarGasReminderActivity.this));
		confirm.setTypeface(GetFonts.getTypeface(MyCarGasReminderActivity.this));
		cancel.setTypeface(GetFonts.getTypeface(MyCarGasReminderActivity.this));	
		text1.setTypeface(GetFonts.getTypeface(MyCarGasReminderActivity.this));		
		text2.setTypeface(GetFonts.getTypeface(MyCarGasReminderActivity.this));		
		text3.setTypeface(GetFonts.getTypeface(MyCarGasReminderActivity.this));		
		text4.setTypeface(GetFonts.getTypeface(MyCarGasReminderActivity.this));		
		text5.setTypeface(GetFonts.getTypeface(MyCarGasReminderActivity.this));		
		text6.setTypeface(GetFonts.getTypeface(MyCarGasReminderActivity.this));		
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

	public void Confirm(View v){
		Fuel newFuel = new Fuel();
		if (edit_amount.getText().toString().length() > 0
				&& edit_price.getText().toString().length() > 0
				&& edit_kilometers.getText().toString().length() > 0) {
			newFuel.amount = edit_amount.getText().toString();
			newFuel.price = edit_price.getText().toString();
			newFuel.kilometers = edit_kilometers.getText().toString();
			if(edit_date.getText().toString().length()>0)
				newFuel.date = edit_date.getText().toString();
			if(edit_gasStation.getText().toString().length()>0)
				newFuel.gasStation = edit_gasStation.getText().toString();
			newFuel.fuelType = getFuelType();
			Gson gson = new Gson();
			String json = gson.toJson(newFuel);
			memoryManager.SaveCarFuel(json);
			finish();
			activityManager.startActivity(MyCarBasicInfoActivity.class);
		}else
			Toast.makeText(context, "Popunite obavezna polja!",
					Toast.LENGTH_LONG).show();
	}
	
	public void Cancel(){
		finish();
	}
	
	private String getFuelType() {
		return typeOfFuel.getSelectedItem().toString();
	}
}
