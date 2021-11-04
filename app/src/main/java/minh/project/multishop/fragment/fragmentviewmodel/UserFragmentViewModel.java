package minh.project.multishop.fragment.fragmentviewmodel;

import static android.app.Activity.RESULT_OK;
import static minh.project.multishop.adapter.BaseDialog.CANCEL_BUTTON;
import static minh.project.multishop.adapter.BaseDialog.CONFIRM_BUTTON;
import static minh.project.multishop.adapter.BaseDialog.CONTENT;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;

import minh.project.multishop.LoginActivity;
import minh.project.multishop.MainActivity;
import minh.project.multishop.R;
import minh.project.multishop.adapter.BaseDialog;
import minh.project.multishop.base.BaseFragment;
import minh.project.multishop.base.BaseFragmentViewModel;
import minh.project.multishop.database.entity.User;
import minh.project.multishop.database.repository.UserDBRepository;
import minh.project.multishop.databinding.FragmentUserBinding;
import minh.project.multishop.fragment.UserFragment;
import minh.project.multishop.models.UserProfile;
import minh.project.multishop.network.repository.UserNetRepository;

public class UserFragmentViewModel extends BaseFragmentViewModel<UserFragment> {

    private static final int REQUEST_LOGIN = 10001;
    private User mUser;
    private final UserDBRepository mUserDBRepository;
    private FragmentUserBinding binding;
    private final UserNetRepository mUserNetRepository;

    /**
     * constructor
     *
     * @param userFragment Fragment object
     */
    public UserFragmentViewModel(UserFragment userFragment) {
        super(userFragment);
        mUserDBRepository = new UserDBRepository();
        mUserNetRepository = UserNetRepository.getInstance();
    }

    @Override
    public void initView(View view) {
        binding = mFragment.getBinding();

        binding.tvSignIn.setOnClickListener(mFragment);
        binding.lvScan.setOnClickListener(mFragment);
        binding.lvAccount.setOnClickListener(mFragment);
        binding.lvBag.setOnClickListener(mFragment);
        binding.lvOrder.setOnClickListener(mFragment);
        binding.lvSave.setOnClickListener(mFragment);
        binding.lvSet.setOnClickListener(mFragment);
        binding.lvOffline.setOnClickListener(mFragment);
        binding.lvContact.setOnClickListener(mFragment);
        binding.lvOut.setOnClickListener(mFragment);
    }

    public void checkSignIn() {
        mUser = mUserDBRepository.getCurrentUser();
        if (mUser == null) { // no sign
            binding.tvUserName.setVisibility(View.GONE);
            binding.ivHead.setVisibility(View.GONE);
            binding.tvSignIn.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.ivQrCode.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_RIGHT, R.id.iv_head);
            binding.ivQrCode.setLayoutParams(params);
            return;
        }

        // sign in
        binding.tvUserName.setVisibility(View.VISIBLE);
        binding.tvSignIn.setVisibility(View.GONE);
        binding.ivHead.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.ivQrCode.getLayoutParams();
        params.addRule(RelativeLayout.BELOW, 0);
        binding.ivQrCode.setLayoutParams(params);
        binding.tvUserName.setText(mUser.getUsername());

        mUserNetRepository.getUserProfile(mUser.getAccToken()).observe(mFragment.getViewLifecycleOwner(), new Observer<UserProfile>() {
            @Override
            public void onChanged(UserProfile userProfile) {
                Log.d("TAG", "onChanged: User profile success");
            }
        });
//        mTvUserName.setText(mUser.getHuaweiAccount().getDisplayName());
//
//        if (mUser.getHuaweiAccount().getAvatarUri() == Uri.EMPTY) {
//            Glide.with(mActivity).load(R.mipmap.head_my).apply(option).into(mIvUserHead);
//        } else {
//            Glide.with(mActivity).load(mUser.getHuaweiAccount().getAvatarUriString()).apply(option).into(mIvUserHead);
//        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClickEvent(int viewId) {
        switch (viewId) {
            case R.id.iv_qr_code: // QR Code

                break;
            case R.id.tv_sign_in: // Sign in
                Intent intent = new Intent(mFragment.getActivity(), LoginActivity.class);
                mFragment.requireActivity().startActivity(intent);
                mFragment.requireActivity().finish();
                break;
            case R.id.lv_scan: // Scan to pay

                break;
            case R.id.lv_account: // My Account

                break;
            case R.id.lv_bag: // Bag

                break;
            case R.id.lv_order: // Order Center

                break;
            case R.id.lv_save: // Favourite

                break;
            case R.id.lv_set: // Setting

                break;
            case R.id.lv_offline: // Offline Shop

                break;
            case R.id.lv_contact: // Contact Us

                break;
            case R.id.lv_out: // Log out
                checkSignOut();
                break;
            case R.id.lv_left: // Default
                break;
            default:
                break;
        }
    }

    private void checkSignOut() {
        if (mUser == null) {
            Toast.makeText(mFragment.getActivity(), R.string.please_sign_first, Toast.LENGTH_SHORT).show();
            return;
        }

        Bundle data = new Bundle();
        data.putString(CONFIRM_BUTTON, mFragment.getString(R.string.confirm));
        data.putString(CANCEL_BUTTON, mFragment.getString(R.string.cancel));
        data.putString(CONTENT, mFragment.getString(R.string.confirm_log_out));

        BaseDialog dialog = new BaseDialog(mFragment.requireActivity(), data, true);
        dialog.setConfirmListener(v -> {
            signOut();
            dialog.dismiss();
        });
        dialog.setCancelListener(v -> dialog.dismiss());
        dialog.show();
    }

    private void signOut() {
        mUserDBRepository.deleteUser();
        checkSignIn();
    }
}
