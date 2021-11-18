package minh.project.multishop.viewmodel;

import static minh.project.multishop.utils.CurrencyFormat.currencyFormat;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import minh.project.multishop.CartActivity;
import minh.project.multishop.R;
import minh.project.multishop.adapter.CartAdapter;
import minh.project.multishop.base.BaseActivityViewModel;
import minh.project.multishop.database.entity.User;
import minh.project.multishop.database.repository.UserDBRepository;
import minh.project.multishop.databinding.ActivityCartBinding;
import minh.project.multishop.models.CartItem;
import minh.project.multishop.network.dtos.DTORequest.EditCartRequest;
import minh.project.multishop.network.dtos.DTOResponse.EditCartResponse;
import minh.project.multishop.network.repository.CartRepository;

public class CartActivityViewModel extends BaseActivityViewModel<CartActivity> {

    private ActivityCartBinding mBinding;
    private final UserDBRepository dbRepository;
    private final CartRepository cartRepository;
    private final CartAdapter cartAdapter;
    private final User mUser;
    private List<CartItem> cartList;

    private TextView textActualPrice;
    private TextView textShipCost;
    private TextView textPay;
    private TextView textDelete;
    private CheckBox checkAllSelect;
    private RecyclerView recyclerBagList;
    private TextView textEdit;
    private LinearLayout layoutLoginFirst;
    private RelativeLayout layoutBottom;
    private int totalPrice;
    private int totalQuantity;

    /**
     * constructor
     *
     * @param cartActivity Activity object
     */
    public CartActivityViewModel(CartActivity cartActivity) {
        super(cartActivity);
        cartList = new ArrayList<>();
        dbRepository = UserDBRepository.getInstance();
        cartRepository = CartRepository.getInstance();
        mUser = dbRepository.getCurrentUser();
        cartAdapter = new CartAdapter(mActivity);
    }

