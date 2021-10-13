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
import java.sql.Connection;
import java.sql.DriverManager;

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
            if (req.endsWith(ext)) {
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
                } else if (!Db.setConnection(json)) {
                    try {
                        String url = "jdbc:mysql://localhost:3306/J181?useUnicode=true&characterEncoding=UTF-8"
                                     + "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
                        String username = "root";
                        String password = "password";
                        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                        try (Connection conn = DriverManager.getConnection(url, username, password)) {
                            System.out.println("Connection to MySQL BookDB successful!");


                        }
                    } catch (Exception ex) {
                        System.out.println("Connection to MySQL BookDB failed...");
                        System.out.println(ex.getMessage());
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
