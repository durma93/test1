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
import android.widget.Button;
import android.widget.TextView;

public class ExitDialog  extends DialogFragment {
	protected BaseActivity activityContext;
	protected Activity activity;
	
	@SuppressLint("ValidFragment")
	public ExitDialog(BaseActivity context){
		this.activityContext = context;
	}
	public ExitDialog(){}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Dialog dialogView = new Dialog(getActivity());
		dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		dialogView.setContentView(R.layout.exit_dialog);
		
		TextView title = (TextView) dialogView.findViewById(R.id.title);
		TextView message = (TextView) dialogView.findViewById(R.id.message);
		title.setTypeface(GetFonts.getBoldTypeface(activityContext));
		message.setTypeface(GetFonts.getTypeface(activityContext));

		Button buttonOk = (Button) dialogView.findViewById(R.id.buttonCallHelpDialogOk);
		buttonOk.setTypeface(GetFonts.getBoldTypeface(activityContext));
		buttonOk.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (activityContext != null) {
					activityContext.finish();
				}
				ExitDialog.this.getDialog().cancel();
			}
		});

		Button buttonCancel = (Button) dialogView.findViewById(R.id.buttonCallHelpDialogCancel);
		buttonCancel.setTypeface(GetFonts.getBoldTypeface(activityContext));
		buttonCancel.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				ExitDialog.this.getDialog().cancel();
			}
		});

		dialogView.show();

		return dialogView;

	}

}
