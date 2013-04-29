package jp.recruit.bootcamp.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestController extends ControllerAbstract{

	@Override
	public String processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    
	    out.print("I'm TestController. <br>");
	    out.print(String.format("request-uri = %s", requestURI));
	    
	    out.close();
		return null;
	}

}
