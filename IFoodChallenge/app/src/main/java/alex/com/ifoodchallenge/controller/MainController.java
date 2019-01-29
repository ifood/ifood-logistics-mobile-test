package alex.com.ifoodchallenge.controller;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import alex.com.ifoodchallenge.controller.interfaces.TweetService;
import alex.com.ifoodchallenge.domain.utils.GenericCallback;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainController {

    public void callTweets(Context context, final GenericCallback callback, final String user, final String condition, final String since_id) {
        GenericCallback<String> c = new GenericCallback<String>() {
            @Override
            public void call(String token) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);

                final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .readTimeout(60, TimeUnit.SECONDS)
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .addInterceptor(logging)
                        .build();

                final Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.twitter.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(okHttpClient)
                        .build();
                TweetService api = retrofit.create(TweetService.class);
                Call<ResponseBody> call = null;
                if (condition.equals("search")) {
                    call = api.getTweets(user);
                } else {
                    call = api.getNewTweets(user, since_id);
                }
                call.enqueue(new Callback<ResponseBody>() {

                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            callback.object = response.body();
                            callback.success = true;
                            callback.call("Success");
                        } else {
                            callback.object = null;
                            callback.success = false;
                            callback.call("Error");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        callback.object = null;
                        callback.success = false;
                        callback.call(t.getMessage());
                        t.printStackTrace();
                    }
                });
            }
        };
        requestToken(c);
    }

    private void requestToken(final GenericCallback c) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.twitter.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        TweetService api = retrofit.create(TweetService.class);
        Call<ResponseBody> call = api.requestToken();
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    c.object = response.body();
                    c.success = true;
                    c.call("Success token");
                } else {
                    c.object = null;
                    c.success = false;
                    c.call("Error token");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                c.object = null;
                c.success = false;
                c.call(t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
