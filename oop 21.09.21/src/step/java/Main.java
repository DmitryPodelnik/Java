package step.java;

import oracle.jdbc.driver.DBConversion;
import step.java.exercises.Db;
import step.java.exercises.Threads;
import step.java.fs.CreateFiles;
import step.java.library.*;

public class Main {

    public static void main(String[] args) {
        // Library lib = new Library();
        // lib.add( new Journal( "Quantum Mechanics", "2021, 1" ) );
        // lib.add( new Book( "Martin Eden", "Jack London" ) );
        // lib.add(new Newspaper("Ukrainian Truth", "Fronir Ukraine"));
        // lib.add(new Hologram("Pectoral"));
        // lib.addDirectoryAsync("./src/step/java/fs/", lib::print);
        // System.out.println("--------");
        // lib.print();
        // System.out.println("--------");
        //lib.print();

        // CreateFiles creator = new CreateFiles();
        // creator.showDir();
        // creator.createBook();
        // System.out.println(creator.getFileContent("./src/step/java/fs/" + "Jack London_Martin Eden.json"));
        // creator.createBook();

        // new Threads().demo2();

        // Db db = new Db();
        // db.demo();
        // new Db().login_xe();
        // new Db().auth_xe();
        // new Db().login_xe();
        new Db().register_xe();
    }
}
