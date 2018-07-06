package rs.org.amss.model;

import java.io.Serializable;
import java.util.HashMap;

public class ActivityExtra implements Serializable {
	
	private static final long serialVersionUID = -7036323366316342295L;

	public static final String HOME_DRAWABLE_ID = "homeDrawableId";
	
	public static final String HOME_TEXT_ID = "homeTextId";
	
	public static final String TEXT_ID = "textId";
	
	public static final String LINK = "link";
	
	private HashMap<String, Object> params = new HashMap<String, Object>();
	
	public void clear(){
		params.clear();
	}
	
	public void add(String key, Object value){
		params.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key){
		return (T)params.get(key);
	}
}
