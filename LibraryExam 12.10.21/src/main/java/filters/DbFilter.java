package filters;

import org.json.JSONObject;
import utils.Db;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@WebFilter("/*")
public class DbFilter implements Filter {
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // Filter path is "/*", so all requests pass through
        // this filter. Suck as .css, .js, etc.
        String req = ((HttpServletRequest) servletRequest).getRequestURI();
        for (String ext : new String[] {".css", ".js", ".jsp"}) {
            if (req.endsWith("")) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        // Real file path - stores in Servlet Context
        String path =
                ((HttpServletRequest) servletRequest)
                .getSession()
                .getServletContext()
                .getRealPath("/WEB-INF/");
        // System.out.println(path);
        File config = new File(path + "config.json");
        if (config.exists()) {
            // read file-config content to byte[]
            int len = (int) config.length();
            byte[] buf = new byte[len];
            try (InputStream reader = new FileInputStream(config)) {
                if (len != reader.read(buf)) {
                    throw new IOException("File integrity check error");
                }
                // Extract JSON
                JSONObject json = new JSONObject(
                        new String(buf)
                );
                // Test connection
                if (Db.setConnection(json)) {
                    // Check for Books table
                    if (Db.getBookOrm().isTableExists()) {
                        filterChain.doFilter(servletRequest, servletResponse);
                    } else {
                        // Install page
                        servletRequest
                                .getRequestDispatcher("/install.jsp")
                                .forward(servletRequest, servletResponse);
                    }
                } else {
                    // Show static page
                    // No connection - use static mode
                    servletRequest
                            .getRequestDispatcher("/static.jsp")
                            .forward(servletRequest, servletResponse);
                }

                return;
            } catch (IOException ex) {
                System.err.println("DbFilter: " + ex.getMessage());
            }
        }

        System.err.println("Config not found");
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}

/*
    Фильтр проверяет, есть ли таблица Books.
    Если нет, то переходит на страницу install.jsp, на которой есть кнопка "Создать БД"
 */
