package com.example.zhoukao01.http.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * 分类：
 * 1.饿汉式
 * <p>
 * 2.懒汉式
 * <p>
 * 1.子线程请求数据
 * 2.将数据发送到主线程
 * <p>
 * handler -- android中的一种通信机制
 * 线程间的通信
 * 原理：
 */
public class HttpUtils {
    private static final String TAG = "HttpUtils---";
    private MyHandler myHandler = new MyHandler();
    private static final int SUCCESS = 0;
    private static final int ERROR = 1;

    //单利
    private static HttpUtils httpUtils = new HttpUtils();
    private HttpUtilListener httpUtilsListener;

    private HttpUtils() {
    }//构造方法私有化

    public static HttpUtils getInstance() {
        if (httpUtils == null) {
            httpUtils = new HttpUtils();
        }
        return httpUtils;
    }

    //封装get
    public void get(final String url) {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL u = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) u.openConnection();
                    connection.setConnectTimeout(3000);
                    if (connection.getResponseCode() == 200) {
                        InputStream inputStream = connection.getInputStream();
                        String json = inputStream2String2(inputStream);
                        //发送数据
                        Message message = myHandler.obtainMessage();
                        message.what = SUCCESS;
                        message.obj = json;
                        myHandler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Message message = myHandler.obtainMessage();
                    message.what = ERROR;
                    message.obj = e.getMessage();
                    myHandler.sendMessage(message);
                }
            }
        }.start();
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    //成功
                    String json = (String) msg.obj;
                    Log.d(TAG, "handleMessage: " + json);
                    httpUtilsListener.getSuccess(json);
                    break;
                case ERROR:
                    //失败
                    String error = (String) msg.obj;
                    Log.d(TAG, "handleMessage: " + error);
                    httpUtilsListener.getError(error);
                    break;
            }
        }
    }

    //1.定义接口
    public interface HttpUtilListener {
        void getSuccess(String json);

        void getError(String error);
    }

    //2.外部访问的方法
    public void setHttpUtilsListener(HttpUtilListener httpUtilsListener) {
        this.httpUtilsListener = httpUtilsListener;
    }

    //将流--字符串
    public String inputStream2String(InputStream inputStream) {
        int len = 0;
        byte[] butter = new byte[1024];
        StringBuffer stringBuffer = new StringBuffer();
        try {

            while ((len = inputStream.read(butter)) != -1) {
                String s = new String(butter, 0, len);
                stringBuffer.append(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return stringBuffer.toString();
    }

    //将流--字符串  使用字符流--部分乱码
    public String inputStream2String2(InputStream inputStream) {
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(reader);
        StringBuffer sr = new StringBuffer();
        try {

            String s = null;
            while ((s = br.readLine()) != null) {
                sr.append(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sr.toString();
    }


}
