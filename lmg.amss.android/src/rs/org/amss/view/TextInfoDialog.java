package rs.org.amss.view;

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

import rs.org.amss.shell.BaseActivity;
import rs.org.amss.R;

public class TextInfoDialog extends DialogFragment {
	protected BaseActivity activityContext;
	protected TextView textView;
	protected String text;

	@SuppressLint("ValidFragment")
	public TextInfoDialog(String text, BaseActivity context) {
		this.activityContext = context;
		this.text = text;
	}
	public TextInfoDialog(){}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Dialog dialogView = new Dialog(getActivity());
		dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		dialogView.setContentView(R.layout.text_info_dialog);


		textView = (TextView) dialogView.findViewById(R.id.text_view);
		textView.setText(text);

		ImageView buttonClose = (ImageView) dialogView.findViewById(R.id.close_button);
		buttonClose.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				TextInfoDialog.this.getDialog().cancel();
			}
		});

		dialogView.show();
		return dialogView;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
