package jp.recruit.bootcamp;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.PropertyConfigurator;

public class Log4jConfigListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent event) {
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();

        // XXX: should be unique name
        System.setProperty("Log4jSavePath.MF", context.getRealPath("/"));

        String log4jConfigPath = context
                .getRealPath(ApplicationResource.LOG4J_CONFIG);
        PropertyConfigurator.configure(log4jConfigPath);
    }

}
