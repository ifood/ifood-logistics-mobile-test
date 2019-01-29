package alex.com.ifoodchallenge.controller.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface TweetService {
    @Headers("Content-Type: application/json")
    @GET("1.1/search/tweets.json")
    Call<ResponseBody> getTweets(@Query("q") String user);

    @Headers("Content-Type: application/json")
    @GET("1.1/search/tweets.json")
    Call<ResponseBody> getNewTweets(@Query("q") String user, @Query("since_id") String since_id);

    @Headers("Content-Type: application/json")
    @GET("oauth/authenticate")
    Call<ResponseBody> requestToken();
}
