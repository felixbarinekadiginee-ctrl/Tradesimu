package com.tradesimu.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.*;
import android.graphics.drawable.GradientDrawable;
import android.view.*;
import android.view.animation.*;
import android.widget.*;
import android.content.Intent;
import android.content.Context;
import android.animation.ValueAnimator;

public class IntroductionActivity extends Activity {
    private final int GREEN = Color.rgb(0, 220, 120);
    private final int DARK_GREEN = Color.rgb(3, 25, 18);
    private final int BLACK_GREEN = Color.rgb(2, 12, 9);
    private final int WHITE = Color.WHITE;

    private FrameLayout root;
    private LinearLayout content;
    private int page = 0;

    private String[] titles = {
            "Understand the Market",
            "Practice With Virtual Money",
            "Make Your First Practice Trade"
    };

    private String[] descriptions = {
            "Learn how markets work, understand price movements, and build your trading knowledge step by step.",
            "Practice trading without risking real money. Use virtual funds to learn, experiment, and improve.",
            "Put what you've learned into practice. Make your first virtual trade and start building your trading skills."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showPage();
    }

    private void showPage() {

        root = new FrameLayout(this);

        root.addView(
                new TradingBackground(this),
                new FrameLayout.LayoutParams(
                        -1,
                        -1
                )
        );

        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setGravity(Gravity.CENTER_HORIZONTAL);
        content.setPadding(
                dp(28),
                dp(45),
                dp(28),
                dp(35)
        );

        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.addView(content);

        root.addView(
                scrollView,
                new FrameLayout.LayoutParams(
                        -1,
                        -1
                )
        );

        TextView logo = textView(
                "TradeSim",
                34,
                GREEN,
                Gravity.CENTER
        );

        logo.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

        content.addView(
                logo,
                new LinearLayout.LayoutParams(
                        -1,
                        dp(55)
                )
        );

        TextView step = textView(
                "STEP " + (page + 1) + " OF 3",
                13,
                Color.LTGRAY,
                Gravity.CENTER
        );

        LinearLayout.LayoutParams stepParams =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(40)
                );

        stepParams.topMargin = dp(18);

        content.addView(step, stepParams);

        TextView icon = textView(
                page == 0 ? "📈" :
                        page == 1 ? "💰" : "🎯",
                65,
                WHITE,
                Gravity.CENTER
        );

