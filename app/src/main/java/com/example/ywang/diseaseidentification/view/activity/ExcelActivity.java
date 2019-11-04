package com.example.ywang.diseaseidentification.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.ywang.diseaseidentification.R;
import java.util.ArrayList;
import java.util.List;

public class ExcelActivity extends AppCompatActivity {
    private List<String[]> cropList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excel);
    }
}
