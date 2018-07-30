package rs.org.amss.shell;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import rs.org.amss.R;
import rs.org.amss.core.BenefitsHistoryAdapter;
import rs.org.amss.core.Variables;
import rs.org.amss.core.WebMethods;
import rs.org.amss.core.WebResponseParser;
import rs.org.amss.model.GetFonts;
import rs.org.amss.model.History;
import rs.org.amss.model.Member;
import rs.org.amss.model.MemberNew;
import rs.org.amss.model.Membership;
import rs.org.amss.model.ServisTest;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MembershipActivity extends BaseActivity {
	private View[] tabTriangles;
	private List<History> history = null;
    private static final String TAG = "MembershipActivity";
	private boolean servicesLoaded = false;
	private boolean memberLoaded = false;
	private boolean membershipLoaded = false;
	private String info = "";


    @Override
    public void onBackPressed() {
        goToHome();
    }

    @SuppressLint("WrongConstant")
    private void goToHome() {
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.membership);
		checkIsLogIn(this);
		setHomeAction(R.drawable.ic_membership, R.string.membership_title, MainActivity.class);
		final Context context = this;
		//new GetMemberInfoTask().execute();
		new GetServicesInfoTask().execute();
//		new GetMembershipInfoTask().execute();
		tabTriangles = new View[] {
			findViewById(R.id.benefits_tab_status_triangle),
			findViewById(R.id.benefits_tab_history_triangle),
			findViewById(R.id.benefits_tab_my_card_triangle)
		};


		((TextView)findViewById(R.id.title)).setText(R.string.main_members_text);
		((TextView)findViewById(R.id.subtitle)).setText("");
//		((TextView)findViewById(R.id.mojakartica)).setTypeface(GetFonts.getBoldTypeface(this));
		findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				goToHome();
			}
		});



		/*progressDialog = ProgressDialog.show(this, getResources().getString(R.string.membership_progress_title), getResources().getString(R.string.MembershipActivity_ProgressBar_Message));
		progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					progressDialog.cancel();
					MembershipActivity.this.finish();
					return true;
				}
				return false;
			}
		});
		progressDialog.show();*/

		final ViewPager viewPager = (ViewPager) findViewById(R.id.membership_view_pager);
		viewPager.setAdapter(new PagerAdapter() {
			@Override
			public Object instantiateItem(ViewGroup collection, int position) {
				LayoutInflater inflater = LayoutInflater.from(context);
				ViewGroup layout = null;
				switch(position) {
				case 0:
					layout = (ViewGroup) inflater.inflate(R.layout.membership_benefits_view, collection, false);
                    ((TextView)layout.findViewById(R.id.benefits_o1)).setTypeface(GetFonts.getBoldTypeface(MembershipActivity.this));
                    ((TextView)layout.findViewById(R.id.benefits_o2)).setTypeface(GetFonts.getBoldTypeface(MembershipActivity.this));
                    ((TextView)layout.findViewById(R.id.benefits_o3)).setTypeface(GetFonts.getBoldTypeface(MembershipActivity.this));
                    ((TextView)layout.findViewById(R.id.benefits_o4)).setTypeface(GetFonts.getBoldTypeface(MembershipActivity.this));
                    ((TextView)layout.findViewById(R.id.benefits_o5)).setTypeface(GetFonts.getBoldTypeface(MembershipActivity.this));
                    ((TextView)layout.findViewById(R.id.benefits_o6)).setTypeface(GetFonts.getBoldTypeface(MembershipActivity.this));
                    ((TextView)layout.findViewById(R.id.benefits_o7)).setTypeface(GetFonts.getBoldTypeface(MembershipActivity.this));
                    ((TextView)layout.findViewById(R.id.benefits_s1)).setTypeface(GetFonts.getBoldTypeface(MembershipActivity.this));
                    ((TextView)layout.findViewById(R.id.benefits_s2)).setTypeface(GetFonts.getBoldTypeface(MembershipActivity.this));
                    ((TextView)layout.findViewById(R.id.benefits_s3)).setTypeface(GetFonts.getBoldTypeface(MembershipActivity.this));
                    ((TextView)layout.findViewById(R.id.benefits_s4)).setTypeface(GetFonts.getBoldTypeface(MembershipActivity.this));
                    ((TextView)layout.findViewById(R.id.benefits_s5)).setTypeface(GetFonts.getBoldTypeface(MembershipActivity.this));
                    ((TextView)layout.findViewById(R.id.benefits_s7)).setTypeface(GetFonts.getBoldTypeface(MembershipActivity.this));
                    ((TextView)layout.findViewById(R.id.benefits_s8)).setTypeface(GetFonts.getBoldTypeface(MembershipActivity.this));
                    ((TextView)layout.findViewById(R.id.benefits_s9)).setTypeface(GetFonts.getBoldTypeface(MembershipActivity.this));
                    ((TextView)layout.findViewById(R.id.benefits_s10)).setTypeface(GetFonts.getBoldTypeface(MembershipActivity.this));
                    ((TextView)layout.findViewById(R.id.benefits_s11)).setTypeface(GetFonts.getBoldTypeface(MembershipActivity.this));
					break;
				case 1:
					layout = (ViewGroup) inflater.inflate(R.layout.membership_history_view, collection, false);
					break;
				case 2:
					layout = (ViewGroup) inflater.inflate(R.layout.membership_card_view, collection, false);
					break;
				}
				collection.addView(layout);

				return layout;
			}

			@Override
			public boolean isViewFromObject(View view, Object object) {
				return view == object;
			}

			@Override
			public int getCount() {
				return 3;
			}

			@Override
			public void destroyItem(ViewGroup collection, int position, Object view) {
				collection.removeView((View) view);
			}


		});

        findViewById(R.id.benefits_tab_history).setBackgroundColor(0x00000000);
		findViewById(R.id.benefits_tab_my_card).setBackgroundColor(0x00000000);
        findViewById(R.id.benefits_tab_status).setBackgroundDrawable(getResources().getDrawable(R.drawable.primary_button));

