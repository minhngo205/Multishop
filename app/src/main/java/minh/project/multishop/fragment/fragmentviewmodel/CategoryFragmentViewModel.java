package minh.project.multishop.fragment.fragmentviewmodel;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import minh.project.multishop.adapter.CategoryAdapter;
import minh.project.multishop.adapter.ProductCateAdapter;
import minh.project.multishop.base.BaseFragmentViewModel;
import minh.project.multishop.databinding.FragmentCategoryBinding;
import minh.project.multishop.fragment.CategoryFragment;
import minh.project.multishop.models.Category;
import minh.project.multishop.models.Product;
import minh.project.multishop.network.IAppAPI;
import minh.project.multishop.network.RetroInstance;
import minh.project.multishop.network.dtos.DTOmodels.DTOCategory;
import minh.project.multishop.network.dtos.DTOmodels.DTOProduct;
import minh.project.multishop.network.dtos.Response.GetListProductResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragmentViewModel extends BaseFragmentViewModel<CategoryFragment> {
    private static final String TAG = "CategoryFragment";

    //View
    private RecyclerView mCategoryView;
    private RecyclerView mProductView;
    private LinearLayout noProductView;

    private int firstPosition;
    private List<Category> categoryList;

    private FragmentCategoryBinding mBinding;


    private MutableLiveData<List<Category>> liveCateData;
    private MutableLiveData<List<Product>> liveProdData;
    private final IAppAPI api;

    public LiveData<List<Category>> getListCategory(){
        if(liveCateData==null){
            liveCateData = new MutableLiveData<>();
            loadCateData();
        }
        return liveCateData;
    }

    public LiveData<List<Product>> getProdByCate(int cateID){
        liveProdData = new MutableLiveData<>();
        loadProdData(cateID);
        return liveProdData;
    }

    private void loadProdData(int id) {
        Call<GetListProductResponse> call = api.getProductByCategory(id,40);
        call.enqueue(new Callback<GetListProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetListProductResponse> call, @NonNull Response<GetListProductResponse> response) {
                if(response.isSuccessful()){
                    List<DTOProduct> responseProductList;
                    List<Product> result = new ArrayList<>();
                    if(response.body()!=null){
                        responseProductList = response.body().productList;
                        for (DTOProduct product: responseProductList ) {
                            result.add(product.toProductEntity());
                        }
                        liveProdData.postValue(result);
//                        Log.d(TAG, "Response size: "+result.size());
                    } else {
                        liveProdData.postValue(null);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetListProductResponse> call, @NonNull Throwable t) {
                liveProdData.postValue(null);
            }
        });
    }

    private void loadCateData() {
        Call<List<DTOCategory>> call = api.getAllCategory();
        call.enqueue(new Callback<List<DTOCategory>>() {
            @Override
            public void onResponse(@NonNull Call<List<DTOCategory>> call, @NonNull Response<List<DTOCategory>> response) {
                List<Category> result = new ArrayList<>();
                if (response.isSuccessful()){
                    if(response.body()==null) return;
                    for (DTOCategory dc: response.body()) {
                        result.add(dc.toCateEntity());
                    }
                    liveCateData.postValue(result);
                } else {
                    liveCateData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<DTOCategory>> call, @NonNull Throwable t) {
                liveCateData.postValue(null);
            }
        });
    }

    /**
     * constructor
     *
     * @param categoryFragment Fragment object
     */
    public CategoryFragmentViewModel(CategoryFragment categoryFragment) {
        super(categoryFragment);
        api = RetroInstance.getRetroInstance().create(IAppAPI.class);
    }

    @Override
    public void initView(View view) {
        mBinding = mFragment.getBinding();
        mCategoryView = mBinding.recyclerCatalogueType;
        mProductView = mBinding.recyclerCatalogueProduct;
        noProductView = mBinding.lvNoProduct;
        initCategoryView(firstPosition);
    }

    private void initCategoryView(int showPosition){
        categoryList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mFragment.getContext(), LinearLayoutManager.HORIZONTAL,false);
        mCategoryView.setLayoutManager(layoutManager);
        CategoryAdapter adapter = new CategoryAdapter(categoryList,mFragment.getContext(),showPosition);
        adapter.setOnItemClickListener(position -> {
            initProductView(categoryList.get(position).getID());
            Toast.makeText(mFragment.getActivity(), "id: "+position, Toast.LENGTH_SHORT).show();
        });
        getListCategory().observe(mFragment.getViewLifecycleOwner(), new Observer<List<Category>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Category> categories) {
                categoryList.addAll(categories);
                adapter.notifyDataSetChanged();
            }
        });
        mCategoryView.setAdapter(adapter);
    }

    private void initProductView(int category) {
        getProdByCate(category).observe(mFragment.getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if(products==null || products.size()==0){
                    noProductView.setVisibility(View.VISIBLE);
                    mProductView.setVisibility(View.GONE);
                    return;
                }

                noProductView.setVisibility(View.GONE);
                mProductView.setVisibility(View.VISIBLE);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(mFragment.getContext(), 2);
                mProductView.setLayoutManager(gridLayoutManager);
                mProductView.setNestedScrollingEnabled(false);
                ProductCateAdapter adapter = new ProductCateAdapter(mFragment.getContext(),products);
                mProductView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onClickEvent(int viewId) {

    }

    public void setFirstPosition(int firstPosition) {
        this.firstPosition = firstPosition;
    }
}
