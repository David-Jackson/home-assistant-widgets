package fyi.jackson.drew.homeassistantwidgets.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import fyi.jackson.drew.homeassistantwidgets.network.Icon;
import fyi.jackson.drew.homeassistantwidgets.network.IconsList;
import fyi.jackson.drew.homeassistantwidgets.network.MaterialDesignIconsInterface;
import fyi.jackson.drew.homeassistantwidgets.utils.svgparse.SVGParser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class MaterialDesignIcons {

    private static final String TAG = "MaterialDesignIcons";
    private static final String BASE_URL = "https://materialdesignicons.com/";

    List<Icon> iconList = null;

    public MaterialDesignIcons() {
        loadIcons();
    }

    private void loadIcons() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MaterialDesignIconsInterface mdiInterface = retrofit.create(MaterialDesignIconsInterface.class);

        String pkg = "38EF63D0-4744-11E4-B3CF-842B2B6CFE1B";
        Call<IconsList> call = mdiInterface.getPackage(pkg);
        call.enqueue(new Callback<IconsList>() {
            @Override
            public void onResponse(Call<IconsList> call, Response<IconsList> response) {
                Log.d(TAG, "onResponse: Success");
                IconsList iconsList = response.body();
                iconList = iconsList.getIcons();
                Log.d(TAG, "onResponse: " + iconList.size() + " icons downloaded");
                onIconsLoaded();
            }

            @Override
            public void onFailure(Call<IconsList> call, Throwable t) {
                Log.d(TAG, "onFailure: Fail");
                t.printStackTrace();
            }
        });
    }

    public abstract void onIconsLoaded();

    public boolean hasIcons() {
        return iconList != null;
    }

    public Icon findIcon(String iconName) {
        for (Icon icon : iconList) {
            if (icon.getName().equals(iconName)) {
                return icon;
            }
        }
        return null;
    }

    public Path pathFromIcon(Icon icon) {
        Path iconPath = SVGParser.parsePath(icon.getData());
        return iconPath;
    }

    public Drawable getIconDrawable(String iconName, final float height, final float width) {
        Icon icon = findIcon(iconName);
        if (icon == null) return null;

        final Path iconPath = pathFromIcon(icon);

        Drawable iconDrawable = new Drawable() {
            @Override
            public void draw(@NonNull Canvas canvas) {
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);

                Matrix scaleMatrix = new Matrix();
                scaleMatrix.setScale(height / 24, width / 24);

                iconPath.transform(scaleMatrix);

                canvas.drawPath(iconPath, paint);
            }

            @Override
            public void setAlpha(int i) {

            }

            @Override
            public void setColorFilter(@Nullable ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return PixelFormat.OPAQUE;
            }
        };

        return iconDrawable;
    }
}
