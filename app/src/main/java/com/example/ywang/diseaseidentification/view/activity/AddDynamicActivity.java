package com.example.ywang.diseaseidentification.view.activity;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.donkingliang.labels.LabelsView;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.adapter.FullyGridLayoutManager;
import com.example.ywang.diseaseidentification.adapter.GridImageAdapter;
import com.example.ywang.diseaseidentification.bean.baseData.DynamicBean;
import com.example.ywang.diseaseidentification.bean.baseData.TabBean;
import com.example.ywang.diseaseidentification.utils.UpLoadFileTask;
import com.example.ywang.diseaseidentification.utils.UpToServlet;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddDynamicActivity extends AppCompatActivity implements View.OnClickListener{

    private final static String TAG = "DynamicActivity";
    private RecyclerView recyclerView;
    private GridImageAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private int maxSelectNum = 9;
    private Button send_btn;
    private ImageView back_Btn;
    private EditText mEditText;
    private LabelsView labelsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dynamic);
        recyclerView = (RecyclerView) findViewById( R.id.dynamic_recycler );
        FullyGridLayoutManager manager = new FullyGridLayoutManager(
                AddDynamicActivity.this,
                3,
                GridLayoutManager.VERTICAL,
                false
        );
        recyclerView.setLayoutManager( manager );
        adapter = new GridImageAdapter( AddDynamicActivity.this, onAddPicClickListener );
        adapter.setList( selectList );
        recyclerView.setAdapter( adapter);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();

                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            int themeId = R.style.picture_default_style;
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(AddDynamicActivity.this)
                                    .themeStyle(themeId)
                                    .maxSelectNum( maxSelectNum )
                                    .openExternalPreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(AddDynamicActivity.this)
                                    .externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(AddDynamicActivity.this)
                                    .externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
        send_btn = (Button) findViewById( R.id.toolbar_send );
        back_Btn = (ImageView) findViewById( R.id.toolbar_back );
        mEditText = (EditText) findViewById( R.id.item_edit );
        send_btn.setOnClickListener( this );
        back_Btn.setOnClickListener( this );

        labelsView = (LabelsView) findViewById(R.id.labels);
        ArrayList<String> label = new ArrayList<>();
        label.add("农业资讯");
        label.add("病虫防治");
        label.add("疾病普及");
        label.add("农耕作业");
        label.add("耕种妙招");
        label.add("生活点滴");
        labelsView.setLabels(label); //直接设置一个字符串数组就可以了。

        //LabelsView可以设置任何类型的数据，而不仅仅是String。
        ArrayList<TabBean> testList = new ArrayList<>();
        testList.add(new TabBean("农业资讯",1));
        testList.add(new TabBean("病虫防治",2));
        testList.add(new TabBean("疾病普及",3));
        testList.add(new TabBean("农耕作业",4));
        testList.add(new TabBean("耕种妙招",5));
        testList.add(new TabBean("生活点滴",6));
        labelsView.setLabels(testList, new LabelsView.LabelTextProvider<TabBean>() {
            @Override
            public CharSequence getLabelText(TextView label, int position, TabBean data) {
                //根据data和position返回label需要显示的数据。
                return data.getName();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toolbar_send:
                for (int i = 0;i < selectList.size();i ++){
                    LocalMedia localMedia = selectList.get(i);
                    String path = localMedia.getPath();
                    String content = mEditText.getText().toString().trim();
                    String type = "农业资讯";
                    Random rand = new Random();
                    String num = String.valueOf((int) rand.nextInt(1000) + 1);
                    String avatar = "农民" + num;
                    DynamicBean bean = new DynamicBean(avatar,content,getTime(),type,new ArrayList<String>(),selectList.size());
                    UpLoadFileTask task = new UpLoadFileTask(AddDynamicActivity.this,bean);
                    task.execute(path);
                }
                if(UpToServlet.SUCCESS){
                    Toast.makeText(this, "上传成功！", Toast.LENGTH_SHORT).show();
                    UpToServlet.SUCCESS = false;
                }else {
                    Toast.makeText(this, "上传成功！", Toast.LENGTH_SHORT).show();
                    UpToServlet.SUCCESS = false;
                }
                this.finish();
                break;
            case R.id.toolbar_back:
                finish();
                break;
            default:
                break;
        }
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            // 单独拍照
            PictureSelector.create(AddDynamicActivity.this)
                    .openGallery(PictureMimeType.ofAll())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .maxSelectNum(maxSelectNum)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(3)// 每行显示个数
                    .selectionMode(
                            PictureConfig.MULTIPLE)// 多选 or 单选
                    .previewImage(true)// 是否可预览图片
                    .previewVideo(true)// 是否可预览视频
                    .enablePreviewAudio(true) // 是否可播放音频
                    .isCamera(true)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .enableCrop(false)// 是否裁剪
                    .compress(true)// 是否压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    .compressSavePath(getPath())//压缩图片保存地址
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .withAspectRatio(0, 0)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
                    .isGif(true)// 是否显示gif图片
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    .circleDimmedLayer(false)// 是否圆形裁剪
                    .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                    .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    .openClickSound(true)// 是否开启点击声音
                    .selectionMedia(selectList)// 是否传入已选图片
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getPath());
                    }
                    adapter.setList(selectList);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    /*压缩图片存放的地址*/
    public String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    private String getTime(){
        Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
        t.setToNow(); // 取得系统时间
        int year = t.year;
        int month = t.month+1;
        int day = t.monthDay;
        int hour = t.hour; // 0-23
        int min = t.minute;
        return year + "." + month + "." + day + "-" + hour + ":" + min;
    }
}
