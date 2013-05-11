package jp.recruit.bootcamp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.recruit.bootcamp.ApplicationResource;
import jp.recruit.bootcamp.helper.DebugHelper;

public abstract class ControllerAbstract extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /* パラメータ名 */
    protected static final String JSP = "jsp", TITLE = "title",
            LAYOUT = "layout", ERRORS = "_errors", PAGE = "_page";

    protected String requestURI = null;
    protected HttpSession session = null;
    protected PrintWriter out = null;
    protected List<String> errors = null;
    protected Map<String, String> page = null;

    // beforeProcessRequestの中から呼び出される。ほかはだめぽ
    // public abstract String processRequest(HttpServletRequest request,
    // HttpServletResponse response) throws ServletException, IOException;

    protected void render(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String layout = page.get(LAYOUT);
        if (layout != null) {
            RequestDispatcher rd = request.getRequestDispatcher(layout);
            rd.forward(request, response);
        } else {
            DebugHelper.fatal("layout is null.");
        }
        return;
    }

    protected void setPageAttribute(String key, String value) {
        if (key.equals(JSP)) {
            String layoutFullPath = ApplicationResource.LAYOUT_VIEW_PATH
                    + value;
            page.put(JSP, layoutFullPath);
        } else if (key.equals(LAYOUT)) {
            if (value != null) {
                String layoutFullPath = ApplicationResource.LAYOUT_LAYOUT_PATH
                        + value;
                page.put(LAYOUT, layoutFullPath);
            } else {
                page.put(LAYOUT, null);
            }
        } else {
            page.put(key, value);
        }
    }

    public void callAction(String action, HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException, SecurityException, NoSuchMethodException {

        // 前処理: パラメータとかの作成
        requestURI = request.getRequestURI();
        session = request.getSession(true);
        out = response.getWriter();
        errors = new LinkedList<String>();

        //
        page = new HashMap<String, String>();
        setPageAttribute(LAYOUT, ApplicationResource.LAYOUT_DEFAULT);

        // 指定リクエストを指定アクションへ委譲
        Class<?>[] mp = { HttpServletRequest.class, HttpServletResponse.class };
        Method m = this.getClass().getMethod(action, mp);
        m.invoke(this, request, response);

        // 後処理: なんか共通のあれ
        session.setAttribute(ERRORS, errors);
        request.setAttribute(PAGE, page);

        render(request, response);
        out.close();

        return;
    }
}
