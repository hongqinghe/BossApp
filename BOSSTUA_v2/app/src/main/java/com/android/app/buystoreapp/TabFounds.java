package com.android.app.buystoreapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.app.buystoreapp.news.FoundsContentFragment;
import com.viewpagerindicator.TabPageIndicator;

public class TabFounds extends Fragment {
    private String[] titleArray;
    private String[] titles;

    private NewsContentAdapter mAdapter;
    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Context contextThemeWrapper = new ContextThemeWrapper(
                getActivity(), R.style.StyledIndicators);
        LayoutInflater localInflater = inflater
                .cloneInContext(contextThemeWrapper);
        View parent = localInflater.inflate(R.layout.main_tab_news, null);
        titleArray = getResources().getStringArray(R.array.tab_founds_tag);
        titles = new String[titleArray.length];

        for (int i = 0; i < titleArray.length; i++) {
            titles[i] = titleArray[i];
        }

        mViewPager = (ViewPager) parent.findViewById(R.id.id_news_pager);
        // 关闭预加载，默认一次只加载一个Fragment
        mViewPager.setOffscreenPageLimit(1);
        mAdapter = new NewsContentAdapter(getFragmentManager());
        mViewPager.setAdapter(mAdapter);

        TabPageIndicator indicator = (TabPageIndicator) parent
                .findViewById(R.id.id_news_indicator);
        indicator.setViewPager(mViewPager);
        return parent;
    }
    class NewsContentAdapter extends FragmentStatePagerAdapter {

        public NewsContentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            return new FoundsContentFragment(getActivity(), position);
        }

        @Override
        public Object instantiateItem(View container, int position) {
            FoundsContentFragment fragment = (FoundsContentFragment) super
                    .instantiateItem(container, position);
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position % titles.length];
        }

        @Override
        public int getCount() {
            return titles.length;
        }

    }
}