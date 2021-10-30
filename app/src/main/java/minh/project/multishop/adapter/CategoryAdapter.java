package minh.project.multishop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import minh.project.multishop.R;
import minh.project.multishop.models.Category;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private final List<Category> categoryList;
    private final Context mContext;
    private OnItemClickListener onItemClickListener;
    private final int showPosition;
    private TextView currentType;
    private View currentBar;
    private int row_index = 0;

    public CategoryAdapter(List<Category> categoryList, Context context, int showPosition) {
        this.categoryList = categoryList;
        this.mContext = context;
        this.showPosition = showPosition;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_category_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Category category = categoryList.get(position);

        holder.cateName.setText(category.getName());
        holder.cateName.setTextColor(mContext.getResources().getColor(R.color.item_catalogue_no_select));
        holder.bar.setVisibility(View.GONE);

        if(row_index==showPosition){
            holder.itemView.performClick();
            Log.d("TAG", "onBindViewHolder: "+showPosition);
        }

        holder.itemView.setOnClickListener(v -> {
            row_index = position;
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }
            changeStatus(holder,row_index);
        });
    }

    private void changeStatus(MyViewHolder holder, int position) {
        Category category = categoryList.get(position);
        if (currentType != null) {
            currentType.setTextColor(mContext.getResources().getColor(R.color.item_catalogue_no_select));
            currentBar.setVisibility(View.GONE);
            currentType.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        }

        currentType = holder.cateName;
        currentBar = holder.bar;

        currentType.setText(category.getName());
        currentBar.setVisibility(View.GONE);
        currentType.setBackgroundColor(mContext.getResources().getColor(R.color.red_type_1));
        currentType.setTextColor(mContext.getResources().getColor(R.color.white));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView cateName;
        View bar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cateName = itemView.findViewById(R.id.catalogue_type);
            bar = itemView.findViewById(R.id.item_bar);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        /**
         * This function is used to make action when item is clicked.
         *
         * @param position item position
         */
        void onItemClick(int position);
    }
}
