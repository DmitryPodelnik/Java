package step.java.library.Factory;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import step.java.library.Journal;

import static org.junit.Assert.*;

public class JournalFactoryTest {

    @org.junit.Test
    public void create() {
        JSONObject book = TestSamples.getJsonBook();
        JSONObject jrnl = TestSamples.getJsonJournal();

        JournalFactory factory = new JournalFactory();
        Assert.assertNull(factory.create(book));

        Journal journal = (Journal) factory.create(jrnl);
        Assert.assertNotNull(factory.create(jrnl));
        Assert.assertNull(factory.create(TestSamples.getJsonInvalidType()));

        Assert.assertEquals(
                journal.getNumber(),
                jrnl.getString("number")
        );
        Assert.assertEquals(
                journal.getTitle(),
                jrnl.getString("title")
        );

    }

    @org.junit.Test
    public void getLiteratureType() {
        Assert.assertEquals(
                new JournalFactory().getLiteratureType(),
                "Journal"
        );
    }
}