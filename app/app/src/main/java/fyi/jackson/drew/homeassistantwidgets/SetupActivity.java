package fyi.jackson.drew.homeassistantwidgets;

import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import fyi.jackson.drew.homeassistantwidgets.utils.PrefUtils;
import fyi.jackson.drew.homeassistantwidgets.utils.StringUtils;

public class SetupActivity extends AppCompatActivity {

    public static final String TAG = "SetupActivity";

    LinearLayout parentLayout;
    int[] stepLayouts;
    int currentStep = -1;

    EditText domainEditText;
    CheckBox protocolCheckBox;
    CheckBox accessCheckBox;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        try {
            getActionBar().setTitle(R.string.setup_activity_title);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        parentLayout = findViewById(R.id.parent_layout);

        stepLayouts = new int[] {
                R.id.step_1,
                R.id.step_2,
                R.id.step_3,
                R.id.step_4
        };

        parentLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextStep();
            }
        }, 1000);

        bindElements();
        fillWithPreferences();
    }

    private void bindElements() {
        domainEditText = findViewById(R.id.et_domain);
        protocolCheckBox = findViewById(R.id.cb_protocol);
        accessCheckBox = findViewById(R.id.cb_access);
        passwordEditText = findViewById(R.id.et_password);
    }

    private void fillWithPreferences() {
        SharedPreferences sharedPrefs = PrefUtils.getPreferences(this);

        String domain = sharedPrefs.getString(getString(R.string.sp_ha_domain), null);
        boolean isHttps = sharedPrefs.getBoolean(getString(R.string.sp_ha_https), false);
        boolean access = sharedPrefs.getBoolean(getString(R.string.sp_ha_access), false);
        String password = sharedPrefs.getString(getString(R.string.sp_ha_password), null);

        if (domain != null) domainEditText.setText(domain);
        protocolCheckBox.setChecked(isHttps);
        accessCheckBox.setChecked(access);
        if (password != null) passwordEditText.setText(password);
    }

    private boolean validateCurrentStep() {
        switch (currentStep) {
            case 0: // Domain
                return validateDomain();
            case 1: // Protocol
                return validateProtocol();
            case 2: // Access
                return validateAccess();
            case 3: // Password
                return validatePassword();
            default:
                return false;
        }
    }

    private boolean validateDomain() {
        String domain = domainEditText.getText().toString();

        if (StringUtils.matchesUrlOrIpAddress(domain, this)) {
            protocolCheckBox.setChecked(
                    StringUtils.matchesHttpsUrlOrIp(domain, this)
            );
            PrefUtils.setString(getString(R.string.sp_ha_domain), domain, this);
            return true;
        }
        return false;
    }

    private boolean validateProtocol() {
        PrefUtils.setBoolean(getString(R.string.sp_ha_https),
                protocolCheckBox.isChecked(), this);
        return true;
    }

    private boolean validateAccess() {
        PrefUtils.setBoolean(getString(R.string.sp_ha_access),
                accessCheckBox.isChecked(), this);
        return true;
    }

    private boolean validatePassword() {
        String password = passwordEditText.getText().toString();
        boolean valid = !password.isEmpty();
        if (valid) {
            PrefUtils.setString(getString(R.string.sp_ha_password), password, this);
        }
        return !password.isEmpty();
    }

    private void nextStep() {
        goToStep(currentStep + 1);
    }

    private void goToStep(int step) {
        if (step == currentStep) return;
        setupTransiton();
        collapseCurrentStep();
        currentStep = step;
        if (currentStep >= stepLayouts.length) return;
        expandCurrentStep();

    }

    private void setupTransiton() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(parentLayout);
        }
    }

    private void collapseCurrentStep() {
        if (currentStep < 0 || currentStep >= stepLayouts.length) return;
        View layout = findViewById(stepLayouts[currentStep]);
        layout.findViewById(R.id.stepper_check)
                .setVisibility(View.VISIBLE);
        layout.findViewById(R.id.stepper_reveal)
                .setVisibility(View.GONE);
    }

    private void expandCurrentStep() {
        View layout = findViewById(stepLayouts[currentStep]);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            layout.findViewById(R.id.stepper_step)
                    .setBackground(getDrawable(R.drawable.background_stepper_step_active));
        }
        layout.findViewById(R.id.stepper_reveal)
                .setVisibility(View.VISIBLE);
        layout.findViewById(R.id.reveal_continue)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (validateCurrentStep()) {
                            nextStep();
                        }
                    }
                });
        layout.setTag(currentStep);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToStep((Integer) view.getTag());
            }
        });
    }
}
