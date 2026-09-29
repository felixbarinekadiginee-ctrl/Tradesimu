package com.tradesimu.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 50, 30, 30);

        TextView title = new TextView(this);
        title.setText("TradeSim");
        title.setTextSize(32);
        title.setTextColor(Color.BLACK);

        TextView subtitle = new TextView(this);
        subtitle.setText("Markets");
        subtitle.setTextSize(22);
        subtitle.setTextColor(Color.DKGRAY);
        subtitle.setPadding(0, 30, 0, 20);

        layout.addView(title);
        layout.addView(subtitle);

        addMarket(layout, "BTC/USD", "Bitcoin", "$63,240.32", "+2.48%", true);
        addMarket(layout, "ETH/USD", "Ethereum", "$3,412.76", "+1.92%", true);
        addMarket(layout, "AAPL", "Apple Inc.", "$227.48", "+0.85%", true);
        addMarket(layout, "TSLA", "Tesla Inc.", "$248.17", "-1.23%", false);

        setContentView(layout);
    }

    private void addMarket(
            LinearLayout layout,
            String name,
            String description,
            String price,
            String change,
            boolean positive) {

        TextView market = new TextView(this);

        market.setText(
                name + "\n" +
                description + "\n" +
                price + "   " + change
        );

        market.setTextSize(19);
        market.setPadding(20, 25, 20, 25);
        market.setTextColor(positive
                ? Color.rgb(0, 160, 70)
                : Color.RED);

        layout.addView(market);
    }
    }
