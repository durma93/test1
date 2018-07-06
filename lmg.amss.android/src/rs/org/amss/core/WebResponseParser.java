package rs.org.amss.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rs.org.amss.model.AmsoStation;
import rs.org.amss.model.AmssStation;
import rs.org.amss.model.Benefit;
import rs.org.amss.model.CameraNew;
import rs.org.amss.model.CityDistance;
import rs.org.amss.model.DamageType;
import rs.org.amss.model.EpiscopasyInfo;
import rs.org.amss.model.History;
import rs.org.amss.model.LoginStep1;
import rs.org.amss.model.LoginStep2;
import rs.org.amss.model.Member;
import rs.org.amss.model.Membership;
import rs.org.amss.model.Monastery;
import rs.org.amss.model.MonasteryDistance;
import rs.org.amss.model.Municipality;
import rs.org.amss.model.News;
import rs.org.amss.model.ParkingCity;
import rs.org.amss.model.ParkingCityNew;
import rs.org.amss.model.PaymentInvoice;
import rs.org.amss.model.RoadCondition;
import rs.org.amss.model.RoadConditionVideo;
import rs.org.amss.model.Toll;
import rs.org.amss.model.TollCity;
import rs.org.amss.model.TypeOfRate;
import rs.org.amss.model.VehicleType;
import rs.org.amss.R;
import net.lmggroup.utility.XmlParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kxml2.io.KXmlParser;
import org.kxml2.kdom.Document;
import org.kxml2.kdom.Element;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

public class WebResponseParser {

	public static final String TAG = "WebResponseParser";

	public static final String KEY_CAMERA = "Camera";
    public static final String KEY_GRUPA= "grupa";
    public static final String KEY_NAZIV = "naziv";
    public static final String KEY_GEOGRAFSKA_SIRINA = "geografska_sirina";
    public static final String KEY_GEOGRAFSKA_DUZINA = "geografska_duzina";
    public static final String KEY_OPIS = "opis";
    public static final String KEY_TEHNOLOGIJA = "tehnologija";
    public static final String KEY_UGAO_KAMERE = "ugao_kamere";
    public static final String KEY_URL = "url";

    public static final String KEY_GRADOVI_CHILD = "grad";
    
    private static final String KEY_PARKING_ZONA = "ParkingZones";
    private static final String KEY_CITY = "city";
    private static final String KEY_ICON = "icon";
    private static final String KEY_ZONE = "zone";
    private static final String KEY_MAXTIME = "maxTime";
    private static final String KEY_FROM_UNTIL = "fromUntil";
    private static final String KEY_PRICE = "price";
    private static final String KEY_SMS_NUMBER = "smsNumber";


    public static ArrayList<ParkingCityNew> getParkingZones(String response) {

        ArrayList<ParkingCityNew> arrayZones = new ArrayList<>();

        ParkingCityNew zona = null;
        
        String curText = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();

            ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
            InputStreamReader isr = new InputStreamReader(bin);

            xpp.setInput(isr);

            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase(KEY_PARKING_ZONA)) {
                            zona = new ParkingCityNew();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        curText = xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagName.equalsIgnoreCase(KEY_PARKING_ZONA)) {
                            arrayZones.add(zona);

                        } else if (tagName.equalsIgnoreCase(KEY_CITY)) {
                            zona.setCity(curText);
                        } else if (tagName.equalsIgnoreCase(KEY_ICON)) {
                            zona.setIcon(curText);
                        } else if (tagName.equalsIgnoreCase(KEY_ZONE)) {
                            zona.setZone(curText);
                        } else if (tagName.equalsIgnoreCase(KEY_MAXTIME)) {
                            zona.setMaxTime(curText);
                        } else if (tagName.equalsIgnoreCase(KEY_FROM_UNTIL)) {
                            zona.setFromUntil(curText);
                        } else if (tagName.equalsIgnoreCase(KEY_PRICE)) {
                            zona.setPrice(curText);
                        } else if (tagName.equalsIgnoreCase(KEY_SMS_NUMBER)) {
                            zona.setSmsNumber(curText);
                        }
                        break;

