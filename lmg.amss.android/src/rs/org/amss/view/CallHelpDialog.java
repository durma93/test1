package rs.org.amss.view;

import java.text.DecimalFormat;

import rs.org.amss.core.Constants;
import rs.org.amss.core.Variables;
import rs.org.amss.shell.BaseMapActivity;
import rs.org.amss.R;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CallHelpDialog  extends DialogFragment {

	protected BaseMapActivity activityContext;

	@SuppressLint("ValidFragment")
	public CallHelpDialog(BaseMapActivity context){
		this.activityContext = context;
	}

	public CallHelpDialog(){}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		return super.onCreateView(inflater, container, savedInstanceState);

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Dialog dialogView = new Dialog(getActivity());

		dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		dialogView.setContentView(R.layout.callhelp_dialog);

		TextView title = (TextView)dialogView.findViewById(R.id.call_help_title);
		TextView subtitle = (TextView)dialogView.findViewById(R.id.call_help_subtitle);
		TextView message = (TextView)dialogView.findViewById(R.id.call_help_message);
		TextView longitude = (TextView)dialogView.findViewById(R.id.call_help_longitude_title);
		TextView latitude = (TextView)dialogView.findViewById(R.id.call_help_latitude_title);
		TextView textLatitude = (TextView) dialogView.findViewById(R.id.textCallHelpDialogLatitude);
		textLatitude.setText(formatLocationParam(Variables.latitude));
		TextView textLongitude = (TextView) dialogView.findViewById(R.id.textCallHelpDialogLongitude);
		textLongitude.setText(formatLocationParam(Variables.longitude));

        ImageView buttonOk = (ImageView) dialogView.findViewById(R.id.buttonCallHelpDialogOk);
		buttonOk.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (activityContext != null)
					activityContext.getActivityManager().makeCall(Constants.emergencyPhoneNumber);
//				else if (mapActivityContext != null)
//					mapActivityContext.getActivityManager().makeCall(Constants.emergencyPhoneNumber);					
				CallHelpDialog.this.getDialog().cancel();
			}
		});

        ImageView buttonCancel = (ImageView) dialogView.findViewById(R.id.buttonCallHelpDialogCancel);
		buttonCancel.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				CallHelpDialog.this.getDialog().cancel();
			}
		});

		dialogView.show();

		return dialogView;
	}

	private static String formatLocationParam(double value){
		DecimalFormat maxDigitsFormatter = new DecimalFormat("#.######");
		return maxDigitsFormatter.format(value);
	}
}
