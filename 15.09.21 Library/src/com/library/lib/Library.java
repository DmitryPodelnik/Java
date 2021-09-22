package com.library.lib;

import com.library.factory.LiteratureFactory;
import com.library.fs.CreateFiles;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
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

            // if lit is NOT NULL and has right file extension (.exe, .json or .txt), then add or ignore
            if( lit == null && !CreateFiles.checkRightExtension(file.getName())) {
                System.out.println("ignored");
            } else {
                this.add(lit);
                System.out.println("added");
            }
        }

    }
}
