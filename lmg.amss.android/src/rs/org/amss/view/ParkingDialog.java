package rs.org.amss.view;

import java.util.List;

import rs.org.amss.core.Variables;
import rs.org.amss.shell.ParkingActivity;
import rs.org.amss.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ParkingDialog  extends DialogFragment {
	protected ParkingActivity activityContext;
	EditText registracija;
	Spinner grad;
	String[] cities;
	List<String> citiesList;

	@SuppressLint("ValidFragment")
	public ParkingDialog(Activity activity){
		this.activityContext = (ParkingActivity) activity;
	}

	public ParkingDialog(){
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
		dialogView.setContentView(R.layout.parking_dialog);

		registracija = (EditText)dialogView.findViewById(R.id.regPlate);
		grad = (Spinner)dialogView.findViewById(R.id.city_chooser);
		grad.setSelection(getIndex(grad, Variables.parking_city_choosed));

		if(Variables.registrationPlate!=null && Variables.registrationPlate.length()>0)
			registracija.setText(Variables.registrationPlate);

		View buttonOk = dialogView.findViewById(R.id.buttonCallHelpDialogOk);
		buttonOk.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (activityContext != null && registracija.getText().toString().length() > 0) {
				    String text = registracija.getText().toString().replace("-", "").replace(" ", "").replace("_", "").replace("/", "");
				    if (text.length() != 7 && text.length() != 8) {
				        activityContext.getLayoutManager().showWarning("Neispravan format tablice");
				        return;
                    }

					Variables.registrationPlate = text;
					activityContext.plate.setText(Variables.registrationPlate.toUpperCase());				
					Variables.parking_city_choosed = (String) grad.getSelectedItem();
					activityContext.finish();
					activityContext.startActivity(new Intent(activityContext, ParkingActivity.class));
				} else {
					activityContext.getLayoutManager().showWarning(R.string.parking_insert_reg_plate_error);
				}
			}
		});

		View  buttonCancel =  dialogView.findViewById(R.id.buttonCallHelpDialogCancel);
		buttonCancel.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				activityContext.finish();
				ParkingDialog.this.getDialog().cancel();
			}
		});

		dialogView.show();

		return dialogView;

	}
	private int getIndex(Spinner spinner, String myString)
	{
		int index = 0;

		for (int i=0;i<spinner.getCount();i++){
			if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString))
				index = i;
		}
		return index;
	} 
}
