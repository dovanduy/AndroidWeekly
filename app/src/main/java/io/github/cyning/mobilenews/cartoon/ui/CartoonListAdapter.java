package io.github.cyning.mobilenews.cartoon.ui;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.cyning.androidweekly.R;
import io.github.cyning.mobilenews.cartoon.model.CartoonInfo;
import io.github.cyning.mobilenews.common.CollectionsUtils;

/**
 * @author Cyning
 * @since 2016.04.10
 * Time    12:19 AM
 * Desc    <p>类/接口描述</p>
 */

public class CartoonListAdapter extends RecyclerView.Adapter<CartoonListAdapter.CartoonViewHolder> {

    List<CartoonInfo> cartoonInfoList = null;
    private Context context;
    private LayoutInflater inflater;

    public CartoonListAdapter(List<CartoonInfo> cartoonInfoList, Context context) {
        this.cartoonInfoList = cartoonInfoList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public CartoonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.cartoon_item, null);
        CartoonViewHolder holder = new CartoonViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CartoonViewHolder holder, int position) {

        CartoonInfo cartoonInfo = cartoonInfoList.get(position);
        holder.ivPic1.setImageURI(Uri.parse(cartoonInfo.getCover_url()));
        holder.tvTitle.setText(cartoonInfo.getArtist_name());
        holder.tvLastUp.setText("更新至"+cartoonInfo.getLastup());
        holder.tvPv.setText("热度:"+cartoonInfo.getPgv_count());
        holder.tvStory.setText(cartoonInfo.getBrief_intrd());

    }

    @Override
    public int getItemCount() {
        return CollectionsUtils.hasEle(cartoonInfoList) ? cartoonInfoList.size() : 0;
    }

    class CartoonViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivPic1)
        SimpleDraweeView ivPic1;
        @Bind(R.id.tvTitle)
        TextView tvTitle;
        @Bind(R.id.tvPv)
        TextView tvPv;
        @Bind(R.id.tvLastUp)
        TextView tvLastUp;
        @Bind(R.id.tvStory)
        TextView tvStory;

        public CartoonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }

}
