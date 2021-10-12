package com.library;

import com.library.exercises.Db;
import com.library.factory.LiteratureFactory;
import com.library.lib.*;

public class Main {

    public static void main(String[] args) {
        // Library lib = new Library();
//        lib.add( new Journal( "Quantum Mechanics", "2021, 1" ) );
//        lib.add( new Book( "Martin Eden", "Jack London" ) );
//        lib.add(new Newspaper("Ukrainian Truth", "Fronir Ukraine"));
//        lib.add(new Hologram("Pectoral"));
        // lib.addDirectoryAsync("./src/com/library/fs", lib::print);
        // lib.print();
//        Db db = new Db();
//        db.demo();

        // CreateFiles creator = new CreateFiles();
        // creator.showDir();
        // creator.createBook();
        // System.out.println(creator.getFileContent("./src/step/java/fs/" + "Jack London_Martin Eden.json"));
        // creator.createBook();

        // new Db().demo_xe();
        // new Db().demo_maria();
        new Db().register_xe();
    }
}
