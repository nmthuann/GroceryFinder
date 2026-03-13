package com.nmt.groceryfinder.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 10/4/2024
 */
public class UrlUtil {
    public static String decodeUrl(String encodedUrl) {
        return URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8);
    }
}
