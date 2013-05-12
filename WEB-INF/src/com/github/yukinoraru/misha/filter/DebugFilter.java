package com.github.yukinoraru.misha.filter;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.github.yukinoraru.misha.helper.DebugHelper;

/**
 * リクエスト情報を出力する。<br>
 * リクエストパラメータも併せて表示する
 *
 * @see RootFilter
 * @see CustomFilterAbstract
 */
public class DebugFilter extends CustomFilterAbstract {

    @Override
    public void execute(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest requestHttp = (HttpServletRequest) request;
        String requestURI = requestHttp.getRequestURI();

        DebugHelper.out("REQUEST-URI = %s", requestURI);
        DebugHelper.out("REQUEST-PARAMETERS = %d", request.getParameterMap()
                .size());

        printRequestParameters(request);
    }

    public static void printRequestParameters(ServletRequest request) {
        Map<?, ?> params = request.getParameterMap();
        Iterator<?> i = params.keySet().iterator();

        while (i.hasNext()) {
            String key = (String) i.next();
            String value = ((String[]) params.get(key))[0];
            DebugHelper.out("\tPARAMS[\"%s\"] => %s", key, value);
        }
    }

}
