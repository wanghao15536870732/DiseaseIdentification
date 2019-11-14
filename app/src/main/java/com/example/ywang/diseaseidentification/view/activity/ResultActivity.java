package com.example.ywang.diseaseidentification.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.bean.baseData.ResultData;
import com.example.ywang.diseaseidentification.utils.file.ConstantUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView[] title = new TextView[3];
    private TextView[] content = new TextView[3];
    private TextView[] percent = new TextView[3];
    private ImageView[] imageViews = new ImageView[3];

    private TextView[] disease_title = new TextView[3];
    private TextView[] disease_content = new TextView[3];
    private TextView[] disease_percent = new TextView[3];
    private ImageView[] disease_image = new ImageView[3];

    private List<ResultData> resultData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//设置透明导航栏
        }
        Toolbar toolbar =findViewById(R.id.result_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.result_collapsing_toolbar);
        initView();
        Intent intent = getIntent();
        String resultJson = intent.getStringExtra("result");
        String imagePath = intent.getStringExtra("imagePath");
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        imageView.setImageBitmap(bitmap);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(resultJson);
            JSONArray jsonArray = jsonObject.getJSONArray("result");
            for(int i = 0;i < 3;i ++){
                JSONObject json = jsonArray.getJSONObject(i);
                Double score = json.getDouble("score");
                Log.e("result",score.toString());
                String name = json.getString("name");

                if(i == 0){
                    collapsingToolbar.setTitle(name);
                }
                Log.e("result",name);
                JSONObject baike_info = json.getJSONObject("baike_info");
                String description = baike_info.getString("description");
                String image_url = baike_info.getString("image_url");
                title[i].setText(name);
                content[i].setText(description);
                percent[i].setText(String.valueOf(score));
                Glide.with(this).load(image_url).into(imageViews[i]);
                Log.e("result",description);
            }
            Toast.makeText(this, title[0].getText().toString(), Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(title[0].getText().toString().equals("葡萄")){
            for(int i = 0;i < 3;i ++){
                disease_title[i].setText(resultData.get(i).getName());
                disease_content[i].setText(resultData.get(i).getContent());
                disease_percent[i].setText(resultData.get(i).getPercent());
                Glide.with(this).load(resultData.get(i).getImageUrl()).into(disease_image[i]);
            }
        }else if(title[0].getText().toString().equals("稻")){
            for(int i = 0;i < 3;i ++){
                disease_title[i].setText(resultData.get(i + 3).getName());
                disease_content[i].setText(resultData.get(i + 3).getContent());
                disease_percent[i].setText(resultData.get(i + 3).getPercent());
                Glide.with(this).load(resultData.get(i + 3).getImageUrl()).into(disease_image[i]);
            }
        }else if(title[0].getText().toString().equals("大青")){
            for(int i = 0;i < 3;i ++){
                disease_title[i].setText(resultData.get(i + 6).getName());
                disease_content[i].setText(resultData.get(i + 6).getContent());
                disease_percent[i].setText(resultData.get(i + 6).getPercent());
                Glide.with(this).load(resultData.get(i + 6).getImageUrl()).into(disease_image[i]);
            }
        } else if(title[0].getText().toString().equals("玉蜀黍") || title[0].getText().toString().equals("玉米")){
            for(int i = 0;i < 3;i ++){
                disease_title[i].setText(resultData.get(i + 9).getName());
                disease_content[i].setText(resultData.get(i + 9).getContent());
                disease_percent[i].setText(resultData.get(i + 9).getPercent());
                Glide.with(this).load(resultData.get(i + 9).getImageUrl()).into(disease_image[i]);
            }
        }
        Log.e("result",resultJson);
    }

    private void initView(){
        title[0] = findViewById(R.id.result_title1);
        title[1] = findViewById(R.id.result_title2);
        title[2] = findViewById(R.id.result_title3);
        content[0] = findViewById(R.id.result_content1);
        content[1] = findViewById(R.id.result_content2);
        content[2] = findViewById(R.id.result_content3);
        percent[0] = findViewById(R.id.result_percent1);
        percent[1] = findViewById(R.id.result_percent2);
        percent[2] = findViewById(R.id.result_percent3);


        disease_title[0] = findViewById(R.id.disease_title1);
        disease_title[1] = findViewById(R.id.disease_title2);
        disease_title[2] = findViewById(R.id.disease_title3);
        disease_content[0] = findViewById(R.id.disease_content1);
        disease_content[1] = findViewById(R.id.disease_content2);
        disease_content[2] = findViewById(R.id.disease_content3);
        disease_percent[0] = findViewById(R.id.disease_percent1);
        disease_percent[1] = findViewById(R.id.disease_percent2);
        disease_percent[2] = findViewById(R.id.disease_percent3);
        imageView = (ImageView) findViewById(R.id.background);
        imageViews[0] = findViewById(R.id.result_image1);
        imageViews[1] = findViewById(R.id.result_image2);
        imageViews[2] = findViewById(R.id.result_image3);
        disease_image[0] = findViewById(R.id.disease_image1);
        disease_image[1] = findViewById(R.id.disease_image2);
        disease_image[2] = findViewById(R.id.disease_image3);
        ConstantUtils.getCSV(ResultActivity.this,R.raw.disease);
        List<String[]> list = ConstantUtils.scoreList;
        for(int i = 1;i < list.size();i ++){
            resultData.add(new ResultData(list.get(i)[0],"score",list.get(i)[2],list.get(i)[1],list.get(i)[3]));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
