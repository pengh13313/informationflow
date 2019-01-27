package com.example.a11708.informationflow.Thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.a11708.informationflow.Model.Beans;
import com.example.a11708.informationflow.Model.EventBusClass;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MessageRunnable implements Runnable {
    private Handler mHandler;
    private String mUrl;
    //private List<String> mReceiveUrlList = new ArrayList<>();
    private String mContent;
    private OkHttpClient mClient = new OkHttpClient();
    Response response;

    public MessageRunnable(String url, Handler handler) {
        mUrl = url;
        mHandler = handler;
    }

    @Override
    public void run() {
        mContent = Receive();
        EventBus.getDefault().post(new EventBusClass(mContent));
        Message msg = Message.obtain();
        msg.obj = mContent;
        mHandler.sendMessage(msg);

    }

    private String Receive() {
        String content = null;
        try {
            final Request request = new Request.Builder().get().url(mUrl).build();
            response = mClient.newCall(request).execute();
            if (response == null) {
                return null;
            }
            if (response.isSuccessful()) {
                content = response.body().string();
                if (content == null) {
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }


}
