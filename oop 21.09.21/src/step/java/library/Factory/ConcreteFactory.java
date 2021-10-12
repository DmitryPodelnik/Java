package step.java.library.Factory;

import org.json.JSONObject;
import step.java.library.Literature;

/**
 * Interface for concrete factories: BookF, JournalF, etc.
 */
public interface ConcreteFactory {
    /**
     * Factory method
     *
     * @param obj JSON object
     * @returnconcrete object of Literature type
     */
    Literature create(JSONObject obj);

    /**
     * Supported type of Literature
     *
     * @return String type representation
     */
    String getLiteratureType();
}
