package com.usepjh92.user.myfamilyband.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.usepjh92.user.myfamilyband.R;
import com.usepjh92.user.myfamilyband.item.WriteItem;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class WriteActivity extends AppCompatActivity {

    EditText editDesc, editTitle;

    ArrayList<WriteItem> writeItems = new ArrayList<>();
    String insertUrl = "http://neworld.dothome.co.kr/android/InsertWrite.php";
    ImageView plusImg;
    final int REQ_PICTURE = 10;
    Uri imgUri;
    String imgPath;
    String upLoadServerURL = "http://neworld.dothome.co.kr/android/Upload.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        editDesc = (EditText) findViewById(R.id.edit_write);
        editTitle = (EditText) findViewById(R.id.edit_title);
        plusImg = (ImageView) findViewById(R.id.plus_img);


    }

    public void clickExit(View v) {
        finish();
    }

    public void clickImg(View v) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, REQ_PICTURE);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Complete action using"), REQ_PICTURE);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQ_PICTURE:
                if (resultCode != RESULT_OK) return;
                //선택된 이미지의 정보를 가지고 있는 Uri 객체 얻어오기
                imgUri = data.getData();
                try {
                    Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                    plusImg.setVisibility(View.VISIBLE);
                    plusImg.setImageBitmap(bm);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    public void clickCom(View v) {

        new Thread() {
            @Override
            public void run() {

                upLoadData();
            }
        }.start();
    }

//    public void upLoadFile() {
//        final String lineEnd = "\r\n";
//        final String twoHyphens = "--";
//        final String boundary = "*****";
//
//        imgPath = imgUri.toString();
//        String[] projection = {MediaStore.Images.Media.DATA};
//        Cursor cursor = getContentResolver().query(imgUri, projection, null, null, null);
//        if (imgPath.contains("content://")) {
//            if (cursor != null && cursor.getCount() != 0) {
//                if (cursor != null) {
//                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                    cursor.moveToFirst();
//                    imgPath = cursor.getString(column_index);
//
//                }
//            }
//        } else if (imgPath.contains("file://")) {
//            //파일매니저로 선택했을시
//            imgPath = imgUri.getPath();
//        }
//
//        Log.e("imgpath", imgPath);
//
//        new Thread() {
//
//            @Override
//            public void run() {
//
//                try {
//                    URL url = new URL(upLoadServerURL);
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    conn.setRequestMethod("POST");
//                    conn.setDoInput(true);
//                    conn.setDoOutput(true);
//                    conn.setUseCaches(false);
//
//                    //파일 전송의 헤더 영역 속성 설정...
//                    conn.setRequestProperty("Connection", "Keep-Alive");
//                    conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
//
//                    //파일전송의 body 영역에 들어갈 Data 작성 및 Output
//                    OutputStream os = conn.getOutputStream();
//                    DataOutputStream dos = new DataOutputStream(os);
//
//                    dos.writeBytes(twoHyphens + boundary + lineEnd);
//                    dos.writeBytes("Content-Disposition:form-data;name=uploaded_file;filename=" + imgPath + lineEnd);
//                    dos.writeBytes(lineEnd);
//                    //파일전송의 body 에 들어갈 실제 이미지의 byte 데이터 output
//                    FileInputStream fis = new FileInputStream(imgPath);
//                    int availableByte = fis.available(); // 보낼 이미지의 총 바이트수 얻어오기...
//                    Log.i("avilable", availableByte + "");
//                    //데이터의 전송은 1024 byte(1kb) 단위로 나누어서 보냄..
//                    int bufferSize = Math.min(availableByte, 1024);
//                    byte[] buffer = new byte[bufferSize];
//
//                    //이미지 파일에서 buffer 배열로 이미지의 바이트데이터를 읽어오기
//                    //현재 읽을 번째의 0번째부터 버퍼사이즈만큼 읽어오기
//                    int readByte = fis.read(buffer, 0, bufferSize); //리턴값 읽어온 바이트 수.. 없으면 -1
//
//                    while (readByte > 0) {
//                        Log.i("bufferSize", buffer.length + "");
//                        dos.write(buffer, 0, bufferSize); //파일 전송에 body 에 들어갈 Data 작성..
//                        availableByte = fis.available(); // 읽어간 값을 제외한 나머지 바이트 수...
//                        bufferSize = Math.min(availableByte, 1024);
//                        readByte = fis.read(buffer, 0, bufferSize);
//
//                    }
//
//                    dos.writeBytes(lineEnd);
//                    dos.writeBytes(twoHyphens + boundary + lineEnd);
//                    dos.flush();
//                    dos.close();
//                    Log.i("complete", "aaaaaa");
//                    //서버로 부터 파일업로드가 잘 되었는지 응답받기...
//                    InputStream is = conn.getInputStream();
//                    InputStreamReader isr = new InputStreamReader(is);
//                    BufferedReader reader = new BufferedReader(isr);
//
//                    StringBuffer sb = new StringBuffer();
//                    String line = reader.readLine();
//
//                    while (line != null) {
//                        Log.i("line", line);
//                        line = reader.readLine();
//                    }
//
//                } catch (MalformedURLException e) {
//                    Log.i("URL", "URL ERROR");
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    Log.i("io", "io ERROR");
//                    e.printStackTrace();
//                }
//            }//run method...
//        }.start();
//
//
//    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            finish();
        }
    };

    public void upLoadData() {

        new Thread() {

            @Override
            public void run() {

                //이 작업은 DB 테이블 생성이기 때문에 무조건 해야함..

                String title = editTitle.getText().toString();
                String desc = editDesc.getText().toString();

                try {
                    title = URLEncoder.encode(title, "utf-8");
                    desc = URLEncoder.encode(desc, "utf-8");

                    URL url = new URL(insertUrl);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);

                    String data = "title=" + title + "&maindesc=" + desc;

                    OutputStream os = conn.getOutputStream();
                    os.write(data.getBytes());
                    os.flush();
                    os.close();
                    InputStream is = conn.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);

                    StringBuffer sb = new StringBuffer();
                    String line = reader.readLine();
                    while (line != null) {
                        Log.i("line", line);
                        sb.append(line);
                        line = reader.readLine();
                    }

                    if (imgUri != null) {

                        final String lineEnd = "\r\n";
                        final String twoHyphens = "--";
                        final String boundary = "*****";

                        imgPath = imgUri.toString();

                        String[] projection = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(imgUri, projection, null, null, null);
                        if (imgPath.contains("content://")) {
                            if (cursor != null && cursor.getCount() != 0) {
                                if (cursor != null) {
                                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                                    cursor.moveToFirst();
                                    imgPath = cursor.getString(column_index);

                                }
                            }
                        } else if (imgPath.contains("file://")) {
                            //파일매니저로 선택했을시
                            imgPath = imgUri.getPath();
                        }


                        URL url1 = new URL(upLoadServerURL);
                        HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
                        conn1.setRequestMethod("POST");
                        conn1.setDoInput(true);
                        conn1.setDoOutput(true);
                        conn1.setUseCaches(false);

                        //파일 전송의 헤더 영역 속성 설정...
                        conn1.setRequestProperty("Connection", "Keep-Alive");
                        conn1.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

                        //파일전송의 body 영역에 들어갈 Data 작성 및 Output
                        OutputStream os1 = conn1.getOutputStream();
                        DataOutputStream dos1 = new DataOutputStream(os1);

                        dos1.writeBytes(twoHyphens + boundary + lineEnd);
                        dos1.writeBytes("Content-Disposition:form-data;name=uploaded_file;filename=" + imgPath + lineEnd);
                        dos1.writeBytes(lineEnd);

                        Log.e("FilePath", imgPath + "");

                        //파일전송의 body 에 들어갈 실제 이미지의 byte 데이터 output
                        FileInputStream fis1 = new FileInputStream(imgPath);
                        int availableByte = fis1.available(); // 보낼 이미지의 총 바이트수 얻어오기...
                        Log.i("avilable", availableByte + "");
                        //데이터의 전송은 1024 byte(1kb) 단위로 나누어서 보냄..
                        int bufferSize = Math.min(availableByte, 1024);
                        byte[] buffer = new byte[bufferSize];

                        //이미지 파일에서 buffer 배열로 이미지의 바이트데이터를 읽어오기
                        //현재 읽을 번째의 0번째부터 버퍼사이즈만큼 읽어오기
                        int readByte = fis1.read(buffer, 0, bufferSize); //리턴값 읽어온 바이트 수.. 없으면 -1

                        while (readByte > 0) {
                            Log.i("bufferSize", buffer.length + "");
                            dos1.write(buffer, 0, bufferSize); //파일 전송에 body 에 들어갈 Data 작성..
                            availableByte = fis1.available(); // 읽어간 값을 제외한 나머지 바이트 수...
                            bufferSize = Math.min(availableByte, 1024);
                            readByte = fis1.read(buffer, 0, bufferSize);

                        }
                        dos1.writeBytes(lineEnd);
                        dos1.writeBytes(twoHyphens + boundary + lineEnd);
                        dos1.flush();
                        dos1.close();
                        Log.i("complete", "aaaaaa");
                        InputStream is1 = conn1.getInputStream();
                        InputStreamReader isr1 = new InputStreamReader(is1);
                        BufferedReader reader1 = new BufferedReader(isr1);

                        StringBuffer sb1 = new StringBuffer();
                        String line1 = reader1.readLine();

                        while (line1 != null) {
                            Log.i("line", line1);
                            sb1.append(line1);
                            line1 = reader1.readLine();
                        }

                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(WriteActivity.this, "저장이 되었습니다.", Toast.LENGTH_SHORT).show();
                            editTitle.setText("");
                            editDesc.setText("");
                        }
                    });

                    handler.sendEmptyMessage(0);

                    imgUri = null;

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }.start();


    }
}
