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

        showWelcomeScreen();
    }

    private void showWelcomeScreen() {

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

        startButton.setOnClickListener(v -> showLearnTrading());

        layout.addView(logo);
        layout.addView(name);
        layout.addView(subtitle);
        layout.addView(startButton);

        setContentView(layout);
    }

    private void showLearnTrading() {

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(40, 40, 40, 40);
        layout.setBackgroundColor(Color.WHITE);

        TextView icon = new TextView(this);
        icon.setText("📈");
        icon.setTextSize(70);
        icon.setGravity(Gravity.CENTER);

        TextView title = new TextView(this);
        title.setText("Learn Trading");
        title.setTextSize(32);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.rgb(20, 90, 60));

        TextView description = new TextView(this);
        description.setText(
                "Learn the basics of buying and selling " +
                "in a simple trading simulator."
        );
        description.setTextSize(19);
        description.setGravity(Gravity.CENTER);
        description.setPadding(20, 20, 20, 30);

        Button nextButton = new Button(this);
        nextButton.setText("NEXT");

        layout.addView(icon);
        layout.addView(title);
        layout.addView(description);
        layout.addView(nextButton);

        setContentView(layout);
    }
                         }
