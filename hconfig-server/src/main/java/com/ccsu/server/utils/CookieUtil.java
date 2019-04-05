package com.ccsu.server.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author ZhangHang
 * @date 2018-01-31 18:28
 * *****************
 * function:
 */
public class CookieUtil {

    public static Cookie getCookie(HttpServletRequest request, String value) {
        Cookie[] cookies = request.getCookies();
        if (Objects.nonNull(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(value)) {
                    return cookie;
                }
            }
            return null;
        } else {
            return null;
        }
    }
}
