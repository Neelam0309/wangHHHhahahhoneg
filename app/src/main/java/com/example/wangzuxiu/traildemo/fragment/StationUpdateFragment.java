package com.example.wangzuxiu.traildemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wangzuxiu.traildemo.Adapter.ItemListAdapter;
import com.example.wangzuxiu.traildemo.R;

public class StationUpdateFragment extends Fragment {
    private RecyclerView rvItemList;
    private RecyclerView.Adapter itemListAdapter;
    private RecyclerView.LayoutManager itemListManager;
    private TextView tvEmptyItemList;
    private String[][] itemList = {{"fileURL", "My thought is ...", "Zhang Peiyan", "2018-03-02"}, {"fileURL", "My Idea is ...", "Sriraj", "2018-03-01"}};


    public StationUpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_station_update, container, false);

        rvItemList = (RecyclerView) fragmentView.findViewById(R.id.item_list);
        rvItemList.setHasFixedSize(false);

        itemListManager = new LinearLayoutManager(getActivity());
        rvItemList.setLayoutManager(itemListManager);

        itemListAdapter = new ItemListAdapter(itemList);
        rvItemList.setAdapter(itemListAdapter);

        tvEmptyItemList = (TextView) fragmentView.findViewById(R.id.tv_empty_item_list);
        tvEmptyItemList.setVisibility(itemList.length == 0 ? View.VISIBLE : View.GONE);

        return fragmentView;
    }

}
