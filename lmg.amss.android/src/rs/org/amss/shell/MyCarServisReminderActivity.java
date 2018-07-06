package rs.org.amss.shell;

import rs.org.amss.R;
import rs.org.amss.core.MemoryManager;
import rs.org.amss.core.Variables;
import rs.org.amss.model.GetFonts;
import rs.org.amss.model.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class MyCarServisReminderActivity extends BaseActivity {

	public Context context = this;
	public TextView manufacturer, model, kilometars;
	public EditText edit_date, edit_desc, edit_price;
	public CheckBox cb;
	public Button confirm, cancel;
	public boolean freq = false;
	MemoryManager memoryManager;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_car_servis_change);
		setHomeAction(R.drawable.strelica, R.string.my_car_step_three_text,
				MyCarActivity.class);
		checkIsLogIn(MyCarServisReminderActivity.this);

		Intent i = getIntent();
		Boolean forChange = i.getBooleanExtra("FORCHANGE", false);
		memoryManager = new MemoryManager(MyCarServisReminderActivity.this);

		edit_date = (EditText) findViewById(R.id.edit_date);
		edit_desc = (EditText) findViewById(R.id.edit_desc);
		edit_price = (EditText) findViewById(R.id.edit_price);
		cb = (CheckBox) findViewById(R.id.radio_freq);
		confirm = (Button) findViewById(R.id.buttonConfirm);
		cancel = (Button) findViewById(R.id.buttonCancel);

		
		
		setTypefaces();

		if (forChange) {
			if (Variables.servis != null) {
				edit_date.setText(Variables.servis.date);
				edit_desc.setText(Variables.servis.description);
				edit_price.setText("" + Variables.servis.price);
				if (Variables.servis.frequency)
					cb.setChecked(true);
				else
					cb.setChecked(false);
			}
		}
	}

	private void setTypefaces() {
		TextView text1 = (TextView)findViewById(R.id.text1);
		TextView text2 = (TextView)findViewById(R.id.text2);
		TextView text3 = (TextView)findViewById(R.id.text3);
		TextView text4 = (TextView)findViewById(R.id.text4);

		confirm.setTypeface(GetFonts.getTypeface(MyCarServisReminderActivity.this));
		cancel.setTypeface(GetFonts.getTypeface(MyCarServisReminderActivity.this));
		edit_date.setTypeface(GetFonts.getTypeface(MyCarServisReminderActivity.this));
		edit_desc.setTypeface(GetFonts.getTypeface(MyCarServisReminderActivity.this));
		edit_price.setTypeface(GetFonts.getTypeface(MyCarServisReminderActivity.this));		
		text1.setTypeface(GetFonts.getTypeface(MyCarServisReminderActivity.this));		
		text2.setTypeface(GetFonts.getTypeface(MyCarServisReminderActivity.this));		
		text3.setTypeface(GetFonts.getTypeface(MyCarServisReminderActivity.this));		
		text4.setTypeface(GetFonts.getTypeface(MyCarServisReminderActivity.this));		
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
		Service newService = new Service();
		if (edit_date.getText().toString().length() > 0
				&& edit_desc.getText().toString().length() > 0) {
			newService.date = edit_date.getText().toString();
			newService.description = edit_desc.getText().toString();
			newService.price = Double.parseDouble(edit_price.getText().toString());
			newService.frequency = freq;
			Gson gson = new Gson();
			String json = gson.toJson(newService);
			memoryManager.SaveCarService(json);
			finish();
			activityManager.startActivity(MyCarBasicInfoActivity.class);
		}else
			Toast.makeText(context, "Popunite obavezna polja!",
					Toast.LENGTH_LONG).show();
	}

	public void Cancel(View v){
		finish();
	}
	
	public void OnCheckClicked (View v){
		boolean checked = cb.isChecked();
		freq = checked;
	}

}
