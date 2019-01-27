package com.example.a11708.informationflow.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a11708.informationflow.Activity.MainActivity;
import com.example.a11708.informationflow.Adapter.MessageRecycleListAdapter;
import com.example.a11708.informationflow.Model.Beans;
import com.example.a11708.informationflow.Model.EventBusClass;
import com.example.a11708.informationflow.R;
import com.example.a11708.informationflow.Thread.MessageRunnable;
import com.example.a11708.informationflow.Utils.NetSendRequestUtils;
import com.example.a11708.informationflow.Utils.NetWorkAvailableUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Top extends Fragment {
    private String mUrl;
    private String mInformation;
    private TextView mTextview;
    private int mCurrentCount = 15;
    private int mOldCount = 0;
    private int mNewCount = 0;
    private MessageRecycleListAdapter mAdapter;
    private RecyclerView mRecycleView;
    private SmartRefreshLayout mReflash;
    private Context mContext;
    private List<Beans.ResultBean.DataBean> mMessageList = new ArrayList<Beans.ResultBean.DataBean>();
    int page = 1;//PAGE_COUNT
/*    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String information = msg.obj.toString();
            mMessageList = UseGson(information);
            if (mMessageList.size() == 0) {
                return;
            }
            mContext = getContext();
            mRecycleView = (RecyclerView) getActivity().findViewById(R.id.recycle_message);
            mAdapter = new MessageRecycleListAdapter(mMessageList, getActivity());

        }
    };*/

    public Top() {

        // Required empty public constructor
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top, container, false);
        if(mUrl==null){
            Toast.makeText(getContext(),"网络异常",Toast.LENGTH_SHORT).show();
        }
        InitView(view);
        ShowMessage(mUrl);
        RefashData(view);
        return view;
    }

    private void InitView(View view) {
        mReflash = (SmartRefreshLayout) view.findViewById(R.id.reflash);
        mRecycleView = (RecyclerView) view.findViewById(R.id.recycle_message);
        mAdapter = new MessageRecycleListAdapter(mMessageList, getActivity());
        mTextview = (TextView) view.findViewById(R.id.reflashend);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setNestedScrollingEnabled(false);
        mAdapter.bindToRecyclerView(mRecycleView);
        mAdapter.setEnableLoadMore(true);
        mAdapter.openLoadAnimation();
        mAdapter.disableLoadMoreIfNotFullPage();

    }


    private void RefashData(final View view) {
        mReflash.setOnRefreshListener(new OnRefreshListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                try {
                    mOldCount = mMessageList.size();
                    InitView(view);
                    mMessageList.clear();
                    ShowMessage(mUrl);
                    mAdapter.notifyDataSetChanged();
                } catch (RuntimeException e) {
                    mReflash.finishRefresh();
                }
                if (NetWorkAvailableUtils.networkavailable(getActivity()) == false) {
                    mReflash.finishRefresh();
                    mTextview.setText("刷新失败，请检查网络");
                    mTextview.setBackgroundColor(R.color.pink);
                    mTextview.setTextColor(R.color.red);
                } else {
                    mNewCount = mNewCount - mOldCount;
                    if (mNewCount == 0) {
                        mTextview.setText("暂无更新，建议查看其它频道");
                    } else {
                        mTextview.setText("又为你更新了" + mNewCount + "条内容哦~");
                    }
                }
                mReflash.finishRefresh();
                TranslateMove();
            }
        });
        mReflash.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                try {
                    ++page;
                    Toast.makeText(getContext(), String.valueOf(page), Toast.LENGTH_LONG).show();
                    ShowMessage(mUrl + "&page=" + String.valueOf(page));
                } catch (RuntimeException e) {
                    mReflash.finishRefresh(2000, false);
                }
                if (NetWorkAvailableUtils.networkavailable(getActivity()) == false) {
                    mReflash.finishRefresh(2000, false);
                }
                mAdapter.notifyDataSetChanged();
                mReflash.finishLoadMore(2000);
            }
        });
    }

    private void TranslateMove() {
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation outanim = new TranslateAnimation(0, 0, -60, 0);
        outanim.setDuration(2000);
        //animationSet.setStartOffset(10000);
        animationSet.addAnimation(outanim);
        animationSet.setFillBefore(true);
        outanim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mTextview.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mTextview.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mTextview.startAnimation(animationSet);
    }

    private void ShowMessage(String mUrl) {
        NetSendRequestUtils.sendRequest(mUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String InternetData = response.body().string();
                mMessageList = UseGson(InternetData);
                /*mCurrentMessageList=mMessageList.subList(0,mCurrentCount);*/
                ShowList();
            }
        });
    }

    private void ShowList() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.addData(mMessageList);
                mNewCount = mMessageList.size();
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(EventBusClass event) {
        mInformation = event.getmMsg();
        Log.e("minfor", mInformation);
    }


/*    private void Refresh() {
        try {
            new Thread(new MessageRunnable(mUrl, mHandler)).start();
        } catch (Exception e) {
            mReflash.finishRefresh(2000, false);
        }
        Toast.makeText(getContext(), mUrl, Toast.LENGTH_SHORT).show();
        mReflash.finishRefresh(2000, true);
    }*/

    private List<Beans.ResultBean.DataBean> UseGson(String MessageUrl) {
        Gson gson = new Gson();
        Beans beans = gson.fromJson(MessageUrl, Beans.class);
        if (mMessageList == null) {
            return null;
        }
        return beans.result.data;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
