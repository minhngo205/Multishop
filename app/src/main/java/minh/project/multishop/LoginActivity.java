package minh.project.multishop;

import static minh.project.multishop.fragment.fragmentviewmodel.UserFragmentViewModel.REQUEST_LOGIN;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import minh.project.multishop.base.BaseActivity;
import minh.project.multishop.databinding.ActivityLoginBinding;
import minh.project.multishop.viewmodel.LoginActivityViewModel;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ActivityLoginBinding loginBinding;
    private LoginActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View viewRoot = loginBinding.getRoot();
        setContentView(viewRoot);

        mViewModel = new LoginActivityViewModel(this);
        setResult(RESULT_OK);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.initView();
    }

    public ActivityLoginBinding getLoginBinding() {
        return loginBinding;
    }

    @Override
    public void onClick(View view) {
        mViewModel.onClickEvent(view.getId());
    }
}