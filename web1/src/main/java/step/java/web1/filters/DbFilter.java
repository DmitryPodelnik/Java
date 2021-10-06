package step.java.web1.filters;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.servlet.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DbFilter implements Filter {

    private FilterConfig filterConfig;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {

        System.out.println("Filter works");

        File config = new File(
                filterConfig
                        .getServletContext().
                        getRealPath("/WEB-INF/config/")
                        + "/" + "db.json");
        if (!config.exists()) {
            System.err.println("config/db.json not found");
            return;
        }
        try (InputStream reader = new FileInputStream(config)) {
            byte[] buf = new byte[(int) config.length()];
            reader.read(buf);
            JSONObject configData = (JSONObject)
                    new JSONParser().parse(new String(buf));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        filterConfig = null;
    }
}

/*
    Сервлетные фильтры
    Реализация концепции Middleware:
    - Создание "цепочки" последовательных вызовов методов разных классов;
    - Механизм автоматического встраивания новых классов в эту цепочку.
    Для создания фильтра:
    1. Реализуем интерфейс javax.servlet.Filter
    2. Встраиваем фильтр:
        а) через аннотацию @WebFilter
        б) через web.xml
 */
