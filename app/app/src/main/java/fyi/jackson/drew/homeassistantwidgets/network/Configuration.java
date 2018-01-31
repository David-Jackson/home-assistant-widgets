package fyi.jackson.drew.homeassistantwidgets.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import fyi.jackson.drew.homeassistantwidgets.R;
import fyi.jackson.drew.homeassistantwidgets.utils.PrefUtils;

public class Configuration {

    private String TAG = "Configuration";

    private String protocol, domain, password;
    private boolean access;

    private boolean isConfigured = true;

    public Configuration(Context context) {
        SharedPreferences sharedPrefs =  PrefUtils.getPreferences(context);

        boolean isHttps = sharedPrefs.getBoolean(context.getString(R.string.sp_ha_https), false);
        boolean isHttpsSet =
                isHttps == sharedPrefs.getBoolean(context.getString(R.string.sp_ha_https), true);

        protocol = "http" + (isHttps ? "s" : "");

        domain = sharedPrefs.getString(context.getString(R.string.sp_ha_domain), null);

        password = sharedPrefs.getString(context.getString(R.string.sp_ha_password), null);

        access = sharedPrefs.getBoolean(context.getString(R.string.sp_ha_access), false);
        boolean isAccessSet =
                access == sharedPrefs.getBoolean(context.getString(R.string.sp_ha_access), true);

        isConfigured =
                (domain != null) &&
                (password != null) &&
                isHttpsSet &&
                isAccessSet;
    }

    public String buildBaseUrl() {
        return new Uri.Builder()
                .scheme(protocol)
                .authority(domain)
                .build().toString();
    }

    public boolean isConfigured() {
        return this.isConfigured;
    }

    public String getPassword() {
        return password;
    }

    public boolean isInternetAccessible() {
        return access;
    }
}
