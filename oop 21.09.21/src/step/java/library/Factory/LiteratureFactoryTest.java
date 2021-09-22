package step.java.library.Factory;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import step.java.library.Book;
import step.java.library.Journal;
import step.java.library.Literature;

import static org.junit.Assert.*;

public class LiteratureFactoryTest {

    @Test
    public void registerFactory() {
        LiteratureFactory litFactory = new LiteratureFactory();
        BookFactory bookFactory = new BookFactory();
        JournalFactory journalFactory = new JournalFactory();
        NewspaperFactory newspaperFactory = new NewspaperFactory();

        Assert.assertTrue(litFactory.registerFactory(bookFactory));
        Assert.assertFalse(litFactory.registerFactory(bookFactory));
        Assert.assertTrue(litFactory.registerFactory(journalFactory));
        Assert.assertFalse(litFactory.registerFactory(journalFactory));
        Assert.assertTrue(litFactory.registerFactory(newspaperFactory));
        Assert.assertFalse(litFactory.registerFactory(newspaperFactory));
    }

    @Test
    public void createFrom() {
        BookFactory bookFactory = new BookFactory();
        JournalFactory journalFactory = new JournalFactory();
        NewspaperFactory newspaperFactory = new NewspaperFactory();

        LiteratureFactory literatureFactory = new LiteratureFactory();
//        literatureFactory.registerFactory(bookFactory);
//        literatureFactory.registerFactory(journalFactory);
//        literatureFactory.registerFactory(newspaperFactory);

        Literature lit;
        lit = literatureFactory.createFrom(TestSamples.getJsonBook());
        Assert.assertTrue(lit instanceof Book);
        lit = literatureFactory.createFrom(TestSamples.getJsonJournal());
        Assert.assertTrue(lit instanceof Journal);
        lit = literatureFactory.createFrom(TestSamples.getJsonInvalidType());
        Assert.assertNull(lit);
        lit = literatureFactory.createFrom(TestSamples.getJsonNewspaper());
        Assert.assertNull(lit);
    }
}