package jp.recruit.bootcamp.filter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jp.recruit.bootcamp.ApplicationResource;
import jp.recruit.bootcamp.controller.ControllerAbstract;
import jp.recruit.bootcamp.helper.DebugHelper;

/**
 * リクエストのルーティングを行う。<br>
 * 適切なコントローラ#アクションにリクエストのすべてを委譲する。
 *
 * @see Route
 * @see RootFilter
 */
public class RoutingFilter extends CustomFilterAbstract {

    private ArrayList<Route> _routes;

    @Override
    public void init(FilterConfig config) {

        // ルーティングテーブルの読み込み
        String routeConfigPath = config.getServletContext().getRealPath(
                ApplicationResource.ROUTE_CONFIG);
        _routes = Route.loadRoutes(routeConfigPath);
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
                    DebugHelper
                            .out("route matched: (requestURI = [%s], pattern = [%s]) -> [%s#%s]",
                                    requestURI, r.getPattern(),
                                    r.getController(), r.getAction());
                    try {

                        // delegate request to the controller
                        String className = r.getController();
                        Class<?> classForName = Class.forName(className);

                        Class<?>[] mp = { String.class,
                                HttpServletRequest.class,
                                HttpServletResponse.class };
                        ControllerAbstract instance = (ControllerAbstract) classForName
                                .newInstance();

                        Method m = classForName.getMethod("callAction", mp);

                        m.invoke(instance, r.getAction(), request, response);

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
            DebugHelper.fatal("Failed to routing.");
            e.printStackTrace();
        }
        chain.doFilter(request, response);
    }

}
