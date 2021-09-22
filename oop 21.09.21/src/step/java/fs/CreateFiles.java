package step.java.fs;

import org.apache.commons.io.FilenameUtils;
import step.java.library.Book;
import step.java.library.Factory.BookFactory;
import step.java.library.Factory.TestSamples;

import java.io.*;
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
                     new FileOutputStream(file) ){
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

    /**
     * Read all file data and return as string
     * @param filename
     * @return
     */
    public String getFileContent(String filename) {
        File file = new File(filename);
        if(!file.exists()) {
            return null;
        }
        try (InputStream reader = new FileInputStream(file)) {
            int sym; // symbol from the file, -1 -> EOF
            StringBuilder sb = new StringBuilder();

            while((sym = reader.read()) != -1) {
                // str += (char) sym; not correct
                sb.append((char) sym);
            }

            return sb.toString();
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * check for right file extension (.exe, .json or .txt)
     * @return true if extension is right and false if not
     */
    public static boolean checkRightExtension(String filename) {
        return isJsonFile(filename) || isExeFile(filename) || isTxtFile(filename);
    }

    public static boolean isJsonFile(String filename) {
        return FilenameUtils.getExtension(filename) == "json"
                ? true
                : false;
    }
    public static boolean isExeFile(String filename) {
        return FilenameUtils.getExtension(filename) == "exe"
                ? true
                : false;
    }
    public static boolean isTxtFile(String filename) {
        return FilenameUtils.getExtension(filename) == "txt"
                ? true
                : false;
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