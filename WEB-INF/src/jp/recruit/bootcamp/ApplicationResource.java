package jp.recruit.bootcamp;

import org.apache.log4j.Level;

public class ApplicationResource {

	public static final Level LOGLEVEL = Level.ALL;

	public static final String LAYOUT_VIEW_PATH = "/WEB-INF/views/";
	public static final String LAYOUT_LAYOUT_PATH = LAYOUT_VIEW_PATH + "layout/";


	public static final String LAYOUT_STYLE_BASIC = "basic-layout.jsp";
	public static final String LAYOUT_STYLE_PLAIN = "plain-layout.jsp";
	public static final String LAYOUT_DEFAULT = LAYOUT_STYLE_BASIC;


}
