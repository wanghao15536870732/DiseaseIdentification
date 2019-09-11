package com.example.ywang.diseaseidentification.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ywang.diseaseidentification.R;

import java.util.ArrayList;

public class NewsFragment extends Fragment{

    private RecyclerView recyclerView;
    private ArrayList<String> nameList = new ArrayList<>();
    private MyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        for(int i = 0;i < 30;i ++){
            nameList.add("name "+i);
        }
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
        return view;
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.list_item,parent,false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            //set item's value
            holder.nameText.setText(nameList.get(position));
        }

        @Override
        public int getItemCount() {
            return nameList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView nameText;
            public MyViewHolder(View itemView) {
                super(itemView);
                //get到相关控件的引用
                nameText= (TextView) itemView.findViewById(R.id.name_text);
            }
        }
    }
}
