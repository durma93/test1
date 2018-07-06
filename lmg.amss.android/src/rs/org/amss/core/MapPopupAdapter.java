package rs.org.amss.core;

import rs.org.amss.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class MapPopupAdapter implements InfoWindowAdapter {
	Activity activity = null;
	LayoutInflater inflater = null;

  public MapPopupAdapter(Activity activity) {
	  this.activity = activity;
	  this.inflater=activity.getLayoutInflater();
  }

  @Override
  public View getInfoWindow(Marker marker) {
    return(null);
  }

  @Override
  public View getInfoContents(Marker marker) {
    View popup= inflater.inflate(R.layout.map_info_popup, null);

    TextView title = (TextView)popup.findViewById(R.id.title);
    title.setText(marker.getTitle());
    
    TextView snippet = (TextView)popup.findViewById(R.id.snippet);
    snippet.setText(marker.getSnippet());
    
    // Linkify.addLinks(snippet, Linkify.PHONE_NUMBERS | Linkify.EMAIL_ADDRESSES | Linkify.WEB_URLS);

    return(popup);
  }
}