//		findViewById(R.id.mojakarticabaton).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//                final ViewGroup cardBackgroundTint = (ViewGroup) findViewById(R.id.cardBackgroundLayout);
//                final View back = findViewById(R.id.tintBackButton);
//                back.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        cardBackgroundTint.setVisibility(View.GONE);
//                        back.setVisibility(View.GONE);
//                    }
//                });
//                cardBackgroundTint.setVisibility(View.VISIBLE);
//                back.setVisibility(View.VISIBLE);
//                View cardView = getLayoutInflater().inflate(R.layout.membership_card_view, cardBackgroundTint, false);
//                cardBackgroundTint.addView(cardView);
//                TextView broj = (TextView) cardBackgroundTint.findViewById(R.id.membership_broj_kartice);
//                TextView ime = (TextView) cardBackgroundTint.findViewById(R.id.membership_ime);
//                TextView datumIsteka = (TextView) findViewById(R.id.membership_datum_isteka);
//                new GetMembershipInfoTask().execute();
//                broj.setText(Variables.getUser().getMembershipCardNumber() + "");
//                ime.setText(Variables.getMember().lastName + " " + Variables.getMember().firstName);
//                cardView.invalidate();
//                cardView.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View view, MotionEvent motionEvent) {
//                        return true;
//                    }
//                });
//                cardBackgroundTint.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        cardBackgroundTint.setVisibility(View.GONE);
//                    }
//                });
//			}
//		});

		viewPager.setOnPageChangeListener( new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				for (int i = 0; i < tabTriangles.length; i++) {
					tabTriangles[i].setVisibility(View.INVISIBLE);
				}
				tabTriangles[position].setVisibility(View.VISIBLE);

				if (position == 0) {
                    findViewById(R.id.benefits_tab_history).setBackgroundColor(0x00000000);
                    findViewById(R.id.benefits_tab_my_card).setBackgroundColor(0x00000000);
                    findViewById(R.id.benefits_tab_status).setBackgroundDrawable(getResources().getDrawable(R.drawable.primary_button));
					//(new GetMemberInfoTask()).execute();
					(new GetServicesInfoTask()).execute();
				} else if (position == 1) {
                    findViewById(R.id.benefits_tab_status).setBackgroundColor(0x00000000);
                    findViewById(R.id.benefits_tab_my_card).setBackgroundColor(0x00000000);
                    findViewById(R.id.benefits_tab_history).setBackgroundDrawable(getResources().getDrawable(R.drawable.primary_button));
					(new GetHistoryTask()).execute();
				} else if (position == 2) {
                    findViewById(R.id.benefits_tab_history).setBackgroundColor(0x00000000);
                    findViewById(R.id.benefits_tab_status).setBackgroundColor(0x00000000);
                    findViewById(R.id.benefits_tab_my_card).setBackgroundDrawable(getResources().getDrawable(R.drawable.primary_button));
					TextView broj = (TextView) findViewById(R.id.membership_broj_kartice);
					TextView ime = (TextView) findViewById(R.id.membership_ime);
					TextView datumIsteka = (TextView) findViewById(R.id.membership_datum_isteka);

					if (broj != null){
						broj.setTypeface(GetFonts.getBoldTypeface(MembershipActivity.this));
						//broj.setText(Variables.getUser().getMembershipCardSeries() +"-"+ Variables.getUser().getMembershipCardNumber());
					}
					/*if (ime != null) {
						ime.setTypeface(GetFonts.getBoldTypeface(MembershipActivity.this));
						if (Variables.getMember().isIndividual)
    						ime.setText(Variables.getMember().firstName + " " + Variables.getMember().lastName);
						else
						    ime.setText(Variables.getMember().companyName);
					}*/
					if (datumIsteka != null)
						datumIsteka.setTypeface(GetFonts.getBoldTypeface(MembershipActivity.this));

					new GetMembershipInfoTask().execute();
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

			@Override
			public void onPageScrollStateChanged(int state) { }
		});

		TextView status = (TextView) findViewById(R.id.benefits_tab_status);
		status.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				viewPager.setCurrentItem(0);
			}
		});

		TextView history = (TextView)findViewById(R.id.benefits_tab_history);
		history.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				viewPager.setCurrentItem(1);
			}
		});

		TextView myCard = (TextView)findViewById(R.id.benefits_tab_my_card);
		myCard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				viewPager.setCurrentItem(2);
			}
		});
	}
    public static Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap( v.getLayoutParams().width,  v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;
    }

	private class GetMemberInfoTask extends AsyncTask<Object, Void, MemberNew> {

		protected MemberNew doInBackground(Object... parameters) {
			int memberId = Variables.getUser().getMembershipId();
            Log.d(TAG, "doInBackground: "+memberId);
            MemberNew member = Variables.getMember();
			if (member == null) {
				String response;
				try {
					response = WebMethods.getMemberInfo(memberId);
					member = WebResponseParser.getMemberInfo(response);
					Variables.setMember(member);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
					MembershipActivity.this.getLayoutManager().showError(R.string.Loading_ClientProtocolException);
				} catch (IOException e) {
					e.printStackTrace();
					MembershipActivity.this.getLayoutManager().showError(R.string.Loading_IOException);
				} catch (Exception e) {
					e.printStackTrace();
					MembershipActivity.this.getLayoutManager().showError(R.string.Loading_Exception);
				}
			}
			return member;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		protected void onPostExecute(Member result) {
			if (result != null) {
				setHomeAction(R.drawable.ic_membership, Variables.getMember().lastName + " " + Variables.getMember().firstName/* + " " + Variables.getUser().getMembershipCardSeries().toUpperCase() + " " + Variables.getUser().getMembershipCardNumber()*/, MainActivity.class);
				if (Variables.getMember().isIndividual)
				    Variables.memberName = Variables.getMember().firstName + " " + Variables.getMember().lastName;
				else
				    Variables.memberName = Variables.getMember().companyName;

				Variables.vehicleVendor = Variables.getMember().vehicleBrand;
				Variables.vehicleColor = Variables.getMember().vehicleColor;
				Variables.storeMemberBasicInfo(getApplicationContext());
				loadUserLabel(Variables.memberName);
			}
			new GetMembershipInfoTask().execute();
			memberLoaded = true;
			if (memberLoaded == true && servicesLoaded == true && membershipLoaded == true) {
				progressDialog.cancel();
			}
		}
	}

	
	private class GetMembershipInfoTask extends AsyncTask<Object, Void, ArrayList<Membership>> {

		protected ArrayList<Membership> doInBackground(Object... parameters) {
			int memberId = Variables.getUser().getMembershipId();
			ArrayList<Membership> membership = Variables.getMembershipInfo();
			if (membership == null) {
				String response;
				try {
					response = WebMethods.getMembershipInfo(memberId);
                    Log.d(TAG, "doInBackground: membership response" + response);
					membership = WebResponseParser.getMembershipInfo(response);
                    Log.d(TAG, "doInBackground: membership parsovan " + membership.toString());
					Variables.setMembershipInfo(membership);

				} catch (ClientProtocolException e) {
					e.printStackTrace();
					MembershipActivity.this.getLayoutManager().showError(R.string.Loading_ClientProtocolException);
				} catch (IOException e) {
					e.printStackTrace();
					MembershipActivity.this.getLayoutManager().showError(R.string.Loading_IOException);
				} catch (Exception e) {
					e.printStackTrace();
					MembershipActivity.this.getLayoutManager().showError(R.string.Loading_Exception);
				}
			}
			return membership;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		protected void onPostExecute(ArrayList<Membership> result) {
			if (result != null) {
				for (Membership m : result) {
					if (m.cardNumber != null/* && Variables.getUser().getMembershipCardSeries().equalsIgnoreCase(m.cardNumber.substring(0, 1)) || Variables.getUser().getMembershipCardSeries().equalsIgnoreCase(m.typeOfMembershipName.substring(0, 1))*/) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						SimpleDateFormat output = new SimpleDateFormat("dd.MM.yyyy");
						Date validTo = null;
						Date validFrom = null;
						try {
							if (m.validTo != null) {
								validTo = sdf.parse(m.validTo.split("T")[0]);
								validFrom = sdf.parse(m.validFrom.split("T")[0]);
								TextView validToText = (TextView)findViewById(R.id.membership_datum_isteka);
								if (validToText != null) {
									validToText.setText(output.format(validTo));
								}
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						info =  "Id člana: " + Variables.getUser().getMembershipId() + '\n';
						info += "Ime: " + Variables.getMember().firstName + '\n';
						info += "Prezime: " + Variables.getMember().lastName + '\n';
						info += "Broj kartice: " + Variables.getUser().getMembershipCardId() + '\n';
						//info += "Serija: " + Variables.getUser().getMembershipCardSeries().toUpperCase() + '\n';
						info += "Broj registracije: " + Variables.getUser().getRegistrationPlate() + '\n';
						if (validFrom != null)
							info += "Važi od: " + output.format(validFrom) + '\n';
						if (validTo != null)
							info += "Važi do: " + output.format(validTo) + '\n';

					/*	if (Variables.getMember().isIndividual)
							((TextView)findViewById(R.id.subtitle)).setText(Variables.getMember().firstName + " " + Variables.getMember().lastName + " " + Variables.getUser().getMembershipCardSeries() + "-" + Variables.getUser().getMembershipCardNumber());
						else
							((TextView)findViewById(R.id.subtitle)).setText(Variables.getMember().companyName + " " + Variables.getUser().getMembershipCardSeries() + "-" + Variables.getUser().getMembershipCardNumber());
					*/}
				}
			}

			membershipLoaded = true;
			if (memberLoaded == true && servicesLoaded == true && membershipLoaded == true) {
				progressDialog.cancel();
			}
		}
	}

	private class GetServicesInfoTask extends AsyncTask<Object, Void, Map<String, Integer>> {

		protected Map<String, Integer> doInBackground(Object... parameters) {
			int memberId = Variables.getUser().getMembershipId();
			Map<String, Integer> info = null;
			String response;
			try {
                Log.d("test", "doInBackground: memberId :" +memberId);
				response = WebMethods.getServicesInfoNew(memberId);
                Log.d("test", "doInBackground: response :" +response);
				info = WebResponseParser.getServicesInfo(response);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				MembershipActivity.this.getLayoutManager().showError(R.string.Loading_ClientProtocolException);
			} catch (IOException e) {
				e.printStackTrace();
				MembershipActivity.this.getLayoutManager().showError(R.string.Loading_IOException);
			} catch (Exception e) {
				e.printStackTrace();
				MembershipActivity.this.getLayoutManager().showError(R.string.Loading_Exception);
			}
			return info;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		private void setText(int id, int number) {
			if (number >= -1) {
				TextView tv = (TextView) MembershipActivity.this.findViewById(id);
				if (tv != null) {
					if (number == 0) {
						tv.setText("0");
					} else if (number == -1) {
						tv.setText("0");
						((View)tv.getParent()).setBackgroundDrawable(getResources().getDrawable(R.drawable.primary_button));
					} else {
						tv.setText(number + "x");
					}
				}
			}
		}


		protected void onPostExecute(Map<String, Integer> result) {
		    if (result != null){
                for (String key : result.keySet()) {
                    if ("O1".equalsIgnoreCase(key)) {
                        setText(R.id.benefits_o1, result.get(key));
                    } else if ("O2".equalsIgnoreCase(key)) {
                        setText(R.id.benefits_o2, result.get(key));
                    } else if ("O3".equalsIgnoreCase(key)) {
                        setText(R.id.benefits_o3, result.get(key));
                    } else if ("O4".equalsIgnoreCase(key)) {
                        setText(R.id.benefits_o4, result.get(key));
                    } else if ("O5".equalsIgnoreCase(key)) {
                        setText(R.id.benefits_o5, result.get(key));
                    } else if ("O6".equalsIgnoreCase(key)) {
                        setText(R.id.benefits_o6, result.get(key));
                    } else if ("O7".equalsIgnoreCase(key)) {
                        setText(R.id.benefits_o7, result.get(key));
                    } else if ("S1".equalsIgnoreCase(key)) {
                        setText(R.id.benefits_s1, result.get(key));
                    } else if ("S2".equalsIgnoreCase(key)) {
                        setText(R.id.benefits_s2, result.get(key));
                    } else if ("S3".equalsIgnoreCase(key)) {
                        setText(R.id.benefits_s3, result.get(key));
                    } else if ("S4".equalsIgnoreCase(key)) {
                        setText(R.id.benefits_s4, result.get(key));
                    } else if ("S5".equalsIgnoreCase(key)) {
                        setText(R.id.benefits_s5, result.get(key));
                    } else if ("S6".equalsIgnoreCase(key)) {
                        //setText(R.id.benefits_s6, result.get(key));
                    } else if ("S7".equalsIgnoreCase(key)) {
                        setText(R.id.benefits_s7, result.get(key));
                    } else if ("S8".equalsIgnoreCase(key)) {
                        setText(R.id.benefits_s8, result.get(key));
                    } else if ("S9".equalsIgnoreCase(key)) {
                        setText(R.id.benefits_s9, result.get(key));
                    } else if ("S10".equalsIgnoreCase(key)) {
                        setText(R.id.benefits_s10, result.get(key));
                    } else if ("S11".equalsIgnoreCase(key)) {
                        setText(R.id.benefits_s11, result.get(key));
                    }
                }
            } else {
		        View membershipBenefitsContainer = findViewById(R.id.membership_benefits_container);
		        if (membershipBenefitsContainer != null)
		        	membershipBenefitsContainer.setVisibility(View.GONE);
            }

			servicesLoaded = true;
			if (memberLoaded == true && servicesLoaded == true && membershipLoaded == true) {
				progressDialog.cancel();
			}
		}
	}

	private class GetHistoryTask extends AsyncTask<Object, Void, List<History>> {

		protected List<History> doInBackground(Object... parameters) {
			List<History> history = null;
			if (MembershipActivity.this.history == null) {
				String response;
				try {
					response = WebMethods.getHistoryNew(Variables.getUser().getMembershipCardId());
					history = WebResponseParser.getHistory(response);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
					MembershipActivity.this.getLayoutManager().showError(R.string.Loading_ClientProtocolException);
				} catch (IOException e) {
					e.printStackTrace();
					MembershipActivity.this.getLayoutManager().showError(R.string.Loading_IOException);
				} catch (Exception e) {
					e.printStackTrace();
					MembershipActivity.this.getLayoutManager().showError(R.string.Loading_Exception);
				}
			} else {
				history = MembershipActivity.this.history;
			}

			return history;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		protected void onPostExecute(List<History> result) {
			MembershipActivity.this.history = result;
			ListView list = (ListView) findViewById(R.id.benefits_history_list);

			ArrayList<Object> grouped = new ArrayList<Object>();
			HashMap<String, ArrayList<History>> grouping = new HashMap<String, ArrayList<History>>();

			if (result != null) {
                for (int i = 0; i < result.size(); i++) {
                    History h = result.get(i);
                    if (grouping.containsKey(h.pogodnost_id)) {
                        grouping.get(h.pogodnost_id).add(h);
                    } else {
                        grouping.put(h.pogodnost_id, new ArrayList<History>());
                        grouping.get(h.pogodnost_id).add(h);
                    }
                }
            }

			for (String s : grouping.keySet()) {
				grouped.add(s);                           // Add header
				for (int j = 0; j < grouping.get(s).size(); j++) {
					grouped.add(grouping.get(s).get(j));  // Add value
				}
			}


			BenefitsHistoryAdapter adapter = new BenefitsHistoryAdapter(MembershipActivity.this, result);
			list.setAdapter(adapter);
		}


	}
}
