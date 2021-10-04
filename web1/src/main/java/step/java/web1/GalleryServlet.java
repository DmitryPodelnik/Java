package step.java.web1;

import javax.servlet.ServletException;
import javax.servlet.http.*;
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
                2. <form method="post"enctype="multipart/form-data" ... />
                3. конфиг. сервлета :
                    а) <multipart-config />
                    б) @MultipartConfig
         */

        req.getRequestDispatcher("gallery.jsp")
                .forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart("galleryfile"); // имя <input type="file" />
        HttpSession session = req.getSession();

        /*
            Submitted file name
            a) .getSubmittedFileName()
            b) extract from header:
                content-disposition: form-daya; name="galleryfile"; filename="imp.png"

         */

        session.setAttribute(
                "uploadMessage",
                filePart.getSubmittedFileName());
        resp.sendRedirect(req.getRequestURI());
    }
}

/*
    Галерея
    Отображает картинки и описания к ним.
    Поддерживает возможность загрузки новых изображений.
 */
