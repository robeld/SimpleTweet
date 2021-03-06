package com.codepath.apps.restclienttemplate.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by robeld on 7/5/17.
 */

public class TweetsPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[] {"Home", "Mentions"};
    private Context context;
    static HomeTimelineFragment homeTimelineFragment = new HomeTimelineFragment();
    static MentionsTimelineFragment mentionsTimelineFragment = new MentionsTimelineFragment();

    // return the total # of fragments
    public TweetsPagerAdapter(FragmentManager fm, Context context){
        super(fm);
        this.context = context;
    }
    @Override
    public int getCount() {
        return 2;
    }

    // return the fragment to use depending on the position

    @Override
    public Fragment getItem(int position) {
        if(position == 0 ){
            /*if(homeTimelineFragment == null){
                return new HomeTimelineFragment();
            } else {
                return homeTimelineFragment;
            }*/
            return homeTimelineFragment;
        } else if(position == 1){
            /*if(mentionsTimelineFragment == null){
                return new MentionsTimelineFragment();
            } else {
                return mentionsTimelineFragment;
            }*/
            return mentionsTimelineFragment;
        } else {
            return null;
        }
    }

    // return title


    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
