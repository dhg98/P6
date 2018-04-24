package es.ucm.fdi.control.eventbuilders;

import java.util.ArrayList;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.model.events.Event;

public interface EventBuilder {
	
	/**
	 * Parser given the statement of the project
	 * @param sec
	 * @return
	 */
	public Event parse(IniSection sec);
	
	/**
	 * Determines if an id is valid given the statement of the project.
	 * @param id
	 * @return true if it is and false if it is not.
	 */
	public default boolean isValidId(String id) {
	    for (char c : id.toCharArray()) {
	        if (!Character.isLetterOrDigit(c) && c != '_') {
	            return false;
	        }
	    }
	    return true;
	}
	
	/**
	 * Parser of an Integer number given the lower bound
	 * @param sec
	 * @param key
	 * @param def
	 * @return The number that matches the key in the IniSection if it is greater or equal than def
	 * @throws IllegalArgumentException
	 */
	public default int parseInt(IniSection sec, String key, int def) {
		int res;
		try {
			res = Integer.parseInt(sec.getValue(key));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("There was an error while parsing the key " + key , e);
		}
		if (res < def) {
			throw new IllegalArgumentException("There was an error while parsing the key " + key + 
			        " because one of the numbers is less than " + def);
		} else {
			return res;
		}
	}

	/**
	 * Parser of a Dobule number given the lower bound
	 * @param sec
	 * @param key
	 * @param def
	 * @return The number that matches the key in the IniSection if it is greater or equal than def
	 * @throws IllegalArgumentException
	 */
	public default double parseDouble(IniSection sec, String key, int def) {
		double res;
		try {
			res = Double.parseDouble(sec.getValue(key));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("There was an error while parsing the key " + key , e);
		}
		if (res < def) {
			throw new IllegalArgumentException("There was an error while parsing the key " + key + 
			        " because one of the numbers is less than " + def);
		} else {
			return res;
		}
	}

	/**
	 * Parser of a Long number given the lower bound
	 * @param sec
	 * @param key
	 * @param def
	 * @return The number that matches the key in the IniSection if it is greater or equal than def
	 * @throws IllegalArgumentException
	 */
	public default long parseLong(IniSection sec, String key, int def) throws IllegalArgumentException {
		long res;
		try {
			res = Long.parseLong(sec.getValue(key));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("There was an error while parsing the key " + key , e);
		}
		if (res < def) {
			throw new IllegalArgumentException("There was an error while parsing the key " + key + 
			        " because one of the numbers is less than " + def);
		} else {
			return res;
		}
	}
	
	/**
	 * Parser of a List
	 * @param sec
	 * @param key
	 * @param def
	 * @return the list of ids if they are valid or throws an Exception
	 * @throws IllegalArgumentException
	 */
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

