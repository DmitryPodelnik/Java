package step.java.library.Factory;

import org.json.JSONException;
import org.json.JSONObject;
import step.java.library.Journal;
import step.java.library.Literature;
import step.java.library.Newspaper;

public class NewspaperFactory
        implements ConcreteFactory {

    @Override
    public Literature create(JSONObject obj) {
        // obj.get...("field") throws JSONException if no field found
        try {
            if (!"Newspaper".equals(obj.getString("type"))) {
                return null;
            }
            return new Newspaper(
                    obj.getString("title"),
                    obj.getString("publisher")
            );
        } catch (JSONException ignored) {
            return null;
        }
    }

    @Override
    public String getLiteratureType() {
        return "Newspaper";
    }

}