    @Override
    public void initView() {
        mBinding = mActivity.getBinding();
        cartAdapter.setOnItemModifyListener(mActivity);

        mBinding.titleOrderEvaluation.tvTitle.setText(mActivity.getString(R.string.cart_title));

        textActualPrice = mBinding.textViewActualPrice;
        textActualPrice.setText(currencyFormat(0));

//        textShipCost = mBinding.textViewShipCost;
        textPay = mBinding.textViewPay;
        textDelete = mBinding.textViewDelete;
        checkAllSelect = mBinding.checkBoxAllSelect;

        recyclerBagList = mBinding.listShoppingCart;
        recyclerBagList.setAdapter(cartAdapter);
        recyclerBagList.setLayoutManager(new LinearLayoutManager(mActivity));

//        textEdit = mBinding.titleOrderEvaluation.
        layoutLoginFirst = mBinding.layoutLoginFirst;
        layoutBottom = mBinding.rlBottom;

        mBinding.titleOrderEvaluation.ivBack.setOnClickListener(mActivity);
        mBinding.textViewPay.setOnClickListener(mActivity);
        checkAllSelect.setOnClickListener(mActivity);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClickEvent(int viewId) {
        switch (viewId){
            case R.id.checkBox_all_select:{
                if (cartList.size() != 0) {
                    if (checkAllSelect.isChecked()) {
                        for (CartItem shoppingCart : cartList) {
                            shoppingCart.setChoose(true);
                        }
                    } else {
                        for (CartItem shoppingCart : cartList) {
                            shoppingCart.setChoose(false);
                        }
                    }
                    modifyData(cartList);
                }
                break;
            }
            case R.id.textView_pay:{
                break;
            }
            case R.id.iv_back:{
                mActivity.finish();
                break;
            }
            default: break;
        }
    }

    public void initData() {
        if(null == mUser){
            cartList = new ArrayList<>();
//            textEdit.setVisibility(View.GONE);
            layoutLoginFirst.setVisibility(View.VISIBLE);
            recyclerBagList.setVisibility(View.GONE);
            layoutBottom.setVisibility(View.GONE);
        } else {
            cartRepository.getListCartData(mUser.getAccToken()).observe(mActivity, cartItems -> {
                cartList.addAll(cartItems);
                cartAdapter.setCartItems(cartItems);
                if(cartAdapter.getItemCount()==0){
                    mBinding.llNoItems.setVisibility(View.VISIBLE);
                    return;
                }
                mBinding.llNoItems.setVisibility(View.GONE);
                layoutLoginFirst.setVisibility(View.GONE);
                recyclerBagList.setVisibility(View.VISIBLE);
                layoutBottom.setVisibility(View.VISIBLE);
            });
        }
    }

    private void modifyData(List<CartItem> bagList) {
        this.cartList = bagList;
        cartAdapter.setCartItems(bagList);
//        bagAdapter.notifyDataSetChanged();
        statistics();
//        mBagRepository.insertAll(bagList, mUser);
    }

    public void statistics() {
        totalPrice = 0;
        totalQuantity = 0;
        if (null != cartList) {
            for (int i = 0; i < cartList.size(); i++) {
                CartItem item = cartList.get(i);
                if (item.isChoose()) {
                    totalQuantity += item.getCount();
                    totalPrice += item.getProduct().getSalePrice() * item.getCount();
                }
            }
        }
        if (0 == totalQuantity) {
            checkAllSelect.setChecked(false);
        }
        textActualPrice.setText(currencyFormat(totalPrice));
    }

    public void onItemChoose(int position, boolean isChecked) {
        cartList.get(position).setChoose(isChecked);
        checkAllSelect.setChecked(isAllChoosed());
        modifyData(cartList);
    }

    public void onItemQuantityAdd(int position, View quantityView) {
        CartItem shoppingCart = cartList.get(position);
        EditCartRequest request = new EditCartRequest(shoppingCart.getProduct().productID,1);
        cartRepository.getAddCartData(mUser.getAccToken(),request).observe(mActivity, editCartResponse -> {
            if(editCartResponse==null){
                Toast.makeText(mActivity, "Không thể chỉnh sửa giỏ hàng", Toast.LENGTH_SHORT).show();
                return;
            }
            Map<Integer,Boolean> checkedMap = new HashMap<>();
            for(CartItem item : cartList){
//                checkedMap.put(item.getID(), item.isChoose());
                if(item.isChoose()){
                    checkedMap.put(item.getID(), true);
                }
            }
            reloadData(checkedMap);
        });
    }

    private void reloadData(Map<Integer, Boolean> checkedMap) {
        cartList.clear();
        cartRepository.getListCartData(mUser.getAccToken()).observe(mActivity, new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                cartList = cartItems;
                for(CartItem item : cartList){
                    if(checkedMap.containsKey(item.getID())) item.setChoose(true);
                }
                modifyData(cartList);
            }
        });
    }

    public void onItemQuantityReduce(int position, View quantityView) {
        CartItem shoppingCart = cartList.get(position);
        EditCartRequest request = new EditCartRequest(shoppingCart.getProduct().productID,1);
        cartRepository.getRemoveCartData(mUser.getAccToken(),request).observe(mActivity, editCartResponse -> {
            if(editCartResponse==null){
                Toast.makeText(mActivity, "Không thể chỉnh sửa giỏ hàng", Toast.LENGTH_SHORT).show();
                return;
            }
            Map<Integer,Boolean> checkedMap = new HashMap<>();
            for(CartItem item : cartList){
//                checkedMap.put(item.getID(), item.isChoose());
                if(item.isChoose()){
                    checkedMap.put(item.getID(), true);
                }
            }
            reloadData(checkedMap);
        });
    }

    private boolean isAllChoosed() {
        if (cartList.isEmpty()) {
            return false;
        }
        for (CartItem shoppingCart : cartList) {
            if (!shoppingCart.isChoose()) {
                return false;
            }
        }
        return true;
    }
}
