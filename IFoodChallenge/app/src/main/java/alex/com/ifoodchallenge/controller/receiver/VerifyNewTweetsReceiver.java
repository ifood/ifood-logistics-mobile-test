package alex.com.ifoodchallenge.controller.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import alex.com.ifoodchallenge.controller.MainController;
import alex.com.ifoodchallenge.domain.utils.GenericCallback;

public class VerifyNewTweetsReceiver extends BroadcastReceiver {

    private MainController mainController = new MainController();

    @Override
    public void onReceive(Context context, Intent intent) {
        verifyNewTweets(context);
    }

    private void verifyNewTweets(final Context context) {
        GenericCallback<String> callback = new GenericCallback<String>() {
            @Override
            public void call(String result) {
                Toast.makeText(context, "The task is running", Toast.LENGTH_SHORT).show();
            }
        };
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared_user", 0);
        String user = sharedPreferences.getString("user", "");
        mainController.callTweets(context, callback, user, "task_running", "last_tweet_id");
    }
}
