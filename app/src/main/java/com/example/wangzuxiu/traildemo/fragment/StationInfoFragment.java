package com.example.wangzuxiu.traildemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wangzuxiu.traildemo.Activity.AddNewItemActivity;
import com.example.wangzuxiu.traildemo.Adapter.ItemListAdapter;
import com.example.wangzuxiu.traildemo.R;

public class StationInfoFragment extends Fragment {
    private TextView tvStationName;
    private Button btnUpload;
    private RecyclerView rvMyItemList;
    private RecyclerView.Adapter itemListAdapter;
    private RecyclerView.LayoutManager itemListManager;
    private String[][] myItemList = {{"fileURL", "My thought is ...", "Zhang Peiyan", "2018-03-02"},
            {"fileURL", "My thought is ...", "Zhang Peiyan", "2018-03-03"},};

    public StationInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_station_info, container, false);

        tvStationName = (TextView) fragmentView.findViewById(R.id.tv_station_name);
        tvStationName.setText(getActivity().getTitle());

        btnUpload = (Button) fragmentView.findViewById(R.id.btn_upload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, AddNewItemActivity.class);
                context.startActivity(intent);
            }
        });

        // Use MapView mv_station_location to display the map

        // use ListView / RecyclerView to display the Uploaded Items (Not implemented yet)
        rvMyItemList = (RecyclerView) fragmentView.findViewById(R.id.my_item_list);
        rvMyItemList.setHasFixedSize(false);

        itemListManager = new LinearLayoutManager(getActivity());
        rvMyItemList.setLayoutManager(itemListManager);

        itemListAdapter = new ItemListAdapter(myItemList);
        rvMyItemList.setAdapter(itemListAdapter);

        return fragmentView;
    }
}

