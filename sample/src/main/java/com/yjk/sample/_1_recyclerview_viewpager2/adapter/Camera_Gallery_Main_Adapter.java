package com.yjk.sample._1_recyclerview_viewpager2.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import com.yjk.sample._1_recyclerview_viewpager2.datamodule.Camera_Gallery_List_Data;
import com.yjk.sample._1_recyclerview_viewpager2.fragment.Activity_Camera_Gallery_Fragment;

import java.util.ArrayList;

public class Camera_Gallery_Main_Adapter extends FragmentStateAdapter {
    private String TAG = Camera_Gallery_Main_Adapter.class.toString();
    private ArrayList<Activity_Camera_Gallery_Fragment> fList;



    public Camera_Gallery_Main_Adapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.fList = new ArrayList<>();

        Camera_Gallery_Fragment_Adapter.FirstCallback callback = new Camera_Gallery_Fragment_Adapter.FirstCallback() {
            @Override
            public void onSend(Camera_Gallery_List_Data data) {
                fList.get(1).addItem(data);
            }
            @Override
            public void onDelete(Camera_Gallery_List_Data data) {
                for(Activity_Camera_Gallery_Fragment fragment : fList){
                    fragment.deleteItem(data);
                }
            }
        };

        fList.add(new Activity_Camera_Gallery_Fragment(Activity_Camera_Gallery_Fragment.IMAGE_First,callback));
        fList.add(new Activity_Camera_Gallery_Fragment(Activity_Camera_Gallery_Fragment.IMAGE_Second,callback));
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fList.get(position);
    }

    @Override
    public int getItemCount() {
        return fList.size();
    }

    public void addData(Camera_Gallery_List_Data data){
        for (Activity_Camera_Gallery_Fragment fragment : fList){
            fragment.addItem(data);
        }
    }

}
