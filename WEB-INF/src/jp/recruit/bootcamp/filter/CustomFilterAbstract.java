package jp.recruit.bootcamp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public abstract class CustomFilterAbstract implements Filter {

    public abstract void execute(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException;

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        // TODO: フィルターの共通処理を記述

        execute(request, response, chain);

        // 後処理
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

}
