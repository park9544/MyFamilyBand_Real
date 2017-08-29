package com.usepjh92.user.myfamilyband.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.usepjh92.user.myfamilyband.R;
import com.usepjh92.user.myfamilyband.fragment.Fragment3;

public class TodayDate extends AppCompatActivity {

    EditText editInput;

    String ymd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_date);

        editInput = (EditText)findViewById(R.id.date_input);

        Intent intent = getIntent();
        ymd = intent.getStringExtra("ymd");

    }

    public void clickBtn(View v){

        String input = editInput.getText().toString();
        String ymdMsgUpload = "http://neworld.dothome.co.kr/android/ymdMsg.php";

        //TODO:ymd와 input을 서버로 업로드..

        RequestQueue requestQueue = Volley.newRequestQueue(TodayDate.this);

        SimpleMultiPartRequest smpr = new SimpleMultiPartRequest(Request.Method.POST, ymdMsgUpload, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(TodayDate.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TodayDate.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });

        smpr.addStringParam("ymd" , ymd);
        smpr.addStringParam("msg" , input);
        requestQueue.add(smpr);


        Intent intent = getIntent();
        intent.putExtra("input" , input);
        setResult(RESULT_OK, intent);
        Log.e("input" , input);
        finish();

    }

}
