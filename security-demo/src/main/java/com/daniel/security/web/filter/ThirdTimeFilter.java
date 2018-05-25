package com.daniel.security.web.filter;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

/**
 * on 5/25/2018.
 *
 * 没有@Component的Filter
 */
public class ThirdTimeFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("ThirdTimeFilter init ...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("ThirdTimeFilter start ...");

        long start = new Date().getTime();
        chain.doFilter(request, response);
        System.out.printf("ThirdTimeFilter execute : %d%n", new Date().getTime() - start);

        System.out.println("ThirdTimeFilter finish ...");
    }

    @Override
    public void destroy() {
        System.out.println("ThirdTimeFilter destroy ...");
    }
}
