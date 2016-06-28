package ua.com.mobidev.android.framework.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class UrlUtils {
    public static String encode(String s, String charsetName) {
        try {
            return URLEncoder.encode(s, charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return e.getLocalizedMessage();
        }
    }

    public static String decode(String s, String charsetName) {
        try {
            return URLDecoder.decode(s, charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return e.getLocalizedMessage();
        }
    }






}
