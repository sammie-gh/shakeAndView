package com.sammie.shakeview;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import safety.com.br.android_shake_detector.core.ShakeCallback;
import safety.com.br.android_shake_detector.core.ShakeDetector;
import safety.com.br.android_shake_detector.core.ShakeOptions;

public class MainActivity extends AppCompatActivity {


    private final String TAG = this.getClass().getSimpleName();
    private ToggleButton toggleButton;
    private ShakeOptions shakeOptions;
    private ShakeDetector shakeDetector;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //views
        toggleButton = findViewById(R.id.panicBtn);
        textView = findViewById(R.id.textView);

        shakeOptions = new ShakeOptions()
                .background(true)
                .interval(1000)
                .shakeCount(4)
                .sensibility(2.0f);

        this.shakeDetector = new ShakeDetector(shakeOptions).start(this, new ShakeCallback() {
            @Override
            public void onShake() {
                Log.d(TAG, "onShake " + shakeOptions.getShakeCounts());
                textView.setText("Shake " + shakeOptions.getShakeCounts());
                toggleButton.setChecked(true);


            }
        });


        //        this.shakeDetector = new ShakeDetector(options).start(this);
        //        startService(new Intent(getApplicationContext(), ShakeSensorService.class));


        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    toggleButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.circle_bg_off));
                    Toast message = Toast.makeText(getApplicationContext(), "On", Toast.LENGTH_SHORT);
                    message.setGravity(Gravity.CENTER, message.getXOffset() / 4,
                            message.getYOffset() / 4);
                    message.show();
                    shakeDetector.startService(getBaseContext());


                } else {
                    // The toggle is disabled
                    toggleButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.circle_bg_on));
                    Toast message = Toast.makeText(getApplicationContext(), "Off", Toast.LENGTH_SHORT);
                    message.setGravity(Gravity.CENTER, message.getYOffset() / 4,
                            message.getXOffset() / 4);
                    message.show();
                    shakeDetector.stopShakeDetector(getBaseContext());


                }
            }
        });


//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            String calledFrom = bundle.getString("CALLED_FROM");
//            if (calledFrom != null && calledFrom.equals("ShakeSensorService")) {
//                Log.d(TAG, "Called From " + TAG_SENSOR);
//                Toast.makeText(this, "shaje ", Toast.LENGTH_SHORT).show();
//                toggleButton.setChecked(true);
//            }
//        }
    }

    @Override
    protected void onDestroy() {
        toggleButton.setChecked(false);
        shakeDetector.destroy(getBaseContext());
        super.onDestroy();
    }


}
