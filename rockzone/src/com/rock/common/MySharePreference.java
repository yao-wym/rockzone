package com.rock.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by wym on 2014/11/8.
 */
public class MySharePreference {
    private static SharedPreferences preference;
    private static SharedPreferences.Editor editor;

    public static SharedPreferences getPreference(Context context) {
        if (preference == null) {
            preference = PreferenceManager.getDefaultSharedPreferences(context);
            editor=preference.edit();
        }
        return preference;
    }
    public static  void putString(Context context,String key, String value) {
            getPreference(context);
        editor.putString(key, value);
        editor.commit();
    }
    public static  void putBoolean(Context context,String key,boolean value) {
        getPreference(context);
        editor.putBoolean(key, value);
        editor.commit();
    }
        public static String getString(Context context,String key){
            getPreference(context);
            String uid = preference.getString(key,"");
            return uid;
    }
    public static boolean getBoolean(Context context,String key){
        getPreference(context);
        return preference.getBoolean(key, false);
    }
    public static void putLong(Context context,String key, long value) {
        getPreference(context);
        editor.putLong(key, value);
        editor.commit();
    }
    public static long getLong(Context context,String key){
        getPreference(context);
        return preference.getLong(key, 0);
    }
    public static void putInt(Context context,String key, int value) {
        getPreference(context);
        editor.putInt(key, value);
        editor.commit();
    }
    public static int getInt(Context context,String key){
        getPreference(context);
        return preference.getInt(key, 0);
    }
    public static void clear(Context context){
        getPreference(context);
        editor.clear();
        editor.commit();
    }
}
