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

        // Thay thế các chữ cái tiếng Việt thành chữ cái không dấu tương ứng
        slug = replaceVietnameseChars(slug);

        // Loại bỏ dấu và ký tự đặc biệt
        slug = Normalizer.normalize(slug, Normalizer.Form.NFD);
        slug = slug.replaceAll("[^\\p{ASCII}]", "");

        // Thay thế ký tự đặc biệt và khoảng trắng bằng dấu gạch ngang
        slug = slug.replaceAll("[^a-z0-9]+", "-");

        // Loại bỏ dấu gạch ngang liên tiếp
        slug = slug.replaceAll("-+", "-");

        // Loại bỏ dấu gạch ngang ở đầu và cuối
        slug = slug.replaceAll("^-|-$", "");

        slug = "/" + slug;

        return slug;
    }

    private static String replaceVietnameseChars(String input) {
        // Thay thế các chữ cái tiếng Việt có dấu thành chữ cái không dấu tương ứng
        input = input.replace("à", "a").replace("á", "a").replace("ạ", "a").replace("ả", "a").replace("ã", "a")
                .replace("â", "a").replace("ầ", "a").replace("ấ", "a").replace("ậ", "a").replace("ẩ", "a").replace("ẫ", "a")
                .replace("ă", "a").replace("ằ", "a").replace("ắ", "a").replace("ặ", "a").replace("ẳ", "a").replace("ẵ", "a")
                .replace("è", "e").replace("é", "e").replace("ẹ", "e").replace("ẻ", "e").replace("ẽ", "e")
                .replace("ê", "e").replace("ề", "e").replace("ế", "e").replace("ệ", "e").replace("ể", "e").replace("ễ", "e")
                .replace("ì", "i").replace("í", "i").replace("ị", "i").replace("ỉ", "i").replace("ĩ", "i")
                .replace("ò", "o").replace("ó", "o").replace("ọ", "o").replace("ỏ", "o").replace("õ", "o")
                .replace("ô", "o").replace("ồ", "o").replace("ố", "o").replace("ộ", "o").replace("ổ", "o").replace("ỗ", "o")
                .replace("ơ", "o").replace("ờ", "o").replace("ớ", "o").replace("ợ", "o").replace("ở", "o").replace("ỡ", "o")
                .replace("ù", "u").replace("ú", "u").replace("ụ", "u").replace("ủ", "u").replace("ũ", "u")
                .replace("ư", "u").replace("ừ", "u").replace("ứ", "u").replace("ự", "u").replace("ử", "u").replace("ữ", "u")
                .replace("ỳ", "y").replace("ý", "y").replace("ỵ", "y").replace("ỷ", "y").replace("ỹ", "y")
                .replace("đ", "d");

        // Thay thế chữ hoa (nếu có)
        input = input.replace("À", "a").replace("Á", "a").replace("Ạ", "a").replace("Ả", "a").replace("Ã", "a")
                .replace("Â", "a").replace("Ầ", "a").replace("Ấ", "a").replace("Ậ", "a").replace("Ẩ", "a").replace("Ẫ", "a")
                .replace("Ă", "a").replace("Ằ", "a").replace("Ắ", "a").replace("Ặ", "a").replace("Ẳ", "a").replace("Ẵ", "a")
                .replace("È", "e").replace("É", "e").replace("Ẹ", "e").replace("Ẻ", "e").replace("Ẽ", "e")
                .replace("Ê", "e").replace("Ề", "e").replace("Ế", "e").replace("Ệ", "e").replace("Ể", "e").replace("Ễ", "e")
                .replace("Ì", "i").replace("Í", "i").replace("Ị", "i").replace("Ỉ", "i").replace("Ĩ", "i")
                .replace("Ò", "o").replace("Ó", "o").replace("Ọ", "o").replace("Ỏ", "o").replace("Õ", "o")
                .replace("Ô", "o").replace("Ồ", "o").replace("Ố", "o").replace("Ộ", "o").replace("Ổ", "o").replace("Ỗ", "o")
                .replace("Ơ", "o").replace("Ờ", "o").replace("Ớ", "o").replace("Ợ", "o").replace("Ở", "o").replace("Ỡ", "o")
                .replace("Ù", "u").replace("Ú", "u").replace("Ụ", "u").replace("Ủ", "u").replace("Ũ", "u")
                .replace("Ư", "u").replace("Ừ", "u").replace("Ứ", "u").replace("Ự", "u").replace("Ử", "u").replace("Ữ", "u")
                .replace("Ỳ", "y").replace("Ý", "y").replace("Ỵ", "y").replace("Ỷ", "y").replace("Ỹ", "y")
                .replace("Đ", "d");

        return input;
    }
}
