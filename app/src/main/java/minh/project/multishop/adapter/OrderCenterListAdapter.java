package minh.project.multishop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import minh.project.multishop.R;
import minh.project.multishop.activity.OrderDetailActivity;
import minh.project.multishop.network.dtos.DTOResponse.OrderDetailResponse;
import minh.project.multishop.network.dtos.DTOmodels.DTOOrderItemResponse;
import minh.project.multishop.utils.CurrencyFormat;
import minh.project.multishop.utils.Statistics;

public class OrderCenterListAdapter extends RecyclerView.Adapter<OrderCenterListAdapter.MyViewHolder> {

    private final Context mContext;
    private List<OrderDetailResponse> listOrder;

    public OrderCenterListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setListOrder(List<OrderDetailResponse> listOrder) {
        this.listOrder = listOrder;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ordercenter_list, parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OrderDetailResponse detail = listOrder.get(position);

        int totalCount = 0;
        for(DTOOrderItemResponse item : detail.orderItemResponses){
            totalCount += item.getCount();
        }

        holder.orderID.setText(mContext.getString(R.string.order_center_order_number,detail.orderID));
        holder.status.setText(Statistics.getStatusText(detail.orderStatus));

        OrderItemAdapter adapter = new OrderItemAdapter(mContext);
        adapter.setOrderItemList(detail.castToModelList());
        LinearLayoutManager layoutManager = new MyLinearLayoutManager(mContext);
        holder.orderItemList.setLayoutManager(layoutManager);
        holder.orderItemList.setAdapter(adapter);

        holder.productTotalInfo.setText(mContext.getString(R.string.order_center_total,totalCount) + CurrencyFormat.currencyFormat(detail.totalCost));
        View.OnClickListener redirectToOrderDetail = view -> {
            Intent intent = new Intent(mContext, OrderDetailActivity.class);
            intent.putExtra("ORDER_ID",detail.orderID);
            mContext.startActivity(intent);
        };
        holder.itemView.setOnClickListener(redirectToOrderDetail);
        holder.orderItemList.setOnClickListener(redirectToOrderDetail);
    }

    @Override
    public int getItemCount() {
        return null == listOrder || listOrder.isEmpty() ? 0 : listOrder.size();
    }

    private static class MyLinearLayoutManager extends LinearLayoutManager {
        public MyLinearLayoutManager(Context context) {
            super(context);
        }

        @Override
        public boolean canScrollVertically() {
            return false;
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView orderID;

        TextView status;

        TextView productTotalInfo;

//        TextView payOrder;

//        TextView modifyOrder;

//        TextView cancelOrder;

        RecyclerView orderItemList;

//        RelativeLayout orderOperation;

//        RelativeLayout expressingOper;

//        TextView confirmOrder;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            orderID = itemView.findViewById(R.id.text_order_number);
            status = itemView.findViewById(R.id.text_pending_payment_time);
            productTotalInfo = itemView.findViewById(R.id.text_product_total_info);
            orderItemList = itemView.findViewById(R.id.item_list_order);
        }
    }
}
