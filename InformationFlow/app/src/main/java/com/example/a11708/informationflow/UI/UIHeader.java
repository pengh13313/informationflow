package com.example.a11708.informationflow.UI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a11708.informationflow.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

public class UIHeader extends RelativeLayout implements RefreshHeader {
    private ProgressBar mCircleProgressView;
    private TextView mTextView;

    public UIHeader(Context context) {
        super(context);
        setGravity(Gravity.CENTER_HORIZONTAL);
        setMinimumHeight(dp2px(context, 80));
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);
        View headerView = View.inflate(context, R.layout.my_refresh_header, null);
        mCircleProgressView = (ProgressBar) headerView.findViewById(R.id.circleProgressView);
        //mCircleProgressView.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progressbarstyle));
        mTextView = (TextView) headerView.findViewById(R.id.reflashword);
        addView(headerView, params);
    }

    public UIHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        setGravity(Gravity.CENTER_HORIZONTAL);
        setMinimumHeight(dp2px(context, 80));
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);
        View headerView = View.inflate(context, R.layout.my_refresh_header, null);
        mCircleProgressView = (ProgressBar) headerView.findViewById(R.id.circleProgressView);
        //mCircleProgressView.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progressbarstyle));
        mTextView = (TextView) headerView.findViewById(R.id.reflashword);
        addView(headerView, params);
    }

    public UIHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.CENTER_HORIZONTAL);
        setMinimumHeight(dp2px(context, 80));
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);
        View headerView = View.inflate(context, R.layout.my_refresh_header, null);
        mCircleProgressView = (ProgressBar) headerView.findViewById(R.id.circleProgressView);
        //mCircleProgressView.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progressbarstyle));
        mTextView = (TextView) headerView.findViewById(R.id.reflashword);
        addView(headerView, params);
    }


    private int dp2px(Context context, int i) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                i, context.getResources().getDisplayMetrics());
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {
    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int extendHeight) {

    }

    @Override
    public void onPulling(float percent, int offset, int height, int extendHeight) {
        if (mCircleProgressView == null) return;
        float startPercent = 0.20f;
        if (percent > startPercent && percent < 1) {
            float tempPercent = (percent - startPercent) * 1.0f / (1 - startPercent);
            int p = (int) tempPercent;
            mCircleProgressView.setProgress(p);
        }
    }


    @Override
    public void onReleasing(float percent, int offset, int height, int extendHeight) {

    }

    @Override
    public void onReleased(RefreshLayout refreshLayout, int height, int extendHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int extendHeight) {
        RotateAnimation ta = new RotateAnimation(0, 360,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        ta.setDuration(500);
        ta.setRepeatCount(10000);
        ta.setInterpolator(new LinearInterpolator());
        ta.setFillAfter(true);
        mCircleProgressView.startAnimation(ta);
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        mCircleProgressView.clearAnimation();
        return 100;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            case None: // 无状态
                if (mCircleProgressView != null) mCircleProgressView.setProgress(0);
                break;
            case PullDownToRefresh: // 可以下拉状态
                mTextView.setText("下拉刷新");
                break;
            case Refreshing: // 刷新中状态
                mTextView.setText("正在刷新");
                break;
            case ReleaseToRefresh:  // 释放就开始刷新状态
                mTextView.setText("释放刷新");
                break;
        }

    }
}
