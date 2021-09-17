package com.library.factory;

import com.library.lib.Book;
import com.library.lib.Literature;
import org.json.JSONException;
import org.json.JSONObject;

public class BookFactory implements ConcreteFactory {
    @Override
    public Literature create(JSONObject obj) {
        try {
            if( ! "Book".equals( obj.getString( "type" ) ) )
                return null ;

            return new Book(
                    obj.getString("title"),
                    obj.getString("author")
            );
        } catch( JSONException ignored ) {
            return null;
        }
    }

    @Override
    public String getLiteratureType() {
        return "Book";
    }


}

