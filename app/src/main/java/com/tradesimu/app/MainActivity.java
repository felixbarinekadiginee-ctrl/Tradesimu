package com.tradesimu.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    private int green = Color.rgb(20, 150, 90);

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

        ImageView logo = new ImageView(this);
        logo.setImageResource(com.tradesimu.app.R.drawable.tradesim_logo);
        logo.setAdjustViewBounds(true);

        LinearLayout.LayoutParams logoParams =
                new LinearLayout.LayoutParams(300, 300);

        logoParams.gravity = Gravity.CENTER;
        logo.setLayoutParams(logoParams);

        TextView name = new TextView(this);
        name.setText("TradeSim");
        name.setTextSize(40);
        name.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        name.setTextColor(green);
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

        animateLogo(logo);
    }

    private void animateLogo(ImageView logo) {

        ScaleAnimation animation = new ScaleAnimation(
                0.7f,
                1.0f,
                0.7f,
                1.0f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
        );

        animation.setDuration(800);
        animation.setFillAfter(true);

        logo.startAnimation(animation);
    }

    private void showLearnTrading() {

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(40, 40, 40, 40);
        layout.setBackgroundColor(Color.WHITE);

        ImageView logo = new ImageView(this);
        logo.setImageResource(com.tradesimu.app.R.drawable.tradesim_logo);
        logo.setAdjustViewBounds(true);

        LinearLayout.LayoutParams logoParams =
                new LinearLayout.LayoutParams(180, 180);

        logoParams.gravity = Gravity.CENTER;
        logo.setLayoutParams(logoParams);

        TextView icon = new TextView(this);
        icon.setText("📈");
        icon.setTextSize(55);
        icon.setGravity(Gravity.CENTER);

        TextView title = new TextView(this);
        title.setText("Learn Trading");
        title.setTextSize(32);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(green);

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

        layout.addView(logo);
        layout.addView(icon);
        layout.addView(title);
        layout.addView(description);
        layout.addView(nextButton);

        setContentView(layout);

        animateLogo(logo);
    }
                          }
