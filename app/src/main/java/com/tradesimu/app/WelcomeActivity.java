package com.tradesimu.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WelcomeActivity extends Activity {

    private final int GREEN = Color.rgb(0, 180, 100);
    private final int DARK = Color.rgb(20, 25, 30);
    private final int LIGHT = Color.rgb(245, 248, 247);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout main = new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        main.setGravity(Gravity.CENTER);
        main.setPadding(30, 40, 30, 40);
        main.setBackgroundColor(LIGHT);

        TextView logo = new TextView(this);
        logo.setText("TradeSim");
        logo.setTextSize(38);
        logo.setTextColor(GREEN);
        logo.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        logo.setGravity(Gravity.CENTER);
        main.addView(logo);

        TextView title = new TextView(this);
        title.setText("Welcome to TradeSim");
        title.setTextSize(28);
        title.setTextColor(DARK);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        title.setIncludeFontPadding(true);
        title.setPadding(10, 35, 10, 15);
        main.addView(title);

        TextView description = new TextView(this);
        description.setText(
                "Learn how trading works, practice with virtual money, " +
                "and build your trading skills without risking real money."
        );
        description.setTextSize(17);
        description.setTextColor(Color.DKGRAY);
        description.setGravity(Gravity.CENTER);
        description.setLineSpacing(6, 1.0f);
        description.setIncludeFontPadding(true);
        description.setPadding(20, 5, 20, 35);
        main.addView(description);

        Button login = createButton("Log In", GREEN);
        main.addView(login);

        Button signup = createButton("Sign Up", DARK);
        main.addView(signup);

        setContentView(main);
    }

    private Button createButton(String text, int color) {

        Button button = new Button(this);

        button.setText(text);
        button.setTextSize(17);
        button.setTextColor(Color.WHITE);
        button.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        button.setGravity(Gravity.CENTER);
        button.setAllCaps(false);
        button.setIncludeFontPadding(true);
        button.setPadding(12, 12, 12, 12);

        GradientDrawable background = new GradientDrawable();
        background.setColor(color);
        background.setCornerRadius(35);

        button.setBackground(background);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        75
                );

        params.setMargins(10, 8, 10, 8);
        button.setLayoutParams(params);

        return button;
    }
    }
