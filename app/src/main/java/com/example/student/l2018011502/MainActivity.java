package com.example.student.l2018011502;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    TextView tv,tv2,tv3;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv=findViewById(R.id.imageView);
        tv=findViewById(R.id.textView);
        tv2=findViewById(R.id.textView2);
        tv3=findViewById(R.id.textView3);
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
        MyTask task =new MyTask();
        task.execute(5);
    }

    class MyTask extends AsyncTask<Integer,Integer,String>
    {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tv3.setText(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tv2.setText(String.valueOf(values[0]));
        }

        @Override
        protected String doInBackground(Integer... integers) {
            for(int i=0;i<=integers[0];i++)
            {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("NET", "doInBackground,i="+i);
                publishProgress(i);//發佈進度 給onProgressUpdate
            }
            return "okay";
        }
    }

    public void click3(View v)
    {
            AsyncPhoto task=new AsyncPhoto();
            task.execute("http://campingouzouni.com/wp-content/uploads/2015/05/beach-volley.jpg");
    }

    class AsyncPhoto extends AsyncTask<String,Integer,Bitmap>
    {
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            iv.setImageBitmap(bitmap);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String str_url = strings[0];
            URL url;
            Bitmap bmp = null;
            try {
                url = new URL(str_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                InputStream inputStream = conn.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int length;
                while ((length = inputStream.read(buf)) != -1) {
                    bos.write(buf, 0, length);
                }
                byte[] results = bos.toByteArray();
                bmp = BitmapFactory.decodeByteArray(results, 0, results.length);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bmp;
        }
    }
}
