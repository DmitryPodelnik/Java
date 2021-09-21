package step.java.library.Factory;

import org.json.JSONObject;
import step.java.library.Literature;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class LiteratureFactory {
    /**
     * Collection of factories for concrete types
     */
    private ArrayList<ConcreteFactory> _factories;

    public LiteratureFactory() {
        _factories = new ArrayList<>();
    }

    /**
     * Registration for concrete factories
     * @param factory the factory
     * @return if registered OK, false if error or factory already registered
     */
    public boolean registerFactory(ConcreteFactory factory) {
        if (_factories.contains(factory)) {
            return false;
        }

        _factories.add(factory);
        return true;
    }

    /**
     * Creates concrete Literature (Book, Journal, ...) from JSON Object
     * @param obj JSON Object with concrete fields
     * @return concrete Literature
     */
    Literature createFrom (JSONObject obj) {
        for (ConcreteFactory factory : _factories) {
            Literature lit = factory.create(obj);
            if (lit != null) {
                return lit;
            }
        }
        return null;
    }

    public Literature createFrom (File file) {
        if (file == null) {
            return null;
        }

        try (InputStream reader = new FileInputStream(file)) {
            int sym;
            StringBuilder sb = new StringBuilder();

            while((sym = reader.read()) != -1) {
                sb.append((char) sym);
            }

            return this.createFrom( new JSONObject(sb.toString()) );
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }





}
