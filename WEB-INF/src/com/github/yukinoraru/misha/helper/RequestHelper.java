package com.github.yukinoraru.misha.helper;

import java.io.UnsupportedEncodingException;

public class RequestHelper {
    public static String enforceUTF8(String str)
            throws UnsupportedEncodingException {
        return new String(str.getBytes("ISO8859_1"), "UTF-8");
    }

}
