package com.vaish.filter_interceptor_web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filer--> request routing");
        HttpServletRequest servletRequest1= (HttpServletRequest) servletRequest;
        System.out.println(servletRequest1.getRequestURI());
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("Filter--> After processing");
    }
}
