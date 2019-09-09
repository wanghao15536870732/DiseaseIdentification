package com.example.ywang.diseaseidentification.view.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.utils.CircularAnimUtils;

public class GuideActivity extends AppCompatActivity {

    private Button signUpBtn,signInBtn,skipBtn;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        signInBtn = (Button) findViewById(R.id.btn_sign_in);
        signUpBtn = (Button) findViewById(R.id.btn_sign_up);
        skipBtn = (Button) findViewById(R.id.btn_skip);

        /*登录*/
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        /*注册*/
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CircularAnimUtils.hide(signInBtn);
                //finish();
                CircularAnimUtils.startActivity(GuideActivity.this,RegisterActivity.class,signInBtn,R.color.colorAccent);
            }
        });
        /*跳过*/
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CircularAnimUtils.hide(skipBtn);
                //finish();
                CircularAnimUtils.startActivity(GuideActivity.this,MainActivity.class,signInBtn,R.color.colorPrimary);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.white));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }
}
