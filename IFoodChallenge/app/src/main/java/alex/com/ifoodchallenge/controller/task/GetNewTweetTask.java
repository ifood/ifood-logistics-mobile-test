package alex.com.ifoodchallenge.controller.task;

import android.content.Context;
import android.os.AsyncTask;

import alex.com.ifoodchallenge.domain.utils.GenericCallback;

public class GetNewTweetTask extends AsyncTask<String, Void, Integer> {
    private Context context;
    private GenericCallback callback;

    public GetNewTweetTask(Context context, GenericCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected Integer doInBackground(String... params) {

        return null;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
    }
}
