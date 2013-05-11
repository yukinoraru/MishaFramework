package jp.recruit.bootcamp.controller;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.recruit.bootcamp.helper.DebugHelper;

public class WelcomeController extends ControllerAbstract {

    private static final long serialVersionUID = 1L;

    public String index(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DebugHelper.out("Hello, This is Welcome Controller.");

        setPageAttribute(TITLE, "んふんふ");
        setPageAttribute(JSP, "welcome.jsp");

        return null;
    }

    public String alpha(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DebugHelper.out("THIS IS ALPHA ACTION.");

        setPageAttribute(TITLE, "α");
        setPageAttribute(JSP, "welcome.jsp");

        return null;
    }

    public String beta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DebugHelper.out("THIS IS BETA ACTION.");

        setPageAttribute(TITLE, "β");
        setPageAttribute(JSP, "welcome.jsp");

        return null;
    }

    public String showErrors(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        if ((new Random()).nextBoolean()) {
            errors.add("いっぱい");
            errors.add("エラーが");
            errors.add("発生した");
            errors.add("とき");
        }

        setPageAttribute(LAYOUT, "plain-layout.jsp");
        setPageAttribute(JSP, "plain.jsp");

        return null;
    }

    public String getJSON(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        // output JSON
        response.setContentType("application/json; charset=UTF-8;");
        out.print(String.format("{\"request-uri\": \"%s\", \"key\":\"日本語\"}",
                requestURI));

        setPageAttribute(LAYOUT, null);

        return null;
    }

}
