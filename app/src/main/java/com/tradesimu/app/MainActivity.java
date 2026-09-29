package com.tradesimu.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(30, 30, 30, 30);
        layout.setBackgroundColor(Color.WHITE);

        TextView logo = new TextView(this);
        logo.setText("📈");
        logo.setTextSize(70);
        logo.setGravity(Gravity.CENTER);

        TextView name = new TextView(this);
        name.setText("TradeSim");
        name.setTextSize(40);
        name.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        name.setTextColor(Color.rgb(20, 90, 60));
        name.setGravity(Gravity.CENTER);

        TextView subtitle = new TextView(this);
        subtitle.setText("Learn • Practice • Trade");
        subtitle.setTextSize(18);
        subtitle.setTextColor(Color.DKGRAY);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, 15, 0, 30);

        Button startButton = new Button(this);
        startButton.setText("GET STARTED");
        startButton.setTextSize(18);

        layout.addView(logo);
        layout.addView(name);
        layout.addView(subtitle);
        layout.addView(startButton);

        setContentView(layout);
    }
            }
