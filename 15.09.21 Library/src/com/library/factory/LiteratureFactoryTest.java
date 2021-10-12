package com.library.factory;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class LiteratureFactoryTest {

    @Test
    public void registerFactory() {
        BookFactory bookFactory = new BookFactory();
        JournalFactory journalFactory = new JournalFactory();
        LiteratureFactory literatureFactory = new LiteratureFactory();

        Assert.assertTrue(
                literatureFactory.registerFactory(bookFactory));
        Assert.assertFalse(
                literatureFactory.registerFactory(bookFactory));
        Assert.assertTrue(
                literatureFactory.registerFactory(journalFactory));
        Assert.assertFalse(
                literatureFactory.registerFactory(journalFactory));

    }


    @Test
    public void createFrom() {
        JSONObject objBook = TestSamples.getJsonBook();
        JSONObject objJournal = TestSamples.getJsonJournal();
        JSONObject objInvalid = TestSamples.getJsonInvalidType();
        LiteratureFactory literatureFactory = new LiteratureFactory();

        Assert.assertNotNull(literatureFactory.createFrom(objBook));
        Assert.assertNotNull(literatureFactory.createFrom(objJournal));
        Assert.assertNull(literatureFactory.createFrom(objInvalid));
    }
}