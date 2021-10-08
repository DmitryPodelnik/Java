package step.java.web1;

import step.java.web1.models.Picture;
import step.java.web1.util.Db;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet("/download/*")
public class DownloadServlet
        extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.length() < 2) {
            resp.setStatus(400);
            resp.getWriter().print("Id missing or empty");
            return;
        }
        String pictureId = req.getPathInfo();
        // Задача: по pictureId определить имя файла картинки
        Picture pic = Db.getPictureById(pictureId);
        if (pic == null) {
            resp.setStatus(404);
            resp.getWriter().print("Picture not found");
            return;
        }
        String fullname = req.getServletContext().getRealPath("/uploads")
                + "/" + pic.getName();
        File file = new File(fullname);
        if (!file.exists()) {
            resp.setStatus(400);
            resp.getWriter().print("File not found");
            return;
        }
        // Скачиваем файл:
        // 1. Устанавливаем заголовки
        //  1.1. Content-Type
        resp.setContentType(
                req.getServletContext().getMimeType(pic.getName())
        );
        //  1.2. Content-Disposition
        resp.setHeader("Content-Disposition",
                       "attachment; filename=\"picture"
                       + pic.getName().substring(pic.getName().lastIndexOf("."))\
                       + "\"");
        //  1.3. Content-Length
        resp.setContentLengthLong(file.length());
        // 2. Записываем (копируем) файл в поток ответа (resp)
        //
        resp.getWriter().print(fullname);
    }
}
