package com.bestcode95.staffmanage.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.bestcode95.staffmanage.R;
import com.bestcode95.staffmanage.left.LeftFragment;
import com.bestcode95.staffmanage.right.RightFragment;
import com.bestcode95.staffmanage.utils.BottomTab;
import com.bestcode95.staffmanage.utils.ListBtAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager mViewpager;
    private List<Fragment> mTabs = new ArrayList<Fragment>();
    private FragmentPagerAdapter mAdapter;
    private ListView listView = null;

    ListBtAdapter listBtAdapter = null;
    private List<BottomTab> mTabIndicators = new ArrayList<BottomTab>();

    //listView的键
    private String ICON_KEY = "icon_key";
    private String TEXT_KEY = "text_key";
    private String BT_KEY = "bt_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDatas();
        mViewpager.setAdapter(mAdapter);
        mTabIndicators.get(0).setIconAlpha(1.0f);
        mTabIndicators.get(1).setIconAlpha(0);
        //第二个参数为true有动画效果
    }


    /**
     * 按钮点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        resetOtherTabs();
        switch (v.getId()) {
            case R.id.main_tab_one:
                mTabIndicators.get(0).setIconAlpha(1.0f);
                //第二个参数为true有动画效果
                mViewpager.setCurrentItem(0, false);
                break;
            case R.id.main_tab_two:
                mTabIndicators.get(1).setIconAlpha(1.0f);
                //第二个参数为true有动画效果
                mViewpager.setCurrentItem(1, false);
                break;
            default:
                break;
        }
    }

    /**
     * 初始化界面组件
     */
    private void initView() {
        mViewpager = (ViewPager) findViewById(R.id.main_tab_viewpager);
        listView = (ListView) findViewById(android.R.id.list);
        BottomTab one = (BottomTab) findViewById(R.id.main_tab_one);
        mTabIndicators.add(one);
        BottomTab two = (BottomTab) findViewById(R.id.main_tab_two);
        mTabIndicators.add(two);
        mViewpager.setOnPageChangeListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
    }

    /**
     * 初始化数据,跳转到不同的Fragment
     */
    private void initDatas() {
        LeftFragment leftFragment = new LeftFragment();
        RightFragment rightFragment = new RightFragment();
        mTabs.add(leftFragment);
        mTabs.add(rightFragment);
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mTabs.get(position);
            }

            @Override
            public int getCount() {
                return mTabs.size();
            }
        };
    }


    /**
     * 重置其他的tABIndicator的颜色
     */
    private void resetOtherTabs() {
        for (int i = 0; i < mTabIndicators.size(); i++) {
            mTabIndicators.get(i).setIconAlpha(0);
        }
    }

    /**
     * 解决Fragment无法使用onTouchEvent()方法
     */
    public interface MyTouchListener {
        void onTouchEvent(MotionEvent event);
    }

    // 保存MyTouchListener接口的列表
    private ArrayList<MyTouchListener> myTouchListeners = new ArrayList<MyTouchListener>();


    /**
     * 分发触摸事件给所有注册了MyTouchListener的接口
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyTouchListener listener : myTouchListeners) {
            listener.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        Log.d("TAG", "position = " + position + ", opsitionOffset = " + positionOffset);
        if (positionOffset > 0) {
            BottomTab left = mTabIndicators.get(position);
            BottomTab right = mTabIndicators.get(position + 1);

            left.setIconAlpha(1 - positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
