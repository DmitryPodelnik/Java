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

        }
    }

    public void destroy() {
    }
}