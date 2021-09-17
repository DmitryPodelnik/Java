package com.library.lib;

import java.util.ArrayList;

public class Library {
    // Aggregation - collection of ...
    private ArrayList<Literature> funds ;   // Funds
    // = new ArrayList<>() ;  // Not recommend: code/declaration mixing

    public Library() {
        funds = new ArrayList<>() ;  // OK
    }

    public void add( Literature lit ) {
        funds.add( lit ) ;
    }

    public void print() {
        for( Literature lit : funds ) {
            lit.print() ;
            System.out.println() ;  // new line
        }
    }
}
