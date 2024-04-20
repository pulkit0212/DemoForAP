package com.ap.demo.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;

import com.ap.demo.App;

import java.util.Objects;

public class PrefManager {

    private static SharedPreferences sharedPreferences;

    public static void putString(String key, String val) {
        init();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, val);
        editor.apply();
    }

    public static String getString(String key) {
        init();
        return sharedPreferences.getString(key, "");
    }


    public static void putInt(String key, Integer val) {
        init();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, val);
        editor.apply();
    }


    public static Integer getInt(String key) {
        init();
        return sharedPreferences.getInt(key, 0);
    }

    public static void putBoolean(String key, boolean val) {
        init();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, val);
        editor.apply();
    }

    public static boolean getBoolean(String key) {
        init();
        return sharedPreferences.getBoolean(key, false);
    }

    public static SharedPreferences init() {
        sharedPreferences = Objects.requireNonNull(App.getInstance()).getSharedPreferences("TheLionBook", MODE_PRIVATE);
        return sharedPreferences;
    }

    public static void clear() {
        init();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
