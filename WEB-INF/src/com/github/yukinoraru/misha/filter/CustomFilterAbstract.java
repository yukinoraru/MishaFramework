package com.github.yukinoraru.misha.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public abstract class CustomFilterAbstract implements Filter {

    public abstract void execute(ServletRequest request,
            ServletResponse response, FilterChain chain) throws IOException,
            ServletException;

    protected FilterConfig config;

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        execute(request, response, chain);
    }

    @Override
    public void init(FilterConfig config) {
        this.config = config;
    }
}
