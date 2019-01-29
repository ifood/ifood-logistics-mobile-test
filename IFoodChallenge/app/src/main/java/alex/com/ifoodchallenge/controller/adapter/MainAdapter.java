package alex.com.ifoodchallenge.controller.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.services.language.v1.CloudNaturalLanguage;
import com.google.api.services.language.v1.CloudNaturalLanguageRequestInitializer;
import com.google.api.services.language.v1.model.AnnotateTextRequest;
import com.google.api.services.language.v1.model.AnnotateTextResponse;
import com.google.api.services.language.v1.model.Document;
import com.google.api.services.language.v1.model.Entity;
import com.google.api.services.language.v1.model.Features;

import java.io.IOException;
import java.util.List;

import alex.com.ifoodchallenge.R;
import alex.com.ifoodchallenge.domain.entity.Tweet;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<Tweet> tweetsList;
    private Context context;
    private final String CLOUD_API_KEY = "AIzaSyDSA1fRr2N06TgSMKATUtK66iSsQztnW5w";

    public MainAdapter(Context context, List<Tweet> tweetsList) {
        this.context = context;
        this.tweetsList = tweetsList;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainAdapter.ViewHolder viewHolder, int i) {
        final Tweet t = tweetsList.get(i);
        viewHolder.tvTweet.setText(t.getText());

        viewHolder.clRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CloudNaturalLanguage naturalLanguageService =
                        new CloudNaturalLanguage.Builder(
                                AndroidHttp.newCompatibleTransport(),
                                new AndroidJsonFactory(),
                                null
                        ).setCloudNaturalLanguageRequestInitializer(
                                new CloudNaturalLanguageRequestInitializer(CLOUD_API_KEY)
                        ).setApplicationName("IFood Challenge").build();

                String transcript = t.getText();

                Document document = new Document();
                document.setType("PLAIN_TEXT");
                document.setLanguage("en-US");
                document.setContent(transcript);

                Features features = new Features();
                features.setExtractEntities(true);
                features.setExtractDocumentSentiment(true);

                final AnnotateTextRequest request = new AnnotateTextRequest();
                request.setDocument(document);
                request.setFeatures(features);

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            AnnotateTextResponse response =
                                    naturalLanguageService.documents()
                                            .annotateText(request).execute();

                            final List<Entity> entityList = response.getEntities();
                            final float sentiment = response.getDocumentSentiment().getScore();

                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String entities = "";
                                    for(Entity entity:entityList) {
                                        entities += "\n" + entity.getName().toUpperCase();
                                    }

                                    if (sentiment > 0.3f) {
                                        viewHolder.ivEmoji.setVisibility(View.VISIBLE);
                                        viewHolder.ivEmoji.setImageResource(R.drawable.smiling_emoji);
                                        viewHolder.clRow.setBackgroundColor(Color.YELLOW);
                                    } else if (sentiment < -0.1f) {
                                        viewHolder.ivEmoji.setVisibility(View.VISIBLE);
                                        viewHolder.ivEmoji.setImageResource(R.drawable.sad_emoji);
                                        viewHolder.clRow.setBackgroundColor(Color.BLUE);

                                    } else {
                                        viewHolder.ivEmoji.setVisibility(View.VISIBLE);
                                        viewHolder.ivEmoji.setImageResource(R.drawable.neutral_emoji);
                                        viewHolder.clRow.setBackgroundColor(Color.GRAY);
                                    }

                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return tweetsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout clRow;
        private TextView tvTweet;
        private ImageView ivEmoji;

        public ViewHolder(View itemView) {
            super(itemView);

            clRow = itemView.findViewById(R.id.cl_row);
            tvTweet = itemView.findViewById(R.id.tv_tweet);
            ivEmoji = itemView.findViewById(R.id.iv_emoji);
        }
    }
}

