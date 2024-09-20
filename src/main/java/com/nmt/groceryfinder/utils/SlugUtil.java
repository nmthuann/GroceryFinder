package com.nmt.groceryfinder.utils;

import java.text.Normalizer;

/**
 * @author LENOVO
 * @project ecommerce-spring-boot-be
 * @date 9/8/2024
 */

public class SlugUtil {
    public static String createSlug(String productName) {
        if (productName == null) {
            return "";
        }

        // Chuyển thành chữ thường
        String slug = productName.toLowerCase();

        // Loại bỏ dấu và ký tự đặc biệt
        slug = Normalizer.normalize(slug, Normalizer.Form.NFD);
        slug = slug.replaceAll("[^\\p{ASCII}]", "");

        // Thay thế ký tự đặc biệt và khoảng trắng bằng dấu gạch ngang
        slug = slug.replaceAll("[^a-z0-9]+", "-");

        // Loại bỏ dấu gạch ngang liên tiếp
        slug = slug.replaceAll("-+", "-");

        // Loại bỏ dấu gạch ngang ở đầu và cuối
        slug = slug.replaceAll("^-|-$", "");

        return slug;
    }
}
