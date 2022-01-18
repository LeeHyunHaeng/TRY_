package com.yjk.sample._1_recyclerview_viewpager2.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yjk.sample.R;
import com.yjk.sample._1_recyclerview_viewpager2.adapter.Camera_Gallery_Fragment_Adapter;
import com.yjk.sample._1_recyclerview_viewpager2.datamodule.Camera_Gallery_List_Data;
import java.util.ArrayList;


public class Activity_Camera_Gallery_Fragment extends Fragment {
    public static final int IMAGE_First = 0;
    public static final int IMAGE_Second = 1;
    private int viewType = 0 ;

    private ArrayList<Camera_Gallery_List_Data> mList;
    private Camera_Gallery_Fragment_Adapter adapter;
    private RecyclerView recyclerView;
    private Camera_Gallery_Fragment_Adapter.FirstCallback mCallback;

    private Context mContext;
    private String TAG = Activity_Camera_Gallery_Fragment.class.toString();

    public Activity_Camera_Gallery_Fragment(int type, Camera_Gallery_Fragment_Adapter.FirstCallback callback) {
        this.viewType = type;
        this.mCallback = callback;
        this.adapter = new Camera_Gallery_Fragment_Adapter(viewType,mContext, new Camera_Gallery_Fragment_Adapter.FirstCallback() {

            @Override
            public void onSend(Camera_Gallery_List_Data data) {
                mCallback.onSend(data);
            }
            @Override
            public void onDelete(Camera_Gallery_List_Data data) {
                mCallback.onDelete(data);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_camera_fragment, container, false);
        initView(view);
        return view;
    }


    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_recyclerview);
        if (viewType == IMAGE_First){
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false));
        }else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        }

        recyclerView.setAdapter(adapter);
    }


    public void addItem(Camera_Gallery_List_Data data) {
        adapter.addItem(data);
    }
    public void deleteItem(Camera_Gallery_List_Data data){
        adapter.deleteItem(data);
    }
}
