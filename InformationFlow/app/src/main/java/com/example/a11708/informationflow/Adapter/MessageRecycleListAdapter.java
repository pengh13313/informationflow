package com.example.a11708.informationflow.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.a11708.informationflow.Activity.WebviewActivity;
import com.example.a11708.informationflow.Model.Beans;
import com.example.a11708.informationflow.R;
import com.wenld.multitypeadapter.base.MultiItemView;

import java.util.List;


public class MessageRecycleListAdapter extends BaseMultiItemQuickAdapter<Beans.ResultBean.DataBean, BaseViewHolder> {

    private List<Beans.ResultBean.DataBean> mList;
    private Context mContext;
    private ImageView mPic;
    private ImageView mPic2;
    private ImageView mPic3;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MessageRecycleListAdapter(List data, Context context) {
        super(data);
        mContext = context;
        addItemType(0, R.layout.recycle_item_three_photo);
        addItemType(1, R.layout.recycle_item_one_photo);
    }

    @Override
    protected void convert(BaseViewHolder helper, final Beans.ResultBean.DataBean item) {
        switch (helper.getItemViewType()) {
            case 1:
                helper.setText(R.id.recycle_title, item.title);
                helper.setText(R.id.recycle_author, item.author_name);
                helper.setText(R.id.recycle_time, item.date);
                Glide.with(mContext).load(item.thumbnail_pic_s).thumbnail(0.2f).into((ImageView) helper.getView(R.id.recycle_img1));
                helper.setOnClickListener(R.id.lin_onephoto, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, WebviewActivity.class);
                        intent.putExtra("weburl", item.url);
                        intent.putExtra("webtitle", item.author_name);
                        mContext.startActivity(intent);
                    }
                });
                break;
            case 0:
                helper.setText(R.id.recycle2_title, item.title);
                helper.setText(R.id.recycle2_author, item.author_name);
                helper.setText(R.id.recycle2_time, item.date);
                Glide.with(mContext).load(item.thumbnail_pic_s).thumbnail(0.2f).into((ImageView) helper.getView(R.id.recycle2_img1));
                Glide.with(mContext).load(item.thumbnail_pic_s02).thumbnail(0.2f).into((ImageView) helper.getView(R.id.recycle2_img2));
                Glide.with(mContext).load(item.thumbnail_pic_s03).thumbnail(0.2f).into((ImageView) helper.getView(R.id.recycle2_img3));
                helper.setOnClickListener(R.id.lin_threephoto, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, WebviewActivity.class);
                        intent.putExtra("weburl", item.url);
                        intent.putExtra("webtitle", item.author_name);
                        mContext.startActivity(intent);
                    }
                });
                break;
            default:
                break;
        }

    }


}
