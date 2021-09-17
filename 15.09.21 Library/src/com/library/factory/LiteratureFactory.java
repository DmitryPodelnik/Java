package com.library.factory;

import com.library.lib.Literature;
import org.json.JSONObject;

import java.util.ArrayList;

public class LiteratureFactory {
    /**
     * Collection of factories for concrete types
     */
    private ArrayList<ConcreteFactory> factories ;

    public LiteratureFactory() {
        factories = new ArrayList<>() ;
    }

    /**
     * Registerer for concrete factories
     * @param factory the factory
     * @return true if register OK, false if errors or factory already registered
     */
    public boolean registerFactory( ConcreteFactory factory ) {
        if( factories.contains( factory ) ) {
            return false ;
        }
        factories.add( factory ) ;
        return true ;
    }

    /**
     * Creates concrete Literature (Book, Journal,...) from JSON Object
     * @param obj JSON Object with concrete fields
     * @return concrete Literature
     */
    Literature createFrom(JSONObject obj ) {

        return null ;
    }


}

