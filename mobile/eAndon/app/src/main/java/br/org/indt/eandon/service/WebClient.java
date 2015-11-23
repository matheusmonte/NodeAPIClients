package br.org.indt.eandon.service;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;

import br.org.indt.eandon.Utils.Constants;

public class WebClient {
    
    OkHttpClient client = new OkHttpClient();

    Call get(String url, Callback callback) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);

        return call;
    }

    Call post(String url, String json, Callback callback) throws IOException {
        RequestBody body = RequestBody.create(Constants.JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);

        return call;
    }

    Call put(String url, String json, Callback callback) throws IOException {
        RequestBody body = RequestBody.create(Constants.JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);

        return call;
    }
}