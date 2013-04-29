package jp.recruit.bootcamp.filter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.recruit.bootcamp.Route;
import jp.recruit.bootcamp.controller.ControllerAbstract;

public class RoutingFilter implements Filter {

	private ArrayList<Route> _routes = new ArrayList<Route>();

	@Override
	public void destroy() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest requestHttp = (HttpServletRequest) request;
		String requestURI = requestHttp.getRequestURI();

		// routing
		try {
			for (Route r : _routes) {
				if (requestURI.matches(r.getPattern())) {
					System.out
							.println(String
									.format("route matched: (requestURI = [%s], pattern = [%s]) -> [%s]",
											requestURI, r.getPattern(),
											r.getController()));
					try {
						String className = r.getController();
						Class<?> classForName = Class.forName(className);

						// パラメーターとしてperformメソッドに渡すClassオブジェクトの配列mpを生成
						// 第一引数はアクション名のあとの拡張パス情報
						Class<?>[] mp = { HttpServletRequest.class,
								HttpServletResponse.class };
						// ClassオブジェクトからnewInstanceメソッドでActionクラスのオブジェクトを生成
						ControllerAbstract instance = (ControllerAbstract) classForName
								.newInstance();

						// 取得したActionクラスのMethodオブジェクトを取得
						Method m = classForName.getMethod("beforeProcessRequest", mp);

						// Methodオブジェクトのinvokeメソッドでperformメソッドを実行する
						// 第1引数がActionクラスのインスタンス、第2引数以降がperfomメソッドに渡すパラメーター
						m.invoke(instance, request,
								response);

					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}

					return;
				}
			}
		} catch (IllegalStateException e) {
			System.out.println("むりぽ");
			e.printStackTrace();
		}
		chain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

		_routes.add(new Route("/FrontController/welcome/*.*",
				"jp.recruit.bootcamp.controller.WelcomeController"));
		_routes.add(new Route("/FrontController/test/*.*",
				"jp.recruit.bootcamp.controller.TestController"));

	}

}
