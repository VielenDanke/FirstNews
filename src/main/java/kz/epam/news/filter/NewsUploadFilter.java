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
public class NewsUploadFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Part part = request.getPart("file");
        String section = request.getParameter("section");
        String topic = request.getParameter("topic");
        String shortDescription = request.getParameter("shortDescription");
        String description = request.getParameter("description");

        if ((part.getContentType().equalsIgnoreCase("image/jpeg")
                || part.getContentType().equalsIgnoreCase("image/png")
                    || part.getContentType().equalsIgnoreCase("image/jpg"))
                && !section.isEmpty() && !topic.isEmpty() && !shortDescription.isEmpty() && !description.isEmpty()) {
            filterChain.doFilter(request, response);
        } else {
            request.getSession().setAttribute("error", "error.wrong.file.extension");
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }

    @Override
    public void destroy() {
    }
}
