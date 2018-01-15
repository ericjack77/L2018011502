package com.example.student.l2018011502;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ImageView iv;
    TextView tv;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv=findViewById(R.id.imageView);
        tv=findViewById(R.id.textView);
        pb=findViewById(R.id.progressBar);
    }

    public void click1(View v)
    {
        new Thread(){
            @Override
            public void run() {
                super.run();
               String str_url="http://campingouzouni.com/wp-content/uploads/2015/05/beach-volley.jpg";
                //String str_url="https://5.imimg.com/data5/UH/ND/MY-4431270/red-rose-flower-500x500.jpg";

                URL url;
                try {
                    url = new URL(str_url);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.connect();
                    InputStream inputStream = conn.getInputStream();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] buf = new byte[1024];
                    final int totallength = conn.getContentLength();
                    int sum=0;
                    int length;
                    while((length = inputStream.read(buf)) != -1)
                    {
                        sum += length;
                        final int tmp =sum;
                        final double per = ((double) tmp/(double) totallength)*100;

                        bos.write(buf,0,length);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pb.setProgress((int)per);
                                //pb.setProgress(100* tmp /totallength);
                                tv.setText(String.valueOf(per));

                            }
                        });
                    }
                    byte[] results = bos.toByteArray();
                    final Bitmap bmp = BitmapFactory.decodeByteArray(results,0,results.length);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iv.setImageBitmap(bmp);
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    public void click2(View v)
    {

    }

    class Mythread extends AsyncTask<Integer,Integer,String>
    {

        @Override
        protected String doInBackground(Integer... integers) {
            return null;
        }
    }
}
