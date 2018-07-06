package rs.org.amss.shell;

import java.io.Serializable;

import com.crashlytics.android.Crashlytics;
import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.ActionBar.IntentAction;

import rs.org.amss.R;
import rs.org.amss.core.Constants;
import rs.org.amss.core.LayoutManager;
import rs.org.amss.core.MemoryManager;
import rs.org.amss.core.Variables;
import rs.org.amss.model.GetFonts;
import rs.org.amss.model.User;
import rs.org.amss.view.ChangeSMSTextDialog;
import rs.org.amss.view.DatePickerDialog;
import rs.org.amss.view.ExitDialog;
import rs.org.amss.view.ParkingDialog;
import rs.org.amss.view.TextInfoDialog;
import net.lmggroup.utility.ActivityHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.IntentCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.view.Gravity.RIGHT;

public class BaseActivity extends FragmentActivity {

    protected ProgressDialog progressDialog;
    protected ActivityHelper activityManager;
    protected MemoryManager memoryManager;
    protected LayoutManager layoutManager;
    protected ActionBar actionBar;

    private ViewGroup rootView;
    private DrawerLayout drawerLayout;
    private LinearLayout sideMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activityManager = new ActivityHelper(this);
        memoryManager = new MemoryManager(this);
        layoutManager = new LayoutManager(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        rootView = (ViewGroup) findViewById(android.R.id.content);
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.drawer, rootView, false);
        sideMenu = (LinearLayout) getLayoutInflater().inflate(R.layout.slider_menu, drawerLayout, false);
        DrawerLayout.LayoutParams sideMenuLayoutParams = (DrawerLayout.LayoutParams) sideMenu.getLayoutParams();
        if (sideMenuLayoutParams != null) {
            sideMenuLayoutParams.gravity = RIGHT;
            sideMenu.setLayoutParams(sideMenuLayoutParams);
        }
        View originalContent = rootView.getChildAt(0);
        rootView.removeAllViews();
        FrameLayout.LayoutParams layoutParamsContentView = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootView.addView(drawerLayout, layoutParamsContentView);
        drawerLayout.setId(R.id.material_drawer_layout);
        drawerLayout.addView(originalContent, 0);
        drawerLayout.addView(sideMenu, 1);

        drawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));

        View menuButton = findViewById(R.id.menuButton);
        if (menuButton != null) {
            menuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (drawerLayout.isDrawerOpen(RIGHT)) {
                        drawerLayout.closeDrawers();
                    } else {
                        drawerLayout.openDrawer(RIGHT);
                    }
                }
            });
        }

        sideMenu.findViewById(R.id.slideMenu).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        if (Variables.getUser() != null && Variables.getUser().getMembershipId() > 0) {
            ((TextView) sideMenu.findViewById(R.id.accountMenuItemText)).setText("Izloguj se");
            Variables.loadMemberBasicInfo(getApplicationContext());
            if (Variables.memberName != null) {
                loadUserLabel(Variables.memberName);
            }
//            View profileButton = drawerLayout.findViewById(R.id.profileButton);
//            profileButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    activityManager.startActivity(MembershipActivity.class);
//                }
//            });
//            profileButton.setVisibility(View.VISIBLE);

        }
        sideMenu.findViewById(R.id.accountMenuItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Variables.getUser() != null && Variables.getUser().getMembershipId() > 0) {
                    Variables.setUser(new User());
                    Variables.setMember(null);
                    Variables.setMembershipInfo(null);
                    Variables.memberName = null;
                    Variables.vehicleColor = null;
                    Variables.vehicleVendor = null;
                    memoryManager.ClearPreferences();
                    Intent i = new Intent(BaseActivity.this, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else {
                    drawerLayout.closeDrawers();
                    activityManager.startActivity(LoginActivity.class);
                }
            }
        });
        sideMenu.findViewById(R.id.aboutMenuItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startActivity(AboutUsActivity.class);
            }
        });
        if (findViewById(R.id.subtitle) != null) {
            TextView subtitle = (TextView) findViewById(R.id.subtitle);
            if (!subtitle.getText().toString().isEmpty()) {
                if(!subtitle.getText().toString().contains("/")) {
                    String text = "/  " + subtitle.getText().toString();
                    subtitle.setText(text);
                }
            }
        }
    }

    protected void loadUserLabel(String value) {
        TextView userLabel = (TextView) drawerLayout.findViewById(R.id.userLabel);
        userLabel.setVisibility(View.VISIBLE);
        userLabel.setText(value);
        userLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startActivity(MembershipActivity.class);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Variables.getUser() != null && Variables.getUser().getMembershipId() > 0) {
            TextView item = (TextView) sideMenu.findViewById(R.id.accountMenuItemText);
            item.setText("Izloguj se");
            item.invalidate();
        }
        sideMenu.findViewById(R.id.accountMenuItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Variables.getUser() != null && Variables.getUser().getMembershipId() > 0) {
                    Variables.setUser(new User());
                    Variables.setMember(null);
                    memoryManager.ClearPreferences();
                    Intent i = new Intent(BaseActivity.this, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else {
                    drawerLayout.closeDrawers();
                    activityManager.startActivity(LoginActivity.class);
                }
            }
        });
        sideMenu.findViewById(R.id.aboutMenuItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startActivity(AboutUsActivity.class);
            }
        });
    }

    public void showMain(View view) {
        activityManager.startActivityWithFlag(MainActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    public void showSettings(View view) {
        activityManager.startActivity(HeaderInfoActivity.class);
    }

    public void showSignIn(View view) {
        if (Variables.getUser() != null && Variables.getUser().getMembershipId() > 0)
            activityManager.startActivity(MembershipActivity.class);
        else
            activityManager.startActivity(LoginActivity.class);
    }

    public void showSignOut(View view) {
        activityManager.startActivity(LoginActivatedActivity.class);
    }

    public void showSafetyScreen1(View view) {
        activityManager.startActivity(SafetyActivity.class);
    }

    public void showSafetyScreen2(View view) {
        activityManager.startActivity(SafetyActivity2.class);
    }

    public void showParkingActivity(View view) {
        activityManager.startActivity(ParkingActivity.class);
    }

    public void showNews(View view) {
        activityManager.startActivity(NewsActivity.class);
    }

//	@SuppressLint("Override")
//	public ActionBar getActionBar() {
//		if (actionBar == null){
//			View actionBarView = findViewById(R.id.actionbar);
//			if (actionBarView != null)
//				actionBar = (ActionBar)actionBarView;
//		}
//		return actionBar;
//	}

    public void setTitle() {
        if (getActionBar() != null)
            actionBar.setTitle(getTitle());
    }

    public void setTitle(String title) {
        if (getActionBar() != null)
            actionBar.setTitle(title);
    }

    public void setTitle(int titleId) {
        if (getActionBar() != null)
            actionBar.setTitle(getString(titleId));
    }

    public void setHomeIcon(int drawableId) {
        if (getActionBar() != null) {
            actionBar.setHomeLogo(drawableId);
        }
    }

    public void setHomeAction(int drawableId) {
        if (getActionBar() != null) {
            actionBar.setHomeAction(new IntentAction(this, createHomeIntent(), drawableId));
            //			actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void setHomeAction(int drawableId, int titleId, Class<?> activityClass) {
        if (getActionBar() != null) {
            actionBar.setHomeAction(new IntentAction(this, getBackToChooserIntent(activityClass), drawableId));
            //			actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(titleId));
        }
    }

    public void setHomeAction(int drawableId, String title, Class<?> activityClass) {
        if (getActionBar() != null) {
            actionBar.setHomeAction(new IntentAction(this, getBackToChooserIntent(activityClass), drawableId));
            //			actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
        }
    }

    public Intent createHomeIntent() {
        final Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    public Intent getBackToChooserIntent(Class<?> activityClass) {
        final Intent intent = new Intent(this, activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    public Intent createShareIntent(String title, String extraText) {
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, extraText);
        return Intent.createChooser(intent, title);
    }

    public void addAction(Action action) {
        if (getActionBar() != null)
            actionBar.addAction(action);
    }

    public void showProgress() {
        if (getActionBar() != null)
            actionBar.setProgressBarVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        if (getActionBar() != null)
            actionBar.setProgressBarVisibility(View.GONE);
    }

    public ActivityHelper getActivityManager() {
        return activityManager;
    }

    public MemoryManager getMemoryManager() {
        return memoryManager;
    }

    public LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public Serializable getIntentExtra(String key) {
        Bundle extras = getIntent().getExtras();
        return extras != null ? extras.getSerializable(key) : null;
    }

    public void getDialog(Activity activity) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ParkingDialog parkingDialog = new ParkingDialog(activity);
        parkingDialog.setCancelable(false);
        parkingDialog.show(fragmentManager, "Activity");
    }

    public void getEditTextDialog(Activity activity) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        new ChangeSMSTextDialog(activity).show(fragmentManager, "Activity");
    }

    public void getNotificationDialog(Activity activity, String text) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //new NotificationDialog(activity, text).show(fragmentManager, "Activity");
        new TextInfoDialog(text, (BaseActivity) activity).show(fragmentManager, "Activity");
    }

    public void getDatePickerDialog(Activity activity) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        new DatePickerDialog(activity).show(fragmentManager, "Activity");
    }

    public void getExitDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        new ExitDialog(this).show(fragmentManager, "Activity");
    }

    public void showYouTubeVideo(String videoId) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + videoId));
        startActivity(intent);
    }

    public void showVideo(String videoUri) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUri));
        startActivity(intent);
    }

    public void readImageDimensions(int imageID) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), imageID, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;
        Log.v("Image:", imageHeight + " " + imageWidth + " " + imageType);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static void checkIsLogIn(Activity activity) {
//		Button sign = (Button)activity.findViewById(R.id.buttonSign);
//		Button signOut = (Button)activity.findViewById(R.id.buttonSignOut);
        if (Variables.getUser() != null && Variables.getUser().getMembershipId() > 0) {
//			sign.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.profil));
//			signOut.setVisibility(View.VISIBLE);
            DrawerLayout drawerLayout = (DrawerLayout) activity.findViewById(R.id.material_drawer_layout);
            if (drawerLayout != null) {
                drawerLayout.closeDrawers();
            }
            Crashlytics.setUserIdentifier(Variables.getUser().getMembershipCardId());
            if (Variables.getMember() != null) {
                Crashlytics.setUserEmail(Variables.getMember().email);
                Crashlytics.setUserName(Variables.getMember().firstName + " " + Variables.getMember().lastName);
            }
        } else {
//			sign.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.profil));
        }
    }

    public void setUpAMSOActionBar(String title, int imageResourceID) {
        setUpAMSOActionBar(title, imageResourceID, false);
    }

    public void GoToAMSOMenu(View v) {
        activityManager.startActivity(AMSOActivity.class);
    }

    public void setUpAMSOActionBar(String title, int imageResourceID, boolean hideCallCenter) {
        TextView titleView = (TextView) findViewById(R.id.amso_title);
        titleView.setText(title);

        ImageView activityIcon = (ImageView) findViewById(R.id.activity_icon);
        activityIcon.setImageResource(imageResourceID);

        if (hideCallCenter) {
            ImageView callCenterIcon = (ImageView) findViewById(R.id.call_center);
            callCenterIcon.setVisibility(View.GONE);
        }
    }

    public void setUpAMSOFooter() {
        TextView footer = (TextView) findViewById(R.id.textView1);
        footer.setTypeface(GetFonts.getTypeface(BaseActivity.this));
    }

    public void callAmsoCallCenter(View view) {
        getActivityManager().makeCall(Constants.amsoCallCenterPhoneNumber);
    }

}
