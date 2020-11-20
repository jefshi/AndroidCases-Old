package com.csp.cases.step;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.csp.cases.R;

public class StepActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_step);

        StepView step = findViewById(R.id.step);
        step.refreshStepIndex(3);
    }
}
