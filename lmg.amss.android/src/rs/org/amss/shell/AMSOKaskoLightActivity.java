package rs.org.amss.shell;

import java.text.NumberFormat;
import java.util.Locale;

import rs.org.amss.R;
import rs.org.amss.model.GetFonts;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class AMSOKaskoLightActivity extends BaseActivity {
	public final static String INTENT_EXTRA = "position";

	public Context context = this;
	int amount = 0;
	double percent = 0;
	protected Button calculate;
	protected Spinner choosePeriod, chooseAmount;
	protected TextView showResult, choosePeriodTW, chooseAmountTW, labelTW ;

	private String[] amountList = {"100.000,00", "200.000,00", "300.000,00", "400.000,00", "500.000,00"};
	private String[] periodList = {"preko 8 meseci", "do 8 meseci", "do 7 meseci", "do 6 meseci", "do 5 meseci",
			"do 4 meseca", "do 3 meseca", "do 2 meseca", "do 1 meseca", "do 15 dana", "do 7 dana","do 3 dana" };
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kasko_light_kalkulator);

		String title = getString(R.string.AMSO_label);

		checkIsLogIn(AMSOKaskoLightActivity.this);
		findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
		((TextView) findViewById(R.id.title)).setText(title);
		((TextView) findViewById(R.id.subtitle)).setText("/  Kasko light / Kalkulator premije");

		choosePeriod = (Spinner)findViewById(R.id.choose_period_of_coverage);
		chooseAmount = (Spinner)findViewById(R.id.choose_amount_spinner);
		showResult = (TextView)findViewById(R.id.result);
		chooseAmountTW = (TextView)findViewById(R.id.textView1);
//		chooseAmountTW.setTypeface(GetFonts.getTypeface(context));
		choosePeriodTW = (TextView)findViewById(R.id.textView2);
//		choosePeriodTW.setTypeface(GetFonts.getTypeface(context));
		calculate = (Button)findViewById(R.id.calculate);
		labelTW = (TextView)findViewById(R.id.label);
//		labelTW.setTypeface(GetFonts.getTypeface(context));

		setAdapters();
		calculate.setTypeface(GetFonts.getBoldTypeface(AMSOKaskoLightActivity.this));
		calculate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String message = getString(R.string.AMSO_kasko_light_calculator_result_text) + " "+NumberFormat.getInstance(Locale.GERMAN).format(Calculate(amount, percent))+",00 dinara.";
				getNotificationDialog(AMSOKaskoLightActivity.this, message);
			}
		});
	}

	private void setAdapters() {
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AMSOKaskoLightActivity.this,
				android.R.layout.simple_spinner_item, amountList );
		ArrayAdapter<String> periodAdapter = new ArrayAdapter<String>(AMSOKaskoLightActivity.this,
				android.R.layout.simple_spinner_item, periodList );
		periodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		chooseAmount.setAdapter(dataAdapter);
		chooseAmount.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int arg2, long arg3) {
				switch (arg2) {
				case 0:
					amount = 100000;
					break;
				case 1:
					amount = 200000;
					break;
				case 2:
					amount = 300000;
					break;
				case 3:
					amount = 400000;
					break;
				case 4:
					amount = 500000;
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		choosePeriod.setAdapter(periodAdapter);
		choosePeriod.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int arg2, long arg3) {
				switch (arg2) {
				case 0:
					percent = 1.0;
					break;
				case 1:	
					percent = 0.9;
					break;
				case 2:
					percent = 0.8;
					break;
				case 3:
					percent = 0.7;
					break;
				case 4:
					percent = 0.6;
					break;
				case 5:
					percent = 0.5;
					break;
				case 6:
					percent = 0.4;
					break;
				case 7:
					percent = 0.3;
					break;
				case 8:
					percent = 0.2;
					break;
				case 9:
					percent = 0.15;
					break;
				case 10:
					percent = 0.1;
					break;
				case 11:
					percent = 0.05;
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

	}
	public double Calculate (int amount, double percentOfPeriod){
		double result = 0;
		result = (double)(amount * 0.06 * percentOfPeriod * (percentOfPeriod+0.05));
		return result;
	}

}

