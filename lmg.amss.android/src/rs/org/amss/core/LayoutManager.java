package rs.org.amss.core;

public class LayoutManager {

	public static final String TAG = "LayoutManager";

	public static final int SHORT_LASTING_SIMPLE_TOAST = 0;

	public static final int LONG_LASTING_SIMPLE_TOAST = 1;

	public static final int SHORT_LASTING_INFO_TOAST = 2;

	public static final int LONG_LASTING_INFO_TOAST = 3;

	public static final int SHORT_LASTING_WARNING_TOAST = 4;

	public static final int LONG_LASTING_WARNING_TOAST = 5;

	public static final int SHORT_LASTING_ERROR_TOAST = 6;

	public static final int LONG_LASTING_ERROR_TOAST = 7;	

	private android.app.Activity activityContext;

	public LayoutManager(android.app.Activity activity)
	{
		this.activityContext = activity;
	}

	public void showInfoSimple(String text)
	{
		activityContext.runOnUiThread(new LayoutRunnable(activityContext, SHORT_LASTING_SIMPLE_TOAST, text));
	}

	public void showInfoSimple(int textId)
	{
		activityContext.runOnUiThread(new LayoutRunnable(activityContext, SHORT_LASTING_SIMPLE_TOAST, activityContext.getString(textId)));
	}

	public void showInfo(String text)
	{
		activityContext.runOnUiThread(new LayoutRunnable(activityContext, SHORT_LASTING_INFO_TOAST, text));
	}

	public void showInfo(int textId)
	{
		activityContext.runOnUiThread(new LayoutRunnable(activityContext, SHORT_LASTING_INFO_TOAST, activityContext.getString(textId)));
	}

	public void showWarning(String text)
	{
		activityContext.runOnUiThread(new LayoutRunnable(activityContext, LONG_LASTING_WARNING_TOAST, text));
	}

	public void showWarning(int textId)
	{
		activityContext.runOnUiThread(new LayoutRunnable(activityContext, LONG_LASTING_WARNING_TOAST, activityContext.getString(textId)));
	}

	public void showError(String text)
	{
		activityContext.runOnUiThread(new LayoutRunnable(activityContext, LONG_LASTING_ERROR_TOAST, text));
	}

	public void showError(int textId)
	{
		activityContext.runOnUiThread(new LayoutRunnable(activityContext, LONG_LASTING_ERROR_TOAST, activityContext.getString(textId)));
	}
}
