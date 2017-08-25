package com.usepjh92.user.myfamilyband.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.usepjh92.user.myfamilyband.R;
import com.usepjh92.user.myfamilyband.activity.MoreActivity;
import com.usepjh92.user.myfamilyband.item.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.CheckedOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;


public class Frag2RecyclerAdapter extends RecyclerView.Adapter<Frag2RecyclerAdapter.ViewHolder> {

    ArrayList<Item> items;
    Context context;
    View itemView;

    public Frag2RecyclerAdapter(ArrayList<Item> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public Frag2RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemView = LayoutInflater.from(context).inflate(R.layout.frag2_iist_item, parent, false);

        ViewHolder holder = new ViewHolder(itemView);


        return holder;
    }

    @Override
    public void onBindViewHolder(Frag2RecyclerAdapter.ViewHolder holder, int position) {

        Intent intent = ((Activity)context).getIntent();

        int userId = intent.getExtras().getInt("userId");
        String userName = intent.getExtras().getString("nickName");
        String profileImg = intent.getExtras().getString("profileImg");

        Log.e("frag2 " , userId +"," +userName+ "," +profileImg);

        holder.tvName.setText(items.get(position).title);
        holder.tvDesc.setText(items.get(position).desc);
        holder.userName.setText(userName);
        Glide.with(context).load(profileImg).into(holder.img);

    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView img;
        TextView tvName;
        TextView tvDesc;
        ImageView imgDesc;
        TextView userName;

        public ViewHolder(final View itemView) {
            super(itemView);

            img = (CircleImageView) itemView.findViewById(R.id.circle_img);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvDesc = (TextView) itemView.findViewById(R.id.tv_desc);
            imgDesc = (ImageView) itemView.findViewById(R.id.img_view);
            userName = (TextView) itemView.findViewById(R.id.user_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, MoreActivity.class);
                    context.startActivity(intent);
                }
            });

        }
    }

}
