package minh.project.multishop.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import minh.project.multishop.R;
import minh.project.multishop.adapter.HomeProductAdapter;
import minh.project.multishop.base.BaseFragment;
import minh.project.multishop.databinding.FragmentHomeBinding;
import minh.project.multishop.fragment.fragmentviewmodel.HomeFragmentViewModel;
import minh.project.multishop.models.Product;

public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";
    private FragmentHomeBinding homeBinding;
    private final HomeFragmentViewModel homeViewModel;

    public HomeFragment() {
        // Required empty public constructor
        homeViewModel = new HomeFragmentViewModel(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater,container,false);
        View viewRoot = homeBinding.getRoot();
        homeViewModel.initView(viewRoot);
        homeViewModel.initViewPager();
        return viewRoot;
    }

    @Override
    public void onResume() {
        super.onResume();
        homeViewModel.initHomeProductRecyclerView();
        homeViewModel.StartAnimation();
    }

    @Override
    public void onPause() {
        super.onPause();
        homeViewModel.StopAnimation();
    }

    public FragmentHomeBinding getHomeBinding() {
        return homeBinding;
    }
}