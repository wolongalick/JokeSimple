package com.yyhd.joke.utils;

/**
 * 功能:
 * 作者: 赵贺
 * 日期: 2017/11/6
 * 备注:
 */
public class StringDBCUtil {
    public static String toDBC(String str) {
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375) {
                c[i] = (char) (c[i] - 65248);
            }

        }
        return new String(c);
    }
}
