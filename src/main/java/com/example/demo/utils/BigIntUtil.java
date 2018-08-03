package com.example.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Zhao Guowei on 2018/7/22.
 */
public class BigIntUtil {
    private static long tmpID = 0;

    private static boolean tmpIDlocked = false;

    public static long getUniqueId() {
        long ltime = 0;
        while (true) {
            if (tmpIDlocked == false) {
                tmpIDlocked = true;
                ltime = Long.valueOf(new SimpleDateFormat("yyMMddhhmmssSSS")
                        .format(new Date()).toString()) * 10000;
                if (tmpID < ltime) {
                    tmpID = ltime;
                } else {
                    tmpID = tmpID + 1;
                    ltime = tmpID;
                }
                tmpIDlocked = false;
                return ltime;
            }
        }
    }
}
