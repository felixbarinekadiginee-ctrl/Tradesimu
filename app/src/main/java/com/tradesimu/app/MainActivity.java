package com.tradesimu.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {

    int GREEN = Color.rgb(0, 180, 100);
    int DARK = Color.rgb(20, 25, 30);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHome();
    }

    private void showHome() {

        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(Color.rgb(245, 248, 247));

        LinearLayout main = new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        main.setPadding(20, 25, 20, 30);

        TextView title = text("TradeSim", 30, DARK);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        main.addView(title);

        TextView subtitle = text("Your Trading Dashboard", 20, DARK);
        subtitle.setPadding(0, 5, 0, 20);
        main.addView(subtitle);

        // BALANCE
        LinearLayout balance = card();

        TextView balanceTitle =
                text("VIRTUAL BALANCE", 13, Color.WHITE);

        TextView balanceAmount =
                text("$10,000.00", 32, Color.WHITE);

        balanceAmount.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        TextView profit =
                text("+$0.00  (0.00%)", 15, Color.WHITE);

        balance.addView(balanceTitle);
        balance.addView(balanceAmount);
        balance.addView(profit);

        GradientDrawable balanceBg =
                new GradientDrawable();

        balanceBg.setColor(Color.rgb(18, 130, 78));
        balanceBg.setCornerRadius(28);

        balance.setBackground(balanceBg);

        main.addView(balance);

        // QUICK ACTIONS
        TextView quick =
                text("Quick Actions", 21, DARK);

        quick.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        quick.setPadding(0, 25, 0, 10);
        main.addView(quick);

        LinearLayout actions =
                new LinearLayout(this);

        actions.setOrientation(
                LinearLayout.HORIZONTAL
        );

        Button charts =
                action("📈\nCharts");

        Button tutor =
                action("🤖\nAI Tutor");

        Button rewards =
                action("🎁\nRewards");

        Button profile =
                action("👤\nProfile");

        actions.addView(charts);
        actions.addView(tutor);
        actions.addView(rewards);
        actions.addView(profile);

        main.addView(actions);

        charts.setOnClickListener(v -> {
            startActivity(
                    new Intent(
                            MainActivity.this,
                            ChartActivity.class
                    )
            );
        });

        // MARKETS
        TextView marketTitle =
                text("Markets", 22, DARK);

        marketTitle.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        marketTitle.setPadding(0, 25, 0, 10);
        main.addView(marketTitle);

        main.addView(
                market(
                        "BTC/USD",
                        "$63,240.32",
                        "+2.41%"
                )
        );

        main.addView(
                market(
                        "ETH/USD",
                        "$3,420.18",
                        "+1.87%"
                )
        );

        main.addView(
                market(
                        "AAPL",
                        "$227.16",
                        "+0.92%"
                )
        );

        main.addView(
                market(
                        "TSLA",
                        "$258.12",
                        "-0.64%"
                )
        );

        // START BUTTON
        Button start =
                new Button(this);

        start.setText("START PRACTICING");
        start.setTextSize(17);
        start.setTextColor(Color.WHITE);
        start.setGravity(Gravity.CENTER);
        start.setAllCaps(false);

        GradientDrawable startBg =
                new GradientDrawable();

        startBg.setColor(GREEN);
        startBg.setCornerRadius(35);

        start.setBackground(startBg);

        LinearLayout.LayoutParams startParams =
                new LinearLayout.LayoutParams(
                        -1,
                        70
                );

        startParams.setMargins(0, 25, 0, 15);

        main.addView(start, startParams);

        start.setOnClickListener(v -> {
            startActivity(
                    new Intent(
                            MainActivity.this,
                            ChartActivity.class
                    )
            );
        });

        TextView note =
                text(
                        "💡 Practice trading with virtual money without risking real money.",
                        15,
                        Color.DKGRAY
                );

        note.setGravity(Gravity.CENTER);
        note.setPadding(10, 15, 10, 10);

        main.addView(note);

        scroll.addView(main);
        setContentView(scroll);
    }

    private TextView text(
            String value,
            float size,
            int color
    ) {

        TextView t =
                new TextView(this);

        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);

        return t;
    }

    private LinearLayout card() {

        LinearLayout c =
                new LinearLayout(this);

        c.setOrientation(
                LinearLayout.VERTICAL
        );

        c.setPadding(20, 20, 20, 20);

        return c;
    }

    private Button action(String value) {

        Button b =
                new Button(this);

        b.setText(value);
        b.setTextSize(12);
        b.setTextColor(DARK);
        b.setAllCaps(false);
        b.setGravity(Gravity.CENTER);

        GradientDrawable bg =
                new GradientDrawable();

        bg.setColor(Color.WHITE);
        bg.setCornerRadius(22);

        b.setBackground(bg);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        0,
                        82,
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

        LinearLayout m = card();

        GradientDrawable bg =
                new GradientDrawable();

        bg.setColor(Color.WHITE);
        bg.setCornerRadius(23);

        m.setBackground(bg);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        -1,
                        115
                );

        p.setMargins(0, 0, 0, 10);

        m.setLayoutParams(p);

        TextView n =
                text(name, 18, DARK);

        n.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        TextView pr =
                text(price, 17, DARK);

        pr.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        TextView ch =
                text(
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
