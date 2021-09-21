package step.java.library.Factory;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import step.java.library.Book;
import step.java.library.Journal;

import static org.junit.Assert.*;

public class BookFactoryTest {

    @Test
    public void create() {
        JSONObject book = new JSONObject();
        book.put("type", "Book");
        book.put("author", "Jack London");
        book.put("title", "Martin Eden");

        JSONObject jrnll = new JSONObject();
        jrnll.put("type", "Journal");
        jrnll.put("number", "2021, 2");
        jrnll.put("title", "Quantum Mechanics1");

        BookFactory factory = new BookFactory();
        Assert.assertNull(factory.create(jrnll));
        Assert.assertNull(factory.create(TestSamples.getJsonInvalidType()));

        Book testBook = (Book) factory.create(book);
        Assert.assertNotNull(testBook);

        Assert.assertEquals(
                book.getString("author"),
                testBook.getAuthor()
        );
        Assert.assertEquals(
                book.getString("title"),
                testBook.getTitle()
        );

    }

    @Test
    public void getLiteratureType() {
        Assert.assertEquals(
                "Book",
                new BookFactory().getLiteratureType()
        );
    }
}