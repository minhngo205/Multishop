package minh.project.multishop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import minh.project.multishop.base.BaseFragment;
import minh.project.multishop.databinding.FragmentNewInBinding;
import minh.project.multishop.fragment.fragmentviewmodel.NewInFragmentViewModel;

public class NewInFragment extends BaseFragment {

    private final NewInFragmentViewModel mViewModel;
    private FragmentNewInBinding mBinding;

    public NewInFragment() {
        // Required empty public constructor
        mViewModel = new NewInFragmentViewModel(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentNewInBinding.inflate(inflater,container,false);
        View viewRoot = mBinding.getRoot();
        mViewModel.initView(viewRoot);
        return viewRoot;
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.initNewInProductRV();
        mViewModel.StartAnimation();
    }

    @Override
    public void onPause() {
        super.onPause();
        mViewModel.StopAnimation();
    }

    public FragmentNewInBinding getBinding() {
        return mBinding;
    }
}