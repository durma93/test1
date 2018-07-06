package rs.org.amss.core;

import rs.org.amss.model.GetFonts;
import rs.org.amss.R;
import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LayoutRunnable implements Runnable
{
	private final Activity mainActivity;

	private final int dialogType;

	private final Object parameter;

	public LayoutRunnable(Activity activity, int type, Object param)
	{
		this.mainActivity = activity;
		this.dialogType = type;
		this.parameter = param;
	}	
	
	@Override
	public void run()
	{
		switch (this.dialogType)
		{	
		case LayoutManager.SHORT_LASTING_SIMPLE_TOAST:
			Toast.makeText(this.mainActivity.getApplicationContext(), parameter.toString(), Toast.LENGTH_SHORT).show();
			break;

		case LayoutManager.LONG_LASTING_SIMPLE_TOAST:
			Toast.makeText(this.mainActivity.getApplicationContext(), parameter.toString(), Toast.LENGTH_SHORT).show();
			break;

		case LayoutManager.SHORT_LASTING_INFO_TOAST:
			getToast(Toast.LENGTH_SHORT, R.drawable.info_white, parameter.toString()).show();
			break;

		case LayoutManager.LONG_LASTING_INFO_TOAST:
			getToast(Toast.LENGTH_LONG, R.drawable.info_white, parameter.toString()).show();
			break;

		case LayoutManager.SHORT_LASTING_WARNING_TOAST:
			getToast(Toast.LENGTH_SHORT, R.drawable.upozorenje_white, parameter.toString()).show();
			break;

		case LayoutManager.LONG_LASTING_WARNING_TOAST:
			getToast(Toast.LENGTH_LONG, R.drawable.upozorenje_white, parameter.toString()).show();
			break;

		case LayoutManager.SHORT_LASTING_ERROR_TOAST:
			getToast(Toast.LENGTH_SHORT, R.drawable.zabrana_white, parameter.toString()).show();
			break;

		case LayoutManager.LONG_LASTING_ERROR_TOAST:
			getToast(Toast.LENGTH_LONG, R.drawable.zabrana_white, parameter.toString()).show();
			break;
		}
	}

	private Toast getToast(int toastLength, int imageResource, String text)
	{
		LayoutInflater inflater = this.mainActivity.getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast, null);

		TextView textView = (TextView)layout.findViewById(R.id.textToast);
		textView.setText(text);
		textView.setTypeface(GetFonts.getTypeface(mainActivity));
		textView.setCompoundDrawablesWithIntrinsicBounds(0, imageResource, 0, 0);

		Toast toast = new Toast(this.mainActivity.getApplicationContext());
		toast.setDuration(toastLength);
		toast.setView(layout);
		toast.setGravity(Gravity.CENTER, 0,0);

		return toast;
	}

}
