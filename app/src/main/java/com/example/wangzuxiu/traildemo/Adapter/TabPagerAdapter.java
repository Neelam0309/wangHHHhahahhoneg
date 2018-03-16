package com.example.wangzuxiu.traildemo.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.wangzuxiu.traildemo.fragment.StationDiscussionFragment;
import com.example.wangzuxiu.traildemo.fragment.StationInfoFragment;
import com.example.wangzuxiu.traildemo.fragment.StationPostFragment;
import com.example.wangzuxiu.traildemo.fragment.StationUpdateFragment;


/**
 * Created by mia on 04/03/18.
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;
    int flag;
    String message;
    String trailKey;
    String stationId;

    public TabPagerAdapter(FragmentManager fm, int numOfTabs, String trailKey,String stationId) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.flag = 0;
        this.trailKey=trailKey;
        this.stationId=stationId;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                StationInfoFragment stationInfoFragment = new StationInfoFragment();
                Bundle bundle=new Bundle();
                bundle.putString("trailKey",trailKey);
                stationInfoFragment.setArguments(bundle);
                return stationInfoFragment;
            case 1:
                if (flag == 0) {
                    StationDiscussionFragment stationDiscussionFragment = new StationDiscussionFragment();
                    bundle = new Bundle();
                    bundle.putString("stationId",stationId);
                    stationDiscussionFragment.setArguments(bundle);
                    return stationDiscussionFragment;
                }
            default:
                StationUpdateFragment stationUpdateFragment = new StationUpdateFragment();
                return stationUpdateFragment;
        }
    }

    public void toggleDiscussion(int value) {
        flag = value;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (flag == 1 && object instanceof StationDiscussionFragment ||
                flag == 0 && object instanceof StationPostFragment)
            return POSITION_NONE;
        return POSITION_UNCHANGED;
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
