package fyi.jackson.drew.homeassistantwidgets.stepper;


import android.os.Build;
import android.support.annotation.IdRes;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fyi.jackson.drew.homeassistantwidgets.R;

public class Stepper {
    View root;
    List<Integer> contentIds;
    List<String> titles;
    int currentStep = -1;

    public Stepper(View rootView) {
        root = rootView;
        contentIds = new ArrayList<>();
        titles = new ArrayList<>();
    }

    public void addStep(String title, @IdRes int contentId) {
        titles.add(title);
        contentIds.add(contentId);
    }

    public void inflate() {

    }

    public boolean validateCurrentStep() {
        return true;
    }

    public void nextStep() {
        goToStep(currentStep + 1);
    }

    public void goToStep(int step) {
        if (step == currentStep) return;
        setupTransiton();
        collapseCurrentStep();
        currentStep = step;
        if (currentStep >= contentIds.size()) return;
        expandCurrentStep();

    }

    public void setupTransiton() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition((ViewGroup) root);
        }
    }

    public void collapseCurrentStep() {
        if (currentStep < 0 || currentStep >= contentIds.size()) return;
        root.findViewById(contentIds.get(currentStep))
                .findViewById(R.id.stepper_check)
                .setVisibility(View.VISIBLE);
        root.findViewById(contentIds.get(currentStep))
                .findViewById(R.id.stepper_reveal)
                .setVisibility(View.GONE);
    }

    public void expandCurrentStep() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            root.findViewById(contentIds.get(currentStep))
                    .findViewById(R.id.stepper_step)
                    .setBackground(root.getContext().getDrawable(R.drawable.background_stepper_step_active));
        }
        root.findViewById(contentIds.get(currentStep))
                .findViewById(R.id.stepper_reveal)
                .setVisibility(View.VISIBLE);
        root.findViewById(contentIds.get(currentStep))
                .findViewById(R.id.reveal_continue)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (validateCurrentStep()) {
                            nextStep();
                        }
                    }
                });
        root.findViewById(contentIds.get(currentStep)).setTag(currentStep);
        root.findViewById(contentIds.get(currentStep)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateCurrentStep()) {
                    goToStep((Integer) view.getTag());
                }
            }
        });
    }
}
