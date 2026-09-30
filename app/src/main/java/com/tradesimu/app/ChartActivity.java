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
import android.widget.ScrollView;
import android.widget.TextView;

public class ChartActivity extends Activity {

    private final int GREEN = Color.rgb(0, 180, 100);
    private final int DARK = Color.rgb(20, 25, 30);
    private final int LIGHT = Color.rgb(245, 248, 247);

    private LinearLayout main;
    private int slide = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showIntro();
    }

    // =========================================================
    // INTRODUCTION
    // =========================================================

    private void showIntro() {

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setBackgroundColor(LIGHT);

        main = new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        main.setGravity(Gravity.CENTER);
        main.setPadding(24, 25, 24, 30);

        scroll.addView(main);
        setContentView(scroll);

        showSlide();
    }

    private void showSlide() {

        main.removeAllViews();

        TextView icon = new TextView(this);
        icon.setGravity(Gravity.CENTER);
        icon.setTextSize(58);
        icon.setIncludeFontPadding(true);

        TextView title = new TextView(this);
        title.setGravity(Gravity.CENTER);
        title.setTextSize(27);
        title.setTextColor(DARK);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        title.setIncludeFontPadding(true);
        title.setPadding(10, 20, 10, 15);

        TextView description = new TextView(this);
        description.setGravity(Gravity.CENTER);
        description.setTextSize(17);
        description.setTextColor(Color.DKGRAY);
        description.setLineSpacing(7, 1.05f);
        description.setIncludeFontPadding(true);
        description.setPadding(15, 5, 15, 25);

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

        main.addView(
                icon,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                )
        );

        main.addView(
                title,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                )
        );

        main.addView(
                description,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                )
        );

        // =====================================================
        // SLIDE INDICATORS
        // =====================================================

        LinearLayout indicators = new LinearLayout(this);
        indicators.setGravity(Gravity.CENTER);
        indicators.setPadding(0, 5, 0, 5);

        for (int i = 0; i < 3; i++) {

            TextView dot = new TextView(this);

            if (i == slide) {
                dot.setText("●");
                dot.setTextColor(GREEN);
            } else {
                dot.setText("○");
                dot.setTextColor(Color.GRAY);
            }

            dot.setTextSize(20);
            dot.setIncludeFontPadding(true);
            dot.setPadding(7, 0, 7, 0);

            indicators.addView(dot);
        }

        main.addView(indicators);

        // =====================================================
        // CONTINUE / START PRACTICING BUTTON
        // =====================================================

        Button next = new Button(this);

        if (slide < 2) {
            next.setText("CONTINUE");
        } else {
            next.setText("START PRACTICING");
        }

        next.setTextSize(17);
        next.setTextColor(Color.WHITE);
        next.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        next.setAllCaps(false);
        next.setGravity(Gravity.CENTER);
        next.setIncludeFontPadding(true);

        // IMPORTANT: give Android enough vertical space.
        next.setMinHeight(90);
        next.setMinimumHeight(90);
        next.setPadding(12, 12, 12, 12);

        GradientDrawable buttonBackground =
                new GradientDrawable();

        buttonBackground.setColor(GREEN);
        buttonBackground.setCornerRadius(35);

        next.setBackground(buttonBackground);

        LinearLayout.LayoutParams buttonParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        90
                );

        buttonParams.setMargins(
                15,
                25,
                15,
                15
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

    // =========================================================
    // TRADING SCREEN
    // =========================================================

    private void showTradingChart() {

        main.removeAllViews();
        main.setGravity(Gravity.TOP);
        main.setPadding(18, 20, 18, 10);

        // TITLE
        TextView title = text(
                "BTC/USD",
                28,
                DARK
        );

        title.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        title.setGravity(Gravity.CENTER);

        main.addView(title);

        // PRICE
        TextView price = text(
                "$63,240.32",
                27,
                DARK
        );

        price.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        price.setGravity(Gravity.CENTER);
        price.setPadding(0, 12, 0, 5);

        main.addView(price);

        // CHANGE
        TextView change = text(
                "+2.41%",
                16,
                GREEN
        );

        change.setGravity(Gravity.CENTER);
        main.addView(change);

        // =====================================================
        // CHART
        // =====================================================

        TextView chart = new TextView(this);

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
        chart.setIncludeFontPadding(true);
        chart.setPadding(5, 30, 5, 30);

        GradientDrawable chartBackground =
                new GradientDrawable();

        chartBackground.setColor(Color.WHITE);
        chartBackground.setCornerRadius(25);

        chart.setBackground(chartBackground);

        LinearLayout.LayoutParams chartParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        chartParams.setMargins(0, 15, 0, 5);

        main.addView(chart, chartParams);

        // =====================================================
        // TIME PERIODS
        // =====================================================

        TextView period = text(
                "1H       4H       1D       1W",
                16,
                DARK
        );

        period.setGravity(Gravity.CENTER);
        period.setIncludeFontPadding(true);
        period.setPadding(10, 20, 10, 15);

        main.addView(period);

        // =====================================================
        // BUY BTC
        // =====================================================

        Button buy = tradingButton(
                "BUY BTC",
                GREEN
        );

        main.addView(buy);

        // =====================================================
        // SELL BTC
        // =====================================================

        Button sell = tradingButton(
                "SELL BTC",
                Color.rgb(220, 60, 60)
        );

        main.addView(sell);

        // =====================================================
        // NOTE
        // =====================================================

        TextView note = text(
                "Practice only • Virtual money",
                14,
                Color.GRAY
        );

        note.setGravity(Gravity.CENTER);
        note.setPadding(10, 15, 10, 10);

        main.addView(note);

        // =====================================================
        // BOTTOM NAVIGATION
        // =====================================================

        LinearLayout navigation = new LinearLayout(this);

        navigation.setOrientation(
                LinearLayout.HORIZONTAL
        );

        navigation.setGravity(Gravity.CENTER);
        navigation.setPadding(4, 6, 4, 6);

        GradientDrawable navBackground =
                new GradientDrawable();

        navBackground.setColor(Color.WHITE);
        navBackground.setCornerRadius(25);

        navigation.setBackground(navBackground);

        Button home = navButton("🏠\nHome");
        Button trade = navButton("📊\nTrade");
        Button ai = navButton("🤖\nAI");
        Button rewards = navButton("🎁\nRewards");
        Button profile = navButton("👤\nProfile");

        navigation.addView(home);
        navigation.addView(trade);
        navigation.addView(ai);
        navigation.addView(rewards);
        navigation.addView(profile);

        LinearLayout.LayoutParams navParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        82
                );

        navParams.setMargins(0, 10, 0, 5);

        main.addView(navigation, navParams);

        // HOME returns to MainActivity
        home.setOnClickListener(v -> finish());

        // TRADE keeps the user on the trading screen
        trade.setOnClickListener(v -> showTradingChart());

        animateSlide(title);
        animateSlide(chart);
        animateSlide(buy);
        animateSlide(sell);
        animateSlide(navigation);
    }

    // =========================================================
    // TRADING BUTTON
    // =========================================================

    private Button tradingButton(
            String buttonText,
            int color
    ) {

        Button button = new Button(this);

        button.setText(buttonText);
        button.setTextSize(17);
        button.setTextColor(Color.WHITE);
        button.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );
        button.setAllCaps(false);
        button.setGravity(Gravity.CENTER);
        button.setIncludeFontPadding(true);

        // Bigger height prevents text from being cut off.
        button.setMinHeight(90);
        button.setMinimumHeight(90);
        button.setPadding(12, 12, 12, 12);

        GradientDrawable background =
                new GradientDrawable();

        background.setColor(color);
        background.setCornerRadius(35);

        button.setBackground(background);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        90
                );

        params.setMargins(
                0,
                7,
                0,
                7
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

    // =========================================================
    // BOTTOM NAVIGATION BUTTON
    // =========================================================

    private Button navButton(String value) {

        Button button = new Button(this);

        button.setText(value);
        button.setTextSize(10);
        button.setTextColor(DARK);
        button.setAllCaps(false);
        button.setGravity(Gravity.CENTER);
        button.setIncludeFontPadding(true);

        button.setPadding(2, 6, 2, 6);

        GradientDrawable background =
                new GradientDrawable();

        background.setColor(Color.WHITE);
        background.setCornerRadius(18);

        button.setBackground(background);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        0,
                        70,
                        1
                );

        params.setMargins(2, 2, 2, 2);

        button.setLayoutParams(params);

        return button;
    }

    // =========================================================
    // TEXT HELPER
    // =========================================================

    private TextView text(
            String value,
            float size,
            int color
    ) {

        TextView t = new TextView(this);

        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);

        // Prevent Android from clipping the tops/bottoms
        // of letters.
        t.setIncludeFontPadding(true);

        return t;
    }

    // =========================================================
    // ANIMATION
    // =========================================================

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
