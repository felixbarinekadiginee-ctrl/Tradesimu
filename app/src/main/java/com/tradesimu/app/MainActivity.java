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
        scrollView.setBackgroundColor(LIGHT);

        LinearLayout main = new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        main.setPadding(24, 35, 24, 40);

        // HEADER
        LinearLayout header = new LinearLayout(this);
        header.setGravity(Gravity.CENTER_VERTICAL);

        ImageView logo = new ImageView(this);
        logo.setImageResource(com.tradesimu.app.R.drawable.tradesim_logo);
        logo.setAdjustViewBounds(true);

        LinearLayout.LayoutParams logoParams =
                new LinearLayout.LayoutParams(75, 75);

        header.addView(logo, logoParams);

        LinearLayout titleBox = new LinearLayout(this);
        titleBox.setOrientation(LinearLayout.VERTICAL);
        titleBox.setPadding(15, 0, 0, 0);

        TextView welcome = new TextView(this);
        welcome.setText("Welcome to");
        welcome.setTextSize(14);
        welcome.setTextColor(Color.GRAY);

        TextView title = new TextView(this);
        title.setText("TradeSim");
        title.setTextSize(27);
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
        dashboardTitle.setTextSize(23);
        dashboardTitle.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        dashboardTitle.setTextColor(DARK);
        dashboardTitle.setPadding(0, 30, 0, 15);

        main.addView(dashboardTitle);

        // BALANCE CARD
        LinearLayout balanceCard = createCard();

        TextView balanceLabel = new TextView(this);
        balanceLabel.setText("VIRTUAL BALANCE");
        balanceLabel.setTextSize(13);
        balanceLabel.setTextColor(Color.WHITE);

        TextView balance = new TextView(this);
        balance.setText("$10,000.00");
        balance.setTextSize(34);
        balance.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        balance.setTextColor(Color.WHITE);
        balance.setPadding(0, 8, 0, 5);

        TextView profit = new TextView(this);
        profit.setText("+$0.00   (0.00%)");
        profit.setTextSize(16);
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
        quickTitle.setPadding(0, 28, 0, 12);

        main.addView(quickTitle);

        LinearLayout quickRow = new LinearLayout(this);
        quickRow.setOrientation(LinearLayout.HORIZONTAL);

        Button chartButton = createActionButton("📈\nCharts");
        Button tutorButton = createActionButton("🤖\nAI Tutor");
        Button rewardsButton = createActionButton("🎁\nRewards");
        Button profileButton = createActionButton("👤\nProfile");

        quickRow.addView(chartButton);
        quickRow.addView(tutorButton);
        quickRow.addView(rewardsButton);
        quickRow.addView(profileButton);

        main.addView(quickRow);

        // MARKETS
        TextView marketTitle = new TextView(this);
        marketTitle.setText("Markets");
        marketTitle.setTextSize(23);
        marketTitle.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        marketTitle.setTextColor(DARK);
        marketTitle.setPadding(0, 30, 0, 12);

        main.addView(marketTitle);

        HorizontalScrollView horizontalScroll =
                new HorizontalScrollView(this);

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

        main.addView(horizontalScroll);

        // START TRADING BUTTON
        Button startTrading = new Button(this);
        startTrading.setText("START PRACTICING");
        startTrading.setTextSize(18);
        startTrading.setTextColor(Color.WHITE);
        startTrading.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        startTrading.setAllCaps(false);
        startTrading.setBackground(createButtonBackground(GREEN));

        LinearLayout.LayoutParams tradingParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        65
                );

        tradingParams.setMargins(0, 30, 0, 0);

        main.addView(startTrading, tradingParams);

        animateButton(startTrading);

        // EDUCATIONAL MESSAGE
        TextView message = new TextView(this);
        message.setText(
                "💡 Practice with virtual money and learn trading without risking real money."
        );
        message.setTextSize(15);
        message.setTextColor(Color.DKGRAY);
        message.setGravity(Gravity.CENTER);
        message.setPadding(15, 25, 15, 5);

        main.addView(message);

        scrollView.addView(main);

        setContentView(scrollView);

        // Logo animation
        animateLogo(logo);
    }

    private LinearLayout createCard() {

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(25, 25, 25, 25);

        GradientDrawable background = new GradientDrawable();
        background.setColor(Color.rgb(18, 130, 78));
        background.setCornerRadius(30);

        card.setBackground(background);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        150
                );

        card.setLayoutParams(params);

        return card;
    }

    private Button createActionButton(String text) {

        Button button = new Button(this);
        button.setText(text);
        button.setTextSize(13);
        button.setTextColor(DARK);
        button.setAllCaps(false);

        GradientDrawable background = new GradientDrawable();
        background.setColor(Color.WHITE);
        background.setCornerRadius(25);

        button.setBackground(background);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(0, 90, 1);

        params.setMargins(4, 4, 4, 4);

        button.setLayoutParams(params);

        animateButton(button);

        return button;
    }

    private LinearLayout createMarketCard(
            String name,
            String price,
            String change) {

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(22, 18, 22, 18);

        GradientDrawable background = new GradientDrawable();
        background.setColor(Color.WHITE);
        background.setCornerRadius(25);

        card.setBackground(background);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(210, 145);

        params.setMargins(0, 0, 12, 0);

        card.setLayoutParams(params);

        TextView marketName = new TextView(this);
        marketName.setText(name);
        marketName.setTextSize(18);
        marketName.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        marketName.setTextColor(DARK);

        TextView marketPrice = new TextView(this);
        marketPrice.setText(price);
        marketPrice.setTextSize(19);
        marketPrice.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        marketPrice.setTextColor(DARK);
        marketPrice.setPadding(0, 10, 0, 5);

        TextView marketChange = new TextView(this);
        marketChange.setText(change);
        marketChange.setTextSize(15);

        if (change.startsWith("+")) {
            marketChange.setTextColor(GREEN);
        } else {
            marketChange.setTextColor(Color.RED);
        }

        card.addView(marketName);
        card.addView(marketPrice);
        card.addView(marketChange);

        animateCard(card);

        return card;
    }

    private GradientDrawable createButtonBackground(int color) {

        GradientDrawable background = new GradientDrawable();
        background.setColor(color);
        background.setCornerRadius(35);

        return background;
    }

    private void animateLogo(View view) {

        ScaleAnimation animation = new ScaleAnimation(
                0.6f,
                1.0f,
                0.6f,
                1.0f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
        );

        animation.setDuration(900);
        animation.setFillAfter(true);

        view.startAnimation(animation);
    }

    private void animateView(View view) {

        AlphaAnimation animation =
                new AlphaAnimation(0.0f, 1.0f);

        animation.setDuration(700);

        view.startAnimation(animation);
    }

    private void animateCard(View view) {

        ScaleAnimation animation = new ScaleAnimation(
                0.95f,
                1.0f,
                0.95f,
                1.0f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
        );

        animation.setDuration(500);
        animation.setFillAfter(true);

        view.startAnimation(animation);
    }

    private void animateButton(View view) {

        view.setOnClickListener(v -> {

            ScaleAnimation animation = new ScaleAnimation(
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
            animation.setRepeatMode(Animation.REVERSE);
            animation.setRepeatCount(1);

            v.startAnimation(animation);
        });
    }
            }
