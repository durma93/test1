package rs.org.amss.view;

import java.sql.Date;
import java.util.Calendar;

import rs.org.amss.model.GetFonts;
import rs.org.amss.shell.AMSOTravelInsuranceCalculatorActivity;
import rs.org.amss.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.DatePicker.OnDateChangedListener;

public class DatePickerDialog  extends DialogFragment {
	protected AMSOTravelInsuranceCalculatorActivity activityContext;
	String message;

	@SuppressLint("ValidFragment")
	public DatePickerDialog(Activity activity){
		this.activityContext = (AMSOTravelInsuranceCalculatorActivity) activity;
	}
	public DatePickerDialog(){
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		return super.onCreateView(inflater, container, savedInstanceState);

	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Dialog dialogView = new Dialog(getActivity());
		dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		dialogView.setContentView(R.layout.datepickers);

		Calendar cal = Calendar.getInstance();
		final Date minDate = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		final Date maxDate = activityContext.getMaxDate(minDate);
		Button ok = (Button)dialogView.findViewById(R.id.ok);
		ok.setTypeface(GetFonts.getBoldTypeface(activityContext));

		final TextView dateEnding = (TextView)dialogView.findViewById(R.id.ending_date);
		final TextView begining_date = (TextView)dialogView.findViewById(R.id.begining_date);

		begining_date.setTypeface(GetFonts.getBoldTypeface(activityContext));
		dateEnding.setTypeface(GetFonts.getBoldTypeface(activityContext));

		final DatePicker dpStartDate = (DatePicker)dialogView.findViewById(R.id.dpStartDate);
		final DatePicker dpEndDate = (DatePicker)dialogView.findViewById(R.id.dpEndDate);
		dpStartDate.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {

			private Date minDate_temp = minDate;
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				view.init(year, monthOfYear, dayOfMonth, this);
				minDate_temp  = new Date(year, monthOfYear, dayOfMonth);
				activityContext.getMaxDate(minDate_temp);
			}
		});

		if(activityContext.vrsta_tarife.equals("03")){
			dpEndDate.setVisibility(View.GONE);
			dateEnding.setVisibility(View.GONE);
			dialogView.setTitle(getResources().getString(R.string.date_picker_text_01));
		}
		else{
			dpEndDate.setVisibility(View.VISIBLE);
			dateEnding.setVisibility(View.VISIBLE);
			dialogView.setTitle(getResources().getString(R.string.date_picker_text_02));
			dpEndDate.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {

				@Override
				public void onDateChanged(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					Log.v("MAX on date change", ""+maxDate);
					view.init(year, monthOfYear, dayOfMonth, this);
					if(!isDateAfter(view)){
						Calendar tmp = activityContext.getCalendarDate(maxDate);
						Log.v("tmp", ""+tmp.get(Calendar.YEAR)+" "+tmp.get(Calendar.MONTH)+" "+tmp.get(Calendar.DAY_OF_MONTH));
						view.init(tmp.get(Calendar.YEAR),tmp.get(Calendar.MONTH), tmp.get(Calendar.DAY_OF_MONTH), this);
					}
				}
				private boolean isDateAfter(DatePicker tempView) {
					Date temp = new Date(tempView.getYear()-1900,  tempView.getMonth(), tempView.getDayOfMonth());
					Log.v("MIN date in isDateAfter", ""+temp);
					Log.v("MAX date in isDateAfter", ""+maxDate);
					if(temp.before(maxDate))
						return true;
					else 
						return false;
				}
			});
		}
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				activityContext.startDate.setYear(dpStartDate.getYear()-1900);
				activityContext.startDate.setMonth(dpStartDate.getMonth());
				activityContext.startDate.setDate(dpStartDate.getDayOfMonth());
				activityContext.endDate.setYear(dpEndDate.getYear()-1900);
				activityContext.endDate.setMonth(dpEndDate.getMonth());
				activityContext.endDate.setDate(dpEndDate.getDayOfMonth());
				if(activityContext.startDate.compareTo(activityContext.endDate)<0 ||
						activityContext.startDate.compareTo(activityContext.endDate)==0){
					activityContext.days = ""+activityContext.get_days_between_dates(activityContext.startDate, activityContext.endDate);
					activityContext.duration.setText(activityContext.days+" dana");
					Log.v("Start date: ", ""+activityContext.startDate);
					Log.v("End date: ", ""+activityContext.endDate);
					dialogView.dismiss();

				}else
					activityContext.getLayoutManager().showError(getString(R.string.AMSO_kasko_travel_insurance_date_error_text));				
			}
		});

		dialogView.show();

		return dialogView;
	}

}
