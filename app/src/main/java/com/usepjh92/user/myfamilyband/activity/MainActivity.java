package com.usepjh92.user.myfamilyband.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.usepjh92.user.myfamilyband.R;
import com.usepjh92.user.myfamilyband.adapter.FragPageAdapter;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager pager;
    FragPageAdapter fragPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        pager = (ViewPager)findViewById(R.id.pager);
        fragPageAdapter = new FragPageAdapter(getSupportFragmentManager());
        pager.setAdapter(fragPageAdapter);
        tabLayout.setupWithViewPager(pager , true);

        TabLayout.Tab tab1 = tabLayout.getTabAt(0);
        TabLayout.Tab tab2 = tabLayout.getTabAt(1);
        TabLayout.Tab tab3 = tabLayout.getTabAt(2);

        tab1.setIcon(R.drawable.ic_home_black_24dp);
        tab2.setIcon(R.drawable.ic_border_color_black_24dp);
        tab3.setIcon(R.drawable.ic_textsms_black_24dp);

        tabLayout.setSelectedTabIndicatorColor(Color.BLACK);

        Intent intent = getIntent();

        int userId = intent.getExtras().getInt("userId");
        String userName = intent.getExtras().getString("nickName");
        String profileImg = intent.getExtras().getString("profileImg");

        Log.e("main " , userId +"," +userName+ "," +profileImg);


    }

}
