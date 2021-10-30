package minh.project.multishop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import minh.project.multishop.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding loginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View viewRoot = loginBinding.getRoot();
        setContentView(viewRoot);

        loginBinding.signin.setOnClickListener(view -> {
            loginBinding.signupPage.setVisibility(View.GONE);
            loginBinding.signinPage.setVisibility(View.VISIBLE);
        });

        loginBinding.signup.setOnClickListener(view -> {
            loginBinding.signupPage.setVisibility(View.VISIBLE);
            loginBinding.signinPage.setVisibility(View.GONE);
        });

    }
}