package rs.org.amss.shell;

import java.util.ArrayList;

import rs.org.amss.R;
import rs.org.amss.core.MemoryManager;
import rs.org.amss.core.Variables;
import rs.org.amss.model.Car;
import rs.org.amss.model.GetFonts;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

public class MyCarOptionsActivity extends BaseActivity {

	public Context context = this;
	public String manufacturer, model, kilometers, year;
	public Button confirm, cancel;
	public EditText edit_manufacurer, edit_model, edit_kilometers, edit_year;
	public ArrayList<String> manufacturerlist = new ArrayList<String>();
	public ArrayList<String> modelList = new ArrayList<String>();
	public ArrayList<String> kilometersList = new ArrayList<String>();
	public ArrayList<String> yearList = new ArrayList<String>();
	MemoryManager memoryManager;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_car_change_info);
		setHomeAction(R.drawable.strelica, R.string.my_car_step_one_text,
				MyCarActivity.class);
		checkIsLogIn(MyCarOptionsActivity.this);

		memoryManager = new MemoryManager(MyCarOptionsActivity.this);
		edit_manufacurer = (EditText) findViewById(R.id.edit_manufacurer);
		edit_model = (EditText) findViewById(R.id.edit_model);
		edit_kilometers = (EditText) findViewById(R.id.edit_kilometraza);
		edit_year = (EditText) findViewById(R.id.edit_godiste);

		setTypefaces();
		
		Intent i = getIntent();
		Boolean forChange = i.getBooleanExtra("FORCHANGE", false);

		if(forChange){
			if(Variables.car!=null){
				edit_manufacurer.setText(Variables.car.manufacturer);
				edit_model.setText(Variables.car.model);
				edit_kilometers.setText(Variables.car.kilometers);
				if(Variables.car.year!=null && Variables.car.year.length()>0)
					edit_year.setText(Variables.car.year);
			}
		}
	}

	private void setTypefaces() {
		TextView text1 = (TextView)findViewById(R.id.text1);
		TextView text2 = (TextView)findViewById(R.id.text2);
		TextView text3 = (TextView)findViewById(R.id.text3);
		TextView text4 = (TextView)findViewById(R.id.text4);

		confirm = (Button) findViewById(R.id.buttonConfirm);
		cancel = (Button) findViewById(R.id.buttonCancel);
		confirm.setTypeface(GetFonts.getTypeface(MyCarOptionsActivity.this));
		cancel.setTypeface(GetFonts.getTypeface(MyCarOptionsActivity.this));
		edit_year.setTypeface(GetFonts.getTypeface(MyCarOptionsActivity.this));
		edit_kilometers.setTypeface(GetFonts.getTypeface(MyCarOptionsActivity.this));
		edit_model.setTypeface(GetFonts.getTypeface(MyCarOptionsActivity.this));
		edit_manufacurer.setTypeface(GetFonts.getTypeface(MyCarOptionsActivity.this));
		text1.setTypeface(GetFonts.getTypeface(MyCarOptionsActivity.this));
		text2.setTypeface(GetFonts.getTypeface(MyCarOptionsActivity.this));
		text3.setTypeface(GetFonts.getTypeface(MyCarOptionsActivity.this));
		text4.setTypeface(GetFonts.getTypeface(MyCarOptionsActivity.this));

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

	public void ConfirmButton(View v){
		manufacturer = edit_manufacurer.getText().toString();
		model = edit_model.getText().toString();
		kilometers = edit_kilometers.getText().toString();
		year = edit_year.getText().toString();

		if (manufacturer != null && model != null && year != null) {
			Car newCar = new Car(manufacturer, model, kilometers, year);
			Gson gson = new Gson();
			String json = gson.toJson(newCar);
			memoryManager.SaveNewCar(json);
			finish();
			activityManager.startActivity(MyCarBasicInfoActivity.class);
		}else
			layoutManager.showInfo("Popunite prazna polja.");
	}
	
	public void Cancel(View v){
		finish();
	}
}
