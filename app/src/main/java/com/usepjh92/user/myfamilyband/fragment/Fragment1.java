package com.usepjh92.user.myfamilyband.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.usepjh92.user.myfamilyband.R;
import com.usepjh92.user.myfamilyband.activity.WriteActivity;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;


public class Fragment1 extends Fragment {

    CircleImageView circleImg;
    TextView tv;
    TextView tvName;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);

        circleImg = (CircleImageView) view.findViewById(R.id.pro_img);
        tv = (TextView) view.findViewById(R.id.textView);
        tvName = (TextView)view.findViewById(R.id.tv_nickname);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WriteActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getActivity().getIntent();

        int userId = intent.getExtras().getInt("userId");
        String userName = intent.getExtras().getString("nickName");
        String profileImg = intent.getExtras().getString("profileImg");

        Log.e("frag1 " , userId +"," +userName+ "," +profileImg);
        tvName.setText(userName);
        Glide.with(getActivity()).load(profileImg).into(circleImg);

//        circleImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//
//                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                    intent.setType("image/*");
//                    startActivityForResult(intent, REQ_PICTURE);
//                } else {
//
//                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    intent.setType("image/*");
//                    startActivityForResult(Intent.createChooser(intent, "Complete action using"), REQ_PICTURE);
//                }
//            }
//        });

        return view;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        switch (requestCode){
//
//            case REQ_PICTURE:
//                if (resultCode != getActivity().RESULT_OK) return;
//                imgUri = data.getData();
//                imgPath = imgUri.toString();
//                if(imgPath.contains("content://")){ //갤러리앱(or사진앱)으로 선택했을때
//
//                    Cursor cursor= getActivity().getContentResolver().query(imgUri, null, null, null, null);
//                    if(cursor!=null && cursor.getCount()!=0){
//                        cursor.moveToFirst();
//                        imgPath= cursor.getString( cursor.getColumnIndex("_data") );
//                    }
//
//                }else if(imgPath.contains("file://")){//파일매니져로 선택했을때(디바이스의 기종에 따라 다름)
//                    imgPath= imgUri.getPath();
//                }
//
//                imgProfile(imgPath);
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }

//    public String imgProfile(String imgPath) {
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//
//        SimpleMultiPartRequest smpr = new SimpleMultiPartRequest(Request.Method.POST, upLoadServerURL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
//            }
//        });
//        smpr.addFile("upload", imgPath);
//        requestQueue.add(smpr);
//        return imgPath;
//    }

}
