package com.usepjh92.user.myfamilyband.adapter;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.usepjh92.user.myfamilyband.fragment.Fragment1;
import com.usepjh92.user.myfamilyband.fragment.Fragment2;
import com.usepjh92.user.myfamilyband.fragment.Fragment3;


public class FragPageAdapter extends FragmentPagerAdapter {

    Fragment[] frags = new Fragment[3];

    public FragPageAdapter(FragmentManager fm) {
        super(fm);


        frags[0] = new Fragment1();
        frags[1] = new Fragment2();
        frags[2] = new Fragment3();

    }

    @Override
    public Fragment getItem(int position) {
        return frags[position];
    }

    @Override
    public int getCount() {
        return frags.length;
    }

}
