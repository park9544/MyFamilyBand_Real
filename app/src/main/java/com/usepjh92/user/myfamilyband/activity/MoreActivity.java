package com.usepjh92.user.myfamilyband.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.usepjh92.user.myfamilyband.R;

public class MoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        
    }

    public void clickBack(View v){

        finish();

    }
}
