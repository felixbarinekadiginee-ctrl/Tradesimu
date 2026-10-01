package com.tradesimu.app;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class TradeActivity extends Activity {

    int green = Color.rgb(0,210,115);
    int card = Color.rgb(5,72,42);
    int dark = Color.rgb(3,15,10);

    int dp(float n) {
        return (int)(n * getResources().getDisplayMetrics().density + .5f);
    }

    TextView text(String s, float size) {
        TextView t = new TextView(this);
        t.setText(s);
        t.setTextColor(green);
        t.setTextSize(size);
        t.setGravity(Gravity.CENTER_VERTICAL);
        return t;
    }

    GradientView box() {
        GradientView v = new GradientView();
        v.setBackgroundColor(card);
        return v;
    }

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(dark);
        root.setPadding(dp(16), dp(14), dp(16), 0);

        // TOP BAR
        LinearLayout top = new LinearLayout(this);
        top.setGravity(Gravity.CENTER_VERTICAL);

        TextView profile = text("●", 26);
        TextView title = text("Trade", 22);
        title.setTypeface(null, 1);

        TextView bell = text("♧", 25);
        bell.setGravity(Gravity.CENTER);

        top.addView(profile, new LinearLayout.LayoutParams(dp(45), dp(50)));
        top.addView(title, new LinearLayout.LayoutParams(0, dp(50), 1));
        top.addView(bell, new LinearLayout.LayoutParams(dp(45), dp(50)));

        root.addView(top);

        // COIN CARD
        LinearLayout coin = box();
        coin.setOrientation(LinearLayout.VERTICAL);
        coin.setPadding(dp(16), dp(10), dp(16), dp(10));

        TextView btc = text("BTC / USD", 18);
        btc.setTypeface(null, 1);

        TextView price = text("$63,240.32", 27);
        price.setTypeface(null, 1);

        TextView change = text("+2.48%   Today", 14);

        coin.addView(btc);
        coin.addView(price);
        coin.addView(change);

        root.addView(coin,
                new LinearLayout.LayoutParams(-1, dp(105)));

        Space sp = new Space(this);
        root.addView(sp, new LinearLayout.LayoutParams(1, dp(12)));

        // CHART
        GradientView chart = new GradientView();
        chart.setBackgroundColor(card);
        chart.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        root.addView(chart,
                new LinearLayout.LayoutParams(-1, 0, 1));

        // BUY / SELL
        LinearLayout actions = new LinearLayout(this);
        actions.setPadding(0, dp(12), 0, dp(12));

        Button buy = new Button(this);
        buy.setText("BUY");
        buy.setTextColor(Color.BLACK);
        buy.setTextSize(16);
        buy.setBackgroundColor(green);

        Button sell = new Button(this);
        sell.setText("SELL");
        sell.setTextColor(Color.BLACK);
        sell.setTextSize(16);
        sell.setBackgroundColor(green);

        actions.addView(buy,
                new LinearLayout.LayoutParams(0, dp(55), 1));

        Space gap = new Space(this);
        actions.addView(gap,
                new LinearLayout.LayoutParams(dp(10), 1));

        actions.addView(sell,
                new LinearLayout.LayoutParams(0, dp(55), 1));

        root.addView(actions);

        // ANDROID-STYLE BOTTOM NAVIGATION
        LinearLayout nav = new LinearLayout(this);
        nav.setGravity(Gravity.CENTER);
        nav.setBackgroundColor(Color.rgb(4,25,16));

        String[] names = {"⌂\nHome", "▥\nTrade", "AI\nAI", "★\nRewards", "●\nProfile"};

        for (String n : names) {
            TextView item = text(n, 12);
            item.setGravity(Gravity.CENTER);
            item.setPadding(0, dp(6), 0, dp(6));
            nav.addView(item,
                    new LinearLayout.LayoutParams(0, dp(65), 1));
        }

        root.addView(nav);

        setContentView(root);
    }

    class GradientView extends View {
        Paint p = new Paint(1);

        GradientView() {
            super(TradeActivity.this);
        }

        @Override
        protected void onDraw(Canvas c) {
            super.onDraw(c);

            p.setColor(Color.rgb(8,110,65));
            p.setStrokeWidth(dp(3));
            p.setStyle(Paint.Style.STROKE);

            float w = getWidth();
            float h = getHeight();

            android.graphics.Path path =
                    new android.graphics.Path();

            path.moveTo(0, h * .72f);
            path.lineTo(w*.12f, h*.65f);
            path.lineTo(w*.22f, h*.70f);
            path.lineTo(w*.34f, h*.42f);
            path.lineTo(w*.45f, h*.53f);
            path.lineTo(w*.57f, h*.28f);
            path.lineTo(w*.68f, h*.40f);
            path.lineTo(w*.80f, h*.18f);
            path.lineTo(w, h*.30f);

            c.drawPath(path, p);
        }
    }
}
