package rs.org.amss.view;

import rs.org.amss.model.GetFonts;
import rs.org.amss.shell.BaseActivity;
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

public class SendParkingSmsDialog  extends DialogFragment {

	protected BaseActivity activityContext;

	protected BaseMapActivity mapActivityContext;

	public String number;
	public String message;

	@SuppressLint("ValidFragment")
	public SendParkingSmsDialog(BaseActivity context, String num, String msg){
		this.activityContext = context;
		this.number = num;
		this.message = msg;
	}
	public SendParkingSmsDialog(){}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		return super.onCreateView(inflater, container, savedInstanceState);
	}


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Dialog dialogView = new Dialog(getActivity());
		dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		dialogView.setContentView(R.layout.send_parking_sms_dialog);

		TextView parkingMessage = (TextView)dialogView.findViewById(R.id.parking_message);
		parkingMessage.setText("Poslati poruku na "+number+"?");	
		parkingMessage.setTypeface(GetFonts.getTypeface(activityContext));

		ImageView buttonOk = (ImageView) dialogView.findViewById(R.id.buttonCallHelpDialogOk);
		buttonOk.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (activityContext != null) {
					activityContext.getActivityManager().sendSMS(number, message);
					activityContext.getLayoutManager().showInfo("Poruka poslata na "+number);
				}
				SendParkingSmsDialog.this.getDialog().cancel();
			}
		});

		ImageView buttonCancel = (ImageView) dialogView.findViewById(R.id.buttonCallHelpDialogCancel);
		buttonCancel.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				SendParkingSmsDialog.this.getDialog().cancel();
			}
		});

		dialogView.show();

		return dialogView;
	}

}
