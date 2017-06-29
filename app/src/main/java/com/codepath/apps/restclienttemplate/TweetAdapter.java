package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by robeld on 6/27/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {
    private List<Tweet> mTweets;
    Context context;
    //pass in Tweets array in constructor
    public TweetAdapter(List<Tweet> tweets){
        mTweets = tweets;
    }
    //inflate layout for each row, cache references into ViewHolder

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);

        return viewHolder;
    }

    //bind values based on element's position

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get data according to position
        Tweet tweet = mTweets.get(position);

        //populate views according to data
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        holder.tvTimestamp.setText(getRelativeTimeAgo(tweet.createdAt));
        holder.tvAccount.setText("@" + tweet.user.screenName);

        Glide.with(context).load(tweet.user.profileImageUrl).into(holder.ivProfileImage);
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }
    //create viewholder class

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvTimestamp;
        public TextView tvAccount;

        public ViewHolder(View itemView){
            super(itemView);

            //perform findViewById lookups

            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvTimestamp = (TextView) itemView.findViewById(R.id.tvTimestamp);
            tvAccount = (TextView) itemView.findViewById(R.id.tvAccount);

        }
    }
    // Method for getting relative timestamp from https://gist.github.com/nesquena/f786232f5ef72f6e10a7
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

}
