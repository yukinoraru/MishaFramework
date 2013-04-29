package jp.recruit.bootcamp.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoJSPController extends ControllerAbstract{

    private static final long serialVersionUID = 1L;

    @Override
    public String processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        // output JSON
        response.setContentType("application/json; charset=UTF-8;");
        out.print(String.format("{\"request-uri\": \"%s\", \"key\":\"日本語\"}", requestURI));

        setPageAttribute("layout", null);

        return null;
    }

}
