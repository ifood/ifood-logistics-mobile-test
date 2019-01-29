package alex.com.ifoodchallenge.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import alex.com.ifoodchallenge.R;
import alex.com.ifoodchallenge.controller.MainController;
import alex.com.ifoodchallenge.controller.adapter.MainAdapter;
import alex.com.ifoodchallenge.controller.scheduler.VerifyNewTweetsScheduler;
import alex.com.ifoodchallenge.domain.entity.Tweet;
import alex.com.ifoodchallenge.domain.utils.GenericCallback;

public class MainActivity extends AppCompatActivity {

    private MainController mainController;

    private EditText etUserId;
    private ImageButton ivNewTweets;
    private RecyclerView rvTweets;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindComponents();

        mainController = new MainController();

        View.OnFocusChangeListener listener = new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    GenericCallback<String> callback = new GenericCallback<String>() {
                        @Override
                        public void call(String result) {
                            if (this.success) {

                            } else {
                                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    final SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString("user", etUserId.getText().toString());
                    edit.apply();
                    mainController.callTweets(MainActivity.this, callback, etUserId.getText().toString(), "search", null);
                }
            }
        };

        etUserId.setOnFocusChangeListener(listener);

        List<Tweet> tweetsList = new ArrayList<>();
        Tweet tweettest1 = new Tweet();
        tweettest1.setId_str("teste");
        tweettest1.setText("How happy things are, I'm very happy about everything in my life. I'm happy");
        tweetsList.add(tweettest1);
        Tweet tweettest2 = new Tweet();
        tweettest2.setId_str("teste");
        tweettest2.setText("How sad things are. I'm really sad about everything in my life. I'm sad. I don't like anything, all thing are annoying and stupid");
        tweetsList.add(tweettest2);
        Tweet tweettest3 = new Tweet();
        tweettest3.setId_str("teste");
        tweettest3.setText("How neutral things are");
        tweetsList.add(tweettest3);

        MainAdapter mainAdapter = new MainAdapter(this, tweetsList);
        rvTweets.setAdapter(mainAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvTweets.setLayoutManager(layoutManager);
        rvTweets.setItemAnimator(new DefaultItemAnimator());
        rvTweets.addItemDecoration(new DividerItemDecoration(getApplicationContext(), layoutManager.getOrientation()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        VerifyNewTweetsScheduler.getNewTweets(getApplicationContext());
    }

    @Override
    protected void onStop() {
        super.onStop();
        VerifyNewTweetsScheduler.removeGetNewTweets(getApplicationContext());
    }

    private void bindComponents() {
        etUserId = findViewById(R.id.et_user_id);
        ivNewTweets = findViewById(R.id.iv_new_tweets);
        rvTweets = findViewById(R.id.rv_tweets);
    }
}
