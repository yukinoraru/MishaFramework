package jp.recruit.bootcamp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WelcomeController extends ControllerAbstract{

	@Override
	public String processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out.println("Hello, This is Welcome Controller.");
		
		request.setAttribute("page.title", "んふんふ");
		
		render(request, response, "welcome.jsp");
		return null;
	}

}
