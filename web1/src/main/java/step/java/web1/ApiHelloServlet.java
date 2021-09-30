package step.java.web1;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import step.java.web1.models.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/hello")
public class ApiHelloServlet
        extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {
        resp.getWriter().print(
                // "Hello from API"
                toStringFromJson(
                        new Book(
                        "Stainback",
                        "Grapes of Wrath"
                        ).toJsonString()
                )
        );

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("Hello from POST API");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("Hello from PUT API");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("Hello from DELETE API");
    }

    private String toStringFromJson(String jsonString) {
        JSONParser jsonParser = new JSONParser();
        JSONObject obj = new JSONObject();

        try {
            obj = (JSONObject) jsonParser.parse(jsonString);
            return "Title: " + obj.get("title").toString() + "\nAuthor: " + obj.get("author").toString();
        } catch (ParseException ignore) {

        }
        return null;
    }
}
