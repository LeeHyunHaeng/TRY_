package com.yjk.sample._1_recyclerview_viewpager2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yjk.sample.R;
import com.yjk.sample._1_recyclerview_viewpager2.datamodule.Camera_Gallery_List_Data;
import com.yjk.sample._1_recyclerview_viewpager2.fragment.Activity_Camera_Gallery_Fragment;

import java.util.ArrayList;

public class Camera_Gallery_Fragment_Adapter extends RecyclerView.Adapter<Camera_Gallery_Fragment_Adapter.MyViewHolder> {
    private int viewType = Activity_Camera_Gallery_Fragment.IMAGE_First;
    private ArrayList<Camera_Gallery_List_Data> mList;
    private Context mContext;
    private String TAG = Camera_Gallery_Fragment_Adapter.class.toString();
    private FirstCallback callback;

    public Camera_Gallery_Fragment_Adapter(int viewType,Context context,FirstCallback call) {
        this.viewType = viewType;
        this.mContext = context;
        this.callback = call;
        this.mList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater i = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = i.inflate(R.layout.activity_camera_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Camera_Gallery_List_Data item = mList.get(position);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.imageView.getLayoutParams();
        if (viewType == Activity_Camera_Gallery_Fragment.IMAGE_First){
            params.height = 300;
        }else {
            params.height = 250;
        }
        holder.imageView.setLayoutParams(params);
        holder.imageView.setImageBitmap(item.getBitmap());
        holder.textView.setText(item.getString());
        holder.relativeLayout.setVisibility(viewType == Activity_Camera_Gallery_Fragment.IMAGE_First ? View.VISIBLE : View.GONE);

    }

    public void addItem(Camera_Gallery_List_Data data) {
        if (viewType == Activity_Camera_Gallery_Fragment.IMAGE_First){
            mList.add(data);
            notifyItemInserted(mList.size());
        }else {
            if (data.isLike()){
                mList.add(data);
                notifyItemInserted(mList.size());
            }else {
                deleteItem(data);
            }

        }
    }
    public void deleteItem(Camera_Gallery_List_Data data){
        int position = mList.indexOf(data);
        if (position >= 0 ){
            mList.remove(position);
            notifyItemRemoved(position);
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private RelativeLayout relativeLayout,sendImage;
        private TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_imageview);
            relativeLayout = itemView.findViewById(R.id.re_daytimebar);
            textView = itemView.findViewById(R.id.tv_daytime);
            sendImage = itemView.findViewById(R.id.re_star);

            sendImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Camera_Gallery_List_Data data = mList.get(getAbsoluteAdapterPosition());
                    data.setLike(!data.isLike());
                    callback.onSend(data);
                    notifyItemChanged(getAbsoluteAdapterPosition());
                }
            });

            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Camera_Gallery_List_Data data = mList.get(getAbsoluteAdapterPosition());
                    if (viewType == Activity_Camera_Gallery_Fragment.IMAGE_First){
                        Toast.makeText(imageView.getContext(), "사진이 삭제되었습니다!",Toast.LENGTH_SHORT).show();
                        callback.onDelete(data);
                    }
                    return true;
                }
            });
        }
    }

    public interface FirstCallback{
        void onSend(Camera_Gallery_List_Data data);
        void onDelete(Camera_Gallery_List_Data data);
    }
}
