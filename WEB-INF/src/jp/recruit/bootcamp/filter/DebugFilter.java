package jp.recruit.bootcamp.filter;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import jp.recruit.bootcamp.helper.DebugHelper;

public class DebugFilter extends CustomFilterAbstract {

    @Override
    public void execute(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest requestHttp = (HttpServletRequest) request;
        String requestURI = requestHttp.getRequestURI();

        DebugHelper.out("REQUEST-URI = %s", requestURI);
        DebugHelper.out("REQUEST-PARAMETERS = %d", request.getParameterMap()
                .size());

        Enumeration<?> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String name = (String) enumeration.nextElement();
            DebugHelper.out("\t%s = %s", name, request.getParameter(name));
        }

    }

}
