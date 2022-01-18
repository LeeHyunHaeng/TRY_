package com.yjk.sample._1_recyclerview_viewpager2;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.yjk.common.view.base.BaseActivity;
import com.yjk.sample.R;
import com.yjk.sample._1_recyclerview_viewpager2.adapter.Camera_Gallery_Main_Adapter;
import com.yjk.sample._1_recyclerview_viewpager2.datamodule.Camera_Gallery_List_Data;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/*      마지막 과제 진행
*       내장 카메라와 앨범 기능을 이용하며 Viewpager2,Recyclerview를 주기능으로 이용한다.
*                       이현행
* * */
public class Activity_Camera_Gallery_main extends BaseActivity   {
    final static String TAG = Activity_Camera_Gallery_main.class.toString();

    private RelativeLayout re_dialog;
    private TextView camera, gallery,list_button;
    private ImageView list,star;
    private ViewPager2 mViewpager2;
    private Camera_Gallery_Main_Adapter mAdapter;
    public String imageFilePath;
    private Bitmap bit;
    private File tempFile;
    private ArrayList<Camera_Gallery_List_Data> aList;
    Dialog aDialog;
    private ActivityResultLauncher<Intent> launcher1,launcher2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_main);
        initView();
        setEvent();

        //takePhoto와 연동
        launcher1 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                    File file = new File(imageFilePath);
//                    버전 분기처리
                        if(Build.VERSION.SDK_INT >= 29) {
                            ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), Uri.fromFile(file));
                            try {
                                bit = ImageDecoder.decodeBitmap(source);
                                String str = new SimpleDateFormat("yyyy/MM/dd  -  HH:mm:ss").format(Calendar.getInstance().getTime());
                                addItem(bit,str);
                                }catch (IOException ex) {
                                ex.printStackTrace();
                                }
                        } else {
                            try {
                                bit = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                                String str = new SimpleDateFormat("yyyy/MM/dd  -  HH:mm:ss").format(Calendar.getInstance().getTime());
                                if (bit != null){
                                   addItem(bit,str);
                               }
                            }catch (IOException e) {
                                 e.printStackTrace();
                            }
                        }
            }
        });

        //getPicture과 연동
        launcher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                try {
                    Intent i = result.getData();
                    Uri uri = i.getData();

                    //버전 분기처리
                    if (Build.VERSION.SDK_INT >= 29) {
                        bit = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), uri));
                        String str = new SimpleDateFormat("yyyy/MM/dd  -  HH:mm:ss").format(Calendar.getInstance().getTime());
                        addItem(bit, str);
                    } else {
                        bit = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        String str = new SimpleDateFormat("yyyy/MM/dd  -  HH:mm:ss").format(Calendar.getInstance().getTime());
                        addItem(bit,str);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void initView() {
        bit = null;
        re_dialog = findViewById(R.id.relative_dialog);
        list = findViewById(R.id.image_list);
        star = findViewById(R.id.image_star);
        mViewpager2 = findViewById(R.id.main_viewpager2);
        mAdapter = new Camera_Gallery_Main_Adapter(this);

        mViewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                list.setSelected(position == 0);
                star.setSelected(position == 1);
            }
        });

        //Viewpager2 설정
        mViewpager2.setAdapter(mAdapter);
        mViewpager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mViewpager2.setOffscreenPageLimit(1);

        //카메라권한설정 및 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 요청");
                ActivityCompat.requestPermissions(Activity_Camera_Gallery_main.this, new String[]{Manifest.permission
                        .CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    //카메라권한설정 및 요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }

    @Override
    protected void setEvent() {
        //Dialog 생성
        re_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                shows();
            }
        });

        list.setOnClickListener(view -> mViewpager2.setCurrentItem(0));
        star.setOnClickListener(view -> mViewpager2.setCurrentItem(1));

    }

    //다이얼로그를 통한 takePicture(),getPicture() 메서드 호출
    public void shows() {
        aDialog = new Dialog(Activity_Camera_Gallery_main.this);
        aDialog.setContentView(R.layout.activity_camera_dialog);
        aDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        aDialog.show();

        RelativeLayout camera = aDialog.findViewById(R.id.relative_camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
                aDialog.dismiss();
            }
        });

        RelativeLayout gallery = aDialog.findViewById(R.id.relative_gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPicture();
                aDialog.dismiss();
            }
        });

        Button cancel = aDialog.findViewById(R.id.bt_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aDialog.dismiss();
            }
        });
    }

//    앨범 기능 구현
        public void getPicture () {
            Intent i = new Intent(Intent.ACTION_PICK);
            i.setType(MediaStore.Images.Media.CONTENT_TYPE);
            launcher2.launch(i);
    }

//    카메라 기능 구현
    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null){
            File tempFile = null;

            try {
                tempFile = createImageFile();
            }catch (IOException ex) {
                Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                 ex.printStackTrace();
            }

            if (tempFile != null) {
                Uri photoUri = FileProvider.getUriForFile(Activity_Camera_Gallery_main.this, "com.yjk.sample.filesprovider",tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                launcher1.launch(intent);
            }
        }
    }


    //카메라 촬영후 이미지가 저장될 파일을 생성하는 메서드
    public File createImageFile() throws IOException{
        // 이미지 파일 이름
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Test_" + timeStamp + "_";

        // 이미지가 저장될 폴더 이름
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File Image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageFilePath = Image.getAbsolutePath();
        return Image;
    }

    public void addItem(Bitmap bitmap, String str) {
        Camera_Gallery_List_Data data = new Camera_Gallery_List_Data(bitmap,str);
        mAdapter.addData(data);
    }
}
