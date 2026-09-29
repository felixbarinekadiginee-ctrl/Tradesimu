package com.tradesimu.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    double balance = 10000.00;
    double btcPrice = 63240.32;
    double btcOwned = 0.0;

    TextView balanceText;
    TextView holdingsText;

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

        TextView markets = new TextView(this);
        markets.setText("Markets");
        markets.setTextSize(24);
        markets.setPadding(0, 30, 0, 20);

        balanceText = new TextView(this);
        balanceText.setText("Virtual Balance: $" + String.format("%.2f", balance));
        balanceText.setTextSize(22);
        balanceText.setTextColor(Color.rgb(0, 160, 70));

        holdingsText = new TextView(this);
        holdingsText.setText("BTC Owned: 0.0000 BTC");
        holdingsText.setTextSize(18);
        holdingsText.setPadding(0, 15, 0, 25);

        layout.addView(title);
        layout.addView(balanceText);
        layout.addView(holdingsText);
        layout.addView(markets);

        addMarket(layout, "BTC/USD", "Bitcoin", "$63,240.32", "+2.48%");

        Button buyButton = new Button(this);
        buyButton.setText("BUY BTC — 0.01 BTC");
        buyButton.setTextSize(18);

        buyButton.setOnClickListener(v -> buyBitcoin());

        layout.addView(buyButton);

        addMarket(layout, "ETH/USD", "Ethereum", "$3,412.76", "+1.92%");
        addMarket(layout, "AAPL", "Apple Inc.", "$227.48", "+0.85%");
        addMarket(layout, "TSLA", "Tesla Inc.", "$248.17", "-1.23%");

        setContentView(layout);
    }

    private void buyBitcoin() {

        double amount = 0.01;
        double cost = btcPrice * amount;

        if (balance >= cost) {

            balance -= cost;
            btcOwned += amount;

            balanceText.setText(
                    "Virtual Balance: $" + String.format("%.2f", balance)
            );

            holdingsText.setText(
                    "BTC Owned: " + String.format("%.4f", btcOwned) + " BTC"
            );

            Toast.makeText(
                    this,
                    "Bought 0.01 BTC successfully!",
                    Toast.LENGTH_SHORT
            ).show();

        } else {

            Toast.makeText(
                    this,
                    "Not enough virtual balance!",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void addMarket(
            LinearLayout layout,
            String name,
            String description,
            String price,
            String change) {

        TextView market = new TextView(this);

        market.setText(
                name + "\n" +
                description + "\n" +
                price + "   " + change
        );

        market.setTextSize(19);
        market.setPadding(20, 20, 20, 20);
        market.setTextColor(Color.rgb(0, 150, 70));

        layout.addView(market);
    }
            }
