package rs.org.amss.view;


import java.text.DecimalFormat;

import rs.org.amss.core.Constants;
import rs.org.amss.core.Variables;
import rs.org.amss.model.GetFonts;
import rs.org.amss.shell.BaseMapActivity;
import rs.org.amss.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChangeSMSTextDialog  extends DialogFragment {
	protected BaseMapActivity activityContext;
	EditText editName;
	String message;

	@SuppressLint("ValidFragment")
	public ChangeSMSTextDialog(Activity context){
		activityContext = (BaseMapActivity) context;
	}

	public ChangeSMSTextDialog(){}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Dialog dialog = new Dialog(activityContext);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		dialog.setContentView(R.layout.edit_sms_text_dialog);
		final EditText msg = (EditText)dialog.findViewById(R.id.notification_text);
		TextView titleMsg = (TextView)dialog.findViewById(R.id.title_text);
		titleMsg.setTypeface(GetFonts.getBoldTypeface(activityContext));
		titleMsg.setText(activityContext.getResources().getString(R.string.sendsms_dialog_title));
		titleMsg.setTextSize(22);
		msg.setTypeface(GetFonts.getTypeface(activityContext));
		msg.setHint(activityContext.getResources().getString(R.string.sendsms_hint_text));
		msg.setTextSize(20);
		Button close = (Button)dialog.findViewById(R.id.close_button);
		close.setTypeface(GetFonts.getBoldTypeface(activityContext));
		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
//		if(Variables.getUser()!=null && Variables.getUser().getMembershipCardId()!=null)
//			message = String.format(Constants.emergencySmsFormatWithAccount, String.valueOf(Variables.longitude), String.valueOf(Variables.latitude), Variables.getUser().getMembershipCardId());
//		else
//			message = String.format(Constants.emergencySmsFormatWithoutAccount, String.valueOf(Variables.longitude), String.valueOf(Variables.latitude));
		
		DecimalFormat df = new DecimalFormat("#.######");

		message = "A " + df.format(Variables.longitude) + "\n";
		message += df.format(Variables.latitude) + "\n";
		if (Variables.getUser() != null && Variables.getUser().getMembershipCardId() != null)
			message += String.valueOf(Variables.getUser().getMembershipCardSeries() + "-" + Variables.getUser().getMembershipCardNumber()) + "\n";
		message += String.valueOf(Variables.sendSmsVehicleVendor) + "\n";
		message += String.valueOf(Variables.sendSmsVehicleColor) + "\n";
		message += Variables.sendSmsRegistration + "\n";
		if (Variables.sendSmsIntervention != null)
			message += Variables.sendSmsIntervention.replace('š', 's').replace('Š', 'S').replace('č', 'c').replace('Č', 'C');
		
		msg.setText(message);
		
		
		Button confirm = (Button)dialog.findViewById(R.id.confirm_button);
		confirm.setTypeface(GetFonts.getBoldTypeface(activityContext));
		confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				message = msg.getText().toString().trim();
				Log.v("SMSMessage", message);
				if(activityContext!=null){
					if(msg.getText().toString().length()>0){
						//activityContext.getActivityManager().sendSMS("0641669100", message);
						activityContext.getActivityManager().sendSMS(Constants.emergencyPhoneNumber, message);
						activityContext.getLayoutManager().showInfo(R.string.sendsms_dialog_toast);
						dialog.cancel();
					}
					else{
						activityContext.getErrorToast();
					}
				}
			}
		});
		dialog.show();
		return dialog;
	}

}
