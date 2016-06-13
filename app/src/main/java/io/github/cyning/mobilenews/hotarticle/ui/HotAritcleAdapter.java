package io.github.cyning.mobilenews.hotarticle.ui;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.facebook.drawee.view.SimpleDraweeView;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import io.github.cyning.androidweekly.R;
import io.github.cyning.droidcore.utils.DateUtils;
import io.github.cyning.droidcore.utils.HtmlUtil;
import io.github.cyning.droidcore.utils.StringUtils;
import io.github.cyning.droidcore.utils.ViewUtils;
import io.github.cyning.greendao.HotArticle;
import io.github.cyning.mobilenews.common.CollectionsUtils;
import java.util.List;

/**
 * @author Cyning
 * @since 2016.03.03
 * Time    9:59 PM
 * Desc    <p>热文的Adapter</p>
 */
public class HotAritcleAdapter
    extends RecyclerView.Adapter<HotAritcleAdapter.HotArticleViewHolder> implements
    StickyRecyclerHeadersAdapter<HotAritcleAdapter.DayHolder>{

    private List<HotArticle> articleList = null;
    private Context ctx;

    public HotAritcleAdapter(List<HotArticle> articleList, Context ctx) {
        this.articleList = articleList;
        this.ctx = ctx;
    }

    @Override public HotArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == THREE_PIC) {
            return new HotArticleViewHolder(ViewUtils.inflate(ctx, R.layout.hotarticle_item_3_pic),
                viewType);
        } else if (viewType == ONE_PIC) {
            return new HotArticleViewHolder(
                ViewUtils.inflate(ctx, R.layout.hotarticle_item_one_pic), viewType);
        } else {
            return new HotArticleViewHolder(ViewUtils.inflate(ctx, R.layout.hotarticle_item_no_pic),
                viewType);
        }
    }

    @Override public void onBindViewHolder(HotArticleViewHolder holder, int position) {
        holder.updataPos(position);
    }

    @Override public int getItemCount() {
        return CollectionsUtils.hasEle(articleList) ? articleList.size() : 0;
    }

    @Override public int getItemViewType(int position) {
        HotArticle article = articleList.get(position);
        return getWhichType(article);
    }

    private int getWhichType(HotArticle article) {
        if (article != null && StringUtils.hasText(article.getImgUrl())) {
            return ONE_PIC;
        } else if (article != null) {
            return NO_PIC;
        } else {
            throw new NullPointerException("HotArticle is null");
        }
    }

    public class HotArticleViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
        int type;
        HotArticle article;
        @Bind(R.id.tvTitle) TextView tvTitle;
        @Nullable @Bind(R.id.ivPic1) SimpleDraweeView ivPic1;
        @Nullable @Bind(R.id.ivPic2) SimpleDraweeView ivPic2;
        @Nullable @Bind(R.id.ivPic3) SimpleDraweeView ivPic3;
        @Nullable @Bind(R.id.llPics) LinearLayout llPics;
        @Bind(R.id.tvFrom) TextView tvFrom;
        @Bind(R.id.tvDate) TextView tvDate;
        @Nullable @Bind(R.id.rlBottom) RelativeLayout rlBottom;
        @Nullable @Bind(R.id.tvDescrip) TextView tvDescrip;

        public HotArticleViewHolder(View itemView, int type) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void updataPos(int pos) {
            article = articleList.get(pos);

            tvFrom.setText(article.getCategory());

            //if (StringUtils.isLongNum(article.getUpdateTime())){
            //    long time = Long.valueOf(article.getUpdateTime());
            //    tvDate.setText(DateUtils.toMMDD(time));
            //}

            type = getWhichType(article);
            if (type == ONE_PIC) {
                ivPic1.setImageURI(Uri.parse(article.getImgUrl()));
                String title = StringUtils.hasText(article.getTitle()) ? article.getTitle()
                    : article.getAbstractX();
                tvTitle.setText(HtmlUtil.html2Text2(title));
            } else if (type == NO_PIC) {
                tvTitle.setText(article.getTitle());
                tvDescrip.setText(article.getAbstractX());
            }
        }

        @Override public void onClick(View v) {
            HotDetailActivity.launch(ctx, article);
        }
    }


    public class DayHolder extends RecyclerView.ViewHolder {
        public TextView tvDay ;
        public DayHolder(View itemView) {
            super(itemView);
            tvDay = ViewUtils.find(itemView,R.id.tvGroupName);
        }


    }

    @Override
    public long getHeaderId(int position) {
        HotArticle article = articleList.get(position);
        return DateUtils.strToDateLong(article.getUpdateTime());
    }

    @Override
    public DayHolder onCreateHeaderViewHolder(ViewGroup parent) {
       View itemView = View.inflate(ctx,R.layout.ariticle_group,null);
        DayHolder holder = new DayHolder(itemView);
        return holder;
    }

    @Override
    public void onBindHeaderViewHolder(DayHolder holder, int position) {
        HotArticle article = articleList.get(position);
        holder.tvDay.setText(article.getGroupName());
    }

    private static final int NO_PIC = 0;

    private static final int ONE_PIC = 1;

    private static final int THREE_PIC = 2;


}
