package jp.recruit.bootcamp.helper;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletRequest;

import org.apache.log4j.Logger;
import jp.recruit.bootcamp.ApplicationResource;

public class DebugHelper {

    // 呼び出し元のクラス名を反映したloggerを取得
    // 低速なので注意
    private static Logger getLogger() {
        final Throwable t = new Throwable();
        final StackTraceElement methodCaller = t.getStackTrace()[2];
        final Logger logger = Logger.getLogger(methodCaller.getClassName());
        logger.setLevel(ApplicationResource.LOGLEVEL);
        return logger;
    }

    public static void out(String format, final Object... o) {
        getLogger().debug(String.format(format, o));
    }

    public static void out(String msg) {
        getLogger().debug(msg);
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
        getLogger().error(msg);
    }

    public static void fatal(String msg) {
        getLogger().fatal(msg);
    }

    public static void info(String msg) {
        getLogger().info(msg);
    }
}