        LinearLayout.LayoutParams iconParams =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(100)
                );

        iconParams.topMargin = dp(35);

        content.addView(icon, iconParams);

        TextView title = textView(
                titles[page],
                28,
                WHITE,
                Gravity.CENTER
        );

        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

        LinearLayout.LayoutParams titleParams =
                new LinearLayout.LayoutParams(
                        -1,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        titleParams.topMargin = dp(20);

        content.addView(title, titleParams);

        TextView description = textView(
                descriptions[page],
                17,
                Color.rgb(220, 240, 232),
                Gravity.CENTER
        );

        description.setLineSpacing(dp(5), 1.0f);

        LinearLayout.LayoutParams descriptionParams =
                new LinearLayout.LayoutParams(
                        -1,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        descriptionParams.topMargin = dp(20);

        content.addView(description, descriptionParams);

        Space space = new Space(this);

        content.addView(
                space,
                new LinearLayout.LayoutParams(
                        1,
                        0,
                        1
                )
        );

        LinearLayout dots = new LinearLayout(this);
        dots.setGravity(Gravity.CENTER);

        for (int i = 0; i < 3; i++) {

            View dot = new View(this);

            GradientDrawable dotBackground =
                    new GradientDrawable();

            dotBackground.setShape(
                    GradientDrawable.OVAL
            );

            if (i == page) {
                dotBackground.setColor(GREEN);
            } else {
                dotBackground.setColor(
                        Color.rgb(70, 100, 88)
                );
            }

            dot.setBackground(dotBackground);

            LinearLayout.LayoutParams dotParams =
                    new LinearLayout.LayoutParams(
                            dp(i == page ? 24 : 9),
                            dp(9)
                    );

            dotParams.setMargins(
                    dp(5),
                    0,
                    dp(5),
                    0
            );

            dots.addView(dot, dotParams);
        }

        content.addView(
                dots,
                new LinearLayout.LayoutParams(
                        -1,
                        dp(25)
                )
        );

        Button actionButton = button(
                page == 2
                        ? "START PRACTICING"
                        : "CONTINUE"
        );

        LinearLayout.LayoutParams buttonParams =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(58)
                );

        buttonParams.topMargin = dp(25);

        content.addView(
                actionButton,
                buttonParams
        );

        actionButton.setOnClickListener(v -> {
            if (page < 2) {
                page++;
                showPage();
            } else {
                Intent intent =
                        new Intent(
                                IntroductionActivity.this,
                                MainActivity.class
                        );

                startActivity(intent);
                finish();
            }
        });

        setContentView(root);

        root.setAlpha(0f);

        root.animate()
                .alpha(1f)
                .setDuration(500)
                .start();
    }

    private Button button(String text) {

        Button button = new Button(this);

        button.setText(text);
        button.setTextColor(BLACK_GREEN);
        button.setTextSize(15);

        button.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        button.setAllCaps(false);
        button.setGravity(Gravity.CENTER);

        GradientDrawable background =
                new GradientDrawable();

        background.setColor(GREEN);
        background.setCornerRadius(dp(18));

        button.setBackground(background);
        button.setElevation(dp(5));

        return button;
    }

    private TextView textView(
            String text,
            float size,
            int color,
            int gravity
    ) {

        TextView view = new TextView(this);

        view.setText(text);
        view.setTextSize(size);
        view.setTextColor(color);
        view.setGravity(gravity);
        view.setIncludeFontPadding(true);

        return view;
    }

    private int dp(int value) {

        return (int) (
                value *
                getResources()
                        .getDisplayMetrics()
                        .density
        );
    }

    private class TradingBackground extends View {

        private Paint paint =
                new Paint(Paint.ANTI_ALIAS_FLAG);

        private float progress = 0f;

        public TradingBackground(Context context) {
            super(context);

            paint.setStrokeWidth(dp(2));

            ValueAnimator animator =
                    ValueAnimator.ofFloat(
                            0f,
                            1f
                    );

            animator.setDuration(5000);

            animator.setRepeatCount(
                    ValueAnimator.INFINITE
            );

            animator.setRepeatMode(
                    ValueAnimator.RESTART
            );

            animator.addUpdateListener(
                    animation -> {

                        progress =
                                (float)
                                        animation.getAnimatedValue();

                        invalidate();
                    }
            );

            animator.start();
        }

        @Override
        protected void onDraw(Canvas canvas) {

            super.onDraw(canvas);

            int width = getWidth();
            int height = getHeight();

            LinearGradient gradient =
                    new LinearGradient(
                            0,
                            0,
                            width,
                            height,
                            DARK_GREEN,
                            BLACK_GREEN,
                            Shader.TileMode.CLAMP
                    );

            paint.setShader(gradient);

            canvas.drawRect(
                    0,
                    0,
                    width,
                    height,
                    paint
            );

            paint.setShader(null);

            paint.setColor(
                    Color.argb(
                            35,
                            0,
                            220,
                            120
                    )
            );

            paint.setStrokeWidth(1);

            int spacing = dp(45);

            for (
                    int x = 0;
                    x < width;
                    x += spacing
            ) {
                canvas.drawLine(
                        x,
                        0,
                        x,
                        height,
                        paint
                );
            }

            for (
                    int y = 0;
                    y < height;
                    y += spacing
            ) {
                canvas.drawLine(
                        0,
                        y,
                        width,
                        y,
                        paint
                );
            }

            paint.setColor(GREEN);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(dp(2));

            Path chart = new Path();

            float baseY =
                    height * 0.72f;

            chart.moveTo(
                    0,
                    baseY
            );

            for (
                    int x = 0;
                    x <= width;
                    x += dp(18)
            ) {

                float wave =
                        (float)
                                Math.sin(
                                        x * 0.025
                                                + progress * 6.28
                                );

                float wave2 =
                        (float)
                                Math.sin(
                                        x * 0.011
                                                + progress * 4
                                );

                float y =
                        baseY
                                - wave * dp(22)
                                - wave2 * dp(18)
                                - (x * 0.10f);

                chart.lineTo(
                        x,
                        y
                );
            }

            canvas.drawPath(
                    chart,
                    paint
            );

            paint.setStyle(Paint.Style.FILL);

            float pulse =
                    (float)
                            (
                                    0.5
                                            + 0.5
                                            * Math.sin(
                                                    progress
                                                            * 6.28
                                            )
                            );

            paint.setColor(
                    Color.argb(
                            (int)
                                    (35 + pulse * 45),
                            0,
                            220,
                            120
                    )
            );

            canvas.drawCircle(
                    width / 2f,
                    height * 0.38f,
                    dp(85),
                    paint
            );
        }
    }
            }
