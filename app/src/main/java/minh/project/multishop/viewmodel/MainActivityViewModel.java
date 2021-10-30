package minh.project.multishop.viewmodel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import minh.project.multishop.MainActivity;
import minh.project.multishop.R;
import minh.project.multishop.base.BaseActivityViewModel;
import minh.project.multishop.fragment.CategoryFragment;
import minh.project.multishop.fragment.HomeFragment;
import minh.project.multishop.fragment.NewInFragment;
import minh.project.multishop.fragment.UserFragment;

public class MainActivityViewModel extends BaseActivityViewModel<MainActivity> {

    private final List<Fragment> fragmentList = new ArrayList<>();
    // Home
    private HomeFragment homeFragment;
    // catalogue
    private CategoryFragment categoryFragment;
    // shopCar
    private NewInFragment newInFragment;

    private UserFragment userFragment;

    private final BottomNavigationView.OnNavigationItemSelectedListener navigationListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.home:{
                            if (homeFragment == null) {
                                homeFragment = new HomeFragment();
                            }
                            addFragment(homeFragment);
                            showFragment(homeFragment);
                            break;
                        }
                        case R.id.category:{
                            if (categoryFragment == null) {
                                categoryFragment = new CategoryFragment();
                            }
                            addFragment(categoryFragment);
                            showFragment(categoryFragment);
                            break;
                        }
                        case R.id.new_in:{
                            if(newInFragment == null){
                                newInFragment = new NewInFragment();
                            }
                            addFragment(newInFragment);
                            showFragment(newInFragment);
                            break;
                        }
                        case R.id.cart:{
                            Toast.makeText(mActivity, "Cart", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case R.id.user:{
                            if(userFragment==null){
                                userFragment = new UserFragment();
                            }
                            addFragment(userFragment);
                            showFragment(userFragment);
                            break;
                        }
                        default: break;
                    }
                    return true;
                }
            };
    /**
     * constructor
     *
     * @param mainActivity Activity object
     */
    public MainActivityViewModel(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    public void initView() {
        mActivity.getMainBinding().barHome.ivTakePhoto.setOnClickListener(mActivity);
        mActivity.getMainBinding().barHome.imageSearch.setOnClickListener(mActivity);
        mActivity.getMainBinding().barHome.barSearch.setOnClickListener(mActivity);
        mActivity.getMainBinding().barHome.ivInterestKits.setOnClickListener(mActivity);

        mActivity.getMainBinding().bottomNavigationView
                .setOnNavigationItemSelectedListener(navigationListener);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClickEvent(int viewId) {
        switch (viewId) {
            case R.id.bar_search: // search

                break;
            case R.id.iv_interest_kits: // interest_kits

                break;
            case R.id.iv_take_photo: // take photo

                break;
            default:
                break;
        }
    }

    public void removeAllFragment() {
        for (Fragment fragment : fragmentList) {
            mActivity.getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    private void showFragment(Fragment fragment) {
        for (Fragment frag : fragmentList) {
            if (frag != fragment) {
                mActivity.getSupportFragmentManager().beginTransaction().hide(frag).commit();
            }
        }
        mActivity.getSupportFragmentManager().beginTransaction().show(fragment).commit();
    }

    private void addFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            mActivity.getSupportFragmentManager().beginTransaction().add(R.id.frame_main, fragment).commit();
            fragmentList.add(fragment);
        }
    }

    public void initFragment() {
        Intent intent = mActivity.getIntent();
        if (null != intent) {
            String tab = intent.getStringExtra("tab");
            if ("newIn".equals(tab)) {
                return;
            }
        }
        homeFragment = new HomeFragment();
        addFragment(homeFragment);
        showFragment(homeFragment);
    }

    public boolean backToHomeFragment() {
//        if (mainBinding.tabsRg.getCheckedRadioButtonId() != R.id.tab_home) {
//            mainBinding.tabsRg.check(R.id.tab_home);
//            return true;
//        }
        return false;
    }
}
