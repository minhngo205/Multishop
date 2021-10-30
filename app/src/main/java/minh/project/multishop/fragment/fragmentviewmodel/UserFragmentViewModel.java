package minh.project.multishop.fragment.fragmentviewmodel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import minh.project.multishop.LoginActivity;
import minh.project.multishop.MainActivity;
import minh.project.multishop.R;
import minh.project.multishop.base.BaseFragment;
import minh.project.multishop.base.BaseFragmentViewModel;
import minh.project.multishop.databinding.FragmentUserBinding;
import minh.project.multishop.fragment.UserFragment;

public class UserFragmentViewModel extends BaseFragmentViewModel<UserFragment> {


    /**
     * constructor
     *
     * @param userFragment Fragment object
     */
    public UserFragmentViewModel(UserFragment userFragment) {
        super(userFragment);
    }

    @Override
    public void initView(View view) {
        FragmentUserBinding binding = mFragment.getBinding();

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

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClickEvent(int viewId) {
        switch (viewId) {
            case R.id.iv_qr_code: // QR Code

                break;
            case R.id.tv_sign_in: // Sign in
                Intent intent = new Intent(mFragment.getActivity(), LoginActivity.class);
                mFragment.getActivity().startActivity(intent);
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

                break;
            case R.id.lv_left: // Default
                break;
            default:
                break;
        }
    }
}
