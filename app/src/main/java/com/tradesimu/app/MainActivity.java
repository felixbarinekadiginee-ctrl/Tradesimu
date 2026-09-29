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
        layout.setPadding(30, 60, 30, 30);

        TextView title = new TextView(this);
        title.setText("TradeSim");
        title.setTextSize(32);
        title.setTextColor(Color.BLACK);

        TextView balanceLabel = new TextView(this);
        balanceLabel.setText("Virtual Balance");
        balanceLabel.setTextSize(18);
        balanceLabel.setPadding(0, 50, 0, 10);

        TextView balance = new TextView(this);
        balance.setText("$10,000.00");
        balance.setTextSize(36);
        balance.setTextColor(Color.rgb(0, 160, 70));

        layout.addView(title);
        layout.addView(balanceLabel);
        layout.addView(balance);

        setContentView(layout);
    }
}
