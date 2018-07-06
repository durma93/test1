package rs.org.amss.shell;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import rs.org.amss.R;
import rs.org.amss.core.Variables;
import rs.org.amss.core.WebMethods;
import rs.org.amss.core.WebResponseParser;
import rs.org.amss.model.Base64;
import rs.org.amss.model.DamageType;
import rs.org.amss.model.GetFonts;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class SafetyActivity2 extends BaseActivity {	
	TextView _address;
	ImageView image;
	String comment;
	Spinner damageTypes;
	EditText _comment;
	int serverResponseCode = 0;
	ProgressDialog dialog = null;
	List<String> list = new ArrayList<String>();
	String upLoadServerUri = null;
	String typeOfDamage;
	ProgressDialog progressDialog;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.safety_step2);
		setHomeAction(R.drawable.ic_safety, R.string.SafetyActivity_Label, SafetyActivity2.class);
		checkIsLogIn(SafetyActivity2.this);
		TextView _latitude = (TextView)findViewById(R.id.latitudeTxt);
		TextView _longitude = (TextView)findViewById(R.id.longitudeTxt);
		TextView _longitudeTxt = (TextView)findViewById(R.id.textView2);
		TextView _latitudeTxt = (TextView)findViewById(R.id.textView1);
		TextView _addressTxt = (TextView)findViewById(R.id.textView3);
		TextView _tipTxt = (TextView)findViewById(R.id.textView4);
		_address = (TextView)findViewById(R.id.addressTxt);

		damageTypes = (Spinner)findViewById(R.id.safety_spinner);
		image = (ImageView)findViewById(R.id.imgSlikaj);

		_comment = (EditText) findViewById(R.id.editText1);
		_latitude.setText(""+Variables.latitude);
		_longitude.setText(""+Variables.longitude);

		initializeDamageListGet();
		getAddress();
		getImage();	

		Button posalji = (Button) findViewById(R.id.btnDalje);
		
		posalji.setTypeface(GetFonts.getTypeface(SafetyActivity2.this));
		_comment.setTypeface(GetFonts.getTypeface(SafetyActivity2.this));
		_latitude.setTypeface(GetFonts.getTypeface(SafetyActivity2.this));
		_longitude.setTypeface(GetFonts.getTypeface(SafetyActivity2.this));
		_address.setTypeface(GetFonts.getTypeface(SafetyActivity2.this));
		_longitudeTxt.setTypeface(GetFonts.getTypeface(SafetyActivity2.this));
		_latitudeTxt.setTypeface(GetFonts.getTypeface(SafetyActivity2.this));
		_addressTxt.setTypeface(GetFonts.getTypeface(SafetyActivity2.this));
		_tipTxt.setTypeface(GetFonts.getTypeface(SafetyActivity2.this));

	}
	public void Send(View v){
		comment = _comment.getText().toString();
		getTypeOfDamage(Variables.damageList, typeOfDamage);
		progressDialog = ProgressDialog.show(SafetyActivity2.this, getResources().getString(R.string.main_safety_text), getResources().getString(R.string.SafetyActivity_Loading_Text));
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					progressDialog.cancel();
					SafetyActivity2.this.finish();
					return true;
				}
				return false;
			}
		});
		new ReportDamage().execute();
	}

	private void initializeDamageListGet() {
		progressDialog = ProgressDialog.show(SafetyActivity2.this, getResources().getString(R.string.main_safety_text), getResources().getString(R.string.NetworkActivity_ProgressBar_Message));
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					progressDialog.cancel();
					SafetyActivity2.this.finish();
					return true;
				}
				return false;
			}
		});
		new GetDamageTypesTask().execute();
	}


	private void getAddress() {
		Geocoder geocoder;
		List<Address> addresses;
		geocoder = new Geocoder(this, Locale.getDefault());
		try {
			//			addresses = geocoder.getFromLocation(Variables.latitude, Variables.longitude, 1);
			addresses = geocoder.getFromLocation(Variables.latitude, Variables.longitude, 1);
			if(addresses != null) {
				Address returnedAddress = addresses.get(0);
				StringBuilder strReturnedAddress = new StringBuilder();
				for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
					strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
				}
				_address.setText(strReturnedAddress.toString());
			} else{
				_address.setText("No Address returned!");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void getImage(){
		File img = new File(Variables.path);
		Log.v("Safety", Variables.path);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 8;

		Bitmap bitmap = BitmapFactory.decodeFile(img.getAbsolutePath(),options);
		image.setImageBitmap(bitmap);
	}

	private class ReportDamage extends AsyncTask<Object, Void, String> {			

		protected String doInBackground(Object... parameters) {

			String result = null;
			String response;
			try {
				URI uri = new URI(comment.replace(" ", "%20"));
				String imagePath = Variables.imageName+".jpg";
				String parsedComment = uri.toString();
				response = WebMethods.reportDamage(Variables.damageID, parsedComment, ""+Variables.latitude, ""+Variables.longitude, imagePath);
				uploadImage();
				result = WebResponseParser.parseSingleResponse(response);
				Log.v("Response", response);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				SafetyActivity2.this.getLayoutManager().showError(R.string.Loading_ClientProtocolException);
			} catch (IOException e) {
				e.printStackTrace();
				SafetyActivity2.this.getLayoutManager().showError(R.string.Loading_IOException);
			} catch (Exception e) {
				e.printStackTrace();
				SafetyActivity2.this.getLayoutManager().showError(R.string.Loading_Exception);
			}
			return result;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}

		protected void onPostExecute(String result) {

			if (result != null){ 
				finish();
				SafetyActivity2.this.getLayoutManager().showInfo(R.string.safety_status_ok_message);
			}
			else{
				Log.v("SafetyActivity2", "Error");
			}
			progressDialog.cancel();
		}
	}
	protected class GetDamageTypesTask extends AsyncTask<Object, Void, ArrayList<DamageType>> {			

		protected ArrayList<DamageType> doInBackground(Object... parameters) {
			ArrayList<DamageType> items = new ArrayList<DamageType>();
			if (items == null || items.size() == 0){
				String response;
				try {
					response = WebMethods.getDamageTypeList(); 				
					items = WebResponseParser.getDamageTypeList(response);

					Variables.damageList = items;

				} catch (ClientProtocolException e) {
					e.printStackTrace();
					SafetyActivity2.this.getLayoutManager().showError(R.string.Loading_ClientProtocolException);
				} catch (IOException e) {
					e.printStackTrace();
					SafetyActivity2.this.getLayoutManager().showError(R.string.Loading_IOException);
				} catch (Exception e) {
					e.printStackTrace();
					SafetyActivity2.this.getLayoutManager().showError(R.string.Loading_Exception);
				}
			}
			return items;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}

		protected void onPostExecute(ArrayList<DamageType> result) {

			if (result != null && result.size() > 0){ 
				for(int i=0;i<result.size();i++)
					list.add(result.get(i).name);
				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SafetyActivity2.this,
						android.R.layout.simple_spinner_item, list);
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				damageTypes.setAdapter(dataAdapter);
				damageTypes.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent, View arg1,
							int arg2, long arg3) {
						typeOfDamage = parent.getItemAtPosition(arg2).toString();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});
			}
			progressDialog.cancel();
		}
	}

	@SuppressWarnings("unused")
	public void uploadImage() {
		File img = new File(Variables.path);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 8;
		Bitmap bitmapOrg = BitmapFactory.decodeFile(img.getAbsolutePath(), options);
		Bitmap image = scaleDown(bitmapOrg, 800, true);
		ByteArrayOutputStream bao = new ByteArrayOutputStream(); 
		image.compress(Bitmap.CompressFormat.JPEG, 90, bao); 

		byte [] ba = bao.toByteArray();
		String name = Variables.imageName;
		String ba1=Base64.encodeBytes(ba); 
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(); 
		nameValuePairs.add(new BasicNameValuePair("image",ba1)); 
		nameValuePairs.add(new BasicNameValuePair("name", name));

		try{ 
			HttpClient httpclient = new DefaultHttpClient(); 
			HttpPost httppost = new HttpPost("http://87.250.52.162/amssandroid/bs72/upload2server.php"); 
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs)); 
			HttpResponse response = httpclient.execute(httppost); 
			HttpEntity entity = response.getEntity(); 
			InputStream is = entity.getContent();
		}catch(Exception e){ 
			Log.e("log_tag", "Error in http connection "+e.toString()); 
		} 

	}

	private void getTypeOfDamage(ArrayList<DamageType> listOfDamage, String type){
		for(int i=0; i<listOfDamage.size(); i++){
			if(listOfDamage.get(i).name.equals(type))
				Variables.damageID = listOfDamage.get(i).id;
		}
	}
	public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
			boolean filter) {
		float ratio = Math.min(
				(float) maxImageSize / realImage.getWidth(),
				(float) maxImageSize / realImage.getHeight());
		int width = Math.round((float) ratio * realImage.getWidth());
		int height = Math.round((float) ratio * realImage.getHeight());

		Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
				height, filter);
		return newBitmap;
	}
}
