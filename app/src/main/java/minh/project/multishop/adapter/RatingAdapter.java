package minh.project.multishop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import minh.project.multishop.R;
import minh.project.multishop.models.Rating;
import minh.project.multishop.utils.DateConverter;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.MyViewHolder> {

    private final Context mContext;
    private List<Rating> ratingList;

    public RatingAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setRatingList(List<Rating> ratingList) {
        this.ratingList = ratingList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_review,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Rating rate = ratingList.get(position);

        holder.tvRate.setText(String.valueOf(rate.getRate()));
        holder.tvName.setText(rate.getUser().name);
        holder.tvDate.setText(DateConverter.DateTimeFormat(rate.getCreated_at()));
        holder.tvComment.setText(rate.getComment());
    }

    @Override
    public int getItemCount() {
        return null == ratingList || ratingList.isEmpty() ? 0 : ratingList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvRate;
        TextView tvName;
        TextView tvDate;
        TextView tvComment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRate = itemView.findViewById(R.id.tvProductReviewRating);
            tvName = itemView.findViewById(R.id.tvProductReviewCmt);
            tvDate = itemView.findViewById(R.id.tvProductReviewDuration);
            tvComment = itemView.findViewById(R.id.tvProductReviewSubHeading);
        }
    }
}
