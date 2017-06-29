package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {
    TwitterClient client;
    int RESULT_OK = 20;
    public static String TWEET_KEY = "tweet";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        client = TwitterApp.getRestClient();
    }
    public void onSubmit(View v) {
        EditText etCompose = (EditText) findViewById(R.id.etCompose);
        String body = etCompose.getText().toString();
        client.sendTweet(body, new JsonHttpResponseHandler(){
            Tweet tweet = null;
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    tweet = Tweet.fromJSON(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(ComposeActivity.this, tweet.body, Toast.LENGTH_LONG).show();
                Intent data = new Intent();
                data.putExtra(TWEET_KEY, tweet);
                setResult(RESULT_OK, data);
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
