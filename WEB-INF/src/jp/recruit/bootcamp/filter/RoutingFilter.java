package jp.recruit.bootcamp.filter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jp.recruit.bootcamp.controller.ControllerAbstract;
import jp.recruit.bootcamp.helper.DebugHelper;

public class RoutingFilter extends CustomFilterAbstract {

    private ArrayList<Route> _routes = new ArrayList<Route>();

    public RoutingFilter() {

        _routes.add(new Route("/MishaFramework/welcome/*.*",
                "jp.recruit.bootcamp.controller.WelcomeController"));

        _routes.add(new Route("/MishaFramework/test/*.*",
                "jp.recruit.bootcamp.controller.NoJSPController"));

        _routes.add(new Route("/MishaFramework/plain",
                "jp.recruit.bootcamp.controller.PlainPageController"));

    }

    @Override
    public void execute(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest requestHttp = (HttpServletRequest) request;
        String requestURI = requestHttp.getRequestURI();

        // routing
        try {
            for (Route r : _routes) {
                if (requestURI.matches(r.getPattern())) {
                    DebugHelper.out(String
                                    .format("route matched: (requestURI = [%s], pattern = [%s]) -> [%s]",
                                            requestURI, r.getPattern(),
                                            r.getController()));
                    try {

                        // delegate request to the controller
                        String className = r.getController();
                        Class<?> classForName = Class.forName(className);

                        Class<?>[] mp = { HttpServletRequest.class,
                                HttpServletResponse.class };
                        ControllerAbstract instance = (ControllerAbstract) classForName
                                .newInstance();

                        Method m = classForName.getMethod(
                                "beforeProcessRequest", mp);

                        m.invoke(instance, request, response);

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }

                    return;
                }
            }
        } catch (IllegalStateException e) {
            System.out.println("むりぽ");
            e.printStackTrace();
        }
        chain.doFilter(request, response);

    }

}
