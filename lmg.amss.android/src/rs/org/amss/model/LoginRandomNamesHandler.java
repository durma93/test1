package rs.org.amss.model;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class LoginRandomNamesHandler extends DefaultHandler {
	// booleans that check whether it's in a specific tag or not 
	private boolean _namesFirstName; 

	private LoginRandomNamesGet _names; 

	public LoginRandomNamesGet getData() { 
		return _names; 
	} 

	@Override 
	public void startDocument() throws SAXException { 
		_names = new LoginRandomNamesGet(); 
	} 

	@Override 
	public void endDocument() throws SAXException { 

	} 
	@Override 
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException { 

		if(localName.equals("string")) { 
			_namesFirstName = true; 
		}
	} 

	@Override 
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException { 
		if(localName.equals("string")) { 
			_namesFirstName = false; 
		} 

	} 

	@Override 
	public void characters(char ch[], int start, int length) { 
		String chars = new String(ch, start, length); 
		chars = chars.trim(); 

		if(_namesFirstName) { 
			_names.firstName = chars; 
		} 
	}


} 

