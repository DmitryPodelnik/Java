package com.example.libraryexam;

import utils.Hasher;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class LibraryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Author - передается как форма, извлекается обычным образом
        String author = req.getParameter("author");
        // Title - передается как форма, извлекается обычным образом
        String title = req.getParameter("title");

        Part filePart = req.getPart("galleryfile");  // имя  <input type="file"
        HttpSession session = req.getSession();

        String attachedFilename = null;
        String contentDisposition =
                filePart.getHeader("content-disposition");
        if (contentDisposition != null) {
            for (String part : contentDisposition.split("; ")) {
                if (part.startsWith("filename")) {
                    attachedFilename = part.substring(10, part.length() - 1);
                    break;
                }
            }
        }
        if (attachedFilename != null) {  // имя успешно извлечено
            // Задача: отделить имя и расширение
            String extension;
            int dotPosition = attachedFilename.lastIndexOf(".");
            if (dotPosition > -1) {
                extension = attachedFilename.substring(dotPosition);
                // Определяем путь в файловой системе
                String path = req.getServletContext().getRealPath("/uploads");

                File destination;
                String filename, savedFilename;
                do {
                    // формируем случайное имя файла, сохраняем расширение
                    savedFilename = Hasher.hash(attachedFilename) + extension;
                    // Полное имя файла
                    filename = path + "\\" + savedFilename;
                    destination = new File(filename);
                    attachedFilename = savedFilename;
                } while (destination.exists());  // если файл с таким именем уже есть, перегенерировать имя

                Files.copy(
                        filePart.getInputStream(),  // source (Stream)
                        destination.toPath(),       // destination (Path)
                        StandardCopyOption.REPLACE_EXISTING
                );
    }
}
