package com.example.wangzuxiu.traildemo.fragment;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wangzuxiu.traildemo.Adapter.ItemListAdapter;
import com.example.wangzuxiu.traildemo.R;
import com.example.wangzuxiu.traildemo.model.ContributedItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StationUpdateFragment extends Fragment {
    private RecyclerView rvItemList;
    private RecyclerView.Adapter itemListAdapter;
    private RecyclerView.LayoutManager itemListManager;
    private TextView tvEmptyItemList;
    private String uid= FirebaseAuth.getInstance().getUid();
    // private String[][] itemList = {{"fileURL", "My thought is ...", "Zhang Peiyan", "2018-03-02"}, {"fileURL", "My Idea is ...", "Sriraj", "2018-03-01"}};
    private ArrayList<ContributedItem> ContributedItemList;

    public StationUpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View fragmentView = inflater.inflate(R.layout.fragment_station_update, container, false);

        // modify this according to context
        final String userID = uid;
        final String stationID = "-L7XDL5ditoY4_Dr0BWG";

        ContributedItemList = new ArrayList<ContributedItem>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String temp_ref = "items/" + stationID;
        DatabaseReference ref = database.getReference(temp_ref);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ContributedItemList.clear();
                for (DataSnapshot ContributedItemSnapshot : dataSnapshot.getChildren()) {
                    ContributedItem ci = ContributedItemSnapshot.getValue(ContributedItem.class);
                    ContributedItemList.add(ci);
                }

                rvItemList = (RecyclerView) fragmentView.findViewById(R.id.item_list);
                rvItemList.setHasFixedSize(false);

                itemListManager = new LinearLayoutManager(getActivity());
                rvItemList.setLayoutManager(itemListManager);

                itemListAdapter = new ItemListAdapter(ContributedItemList, getContext());
                rvItemList.setAdapter(itemListAdapter);

                tvEmptyItemList = (TextView) fragmentView.findViewById(R.id.tv_empty_item_list);
                tvEmptyItemList.setVisibility(ContributedItemList.size() == 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return fragmentView;
    }

}
