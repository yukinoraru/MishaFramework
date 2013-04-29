package jp.recruit.bootcamp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.recruit.bootcamp.helper.DebugHelper;

public class WelcomeController extends ControllerAbstract{

    private static final long serialVersionUID = 1L;

    @Override
    public String processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        DebugHelper.out("Hello, This is Welcome Controller.");

        setPageAttribute("title", "んふんふ");
        setPageAttribute("jsp", "welcome.jsp");

        return null;
    }

}
