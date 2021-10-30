package minh.project.multishop.adapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import minh.project.multishop.R;
import minh.project.multishop.utils.FetchImage;

public class ProductViewPagerAdapter extends PagerAdapter {

    private static final String TAG = "ProductViewPagerAdapter";
    private final String[] listImg;
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final Handler mHandler;

    public ProductViewPagerAdapter(String[] listImg, Context mContext) {
        this.listImg = listImg;
        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mHandler = new Handler();
    }

    @Override
    public int getCount() {
        return listImg == null ? 0 : listImg.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View viewItem = mLayoutInflater.inflate(R.layout.item_image_product,container,false);
        initView(viewItem,position);
        container.addView(viewItem);
        return viewItem;
    }

    private void initView(View view, int position) {
        ImageView imageView = view.findViewById(R.id.iv_product);
        if (position < listImg.length) { // image
            Log.d(TAG, "initView: "+listImg[position]);
            Glide.with(mContext)
                    .load(listImg[position])
                    .placeholder(R.drawable.progress_bar_loading)
                    .into(imageView);
//            new FetchImage(listImg[position],imageView,mHandler).start();
//            if (listImg.length >= 1) {
//                // imageView.setOnClickListener(v -> initImgSuper(imageView));
//            }
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    }
}
