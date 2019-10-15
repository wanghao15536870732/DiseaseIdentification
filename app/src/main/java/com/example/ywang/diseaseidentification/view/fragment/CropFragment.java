package com.example.ywang.diseaseidentification.view.fragment;

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
import com.example.ywang.diseaseidentification.adapter.DiseasesAdapter2;
import com.example.ywang.diseaseidentification.bean.baseData.DiseaseData;
import java.util.ArrayList;
import java.util.List;

public class CropFragment extends Fragment {

    private RecyclerView recyclerView;
    private DiseasesAdapter2 adapter;
    private List<DiseaseData> mList = new ArrayList<>();

    public static CropFragment newInstance(List<DiseaseData> list){
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) list);
        CropFragment fragment = new CropFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_crop,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.crop_list);
        Bundle bundle = getArguments();
        if (bundle != null){
            mList = bundle.getParcelableArrayList("list");
        }
        //网格式布局，产生2列数据
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        //让recyclerView的布局采用网格式布局
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DiseasesAdapter2(mList);
        recyclerView.setAdapter(adapter);
        return view;
    }

}
