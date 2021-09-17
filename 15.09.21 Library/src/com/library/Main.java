package com.library;

import com.library.lib.*;

public class Main {

    public static void main(String[] args) {
        Library lib = new Library() ;
        lib.add( new Journal( "Quantum Mechanics", "2021, 1" ) ) ;
        lib.add( new Book( "Martin Eden", "Jack London" ) ) ;
        lib.add( new Newspaper("Ukrainian Truth", "Fronir Ukraine") );
        lib.add( new Hologram("New Hologram") );
        lib.print() ;
    }
}
