package com.nmt.groceryfinder.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author LENOVO
 * @project GroceryFinder
 * @date 10/4/2024
 */
public class UrlUtil {
    public static String decodeUrl(String encodedUrl) {
        try {
            return URLDecoder.decode(encodedUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // Xử lý ngoại lệ nếu cần
            throw new RuntimeException("Failed to decode URL", e);
        }
    }
}
