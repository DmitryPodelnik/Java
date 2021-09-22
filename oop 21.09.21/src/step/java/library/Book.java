package step.java.library;

import org.json.JSONObject;

public class Book
        extends Literature  // extension - inheritance
        implements Printable { // interface implementation

    // Book( "Martin Eden", "Jack London" )
    private String _author;

    public String getAuthor() {
        return _author;
    }

    public void setAuthor(String author) {
        this._author = author;
    }

    public Book(String title, String _author) {
        // this._title  - error, no access
        //this.set_title(title);  // warning
        super.setTitle(title);  // OK
        this._author = _author;
    }

    @Override
    public void print() {
        System.out.printf(
                "Book: %s (by %s)",
                super.getTitle(),
                _author

        );
    }

    /**
     *
     * @param lit - Literature object
     * @return JSON string if lit is instanceof Book or null
     */
    @Override
    public String toJsonString (Literature lit) {
        if (lit instanceof Book) {
            try {
                Book book = (Book) lit;

                JSONObject obj = new JSONObject();
                obj.put("title", book.getTitle());
                obj.put("author", book.getAuthor());

                return obj.toString();
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return super.getTitle() + "_" + this.getAuthor();
    }
}
