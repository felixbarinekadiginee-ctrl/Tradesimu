package com.tradesimu.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private final int GREEN = Color.rgb(0, 180, 100);
    private final int DARK = Color.rgb(20, 25, 30);
    private final int LIGHT = Color.rgb(245, 248, 247);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHome();
    }

    private void showHome() {

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setBackgroundColor(LIGHT);

        LinearLayout main = new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        main.setPadding(18, 20, 18, 30);

        // TITLE
        TextView title = text("TradeSim", 30, DARK);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        main.addView(title);

        TextView subtitle = text("Your Trading Dashboard", 18, Color.DKGRAY);
        subtitle.setPadding(0, 4, 0, 18);
        main.addView(subtitle);

        // BALANCE CARD
        LinearLayout balance = new LinearLayout(this);
        balance.setOrientation(LinearLayout.VERTICAL);
        balance.setPadding(20, 18, 20, 18);

        GradientDrawable balanceBg = new GradientDrawable();
        balanceBg.setColor(Color.rgb(18, 130, 78));
        balanceBg.setCornerRadius(28);
        balance.setBackground(balanceBg);

        balance.addView(text("VIRTUAL BALANCE", 13, Color.WHITE));

        TextView balanceAmount = text("$10,000.00", 30, Color.WHITE);
        balanceAmount.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        balance.addView(balanceAmount);

        balance.addView(text("+$0.00  (0.00%)", 15, Color.WHITE));

        main.addView(
                balance,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                )
        );

        // QUICK ACTIONS
        TextView quick = text("Quick Actions", 21, DARK);
        quick.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        quick.setPadding(0, 22, 0, 10);
        main.addView(quick);

        LinearLayout actions = new LinearLayout(this);
        actions.setOrientation(LinearLayout.HORIZONTAL);

        Button charts = action("📈\nCharts");
        Button tutor = action("🤖\nAI Tutor");
        Button rewards = action("🎁\nRewards");
        Button profile = action("👤\nProfile");

        actions.addView(charts);
        actions.addView(tutor);
        actions.addView(rewards);
        actions.addView(profile);

        main.addView(actions);

        charts.setOnClickListener(v -> openChart());

        // MARKETS
        TextView marketTitle = text("Markets", 22, DARK);
        marketTitle.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        marketTitle.setPadding(0, 22, 0, 10);
        main.addView(marketTitle);

        main.addView(market("BTC/USD", "$63,240.32", "+2.41%"));
        main.addView(market("ETH/USD", "$3,420.18", "+1.87%"));
        main.addView(market("AAPL", "$227.16", "+0.92%"));
        main.addView(market("TSLA", "$258.12", "-0.64%"));

        // START PRACTICING
        Button start = new Button(this);

        start.setText("START PRACTICING");
        start.setTextSize(17);
        start.setTextColor(Color.WHITE);
        start.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        start.setGravity(Gravity.CENTER);
        start.setAllCaps(false);

        // Give the button enough room for Android's internal padding.
        start.setMinHeight(80);
        start.setMinimumHeight(80);
        start.setPadding(12, 12, 12, 12);

        GradientDrawable startBg = new GradientDrawable();
        startBg.setColor(GREEN);
        startBg.setCornerRadius(35);
        start.setBackground(startBg);

        LinearLayout.LayoutParams startParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        80
                );

        startParams.setMargins(0, 18, 0, 12);
        main.addView(start, startParams);

        start.setOnClickListener(v -> openChart());

        // NOTE
        TextView note = text(
                "💡 Practice trading with virtual money without risking real money.",
                14,
                Color.DKGRAY
        );

        note.setGravity(Gravity.CENTER);
        note.setPadding(10, 8, 10, 10);
        note.setIncludeFontPadding(true);

        main.addView(note);

        scroll.addView(main);
        setContentView(scroll);
    }

    private void openChart() {
        Intent intent = new Intent(
                MainActivity.this,
                ChartActivity.class
        );

        startActivity(intent);
    }

    private TextView text(
            String value,
            float size,
            int color
    ) {

        TextView t = new TextView(this);

        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);

        // Keep the full top and bottom of the letters visible.
        t.setIncludeFontPadding(true);

        return t;
    }

    private Button action(String value) {

        Button b = new Button(this);

        b.setText(value);
        b.setTextSize(11);
        b.setTextColor(DARK);
        b.setAllCaps(false);
        b.setGravity(Gravity.CENTER);

        // IMPORTANT:
        // The old height of 75 was too small.
        // 100 gives the two-line buttons enough vertical space.
        b.setMinHeight(100);
        b.setMinimumHeight(100);

        b.setPadding(4, 10, 4, 10);

        GradientDrawable bg = new GradientDrawable();
        bg.setColor(Color.WHITE);
        bg.setCornerRadius(22);

        b.setBackground(bg);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        0,
                        100,
                        1
                );

        p.setMargins(3, 3, 3, 3);

        b.setLayoutParams(p);

        return b;
    }

    private LinearLayout market(
            String name,
            String price,
            String change
    ) {

        LinearLayout m = new LinearLayout(this);

        m.setOrientation(LinearLayout.VERTICAL);
        m.setPadding(18, 12, 18, 12);

        GradientDrawable bg = new GradientDrawable();
        bg.setColor(Color.WHITE);
        bg.setCornerRadius(23);

        m.setBackground(bg);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        p.setMargins(0, 0, 0, 9);

        m.setLayoutParams(p);

        TextView n = text(name, 17, DARK);
        n.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

        TextView pr = text(price, 16, DARK);
        pr.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

        TextView ch = text(
                change,
                14,
                change.startsWith("+")
                        ? GREEN
                        : Color.RED
        );

        m.addView(n);
        m.addView(pr);
        m.addView(ch);

        return m;
    }
    }
