package com.example.myapplication.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

/**
 * 字符串工具类
 * Created by guizhen on 16/9/19.
 */
public class StrUtil {

    /**
     * 字符串为null，或长度为0
     *
     * @param cs
     * @return
     */
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * 字符串不为 null,长度也不为0
     *
     * @param cs
     * @return
     */
    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * 字符串判断是否为空
     *
     * @param value
     * @return
     */
    public static boolean valueIsEmpty(Activity activity, String value, String title) {
        if (TextUtils.isEmpty(value)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean valueIsZero(Activity activity, int value, String title) {
        if (value == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static Long longValue(String str) {
        if (StrUtil.isEmpty(str)) return 0L;
        try {
            return Long.valueOf(str);
        } catch (Throwable t) {
            return 0L;
        }
    }

    public static void copyToClipboard(Context context, String text) {
        copyToClipboard(context, text, "smm_copy_text");
    }

    public static void copyToClipboard(Context context, String text, String label) {
        ClipboardManager myClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData myClip = ClipData.newPlainText(label, text);
        myClipboard.setPrimaryClip(myClip);
    }

    public static String join(CharSequence var0, CharSequence... var1) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < var1.length; i++) {
            sb.append(var1[i]);
            if (i != var1.length - 1) {
                sb.append(var0);
            }
        }
        return sb.toString();
    }
}
