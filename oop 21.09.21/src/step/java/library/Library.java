package step.java.library;

import step.java.library.Factory.LiteratureFactory;

import java.io.File;
import java.util.ArrayList;

public class Library {
    // Aggregation - collection of ...
    private ArrayList<Literature> _funds ;  // Funds
    //        = new ArrayList<>();  // Not recommended: code/declaration mixing

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

    /**
     * Scan directory for JSON files and try to add them to funds
     * @param dirname directory path
     */
    public void addDirectory (String dirname) {
        String tag = "Library.addDirectory ";
        if (dirname == null) {
            System.err.println(tag + "no directory");
            return;
        }

        File dir = new File(dirname);
        if (!dir.isDirectory()) {
            System.err.println(tag + "path is not directory");
            return;
        }

        LiteratureFactory literatureFactory = new LiteratureFactory();
        for (File file : dir.listFiles()) {
            Literature lit = literatureFactory.createFrom(file);
            System.out.print( file.getName() + " ");

            if( lit == null ) {
                System.out.println("ignored");
            } else {
                this.add(lit);
                System.out.println("added");
            }
        }

    }
}
