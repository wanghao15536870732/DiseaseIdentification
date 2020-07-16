package com.example.ywang.diseaseidentification.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.adapter.disease.CropAdapter;
import com.example.ywang.diseaseidentification.bean.baseData.DiseaseData;
import java.util.ArrayList;
import java.util.List;

public class CropFragment extends Fragment {

    private List<DiseaseData> mList = new ArrayList<>();
    private int spanCount;

    public static CropFragment newInstance(List<DiseaseData> list,int spanCount){
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
        CropFragment fragment = new CropFragment(spanCount);
        fragment.setArguments(bundle);
        return fragment;
    }

    @SuppressLint("ValidFragment")
    public CropFragment(int spanCount){
        this.spanCount = spanCount;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_crop,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.crop_list);
        Bundle bundle = getArguments();
        if (bundle != null){
            mList = bundle.getParcelableArrayList("list");
        }
        //网格式布局，产生3列数据
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),spanCount);
        //让recyclerView的布局采用网格式布局
        recyclerView.setLayoutManager(layoutManager);
        CropAdapter adapter = new CropAdapter(mList);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
