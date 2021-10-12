package step.java.library;

import step.java.fs.CreateFiles;
import step.java.library.Factory.LiteratureFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Library {
    // Aggregation - collection of ...
    private ArrayList<Literature> _funds;  // Funds
    //        = new ArrayList<>();  // Not recommended: code/declaration mixing
    private int n;  // threads counter
    private final Object mutex;

    private AtomicInteger count = new AtomicInteger(0);

    public Library() {
        _funds = new ArrayList<>(); // OK
        mutex = new Object();
    }

    public void add(Literature lit) {
        _funds.add(lit);
    }

    public void print() {
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

        for (Literature lit : _funds) {
            if (lit instanceof Printable) {
                ((Printable) lit).print();
                System.out.println(); // new line
            }
        }
    }

    /**
     * Scan directory for JSON files and try to add them to funds
     *
     * @param dirname directory path
     */
    public void addDirectory(String dirname) {
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

        // Задача: реализовать работу с фалйами асинхронно
        for (File file : dir.listFiles()) {

            Runnable createFactory = () -> {
                count.addAndGet(1);

                Literature lit;
                synchronized (file) {
                    lit = literatureFactory.createFrom(file);
                }

                // if lit is NOT NULL and has right file extension (.exe, .json or .txt), then add or ignore
                if (lit == null && !CreateFiles.checkRightExtension(file.getName())) {
                    System.out.println("ignored");
                } else {
                    this.add(lit);
                    System.out.println("added");
                }

                if (count.get() == dir.listFiles().length) {
                    this.print();
                }
            };

            new Thread(createFactory).start();
        }
    }


    /**
     * Scan directory for JSON files and try to add them to funds
     *
     * @param dirname directory path
     * @param then    callback after finish
     */
    public void addDirectoryAsync(String dirname, Runnable then) {
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

        // Задача: реализовать работу с фалйами асинхронно
        n = 0; // clear counter
        for (File file : dir.listFiles()) {
            n++; // increase threads counter
            Runnable createFactory = () -> {
                //count.addAndGet(1);

                try {

                    Literature lit;
                    synchronized (file) {
                        lit = literatureFactory.createFrom(file);
                    }

                    // if lit is NOT NULL and has right file extension (.exe, .json or .txt), then add or ignore
                    if (lit == null && !CreateFiles.checkRightExtension(file.getName())) {
                        System.out.println("ignored");
                    } else {
                        this.add(lit);
                        System.out.println("added");
                    }

                    // if (count.get() == dir.listFiles().length) {
                    //    this.print();
                    // }
                } finally {
                    synchronized (mutex) {
                        n--;
                        if (n == 0) {
                            new Thread(then).start();
                        }
                    }
                }
            };

            new Thread(createFactory).start();
        }
    }


}
