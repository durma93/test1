package rs.org.amss.shell;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;

import rs.org.amss.R;
import rs.org.amss.view.CallHelpDialog;
import rs.org.amss.view.SendSmsDialog;

public class CallHelpActivity extends BaseMapActivity implements View.OnClickListener
{
	public static final String TAG = "CallHelpActivity";

    View callToActionButton;
    View popover;
    View callButton;
    View smsButton;
    View backButton;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.callhelp);
		checkIsLogIn(CallHelpActivity.this);

        callToActionButton = findViewById(R.id.call_to_action_button);
        popover = findViewById(R.id.options_popover);
        callButton = findViewById(R.id.call_button);
        smsButton = findViewById(R.id.sms_button);
        backButton = findViewById(R.id.back_button);

        callToActionButton.setOnClickListener(this);
        popover.setOnClickListener(this);
        callButton.setOnClickListener(this);
        smsButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	public void callHelp(View view)
	{
		FragmentManager fragmentManager = getSupportFragmentManager();
		new CallHelpDialog(this).show(fragmentManager, TAG);
	}	

	public void sendSms(View view)
	{
		FragmentManager fragmentManager = getSupportFragmentManager();
        SendSmsDialog dialog = new SendSmsDialog();
        dialog.setActivityContext(this);
        dialog.show(fragmentManager, TAG);
	}

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.call_to_action_button:
                showPopover();
                break;
            case R.id.options_popover:
                hidePopover();
                break;
            case R.id.call_button:
                callHelp();
                break;
            case R.id.sms_button:
                sendSMS();
                break;
            case R.id.back_button:
                onBackPressed();
                break;
        }
    }

    private void showPopover(){
        popover.setVisibility(View.VISIBLE);
    }
    private void hidePopover(){
        popover.setVisibility(View.GONE);
    }
    private void callHelp()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        new CallHelpDialog(this).show(fragmentManager, TAG);
    }

    private void sendSMS()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SendSmsDialog dialog = new SendSmsDialog();
        dialog.setActivityContext(this);
        dialog.show(fragmentManager, TAG);
    }
}
