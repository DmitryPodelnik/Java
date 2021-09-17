package com.library.lib;

public class Newspaper
        extends Literature {

    // publishing office of newspaper
    private String _publishingHouse;

    public String get_publishingHouse() {
        return _publishingHouse;
    }

    public void set_publishingHouse(String _publishingHouse) {
        this._publishingHouse = _publishingHouse;
    }

    public Newspaper (String title, String publishingHouse) {
        // this.title - error, no access
        // this.setTitle( title ) ;  // warning
        super.setTitle(title); // OK
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
}
