package jp.recruit.bootcamp.controller;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PlainPageController extends ControllerAbstract {

    private static final long serialVersionUID = 1L;

    @Override
    public String processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        if ((new Random()).nextBoolean()) {
            errors.add("いっぱい");
            errors.add("エラーが");
            errors.add("発生した");
            errors.add("とき");
        }

        setPageAttribute("layout", "plain-layout.jsp");
        setPageAttribute("jsp", "plain.jsp");

        return null;
    }

}
