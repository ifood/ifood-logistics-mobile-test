package alex.com.ifoodchallenge.controller.handler;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import alex.com.ifoodchallenge.controller.task.GetNewTweetTask;
import alex.com.ifoodchallenge.domain.utils.GenericCallback;

public class GetNewTweetHandler extends Handler {
    private final Context context;
    private final GenericCallback callback;

    public GetNewTweetHandler(Context context,
                              GenericCallback callback){
        this.callback = callback;
        this.context = context;
    }

    @Override
    public void handleMessage(Message msg) {
        synchronized (this){
            try{
                new GetNewTweetTask(context, callback).execute().get();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
