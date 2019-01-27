package com.example.a11708.informationflow.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.a11708.informationflow.Adapter.FPAdapter;
import com.example.a11708.informationflow.Fragment.Top;
import com.example.a11708.informationflow.R;
import com.example.a11708.informationflow.Utils.NetWorkAvailableUtils;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private SlidingTabLayout mTabLayout;
    private ViewPager mViewPager;
    private FPAdapter madapter;
    private long EXITTIME = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        InitView();
    }

    private void InitView() {
        mTabLayout = (SlidingTabLayout) findViewById(R.id.tablayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        titleList.add("头条");
        titleList.add("社会");
        titleList.add("国内");
        titleList.add("娱乐");
        titleList.add("体育");
        titleList.add("军事");
        titleList.add("科技");
        titleList.add("财经");
        titleList.add("时尚");
        fragmentList = initFragment();
        madapter = new FPAdapter(getSupportFragmentManager(), fragmentList, titleList);
        mViewPager.setAdapter(madapter);
        mTabLayout.setViewPager(mViewPager);
    }

    private List<Fragment> initFragment() {
        List<Fragment> fragmentList = new ArrayList<>();
        String[] title = {"top", "shehui", "guonei", "yule", "tiyu", "junshi", "keji", "caijing", "shishang"};
        String PrefixUrl = "https://api.avatardata.cn/TouTiao/Query?key=6516219528234df39f4b6e138f230545&type=";
        for (int i = 0; i < title.length; i++) {
            //i<title.length
            Top mFragment = new Top();
            mFragment.setmUrl(PrefixUrl + title[i]);
            fragmentList.add(mFragment);
        }
        return fragmentList;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (System.currentTimeMillis() - EXITTIME > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_LONG).show();
                EXITTIME = System.currentTimeMillis();
                return true;
            } else {
                finish();
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
