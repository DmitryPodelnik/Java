package step.java.library;

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
}
