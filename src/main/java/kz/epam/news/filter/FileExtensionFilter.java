package kz.epam.news.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebFilter(
        urlPatterns = "/save"
)
public class FileExtensionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Part part = request.getPart("file");

        if (part.getContentType().equalsIgnoreCase("image/jpeg")
                || part.getContentType().equalsIgnoreCase("image/png")
                    || part.getContentType().equalsIgnoreCase("image/jpg")) {
            filterChain.doFilter(request, response);
        } else {
            request.getSession().setAttribute("error", "Wrong file extension: " + part.getContentType());
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }

    @Override
    public void destroy() {
    }
}
