package fyi.jackson.drew.homeassistantwidgets.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HomeAssistantInterface {

    @GET("api/")
    Call<Status> getStatus(@Query("api_password") String password);

    @GET("api/config")
    Call<Config> getConfig(@Query("api_password") String password);

    @GET("api/discovery_info")
    Call<DiscoveryInfo> getDiscoveryInfo();

    @GET("api/events")
    Call<List<Event>> getEvents(@Query("api_password") String password);

//    Not yet implemented
//    @GET("api/services")
//    Call<List<Event>> getServices(@Query("api_password") String password);

    @GET("api/states")
    Call<List<State>> getStates(@Query("api_password") String password);

//    Implement with Picasso
//    @GET("api/camera_proxy/camera.{entity_id}")
//    Call<Object> getImage(@Path("entity_id") String entityId, @Query("api_password") String password);
}
