package minh.project.multishop.viewmodel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import minh.project.multishop.LoginActivity;
import minh.project.multishop.R;
import minh.project.multishop.base.BaseActivityViewModel;
import minh.project.multishop.database.entity.User;
import minh.project.multishop.database.entity.UserInfo;
import minh.project.multishop.database.repository.UserDBRepository;
import minh.project.multishop.databinding.ActivityLoginBinding;
import minh.project.multishop.network.dtos.DTORequest.LoginRequest;
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
            CacheUserInfo(user);
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result","LOGIN_SUCCESS");
            mActivity.setResult(Activity.RESULT_OK,returnIntent);
            mActivity.finish();
        });
    }

    private void CacheUserInfo(User user) {
        netRepository.getUserProfile(user.getAccToken()).observe(mActivity, userProfile -> {
            if(null == userProfile){
                Toast.makeText(mActivity, "Không thể lấy được thông tin người dùng", Toast.LENGTH_SHORT).show();
                return;
            }

            UserInfo userInfo = userProfile.castToInfo();
            Log.d("TAG", "CacheUserInfo: "+userInfo.getUsername());
            dbRepository.insertUserInfo(userInfo);
//
//            Toast.makeText(mActivity, "Username from DB" + dbRepository.getUserInfo().getUsername(), Toast.LENGTH_SHORT).show();
        });
    }
}
