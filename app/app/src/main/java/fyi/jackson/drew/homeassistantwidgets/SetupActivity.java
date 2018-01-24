package fyi.jackson.drew.homeassistantwidgets;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.regex.Pattern;

public class SetupActivity extends AppCompatActivity {

    public static final String TAG = "SetupActivity";

    LinearLayout parentLayout;
    int[] stepLayouts;
    int currentStep = -1;

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

    }

    private boolean validateCurrentStep() {
        switch (currentStep) {
            case 0: // Domain
                return validateDomain();
            case 1: // Protocol
                return true;
            case 2: // Access
                return true;
            case 3: // Password
                return validatePassword();
            default:
                return false;
        }
    }

    public boolean validateDomain() {
        EditText domainEditText = findViewById(R.id.et_domain);
        String domain = domainEditText.getText().toString();

        Log.d(TAG, "validateDomain: " + getString(R.string.regex_lax_url));
        Log.d(TAG, "validateDomain: " + domain.matches(getString(R.string.regex_lax_url)));
        if (domain.matches(getString(R.string.regex_lax_url))) {
            ((CheckBox) findViewById(R.id.cb_protocol)).setChecked(
                    domain.matches(getString(R.string.regex_strict_https_url))
            );
            return true;
        }
        return false;
    }

    public boolean validatePassword() {
        EditText passwordEditText = findViewById(R.id.et_password);
        String password = passwordEditText.getText().toString();
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
        findViewById(stepLayouts[currentStep])
                .findViewById(R.id.stepper_check)
                .setVisibility(View.VISIBLE);
        findViewById(stepLayouts[currentStep])
                .findViewById(R.id.stepper_reveal)
                .setVisibility(View.GONE);
    }

    private void expandCurrentStep() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findViewById(stepLayouts[currentStep])
                    .findViewById(R.id.stepper_step)
                    .setBackground(getDrawable(R.drawable.background_stepper_step_active));
        }
        findViewById(stepLayouts[currentStep])
                .findViewById(R.id.stepper_reveal)
                .setVisibility(View.VISIBLE);
        findViewById(stepLayouts[currentStep])
                .findViewById(R.id.reveal_continue)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (validateCurrentStep()) {
                            nextStep();
                        }
                    }
                });
        findViewById(stepLayouts[currentStep]).setTag(currentStep);
        findViewById(stepLayouts[currentStep]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateCurrentStep()) {
                    goToStep((Integer) view.getTag());
                }
            }
        });
    }
}
