package com.tradesimu.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends Activity {

private final int GREEN = Color.rgb(0, 210, 115);
private final int GREEN_SOFT = Color.rgb(105, 225, 165);
private final int DARK_GREEN = Color.rgb(3, 25, 18);
private final int BLACK_GREEN = Color.rgb(1, 10, 7);
private final int TEXT = Color.rgb(220, 235, 228);
private final int MUTED = Color.rgb(135, 165, 150);

private LinearLayout contentArea;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    showDashboard();
}

private void showDashboard() {

    LinearLayout root = new LinearLayout(this);
    root.setOrientation(LinearLayout.VERTICAL);

    TradingBackground background = new TradingBackground();
    root.addView(
            background,
            new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0,
                    1
            )
    );

    contentArea = new LinearLayout(this);
    contentArea.setOrientation(LinearLayout.VERTICAL);
    contentArea.setPadding(dp(18), dp(18), dp(18), dp(12));

    ScrollView scroll = new ScrollView(this);
    scroll.setFillViewport(true);
    scroll.setBackgroundColor(Color.TRANSPARENT);
    scroll.addView(contentArea);

    background.addView(
            scroll,
            new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            )
    );

    LinearLayout bottomNav = createBottomNavigation();

    root.addView(
            bottomNav,
            new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    dp(70)
            )
    );

    setContentView(root);
    showHome();
}

private void showHome() {

    contentArea.removeAllViews();

    TextView brand = text(
            "TradeSim",
            28,
            GREEN
    );
    brand.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

    contentArea.addView(brand);

    TextView status = text(
            "MARKETS • SIMULATED TRADING",
            11,
            MUTED
    );
    status.setPadding(0, dp(2), 0, dp(16));
    contentArea.addView(status);

    LinearLayout portfolio = card();

    TextView portfolioLabel = text(
            "PORTFOLIO VALUE",
            11,
            MUTED
    );

    TextView amount = text(
            "$10,000.00",
            32,
            TEXT
    );
    amount.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

    TextView gain = text(
            "+$0.00   •   0.00%",
            14,
            GREEN_SOFT
    );

    portfolio.addView(portfolioLabel);
    portfolio.addView(amount);
    portfolio.addView(gain);

    contentArea.addView(
            portfolio,
            marginParams(0, 0, 0, 14)
    );

    TextView chartTitle = text(
            "BTC / USD",
            20,
            TEXT
    );
    chartTitle.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

    contentArea.addView(chartTitle);

    TextView chartPrice = text(
            "$63,240.32    +2.41%",
            14,
            GREEN_SOFT
    );

    contentArea.addView(
            chartPrice,
            marginParams(0, 2, 0, 8)
    );

    MiniChart chart = new MiniChart();
    contentArea.addView(
            chart,
            new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    dp(190)
            )
    );

    TextView marketTitle = text(
            "Markets",
            20,
            TEXT
    );
    marketTitle.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
    marketTitle.setPadding(0, dp(20), 0, dp(8));
    contentArea.addView(marketTitle);

    addMarket("BTC/USD", "$63,240.32", "+2.41%");
    addMarket("ETH/USD", "$3,420.18", "+1.87%");
    addMarket("AAPL", "$227.16", "+0.92%");
    addMarket("TSLA", "$258.12", "-0.64%");

    TextView trade = text(
            "TRADE NOW",
            16,
            Color.BLACK
    );
    trade.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
    trade.setGravity(Gravity.CENTER);

    GradientDrawable tradeBg = new GradientDrawable();
    tradeBg.setColor(GREEN);
    tradeBg.setCornerRadius(dp(28));
    trade.setBackground(tradeBg);

    contentArea.addView(
            trade,
            new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    dp(58)
            )
    );

    trade.setOnClickListener(v -> showTrade());

    fadeIn(contentArea);
}

private void showTrade() {

    contentArea.removeAllViews();

    addPageTitle(
            "Trade",
            "Practice buying and selling with virtual money."
    );

    LinearLayout order = card();

    TextView pair = text(
            "BTC / USD",
            24,
            TEXT
    );
    pair.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

    TextView price = text(
            "$63,240.32",
            28,
            GREEN
    );
    price.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

    TextView mode = text(
            "SIMULATED MARKET",
            11,
            MUTED
    );

    order.addView(pair);
    order.addView(price);
    order.addView(mode);

    contentArea.addView(
            order,
            marginParams(0, 0, 0, 14)
    );

    addTradeButton("BUY BTC", GREEN, Color.BLACK);
    addTradeButton("SELL BTC", Color.rgb(40, 65, 55), TEXT);

    TextView info = text(
            "All trades are simulated. No real money is being used.",
            13,
            MUTED
    );
    info.setGravity(Gravity.CENTER);
    info.setPadding(0, dp(18), 0, 0);

    contentArea.addView(info);

    fadeIn(contentArea);
}

