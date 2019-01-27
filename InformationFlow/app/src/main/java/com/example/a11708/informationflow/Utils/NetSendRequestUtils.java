package com.example.a11708.informationflow.Utils;

import javax.security.auth.callback.Callback;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class NetSendRequestUtils {
    public static void sendRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().get().url(address).build();
        client.newCall(request).enqueue(callback);
    }

}
