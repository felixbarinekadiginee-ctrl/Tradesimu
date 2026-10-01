package com.tradesimu.app;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

public class TradeActivity extends Activity {

    private final int GREEN = Color.rgb(0, 210, 115);
    private final int DARK = Color.rgb(3, 15, 10);
    private final int CARD = Color.rgb(5, 72, 42);

    private int dp(int n) {
        return (int) (n * getResources()
                .getDisplayMetrics().density + 0.5f);
    }

    private TextView label(String text, float size) {
        TextView v = new TextView(this);
        v.setText(text);
        v.setTextColor(GREEN);
        v.setTextSize(size);
        v.setGravity(Gravity.CENTER_VERTICAL);
        return v;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(DARK);
        root.setPadding(dp(16), dp(12), dp(16), 0);

        // TOP BAR
        LinearLayout top = new LinearLayout(this);
        top.setGravity(Gravity.CENTER_VERTICAL);

        TextView profile = label("●", 25);
        TextView title = label("Trade", 22);
        title.setTypeface(null, 1);

        TextView bell = label("♧", 24);
        bell.setGravity(Gravity.CENTER);

        top.addView(profile,
                new LinearLayout.LayoutParams(dp(45), dp(50)));

        top.addView(title,
                new LinearLayout.LayoutParams(0, dp(50), 1));

        top.addView(bell,
                new LinearLayout.LayoutParams(dp(45), dp(50)));

        root.addView(top);

        // BTC CARD
        LinearLayout coin = new LinearLayout(this);
        coin.setOrientation(LinearLayout.VERTICAL);
        coin.setPadding(dp(16), dp(8), dp(16), dp(8));
        coin.setBackgroundColor(CARD);

        TextView pair = label("BTC / USD", 17);
        pair.setTypeface(null, 1);

        TextView price = label("$63,240.32", 26);
        price.setTypeface(null, 1);

        TextView change = label("+2.48%   Today", 14);

        coin.addView(pair);
        coin.addView(price);
        coin.addView(change);

        root.addView(coin,
                new LinearLayout.LayoutParams(-1, dp(105)));

        Space gap1 = new Space(this);
        root.addView(gap1,
                new LinearLayout.LayoutParams(1, dp(10)));

        // CHART
        ChartView chart = new ChartView(this);

        root.addView(chart,
                new LinearLayout.LayoutParams(
                        -1, 0, 1));

        // BUY / SELL
        LinearLayout actions = new LinearLayout(this);
        actions.setPadding(0, dp(10), 0, dp(10));

        Button buy = new Button(this);
        buy.setText("BUY");
        buy.setTextColor(Color.BLACK);
        buy.setTextSize(16);
        buy.setBackgroundColor(GREEN);

        Button sell = new Button(this);
        sell.setText("SELL");
        sell.setTextColor(Color.BLACK);
        sell.setTextSize(16);
        sell.setBackgroundColor(GREEN);

        actions.addView(buy,
                new LinearLayout.LayoutParams(
                        0, dp(55), 1));

        Space buttonGap = new Space(this);
        actions.addView(buttonGap,
                new LinearLayout.LayoutParams(dp(10), 1));

        actions.addView(sell,
                new LinearLayout.LayoutParams(
                        0, dp(55), 1));

        root.addView(actions);

        // BOTTOM NAVIGATION
        LinearLayout nav = new LinearLayout(this);
        nav.setGravity(Gravity.CENTER);
        nav.setBackgroundColor(Color.rgb(4, 25, 16));

        String[] names = {
                "⌂\nHome",
                "▥\nTrade",
                "AI\nAI",
                "★\nRewards",
                "●\nProfile"
        };

        for (String name : names) {
            TextView item = label(name, 12);
            item.setGravity(Gravity.CENTER);
            nav.addView(item,
                    new LinearLayout.LayoutParams(
                            0, dp(65), 1));
        }

        root.addView(nav);

        setContentView(root);
    }

    // SIMPLE TRADING CHART
    private class ChartView extends View {

        private final Paint paint =
                new Paint(Paint.ANTI_ALIAS_FLAG);

        ChartView(Activity activity) {
            super(activity);
            setBackgroundColor(CARD);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            paint.setColor(Color.rgb(0, 210, 115));
            paint.setStrokeWidth(dp(3));
            paint.setStyle(Paint.Style.STROKE);

            float w = getWidth();
            float h = getHeight();

            Path line = new Path();

            line.moveTo(0, h * 0.72f);
            line.lineTo(w * 0.12f, h * 0.65f);
            line.lineTo(w * 0.23f, h * 0.70f);
            line.lineTo(w * 0.34f, h * 0.43f);
            line.lineTo(w * 0.46f, h * 0.53f);
            line.lineTo(w * 0.57f, h * 0.29f);
            line.lineTo(w * 0.69f, h * 0.40f);
            line.lineTo(w * 0.81f, h * 0.18f);
            line.lineTo(w, h * 0.30f);

            canvas.drawPath(line, paint);
        }
    }
            }