private void showAI() {

    contentArea.removeAllViews();

    addPageTitle(
            "AI Trading",
            "Learn and practice trading concepts."
    );

    LinearLayout aiCard = card();

    TextView icon = text(
            "AI",
            30,
            GREEN
    );
    icon.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

    TextView title = text(
            "TradeSim AI Tutor",
            22,
            TEXT
    );
    title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

    TextView description = text(
            "Ask questions about charts, markets, risk management and trading basics.",
            14,
            MUTED
    );
    description.setPadding(0, dp(8), 0, dp(16));

    aiCard.addView(icon);
    aiCard.addView(title);
    aiCard.addView(description);

    TextView start = text(
            "START LEARNING",
            15,
            Color.BLACK
    );
    start.setGravity(Gravity.CENTER);
    start.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

    GradientDrawable bg = new GradientDrawable();
    bg.setColor(GREEN);
    bg.setCornerRadius(dp(25));
    start.setBackground(bg);

    aiCard.addView(
            start,
            new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    dp(52)
            )
    );

    contentArea.addView(aiCard);

    fadeIn(contentArea);
}

private void showRewards() {

    contentArea.removeAllViews();

    addPageTitle(
            "Rewards",
            "Build your TradeSim reward balance."
    );

    LinearLayout rewardCard = card();

    TextView title = text(
            "TRADE POINTS",
            12,
            MUTED
    );

    TextView points = text(
            "0",
            38,
            GREEN
    );
    points.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

    TextView description = text(
            "Complete activities and, later, watch rewarded ads to earn virtual TradeSim rewards.",
            14,
            MUTED
    );

    rewardCard.addView(title);
    rewardCard.addView(points);
    rewardCard.addView(description);

    contentArea.addView(
            rewardCard,
            marginParams(0, 0, 0, 14)
    );

    LinearLayout daily = card();

    TextView dailyTitle = text(
            "Daily Reward",
            20,
            TEXT
    );
    dailyTitle.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

    TextView dailyText = text(
            "Your reward system will appear here.",
            14,
            MUTED
    );

    daily.addView(dailyTitle);
    daily.addView(dailyText);

    contentArea.addView(daily);

    fadeIn(contentArea);
}

private void showProfile() {

    contentArea.removeAllViews();

    addPageTitle(
            "Profile",
            "Your TradeSim account."
    );

    LinearLayout profile = card();

    TextView account = text(
            "TRADESIM ACCOUNT",
            11,
            MUTED
    );

    TextView name = text(
            "Trader",
            25,
            TEXT
    );
    name.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

    TextView status = text(
            "Virtual trading account",
            14,
            GREEN_SOFT
    );

    profile.addView(account);
    profile.addView(name);
    profile.addView(status);

    contentArea.addView(
            profile,
            marginParams(0, 0, 0, 12)
    );

    addProfileRow("Trade History");
    addProfileRow("Portfolio");
    addProfileRow("Notifications");
    addProfileRow("Settings");

    fadeIn(contentArea);
}

private void addPageTitle(String title, String subtitle) {

    TextView t = text(
            title,
            30,
            TEXT
    );
    t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

    contentArea.addView(t);

    TextView s = text(
            subtitle,
            14,
            MUTED
    );
    s.setPadding(0, dp(4), 0, dp(20));

    contentArea.addView(s);
}

private void addMarket(
        String name,
        String price,
        String change
) {

    LinearLayout row = card();
    row.setOrientation(LinearLayout.HORIZONTAL);
    row.setGravity(Gravity.CENTER_VERTICAL);

    LinearLayout left = new LinearLayout(this);
    left.setOrientation(LinearLayout.VERTICAL);

    TextView n = text(
            name,
            16,
            TEXT
    );
    n.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

    TextView p = text(
            price,
            14,
            MUTED
    );

    left.addView(n);
    left.addView(p);

    TextView c = text(
            change,
            14,
            change.startsWith("+")
                    ? GREEN
                    : Color.rgb(180, 120, 120)
    );
    c.setGravity(Gravity.CENTER_VERTICAL);

    row.addView(
            left,
            new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1
            )
    );

    row.addView(c);

    contentArea.addView(
            row,
            marginParams(0, 0, 0, 8)
    );
}

