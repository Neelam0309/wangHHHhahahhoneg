package com.example.wangzuxiu.traildemo.Adapter;

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

    public TabPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.flag = 0;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                StationInfoFragment stationInfoFragment = new StationInfoFragment();
                return stationInfoFragment;
            case 1:
                if (flag == 0) {
                    StationDiscussionFragment stationDiscussionFragment = new StationDiscussionFragment();
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
