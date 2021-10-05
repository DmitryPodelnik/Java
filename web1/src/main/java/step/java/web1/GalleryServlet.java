package step.java.web1;

import step.java.web1.util.Hasher;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class GalleryServlet
                extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*
            Загрузка файла:
                upload
                download
            Uploading:
                1. <form> <input type="file" ... />
                2. <form method="post"enctype="multipart/form-data" ... />
                3. конфиг. сервлета :
                    а) <multipart-config />
                    б) @MultipartConfig
                4. приём файла: в методе doPost через req.getPart
                5. извлечение имени файла !! по безопасности
                   крайне не рекомендуется сохранять файлы под переданными именами
         */
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        String[] sessionAttributes = {"uploadMessage", "galleryDescription"};
        for (String attrName : sessionAttributes) {
            String attrValue =
                    (String) session.getAttribute(attrName);
            if (attrValue != null) {
                session.removeAttribute(attrName);
            } else {
                attrValue = "";
            }
            req.setAttribute(attrName, attrValue);
        }



        /*
        // сообщение о загрузке файла (имя сохраненного файла)
        String uploadMessage = (String) session.getAttribute("uploadMessage");
        if (uploadMessage != null) {
            session.removeAttribute("uploadMessage");
        } else {
            uploadMessage = "";
        }
        req.setAttribute("uploadMessage", uploadMessage);

        // Description
        String galleryDescription = (String) session.getAttribute("galleryDescription");
        if (galleryDescription != null) {
            session.removeAttribute("galleryDescription");
        } else {
            galleryDescription = "";
        }
        req.setAttribute("galleryDescription", galleryDescription);
         */

        req.getRequestDispatcher("gallery.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Part filePart = req.getPart("galleryfile"); // имя <input type="file" />
        HttpSession session = req.getSession();

        /*
            Submitted file name
            a) .getSubmittedFileName()
            b) extract from header:
                content-disposition: form-daya; name="galleryfile"; filename="imp.png"
         */
        String attachedFilename = null;
        String contentDisposition = filePart.getHeader("content-disposition");
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
                if (extension.equals(".jpeg") || extension.equals(".jpg") ||
                        extension.equals(".gif") || extension.equals(".svg+xml")) {

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
                        attachedFilename = filename;
                    } while (destination.exists()); // если файл с таким именем уже есть, то перегенерировать имя


                    Files.copy(
                            filePart.getInputStream(), // source (Stream)
                            destination.toPath(), // destination (Path)
                            StandardCopyOption.REPLACE_EXISTING
                    );

                    // копируем в папку исходного проекта (для сохранения)
                    path = "D:\\JAVA ITSTEP\\GIT\\web1\\src\\main\\webapp\\uploads";
                    filename = path + "\\" + savedFilename;
                    destination = new File(filename);
                    Files.copy(
                            filePart.getInputStream(), // source (Stream)
                            destination.toPath(), // destination (Path)
                            StandardCopyOption.REPLACE_EXISTING
                    );
                } else {
                    attachedFilename = "no allow file extension";
                }
            } else { // no file extension
                attachedFilename = "no file extension";
            }
        }

        session.setAttribute(
                "uploadMessage",
                attachedFilename == null ? "Name error" : attachedFilename);
        // конец работы с файлом

        // Description передается как форма, извлекается обычным образом
        String description = req.getParameter("galleryDescription");
        session.setAttribute("galleryDescription", description);

        resp.sendRedirect(req.getRequestURI());
    }
}

/*
    Галерея
    Отображает картинки и описания к ним.
    Поддерживает возможность загрузки новых изображений.
 */
