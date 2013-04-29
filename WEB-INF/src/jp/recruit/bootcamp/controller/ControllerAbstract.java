package jp.recruit.bootcamp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ControllerAbstract extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected String requestURI = null;

    // beforeProcessRequestの中から呼び出される。ほかはだめぽ
    public abstract String processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException;

    protected void render(HttpServletRequest request,
            HttpServletResponse response, String layoutPath)
            throws ServletException, IOException {

        String layoutFullPath = "/WEB-INF/views/" + layoutPath;
        request.setAttribute("page.jsp", layoutFullPath);

        //FIXME: 良いレイアウトの指定方法はないかや
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/layout/basic-layout.jsp");
        rd.forward(request, response);

        return;
    }

    public void beforeProcessRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        // 前処理: パラメータとかの作成
        requestURI = request.getRequestURI();

        // リクエストの処理
        processRequest(request, response);

        // 後処理: なんか共通のあれ

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
