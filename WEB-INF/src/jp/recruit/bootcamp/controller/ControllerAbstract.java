package jp.recruit.bootcamp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class ControllerAbstract extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected String requestURI = null;
    protected HttpSession session = null;
    protected PrintWriter out = null;
    protected List<String> errors = null;
    protected Map<String, String> page = null;

    // beforeProcessRequestの中から呼び出される。ほかはだめぽ
    public abstract String processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException;

    protected void render(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        // FIXME: 良いレイアウトの指定方法はないかや
        String layout = page.get("layout");
        if (layout != null) {
            RequestDispatcher rd = request.getRequestDispatcher(layout);
            rd.forward(request, response);
        }

        return;
    }

    protected void setPageAttribute(String key, String value) {
        if (key.equals("jsp")) {
            String layoutFullPath = "/WEB-INF/views/" + value;
            page.put("jsp", layoutFullPath);
        }
        else if (key.equals("layout")) {
            if (value != null) {
                String layoutFullPath = "/WEB-INF/views/layout/" + value;
                page.put("layout", layoutFullPath);
            }
            else{
                page.put("layout", null);
            }
        } else {
            page.put(key, value);
        }
    }

    public void beforeProcessRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        // 前処理: パラメータとかの作成
        requestURI = request.getRequestURI();
        session = request.getSession(true);
        out = response.getWriter();
        errors = new LinkedList<String>();

        //
        page = new HashMap<String, String>();
        setPageAttribute("layout", "basic-layout.jsp");

        // リクエストの処理
        processRequest(request, response);

        // 後処理: なんか共通のあれ
        session.setAttribute("_errors", errors);
        request.setAttribute("_page", page);

        render(request, response);
        out.close();

        return;
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        beforeProcessRequest(request, response);
        return;
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        beforeProcessRequest(request, response);
        return;
    }

}
