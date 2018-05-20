package es.ucm.fdi.model;

import java.util.Map;

/**
 * Must implement it the classes that know how to describe themselves
 * given the statement of the project.
 * @author Daniel Herranz
 *
 */
public interface Describable {
    
    /**
     * Information of the object that is inserted inside the map.
     * @param out
     */
	void describe(Map<String, String> out);
}
