package com.tradesimu.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.*;
import android.graphics.drawable.GradientDrawable;
import android.view.*;
import android.view.animation.*;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.widget.*;
import android.text.InputType;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.android.gms.tasks.Task;

public class WelcomeActivity extends Activity {

    private final int GREEN = Color.rgb(0, 220, 120);
    private final int DARK_GREEN = Color.rgb(3, 25, 18);
    private final int BLACK_GREEN = Color.rgb(2, 12, 9);
    private final int WHITE = Color.WHITE;

    private FrameLayout root;
    private LinearLayout content;

    private FirebaseAuth mAuth;
    private GoogleSignInClient googleSignInClient;

    private static final int RC_GOOGLE_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Google Sign-In setup
        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(
                        GoogleSignInOptions.DEFAULT_SIGN_IN
                )
                        .requestIdToken(
                                getString(
                                        com.tradesimu.app.R.string.default_web_client_id
                                )
                        )
                        .requestEmail()
                        .build();

        googleSignInClient =
                GoogleSignIn.getClient(this, gso);

        showWelcome();
    }

    // =========================
    // WELCOME SCREEN
    // =========================

    private void showWelcome() {

        root = createBackground();

        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setGravity(Gravity.CENTER);
        content.setPadding(dp(28), dp(30), dp(28), dp(30));

        root.addView(content, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));

        ImageView logo = new ImageView(this);
        logo.setImageResource(R.drawable.tradesim_logo);
        logo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        LinearLayout.LayoutParams logoParams =
                new LinearLayout.LayoutParams(dp(145), dp(145));

        logoParams.setMargins(0, 0, 0, dp(15));
        content.addView(logo, logoParams);

        animateLogo(logo);

        TextView title = text("Welcome to TradeSim", 30, WHITE);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        content.addView(title);

        TextView subtitle = text(
                "Learn. Practice. Trade.",
                17,
                Color.rgb(190, 235, 215)
        );

        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, dp(8), 0, dp(35));
        content.addView(subtitle);

        Button signIn = greenButton("Sign In");
        Button signUp = outlineButton("Sign Up");

        content.addView(signIn, buttonParams());
        content.addView(signUp, buttonParams());

        signIn.setOnClickListener(v -> showLogin());
        signUp.setOnClickListener(v -> showSignUp());

        setContentView(root);
    }

    // =========================
    // LOGIN SCREEN
    // =========================

    private void showLogin() {

        root = createBackground();

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);

        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setGravity(Gravity.CENTER_HORIZONTAL);
        content.setPadding(dp(28), dp(45), dp(28), dp(30));

        scroll.addView(content);

        root.addView(scroll, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));

        TextView back = text(
                "← Back",
                17,
                Color.rgb(180, 240, 210)
        );

        back.setPadding(0, 0, 0, dp(25));

        content.addView(back,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

        back.setOnClickListener(v -> showWelcome());

        ImageView logo = new ImageView(this);
        logo.setImageResource(R.drawable.tradesim_logo);
        logo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        content.addView(logo,
                new LinearLayout.LayoutParams(dp(90), dp(90)));

        animateLogo(logo);

        TextView title = text("Sign In", 30, WHITE);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        content.addView(title);

        TextView subtitle = text(
                "Welcome back to TradeSim",
                16,
                Color.rgb(185, 225, 205)
        );

        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, dp(7), 0, dp(25));
        content.addView(subtitle);

        EditText email = input("Email");

        EditText password = input("Password");
        password.setInputType(
                InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD
        );

        content.addView(email, inputParams());
        content.addView(password, inputParams());

        Button login = greenButton("Sign In");
        content.addView(login, buttonParams());

        login.setOnClickListener(v -> {

            String emailText =
                    email.getText().toString().trim();

            String passwordText =
                    password.getText().toString();

            if (emailText.isEmpty()) {
                email.setError("Enter your email");
                email.requestFocus();
                return;
            }

            if (passwordText.isEmpty()) {
                password.setError("Enter your password");
                password.requestFocus();
                return;
            }

            mAuth.signInWithEmailAndPassword(
                    emailText,
                    passwordText
            ).addOnCompleteListener(this, task -> {

                if (task.isSuccessful()) {

                    Toast.makeText(
                            this,
                            "Welcome back!",
                            Toast.LENGTH_SHORT
                    ).show();

                    openDashboard();

                } else {

                    Toast.makeText(
                            this,
                            "Sign in failed: "
                                    + task.getException()
                                    .getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                }
            });
        });

        // Google divider
        addGoogleDivider();

        // Google button
        Button googleButton =
                googleButton("Continue with Google");

        content.addView(
                googleButton,
                buttonParams()
        );

        googleButton.setOnClickListener(
                v -> startGoogleSignIn()
        );

        TextView create = text(
                "Don't have an account?  Sign Up",
                15,
                Color.rgb(190, 240, 215)
        );

        create.setGravity(Gravity.CENTER);
        create.setPadding(0, dp(18), 0, dp(10));
        content.addView(create);

        create.setOnClickListener(v -> showSignUp());

        setContentView(root);
    }

    // =========================
    // SIGN UP SCREEN
    // =========================

    private void showSignUp() {

        root = createBackground();

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);

        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setGravity(Gravity.CENTER_HORIZONTAL);
        content.setPadding(dp(28), dp(45), dp(28), dp(30));

        scroll.addView(content);

        root.addView(scroll, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));

        TextView back = text(
                "← Back",
                17,
                Color.rgb(180, 240, 210)
        );

        back.setPadding(0, 0, 0, dp(25));

        content.addView(back,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

        back.setOnClickListener(v -> showWelcome());

        ImageView logo = new ImageView(this);
        logo.setImageResource(R.drawable.tradesim_logo);
        logo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        content.addView(logo,
                new LinearLayout.LayoutParams(dp(90), dp(90)));

        animateLogo(logo);

        TextView title = text(
                "Create Your Account",
                28,
                WHITE
        );

        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        content.addView(title);

        TextView subtitle = text(
                "Start practicing with virtual money",
                16,
                Color.rgb(185, 225, 205)
        );

        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, dp(7), 0, dp(25));
        content.addView(subtitle);

        EditText name = input("Full Name");
        EditText email = input("Email");

        EditText password = input("Password");
        password.setInputType(
                InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD
        );

        EditText confirm = input("Confirm Password");
        confirm.setInputType(
                InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD
        );

        content.addView(name, inputParams());
        content.addView(email, inputParams());
        content.addView(password, inputParams());
        content.addView(confirm, inputParams());

        Button signUp = greenButton("Create Account");
        content.addView(signUp, buttonParams());

        signUp.setOnClickListener(v -> {

            String nameText =
                    name.getText().toString().trim();

            String emailText =
                    email.getText().toString().trim();

            String passwordText =
                    password.getText().toString();

            String confirmText =
                    confirm.getText().toString();

            if (nameText.isEmpty()) {
                name.setError("Enter your name");
                name.requestFocus();
                return;
            }

            if (emailText.isEmpty()) {
                email.setError("Enter your email");
                email.requestFocus();
                return;
            }

            if (passwordText.isEmpty()) {
                password.setError("Create a password");
                password.requestFocus();
                return;
            }

            if (!passwordText.equals(confirmText)) {

                confirm.setError(
                        "Passwords do not match"
                );

                confirm.requestFocus();
                return;
            }

            mAuth.createUserWithEmailAndPassword(
                    emailText,
                    passwordText
            ).addOnCompleteListener(this, task -> {

                if (task.isSuccessful()) {

                    Toast.makeText(
                            this,
                            "Account created successfully!",
                            Toast.LENGTH_SHORT
                    ).show();

                    openDashboard();

                } else {

                    Toast.makeText(
                            this,
                            "Account creation failed: "
                                    + task.getException()
                                    .getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                }
            });
        });

        // Google divider
        addGoogleDivider();

        // Google button
        Button googleButton =
                googleButton("Continue with Google");

        content.addView(
                googleButton,
                buttonParams()
        );

        googleButton.setOnClickListener(
                v -> startGoogleSignIn()
        );

        TextView login = text(
                "Already have an account?  Sign In",
                15,
                Color.rgb(190, 240, 215)
        );

        login.setGravity(Gravity.CENTER);
        login.setPadding(0, dp(18), 0, dp(10));
        content.addView(login);

        login.setOnClickListener(v -> showLogin());

        setContentView(root);
    }

    // =========================
    // GOOGLE SIGN-IN
    // =========================

    private void startGoogleSignIn() {

        Intent signInIntent =
                googleSignInClient.getSignInIntent();

        startActivityForResult(
                signInIntent,
                RC_GOOGLE_SIGN_IN
        );
    }

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data
    ) {
        super.onActivityResult(
                requestCode,
                resultCode,
                data
        );

        if (requestCode == RC_GOOGLE_SIGN_IN) {

            Task<GoogleSignInAccount> task =
                    GoogleSignIn.getSignedInAccountFromIntent(
                            data
                    );

            try {

                GoogleSignInAccount account =
                        task.getResult(
                                ApiException.class
                        );

                if (account == null) {
                    Toast.makeText(
                            this,
                            "Google sign-in failed.",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }

                AuthCredential credential =
                        GoogleAuthProvider.getCredential(
                                account.getIdToken(),
                                null
                        );

                mAuth.signInWithCredential(
                        credential
                ).addOnCompleteListener(
                        this,
                        authTask -> {

                            if (authTask.isSuccessful()) {

                                Toast.makeText(
                                        this,
                                        "Google sign-in successful!",
                                        Toast.LENGTH_SHORT
                                ).show();

                                openDashboard();

                            } else {

                                Toast.makeText(
                                        this,
                                        "Google sign-in failed: "
                                                + authTask
                                                .getException()
                                                .getMessage(),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }
                );

            } catch (ApiException e) {

                Toast.makeText(
                        this,
                        "Google sign-in cancelled or failed.",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }

    private void openDashboard() {

        Intent intent =
                new Intent(
                        WelcomeActivity.this,
                        MainActivity.class
                );

        startActivity(intent);
        finish();
    }

    // =========================
    // GOOGLE DIVIDER
    // =========================

    private void addGoogleDivider() {

        TextView divider = text(
                "────────  or  ────────",
                14,
                Color.rgb(140, 190, 165)
        );

        divider.setGravity(Gravity.CENTER);
        divider.setPadding(
                0,
                dp(5),
                0,
                dp(5)
        );

        content.addView(
                divider,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dp(45)
                )
        );
    }

    // =========================
    // GOOGLE BUTTON
    // =========================

    private Button googleButton(String value) {

        Button b = new Button(this);

        b.setText("G   " + value);
        b.setTextSize(16);
        b.setTextColor(Color.WHITE);

        b.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        b.setAllCaps(false);
        b.setGravity(Gravity.CENTER);
        b.setIncludeFontPadding(true);

        b.setMinHeight(dp(56));

        b.setPadding(
                dp(15),
                dp(8),
                dp(15),
                dp(8)
        );

        GradientDrawable bg =
                new GradientDrawable();

        bg.setColor(
                Color.argb(45, 255, 255, 255)
        );

        bg.setCornerRadius(dp(30));

        bg.setStroke(
                dp(1),
                Color.argb(100, 255, 255, 255)
        );

        b.setBackground(bg);

        return b;
    }

    // =========================
    // ANIMATED GREEN BACKGROUND
    // =========================

    private FrameLayout createBackground() {

        FrameLayout frame = new FrameLayout(this);

        GradientDrawable gradient = new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[]{
                        BLACK_GREEN,
                        DARK_GREEN,
                        Color.rgb(0, 45, 28),
                        BLACK_GREEN
                }
        );

        frame.setBackground(gradient);

        TradingBackground chart =
                new TradingBackground(this);

        frame.addView(
                chart,
                new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                )
        );

        return frame;
    }

    // =========================
    // LOGO ANIMATION
    // =========================

    private void animateLogo(View logo) {

        logo.setAlpha(0f);
        logo.setScaleX(0.7f);
        logo.setScaleY(0.7f);

        logo.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(900)
                .setInterpolator(
                        new DecelerateInterpolator()
                )
                .start();

        ObjectAnimator pulseX =
                ObjectAnimator.ofFloat(
                        logo,
                        "scaleX",
                        1f,
                        1.06f,
                        1f
                );

        ObjectAnimator pulseY =
                ObjectAnimator.ofFloat(
                        logo,
                        "scaleY",
                        1f,
                        1.06f,
                        1f
                );

        pulseX.setDuration(2200);
        pulseY.setDuration(2200);

        pulseX.setRepeatCount(
                Animation.INFINITE
        );

        pulseY.setRepeatCount(
                Animation.INFINITE
        );

        pulseX.start();
        pulseY.start();
    }

    // =========================
    // TEXT
    // =========================

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

    // =========================
    // INPUT BOX
    // =========================

    private EditText input(String hint) {

        EditText e = new EditText(this);

        e.setHint(hint);

        e.setHintTextColor(
                Color.rgb(150, 190, 170)
        );

        e.setTextColor(Color.WHITE);
        e.setTextSize(16);
        e.setSingleLine(true);

        e.setPadding(
                dp(18),
                dp(12),
                dp(18),
                dp(12)
        );

        GradientDrawable background =
                new GradientDrawable();

        background.setColor(
                Color.argb(80, 0, 70, 45)
        );

        background.setCornerRadius(
                dp(16)
        );

        background.setStroke(
                dp(1),
                Color.argb(
                        130,
                        0,
                        220,
                        120
                )
        );

        e.setBackground(background);

        return e;
    }

    private LinearLayout.LayoutParams inputParams() {

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dp(58)
                );

        p.setMargins(
                0,
                0,
                0,
                dp(13)
        );

        return p;
    }

    // =========================
    // GREEN BUTTON
    // =========================

    private Button greenButton(String value) {

        Button b = new Button(this);

        b.setText(value);
        b.setTextSize(17);
        b.setTextColor(Color.BLACK);

        b.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        b.setAllCaps(false);
        b.setGravity(Gravity.CENTER);
        b.setIncludeFontPadding(true);

        b.setMinHeight(dp(56));

        b.setPadding(
                dp(15),
                dp(8),
                dp(15),
                dp(8)
        );

        GradientDrawable bg =
                new GradientDrawable();

        bg.setColor(GREEN);
        bg.setCornerRadius(dp(30));

        b.setBackground(bg);

        return b;
    }

    // =========================
    // OUTLINE BUTTON
    // =========================

    private Button outlineButton(String value) {

        Button b = new Button(this);

        b.setText(value);
        b.setTextSize(17);
        b.setTextColor(GREEN);

        b.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        b.setAllCaps(false);
        b.setGravity(Gravity.CENTER);
        b.setIncludeFontPadding(true);

        b.setMinHeight(dp(56));

        b.setPadding(
                dp(15),
                dp(8),
                dp(15),
                dp(8)
        );

        GradientDrawable bg =
                new GradientDrawable();

        bg.setColor(
                Color.argb(35, 0, 220, 120)
        );

        bg.setCornerRadius(dp(30));

        bg.setStroke(
                dp(2),
                GREEN
        );

        b.setBackground(bg);

        return b;
    }

    private LinearLayout.LayoutParams buttonParams() {

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dp(58)
                );

        p.setMargins(
                0,
                0,
                0,
                dp(14)
        );

        return p;
    }

    // =========================
    // DP HELPER
    // =========================

    private int dp(int value) {

        return (int) (
                value *
                getResources()
                        .getDisplayMetrics()
                        .density
                        + 0.5f
        );
    }

    // =========================
    // ANIMATED TRADING BACKGROUND
    // =========================

    private static class TradingBackground
            extends View {

        private Paint paint =
                new Paint(Paint.ANTI_ALIAS_FLAG);

        private float offset = 0;

        private Path path = new Path();

        public TradingBackground(
                Context context
        ) {

            super(context);

            paint.setStyle(
                    Paint.Style.STROKE
            );

            paint.setStrokeWidth(3);

            paint.setColor(
                    Color.argb(
                            55,
                            0,
                            255,
                            130
                    )
            );

            animateChart();
        }

        private void animateChart() {

            ValueAnimator animator =
                    ValueAnimator.ofFloat(
                            0,
                            1000
                    );

            animator.setDuration(8000);

            animator.setRepeatCount(
                    ValueAnimator.INFINITE
            );

            animator.setInterpolator(
                    new LinearInterpolator()
            );

            animator.addUpdateListener(
                    animation -> {

                        offset =
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

            float width = getWidth();
            float height = getHeight();

            path.reset();

            float startX =
                    -200 +
                    (offset % 200);

            for (int i = 0; i < 14; i++) {

                float x =
                        startX +
                        i *
                        (width / 8f);

                float y =
                        height * 0.72f
                        -
                        (float)
                        Math.sin(
                                i * 0.9
                                +
                                offset * 0.01
                        )
                        * height * 0.08f
                        -
                        i *
                        height *
                        0.018f;

                if (i == 0) {
                    path.moveTo(x, y);
                } else {
                    path.lineTo(x, y);
                }
            }

            paint.setStrokeWidth(3);

            canvas.drawPath(
                    path,
                    paint
            );

            paint.setStrokeWidth(1);

            for (int i = 1; i < 7; i++) {

                float y =
                        height * i / 7f;

                canvas.drawLine(
                        0,
                        y,
                        width,
                        y,
                        paint
                );
            }

            for (int i = 1; i < 5; i++) {

                float x =
                        width * i / 5f;

                canvas.drawLine(
                        x,
                        0,
                        x,
                        height,
                        paint
                );
            }
        }
    }
            }
