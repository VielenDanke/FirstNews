package kz.epam.news.config.web;

import kz.epam.news.config.HibernateConfig;
import kz.epam.news.config.security.SecurityConfig;
import kz.epam.news.filter.CharsetFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import java.io.File;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static final Integer MAX_UPLOAD_SIZE_IN_MB = 5 * 1024 * 1024;

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{WebConfig.class, HibernateConfig.class, SecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new CharsetFilter()};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {

        // For upload temporary files
        File uploadDirectory = new File(System.getProperty("java.io.tmpdir"));

        // Registration a MultipartConfigElement
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(
                uploadDirectory.getAbsolutePath(),
                MAX_UPLOAD_SIZE_IN_MB,
                MAX_UPLOAD_SIZE_IN_MB * 2,
                MAX_UPLOAD_SIZE_IN_MB / 2
        );

        registration.setMultipartConfig(multipartConfigElement);
    }
}
