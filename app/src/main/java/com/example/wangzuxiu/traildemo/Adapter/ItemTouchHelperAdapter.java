package com.example.wangzuxiu.traildemo.Adapter;

/**
 * Created by Neelam on 3/15/2018.
 */



public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
