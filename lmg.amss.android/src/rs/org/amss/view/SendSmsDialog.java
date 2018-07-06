package rs.org.amss.view;

import java.text.DecimalFormat;

import rs.org.amss.core.Variables;
import rs.org.amss.model.GetFonts;
import rs.org.amss.shell.BaseMapActivity;
import rs.org.amss.R;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class SendSmsDialog extends DialogFragment {
	
	protected BaseMapActivity activityContext;

	public void setActivityContext(BaseMapActivity activityContext) {
		this.activityContext = activityContext;
	}

	public SendSmsDialog(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);

    }
  
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	final Dialog dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		dialog.setContentView(R.layout.sendsms_dialog);
		
        TextView textLatitude = (TextView)dialog.findViewById(R.id.textCallHelpDialogLatitude);
		textLatitude.setText(formatLocationParam(Variables.latitude));

		TextView textLongitude = (TextView) dialog.findViewById(R.id.textCallHelpDialogLongitude);
		textLongitude.setText(formatLocationParam(Variables.longitude));

		if (Variables.getUser() != null) {
			TextView textMemberId = (TextView) dialog.findViewById(R.id.textCallHelpMembershipId);

			String membershipCardId = Variables.getUser().getMembershipCardId();
			if (!TextUtils.isEmpty(membershipCardId)) {
                membershipCardId = Variables.getUser().getMembershipCardSeries() + "-" + Variables.getUser().getMembershipCardNumber();
			}

			textMemberId.setText(membershipCardId);
			textMemberId.setTypeface(GetFonts.getBoldItalicTypeface(activityContext));
			textMemberId.setTextSize(20);
			TextView textMemberIdTitle = (TextView) dialog.findViewById(R.id.send_sms_membership_title);
			textMemberIdTitle.setTypeface(GetFonts.getBoldTypeface(activityContext));
			textMemberIdTitle.setTextSize(20);
			textMemberIdTitle.setVisibility(TextUtils.isEmpty(membershipCardId) ? View.GONE : View.VISIBLE);
			Variables.sendSmsRegistration = Variables.getUser().getRegistrationPlate();
			Variables.sendSmsVehicleVendor = Variables.vehicleVendor;
			Variables.sendSmsVehicleColor = Variables.vehicleColor;
		} else {
			((LinearLayout)dialog.findViewById(R.id.send_sms_membership_group)).setVisibility(View.GONE);
		}

		EditText vehicleVendor = (EditText)dialog.findViewById(R.id.editSendSmsVehicleVendor);
		vehicleVendor.setText(Variables.sendSmsVehicleVendor);
		
		EditText vehicleColor = (EditText)dialog.findViewById(R.id.editSendSmsVehicleColor);
		vehicleColor.setText(Variables.sendSmsVehicleColor);
		
		EditText registration = (EditText)dialog.findViewById(R.id.editSendSmsRegistration);
		registration.setText(Variables.sendSmsRegistration);

		ImageView buttonOk = (ImageView) dialog.findViewById(R.id.buttonCallHelpDialogOk);
		buttonOk.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				Variables.sendSmsVehicleVendor = ((EditText) dialog.findViewById(R.id.editSendSmsVehicleVendor)).getText().toString();
				Variables.sendSmsVehicleColor = ((EditText) dialog.findViewById(R.id.editSendSmsVehicleColor)).getText().toString();
				Variables.sendSmsRegistration = ((EditText) dialog.findViewById(R.id.editSendSmsRegistration)).getText().toString();
				Variables.sendSmsIntervention = ((Spinner) dialog.findViewById(R.id.spinnerSendSmsIntervention)).getSelectedItem().toString();
				
				if (activityContext != null) {
					activityContext.getEditTextDialog(activityContext);
					SendSmsDialog.this.getDialog().cancel();
				}
				SendSmsDialog.this.getDialog().cancel();
			}
		});

		ImageView buttonCancel = (ImageView) dialog.findViewById(R.id.buttonCallHelpDialogCancel);
		buttonCancel.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				SendSmsDialog.this.getDialog().cancel();
			}
		});
        
		dialog.show();
                
        return dialog;
    }
    
    private static String formatLocationParam(double value){
//    	DecimalFormat maxDigitsFormatter = new DecimalFormat("0.######");
		DecimalFormat maxDigitsFormatter = new DecimalFormat("#.######");
    	return maxDigitsFormatter.format(value);
    }
}
