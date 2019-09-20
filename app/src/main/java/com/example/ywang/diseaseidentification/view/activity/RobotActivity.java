package com.example.ywang.diseaseidentification.view.activity;
import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.adapter.ChatListAdapter;
import com.example.ywang.diseaseidentification.bean.baseData.ChatListData;
import com.example.ywang.diseaseidentification.bean.baseEnum.DictationResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iflytek.aiui.AIUIAgent;
import com.iflytek.aiui.AIUIConstant;
import com.iflytek.aiui.AIUIEvent;
import com.iflytek.aiui.AIUIListener;
import com.iflytek.aiui.AIUIMessage;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RobotActivity extends AppCompatActivity implements View.OnClickListener{

    private ListView mChatListView;
    private EditText et_chat_text;
    private Button btn_sent;
    //数据源
    private List<ChatListData> mList = new ArrayList<>();
    private ChatListAdapter adapter;
    //语音助手
    private AIUIAgent mAIUIAgent;
    private int mAIUIState = AIUIConstant.STATE_IDLE;
    private Toolbar toolbar;
    private ImageView voiceBtn,keyboardBtn;
    private TextView voiceTxt;
    //有动画效果
    private RecognizerDialog iatDialog;
    private Button btnRecognizerDialog; //带窗口的语音识别

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robot);
        mChatListView = findViewById(R.id.mChatListView);
        //输入框
        et_chat_text = findViewById(R.id.et_chat_text);
        btn_sent = findViewById(R.id.btn_sent);
        voiceBtn = findViewById(R.id.input_voice);
        keyboardBtn = findViewById(R.id.input_keyboard);
        voiceTxt = findViewById(R.id.et_chat_voice);
        btn_sent.setOnClickListener(this);
        voiceBtn.setOnClickListener(this);
        keyboardBtn.setOnClickListener(this);
        voiceTxt.setOnClickListener(this);

        //设置设配器
        adapter = new ChatListAdapter(RobotActivity.this, mList);
        mChatListView.setAdapter(adapter);
        //去掉分割线
        mChatListView.setDividerHeight(0);
        toolbar = (Toolbar) findViewById(R.id.toolbar_web);
        addLeftItem("我是小农！您的科普小助手！");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("农种小助手");


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            Window window = getWindow();
//            window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(getResources().getColor(android.R.color.white));
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }
    }


    //添加左边的文本
    private void addLeftItem(String text) {
        ChatListData data = new ChatListData();
        data.setType(ChatListAdapter.VALUE_LEFT_TEXT);
        data.setText(text);
        mList.add(data);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());
    }

    //添加右边的文本
    private void addRightItem(String text) {
        ChatListData data = new ChatListData();
        data.setType(ChatListAdapter.VALUE_RIGHT_TEXT);
        data.setText(text);
        mList.add(data);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sent:
                //1.获取输入框内容
                String text = et_chat_text.getText().toString();
                if(!TextUtils.isEmpty(text)){
                    //3.判断是否在1-128个字节之间
                    if(text.length() > 128 || text.length() < 1 ){
                        Toast.makeText(RobotActivity.this,"输入字符长度应在1-128之间！",Toast.LENGTH_SHORT).show();
                    }else{
                        //4.清空输入框
                        et_chat_text.setText("");
                        //5.添加你输入的内容到right item
                        addRightItem(text);

                        if(checkAIUIAgent()){
                            startVoiceNlp( text );
                        }else {
                            Log.e( "AIUI","创建AIUI失败" );
                        }
                    }
                }else{
                    Toast.makeText(this,"输入框不能为空！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.input_voice:
                voiceBtn.setVisibility(View.GONE);
                keyboardBtn.setVisibility(View.VISIBLE);
                et_chat_text.setVisibility(View.GONE);
                voiceTxt.setVisibility(View.VISIBLE);
                hideInput();
                break;
            case R.id.input_keyboard:
                keyboardBtn.setVisibility(View.GONE);
                voiceBtn.setVisibility(View.VISIBLE);
                voiceTxt.setVisibility(View.GONE);
                et_chat_text.setVisibility(View.VISIBLE);
                showInput(et_chat_text);
                break;
            case R.id.et_chat_voice:
                voice_text();
                break;
            default:
                break;

        }
    }

    /**
     * 读取配置
     */
    private String getAIUIParams() {
        String params = "";
        AssetManager assetManager = getResources().getAssets();
        try {
            InputStream ins = assetManager.open( "cfg/aiui_phone.cfg" );
            byte[] buffer = new byte[ins.available()];
            ins.read(buffer);
            ins.close();
            params = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return params;
    }

    private boolean checkAIUIAgent(){
        if( null == mAIUIAgent ){
            Log.i( "RobotActivity", "create aiui agent" );

            //创建AIUIAgent
            mAIUIAgent = AIUIAgent.createAgent( this, getAIUIParams(), mAIUIListener );
        }

        if( null == mAIUIAgent ){
            final String strErrorTip = "创建 AIUI Agent 失败！";
            Toast.makeText( this, strErrorTip, Toast.LENGTH_SHORT ).show();
        }
        return null != mAIUIAgent;
    }

    //开始录音
    private void startVoiceNlp(String result){
        Log.i( "RobotActivity", "start voice nlp" );

        // 先发送唤醒消息，改变AIUI内部状态，只有唤醒状态才能接收语音输入
        // 默认为oneshot 模式，即一次唤醒后就进入休眠，如果语音唤醒后，需要进行文本语义，请将改段逻辑copy至startTextNlp()开头处
        if( AIUIConstant.STATE_WORKING != 	this.mAIUIState ){
            AIUIMessage wakeupMsg = new AIUIMessage(AIUIConstant.CMD_WAKEUP, 0, 0, "", null);
            mAIUIAgent.sendMessage(wakeupMsg);
        }

        // 打开AIUI内部录音机，开始录音
        String params = "data_type=text";
        byte[] textData = result.getBytes();
        AIUIMessage writeMsg = new AIUIMessage( AIUIConstant.CMD_WRITE, 0, 0, params, textData );
        mAIUIAgent.sendMessage(writeMsg);
    }

    //AIUI事件监听器
    private AIUIListener mAIUIListener = new AIUIListener() {

        @Override
        public void onEvent(AIUIEvent event) {
            switch (event.eventType) {
                case AIUIConstant.EVENT_WAKEUP:
                    //唤醒事件
                    Log.i( "RobotActivity",  "on event: "+ event.eventType );
                    break;

                case AIUIConstant.EVENT_RESULT: {
                    //结果事件
                    Log.i( "RobotActivity",  "on event: "+ event.eventType );
                    // 解析得到语义结果
                    String str = "";
                    try {
                        Log.e("RobotActivity",event.info);
                        JSONObject bizParamJson = new JSONObject(event.info);
                        JSONObject data = bizParamJson.getJSONArray("data").getJSONObject(0);
                        JSONObject params = data.getJSONObject("params");
                        JSONObject content = data.getJSONArray("content").getJSONObject(0);
                        if (content.has("cnt_id")) {
                            String cnt_id = content.getString("cnt_id");
                            JSONObject cntJson = new JSONObject(new String(event.data.getByteArray(cnt_id), "utf-8"));
                            String sub = params.optString("sub");
                            JSONObject result = cntJson.optJSONObject("intent");
                            if ("nlp".equals(sub) && result.length() > 2) {
                                //在线语义结果
                                if(result.optInt("rc") == 0){
                                    JSONObject answer = result.optJSONObject("answer");
                                    if(answer != null){
                                        str = answer.optString("text");
                                    }
                                    if (!TextUtils.isEmpty(str)) {
                                        addLeftItem(str);
                                    }
                                    //音频解析
                                    JSONObject resultJson = result.optJSONObject( "data").getJSONArray( "result" ).getJSONObject( 0 );
                                    Log.e( "resultJson",resultJson.toString());
                                }else{
                                    str = "rc4，无法识别";
                                }
                            }
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                } break;

                case AIUIConstant.EVENT_ERROR: {
                    //错误事件
                    Log.i( "RobotActivity",  "on event: "+ event.eventType  );
                    Toast.makeText(RobotActivity.this, event.arg1+"\n"+event.info, Toast.LENGTH_SHORT ).show();
                } break;

                case AIUIConstant.EVENT_VAD: {
                    //vad事件
                    if (AIUIConstant.VAD_BOS == event.arg1) {
                        //找到语音前端点
                        Toast.makeText( RobotActivity.this, "找到vad_bos", Toast.LENGTH_SHORT ).show();
                    } else if (AIUIConstant.VAD_EOS == event.arg1) {
                        //找到语音后端点
                        Toast.makeText( RobotActivity.this, "找到vad_eos", Toast.LENGTH_SHORT ).show();
                    } else {
                        Toast.makeText( RobotActivity.this, event.arg2, Toast.LENGTH_SHORT ).show();
                    }
                } break;

                case AIUIConstant.EVENT_START_RECORD: {
                    //开始录音事件
                    Log.i( "RobotActivity",  "on event: "+ event.eventType );
                } break;

                case AIUIConstant.EVENT_STOP_RECORD: {
                    //停止录音事件
                    Log.i( "RobotActivity",  "on event: "+ event.eventType );
                } break;

                case AIUIConstant.EVENT_STATE: {	// 状态事件
                    mAIUIState = event.arg1;
                } break;

                default:
                    break;
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 显示键盘
     */
    public void showInput(final EditText et) {
        et.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 隐藏键盘
     */
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    /*-------------------------------语音转文字--------------------------*/
    private void voice_text(){
        // 有交互动画的语音识别器
        iatDialog = new RecognizerDialog(RobotActivity.this, mInitListener);

        iatDialog.setListener(new RecognizerDialogListener() {
            String resultJson = "[";//放置在外边做类的变量则报错，会造成json格式不对（？）

            @SuppressLint("SetTextI18n")
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                System.out.println("-----------------   onResult   -----------------");
                if (!isLast) {
                    resultJson += recognizerResult.getResultString() + ",";
                } else {
                    resultJson += recognizerResult.getResultString() + "]";
                }

                if (isLast) {
                    //解析语音识别后返回的json格式的结果
                    Gson gson = new Gson();
                    List<DictationResult> resultList = gson.fromJson(resultJson,
                            new TypeToken<List<DictationResult>>() {
                            }.getType());
                    StringBuilder result = new StringBuilder();
                    for (int i = 0; i < resultList.size() - 1; i++) {
                        result.append(resultList.get(i).toString());
                    }

                    if (et_chat_text.getText() != null) {
                        et_chat_text.setText( et_chat_text.getText().toString() + result);
                    }else {
                        et_chat_text.setText( et_chat_text.getText().toString());
                    }
                }
            }

            @Override
            public void onError(SpeechError speechError) {
                //自动生成的方法存根
                speechError.getPlainDescription(true);
            }
        });
        //开始听写，需将sdk中的assets文件下的文件夹拷入项目的assets文件夹下（没有的话自己新建）
        iatDialog.show();
    }

    private InitListener mInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d("RobotActivity", "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                Toast.makeText(RobotActivity.this, "初始化失败，错误码：" + code, Toast.LENGTH_SHORT).show();
            }
        }
    };

}
