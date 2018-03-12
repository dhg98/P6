package es.ucm.fdi.control.eventbuilders;

import java.util.ArrayList;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.Event;

public interface EventBuilder {
	
	public Event parse(IniSection sec);
	//public String template(); no sabemos que es 
	
	public default boolean isValidId(String id){
		for (int i = 0; i < id.length(); ++i) {
			if (!Character.isLetterOrDigit(id.charAt(i)) && id.charAt(i) != '_') {
				return false;
			}
		}
		return true;
	}
	
	public default int parseInt(IniSection sec, String key, int def) throws IllegalArgumentException {
		int res;
		try {
			res = Integer.parseInt(sec.getValue(key));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("There was an error while parsing the key " + key , e);
		}
		if (res < def) {
			throw new IllegalArgumentException("There was an error while parsing the key " + key + " because one of the numbers is less than " + def);
		} else {
			return res;
		}
	}

	public default double parseDouble(IniSection sec, String key, int def) throws IllegalArgumentException {
		double res;
		try {
			res = Double.parseDouble(sec.getValue(key));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("There was an error while parsing the key " + key , e);
		}
		if (res < def) {
			throw new IllegalArgumentException("There was an error while parsing the key " + key + " because one of the numbers is less than " + def);
		} else {
			return res;
		}
	}

	public default long parseLong(IniSection sec, String key, int def) throws IllegalArgumentException {
		long res;
		try {
			res = Long.parseLong(sec.getValue(key));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("There was an error while parsing the key " + key , e);
		}
		if (res < def) {
			throw new IllegalArgumentException("There was an error while parsing the key " + key + " because one of the numbers is less than " + def);
		} else {
			return res;
		}
	}
	public default ArrayList<String> parseIdList(IniSection sec, String key) {
		String[] it = sec.getValue(key).split(",");
		ArrayList<String> iti = new ArrayList<>();
		for (int i = 0; i < it.length; ++i) {
			if (!isValidId(it[i])) {
				throw new IllegalArgumentException("The IdList is incorrect in the position number " + i);
			} else {
				iti.add(it[i]);
			}
		}
		return iti;
	}
}

