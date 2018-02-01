package fyi.jackson.drew.homeassistantwidgets.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MaterialDesignIconsInterface {

    @GET("api/init")
    Call<InitData> init();

    @GET("api/package/{package_id}")
    Call<IconsList> getPackage(@Path("package_id") String packageId);

}
