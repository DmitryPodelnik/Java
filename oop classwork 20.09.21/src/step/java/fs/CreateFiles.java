package step.java.fs;

import step.java.library.Book;
import step.java.library.Factory.BookFactory;
import step.java.library.Factory.TestSamples;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create files with library funds
 */
public class CreateFiles {
    /**
     * Shows directory content
     */
    public void showDir() {
        // ! Creation of obj IS NOT creation of file
        // File class is for both: files and directories
        File dir = new File("./");
        SimpleDateFormat printer = new SimpleDateFormat(
                "dd.MM.yyyy HH:mm");
        if (dir.isDirectory()) {
            // get files list
            for (File f : dir.listFiles()) {
                System.out.printf(
                        "%s\t%s\t%s%n",
                        printer.format(f.lastModified()),
                        f.isDirectory()
                        ? "<DIR>"
                        : f.length() + "",
                        f.getName()
                );
            }
        } else {
            System.err.println("Path not found");
        }

    }

    public void createBook() {
        Book book = (Book) new BookFactory()
                .create(TestSamples.getJsonBook());
        String filename = "./src/step/java/fs/"
                + book.getAuthor()
                + "_" + book.getTitle()
                + ".json";
        File file = new File(filename);
        // Writing file: stream API
        // try () {} catch() {}
        try (  // try-with-resource (~using(){} in C#)
                OutputStream writer =
                     new FileOutputStream(file)
        ){
            writer.write(
                    TestSamples.getJsonBook()
                    .toString().getBytes()
            );
            writer.flush();
            System.out.println(filename);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

    }
}
/*
    Работа с файлами
    1. Работа с файловой системой (список файлов, копировать, удалить, проверить наличие, создать каталог и т.п);
    2. Работа с файлами как с данными (открыть, читать, писать);
    3.
 */

/*
    Данные - это информация, зафиксированная произвольным образом.
    Информация - это свойство мира. Информация - изменение, производная.
    Информация - сообщение о событии.
    Файл - данные, объединенные именем.
 */