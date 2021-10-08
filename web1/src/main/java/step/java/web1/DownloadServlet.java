package step.java.web1;

import step.java.web1.models.Picture;
import step.java.web1.util.Db;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        resp.getWriter().print(pic.getName());
    }
}
