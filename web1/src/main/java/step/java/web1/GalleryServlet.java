package step.java.web1;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

         */

        req.getRequestDispatcher("gallery.jsp")
                .forward(req,resp);
    }
}

/*
    Галерея
    Отображает картинки и описания к ним.
    Поддерживает возможность загрузки новых изображений.
 */
