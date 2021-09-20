package step.java.library;

public abstract class Literature {  // base - superclass
    private String _title;

    public String getTitle() {
        return _title;
    }

    public void setTitle(String _title) {
        this._title = _title;
    }

    // print() should be for access from funds
    // but nothing to implement, so it is abstract
    // public abstract void print();
}
