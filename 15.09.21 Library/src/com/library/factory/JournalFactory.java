package com.library.factory;

import com.library.lib.Journal;
import com.library.lib.Literature;
import org.json.JSONException;
import org.json.JSONObject;

public class JournalFactory implements ConcreteFactory {
    @Override
    public Literature create(JSONObject obj) {
        // obj.get...("field") throws JSONException if no field found
        try {
            return new Journal(
                    obj.getString("title"),
                    obj.getString("number")
            );
        } catch (JSONException ignored) {
            return null;
        }
    }

    @Override
    public String getLiteratureType() {
        return "Journal";
    }


}

