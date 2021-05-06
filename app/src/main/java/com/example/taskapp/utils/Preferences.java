package com.example.taskapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    private static String nameText;
    private static SharedPreferences preferences;
    private String txt;

    private static Preferences setInstance = null;

    public Preferences(Context context) {
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public static void saveBoardState() {
        preferences.edit().putBoolean("isShown", true).apply();
    }

    public static void saveNameProfile(String name) {
        preferences.edit().putString("text", name).apply();
    }

    public boolean isBoardShown() {
        return preferences.getBoolean("isShown", false);
    }

    public static Preferences getInstance() {
        return setInstance;
    }

    public static Preferences getInstance(Context context) {
        return setInstance = new Preferences(context);
    }

    public String getTxt() {
        return preferences.getString("text", null);
    }
}
