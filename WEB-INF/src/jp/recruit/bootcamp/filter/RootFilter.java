package jp.recruit.bootcamp.filter;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class RootFilter implements Filter {

    private Vector<CustomFilterAbstract> customFilters = new Vector<CustomFilterAbstract>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        addFilter(new DebugFilter());
        addFilter(new EncodingFilter());
        addFilter(new RoutingFilter());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        CustomFilterAbstract filter;

        // apply filters
        Iterator<CustomFilterAbstract> filters = customFilters.iterator();
        while (filters.hasNext()) {
            filter = (CustomFilterAbstract) filters.next();
            filter.doFilter(request, response, chain);
        }

        // chain if response has not committed
        if (!response.isCommitted()) {
            chain.doFilter(request, response);
        }
    }

    private void addFilter(CustomFilterAbstract filter) {
        customFilters.add(filter);
    }

    @Override
    public void destroy() {

    }

}
