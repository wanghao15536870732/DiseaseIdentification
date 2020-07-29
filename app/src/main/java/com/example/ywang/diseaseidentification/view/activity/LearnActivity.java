package com.example.ywang.diseaseidentification.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.adapter.disease.LeftAdapter;
import com.example.ywang.diseaseidentification.adapter.disease.MultiAdapter;
import com.example.ywang.diseaseidentification.adapter.disease.RightAdapter;
import com.example.ywang.diseaseidentification.bean.AgriPosition;
import com.example.ywang.diseaseidentification.bean.AgriProduct;
import com.example.ywang.diseaseidentification.utils.SnackBarUtil;
import com.example.ywang.diseaseidentification.utils.file.ConstantUtils;
import com.example.ywang.diseaseidentification.utils.network.HttpUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LearnActivity extends AppCompatActivity {

    private static String address = "http://wnd.agri114.cn/wndms/json/findDiseaseFeatures.action";
    private final List<AgriProduct> productList = new ArrayList<>();
    private List<AgriPosition> positionList = new ArrayList<>();
    private final List<AgriPosition.DiseaseFeatureListBean> beanList = new ArrayList<>();
    private List<AgriPosition.DiseaseFeatureListBean> selectData = new ArrayList<>();
    private ProgressDialog dialog;

    private RecyclerView mLeftRvRecyclerView; // 左列表
    private RecyclerView mRightRvRecyclerView; // 右上列表
    private RecyclerView mMutiRecyclerView; // 右下列表

    private LeftAdapter leftAdapter;
    private RightAdapter rightAdapter;

    private MultiAdapter mMultiAdapter;
    private TextView submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        mLeftRvRecyclerView = findViewById(R.id.main_left_rv);
        mRightRvRecyclerView = findViewById(R.id.main_right_rv);
        mMutiRecyclerView = findViewById(R.id.main_multi_check);
        parseDiseaseData();

        mMutiRecyclerView.setHasFixedSize(true);
        mMutiRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMultiAdapter = new MultiAdapter(beanList);
        mMutiRecyclerView.setAdapter(mMultiAdapter);

        leftAdapter = new LeftAdapter(productList);
        mLeftRvRecyclerView.setAdapter(leftAdapter);
        mLeftRvRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        rightAdapter = new RightAdapter(positionList);
        mRightRvRecyclerView.setAdapter(rightAdapter);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        mRightRvRecyclerView.setLayoutManager(manager);
        initRecyclerView();

        //最终的多选条目
        mMultiAdapter.setOnItemClickListener(new MultiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (!mMultiAdapter.isSelected.get(position)) {
                    mMultiAdapter.isSelected.put(position, true); // 修改map的值保存状态
                    mMultiAdapter.notifyItemChanged(position);
                    selectData.add(beanList.get(position));
                } else {
                    mMultiAdapter.isSelected.put(position, false); // 修改map的值保存状态
                    mMultiAdapter.notifyItemChanged(position);
                    selectData.remove(beanList.get(position));
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String allFeatures = getAllFeatures(selectData);
                int position = leftAdapter.getSelectPos() + 1;
                if(selectData.size() > 0){
                    Intent featureIntent = new Intent(LearnActivity.this,SelfCheckResultActivity.class);
                    featureIntent.putExtra("diseaseFeatures",allFeatures);
                    featureIntent.putExtra("agriProductId",position);
                    featureIntent.putExtra("agriName",getAgriName(productList.get(position - 1).getImg()));
                    startActivity(featureIntent);
                }else {
                    SnackBarUtil.showSnackBar("还未进行任何选择",mMutiRecyclerView,LearnActivity.this);
                }
            }
        });
        HttpUtil.sendOkHttpRequest(address + "?agriProductId=0", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                positionList.addAll(parsePositionJson(response.body().string()));
                beanList.addAll(positionList.get(0).getDiseaseFeatureList());
                leftAdapter.setSelectPos(0);
                rightAdapter.setSelectPos(0);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        leftAdapter.notifyDataSetChanged();
                        mMultiAdapter.init();
                        rightAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private String getAgriName(String imgUrl){
        String[] contents = imgUrl.split("/");
        String last = contents[contents.length - 1];
        return last.replace(".jpg","");
    }

    private void initRecyclerView() {
        mLeftRvRecyclerView.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, final int i) {
                AgriProduct agriBean = productList.get(i);
                positionList.clear();
                HttpUtil.sendOkHttpRequest(address + "?agriProductId=" + agriBean.getId(), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        positionList.addAll(parsePositionJson(response.body().string()));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                leftAdapter.setSelectPos(i);
                                rightAdapter.setSelectPos(0);
                                leftAdapter.notifyDataSetChanged();
                                rightAdapter.notifyDataSetChanged();
                                if (!positionList.isEmpty()) {
                                    AgriPosition positionBean = positionList.get(0);
                                    beanList.clear();
                                    beanList.addAll(positionBean.getDiseaseFeatureList());
                                }
                                mMultiAdapter.init();
                                mMultiAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }

            @Override
            public void onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

            }
        });

        mRightRvRecyclerView.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                AgriPosition positionBean = positionList.get(i);
                rightAdapter.setSelectPos(i);
                beanList.clear();
                beanList.addAll(positionBean.getDiseaseFeatureList());
                mMultiAdapter.init();
                mMultiAdapter.notifyDataSetChanged();
                rightAdapter.notifyDataSetChanged();
                leftAdapter.notifyDataSetChanged();
            }

            @Override
            public void onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

            }
        });
    }

    private void parseDiseaseData() {
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(LearnActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                new dataAsync().execute(response.body().string());
            }
        });
    }

    class dataAsync extends AsyncTask<String, String, List<AgriProduct>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog = ProgressDialog.show(LearnActivity.this, "稍等", "数据加載中");
                }
            });
        }

        @Override
        protected List<AgriProduct> doInBackground(String... strings) {
            final List<AgriProduct> productList = new ArrayList<>();
            try {
                JSONObject responseData = new JSONObject(strings[0]);
                JSONArray productArray = responseData.getJSONArray("agriProductList");
                for (int i = 0; i < productArray.length(); i++) {
                    JSONObject productObject = productArray.getJSONObject(i);
                    final AgriProduct product = new AgriProduct();
                    product.setImg(productObject.getString("img"));
                    product.setName(productObject.getString("name"));
                    product.setId(productObject.getInt("id"));
                    product.setCategory(productObject.getString("category"));

                    productList.add(product);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return productList;
        }

        @Override
        protected void onPostExecute(List<AgriProduct> agriProducts) {
            super.onPostExecute(agriProducts);
            productList.clear();
            productList.addAll(agriProducts);
            leftAdapter.setSelectPos(0);
            leftAdapter.notifyDataSetChanged();
            dialog.dismiss();
        }
    }

    private List<AgriPosition> parsePositionJson(String responseStr) {
        List<AgriPosition> list = new ArrayList<>();
        try {
            JSONObject responseData = new JSONObject(responseStr);
            JSONArray positionArray = responseData.getJSONArray("diseasePositionList");
            for (int j = 0; j < positionArray.length(); j++) {
                JSONObject jsonObject = positionArray.getJSONObject(j);
                AgriPosition agriPosition = new AgriPosition();
                agriPosition.setId(jsonObject.getInt("id"));
                agriPosition.setName(jsonObject.getString("name"));
                agriPosition.setAgriProductId(jsonObject.getInt("agriProductId"));
                JSONArray diseaseArray = jsonObject.getJSONArray("diseaseFeatureList");
                List<AgriPosition.DiseaseFeatureListBean> mList = new ArrayList<>();
                for (int k = 0; k < diseaseArray.length(); k++) {
                    JSONObject diseaseObject = diseaseArray.getJSONObject(k);
                    AgriPosition.DiseaseFeatureListBean bean = new AgriPosition.DiseaseFeatureListBean();
                    bean.setId(diseaseObject.getInt("id"));
                    bean.setAgriProductId(diseaseObject.getInt("agriProductId"));
                    bean.setDiseasePositonId(diseaseObject.getInt("diseasePositonId"));
                    bean.setName(diseaseObject.getString("name"));
                    mList.add(bean);
                }
                agriPosition.setDiseaseFeatureList(mList);
                list.add(agriPosition);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private String getAllFeatures(List<AgriPosition.DiseaseFeatureListBean> selectData) {
        StringBuilder features = new StringBuilder();
        for (int i = 0; i < selectData.size(); i++) {
            features.append("|");
            features.append(selectData.get(i).getId());
            features.append("|");
        }
        return features.toString();
    }
}
