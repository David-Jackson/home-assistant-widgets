package fyi.jackson.drew.homeassistantwidgets;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    Configuration configuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        configuration = new Configuration(this);
        if (!configuration.isConfigured()) openSetupActivity();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void openSetupActivity() {
        Intent setupActivityIntent = new Intent(this, SetupActivity.class);
        startActivity(setupActivityIntent);
    }
}
