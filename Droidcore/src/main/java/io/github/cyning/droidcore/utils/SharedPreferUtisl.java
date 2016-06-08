package io.github.cyning.droidcore.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Cyning
 * @since 2015.10.21
 * Time    9:56 AM
 * Desc    <p>SharedPreferences的工具类</p>
 */

public class SharedPreferUtisl {

    public static void putString(Context context, String spName, String key, String value) {
        SharedPreferences sPreferences = context.getSharedPreferences(spName, 0);
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Context context, String spName, String key) {
        SharedPreferences sPreferences = context.getSharedPreferences(spName, 0);
        return sPreferences.getString(key, "");
    }

    public static void putLong(Context context, String spName, String key, long value) {
        SharedPreferences sPreferences = context.getSharedPreferences(spName, 0);
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static long getLong(Context context, String spName, String key) {
        SharedPreferences sPreferences = context.getSharedPreferences(spName, 0);
        return sPreferences.getLong(key, 0L);
    }

    public static void putInt(Context context, String spName, String key, int value) {
        SharedPreferences sPreferences = context.getSharedPreferences(spName, 0);
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static int getInt(Context context, String spName, String key) {
        SharedPreferences sPreferences = context.getSharedPreferences(spName, 0);
        return sPreferences.getInt(key, 0);
    }

    public static void putBool(Context context, String spName, String key, boolean value) {
        SharedPreferences sPreferences = context.getSharedPreferences(spName, 0);
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBool(Context context, String spName, String key, boolean defaultValue) {
        SharedPreferences sPreferences = context.getSharedPreferences(spName, 0);
        return sPreferences.getBoolean(key, defaultValue);
    }

    public static SharedPreferences getSharedPreferences(Context context, String spName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, 0);
        return sharedPreferences;
    }

    public static boolean removeFromSharedPreferences(Context context, String spName, String key) {
        SharedPreferences sPreferences = context.getSharedPreferences(spName, 0);
        boolean result = sPreferences.edit().remove(key).commit();
        return result;
    }

    public static void clearAllDataInSP(Context context, String spName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(spName, 0);
        sharedPreferences.edit().clear().commit();
    }
}