private void addTradeButton(
        String label,
        int backgroundColor,
        int textColor
) {

    TextView button = text(
            label,
            16,
            textColor
    );

    button.setGravity(Gravity.CENTER);
    button.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

    GradientDrawable bg = new GradientDrawable();
    bg.setColor(backgroundColor);
    bg.setCornerRadius(dp(26));
    button.setBackground(bg);

    contentArea.addView(
            button,
            marginParams(0, 0, 0, 10)
    );
}

private void addProfileRow(String label) {

    TextView row = text(
            label,
            16,
            TEXT
    );
    row.setGravity(Gravity.CENTER_VERTICAL);
    row.setPadding(dp(18), 0, dp(18), 0);

    GradientDrawable bg = new GradientDrawable();
    bg.setColor(Color.argb(125, 8, 30, 21));
    bg.setCornerRadius(dp(20));
    row.setBackground(bg);

    contentArea.addView(
            row,
            marginParams(
                    0,
                    0,
                    0,
                    8,
                    dp(52)
            )
    );
}

private LinearLayout card() {

    LinearLayout box = new LinearLayout(this);
    box.setOrientation(LinearLayout.VERTICAL);
    box.setPadding(
            dp(18),
            dp(16),
            dp(18),
            dp(16)
    );

    GradientDrawable bg = new GradientDrawable();
    bg.setColor(Color.argb(170, 7, 31, 21));
    bg.setStroke(dp(1), Color.argb(100, 0, 210, 115));
    bg.setCornerRadius(dp(22));

    box.setBackground(bg);

    return box;
}

private LinearLayout createBottomNavigation() {

    LinearLayout nav = new LinearLayout(this);
    nav.setOrientation(LinearLayout.HORIZONTAL);
    nav.setGravity(Gravity.CENTER);
    nav.setPadding(
            dp(6),
            dp(6),
            dp(6),
            dp(6)
    );

    GradientDrawable bg = new GradientDrawable();
    bg.setColor(Color.rgb(2, 18, 12));
    bg.setStroke(
            dp(1),
            Color.argb(100, 0, 210, 115)
    );
    nav.setBackground(bg);

    addNavItem(nav, "⌂", "Home", 0);
    addNavItem(nav, "▣", "Trade", 1);
    addNavItem(nav, "✦", "AI", 2);
    addNavItem(nav, "◇", "Rewards", 3);
    addNavItem(nav, "●", "Profile", 4);

    return nav;
}

private void addNavItem(
        LinearLayout nav,
        String icon,
        String label,
        int position
) {

    LinearLayout item = new LinearLayout(this);
    item.setOrientation(LinearLayout.VERTICAL);
    item.setGravity(Gravity.CENTER);

    TextView iconView = text(
            icon,
            21,
            GREEN
    );
    iconView.setGravity(Gravity.CENTER);

    TextView labelView = text(
            label,
            10,
            GREEN
    );
    labelView.setGravity(Gravity.CENTER);

    item.addView(iconView);
    item.addView(labelView);

    nav.addView(
            item,
            new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1
            )
    );

    item.setOnClickListener(v -> {

        if (position == 0) {
            showHome();
        } else if (position == 1) {
            showTrade();
        } else if (position == 2) {
            showAI();
        } else if (position == 3) {
            showRewards();
        } else {
            showProfile();
        }
    });
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
    t.setIncludeFontPadding(true);

    return t;
}

private LinearLayout.LayoutParams marginParams(
        int left,
        int top,
        int right,
        int bottom
) {

    LinearLayout.LayoutParams p =
            new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

    p.setMargins(
            dp(left),
            dp(top),
            dp(right),
            dp(bottom)
    );

    return p;
}

private LinearLayout.LayoutParams marginParams(
        int left,
        int top,
        int right,
        int bottom,
        int height
) {

    LinearLayout.LayoutParams p =
            new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    dp(height)
            );

    p.setMargins(
            dp(left),
            dp(top),
            dp(right),
            dp(bottom)
    );

    return p;
}

private int dp(int value) {
    return (int) (
            value *
            getResources()
                    .getDisplayMetrics()
                    .density
    );
}

