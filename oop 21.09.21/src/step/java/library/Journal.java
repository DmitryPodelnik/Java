package step.java.library;

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
}
