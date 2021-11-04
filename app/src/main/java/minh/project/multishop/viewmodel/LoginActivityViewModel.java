package minh.project.multishop.viewmodel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.Observer;

import minh.project.multishop.LoginActivity;
import minh.project.multishop.MainActivity;
import minh.project.multishop.R;
import minh.project.multishop.base.BaseActivityViewModel;
import minh.project.multishop.database.entity.User;
import minh.project.multishop.database.repository.UserDBRepository;
import minh.project.multishop.databinding.ActivityLoginBinding;
import minh.project.multishop.models.UserProfile;
import minh.project.multishop.network.dtos.DTORequest.LoginRequest;
import minh.project.multishop.network.dtos.DTOResponse.LoginResponse;
import minh.project.multishop.network.repository.UserNetRepository;

public class LoginActivityViewModel extends BaseActivityViewModel<LoginActivity> {

    private final UserNetRepository netRepository;
    private final ActivityLoginBinding mBinding;
    private final UserDBRepository dbRepository;

    /**
     * constructor
     *
     * @param loginActivity Activity object
     */
    public LoginActivityViewModel(LoginActivity loginActivity) {
        super(loginActivity);
        netRepository = UserNetRepository.getInstance();
        mBinding = mActivity.getLoginBinding();
        dbRepository = new UserDBRepository();
    }

    @Override
    public void initView() {
        mBinding.signin.setOnClickListener(mActivity);
        mBinding.signup.setOnClickListener(mActivity);
        mBinding.continueButton.setOnClickListener(mActivity);

//        mBinding.signin.setOnClickListener(view -> {
//            mBinding.signupPage.setVisibility(View.GONE);
//            mBinding.signinPage.setVisibility(View.VISIBLE);
//        });
//
//        mBinding.signup.setOnClickListener(view -> {
//            mBinding.signupPage.setVisibility(View.VISIBLE);
//            mBinding.signinPage.setVisibility(View.GONE);
//        });
//
//        mBinding.continueButton.setOnClickListener(view -> {
//            String username = String.valueOf(mBinding.email.getText());
//            String password = String.valueOf(mBinding.password.getText());
//            if(username.trim().isEmpty()){
//                mBinding.email.setError("Cần có tên đăng nhập");
//                mBinding.email.requestFocus();
//                return;
//            }
//            if(password.trim().isEmpty()){
//                mBinding.password.setError("Cần có mật khẩu");
//                mBinding.password.requestFocus();
//                return;
//            }
//
//            LoginRequest loginRequest = new LoginRequest(username,password);
//
//            netRepository.getLoginData(loginRequest).observe(mActivity, loginResponse -> {
//                if (loginResponse==null){
//                    Toast.makeText(mActivity, "Tài khoản không tồn tại hoặc sai mật khẩu", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                Toast.makeText(mActivity, "Success "+loginResponse.getAccessToken().length(), Toast.LENGTH_SHORT).show();
//            });
//        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClickEvent(int viewId) {
        switch (viewId){
            case R.id.signin:{
                mBinding.signupPage.setVisibility(View.GONE);
                mBinding.signinPage.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.signup:{
                mBinding.signupPage.setVisibility(View.VISIBLE);
                mBinding.signinPage.setVisibility(View.GONE);
                break;
            }
            case R.id.continue_button:{
                String username = String.valueOf(mBinding.email.getText());
                String password = String.valueOf(mBinding.password.getText());
                if(username.trim().isEmpty()){
                    mBinding.email.setError("Cần có tên đăng nhập");
                    mBinding.email.requestFocus();
                    return;
                }
                if(password.trim().isEmpty()){
                    mBinding.password.setError("Cần có mật khẩu");
                    mBinding.password.requestFocus();
                    return;
                }
                login(username,password);
                break;
            }
            default: break;
        }
    }

    private void login(String username, String password) {
        LoginRequest loginRequest = new LoginRequest(username,password);

        netRepository.getLoginData(loginRequest).observe(mActivity, loginResponse -> {
            if (loginResponse==null){
                Toast.makeText(mActivity, "Đăng nhập thất bại. Kiểm tra tài khoản và mật khẩu", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = new User(username,loginResponse.getRefreshToken(),loginResponse.getAccessToken());
            dbRepository.setCurrentUser(user);
        });

        Intent intent = new Intent(mActivity, MainActivity.class);
        mActivity.startActivity(intent);
        mActivity.finish();
    }
}