private void fadeIn(View view) {

    AlphaAnimation animation =
            new AlphaAnimation(0.0f, 1.0f);

    animation.setDuration(450);
    view.startAnimation(animation);
}

private class MiniChart extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path path = new Path();
    private float phase = 0;

    public MiniChart() {
        super(MainActivity.this);

        paint.setStrokeWidth(dp(2));
        paint.setStyle(Paint.Style.STROKE);

        postDelayed(new Runnable() {
            @Override
            public void run() {
                phase += 0.08f;
                invalidate();
                postDelayed(this, 45);
            }
        }, 45);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        paint.setColor(
                Color.argb(110, 0, 210, 115)
        );
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dp(1));

        for (int i = 1; i < 6; i++) {

            float y =
                    getHeight() * i / 6f;

            canvas.drawLine(
                    0,
                    y,
                    getWidth(),
                    y,
                    paint
            );
        }

        path.reset();

        float width = getWidth();
        float height = getHeight();

        for (int x = 0; x <= width; x += 8) {

            float progress = x / width;

            float y =
                    height * 0.65f
                    - height * 0.25f * progress
                    - height * 0.07f *
                    (float) Math.sin(
                            progress * 10 + phase
                    )
                    - height * 0.04f *
                    (float) Math.sin(
                            progress * 25 + phase * 1.4f
                    );

            if (x == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }

        paint.setColor(GREEN);
        paint.setStrokeWidth(dp(3));
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawPath(path, paint);

        paint.setStyle(Paint.Style.FILL);

        float endY =
                height * 0.65f
                - height * 0.25f
                - height * 0.07f *
                (float) Math.sin(10 + phase)
                - height * 0.04f *
                (float) Math.sin(25 + phase * 1.4f);

        canvas.drawCircle(
                width,
                endY,
                dp(5),
                paint
        );
    }
}

private class TradingBackground extends LinearLayout {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path chartPath = new Path();
    private float animation = 0;

    public TradingBackground() {

        super(MainActivity.this);

        setOrientation(LinearLayout.VERTICAL);

        GradientDrawable gradient =
                new GradientDrawable(
                        GradientDrawable.Orientation.TL_BR,
                        new int[]{
                                DARK_GREEN,
                                BLACK_GREEN,
                                Color.rgb(0, 15, 9)
                        }
                );

        setBackground(gradient);

        postDelayed(new Runnable() {
            @Override
            public void run() {

                animation += 0.015f;

                invalidate();

                postDelayed(this, 45);
            }
        }, 45);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        drawTradingBackground(canvas);

        super.dispatchDraw(canvas);
    }

    private void drawTradingBackground(Canvas canvas) {

        int width = getWidth();
        int height = getHeight();

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dp(1));
        paint.setColor(
                Color.argb(28, 0, 210, 115)
        );

        int spacing = dp(55);

        for (int x = 0; x < width; x += spacing) {

            canvas.drawLine(
                    x,
                    0,
                    x,
                    height,
                    paint
            );
        }

        for (int y = 0; y < height; y += spacing) {

            canvas.drawLine(
                    0,
                    y,
                    width,
                    y,
                    paint
            );
        }

        chartPath.reset();

        float chartHeight =
                Math.max(height, dp(500));

        for (int x = 0; x <= width; x += 10) {

            float progress =
                    x / (float) Math.max(width, 1);

            float y =
                    chartHeight * 0.50f
                    - chartHeight * 0.12f * progress
                    - chartHeight * 0.06f *
                    (float) Math.sin(
                            progress * 8 + animation
                    )
                    - chartHeight * 0.025f *
                    (float) Math.sin(
                            progress * 22 + animation * 1.5f
                    );

            if (x == 0) {
                chartPath.moveTo(x, y);
            } else {
                chartPath.lineTo(x, y);
            }
        }

        paint.setColor(
                Color.argb(55, 0, 210, 115)
        );
        paint.setStrokeWidth(dp(2));

        canvas.drawPath(
                chartPath,
                paint
        );

        paint.setStyle(Paint.Style.FILL);

        float pulse =
                (float)
                (
                        0.5
                        +
                        0.5 *
                        Math.sin(animation * 3)
                );

        paint.setColor(
                Color.argb(
                        (int) (20 + 35 * pulse),
                        0,
                        210,
                        115
                )
        );

        canvas.drawCircle(
                width * 0.78f,
                height * 0.30f,
                dp(70) + dp(20) * pulse,
                paint
        );
    }
}

        }
