package fyi.jackson.drew.homeassistantwidgets.utils;

import android.content.Context;

import fyi.jackson.drew.homeassistantwidgets.R;

public class StringUtils {

    public static boolean matchesUrlOrIpAddress(String searchStr, Context context) {
        return matchesUrl(searchStr, context) || matchesIpAddress(searchStr, context);
    }

    public static boolean matchesIpAddress(String searchStr, Context context) {
        return matchesIpAddress(searchStr, context, false);
    }

    public static boolean matchesIpAddress(String searchStr, Context context, boolean strictHttps) {
        int httpRegexId = (strictHttps ? R.string.regex_strict_https : R.string.regex_lax_http);
        String regex = context.getString(httpRegexId) + context.getString(R.string.regex_ip);
        return searchStr.matches(regex);
    }

    public static boolean matchesUrl(String searchStr, Context context) {
        return matchesUrl(searchStr, context, false);
    }

    public static boolean matchesUrl(String searchStr, Context context, boolean strictHttps) {
        int httpRegexId = (strictHttps ? R.string.regex_strict_https : R.string.regex_lax_http);
        String regex = context.getString(httpRegexId) + context.getString(R.string.regex_url);
        return searchStr.matches(regex);
    }

    public static boolean matchesHttpsUrlOrIp(String searchStr, Context context) {
        return matchesUrl(searchStr, context, true) ||
                matchesIpAddress(searchStr, context, true);
    }

}
