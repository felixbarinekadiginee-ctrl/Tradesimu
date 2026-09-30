package com.tradesimu.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.*;
import android.graphics.drawable.GradientDrawable;
import android.view.*;
import android.widget.*;
import android.content.Intent;
import android.content.Context;
import android.animation.ValueAnimator;

public class IntroductionActivity extends Activity {

    private final int GREEN = Color.rgb(0, 180, 95);
    private final int DARK_GREEN = Color.rgb(2, 18, 12);
    private final int BLACK_GREEN = Color.rgb(1, 8, 6);

    private final int TEXT_MAIN = Color.rgb(205, 220, 212);
    private final int TEXT_SECONDARY = Color.rgb(145, 165, 155);

    private FrameLayout root;
    private LinearLayout content;

    private int page = 0;

    private final String[] titles = {
            "Understand the Market",
            "Practice With Virtual Money",
            "Make Your First Practice Trade"
    };

    private final String[] descriptions = {
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

        // Dark animated trading background
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
                dp(30),
                dp(45),
                dp(30),
                dp(30)
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

        // TradeSim name
        TextView logoText = textView(
                "TradeSim",
                28,
                GREEN,
                Gravity.CENTER
        );

        logoText.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        content.addView(
                logoText,
                new LinearLayout.LayoutParams(
                        -1,
                        dp(45)
                )
        );

        // Small progress indicator
        TextView progressText = textView(
                (page + 1) + " / 3",
                13,
                TEXT_SECONDARY,
                Gravity.CENTER
        );

        LinearLayout.LayoutParams progressParams =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(35)
                );

        progressParams.topMargin = dp(20);

        content.addView(
                progressText,
                progressParams
        );

        // Smaller, dimmer icon
        TextView icon = textView(
                page == 0
                        ? "📈"
                        : page == 1
                        ? "💰"
                        : "🎯",
                48,
                TEXT_MAIN,
                Gravity.CENTER
        );

        LinearLayout.LayoutParams iconParams =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(80)
                );

        iconParams.topMargin = dp(25);

        content.addView(
                icon,
                iconParams
        );

        // Main title
        TextView title = textView(
                titles[page],
                25,
                TEXT_MAIN,
                Gravity.CENTER
        );

        title.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        title.setIncludeFontPadding(true);

        LinearLayout.LayoutParams titleParams =
                new LinearLayout.LayoutParams(
                        -1,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        titleParams.topMargin = dp(20);

        content.addView(
                title,
                titleParams
        );

        // Description
        TextView description = textView(
                descriptions[page],
                16,
                TEXT_SECONDARY,
                Gravity.CENTER
        );

        description.setLineSpacing(
                dp(4),
                1.0f
        );

        LinearLayout.LayoutParams descriptionParams =
                new LinearLayout.LayoutParams(
                        -1,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        descriptionParams.topMargin = dp(18);

        content.addView(
                description,
                descriptionParams
        );

        // Push controls toward bottom
        Space space = new Space(this);

        content.addView(
                space,
                new LinearLayout.LayoutParams(
                        1,
                        0,
                        1
                )
        );

        // Page dots
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
                        Color.rgb(45, 65, 55)
                );
            }

            dot.setBackground(dotBackground);

            LinearLayout.LayoutParams dotParams =
                    new LinearLayout.LayoutParams(
                            dp(i == page ? 20 : 7),
                            dp(7)
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
                        dp(20)
                )
        );

        // Continue / Start button
        Button actionButton = createButton(
                page == 2
                        ? "START PRACTICING"
                        : "CONTINUE"
        );

        LinearLayout.LayoutParams buttonParams =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(55)
                );

        buttonParams.topMargin = dp(22);

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
                .setDuration(450)
                .start();
    }

    private Button createButton(String text) {

        Button button = new Button(this);

        button.setText(text);
        button.setTextSize(14);
        button.setTextColor(
                Color.rgb(1, 15, 9)
        );

        button.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        button.setAllCaps(false);

        GradientDrawable background =
                new GradientDrawable();

        background.setColor(GREEN);
        background.setCornerRadius(
                dp(16)
        );

        button.setBackground(background);

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

        private final Paint paint =
                new Paint(Paint.ANTI_ALIAS_FLAG);

        private float progress = 0f;

        public TradingBackground(Context context) {

            super(context);

            ValueAnimator animator =
                    ValueAnimator.ofFloat(
                            0f,
                            1f
                    );

            animator.setDuration(7000);

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
                                        animation
                                                .getAnimatedValue();

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

            // Very dark green gradient
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

            // Very subtle grid
            paint.setColor(
                    Color.argb(
                            18,
                            0,
                            180,
                            95
                    )
            );

            paint.setStrokeWidth(1);

            int spacing = dp(55);

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

            // Dim animated trading line
            paint.setColor(
                    Color.argb(
                            75,
                            0,
                            190,
                            100
                    )
            );

            paint.setStyle(
                    Paint.Style.STROKE
            );

            paint.setStrokeWidth(
                    dp(2)
            );

            Path chart = new Path();

            float baseY =
                    height * 0.74f;

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
                                        x * 0.024
                                                + progress * 6.28
                                );

                float wave2 =
                        (float)
                                Math.sin(
                                        x * 0.010
                                                + progress * 4
                                );

                float y =
                        baseY
                                - wave * dp(18)
                                - wave2 * dp(12)
                                - (x * 0.07f);

                chart.lineTo(
                        x,
                        y
                );
            }

            canvas.drawPath(
                    chart,
                    paint
            );

            paint.setStyle(
                    Paint.Style.FILL
            );

            // Very subtle glow
            float pulse =
                    (float)
                            (
                                    0.5
                                            + 0.5
                                            * Math.sin(
                                                    progress * 6.28
                                            )
                            );

            paint.setColor(
                    Color.argb(
                            (int)
                                    (12 + pulse * 15),
                            0,
                            180,
                            95
                    )
            );

            canvas.drawCircle(
                    width / 2f,
                    height * 0.42f,
                    dp(90),
                    paint
            );
        }
    }
    }
