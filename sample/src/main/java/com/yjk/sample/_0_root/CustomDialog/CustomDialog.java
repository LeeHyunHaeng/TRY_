package com.yjk.sample._0_root.CustomDialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yjk.sample.R;
import com.yjk.sample.databinding.DialogBinding;


public class CustomDialog extends Dialog implements View.OnClickListener {

//    private EditText et_title,et_contents;
//    private Button bt_positive,bt_negative;
    private Context mContext;
    private CustomDialogListener customDialogListener;

    private DialogBinding binding;

    public CustomDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        bt_positive = findViewById(R.id.bt_positive);
//        bt_negative = findViewById(R.id.bt_negative);
//        et_title = findViewById(R.id.et_title);
//        et_contents = findViewById(R.id.et_contents);

        binding.btPositive.setOnClickListener(this);
        binding.btNegative.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (binding.btPositive.equals(view)) {
            String title = binding.etTitle.getText().toString();
            String contents = binding.etContents.getText().toString();

            if (title.trim().equals("") || contents.trim().equals("")) {
                Toast.makeText(mContext, "title과 contents를 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                customDialogListener.onPositiveClicked(title, contents);
            }
            dismiss();
        } else if (binding.btNegative.equals(view)) {
            cancel();
        }

    }

    public interface CustomDialogListener {
        void onPositiveClicked(String title, String contents);
        void onNegativeClicked();
    }

    public void setDialogListener(CustomDialogListener customDialogListener) {
        this.customDialogListener = customDialogListener;
    }
}
