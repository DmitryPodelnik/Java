package step.java.library;

import java.util.ArrayList;

public class Library {
    // Aggregation - collection of ...
    private ArrayList<Literature> _funds ;  // Funds
    //        = new ArrayList<>();  // Not recommended: code/

    public Library() {
        _funds = new ArrayList<>(); // OK
    }

    public void add (Literature lit) {
        _funds.add(lit);
    }

    public void print(){
        /* for (Literature lit: _funds) {
            lit.print();
            System.out.println(); // new line
        } */
        // print () seems to be unnecessary in Literature class
        /*
        for (Literature lit : _funds) {
            if (lit instanceof Book) {
                ((Book) lit).print();
            }
        }  // reduces scalability - we should add code for each new class
        */

        // The problem: some funds are printable, some are not
        // We want to add Holograms

        for (Literature lit: _funds) {
            if (lit instanceof Printable) {
                ((Printable) lit).print();
                System.out.println(); // new line
            }
        }
    }
}
