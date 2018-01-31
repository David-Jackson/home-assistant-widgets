package fyi.jackson.drew.homeassistantwidgets;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

import fyi.jackson.drew.homeassistantwidgets.network.Configuration;
import fyi.jackson.drew.homeassistantwidgets.network.HomeAssistantInterface;
import fyi.jackson.drew.homeassistantwidgets.network.State;
import fyi.jackson.drew.homeassistantwidgets.recycler.AddComponentAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    Configuration configuration;

    Retrofit retrofit;
    HomeAssistantInterface haInterface;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        configuration = new Configuration(this);
        if (!configuration.isConfigured()) openSetupActivity();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: isConfigured = " + configuration.isConfigured());
        Log.d(TAG, "onCreate: baseUrl = " + configuration.buildBaseUrl());
        Log.d(TAG, "onCreate: password = " + configuration.getPassword());

        retrofit = new Retrofit.Builder()
                .baseUrl(configuration.buildBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        haInterface = retrofit.create(HomeAssistantInterface.class);

        recyclerView = findViewById(R.id.rv);
        layoutManager = new LinearLayoutManager(this);
        adapter = new AddComponentAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void openSetupActivity() {
        Intent setupActivityIntent = new Intent(this, SetupActivity.class);
        startActivity(setupActivityIntent);
    }

    public void request(View view) {
        Call<List<State>> call = haInterface.getStates(configuration.getPassword());
        call.enqueue(new Callback<List<State>>() {
            @Override
            public void onResponse(Call<List<State>> call, Response<List<State>> response) {
                Log.d(TAG, "onResponse: ");
                Log.d(TAG, "\t " + response.code());
                Log.d(TAG, "\t " + response.body());
                ((AddComponentAdapter) adapter).setStateList(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<State>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
}
