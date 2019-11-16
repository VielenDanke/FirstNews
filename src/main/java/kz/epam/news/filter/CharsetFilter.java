package kz.epam.news.filter;

import javax.servlet.*;
import java.io.IOException;

public class CharsetFilter implements Filter {

    private static final String CONTEXT_TYPE = "text/html";

    public void init(FilterConfig config) {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String encodingToUTF = "UTF-8";
        String contentType = CONTEXT_TYPE + ";charset=utf-8";

        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding(encodingToUTF);
        }

        response.setContentType(contentType);
        response.setCharacterEncoding(encodingToUTF);

        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}