                    default:
                        break;

                }
                eventType = xpp.next();
            }
        }catch (Exception e){
            e.getMessage();
        }


        return arrayZones;
    
        /*if (response != null && response.equals("") == false)
        {
            KXmlParser xmlParser = new KXmlParser();
            Document xmlDoc = new Document();

            ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
            InputStreamReader isr = new InputStreamReader( bin );

            try
            {
                xmlParser.setInput(isr);
                xmlDoc.parse(xmlParser);
                Element xmlRoot = xmlDoc.getRootElement();
                if(xmlRoot != null)
                {
                    if(xmlRoot != null)
                    {
                        Element[] xmlChild = XmlParser.getChildren(xmlRoot);
                        xmlChild = XmlParser.getChildren(xmlChild[0]);
                        xmlChild = XmlParser.getChildren(xmlChild[0]);
                        xmlChild = XmlParser.getChildren(xmlChild[1]);
                        result.getSmsNumber() = new String[xmlChild.length];
                        result.zone = new ArrayList<String>(xmlChild.length);
                        result.icons = new Integer[xmlChild.length];
						result.info = new String[xmlChild.length];
						result.price = new String[xmlChild.length];
						result.maxTime = new String[xmlChild.length];
                        for(int i = 0; i < xmlChild.length; i++)
                        {
                            Element[] map = XmlParser.getChildren(xmlChild[i]);
                            for(Element item: map) {
                                Element[] content = XmlParser.getChildren(item);
                                String key = content[0].getText(0);
                                String value = content[1].getText(0);
                                Log.d("PARSE", key +" : "+value);
                                if(key.equals("zone")) {
                                    result.zone.add(value);
                                } else if(key.equals("icon")) {
                                    result.icons[i] = getIconFromString(value);
                                } else if(key.equals("smsNumber")) {
                                    result.brojevi[i] = value;
                                } else if(key.equals("fromUntil")) {
									result.info[i] = value;
								} else if(key.equals("price")) {
                                    result.price[i] = value;
								} else if(key.equals("maxTime")) {
                                	result.maxTime[i] = value;
								}
                            }
                        }
                    }
                }
            }
            catch (IOException e)
            {
                Log.e(TAG, e.getMessage());
            }
            catch (XmlPullParserException e)
            {
                Log.e(TAG, e.getMessage());
            }

            try
            {
                isr.close();
            }
            catch (IOException e) {}

        }

        return result;*/
    }


    public static List<CameraNew> getCameras(String response) throws XmlPullParserException {

        List<CameraNew> cameras = new ArrayList<>();

        CameraNew result = null;

        String curText = "";
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();

            ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
            InputStreamReader isr = new InputStreamReader( bin );

            xpp.setInput(isr);

            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT){
                String tagName = xpp.getName();

                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase(KEY_CAMERA)){
                            result = new CameraNew();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        curText = xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagName.equalsIgnoreCase(KEY_CAMERA)){
                            cameras.add(result);

                        }else if(tagName.equalsIgnoreCase(KEY_GRUPA)) {
                            result.setGrupa(curText);
                        }else if(tagName.equalsIgnoreCase(KEY_NAZIV)){
                            result.setNaziv(curText);
                        }else if(tagName.equalsIgnoreCase(KEY_GEOGRAFSKA_SIRINA)){
                            result.setGeografska_sirina(curText);
                        }else if(tagName.equalsIgnoreCase(KEY_GEOGRAFSKA_DUZINA)){
                            result.setGeografska_duzina(curText);
                        }else if(tagName.equalsIgnoreCase(KEY_OPIS)){
                            result.setOpis(curText);
                        }else if(tagName.equalsIgnoreCase(KEY_TEHNOLOGIJA)){
                            result.setTehnologija(curText);
                        }else if(tagName.equalsIgnoreCase(KEY_UGAO_KAMERE)){
                            result.setUgaoKamere(curText);
                        }else if(tagName.equalsIgnoreCase(KEY_URL)){
                            result.setUrl(curText);
                        }
                        break;

                    default:
                        break;
                }
                eventType = xpp.next();
            }
        }catch (Exception e){
            e.getMessage();
        }


        return cameras;
    }

    private static Integer getIconFromString(String value) {
        if(value.equals("crvena")) return R.drawable.zona1;
        else if(value.equals("zuta")) return R.drawable.zona2;
        else if(value.equals("zelena")) return R.drawable.zona3;
        else if(value.equals("plava")) return R.drawable.dnevna_karta;
        else if(value.equals("bela")) return R.drawable.zone_white;
        return R.drawable.dnevna_karta;
    }

    public static ArrayList<String> getParkingCities(String response) {


        ArrayList<String> nazivi = new ArrayList<>();

        String curText = "";
		try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();

            ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
            InputStreamReader isr = new InputStreamReader( bin );

            xpp.setInput(isr);

            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();

                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase(KEY_GRADOVI_CHILD));
                        break;

                    case XmlPullParser.TEXT:
                        curText = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagName.equalsIgnoreCase(KEY_GRADOVI_CHILD)) {
                            //String naziv = grad.getNaziv();
                            nazivi.add(curText);
                        }
                        /*else if (tagName.equalsIgnoreCase(KEY_GRADOVI_CHILD))
                            grad.setNaziv(curText);
                            break;*/

                    default:
                        break;
                }
                eventType = xpp.next();
            }
        }
		catch (Exception e){
		    e.getMessage();
        }

        return nazivi;


		/*ArrayList<String> result = new ArrayList<String>();
		if (response != null && response.equals("") == false)
		{
			KXmlParser xmlParser = new KXmlParser();
			Document xmlDoc = new Document();

			ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
			InputStreamReader isr = new InputStreamReader( bin );

			try
			{
				xmlParser.setInput(isr);
				xmlDoc.parse(xmlParser);
				Element xmlRoot = xmlDoc.getRootElement();
				if(xmlRoot != null)
				{

                        Element[] xmlChild = XmlParser.getChildren(xmlRoot);
                        xmlChild = XmlParser.getChildren(xmlChild[0]);
                        xmlChild = XmlParser.getChildren(xmlChild[0]);
                        //xmlChild = XmlParser.getChildren(xmlChild[1]);
                        for(Element element: xmlChild)
                        {
                            result.add(element.getText(0));
                            Log.d(TAG, "getParkingCities: iz parsera array: " +result.get(0));
                        }

				}
			}
			catch (IOException e)
			{
				Log.e(TAG, e.getMessage());
			}
			catch (XmlPullParserException e)
			{
				Log.e(TAG, e.getMessage());
			}

			try
			{
				isr.close();
			}
			catch (IOException e) {}

		}
		return result;*/
	}

        public static ArrayList<String> getStringValues(String response)
	{
		ArrayList<String> result = new ArrayList<String>();
		if (response != null && response.equals("") == false)
		{
			KXmlParser xmlParser = new KXmlParser();
			Document xmlDoc = new Document();

			ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
			InputStreamReader isr = new InputStreamReader( bin );

			try
			{
				xmlParser.setInput(isr);
				xmlDoc.parse(xmlParser);
				Element xmlRoot = xmlDoc.getRootElement();
				if(xmlRoot != null)
				{
					Element[] xmlChild = XmlParser.getChildren(xmlRoot);
					for ( int index = 0; index < xmlChild.length; ++index )
					{
						if (xmlChild[index].getChildCount() > 0){
							String item = xmlChild[index].getText(0);
							result.add(item);
						}
					}
				}
			}
			catch (IOException e)
			{
				Log.e(TAG, e.getMessage());
			}
			catch (XmlPullParserException e)
			{
				Log.e(TAG, e.getMessage());
			}

			try
			{
				isr.close();
			}
			catch (IOException e) {}

		}
		return result;
	}

	public static String parseSingleResponse(String response)
	{
		String result = null;
		if (response != null && response.equals("") == false)
		{
			KXmlParser xmlParser = new KXmlParser();
			Document xmlDoc = new Document();

			ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
			InputStreamReader isr = new InputStreamReader( bin );

			try
			{
				xmlParser.setInput(isr);
				xmlDoc.parse(xmlParser);
				Element xmlRoot = xmlDoc.getRootElement();
				if(xmlRoot != null)
				{
					result = xmlRoot.getText(0);			
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (XmlPullParserException e)
			{
				e.printStackTrace();
			}

			try
			{
				isr.close();
			}
			catch (IOException e) {}
		}

		return result;
	}

	public static ArrayList<LoginStep1> getLoginStep1List(String response)
	{
		ArrayList<LoginStep1> result = new ArrayList<LoginStep1>();
		if (response != null && response.equals("") == false)
		{
			KXmlParser xmlParser = new KXmlParser();
			Document xmlDoc = new Document();

			ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
			InputStreamReader isr = new InputStreamReader( bin );

			try
			{
				xmlParser.setInput(isr);
				xmlDoc.parse(xmlParser);
				Element xmlRoot = xmlDoc.getRootElement();
				if(xmlRoot != null)
				{
					Element[] xmlChild = XmlParser.getChildren(xmlRoot);
					for ( int index = 0; index < xmlChild.length; ++index )
					{
						LoginStep1 item = new LoginStep1();
						Element[] contentNodes = XmlParser.getChildren(xmlChild[index]);
						for ( int i = 0; i < contentNodes.length; ++i )
						{
							if (contentNodes[i].getName().equals("FullName") && contentNodes[i].getChildCount() > 0)
							{
								item.fullName = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("CompanyName") && contentNodes[i].getChildCount() > 0)
							{
								item.companyName = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("TypeOfMemeber") && contentNodes[i].getChildCount() > 0)
							{
								item.typeOfMember = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("IsValid") && contentNodes[i].getChildCount() > 0)
							{
								Log.d("LoginStep1", "IsValid = " + contentNodes[i].getText(0));
								// item.isValid = Boolean.getBoolean(contentNodes[i].getText(0));
								item.isValid = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
							}
						}
						result.add(item);
					}
				}
			}
			catch (IOException e)
			{
				Log.e(TAG, e.getMessage());
				// Layout.showError(HotActivity.this, e.getMessage());
			}
			catch (XmlPullParserException e)
			{
				Log.e(TAG, e.getMessage());
				// Layout.showError(HotActivity.this, e.getMessage());
			}

			try
			{
				isr.close();
			}
			catch (IOException e) {}

		}
		return result;
	}

	public static ArrayList<LoginStep2> getLoginStep2List(String response)
	{
		ArrayList<LoginStep2> result = new ArrayList<LoginStep2>();
		if (response != null && response.equals("") == false)
		{
			KXmlParser xmlParser = new KXmlParser();
			Document xmlDoc = new Document();

			ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
			InputStreamReader isr = new InputStreamReader( bin );

			try
			{
				xmlParser.setInput(isr);
				xmlDoc.parse(xmlParser);
				Element xmlRoot = xmlDoc.getRootElement();
				if(xmlRoot != null)
				{
					Element[] xmlChild = XmlParser.getChildren(xmlRoot);
					for ( int index = 0; index < xmlChild.length; ++index )
					{
						LoginStep2 item = new LoginStep2();
						Element[] contentNodes = XmlParser.getChildren(xmlChild[index]);
						for ( int i = 0; i < contentNodes.length; ++i )
						{
							if (contentNodes[i].getName().equals("RegistrationPlate") && contentNodes[i].getChildCount() > 0)
							{
								item.registrationPlate = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("IsValid") && contentNodes[i].getChildCount() > 0)
							{
								// item.isValid = Boolean.getBoolean(contentNodes[i].getText(0));
								item.isValid = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
							}
						}
						result.add(item);
					}
				}
			}
			catch (IOException e)
			{
				Log.e(TAG, e.getMessage());
				// Layout.showError(HotActivity.this, e.getMessage());
			}
			catch (XmlPullParserException e)
			{
				Log.e(TAG, e.getMessage());
				// Layout.showError(HotActivity.this, e.getMessage());
			}

			try
			{
				isr.close();
			}
			catch (IOException e) {}

		}
		return result;
	}

	public static int getMembershipId(String response)
	{
		int result = -1;
		if (response != null && response.equals("") == false)
		{
			KXmlParser xmlParser = new KXmlParser();
			Document xmlDoc = new Document();

			ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
			InputStreamReader isr = new InputStreamReader( bin );

			try
			{
				xmlParser.setInput(isr);
				xmlDoc.parse(xmlParser);
				Element xmlRoot = xmlDoc.getRootElement();
				if(xmlRoot != null)
				{
					result = Integer.parseInt(xmlRoot.getText(0));
				}
			}
			catch (IOException e)
			{
				Log.e(TAG, e.getMessage());
				// Layout.showError(HotActivity.this, e.getMessage());
			}
			catch (XmlPullParserException e)
			{
				Log.e(TAG, e.getMessage());
				// Layout.showError(HotActivity.this, e.getMessage());
			}

			try
			{
				isr.close();
			}
			catch (IOException e) {}

		}
		return result;
	}

	public static ArrayList<AmssStation> getStationList(String response)
	{
		ArrayList<AmssStation> result = new ArrayList<AmssStation>();
		if (response != null && response.equals("") == false)
		{
			KXmlParser xmlParser = new KXmlParser();
			Document xmlDoc = new Document();

			ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
			InputStreamReader isr = new InputStreamReader( bin );

			try
			{
				xmlParser.setInput(isr);
				xmlDoc.parse(xmlParser);
				Element xmlRoot = xmlDoc.getRootElement();
                if(xmlRoot != null)
                {
                    Element[] xmlChild = XmlParser.getChildren(xmlRoot);
                    for ( int index = 0; index < xmlChild.length; ++index )
                    {
                        AmssStation item = new AmssStation();
                        Element[] contentNodes = XmlParser.getChildren(xmlChild[index]);
                        for ( int i = 0; i < contentNodes.length; ++i )
                        {
                            if (contentNodes[i].getName().equals("ID") && contentNodes[i].getChildCount() > 0)
                            {
                                item.id = Integer.parseInt(contentNodes[i].getText(0));
                            }
                            else if (contentNodes[i].getName().equals("Name") && contentNodes[i].getChildCount() > 0)
                            {
                                item.name = contentNodes[i].getText(0);
                            }
                            else if (contentNodes[i].getName().equals("ShortName") && contentNodes[i].getChildCount() > 0)
                            {
                                item.shortName = contentNodes[i].getText(0);
                            }
                            else if (contentNodes[i].getName().equals("Latitude") && contentNodes[i].getChildCount() > 0)
                            {
                                item.latitude = Double.parseDouble(contentNodes[i].getText(0));
                            }
                            else if (contentNodes[i].getName().equals("Longitude") && contentNodes[i].getChildCount() > 0)
                            {
                                item.longitude = Double.parseDouble(contentNodes[i].getText(0));
                            }
                            else if (contentNodes[i].getName().equals("City") && contentNodes[i].getChildCount() > 0)
                            {
                                item.city = contentNodes[i].getText(0);
                            }
                            else if (contentNodes[i].getName().equals("Address") && contentNodes[i].getChildCount() > 0)
                            {
                                item.address = contentNodes[i].getText(0);
                            }
                            else if (contentNodes[i].getName().equals("WorkingTime") && contentNodes[i].getChildCount() > 0)
                            {
                                item.workingTime = contentNodes[i].getText(0);
                            }
                            else if (contentNodes[i].getName().equals("Phone") && contentNodes[i].getChildCount() > 0)
                            {
                                item.phone = contentNodes[i].getText(0);
                            }
                            else if (contentNodes[i].getName().equals("IsForInternationalDocuments") && contentNodes[i].getChildCount() > 0)
                            {
                                item.isForInternationalDocuments = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
                            }
                            else if (contentNodes[i].getName().equals("IsForTechnicalReview") && contentNodes[i].getChildCount() > 0)
							{
								item.isForTechnicalReview = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
							}
							else if (contentNodes[i].getName().equals("IsForAMSSMembership") && contentNodes[i].getChildCount() > 0)
							{
								item.isForAmssMembership = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
							}
							else if (contentNodes[i].getName().equals("HasDrivingSchool") && contentNodes[i].getChildCount() > 0)
							{
								item.hasDrivingSchool = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
							}
							else if (contentNodes[i].getName().equals("HasCarWash") && contentNodes[i].getChildCount() > 0)
							{
								item.hasCarWash = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
							}
							else if (contentNodes[i].getName().equals("HasCarService") && contentNodes[i].getChildCount() > 0)
							{
								item.hasCarService = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
							}
							else if (contentNodes[i].getName().equals("HasRestaurant") && contentNodes[i].getChildCount() > 0)
							{
								item.hasRestaurant = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
							}
							else if (contentNodes[i].getName().equals("IsForRegistration") && contentNodes[i].getChildCount() > 0)
							{
								item.isForRegistration = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
							}
							else if (contentNodes[i].getName().equals("IsForVehicleInspectionAttest") && contentNodes[i].getChildCount() > 0)
							{
								item.isForVehicleInspectionAttest = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
							}
							else if (contentNodes[i].getName().equals("IsForHelpOnRoad") && contentNodes[i].getChildCount() > 0)
							{
								item.isForHelpOnRoad = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
							}
							else if (contentNodes[i].getName().equals("IsForSpedition") && contentNodes[i].getChildCount() > 0)
							{
								item.isForSpedition = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
							}
							else if (contentNodes[i].getName().equals("IsForTourism") && contentNodes[i].getChildCount() > 0)
							{
								item.isForTourism = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
							}
							else if (contentNodes[i].getName().equals("HasCurrencyExchange") && contentNodes[i].getChildCount() > 0)
							{
								item.hasCurrencyExchange = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
							}
							else if (contentNodes[i].getName().equals("IsForInsurance") && contentNodes[i].getChildCount() > 0)
							{
								item.isForInsurance = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
							}
							else if (contentNodes[i].getName().equals("IsForTowing") && contentNodes[i].getChildCount() > 0)
							{
								item.isForTowing = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
							}
							else if (contentNodes[i].getName().equals("HasTNGPump") && contentNodes[i].getChildCount() > 0)
							{
								item.hasTNGPump = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
							}
							else if (contentNodes[i].getName().equals("HasShop") && contentNodes[i].getChildCount() > 0)
							{
								item.hasShop = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
							}
							else if (contentNodes[i].getName().equals("HasSalesAndComplementOfTAGToll") && contentNodes[i].getChildCount() > 0)
							{
								item.hasSalesAndComplementOfTAGToll = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
							}
						}
						result.add(item);
					}
				}
			}
			catch (IOException e)
			{
				Log.e(TAG, e.getMessage());
				// Layout.showError(HotActivity.this, e.getMessage());
			}
			catch (XmlPullParserException e)
			{
				Log.e(TAG, e.getMessage());
				// Layout.showError(HotActivity.this, e.getMessage());
			}

			try
			{
				isr.close();
			}
			catch (IOException e) {}

		}
		return result;
	}

	public static ArrayList<AmsoStation> getAmsoStationList(String response)
	{
		ArrayList<AmsoStation> result = new ArrayList<AmsoStation>();
		if (response != null && response.equals("") == false)
		{
			KXmlParser xmlParser = new KXmlParser();
			Document xmlDoc = new Document();

			ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
			InputStreamReader isr = new InputStreamReader( bin );

			try
			{
				xmlParser.setInput(isr);
				xmlDoc.parse(xmlParser);
				Element xmlRoot = xmlDoc.getRootElement();
				if(xmlRoot != null)
				{
					Element[] xmlChild = XmlParser.getChildren(xmlRoot);
					for ( int index = 0; index < xmlChild.length; ++index )
					{
						AmsoStation item = new AmsoStation();
						Element[] contentNodes = XmlParser.getChildren(xmlChild[index]);
						for ( int i = 0; i < contentNodes.length; ++i )
						{
							if (contentNodes[i].getName().equals("ID") && contentNodes[i].getChildCount() > 0)
							{
								item.id = Integer.parseInt(contentNodes[i].getText(0));
							}
							else if (contentNodes[i].getName().equals("Name") && contentNodes[i].getChildCount() > 0)
							{
								item.name = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("RegionName") && contentNodes[i].getChildCount() > 0)
							{
								item.regionName = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("RegionID") && contentNodes[i].getChildCount() > 0)
							{
								item.regionId = Integer.parseInt(contentNodes[i].getText(0));
							}
							else if (contentNodes[i].getName().equals("MunicipalityName") && contentNodes[i].getChildCount() > 0)
							{
								item.municipalityName = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("MunicipalityID") && contentNodes[i].getChildCount() > 0)
							{
								item.municipalityId = Integer.parseInt(contentNodes[i].getText(0));
							}
							else if (contentNodes[i].getName().equals("TownName") && contentNodes[i].getChildCount() > 0)
							{
								item.townName = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("TownID") && contentNodes[i].getChildCount() > 0)
							{
								item.townId = Integer.parseInt(contentNodes[i].getText(0));
							}
							else if (contentNodes[i].getName().equals("Street") && contentNodes[i].getChildCount() > 0)
							{
								item.street = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("HouseNumber") && contentNodes[i].getChildCount() > 0)
							{
								item.houseNumber = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("Phone") && contentNodes[i].getChildCount() > 0)
							{
								item.phone = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("WorkingTime") && contentNodes[i].getChildCount() > 0)
							{
								item.workingTime = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("AutomobileLiability") && contentNodes[i].getChildCount() > 0)
							{
								item.hasAutomobileLiability = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
							}
							else if (contentNodes[i].getName().equals("GreenCard") && contentNodes[i].getChildCount() > 0)
							{
								item.hasGreenCard = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
							}
							else if (contentNodes[i].getName().equals("CarAccident") && contentNodes[i].getChildCount() > 0)
							{
								item.hasCarAccident = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
							}
							else if (contentNodes[i].getName().equals("Kasko") && contentNodes[i].getChildCount() > 0)
							{
								item.hasKasko = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
							}
							else if (contentNodes[i].getName().equals("KaskoLight") && contentNodes[i].getChildCount() > 0)
							{
								item.hasKaskoLight = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
							}
							else if (contentNodes[i].getName().equals("VoluntaryHealthInsurance") && contentNodes[i].getChildCount() > 0)
							{
								item.hasVoluntaryHealthInsurance = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
							}
							else if (contentNodes[i].getName().equals("Membership") && contentNodes[i].getChildCount() > 0)
							{
								item.hasMembership = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
							}
							else if (contentNodes[i].getName().equals("InternationalDocuments") && contentNodes[i].getChildCount() > 0)
							{
								item.hasInternationalDocuments = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;
							}
							else if (contentNodes[i].getName().equals("AdditionalServices") && contentNodes[i].getChildCount() > 0)
							{
								item.additionalServices = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("Latitude") && contentNodes[i].getChildCount() > 0)
							{
								item.latitude = Double.parseDouble(contentNodes[i].getText(0));
							}
							else if (contentNodes[i].getName().equals("Longitude") && contentNodes[i].getChildCount() > 0)
							{
								item.longitude = Double.parseDouble(contentNodes[i].getText(0));
							}
						}

						item.hasAdditionalServices = item.additionalServices != null && item.additionalServices.length() > 0;
						result.add(item);
					}
				}
			}
			catch (IOException e)
			{
				Log.e(TAG, e.getMessage());
				// Layout.showError(HotActivity.this, e.getMessage());
			}
			catch (XmlPullParserException e)
			{
				Log.e(TAG, e.getMessage());
				// Layout.showError(HotActivity.this, e.getMessage());
			}

			try
			{
				isr.close();
			}
			catch (IOException e) {}

		}
		return result;
	}

	public static ArrayList<DamageType> getDamageTypeList(String response)
	{
		ArrayList<DamageType> result = new ArrayList<DamageType>();
		if (response != null && response.equals("") == false)
		{
			KXmlParser xmlParser = new KXmlParser();
			Document xmlDoc = new Document();

			ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
			InputStreamReader isr = new InputStreamReader( bin );

			try
			{
				xmlParser.setInput(isr);
				xmlDoc.parse(xmlParser);
				Element xmlRoot = xmlDoc.getRootElement();
				if(xmlRoot != null)
				{
					Element[] xmlChild = XmlParser.getChildren(xmlRoot);
					for ( int index = 0; index < xmlChild.length; ++index )
					{
						DamageType item = new DamageType();
						Element[] contentNodes = XmlParser.getChildren(xmlChild[index]);
						for ( int i = 0; i < contentNodes.length; ++i )
						{
							if (contentNodes[i].getName().equals("ID") && contentNodes[i].getChildCount() > 0)
							{
								item.id = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("Name") && contentNodes[i].getChildCount() > 0)
							{
								item.name = contentNodes[i].getText(0);
							}
						}
						result.add(item);
					}
				}
			}
			catch (IOException e)
			{
				Log.e(TAG, e.getMessage());
			}
			catch (XmlPullParserException e)
			{
				Log.e(TAG, e.getMessage());
			}

			try
			{
				isr.close();
			}
			catch (IOException e) {}

		}
		return result;
	}

	public static ArrayList<RoadCondition> getRoadConditionList(String response)
	{
		ArrayList<RoadCondition> result = new ArrayList<RoadCondition>();
		if (response != null && response.equals("") == false)
		{
			KXmlParser xmlParser = new KXmlParser();
			Document xmlDoc = new Document();

			ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
			InputStreamReader isr = new InputStreamReader( bin );

			try
			{
				xmlParser.setInput(isr);
				xmlDoc.parse(xmlParser);
				Element xmlRoot = xmlDoc.getRootElement();
				if(xmlRoot != null)
				{
					Element[] xmlChild = XmlParser.getChildren(xmlRoot);
					for ( int index = 0; index < xmlChild.length; ++index )
					{
						RoadCondition item = new RoadCondition();
						Element[] contentNodes = XmlParser.getChildren(xmlChild[index]);
						for ( int i = 0; i < contentNodes.length; ++i )
						{
							if (contentNodes[i].getName().equals("ID") && contentNodes[i].getChildCount() > 0)
							{
								item.id = Integer.parseInt(contentNodes[i].getText(0));
							}
							else if (contentNodes[i].getName().equals("Name") && contentNodes[i].getChildCount() > 0)
							{
								item.name = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("Text") && contentNodes[i].getChildCount() > 0)
							{
								item.text = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("IconType") && contentNodes[i].getChildCount() > 0)
							{
								item.iconType = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("Latitude") && contentNodes[i].getChildCount() > 0)
							{
								item.latitude = Double.parseDouble(contentNodes[i].getText(0));
							}
							else if (contentNodes[i].getName().equals("Longitude") && contentNodes[i].getChildCount() > 0)
							{
								item.longitude = Double.parseDouble(contentNodes[i].getText(0));
							}
						}
						result.add(item);
					}
				}
			}
			catch (IOException e)
			{
				Log.e(TAG, e.getMessage());
			}
			catch (XmlPullParserException e)
			{
				Log.e(TAG, e.getMessage());
			}

			try
			{
				isr.close();
			}
			catch (IOException e) {}

		}
		return result;
	}

	public static RoadConditionVideo getRoadConditionVideo(String response)
	{

		RoadConditionVideo result = new RoadConditionVideo();
		if (response != null && response.equals("") == false)
		{
			KXmlParser xmlParser = new KXmlParser();
			Document xmlDoc = new Document();

			ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
			InputStreamReader isr = new InputStreamReader( bin );

			try
			{
				xmlParser.setInput(isr);
				xmlDoc.parse(xmlParser);
				Element xmlRoot = xmlDoc.getRootElement();
				if(xmlRoot != null)
				{
					Element[] contentNodes = XmlParser.getChildren(xmlRoot);
					for ( int i = 0; i < contentNodes.length; ++i )
					{
						if (contentNodes[i].getName().equals("ID") && contentNodes[i].getChildCount() > 0)
						{
							result.id = Integer.parseInt(contentNodes[i].getText(0));
						}
						else if (contentNodes[i].getName().equals("Title") && contentNodes[i].getChildCount() > 0)
						{
							result.title = contentNodes[i].getText(0);
						}
						else if (contentNodes[i].getName().equals("Alias") && contentNodes[i].getChildCount() > 0)
						{
							result.alias = contentNodes[i].getText(0);
						}
						else if (contentNodes[i].getName().equals("IntroText") && contentNodes[i].getChildCount() > 0)
						{
							result.introText = "";
							for (int index = 0; index < contentNodes[i].getChildCount(); index++ )
								result.introText = result.introText + contentNodes[i].getText(index);
						}
					}
				}
			}
			catch (IOException e)
			{
				Log.e(TAG, e.getMessage());
			}
			catch (XmlPullParserException e)
			{
				Log.e(TAG, e.getMessage());
			}

			try
			{
				isr.close();
			}
			catch (IOException e) {}

		}
		return result;
	}
	
	public static Member getMemberInfo(String response)
	{
		Member result = new Member();
		if (response != null && response.equals("") == false)
		{
			KXmlParser xmlParser = new KXmlParser();
			Document xmlDoc = new Document();

			ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
			InputStreamReader isr = new InputStreamReader( bin );

			try
			{
				xmlParser.setInput(isr);
				xmlDoc.parse(xmlParser);
				Element xmlRoot = xmlDoc.getRootElement();
				if(xmlRoot != null)
				{
					Element[] contentNodes = XmlParser.getChildren(xmlRoot);
					for ( int i = 0; i < contentNodes.length; ++i )
					{
						if (contentNodes[i].getName().equals("FirstName") && contentNodes[i].getChildCount() > 0)
							result.firstName = contentNodes[i].getText(0);
						else if (contentNodes[i].getName().equals("LastName") && contentNodes[i].getChildCount() > 0)
							result.lastName = contentNodes[i].getText(0);
						else if (contentNodes[i].getName().equals("PersonalNumber") && contentNodes[i].getChildCount() > 0)
							result.personalNumber = contentNodes[i].getText(0);
						else if (contentNodes[i].getName().equals("PIB") && contentNodes[i].getChildCount() > 0)
							result.pib = contentNodes[i].getText(0);
						else if (contentNodes[i].getName().equals("CompanyName") && contentNodes[i].getChildCount() > 0)
							result.companyName = contentNodes[i].getText(0);
						else if (contentNodes[i].getName().equals("ZipCode") && contentNodes[i].getChildCount() > 0)
							result.postCode = contentNodes[i].getText(0);
						else if (contentNodes[i].getName().equals("City") && contentNodes[i].getChildCount() > 0)
							result.city = contentNodes[i].getText(0);
						else if (contentNodes[i].getName().equals("Address") && contentNodes[i].getChildCount() > 0)
							result.address = contentNodes[i].getText(0);
						else if (contentNodes[i].getName().equals("Phone1") && contentNodes[i].getChildCount() > 0)
							result.telephone = contentNodes[i].getText(0);
						else if (contentNodes[i].getName().equals("Phone2") && contentNodes[i].getChildCount() > 0)
							result.mobilePhone = contentNodes[i].getText(0);
						else if (contentNodes[i].getName().equals("Email") && contentNodes[i].getChildCount() > 0)
							result.email = contentNodes[i].getText(0);
						else if (contentNodes[i].getName().equals("TypeOfPerson") && contentNodes[i].getChildCount() > 0)
							result.isIndividual = "fizicko".equalsIgnoreCase(contentNodes[i].getText(0));
					}
				}
			}
			catch (IOException e)
			{
				Log.e(TAG, e.getMessage());
			}
			catch (XmlPullParserException e)
			{
				Log.e(TAG, e.getMessage());
			}

			try
			{
				isr.close();
			}
			catch (IOException e) {}

		}
		return result;
	}
	
	public static ArrayList<Membership> getMembershipInfo(String response) {
		ArrayList<Membership> result = new ArrayList<Membership>();
		if (response != null && response.equals("") == false) {
			KXmlParser xmlParser = new KXmlParser();
			Document xmlDoc = new Document();

			ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
			InputStreamReader isr = new InputStreamReader( bin );

			try {
				xmlParser.setInput(isr);
				xmlDoc.parse(xmlParser);
				Element xmlRoot = xmlDoc.getRootElement();
				if(xmlRoot != null) {
					Element[] xmlChild = XmlParser.getChildren(xmlRoot);
					for ( int index = 0; index < xmlChild.length; ++index ) {
						Membership item = new Membership();
						Element[] contentNodes = XmlParser.getChildren(xmlChild[index]);
						for ( int i = 0; i < contentNodes.length; ++i ) {
							if (contentNodes[i].getName().equals("ID") && contentNodes[i].getChildCount() > 0) {
								item.id = Integer.parseInt(contentNodes[i].getText(0));
							} else if (contentNodes[i].getName().equals("MembershipID") && contentNodes[i].getChildCount() > 0) {
								item.membershipId = Integer.parseInt(contentNodes[i].getText(0));
							} else if (contentNodes[i].getName().equals("CardNumber") && contentNodes[i].getChildCount() > 0) {
								item.cardNumber = contentNodes[i].getText(0);
							} else if (contentNodes[i].getName().equals("SocietyID") && contentNodes[i].getChildCount() > 0) {
								item.societyId = contentNodes[i].getText(0);
							} else if (contentNodes[i].getName().equals("SocietyName") && contentNodes[i].getChildCount() > 0) {
								item.societyName = contentNodes[i].getText(0);
							} else if (contentNodes[i].getName().equals("TypeOfMembershipID") && contentNodes[i].getChildCount() > 0) {
								item.typeOfMembershipId = contentNodes[i].getText(0);
							} else if (contentNodes[i].getName().equals("TypeOfMembershipName") && contentNodes[i].getChildCount() > 0) {
								item.typeOfMembershipName = contentNodes[i].getText(0);
							} else if (contentNodes[i].getName().equals("DateOfMembership") && contentNodes[i].getChildCount() > 0) {
								item.dateOfMembership = contentNodes[i].getText(0);
							} else if (contentNodes[i].getName().equals("ValidFrom") && contentNodes[i].getChildCount() > 0) {
								item.validFrom = contentNodes[i].getText(0);
							} else if (contentNodes[i].getName().equals("ValidTo") && contentNodes[i].getChildCount() > 0) {
								item.validTo = contentNodes[i].getText(0);
							} else if (contentNodes[i].getName().equals("Status") && contentNodes[i].getChildCount() > 0) {
								item.status = contentNodes[i].getText(0);
							}
						}
						result.add(item);
					}
				}
			}
			catch (IOException e)
			{
				Log.e(TAG, e.getMessage());
			}
			catch (XmlPullParserException e)
			{
				Log.e(TAG, e.getMessage());
			}

			try
			{
				isr.close();
			}
			catch (IOException e) {}

		}
		return result;
	}
	
	public static ArrayList<History> getHistory(String response) {
		ArrayList<History> result = new ArrayList<History>();
		
		if (response != null && response.equals("") == false) {
			KXmlParser xmlParser = new KXmlParser();
			Document xmlDoc = new Document();

			ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
			InputStreamReader isr = new InputStreamReader( bin );

			try {
				xmlParser.setInput(isr);
				xmlDoc.parse(xmlParser);
				Element xmlRoot = xmlDoc.getRootElement();
				if(xmlRoot != null) {
					Element[] xmlChild = XmlParser.getChildren(xmlRoot);
					for (int index = 0; index < xmlChild.length; ++index ) {
						Element[] contentNodes = XmlParser.getChildren(xmlChild[index]);
						if (contentNodes.length > 0) {
							contentNodes = XmlParser.getChildren(contentNodes[0]);
							for ( int i = 0; i < contentNodes.length; ++i ) {
								if ("historyTxt".equalsIgnoreCase(contentNodes[i].getName())) {
									String json = contentNodes[i].getText(0);
									JSONArray arr = new JSONArray(json);
									
									for (int j = 0; j < arr.length(); j++) {
										History item = new History();
										JSONObject o = arr.getJSONObject(j);
										item.pogodnost_id = o.getString("pogodnost_id");
										item.lokacija = o.getString("lokacija");
										item.vrsta_intervencije = o.getString("vrsta_intervencije");
										item.datum_usluge = o.getString("datum_usluge");
										result.add(item);
									}
								}
							}
						}
					}
				}
			} catch (IOException e) {
				Log.e(TAG, e.getMessage());
			} catch (XmlPullParserException e) {
				Log.e(TAG, e.getMessage());
			} catch (JSONException e) {
				Log.e(TAG, e.getMessage());
			}

			try {
				isr.close();
			} catch (IOException e) {}

		}
		return result;
	}
	
	public static Map<String, Integer> getServicesInfo(String response)
	{
		Map<String, Integer> result = new HashMap<String, Integer>();
		
		if (response != null && response.equals("") == false)
		{
			KXmlParser xmlParser = new KXmlParser();
			Document xmlDoc = new Document();

			ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
			InputStreamReader isr = new InputStreamReader( bin );

			try
			{
				xmlParser.setInput(isr);
				xmlDoc.parse(xmlParser);
				Element xmlRoot = xmlDoc.getRootElement();
				if(xmlRoot != null) {
					Element[] xmlChild = XmlParser.getChildren(xmlRoot);
					for ( int index = 0; index < xmlChild.length; ++index ) {
						RoadCondition item = new RoadCondition();
						Element[] contentNodes = XmlParser.getChildren(xmlChild[index]);
						if (contentNodes.length > 0) {
							contentNodes = XmlParser.getChildren(contentNodes[0]);
							for ( int i = 0; i < contentNodes.length; ++i ) {
								String name = contentNodes[i].getName();
								if ("O1".equalsIgnoreCase(name) || "O2".equalsIgnoreCase(name) || "O3".equalsIgnoreCase(name) || "O4".equalsIgnoreCase(name) || "O5".equalsIgnoreCase(name) || "O6".equalsIgnoreCase(name)) {
									String o = null;
									try {
										o = contentNodes[i].getText(0);
									} catch (Exception e) {
										o = "-1";
									}
									result.put(name, Integer.parseInt(o));
								} else if ("S1".equalsIgnoreCase(name) || "S2".equalsIgnoreCase(name) || "S3".equalsIgnoreCase(name) || "S4".equalsIgnoreCase(name) || "S5".equalsIgnoreCase(name) || "S6".equalsIgnoreCase(name) || "S7".equalsIgnoreCase(name) || "S8".equalsIgnoreCase(name) || "S9".equalsIgnoreCase(name) || "S10".equalsIgnoreCase(name) || "S11".equalsIgnoreCase(name)) {
									String s = null;
									try {
										s = contentNodes[i].getText(0);
									} catch (Exception e) {
										s = "-1";
									}
									result.put(name,Integer.parseInt(s));
								}
							}
						}

					}
				}
			}
			catch (IOException e)
			{
				Log.e(TAG, e.getMessage());
			}
			catch (XmlPullParserException e)
			{
				Log.e(TAG, e.getMessage());
			}

			try
			{
				isr.close();
			}
			catch (IOException e) {}

		}
		return result;
	}

	public static ArrayList<TollCity> getTollCityList(String response)
	{
		ArrayList<TollCity> result = new ArrayList<TollCity>();
		if (response != null && response.equals("") == false)
		{
			KXmlParser xmlParser = new KXmlParser();
			Document xmlDoc = new Document();

			ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
			InputStreamReader isr = new InputStreamReader( bin );

			try
			{
				xmlParser.setInput(isr);
				xmlDoc.parse(xmlParser);
				Element xmlRoot = xmlDoc.getRootElement();
				if(xmlRoot != null)
				{
					Element[] xmlChild = XmlParser.getChildren(xmlRoot);
					for ( int index = 0; index < xmlChild.length; ++index )
					{
						TollCity item = new TollCity();
						Element[] contentNodes = XmlParser.getChildren(xmlChild[index]);
						for ( int i = 0; i < contentNodes.length; ++i )
						{
							if (contentNodes[i].getName().equals("ID") && contentNodes[i].getChildCount() > 0)
							{
								item.id = Integer.parseInt(contentNodes[i].getText(0));
							}
							else if (contentNodes[i].getName().equals("Name") && contentNodes[i].getChildCount() > 0)
							{
								item.name = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("Group") && contentNodes[i].getChildCount() > 0)
							{
								item.group = Integer.parseInt(contentNodes[i].getText(0));
							}
						}
						result.add(item);
					}
				}
			}
			catch (IOException e)
			{
				Log.e(TAG, e.getMessage());
			}
			catch (XmlPullParserException e)
			{
				Log.e(TAG, e.getMessage());
			}

			try
			{
				isr.close();
			}
			catch (IOException e) {}

		}
		return result;
	}

	public static Toll getToll(String response)
	{
		Toll result = new Toll();
		if (response != null && response.equals("") == false)
		{
			KXmlParser xmlParser = new KXmlParser();
			Document xmlDoc = new Document();

			ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
			InputStreamReader isr = new InputStreamReader( bin );

			try
			{
				xmlParser.setInput(isr);
				xmlDoc.parse(xmlParser);
				Element xmlRoot = xmlDoc.getRootElement();
				if(xmlRoot != null)
				{
					Element[] contentNodes = XmlParser.getChildren(xmlRoot);
					for ( int i = 0; i < contentNodes.length; ++i )
					{
						if (contentNodes[i].getName().equals("FromID") && contentNodes[i].getChildCount() > 0)
						{
							result.fromId = Integer.parseInt(contentNodes[i].getText(0));
						}
						else if (contentNodes[i].getName().equals("FromCityName") && contentNodes[i].getChildCount() > 0)
						{
							result.fromCityName = contentNodes[i].getText(0);
						}
						if (contentNodes[i].getName().equals("ToID") && contentNodes[i].getChildCount() > 0)
						{
							result.toId = Integer.parseInt(contentNodes[i].getText(0));
						}
						else if (contentNodes[i].getName().equals("ToCityName") && contentNodes[i].getChildCount() > 0)
						{
							result.toCityName = contentNodes[i].getText(0);
						}
						else if (contentNodes[i].getName().equals("CategoryIPriceRSD") && contentNodes[i].getChildCount() > 0)
						{
							result.categoryIPriceRSD = Double.parseDouble(contentNodes[i].getText(0));
						}
						else if (contentNodes[i].getName().equals("CategoryIIPriceRSD") && contentNodes[i].getChildCount() > 0)
						{
							result.categoryIIPriceRSD = Double.parseDouble(contentNodes[i].getText(0));
						}
						else if (contentNodes[i].getName().equals("CategoryIIIPriceRSD") && contentNodes[i].getChildCount() > 0)
						{
							result.categoryIIIPriceRSD = Double.parseDouble(contentNodes[i].getText(0));
						}
						else if (contentNodes[i].getName().equals("CategoryIVPriceRSD") && contentNodes[i].getChildCount() > 0)
						{
							result.categoryIVPriceRSD = Double.parseDouble(contentNodes[i].getText(0));
						}
						else if (contentNodes[i].getName().equals("CategoryIPriceEUR") && contentNodes[i].getChildCount() > 0)
						{
							result.categoryIPriceEUR = Double.parseDouble(contentNodes[i].getText(0));
						}
						else if (contentNodes[i].getName().equals("CategoryIIPriceEUR") && contentNodes[i].getChildCount() > 0)
						{
							result.categoryIIPriceEUR = Double.parseDouble(contentNodes[i].getText(0));
						}
						else if (contentNodes[i].getName().equals("CategoryIIIPriceEUR") && contentNodes[i].getChildCount() > 0)
						{
							result.categoryIIIPriceEUR = Double.parseDouble(contentNodes[i].getText(0));
						}
						else if (contentNodes[i].getName().equals("CategoryIVPriceEUR") && contentNodes[i].getChildCount() > 0)
						{
							result.categoryIVPriceEUR = Double.parseDouble(contentNodes[i].getText(0));
						}
					}
				}
			}
			catch (IOException e)
			{
				Log.e(TAG, e.getMessage());
			}
			catch (XmlPullParserException e)
			{
				Log.e(TAG, e.getMessage());
			}

			try
			{
				isr.close();
			}
			catch (IOException e) {}

		}
		return result;
	}

	public static CityDistance getDistance(String response)
	{
		CityDistance result = new CityDistance();
		if (response != null && response.equals("") == false)
		{
			KXmlParser xmlParser = new KXmlParser();
			Document xmlDoc = new Document();

			ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
			InputStreamReader isr = new InputStreamReader( bin );

			try
			{
				xmlParser.setInput(isr);
				xmlDoc.parse(xmlParser);
				Element xmlRoot = xmlDoc.getRootElement();
				if(xmlRoot != null)
				{
					Element[] contentNodes = XmlParser.getChildren(xmlRoot);
					for ( int i = 0; i < contentNodes.length; ++i )
					{
						if (contentNodes[i].getName().equals("ID") && contentNodes[i].getChildCount() > 0)
						{
							result.id = Integer.parseInt(contentNodes[i].getText(0));
						}
						else if (contentNodes[i].getName().equals("DestinationName") && contentNodes[i].getChildCount() > 0)
						{
							result.destinationName = contentNodes[i].getText(0);
						}
						if (contentNodes[i].getName().equals("Km") && contentNodes[i].getChildCount() > 0)
						{
							result.km = Double.parseDouble(contentNodes[i].getText(0));
						}
						else if (contentNodes[i].getName().equals("Route") && contentNodes[i].getChildCount() > 0)
						{
							result.route = contentNodes[i].getText(0);
						}
					}
				}
			}
			catch (IOException e)
			{
				Log.e(TAG, e.getMessage());
			}
			catch (XmlPullParserException e)
			{
				Log.e(TAG, e.getMessage());
			}

			try
			{
				isr.close();
			}
			catch (IOException e) {}

		}
		return result;
	}

	public static ArrayList<Benefit> getBenefitList(String response)
	{
		ArrayList<Benefit> result = new ArrayList<Benefit>();
		if (response != null && response.equals("") == false)
		{
			KXmlParser xmlParser = new KXmlParser();
			Document xmlDoc = new Document();

			ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
			InputStreamReader isr = new InputStreamReader( bin );

			try
			{
				xmlParser.setInput(isr);
				xmlDoc.parse(xmlParser);
				Element xmlRoot = xmlDoc.getRootElement();
				if(xmlRoot != null)
				{
					Element[] xmlChild = XmlParser.getChildren(xmlRoot);
					for ( int index = 0; index < xmlChild.length; ++index )
					{
						Benefit item = new Benefit();
						Element[] contentNodes = XmlParser.getChildren(xmlChild[index]);
						for ( int i = 0; i < contentNodes.length; ++i )
						{
							if (contentNodes[i].getName().equals("ID") && contentNodes[i].getChildCount() > 0)
							{
								item.id = Integer.parseInt(contentNodes[i].getText(0));
							}
							else if (contentNodes[i].getName().equals("Mark") && contentNodes[i].getChildCount() > 0)
							{
								item.mark = contentNodes[i].getText(0);
							}
							if (contentNodes[i].getName().equals("TypeOfMembership") && contentNodes[i].getChildCount() > 0)
							{
								item.type = Integer.parseInt(contentNodes[i].getText(0));
							}
							else if (contentNodes[i].getName().equals("Name") && contentNodes[i].getChildCount() > 0)
							{
								item.name = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("Info") && contentNodes[i].getChildCount() > 0)
							{
								item.info = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("NumberOfUsage") && contentNodes[i].getChildCount() > 0)
							{
								item.numberOfUsage = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("Usage") && contentNodes[i].getChildCount() > 0)
							{
								item.usage = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("IsValidInSerbia") && contentNodes[i].getChildCount() > 0)
							{
								item.isValidInSerbia = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;;
							}
							else if (contentNodes[i].getName().equals("IsValidAbroad") && contentNodes[i].getChildCount() > 0)
							{
								item.isValidAbroad = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;;
							}
							else if (contentNodes[i].getName().equals("IsValid") && contentNodes[i].getChildCount() > 0)
							{
								item.isValid = contentNodes[i].getText(0).toLowerCase().equals("true") ? true : false;;
							}
						}
						result.add(item);
					}
				}
			}
			catch (IOException e)
			{
				Log.e(TAG, e.getMessage());
			}
			catch (XmlPullParserException e)
			{
				Log.e(TAG, e.getMessage());
			}

			try
			{
				isr.close();
			}
			catch (IOException e) {}

		}
		return result;
	}

	public static ArrayList<Municipality> getMunicipalityList(String response)
	{
		ArrayList<Municipality> result = new ArrayList<Municipality>();
		if (response != null && response.equals("") == false)
		{
			KXmlParser xmlParser = new KXmlParser();
			Document xmlDoc = new Document();

			ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
			InputStreamReader isr = new InputStreamReader( bin );

			try
			{
				xmlParser.setInput(isr);
				xmlDoc.parse(xmlParser);
				Element xmlRoot = xmlDoc.getRootElement();
				if(xmlRoot != null)
				{
					Element[] xmlChild = XmlParser.getChildren(xmlRoot);
					for ( int index = 0; index < xmlChild.length; ++index )
					{
						Municipality item = new Municipality();
						Element[] contentNodes = XmlParser.getChildren(xmlChild[index]);
						for ( int i = 0; i < contentNodes.length; ++i )
						{
							if (contentNodes[i].getName().equals("ID") && contentNodes[i].getChildCount() > 0)
							{
								item.id = Integer.parseInt(contentNodes[i].getText(0));
							}
							else if (contentNodes[i].getName().equals("Name") && contentNodes[i].getChildCount() > 0)
							{
								item.name = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("DistrictID") && contentNodes[i].getChildCount() > 0)
							{
								item.districtId = Integer.parseInt(contentNodes[i].getText(0));
							}
							else if (contentNodes[i].getName().equals("IDNumber") && contentNodes[i].getChildCount() > 0)
							{
								item.number = Integer.parseInt(contentNodes[i].getText(0));
							}
						}
						result.add(item);
					}
				}
			}
			catch (IOException e)
			{
				Log.e(TAG, e.getMessage());
			}
			catch (XmlPullParserException e)
			{
				Log.e(TAG, e.getMessage());
			}

			try
			{
				isr.close();
			}
			catch (IOException e) {}

		}
		return result;
	}

	public static ArrayList<VehicleType> getVehicleTypeList(String response)
	{
		ArrayList<VehicleType> result = new ArrayList<VehicleType>();
		if (response != null && response.equals("") == false)
		{
			KXmlParser xmlParser = new KXmlParser();
			Document xmlDoc = new Document();

			ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
			InputStreamReader isr = new InputStreamReader( bin );

			try
			{
				xmlParser.setInput(isr);
				xmlDoc.parse(xmlParser);
				Element xmlRoot = xmlDoc.getRootElement();
				if(xmlRoot != null)
				{
					Element[] xmlChild = XmlParser.getChildren(xmlRoot);
					for ( int index = 0; index < xmlChild.length; ++index )
					{
						VehicleType item = new VehicleType();
						Element[] contentNodes = XmlParser.getChildren(xmlChild[index]);
						for ( int i = 0; i < contentNodes.length; ++i )
						{
							if (contentNodes[i].getName().equals("ID") && contentNodes[i].getChildCount() > 0)
							{
								item.id = Integer.parseInt(contentNodes[i].getText(0));
							}
							else if (contentNodes[i].getName().equals("Name") && contentNodes[i].getChildCount() > 0)
							{
								item.name = contentNodes[i].getText(0);
							}
						}
						result.add(item);
					}
				}
			}
			catch (IOException e)
			{
				Log.e(TAG, e.getMessage());
			}
			catch (XmlPullParserException e)
			{
				Log.e(TAG, e.getMessage());
			}

			try
			{
				isr.close();
			}
			catch (IOException e) {}

		}
		return result;
	}

	public static ArrayList<PaymentInvoice> getPaymentInvoiceList(String response)
	{
		ArrayList<PaymentInvoice> result = new ArrayList<PaymentInvoice>();
		if (response != null && response.equals("") == false)
		{
			KXmlParser xmlParser = new KXmlParser();
			Document xmlDoc = new Document();

			ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
			InputStreamReader isr = new InputStreamReader( bin );

			try
			{
				xmlParser.setInput(isr);
				xmlDoc.parse(xmlParser);
				Element xmlRoot = xmlDoc.getRootElement();
				if(xmlRoot != null)
				{
					Element[] xmlChild = XmlParser.getChildren(xmlRoot);
					for ( int index = 0; index < xmlChild.length; ++index )
					{
						PaymentInvoice item = new PaymentInvoice();
						Element[] contentNodes = XmlParser.getChildren(xmlChild[index]);
						for ( int i = 0; i < contentNodes.length; ++i )
						{
							if (contentNodes[i].getName().equals("Payee") && contentNodes[i].getChildCount() > 0)
							{
								item.payee = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("PurposeOfPayment") && contentNodes[i].getChildCount() > 0)
							{
								item.purpose = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("Amount") && contentNodes[i].getChildCount() > 0)
							{
								item.amount = Double.parseDouble(contentNodes[i].getText(0));
							}
						}
						result.add(item);
					}
				}
			}
			catch (IOException e)
			{
				Log.e(TAG, e.getMessage());
			}
			catch (XmlPullParserException e)
			{
				Log.e(TAG, e.getMessage());
			}

			try
			{
				isr.close();
			}
			catch (IOException e) {}

		}
		return result;
	}

	public static MonasteryDistance getMonasteryDistance(String response)
	{
		MonasteryDistance item = new MonasteryDistance();
		if (response != null && response.equals("") == false)
		{
			KXmlParser xmlParser = new KXmlParser();
			Document xmlDoc = new Document();

			ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
			InputStreamReader isr = new InputStreamReader( bin );

			try
			{
				xmlParser.setInput(isr);
				xmlDoc.parse(xmlParser);
				Element xmlRoot = xmlDoc.getRootElement();
				if(xmlRoot != null)
				{
					Element[] contentNodes = XmlParser.getChildren(xmlRoot);
					for ( int i = 0; i < contentNodes.length; ++i )
					{
						if (contentNodes[i].getName().equals("Monastery"))
						{
							Element[] contentNodesChildren = XmlParser.getChildren(contentNodes[i]);
							Monastery monaster = new Monastery();
							for(int j=0; j<contentNodesChildren.length; ++j){
								if(contentNodesChildren[j].getName().equals("ID")){
									monaster.ID = Integer.parseInt(contentNodesChildren[j].getText(0));
								}
								else if(contentNodesChildren[j].getName().equals("Name")){
									monaster.name = contentNodesChildren[j].getText(0);
								}
								item.monastery = monaster;
							}
						}
						else if (contentNodes[i].getName().equals("EpiscopacyInfo") )
						{	
							Element[] contentNodesChildren = XmlParser.getChildren(contentNodes[i]);
							EpiscopasyInfo info = new EpiscopasyInfo();
							for(int j=0; j<contentNodesChildren.length; ++j){
								if(contentNodesChildren[j].getName().equals("ID") ){
									info.id = Integer.parseInt(contentNodesChildren[j].getText(0));
								}
								else if(contentNodesChildren[j].getName().equals("Name") ){
									info.name = contentNodesChildren[j].getText(0);
								}
								else if(contentNodesChildren[j].getName().equals("Center") ){
									info.center = contentNodesChildren[j].getText(0);
								}
								else if(contentNodesChildren[j].getName().equals("Address") ){
									info.address = contentNodesChildren[j].getText(0);
								}
								else if(contentNodesChildren[j].getName().equals("Phone") ){
									info.phone = contentNodesChildren[j].getText(0);
								}
								item.info = info;
							}
						}
						else if (contentNodes[i].getName().equals("Location") )
						{
							item.location = contentNodes[i].getText(0);
						}
						else if (contentNodes[i].getName().equals("Place") )
						{
							item.place = contentNodes[i].getText(0);
						}
						else if (contentNodes[i].getName().equals("DistanceFromBelgrade") )
						{
							item.distanceFromBGD = contentNodes[i].getText(0);
						}
						else if (contentNodes[i].getName().equals("DistanceFromNoviSad") )
						{
							item.distanceFromNS = contentNodes[i].getText(0);
						}
						else if (contentNodes[i].getName().equals("DistanceFromEpCenter") )
						{
							item.distanceFromCenter = contentNodes[i].getText(0);
						}
						else if (contentNodes[i].getName().equals("Itinerary") )
						{
							item.itinerary = contentNodes[i].getText(0);
						}
					}
				}
			}
			catch (IOException e)
			{
				Log.e(TAG, e.getMessage());
			}
			catch (XmlPullParserException e)
			{
				Log.e(TAG, e.getMessage());
			}

			try
			{
				isr.close();
			}
			catch (IOException e) {}

		}
		return item;
	}

	public static ArrayList<News> getNewsList(String response)
	{
		ArrayList<News> result = new ArrayList<News>();
		if (response != null && response.equals("") == false)
		{
			KXmlParser xmlParser = new KXmlParser();
			Document xmlDoc = new Document();

			ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
			InputStreamReader isr = new InputStreamReader( bin );

			try
			{
				xmlParser.setInput(isr);
				xmlDoc.parse(xmlParser);
				Element xmlRoot = xmlDoc.getRootElement();
				if(xmlRoot != null)
				{
					Element[] xmlChild = XmlParser.getChildren(xmlRoot);
					for ( int index = 0; index < xmlChild.length; ++index )
					{
						News item = new News();
						Element[] contentNodes = XmlParser.getChildren(xmlChild[index]);
						for ( int i = 0; i < contentNodes.length; ++i )
						{
							if (contentNodes[i].getName().equals("ID") && contentNodes[i].getChildCount() > 0)
							{
								item.id = Integer.parseInt(contentNodes[i].getText(0));
							}
							else if (contentNodes[i].getName().equals("Title") && contentNodes[i].getChildCount() > 0)
							{
								item.title = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("NewsText") && contentNodes[i].getChildCount() > 0)
							{
								item.newsText = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("Date") && contentNodes[i].getChildCount() > 0)
							{
								item.date = contentNodes[i].getText(0);
							}
						}
						result.add(item);
					}
				}
			}
			catch (IOException e)
			{
				Log.e(TAG, e.getMessage());
			}
			catch (XmlPullParserException e)
			{
				Log.e(TAG, e.getMessage());
			}

			try
			{
				isr.close();
			}
			catch (IOException e) {}

		}
		return result;
	}

	public static ArrayList<TypeOfRate> getCoveragePeriod(String response)
	{
		ArrayList<TypeOfRate> result = new ArrayList<TypeOfRate>();
		if (response != null && response.equals("") == false)
		{
			KXmlParser xmlParser = new KXmlParser();
			Document xmlDoc = new Document();

			ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
			InputStreamReader isr = new InputStreamReader( bin );

			try
			{
				xmlParser.setInput(isr);
				xmlDoc.parse(xmlParser);
				Element xmlRoot = xmlDoc.getRootElement();
				if(xmlRoot != null)
				{
					Element[] xmlChild = XmlParser.getChildren(xmlRoot);
					for ( int index = 0; index < xmlChild.length; ++index )
					{
						TypeOfRate item = new TypeOfRate();
						Element[] contentNodes = XmlParser.getChildren(xmlChild[index]);
						for ( int i = 0; i < contentNodes.length; ++i )
						{
							if (contentNodes[i].getName().equals("ID") && contentNodes[i].getChildCount() > 0)
							{
								item.id = contentNodes[i].getText(0);
							}
							else if (contentNodes[i].getName().equals("Days") && contentNodes[i].getChildCount() > 0)
							{
								item.type = contentNodes[i].getText(0);
							}
						}
						result.add(item);
					}
				}
			}
			catch (IOException e)
			{
				Log.e(TAG, e.getMessage());
			}
			catch (XmlPullParserException e)
			{
				Log.e(TAG, e.getMessage());
			}

			try
			{
				isr.close();
			}
			catch (IOException e) {}

		}
		return result;
	}
	public static ArrayList<TollCity> getDestinationCityList(String response)
	{
		ArrayList<TollCity> result = new ArrayList<TollCity>();
		if (response != null && response.equals("") == false)
		{
			KXmlParser xmlParser = new KXmlParser();
			Document xmlDoc = new Document();

			ByteArrayInputStream bin = new ByteArrayInputStream(response.getBytes());
			InputStreamReader isr = new InputStreamReader( bin );

			try
			{
				xmlParser.setInput(isr);
				xmlDoc.parse(xmlParser);
				Element xmlRoot = xmlDoc.getRootElement();
				if(xmlRoot != null)
				{
					Element[] xmlChild = XmlParser.getChildren(xmlRoot);
					for ( int index = 0; index < xmlChild.length; ++index )
					{
						TollCity item = new TollCity();
						Element[] contentNodes = XmlParser.getChildren(xmlChild[index]);
						for ( int i = 0; i < contentNodes.length; ++i )
						{
							if (contentNodes[i].getName().equals("ID") && contentNodes[i].getChildCount() > 0)
							{
								item.id = Integer.parseInt(contentNodes[i].getText(0));
							}
							else if (contentNodes[i].getName().equals("Name") && contentNodes[i].getChildCount() > 0)
							{
								item.name = contentNodes[i].getText(0);
							}
						}
						result.add(item);
					}
				}
			}
			catch (IOException e)
			{
				Log.e(TAG, e.getMessage());
			}
			catch (XmlPullParserException e)
			{
				Log.e(TAG, e.getMessage());
			}

			try
			{
				isr.close();
			}
			catch (IOException e) {}

		}
		return result;
	}


}
