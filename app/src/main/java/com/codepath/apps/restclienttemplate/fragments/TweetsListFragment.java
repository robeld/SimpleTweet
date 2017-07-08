package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TweetAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static com.codepath.apps.restclienttemplate.R.id.rvTweet;

/**
 * Created by robeld on 7/3/17.
 */

public class TweetsListFragment extends Fragment implements TweetAdapter.TweetAdapterListener {

    public interface TweetSelectedListener {

        //handle tweet selection
        public void  onTweetSelected(Tweet tweet);
    }
    public TweetAdapter tweetAdapter;
    public ArrayList<Tweet> tweets;
    public RecyclerView rvTweets;

    //inflation happens inside onCreateView

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate the layout
        View v = inflater.inflate(R.layout.fragments_tweets_list, container, false);

        //find RecyclerView
        rvTweets = (RecyclerView) v.findViewById(rvTweet);
        //init arraylist (data source)
        tweets = new ArrayList<>();
        //construct the adapter from data source
        tweetAdapter = new TweetAdapter(tweets, this);
        //RecyclerView set up (layout manager, use adapter)
        rvTweets.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTweets.setAdapter(tweetAdapter);

        return v;
    }
    public void addItems(JSONArray response){
        for(int i = 0; i < response.length(); i++){
            //convert each object to a Tweet model
            //add that Tweet model to our data source
            //notify adapter that we've added an item
            try {
                Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
                tweets.add(tweet);
                tweetAdapter.notifyItemInserted(tweets.size() - 1);
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemSelected(View view, int position) {
        Tweet tweet = tweets.get(position);
        Toast.makeText(getContext(), tweet.body, Toast.LENGTH_LONG).show();
        //((TweetSelectedListener) getActivity()).onTweetSelected(tweet);
    }
    public void addTweet(Tweet tweet){
        Toast.makeText(getContext(), tweet.body, Toast.LENGTH_LONG).show();
        //tweets.add(0, tweet);
        //tweetAdapter.notifyItemInserted(0);
        //rvTweets.getLayoutManager().scrollToPosition(0);
    }

}
