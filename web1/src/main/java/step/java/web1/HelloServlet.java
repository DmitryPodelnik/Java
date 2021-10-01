package step.java.web1;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
                      throws IOException {
        response.setContentType("text/html");

        try {
            request
                    .getRequestDispatcher("hello_view.jsp")
                    .forward(request, response);
        } catch (ServletException ex) {
            System.out.println(ex.getMessage());
            // Hello
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h1>" + message + "</h1>");
            out.println("</body></html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cellphone = req.getParameter("cellular");
        String username = req.getParameter("username");

        String cellphoneMessage = "";
        if (cellphone == null || cellphone.isEmpty()) {
            cellphoneMessage = "Cellphone cannot be empty";
        }

        resp.sendRedirect(req.getRequestURI());
        // Клиент получит ответ со статусом 30х и Location: тот же адрес - Browser отправит запрос
        // на этот адрес методом GET !!!

        // !! На запросы POST нельзя строить View
        /*
        req.setAttribute("cellphoneMessage", cellphoneMessage);
        try {
            req.getRequestDispatcher("hello_view.jsp")
               .forward(req, resp);
        } catch (ServletException ex) {
            System.out.println(ex.getMessage());
        }
         */
    }

    public void destroy() {
    }
}