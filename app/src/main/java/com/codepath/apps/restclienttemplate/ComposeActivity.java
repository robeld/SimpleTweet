package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {
    TwitterClient client;
    private TextView mTextView;
    private EditText etCompose;
    private int color;
    public static String TWITTER_KEY = "tweet_key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        client = TwitterApp.getRestClient();
        mTextView = (TextView) findViewById(R.id.mTextView);
        etCompose = (EditText) findViewById(R.id.etCompose);
        // code from https://stackoverflow.com/questions/3013791/live-character-count-for-edittext
        etCompose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String print = Integer.toString(s.length()) + " characters / 140";
                if (s.length() >= 0 && s.length() <= 70) color = Color.GREEN;
                if(s.length() > 70 && s.length() <= 140) color = Color.YELLOW;
                if(s.length() > 140) color = Color.RED;
                mTextView.setTextColor(color);
                mTextView.setText(print);
            }
        });
    }

    public void onSubmit(View v) {
        String body = etCompose.getText().toString();
        client.sendTweet(body, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Tweet tweet = null;
                try {
                    tweet = Tweet.fromJSON(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent data = new Intent();
                data.putExtra(TWITTER_KEY, tweet);
                setResult(RESULT_OK, data);
                Log.d("ComposeActivity", tweet.body);
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(ComposeActivity.this, "Failure", Toast.LENGTH_LONG).show();
            }
        });
    }
}
