package rs.org.amss.shell;

import java.io.File;
import java.io.FileInputStream;
import java.util.Random;

import rs.org.amss.R;
import rs.org.amss.core.GpsManager;
import rs.org.amss.core.Variables;
import rs.org.amss.model.GetFonts;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class SafetyActivity extends BaseActivity {


	protected static final String PHOTO_TAKEN = "photo_taken";

	GpsManager gps ;
	File imageDir;
	boolean taken = false;
	ImageView image;
	double latitude;
	double longitude;
	public String TAG="SafetyActivity";
	String path;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.safety_step1);
		setHomeAction(R.drawable.ic_safety, R.string.SafetyActivity_Label, MainActivity.class);
		checkIsLogIn(SafetyActivity.this);
		SetFonts();
		
		image = (ImageView)findViewById(R.id.imgSlikaj);

		findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});
		checkDoesFolderExists(getApplicationContext());
		Random ran = new Random();
		int random = ran.nextInt(10000000-1000000)+1000000;
		String file = "bs72_amss_safety"+random;
		Variables.imageName = file;
		path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AMSS/" + file + ".jpg";

	}
	private void SetFonts(){
		Button lokator, slikaj, dalje;
		EditText duzina, sirina;
		lokator = (Button)findViewById(R.id.btnGetLocation);
		slikaj = (Button)findViewById(R.id.btnSlikaj);
		dalje = (Button)findViewById(R.id.btnDalje);
		duzina = (EditText)findViewById(R.id.txtLong);
		sirina = (EditText)findViewById(R.id.txtLat);

		lokator.setTypeface(GetFonts.getTypeface(SafetyActivity.this));
		slikaj.setTypeface(GetFonts.getTypeface(SafetyActivity.this));
		dalje.setTypeface(GetFonts.getTypeface(SafetyActivity.this));
		duzina.setTypeface(GetFonts.getTypeface(SafetyActivity.this));
		sirina.setTypeface(GetFonts.getTypeface(SafetyActivity.this));
	}

	public void GetCoordinates(View v){
		gps=new GpsManager(SafetyActivity.this);
		if(gps.canGetLocation()){
			Variables.latitude = gps.getLatitude();
			Variables.longitude = gps.getLongitude();

			EditText etLat = (EditText) findViewById(R.id.txtLat);
			etLat.setText(""+Variables.latitude);

			EditText etLong = (EditText) findViewById(R.id.txtLong);
			etLong.setText(""+Variables.longitude);
		}
	}

	public void Next(View v){
		if(taken)
			activityManager.startActivity(SafetyActivity2.class);
		else
			Toast.makeText(getApplicationContext(), "Nedostaje slika!", Toast.LENGTH_LONG).show();
	}

	public void GetPicture(View v){
		startCameraActivity(path);
	}

	private void checkDoesFolderExists(Context context) {
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
			imageDir=new File(android.os.Environment.getExternalStorageDirectory()+"/AMSS");
		else
			imageDir=context.getCacheDir();
		if(!imageDir.exists())
			imageDir.mkdirs();
	}

	protected void startCameraActivity(String path)
	{
		Variables.path = path;
		File file = new File(path);
		Uri outputFileUri = Uri.fromFile(file);

		Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

		startActivityForResult(intent, 0);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{	
		Log.i( "MakeMachine", "resultCode: " + resultCode );
		switch( resultCode )
		{
		case 0:
			Toast.makeText(getApplicationContext(), "Photo NOT Taken", Toast.LENGTH_SHORT).show();
			Log.i( "MakeMachine", "User cancelled" );
			break;

		case -1:
			onPhotoTaken(Variables.path);
			Log.v(TAG, "Photo taken! "+Variables.path);
			break;
		}
	}

	protected void onPhotoTaken(String path)
	{
		File img = new File(path);
		taken = true;

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 8;

		Bitmap bitmap = BitmapFactory.decodeFile(img.getAbsolutePath(),options);
		if(bitmap==null){
			try {
				FileInputStream fi = new FileInputStream(img);
				Bitmap bitmap2 = BitmapFactory.decodeStream(fi);
				image.setImageBitmap(bitmap2);
				Log.v(TAG+" bm null", img.getAbsolutePath() + " "+bitmap);

			} catch (Exception ex) {

			}	
		}else{
			Log.v(TAG, img.getAbsolutePath() + " "+bitmap);
			image.setImageBitmap(bitmap);
		}
	}

}
