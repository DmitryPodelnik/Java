package step.java.library.Factory;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import step.java.library.Newspaper;

import static org.junit.Assert.*;

public class NewspaperFactoryTest {

    @Test
    public void create() {
        JSONObject newspaper = TestSamples.getJsonNewspaper();
        JSONObject journal = TestSamples.getJsonJournal();
        JSONObject invalid = TestSamples.getJsonInvalidType();

        NewspaperFactory factory = new NewspaperFactory();
        Assert.assertNotNull(factory.create(newspaper));
        Assert.assertNull(factory.create(journal));
        Assert.assertNull(factory.create(invalid));

        Newspaper nPaper = (Newspaper) factory.create(newspaper);

        Assert.assertEquals(
                nPaper.getPublishingHouse(),
                newspaper.getString("publisher")
        );
    }

    @Test
    public void getLiteratureType() {
        Assert.assertEquals(
                new NewspaperFactory().getLiteratureType(),
                "Newspaper"
        );
    }
}