package com.tradesimu.app;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
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
        showDashboard();
    }

    private void showDashboard() {

        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.setBackgroundColor(LIGHT);

        LinearLayout main = new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        main.setPadding(20, 25, 20, 35);

        // HEADER
        LinearLayout header = new LinearLayout(this);
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER_VERTICAL);

        ImageView logo = new ImageView(this);
        logo.setImageResource(R.drawable.tradesim_logo);
        logo.setAdjustViewBounds(true);

        LinearLayout.LayoutParams logoParams =
                new LinearLayout.LayoutParams(65, 65);

        header.addView(logo, logoParams);

        LinearLayout titleBox = new LinearLayout(this);
        titleBox.setOrientation(LinearLayout.VERTICAL);
        titleBox.setPadding(12, 0, 0, 0);

        TextView welcome = new TextView(this);
        welcome.setText("Welcome to");
        welcome.setTextSize(14);
        welcome.setTextColor(Color.GRAY);

        TextView title = new TextView(this);
        title.setText("TradeSim");
        title.setTextSize(26);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        title.setTextColor(DARK);

        titleBox.addView(welcome);
        titleBox.addView(title);
        header.addView(titleBox);

        main.addView(header);

        animateView(header);

        // DASHBOARD TITLE
        TextView dashboardTitle = new TextView(this);
        dashboardTitle.setText("Your Trading Dashboard");
        dashboardTitle.setTextSize(22);
        dashboardTitle.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        dashboardTitle.setTextColor(DARK);
        dashboardTitle.setPadding(0, 25, 0, 12);

        main.addView(dashboardTitle);

        // BALANCE CARD
        LinearLayout balanceCard = new LinearLayout(this);
        balanceCard.setOrientation(LinearLayout.VERTICAL);
        balanceCard.setPadding(22, 20, 22, 20);

        GradientDrawable balanceBackground = new GradientDrawable();
        balanceBackground.setColor(Color.rgb(18, 130, 78));
        balanceBackground.setCornerRadius(28);

        balanceCard.setBackground(balanceBackground);

        TextView balanceLabel = new TextView(this);
        balanceLabel.setText("VIRTUAL BALANCE");
        balanceLabel.setTextSize(13);
        balanceLabel.setTextColor(Color.WHITE);

        TextView balance = new TextView(this);
        balance.setText("$10,000.00");
        balance.setTextSize(31);
        balance.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        balance.setTextColor(Color.WHITE);
        balance.setPadding(0, 6, 0, 4);

        TextView profit = new TextView(this);
        profit.setText("+$0.00  (0.00%)");
        profit.setTextSize(15);
        profit.setTextColor(Color.WHITE);

        balanceCard.addView(balanceLabel);
        balanceCard.addView(balance);
        balanceCard.addView(profit);

        main.addView(balanceCard);
        animateCard(balanceCard);

        // QUICK ACTIONS
        TextView quickTitle = new TextView(this);
        quickTitle.setText("Quick Actions");
        quickTitle.setTextSize(21);
        quickTitle.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        quickTitle.setTextColor(DARK);
        quickTitle.setPadding(0, 25, 0, 10);

        main.addView(quickTitle);

        LinearLayout quickRow = new LinearLayout(this);
        quickRow.setOrientation(LinearLayout.HORIZONTAL);
        quickRow.setGravity(Gravity.CENTER);

        Button chartButton = createActionButton("📈\nCharts");
        Button tutorButton = createActionButton("🤖\nAI Tutor");
        Button rewardsButton = createActionButton("🎁\nRewards");
        Button profileButton = createActionButton("👤\nProfile");

        quickRow.addView(chartButton);
        quickRow.addView(tutorButton);
        quickRow.addView(rewardsButton);
        quickRow.addView(profileButton);

        main.addView(quickRow);

        // CHART BUTTON OPENS CHART SCREEN
        chartButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ChartActivity.class));
        });

        // MARKETS
        TextView marketTitle = new TextView(this);
        marketTitle.setText("Markets");
        marketTitle.setTextSize(22);
        marketTitle.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        marketTitle.setTextColor(DARK);
        marketTitle.setPadding(0, 25, 0, 10);

        main.addView(marketTitle);

        HorizontalScrollView horizontalScroll =
                new HorizontalScrollView(this);

        horizontalScroll.setHorizontalScrollBarEnabled(false);

        LinearLayout markets = new LinearLayout(this);
        markets.setOrientation(LinearLayout.HORIZONTAL);

        markets.addView(createMarketCard(
                "BTC/USD",
                "$63,240.32",
                "+2.41%"
        ));

        markets.addView(createMarketCard(
                "ETH/USD",
                "$3,420.18",
                "+1.87%"
        ));

        markets.addView(createMarketCard(
                "AAPL",
                "$227.16",
                "+0.92%"
        ));

        markets.addView(createMarketCard(
                "TSLA",
                "$258.12",
                "-0.64%"
        ));

        horizontalScroll.addView(markets);
