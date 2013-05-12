package com.github.yukinoraru.misha.helper;

import java.util.Arrays;

import org.apache.log4j.Logger;

import com.github.yukinoraru.misha.ApplicationResource;

public class DebugHelper {

    /**
     * 呼び出し元のクラス名を反映したloggerを取得<br>
     * このクラス以外からコールしてはならない<br>
     * また低速であるのでデバッグ時以外は切ること<br>
     */
    private static Logger getLogger() {
        final Throwable t = new Throwable();
        final StackTraceElement methodCaller = t.getStackTrace()[2];
        final Logger logger = Logger.getLogger(methodCaller.getClassName());
        logger.setLevel(ApplicationResource.LOGLEVEL);
        return logger;
    }

    /**
     * <code> DebugHelper.out(String.format(f, o )) </code><br>
     * と等価
     *
     * @see DebugHelper#out(String)
     */
    public static void out(String format, final Object... o) {
        getLogger().debug(String.format(format, o));
    }

    /**
     * DEBUGレベルでmsgを出力する
     *
     * @see DebugHelper#out(String, Object...)
     */
    public static void out(String msg) {
        getLogger().debug(msg);
    }

    public static void out(Object obj) {
        out(obj.toString());
    }

    /**
     * 配列をすべて出力する。
     */
    public static void out(Object[] array) {
        print_r(array);
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
