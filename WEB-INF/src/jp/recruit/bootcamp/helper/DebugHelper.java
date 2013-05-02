package jp.recruit.bootcamp.helper;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletRequest;
import org.apache.log4j.Logger;
import jp.recruit.bootcamp.ApplicationResource;

public class DebugHelper {

    // TODO: 呼び出し元のクラス名を元に作成する
    private static final Logger logger = Logger.getLogger("DebugHelper");

    public static void out(String msg) {
        if (ApplicationResource.isDebugMode()) {
            logger.debug(msg);
        }
    }

    public static void out(Object obj) {
        out(obj.toString());
    }

    public static void out(Object[] array) {
        print_r(array);
    }

    public static void printRequestParameters(ServletRequest request) {
        Map<?, ?> params = request.getParameterMap();
        Iterator<?> i = params.keySet().iterator();

        while (i.hasNext()) {
            String key = (String) i.next();
            String value = ((String[]) params.get(key))[0];
            out(String.format("params[%s] = %s", key, value));
        }
    }

    public static void printr(Object[] array) {
        out(Arrays.toString(array));
    }

    public static void print_r(Object[] array) {
        out(Arrays.deepToString(array));
    }

    public static void error(String msg) {
        logger.error(msg);
    }

    public static void fatal(String msg) {
        logger.fatal(msg);
    }

    public static void info(String msg) {
        logger.info(msg);
    }
}
