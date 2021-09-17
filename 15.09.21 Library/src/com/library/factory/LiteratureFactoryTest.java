package com.library.factory;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class LiteratureFactoryTest {

    @Test
    public void registerFactory() {
        BookFactory bookFactory = new BookFactory() ;
        JournalFactory journalFactory = new JournalFactory() ;
        LiteratureFactory literatureFactory = new LiteratureFactory() ;

        Assert.assertTrue(
                literatureFactory.registerFactory( bookFactory ) ) ;
        Assert.assertFalse(
                literatureFactory.registerFactory( bookFactory ) ) ;
        Assert.assertTrue(
                literatureFactory.registerFactory( journalFactory ) ) ;
        Assert.assertFalse(
                literatureFactory.registerFactory( journalFactory ) ) ;

    }


    @Test
    public void createFrom() {
        
    }
}