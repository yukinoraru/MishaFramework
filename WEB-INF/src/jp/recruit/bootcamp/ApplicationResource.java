package jp.recruit.bootcamp;

import org.apache.log4j.Level;

/**
 *　Define Common Constants in Application
 */
public class ApplicationResource {


    /**
     * Set Logging level for DebugHelper
     */
    public static final Level LOGLEVEL = Level.ALL;
    public static final String LOG4J_ENV_VARNAME = "Log4jSavePath.MF"; // should be unique in System

    public static final String ROUTE_CONFIG = "/WEB-INF/conf/routes.xml";
    public static final String LOG4J_CONFIG = "/WEB-INF/conf/mf-log4j.properties";

    public static final String LAYOUT_VIEW_PATH = "/WEB-INF/views/";
    public static final String LAYOUT_LAYOUT_PATH = LAYOUT_VIEW_PATH
            + "layout/";

    public static final String LAYOUT_STYLE_BASIC = "basic-layout.jsp";
    public static final String LAYOUT_STYLE_PLAIN = "plain-layout.jsp";
    public static final String LAYOUT_DEFAULT = LAYOUT_STYLE_BASIC;

}
