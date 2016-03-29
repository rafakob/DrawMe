package com.rafakob.example.drawme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.transparent_pressed).setPressed(true);
        findViewById(R.id.transparent_disabled).setEnabled(false);

        findViewById(R.id.colored_pressed1).setPressed(true);
        findViewById(R.id.colored_pressed2).setPressed(true);
        findViewById(R.id.colored_disabled).setEnabled(false);

        findViewById(R.id.rounded_pressed).setPressed(true);
        findViewById(R.id.rounded_disabled).setEnabled(false);

        findViewById(R.id.ctrl_pressed1).setPressed(true);
        findViewById(R.id.ctrl_pressed2).setPressed(true);

        findViewById(R.id.mask_def).setPressed(true);
        findViewById(R.id.mask_dark).setPressed(true);
        findViewById(R.id.mask_light).setPressed(true);

        findViewById(R.id.ada1).setPressed(true);
        findViewById(R.id.ada2).setPressed(true);
        findViewById(R.id.ada3).setPressed(true);
        findViewById(R.id.ada4).setPressed(true);
    }
}
