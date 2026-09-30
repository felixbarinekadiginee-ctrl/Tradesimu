package com.tradesimu.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChartActivity extends Activity {

    private int GREEN = Color.rgb(0, 180, 100);
    private int DARK = Color.rgb(20, 25, 30);
    private int LIGHT = Color.rgb(245, 248, 247);

    private LinearLayout main;
    private int slide = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showIntro();
    }

    private void showIntro() {

        main = new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        main.setGravity(Gravity.CENTER);
        main.setPadding(30, 30, 30, 30);
        main.setBackgroundColor(LIGHT);

        setContentView(main);

        showSlide();
    }

    private void showSlide() {

        main.removeAllViews();

        TextView icon = new TextView(this);
        icon.setGravity(Gravity.CENTER);
        icon.setTextSize(60);

        TextView title = new TextView(this);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(28);
        title.setTextColor(DARK);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        title.setPadding(10, 25, 10, 15);

        TextView description = new TextView(this);
        description.setGravity(Gravity.CENTER);
        description.setTextSize(17);
        description.setTextColor(Color.DKGRAY);
        description.setLineSpacing(6, 1.0f);
        description.setPadding(20, 5, 20, 25);

        if (slide == 0) {

            icon.setText("📈");

            title.setText("Understand the Market");

            description.setText(
                    "Learn how prices move and discover " +
                    "how traders study market charts."
            );

        } else if (slide == 1) {

            icon.setText("💰");

            title.setText("Practice With Virtual Money");

            description.setText(
                    "TradeSim gives you virtual money " +
                    "so you can practice without risking real money."
            );

        } else {

            icon.setText("🎯");

            title.setText("Make Your First Practice Trade");

            description.setText(
                    "Study the chart, choose a time period, " +
                    "and practice buying or selling virtual assets."
            );
        }

        main.addView(icon);

        main.addView(title);

        main.addView(description);

        // SLIDE INDICATORS
        LinearLayout indicators =
                new LinearLayout(this);

        indicators.setGravity(Gravity.CENTER);

        for (int i = 0; i < 3; i++) {

            TextView dot =
                    new TextView(this);

            if (i == slide) {
                dot.setText("●");
                dot.setTextColor(GREEN);
            } else {
                dot.setText("○");
                dot.setTextColor(Color.GRAY);
            }

            dot.setTextSize(20);
            dot.setPadding(7, 0, 7, 0);

            indicators.addView(dot);
        }

        main.addView(indicators);

        Button next =
                new Button(this);

        if (slide < 2) {
            next.setText("CONTINUE");
        } else {
            next.setText("START PRACTICING");
        }

        next.setTextSize(17);
        next.setTextColor(Color.WHITE);
        next.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );
        next.setAllCaps(false);
        next.setGravity(Gravity.CENTER);

        GradientDrawable buttonBackground =
                new GradientDrawable();

        buttonBackground.setColor(GREEN);
        buttonBackground.setCornerRadius(35);

        next.setBackground(buttonBackground);

        LinearLayout.LayoutParams buttonParams =
                new LinearLayout.LayoutParams(
                        -1,
                        65
                );

        buttonParams.setMargins(
                20,
                30,
                20,
                10
        );

        main.addView(next, buttonParams);

        next.setOnClickListener(v -> {

            if (slide < 2) {

                slide++;

                showSlide();

            } else {

                showTradingChart();
            }
        });

        animateSlide(icon);
        animateSlide(title);
        animateSlide(description);
        animateSlide(next);
    }

    private void showTradingChart() {

        main.removeAllViews();

        TextView title =
                new TextView(this);

        title.setText("BTC/USD");
        title.setTextSize(28);
        title.setTextColor(DARK);
        title.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );
        title.setGravity(Gravity.CENTER);

        main.addView(title);

        TextView price =
                new TextView(this);

        price.setText("$63,240.32");
        price.setTextSize(27);
        price.setTextColor(DARK);
        price.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );
        price.setGravity(Gravity.CENTER);
        price.setPadding(0, 15, 0, 5);

        main.addView(price);

        TextView change =
                new TextView(this);

        change.setText("+2.41%");
        change.setTextSize(16);
        change.setTextColor(GREEN);
        change.setGravity(Gravity.CENTER);

        main.addView(change);

        // CHART
        TextView chart =
                new TextView(this);

        chart.setText(
                "\n" +
                "       ╱╲\n" +
                "      ╱  ╲      ╱╲\n" +
                "  ╱╲ ╱    ╲    ╱  ╲\n" +
                " ╱  ╲      ╲__╱    ╲\n" +
                "╱                  ╲\n" +
                "────────────────────\n" +
                " MON  TUE  WED  THU  FRI\n"
        );

        chart.setTextSize(18);
        chart.setTextColor(GREEN);
        chart.setTypeface(Typeface.MONOSPACE);
        chart.setGravity(Gravity.CENTER);
        chart.setPadding(5, 35, 5, 30);

        GradientDrawable chartBackground =
                new GradientDrawable();

        chartBackground.setColor(Color.WHITE);
        chartBackground.setCornerRadius(25);

        chart.setBackground(chartBackground);

        main.addView(chart);

        TextView period =
                new TextView(this);

        period.setText(
                "1H       4H       1D       1W"
        );

        period.setTextSize(16);
        period.setTextColor(DARK);
        period.setGravity(Gravity.CENTER);
        period.setPadding(10, 25, 10, 15);

        main.addView(period);

        Button buy =
                tradingButton(
                        "BUY BTC",
                        GREEN
                );

        main.addView(buy);

        Button sell =
                tradingButton(
                        "SELL BTC",
                        Color.rgb(220, 60, 60)
                );

        main.addView(sell);

        TextView note =
                new TextView(this);

        note.setText(
                "Practice only • Virtual money"
        );

        note.setTextSize(14);
        note.setTextColor(Color.GRAY);
        note.setGravity(Gravity.CENTER);
        note.setPadding(10, 20, 10, 10);

        main.addView(note);

        animateSlide(title);
        animateSlide(chart);
        animateSlide(buy);
        animateSlide(sell);
    }

    private Button tradingButton(
            String text,
            int color
    ) {

        Button button =
                new Button(this);

        button.setText(text);
        button.setTextSize(17);
        button.setTextColor(Color.WHITE);
        button.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );
        button.setAllCaps(false);
        button.setGravity(Gravity.CENTER);

        GradientDrawable background =
                new GradientDrawable();

        background.setColor(color);
        background.setCornerRadius(35);

        button.setBackground(background);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        -1,
                        65
                );

        params.setMargins(
                0,
                5,
                0,
                5
        );

        button.setLayoutParams(params);

        button.setOnClickListener(v -> {

            ScaleAnimation animation =
                    new ScaleAnimation(
                            1.0f,
                            0.92f,
                            1.0f,
                            0.92f,
                            Animation.RELATIVE_TO_SELF,
                            0.5f,
                            Animation.RELATIVE_TO_SELF,
                            0.5f
                    );

            animation.setDuration(120);
            animation.setRepeatMode(
                    Animation.REVERSE
            );
            animation.setRepeatCount(1);

            v.startAnimation(animation);
        });

        return button;
    }

    private void animateSlide(View view) {

        AlphaAnimation animation =
                new AlphaAnimation(
                        0.0f,
                        1.0f
                );

        animation.setDuration(600);

        view.startAnimation(animation);
    }
}
