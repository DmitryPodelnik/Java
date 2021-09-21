package com.library.lib;

public abstract class Literature {  // base - superclass
    private String title ;

    public String getTitle () {

        return title;
    }

    public void setTitle (String title) {

        this.title = title;
    }

    /**
     *
     */
    public abstract String toJsonString (Literature lit) ;

    // print() should be for access from funds
    // but nothing to implement, so it is abstract
    public abstract void print () ;
}
