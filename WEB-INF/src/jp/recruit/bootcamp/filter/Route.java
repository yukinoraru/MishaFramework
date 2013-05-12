package jp.recruit.bootcamp.filter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * ルーティング情報を格納するクラス
 *
 * @see RoutingFilter
 */
public class Route {
    private String pattern, controller, action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Route(String pattern, String controller, String action) {
        super();
        this.pattern = pattern;
        this.controller = controller;
        this.action = action;
    }

    public String getController() {
        return controller;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    @Override
    public String toString() {
        return String.format("[%s] -> %s#%s", pattern, controller, action);
    }

    //
    public static ArrayList<Route> loadRoutes(String filename) {
        ArrayList<Route> routes = new ArrayList<Route>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filename));
            NodeList routeinfo = document.getDocumentElement().getChildNodes();

            // load prefix
            String controllerBasePath = document.getDocumentElement()
                    .getAttribute("controllerBasePath");
            String urlPrefix = document.getDocumentElement().getAttribute(
                    "urlPrefix");

            for (int i = 0, length = routeinfo.getLength(); i < length; i++) {
                Node route = routeinfo.item(i);

                if (!(route instanceof Element)) {
                    continue;
                }

                //
                Element childElement = (Element) route;
                String pattern = childElement.getAttribute("pattern");
                String controller = childElement.getAttribute("controller");
                String action = childElement.getAttribute("action");

                //
                pattern = (urlPrefix != null) ? urlPrefix + pattern : pattern;
                controller = (controllerBasePath != null) ? String.format(
                        "%s.%s", controllerBasePath, controller) : controller;

                routes.add(new Route(pattern, controller, action));
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return routes;
    }
}
