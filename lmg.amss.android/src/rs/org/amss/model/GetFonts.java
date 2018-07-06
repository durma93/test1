package rs.org.amss.model;

import android.content.Context;
import android.graphics.Typeface;

public class GetFonts {
	private static Typeface typeFace;  
	private static Typeface slimTypeFace;
	private static Typeface boldTypeFace;
	private static Typeface italicTypeFace;
	private static Typeface boldItalicTypeFace;
	private static Typeface lightItalicTypeFace;


	public static Typeface getTypeface(Context mContext){
		if(typeFace==null){
			typeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/RobotoCondensedRegular.ttf");
		}
		return typeFace;
	}
	public static Typeface getSlimTypeface(Context mContext){
		if(slimTypeFace==null){
			slimTypeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/RobotoCondensedLight.ttf");
		}
		return slimTypeFace;
	}
	public static Typeface getBoldTypeface(Context mContext){
		if(boldTypeFace==null){
			boldTypeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/Rubik-Bold.ttf");
		}
		return boldTypeFace;
	}
	public static Typeface getItalicTypeface(Context mContext){
		if(italicTypeFace==null){
			italicTypeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/RobotoCondensedItalic.ttf");
		}
		return italicTypeFace;
	}

	public static Typeface getBoldItalicTypeface(Context mContext){
		if(boldItalicTypeFace==null){
			boldItalicTypeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/RobotoCondensedBoldItalic.ttf");
		}
		return boldItalicTypeFace;
	}
	public static Typeface getLightItalicTypeface(Context mContext){
		if(lightItalicTypeFace==null){
			lightItalicTypeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/RobotoCondensedLightItalic.ttf");
		}
		return lightItalicTypeFace;
	}
	public static Typeface getPlaceTypeface(Context mContext){
		return Typeface.createFromAsset(mContext.getAssets(), "fonts/tablicafont.ttf");
	}

}
