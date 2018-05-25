package com.daniel.security.web.filter;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

/**
 * on 5/25/2018.
 *
 * 有@Component的Filter
 */
//@Component
public class TimeFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("timeFilter init ...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("timeFilter start ...");

        long start = new Date().getTime();
        chain.doFilter(request, response);
        System.out.printf("timeFilter execute : %d%n", new Date().getTime() - start);

        System.out.println("timeFilter finish ...");
    }

    @Override
    public void destroy() {
        System.out.println("timeFilter destroy ...");
    }
}
