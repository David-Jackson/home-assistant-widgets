package fyi.jackson.drew.homeassistantwidgets.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

public class PrefUtils {

    public static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreferences.Editor getPreferenceEditor(Context context) {
        return getPreferences(context).edit();
    }

    public static void setString(String prefName, String prefValue, Context context) {
        getPreferenceEditor(context).putString(prefName, prefValue).apply();
    }

    public static String getString(String prefName, @Nullable String defVal, Context context) {
        return getPreferences(context).getString(prefName, defVal);
    }

    public static void setBoolean(String prefName, Boolean prefValue, Context context) {
        getPreferenceEditor(context).putBoolean(prefName, prefValue).apply();
    }

    public static Boolean getBoolean(String prefName, @Nullable Boolean defVal, Context context) {
        return getPreferences(context).getBoolean(prefName, defVal);
    }
}