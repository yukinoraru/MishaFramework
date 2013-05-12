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
import jp.recruit.bootcamp.filter.RoutingFilter;

/**
 * コントローラーの抽象クラス
 * コントローラーは基本的にこのクラスを継承する
 */
public abstract class ControllerAbstract extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     *  setPageAttributeで用いる共通名群、以下の共通名が用意されている。
     *  <ul compact>
     *  <li>JSP : {@value #JSP}と等価</li>
     *  <li>TITLE : {@value #TITLE}と等価</li>
     *  <li>LAYOUT : {@value #LAYOUT}と等価</li>
     *  </ul>
     */
    protected static final String JSP = "jsp", TITLE = "title",
            LAYOUT = "layout", ERRORS = "_errors", PAGE = "_page";

    protected String requestURI = null;
    protected HttpSession session = null;
    protected PrintWriter out = null;
    protected List<String> errors = null;
    protected Map<String, String> page = null;


    /**
     * page.layoutを元にページを描画する。<br>
     * 通常は次のメソッドから呼び出される。{@link ControllerAbstract#callAction} <br>
     * なおpage.layoutがnullの場合はこのメソッドは何もしない。
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void render(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String layout = page.get(LAYOUT);
        if (layout != null) {
            RequestDispatcher rd = request.getRequestDispatcher(layout);
            rd.forward(request, response);
        }
        return;
    }

    /**
     * page変数に属性を設定する。<br>
     * 基本的な動作はpage.put(key, value)と等しいが、<br>
     * keyにLAYOUTもしくはJSPを指定した場合に<br>
     * 自動的にレイアウトのパスを修正する。<br>
     * 例:) <br>
     * <code>setPageAttribute(LAYOUT, "plain-layout.jsp");</code><br>
     * というコードは、<br>
     * <code>setPageAttribute("layout", "/WEB-INF/views/layout/plain-layout.jsp");</code><br>
     * に等しい。
     * @see ControllerAbstract#LAYOUT
     * @see ControllerAbstract#JSP
     * @param key パラメータの名前。"hoge"を指定するとJS側では${_page.hoge}として使用できる。
     * @param value パラメータの中身
     */
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

    /**
     * actionにリクエストのすべてを委譲する。<br>
     * つまり継承先のコントローラ内のactionというメソッドを呼び出す。<br>
     * それに伴いすべてのactionで共通となる前/後処理も行う。<br>
     * {@link RoutingFilter#execute} からの呼び出し
     * @param action アクション名
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws SecurityException
     * @throws NoSuchMethodException
     */
    public void callAction(String action, HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException,
            IllegalArgumentException, IllegalAccessException,
            InvocationTargetException, SecurityException, NoSuchMethodException {

        // 共通前処理: パラメータの作成
        requestURI = request.getRequestURI();
        session = request.getSession(true);
        out = response.getWriter();
        errors = new LinkedList<String>();
        page = new HashMap<String, String>();

        // レイアウトテンプレートのデフォルト値を設定
        setPageAttribute(LAYOUT, ApplicationResource.LAYOUT_DEFAULT);

        // 指定リクエストを指定アクションへ委譲
        Class<?>[] mp = { HttpServletRequest.class, HttpServletResponse.class };
        Method m = this.getClass().getMethod(action, mp);
        m.invoke(this, request, response);

        // 後処理: セッションもしくはリクエストスコープに変数のセット
        session.setAttribute(ERRORS, errors);
        request.setAttribute(PAGE, page);

        render(request, response);
        out.close();

        return;
    }
}
