package kz.epam.news.filter;

import kz.epam.news.exception.WrongDataException;

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

        switch (part.getContentType()) {
            case "image/jpeg":
                filterChain.doFilter(request, response);
            case "image/png":
                filterChain.doFilter(request, response);
            case "image/jpg":
                filterChain.doFilter(request, response);
            default:
                request.getSession().setAttribute("error", "Wrong file extension: " + part.getContentType());
                response.sendRedirect(request.getContextPath() + "/error");
        }
    }

    @Override
    public void destroy() {

    }
}
