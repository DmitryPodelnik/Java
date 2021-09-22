package step.java.library;

import org.json.JSONObject;

public class Newspaper
        extends Literature
        implements Printable
        {

    // publishing office of newspaper
    private String _publishingHouse;

    public String getPublishingHouse() {
        return _publishingHouse;
    }

    public void setPublishingHouse(String _publishingHouse) {
        this._publishingHouse = _publishingHouse;
    }

    public Newspaper (String title, String publishingHouse) {
        // this.title - error, no access
        // this.setTitle( title ) ;  // warning
        super.setTitle(title);
        this._publishingHouse = publishingHouse;
    }

    @Override
    public void print () {
        System.out.printf(
                "Newspaper: %s (%s)",
                super.getTitle(),
                _publishingHouse
        );
    }

            /**
             *
             * @param lit - Literature object
             * @return JSON string if lit is instanceof Newspaper or null
             */
            @Override
            public String toJsonString (Literature lit) {
                if (lit instanceof Newspaper) {
                    try {
                        Newspaper newspaper = (Newspaper) lit;

                        JSONObject obj = new JSONObject();
                        obj.put("title", newspaper.getTitle());
                        obj.put("publisher", newspaper.getPublishingHouse());

                        return obj.toString();
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                    }
                }
                return null;
            }

            @Override
            public String toString() {
                return super.getTitle() + "_" + this.getPublishingHouse();
            }
}

