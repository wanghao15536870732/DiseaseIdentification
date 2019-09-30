package com.example.ywang.diseaseidentification.view.activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.utils.SnackBarUtil;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.iflytek.cloud.SpeechSynthesizer;

public class WebUIActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private Toolbar toolbar;
    private TextView tvTitle;
    private WebView webView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String title;
    private String url;
    /*与悬浮按钮相关*/
    private FloatingActionsMenu mFloatingActionsMenu;
    private FloatingActionButton text_to_voice,change_voice,fab_up;
    private String VOICE = null;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_ui);
        toolbar = (Toolbar) findViewById(R.id.toolbar_web);
        tvTitle = (TextView) findViewById(R.id.title_web);
        webView = (WebView) findViewById(R.id.web_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout_web);
        init();
        initFloatButton();
    }
    protected void init(){
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
        title = getIntent().getExtras().getString("title");
        url = getIntent().getExtras().getString("url");
        tvTitle.setText(title);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(this);
        init_web_view();
        scrollView = (ScrollView) findViewById(R.id.scrollView_news);
    }

    @SuppressLint("SetJavaScriptEnabled")
    protected  void init_web_view(){
        WebSettings settings = webView.getSettings();
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        String dir = getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        //启用地理定位
        webView.getSettings().setGeolocationEnabled(true);
        //设置定位的数据库路径
        webView.getSettings().setGeolocationDatabasePath(dir);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                swipeRefreshLayout.setRefreshing(true);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                swipeRefreshLayout.setRefreshing(false);
                super.onPageFinished(view, url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }

            @Override
            public void onGeolocationPermissionsHidePrompt() {
                super.onGeolocationPermissionsHidePrompt();
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });

        //点击返回
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
                return false;
            }

        });
        webView.loadUrl(url);
    }

    @Override
    public void onRefresh() {
        webView.reload();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.item_copy:
            //获取剪贴板管理器：
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", url);
            cm.setPrimaryClip(mClipData);
            SnackBarUtil.showSnackBar(R.string.copy_msg, webView, this);
            break;
        case R.id.item_browser:
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            break;
        case android.R.id.home:
            this.finish();
            break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void initFloatButton() {
        mFloatingActionsMenu = (FloatingActionsMenu) findViewById(R.id.detail_actions_menu);
        change_voice = (FloatingActionButton) findViewById(R.id.change_voice);
        change_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SpeechSynthesizer.getSynthesizer() != null && SpeechSynthesizer.getSynthesizer().isSpeaking()) {
                    SpeechSynthesizer.getSynthesizer().pauseSpeaking();
                    text_to_voice.setIcon(R.drawable.ic_play);
                    text_to_voice.setTitle("文字播报");
                }
                mFloatingActionsMenu.toggle();
                new BottomSheet.Builder(WebUIActivity.this)
                        .title("选择声音种类")
                        .sheet(R.menu.change_voice)
                        .listener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case R.id.xiaoyan:
                                        VOICE = "xiaoyan";
                                        Toast.makeText(WebUIActivity.this, "已选择 小燕 女声 青年 中英文", Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.xiaoyu:
                                        VOICE = "xiaoyu";
                                        Toast.makeText(WebUIActivity.this, "已选择 小宇 男声 青年 中英文", Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.henry:
                                        VOICE = "henry";
                                        Toast.makeText(WebUIActivity.this, "已选择 亨利 男声 青年 英文", Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.vimary:
                                        VOICE = "vimary";
                                        Toast.makeText(WebUIActivity.this, "已选择 玛丽 女声 青年 英文", Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.xiaomei:
                                        VOICE = "xiaomei";
                                        Toast.makeText(WebUIActivity.this, "已选择 小梅 女声 青年 中英文粤语", Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.vixl:
                                        VOICE = "vixl";
                                        Toast.makeText(WebUIActivity.this, "已选择 小莉 女声 青年 中英文台湾普通话", Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.xiaorong:
                                        VOICE = "xiaorong";
                                        Toast.makeText(WebUIActivity.this, "已选择 小蓉 女声 青年 汉语四川话", Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.xiaokun:
                                        VOICE = "xiaokun";
                                        Toast.makeText(WebUIActivity.this, "已选择 小坤 男声 青年 汉语 河南话", Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.xiaoqiang:
                                        VOICE = "xiaoqiang";
                                        Toast.makeText(WebUIActivity.this, "已选择 小强 男声 青年 汉语湖南话", Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.vixying:
                                        VOICE = "vixying";
                                        Toast.makeText(WebUIActivity.this, "已选择 小莹 女声 青年 汉语陕西话", Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.nannan:
                                        VOICE = "nannan";
                                        Toast.makeText(WebUIActivity.this, "已选择 楠楠 女声 童年 汉语普通话", Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.vils:
                                        VOICE = "vils";
                                        Toast.makeText(WebUIActivity.this, "已选择 老孙 男声 老年 汉语普通话", Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.xiaoxin:
                                        VOICE = "xiaoxin";
                                        Toast.makeText(WebUIActivity.this, "已选择 小新 男声 童年 汉语普通话", Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.cancel_chose:
                                        Toast.makeText(WebUIActivity.this, "已取消声音切换", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        }).show();
            }
        });

        text_to_voice = (FloatingActionButton) findViewById( R.id.action_text_to_voice );
        text_to_voice.setIcon( R.drawable.ic_play  );
        text_to_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFloatingActionsMenu.toggle();
                if (text_to_voice.getTitle().equals("文字播报")) {
                    text_to_voice.setIcon( R.drawable.ic_pause);
                    text_to_voice.setTitle( "结束播报" );
                }else {
                    if (SpeechSynthesizer.getSynthesizer().isSpeaking())
                        SpeechSynthesizer.getSynthesizer().pauseSpeaking();
                    text_to_voice.setIcon( R.drawable.ic_play );
                    text_to_voice.setTitle( "文字播报" );
                }
            }
        });

        fab_up = (FloatingActionButton) findViewById(R.id.fab_up);
        fab_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
                mFloatingActionsMenu.toggle();
            }
        });
    }

}
