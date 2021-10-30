package minh.project.multishop.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import minh.project.multishop.R;
import minh.project.multishop.base.BaseFragment;
import minh.project.multishop.databinding.FragmentUserBinding;
import minh.project.multishop.fragment.fragmentviewmodel.UserFragmentViewModel;

public class UserFragment extends BaseFragment implements View.OnClickListener {

    private final UserFragmentViewModel mViewModel;
    private FragmentUserBinding mBinding;


    public UserFragment() {
        mViewModel = new UserFragmentViewModel(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentUserBinding.inflate(inflater,container,false);
        View viewRoot = mBinding.getRoot();
        mViewModel.initView(viewRoot);
        return viewRoot;
    }

    public FragmentUserBinding getBinding() {
        return mBinding;
    }

    @Override
    public void onClick(View view) {
        mViewModel.onClickEvent(view.getId());
    }
}