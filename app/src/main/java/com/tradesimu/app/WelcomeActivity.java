package com.tradesimu.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.graphics.*;
import android.graphics.drawable.GradientDrawable;
import android.view.*;
import android.view.animation.DecelerateInterpolator;
import android.widget.*;
import android.text.InputType;
import android.content.Context;
import android.content.Intent;
import android.animation.ValueAnimator;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

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

    private boolean requestInProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            mAuth = FirebaseAuth.getInstance();

            GoogleSignInOptions gso =
                    new GoogleSignInOptions.Builder(
                            GoogleSignInOptions.DEFAULT_SIGN_IN
                    )
                    .requestIdToken(
                            getString(R.string.default_web_client_id)
                    )
                    .requestEmail()
                    .build();

            googleSignInClient =
                    GoogleSignIn.getClient(this, gso);

            showWelcome();

        } catch (Exception e) {
            e.printStackTrace();

            new AlertDialog.Builder(this)
                    .setTitle("TradeSim")
                    .setMessage(
                            "Unable to initialize authentication.\n\n"
                                    + "Check your Firebase configuration "
                                    + "and Google Sign-In setup."
                    )
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

    // =====================================================
    // WELCOME SCREEN
    // =====================================================

    private void showWelcome() {

        requestInProgress = false;

        root = createBackground();

        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setGravity(Gravity.CENTER);
        content.setPadding(
                dp(28), dp(30), dp(28), dp(30)
        );

        root.addView(
                content,
                new FrameLayout.LayoutParams(-1, -1)
        );

        ImageView logo = new ImageView(this);
        logo.setImageResource(R.drawable.tradesim_logo);
        logo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        LinearLayout.LayoutParams logoParams =
                new LinearLayout.LayoutParams(
                        dp(145), dp(145)
                );

        logoParams.bottomMargin = dp(15);

        content.addView(logo, logoParams);
        animateLogo(logo);

        TextView title = text(
                "Welcome to TradeSim",
                30,
                WHITE
        );

        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);

        content.addView(title);
        animateEntrance(title, 200);

        TextView subtitle = text(
                "Learn. Practice. Trade.",
                17,
                Color.rgb(190, 235, 215)
        );

        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(
                0, dp(8), 0, dp(35)
        );

        content.addView(subtitle);
        animateEntrance(subtitle, 350);

        Button signIn = greenButton("Sign In");
        Button signUp = outlineButton("Sign Up");

        content.addView(signIn, buttonParams());
        content.addView(signUp, buttonParams());

        animateEntrance(signIn, 450);
        animateEntrance(signUp, 550);

        signIn.setOnClickListener(v -> showLogin());
        signUp.setOnClickListener(v -> showSignUp());

        setContentView(root);
        animateScreen(content);
    }

    // =====================================================
    // LOGIN SCREEN
    // =====================================================

    private void showLogin() {

        requestInProgress = false;

        root = createBackground();

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setClipToPadding(false);
        scroll.setVerticalScrollBarEnabled(false);

        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setGravity(Gravity.CENTER_HORIZONTAL);
        content.setPadding(
                dp(28), dp(35), dp(28), dp(30)
        );

        scroll.addView(
                content,
                new ScrollView.LayoutParams(-1, -2)
        );

        root.addView(
                scroll,
                new FrameLayout.LayoutParams(-1, -1)
        );

        TextView back = text(
                "← Back",
                17,
                Color.rgb(180, 240, 210)
        );

        back.setGravity(Gravity.START);
        back.setPadding(0, 0, 0, dp(20));
        content.addView(back, matchParams());

        back.setOnClickListener(v -> showWelcome());

        addLogo(dp(90));

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
        subtitle.setPadding(
                0, dp(7), 0, dp(25)
        );

        content.addView(subtitle);

        EditText email = input("Email");

        email.setInputType(
                InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        );

        email.setImeOptions(
                android.view.inputmethod.EditorInfo.IME_ACTION_NEXT
        );

        EditText password = passwordInput("Password");

        content.addView(email, inputParams());
        addPasswordField(password);

        TextView forgot = text(
                "Forgot Password?",
                14,
                GREEN
        );

        forgot.setGravity(Gravity.END);
        forgot.setPadding(
                0, dp(2), 0, dp(18)
        );

        content.addView(forgot, matchParams());
        forgot.setOnClickListener(v -> sendPasswordReset(email));

        Button login = greenButton("Sign In");
        content.addView(login, buttonParams());

        login.setOnClickListener(v -> {

            if (requestInProgress) {
                return;
            }

            String emailText =
                    email.getText().toString().trim();

            String passwordText =
                    password.getText().toString();

            if (!validEmail(emailText)) {
                email.setError("Enter a valid email address");
                email.requestFocus();
                return;
            }

            if (passwordText.isEmpty()) {
                password.setError("Enter your password");
                password.requestFocus();
                return;
            }

            requestInProgress = true;
            login.setEnabled(false);
            login.setText("Signing in...");

            try {

                mAuth.signInWithEmailAndPassword(
                        emailText,
                        passwordText
                ).addOnCompleteListener(this, task -> {

                    requestInProgress = false;
                    login.setEnabled(true);
                    login.setText("Sign In");

                    if (task.isSuccessful()) {
                        openIntroduction();
                    } else {
                        showAuthError(
                                "Sign in failed",
                                task.getException()
                        );
                    }
                });

            } catch (Exception e) {

                requestInProgress = false;
                login.setEnabled(true);
                login.setText("Sign In");

                showAuthError("Sign in failed", e);
            }
        });

        addGoogleDivider();

        Button google = googleButton("Continue with Google");
        content.addView(google, buttonParams());

        google.setOnClickListener(v -> startGoogleSignIn());

        TextView create = text(
                "Don't have an account? Sign Up",
                15,
                Color.rgb(190, 240, 215)
        );

        create.setGravity(Gravity.CENTER);
        create.setPadding(
                0, dp(18), 0, dp(10)
        );

        content.addView(create);
        create.setOnClickListener(v -> showSignUp());

        setContentView(root);
        animateScreen(content);
    }

    // =====================================================
    // SIGN-UP SCREEN
    // =====================================================

    private void showSignUp() {

        requestInProgress = false;

        root = createBackground();

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setClipToPadding(false);
        scroll.setVerticalScrollBarEnabled(false);

        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setGravity(Gravity.CENTER_HORIZONTAL);
        content.setPadding(
                dp(28), dp(30), dp(28), dp(30)
        );

        scroll.addView(
                content,
                new ScrollView.LayoutParams(-1, -2)
        );

        root.addView(
                scroll,
                new FrameLayout.LayoutParams(-1, -1)
        );

        TextView back = text(
                "← Back",
                17,
                Color.rgb(180, 240, 210)
        );

        back.setGravity(Gravity.START);
        back.setPadding(0, 0, 0, dp(15));

        content.addView(back, matchParams());
        back.setOnClickListener(v -> showWelcome());

        addLogo(dp(85));

        TextView title = text(
                "Create Your Account",
                27,
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
        subtitle.setPadding(
                0, dp(7), 0, dp(22)
        );

        content.addView(subtitle);

        EditText name = input("Full Name");
        name.setInputType(
                InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS
        );

        EditText email = input("Email");

        email.setInputType(
                InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        );

        EditText password = passwordInput("Password");
        EditText confirm = passwordInput("Confirm Password");

        content.addView(name, inputParams());
        content.addView(email, inputParams());

        addPasswordField(password);
        addPasswordField(confirm);

        TextView passwordHint = text(
                "Use at least 6 characters for your password.",
                13,
                Color.rgb(175, 210, 190)
        );

        passwordHint.setPadding(
                0, 0, 0, dp(15)
        );

        content.addView(passwordHint, matchParams());

        Button signUp = greenButton("Create Account");
        content.addView(signUp, buttonParams());

        signUp.setOnClickListener(v -> {

            if (requestInProgress) {
                return;
            }

            String nameText =
                    name.getText().toString().trim();

            String emailText =
                    email.getText().toString().trim();

            String passwordText =
                    password.getText().toString();

            String confirmText =
                    confirm.getText().toString();

            if (nameText.isEmpty()) {
                name.setError("Enter your full name");
                name.requestFocus();
                return;
            }

            if (!validEmail(emailText)) {
                email.setError("Enter a valid email address");
                email.requestFocus();
                return;
            }

            if (passwordText.length() < 6) {
                password.setError(
                        "Password must contain at least 6 characters"
                );
                password.requestFocus();
                return;
            }

            if (!passwordText.equals(confirmText)) {
                confirm.setError("Passwords do not match");
                confirm.requestFocus();
                return;
            }

            requestInProgress = true;
            signUp.setEnabled(false);
            signUp.setText("Creating account...");

            try {

                mAuth.createUserWithEmailAndPassword(
                        emailText,
                        passwordText
                ).addOnCompleteListener(this, task -> {

                    requestInProgress = false;
                    signUp.setEnabled(true);
                    signUp.setText("Create Account");

                    if (task.isSuccessful()) {

                        FirebaseUser user =
                                mAuth.getCurrentUser();

                        if (user != null) {

                            UserProfileChangeRequest profileUpdates =
                                    new UserProfileChangeRequest.Builder()
                                            .setDisplayName(nameText)
                                            .build();

                            user.updateProfile(profileUpdates);
                        }

                        openIntroduction();

                    } else {

                        showAuthError(
                                "Account creation failed",
                                task.getException()
                        );
                    }
                });

            } catch (Exception e) {

                requestInProgress = false;
                signUp.setEnabled(true);
                signUp.setText("Create Account");

                showAuthError(
                        "Account creation failed",
                        e
                );
            }
        });

        addGoogleDivider();

        Button google = googleButton("Continue with Google");
        content.addView(google, buttonParams());

        google.setOnClickListener(v -> startGoogleSignIn());

        TextView login = text(
                "Already have an account? Sign In",
                15,
                Color.rgb(190, 240, 215)
        );

        login.setGravity(Gravity.CENTER);
        login.setPadding(
                0, dp(18), 0, dp(10)
        );

        content.addView(login);
        login.setOnClickListener(v -> showLogin());

        setContentView(root);
        animateScreen(content);
    }

    // =====================================================
    // PASSWORD RESET
    // =====================================================

    private void sendPasswordReset(EditText emailField) {

        String emailText =
                emailField.getText().toString().trim();

        if (!validEmail(emailText)) {
            emailField.setError(
                    "Enter a valid email address first"
            );
            emailField.requestFocus();
            return;
        }

        try {

            mAuth.sendPasswordResetEmail(emailText)
                    .addOnCompleteListener(this, task -> {

                        if (task.isSuccessful()) {

                            Toast.makeText(
                                    this,
                                    "If the account exists, a password reset email will be sent.",
                                    Toast.LENGTH_LONG
                            ).show();

                        } else {

                            showAuthError(
                                    "Could not request password reset",
                                    task.getException()
                            );
                        }
                    });

        } catch (Exception e) {
            showAuthError("Password reset failed", e);
        }
    }

    // =====================================================
    // GOOGLE SIGN-IN
    // =====================================================

    private void startGoogleSignIn() {

        if (requestInProgress) {
            return;
        }

        try {

            startActivityForResult(
                    googleSignInClient.getSignInIntent(),
                    RC_GOOGLE_SIGN_IN
            );

        } catch (Exception e) {

            showAuthError(
                    "Unable to start Google Sign-In. Check your configuration",
                    e
            );
        }
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

        if (requestCode != RC_GOOGLE_SIGN_IN) {
            return;
        }

        if (data == null) {

            Toast.makeText(
                    this,
                    "Google Sign-In was cancelled.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        Task<GoogleSignInAccount> task =
                GoogleSignIn.getSignedInAccountFromIntent(data);

        try {

            GoogleSignInAccount account =
                    task.getResult(ApiException.class);

            if (account == null || account.getIdToken() == null) {

                Toast.makeText(
                        this,
                        "Google Sign-In did not return an ID token. Check Firebase configuration.",
                        Toast.LENGTH_LONG
                ).show();

                return;
            }

            AuthCredential credential =
                    GoogleAuthProvider.getCredential(
                            account.getIdToken(),
                            null
                    );

            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, authTask -> {

                        if (authTask.isSuccessful()) {
                            openIntroduction();
                        } else {
                            showAuthError(
                                    "Google authentication failed",
                                    authTask.getException()
                            );
                        }
                    });

        } catch (ApiException e) {

            String message;

            switch (e.getStatusCode()) {

                case 12501:
                    message = "Google Sign-In cancelled.";
                    break;

                case 10:
                    message =
                            "Google Sign-In configuration error. "
                                    + "Check the SHA certificate and "
                                    + "Web client ID in Firebase.";
                    break;

                default:
                    message =
                            "Google Sign-In failed. Error code: "
                                    + e.getStatusCode();
                    break;
            }

            Toast.makeText(
                    this,
                    message,
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    // =====================================================
    // PASSWORD FIELD WITH SHOW/HIDE CONTROL
    // =====================================================

    private EditText passwordInput(String hint) {

        LinearLayout wrapper = new LinearLayout(this);
        wrapper.setOrientation(LinearLayout.HORIZONTAL);
        wrapper.setGravity(Gravity.CENTER_VERTICAL);

        GradientDrawable background = new GradientDrawable();
        background.setColor(Color.rgb(8, 42, 30));
        background.setCornerRadius(dp(16));
        background.setStroke(
                dp(1),
                Color.rgb(0, 150, 85)
        );

        wrapper.setBackground(background);

        EditText field = new EditText(this);
        field.setHint(hint);
        field.setHintTextColor(Color.rgb(150, 190, 170));
        field.setTextColor(WHITE);
        field.setTextSize(16);
        field.setSingleLine(true);

        // Password is hidden by default.
        field.setInputType(
                InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_VARIATION_PASSWORD
        );

        field.setBackgroundColor(Color.TRANSPARENT);
        field.setPadding(
                dp(16), dp(10), dp(4), dp(10)
        );

        field.setSelectAllOnFocus(false);

        LinearLayout.LayoutParams fieldParams =
                new LinearLayout.LayoutParams(0, -1, 1f);

        wrapper.addView(field, fieldParams);

        TextView toggle = text("SHOW", 12, GREEN);
        toggle.setGravity(Gravity.CENTER);
        toggle.setPadding(
                dp(8), 0, dp(12), 0
        );

        toggle.setContentDescription("Show or hide password");

        wrapper.addView(
                toggle,
                new LinearLayout.LayoutParams(-2, -1)
        );

        toggle.setOnClickListener(v -> {

            int position = field.getSelectionStart();

            boolean hidden =
                    (field.getInputType()
                            & InputType.TYPE_TEXT_VARIATION_PASSWORD) != 0;

            if (hidden) {

                field.setInputType(
                        InputType.TYPE_CLASS_TEXT
                                | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                );

                toggle.setText("HIDE");

            } else {

                field.setInputType(
                        InputType.TYPE_CLASS_TEXT
                                | InputType.TYPE_TEXT_VARIATION_PASSWORD
                );

                toggle.setText("SHOW");
            }

            field.setTypeface(Typeface.DEFAULT);

            if (position >= 0) {
                field.setSelection(
                        Math.min(position, field.length())
                );
            }
        });

        // Save the wrapper so the correct view is added to the layout.
        field.setTag(wrapper);

        return field;
    }

    private void addPasswordField(EditText field) {

        Object tag = field.getTag();

        if (tag instanceof View) {

            View wrapper = (View) tag;

            content.addView(
                    wrapper,
                    inputParams()
            );

        } else {

            throw new IllegalStateException(
                    "Password field wrapper was not created."
            );
        }
    }

    // =====================================================
    // NAVIGATION
    // =====================================================

    private void openIntroduction() {

        try {

            Intent intent = new Intent(
                    WelcomeActivity.this,
                    IntroductionActivity.class
            );

            startActivity(intent);
            finish();

        } catch (Exception e) {

            showAuthError(
                    "Authentication succeeded, but the introduction screen could not open",
                    e
            );
        }
    }

    // =====================================================
    // VALIDATION AND ERROR MESSAGES
    // =====================================================

    private boolean validEmail(String email) {

        return android.util.Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches();
    }

    private void showAuthError(
            String title,
            Exception exception
    ) {

        if (isFinishing() || isDestroyed()) {
            return;
        }

        String message = title + ".";

        if (exception != null
                && exception.getMessage() != null) {

            message += "\n\n" + exception.getMessage();
        }

        new AlertDialog.Builder(this)
                .setTitle("TradeSim")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    // =====================================================
    // LOGO AND ENTRANCE ANIMATIONS
    // =====================================================

    private void addLogo(int size) {

        ImageView logo = new ImageView(this);
        logo.setImageResource(R.drawable.tradesim_logo);
        logo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(size, size);

        params.setMargins(
                0, 0, 0, dp(12)
        );

        content.addView(logo, params);
        animateLogo(logo);
    }

    private void animateLogo(View logo) {

        logo.setAlpha(0f);
        logo.setScaleX(0.8f);
        logo.setScaleY(0.8f);

        logo.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(700)
                .setInterpolator(new DecelerateInterpolator())
                .start();

        logo.postDelayed(() -> {

            if (isFinishing()
                    || isDestroyed()
                    || logo.getParent() == null) {
                return;
            }

            logo.animate()
                    .scaleX(1.04f)
                    .scaleY(1.04f)
                    .setDuration(1200)
                    .withEndAction(() -> {

                        if (!isFinishing()
                                && !isDestroyed()
                                && logo.getParent() != null) {

                            logo.animate()
                                    .scaleX(1f)
                                    .scaleY(1f)
                                    .setDuration(1200)
                                    .start();
                        }
                    })
                    .start();

        }, 700);
    }

    private void animateEntrance(View view, long delay) {

        view.setAlpha(0f);
        view.setTranslationY(dp(18));

        view.animate()
                .alpha(1f)
                .translationY(0f)
                .setStartDelay(delay)
                .setDuration(650)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    private void animateScreen(View view) {

        view.setAlpha(0f);

        view.animate()
                .alpha(1f)
                .setDuration(350)
                .start();
    }

    // =====================================================
    // ANIMATED DARK-GREEN BACKGROUND
    // =====================================================

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
                new FrameLayout.LayoutParams(-1, -1)
        );

        return frame;
    }

    private class TradingBackground extends View {

        private final Paint paint =
                new Paint(Paint.ANTI_ALIAS_FLAG);

        private final Path chartPath = new Path();

        private float progress = 0f;
        private ValueAnimator animator;

        public TradingBackground(Context context) {

            super(context);

            setLayerType(View.LAYER_TYPE_SOFTWARE, null);

            animator = ValueAnimator.ofFloat(0f, 1f);
            animator.setDuration(8000);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setRepeatMode(ValueAnimator.RESTART);

            animator.addUpdateListener(animation -> {

                progress =
                        (float) animation.getAnimatedValue();

                invalidate();
            });
        }

        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();

            if (animator != null && !animator.isStarted()) {
                animator.start();
            }
        }

        @Override
        protected void onDetachedFromWindow() {

            if (animator != null) {
                animator.cancel();
            }

            super.onDetachedFromWindow();
        }

        @Override
        protected void onDraw(Canvas canvas) {

            super.onDraw(canvas);

            int width = getWidth();
            int height = getHeight();

            if (width <= 0 || height <= 0) {
                return;
            }

            // Dark green gradient.
            LinearGradient gradient = new LinearGradient(
                    0,
                    0,
                    width,
                    height,
                    DARK_GREEN,
                    BLACK_GREEN,
                    Shader.TileMode.CLAMP
            );

            paint.setShader(gradient);
            paint.setStyle(Paint.Style.FILL);

            canvas.drawRect(
                    0, 0, width, height, paint
            );

            paint.setShader(null);

            // Subtle trading grid.
            paint.setColor(
                    Color.argb(22, 0, 220, 120)
            );

            paint.setStrokeWidth(dp(1));

            int spacing = dp(55);

            for (int x = 0; x < width; x += spacing) {

                canvas.drawLine(
                        x, 0, x, height, paint
                );
            }

            for (int y = 0; y < height; y += spacing) {

                canvas.drawLine(
                        0, y, width, y, paint
                );
            }

            // Animated market chart.
            paint.setColor(
                    Color.argb(100, 0, 220, 120)
            );

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(dp(2));
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeJoin(Paint.Join.ROUND);

            chartPath.reset();

            float baseY = height * 0.73f;

            chartPath.moveTo(0, baseY);

            int step = Math.max(dp(12), 1);

            for (int x = 0; x <= width; x += step) {

                float wave1 = (float) Math.sin(
                        x * 0.024f + progress * 6.28f
                );

                float wave2 = (float) Math.sin(
                        x * 0.010f + progress * 4f
                );

                float y = baseY
                        - wave1 * dp(18)
                        - wave2 * dp(12)
                        - x * 0.07f;

                chartPath.lineTo(x, y);
            }

            canvas.drawPath(chartPath, paint);

            paint.setStyle(Paint.Style.FILL);

            // Moving glow.
            float pulse = (float) (
                    0.5 + 0.5 * Math.sin(progress * 6.28f)
            );

            paint.setColor(
                    Color.argb(
                            (int) (12 + pulse * 20),
                            0,
                            220,
                            120
                    )
            );

            float glowX = width * (
                    0.2f + 0.6f * progress
            );

            float glowY = height * 0.40f;

            canvas.drawCircle(
                    glowX,
                    glowY,
                    dp(85),
                    paint
            );

            // Additional subtle glow near the bottom.
            paint.setColor(
                    Color.argb(10, 0, 220, 120)
            );

            canvas.drawCircle(
                    width * 0.25f,
                    height * 0.82f,
                    dp(110),
                    paint
            );
        }
    }

    // =====================================================
    // GOOGLE SIGN-IN DIVIDER
    // =====================================================

    private void addGoogleDivider() {

        TextView divider = text(
                "────────  or  ────────",
                14,
                Color.rgb(140, 190, 165)
        );

        divider.setGravity(Gravity.CENTER);
        divider.setPadding(
                0, dp(5), 0, dp(5)
        );

        content.addView(
                divider,
                new LinearLayout.LayoutParams(
                        -1, dp(45)
                )
        );
    }

    // =====================================================
    // BUTTONS
    // =====================================================

    private Button greenButton(String value) {

        Button button = new Button(this);

        button.setText(value);
        button.setTextSize(17);
        button.setTextColor(Color.rgb(1, 15, 9));
        button.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        button.setAllCaps(false);
        button.setMinHeight(dp(56));

        GradientDrawable background =
                new GradientDrawable();

        background.setColor(GREEN);
        background.setCornerRadius(dp(30));

        button.setBackground(background);
        button.setElevation(dp(4));

        return button;
    }

    private Button outlineButton(String value) {

        Button button = new Button(this);

        button.setText(value);
        button.setTextSize(17);
        button.setTextColor(GREEN);
        button.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        button.setAllCaps(false);
        button.setMinHeight(dp(56));

        GradientDrawable background =
                new GradientDrawable();

        background.setColor(Color.rgb(8, 25, 19));
        background.setCornerRadius(dp(30));
        background.setStroke(dp(2), GREEN);

        button.setBackground(background);
        button.setElevation(dp(2));

        return button;
    }

    private Button googleButton(String value) {

        Button button = new Button(this);

        button.setText(value);
        button.setTextSize(15);
        button.setTextColor(WHITE);
        button.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        button.setAllCaps(false);
        button.setMinHeight(dp(54));

        GradientDrawable background =
                new GradientDrawable();

        background.setColor(Color.rgb(12, 38, 29));
        background.setCornerRadius(dp(16));
        background.setStroke(
                dp(1),
                Color.rgb(60, 120, 90)
        );

        button.setBackground(background);

        return button;
    }

    // =====================================================
    // TEXT AND INPUT HELPERS
    // =====================================================

    private TextView text(
            String value,
            float size,
            int color
    ) {

        TextView view = new TextView(this);

        view.setText(value);
        view.setTextSize(
                TypedValue.COMPLEX_UNIT_SP,
                size
        );

        view.setTextColor(color);
        view.setIncludeFontPadding(true);

        return view;
    }

    private EditText input(String hint) {

        EditText field = new EditText(this);

        field.setHint(hint);
        field.setHintTextColor(
                Color.rgb(150, 190, 170)
        );

        field.setTextColor(WHITE);
        field.setTextSize(16);
        field.setSingleLine(true);
        field.setPadding(
                dp(16), dp(12), dp(16), dp(12)
        );

        GradientDrawable background =
                new GradientDrawable();

        background.setColor(Color.rgb(8, 42, 30));
        background.setCornerRadius(dp(16));
        background.setStroke(
                dp(1),
                Color.rgb(0, 150, 85)
        );

        field.setBackground(background);

        return field;
    }

    // =====================================================
    // LAYOUT HELPERS
    // =====================================================

    private LinearLayout.LayoutParams buttonParams() {

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        -1, dp(56)
                );

        params.setMargins(
                0, dp(7), 0, dp(7)
        );

        return params;
    }

    private LinearLayout.LayoutParams inputParams() {

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        -1, dp(56)
                );

        params.setMargins(
                0, dp(7), 0, dp(7)
        );

        return params;
    }

    private LinearLayout.LayoutParams matchParams() {

        return new LinearLayout.LayoutParams(
                -1,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
    }

    private int dp(int value) {

        return (int) (
                value
                        * getResources()
                        .getDisplayMetrics()
                        .density
        );
    }
    }
