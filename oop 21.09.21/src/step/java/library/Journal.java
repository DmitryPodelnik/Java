package step.java.library;

import org.json.JSONObject;

public class Journal
        extends Literature
        implements Printable
        {
    private String _number;  // Year, № (2021, 1)

    public String getNumber() {
        return _number;
    }

    public void setNumber(String number) {
        this._number = number;
    }

    public Journal(String title, String number) {
        super.setTitle(title);
        this._number = number;
    }

    @Override
    public void print() {
        System.out.printf(
                "JournalFactory: %s (%s)",
                super.getTitle(),
                _number

        );
    }

            /**
             *
             * @param lit - Literature object
             * @return JSON string if lit is instanceof Journal or null
             */
            @Override
            public String toJsonString (Literature lit) {
                if (lit instanceof Journal) {
                    try {
                        Journal journal = (Journal) lit;

                        JSONObject obj = new JSONObject();
                        obj.put("title", journal.getTitle());
                        obj.put("author", journal.getNumber());

                        return obj.toString();
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                    }
                }
                return null;
            }

            @Override
            public String toString() {
                return super.getTitle() + "_" + this.getNumber();
            }
}
