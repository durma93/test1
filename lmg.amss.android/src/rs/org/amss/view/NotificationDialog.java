package rs.org.amss.view;

import rs.org.amss.model.GetFonts;
import rs.org.amss.shell.BaseActivity;
import rs.org.amss.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class NotificationDialog  extends DialogFragment {
	protected BaseActivity activityContext;
	String message;

	@SuppressLint("ValidFragment")
	public NotificationDialog(Activity activity, String text){
		this.activityContext = (BaseActivity) activity;
		this.message = text;
	}
	public NotificationDialog(){}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Dialog dialogView = new Dialog(getActivity());
		dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		dialogView.setContentView(R.layout.notification_dialog);

		TextView msg = (TextView)dialogView.findViewById(R.id.notification_text);
		msg.setTypeface(GetFonts.getTypeface(activityContext));
		msg.setText(message);
		msg.setTextSize(20);
		Button ok = (Button)dialogView.findViewById(R.id.confirm_button);
		ok.setTypeface(GetFonts.getBoldTypeface(activityContext));
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialogView.cancel();		
			}
		});
		dialogView.show();
		
		return dialogView;
	}

}
