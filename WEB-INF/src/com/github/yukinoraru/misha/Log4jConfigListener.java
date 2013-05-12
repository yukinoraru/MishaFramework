package com.github.yukinoraru.misha;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.PropertyConfigurator;

/**
 * Log4Jの設定を行うリスナークラス<br>
 * プロジェクトの相対パスを格納した変数を設定しているので、<br>
 * Log4Jの設定ファイル内でログファイルをプロジェクト内の相対パスに出力することができる。
 * @see ApplicationResource.LOG4J_ENV_VARNAME
 */
public class Log4jConfigListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent event) {
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();

        System.setProperty(ApplicationResource.LOG4J_ENV_VARNAME, context.getRealPath("/"));

        String log4jConfigPath = context
                .getRealPath(ApplicationResource.LOG4J_CONFIG);
        PropertyConfigurator.configure(log4jConfigPath);
    }

}
