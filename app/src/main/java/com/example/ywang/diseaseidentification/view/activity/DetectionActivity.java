package com.example.ywang.diseaseidentification.view.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bakerj.infinitecards.AnimationTransformer;
import com.bakerj.infinitecards.CardItem;
import com.bakerj.infinitecards.InfiniteCardView;
import com.bakerj.infinitecards.ZIndexTransformer;
import com.bakerj.infinitecards.transformer.DefaultCommonTransformer;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.utils.ConstantUtils;

public class DetectionActivity extends AppCompatActivity {

    private InfiniteCardView mCardView;
    private BaseAdapter mAdapter1, mAdapter2;
    int[] colors = {0xffFF9800, 0xff3F51B5, 0xff673AB7, 0xff006064, 0xffC51162, 0xff9E9E9E
            , 0xff795548, 0xff9E9E9E};
    private Button[] firstButtons = new Button[ConstantUtils.questionList.length];
    private Button[] secondButtons = new Button[ConstantUtils.questionList.length];
    private Button[] thirdButtons = new Button[ConstantUtils.questionList.length];
    private TextView[] titleViews = new TextView[ConstantUtils.questionList.length];

    private Button[][] buttonList;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detection);
        mCardView = findViewById(R.id.view);
        mAdapter1 = new MyAdapter(colors);
        mCardView.setAdapter(mAdapter1);
        mCardView.setCardAnimationListener(new InfiniteCardView.CardAnimationListener() {
            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationEnd() {
            }
        });
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStyle3();
                mCardView.bringCardToFront(1);
            }
        });
        buttonList = new Button[][]{
                firstButtons,
                secondButtons,
                thirdButtons
        };
        toolbar = (Toolbar) findViewById(R.id.detection_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("自测分析");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }


    private void setStyle3() {
        mCardView.setClickable(false);
        mCardView.setAnimType(InfiniteCardView.ANIM_TYPE_FRONT_TO_LAST);
        mCardView.setAnimInterpolator(new OvershootInterpolator(-8));
        mCardView.setTransformerToFront(new DefaultCommonTransformer());
        mCardView.setTransformerToBack(new AnimationTransformer() {
            @Override
            public void transformAnimation(View view, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                int positionCount = fromPosition - toPosition;
                float scale = (0.8f - 0.1f * fromPosition) + (0.1f * fraction * positionCount);
                view.setScaleX(scale);
                view.setScaleY(scale);
                if (fraction < 0.5) {
                    view.setTranslationX(cardWidth * fraction * 1.5f);
                    view.setRotationY(-45 * fraction);
                } else {
                    view.setTranslationX(cardWidth * 1.5f * (1f - fraction));
                    view.setRotationY(-45 * (1 - fraction));
                }
            }

            @Override
            public void transformInterpolatedAnimation(View view, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                int positionCount = fromPosition - toPosition;
                float scale = (0.8f - 0.1f * fromPosition) + (0.1f * fraction * positionCount);
                view.setTranslationY(-cardHeight * (0.8f - scale) * 0.5f - cardWidth * (0.02f *
                        fromPosition - 0.02f * fraction * positionCount));
            }
        });
        mCardView.setZIndexTransformerToBack(new ZIndexTransformer() {
            @Override
            public void transformAnimation(CardItem card, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {
                if (fraction < 0.5f) {
                    card.zIndex = 1f + 0.01f * fromPosition;
                } else {
                    card.zIndex = 1f + 0.01f * toPosition;
                }
            }

            @Override
            public void transformInterpolatedAnimation(CardItem card, float fraction, int cardWidth, int cardHeight, int fromPosition, int toPosition) {

            }
        });
    }



    private class MyAdapter extends BaseAdapter {
        private int[] colors = {};

        MyAdapter(int[] resIds) {
            this.colors = resIds;
        }

        @Override
        public int getCount() {
            return colors.length;
        }

        @Override
        public Integer getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                        .item_card, viewGroup, false);
            }

            view.findViewById(R.id.image_detection).setBackgroundColor(colors[i]);
            titleViews[i] = view.findViewById(R.id.title_view);
            titleViews[i].setText(ConstantUtils.questionList[i]);

            firstButtons[i] = view.findViewById(R.id.choose_first);
            firstButtons[i].setBackgroundColor(colors[i] + 30);

            secondButtons[i] = view.findViewById(R.id.choose_second);
            secondButtons[i].setBackgroundColor(colors[i] + 30);


            thirdButtons[i] = view.findViewById(R.id.choose_third);
            thirdButtons[i].setBackgroundColor(colors[i] + 30);

            firstButtons[i].setEnabled(true);
            secondButtons[i].setEnabled(true);
            thirdButtons[i].setEnabled(true);

            if (i != ConstantUtils.questionList.length - 1) {
                firstButtons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firstButtons[i].setBackgroundColor(colors[i] + 60);
                        secondButtons[i].setBackgroundColor(colors[i] - 30);
                        thirdButtons[i].setBackgroundColor(colors[i] - 30);
                        setStyle3();
                        mCardView.bringCardToFront(1);
                    }
                });
            }else {
                firstButtons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(DetectionActivity.this, "正在分析中...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            secondButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setStyle3();
                    mCardView.bringCardToFront(1);
                    firstButtons[i].setBackgroundColor(colors[i] - 30);
                    secondButtons[i].setBackgroundColor(colors[i] + 60);
                    thirdButtons[i].setBackgroundColor(colors[i] - 30);
                }
            });
            thirdButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setStyle3();
                    mCardView.bringCardToFront(1);
                    firstButtons[i].setBackgroundColor(colors[i] - 30);
                    secondButtons[i].setBackgroundColor(colors[i] - 30);
                    thirdButtons[i].setBackgroundColor(colors[i] + 60);
                }
            });
            for (int k = 0; k < ConstantUtils.answerList[i].length; k++) {
                buttonList[k][i].setText(ConstantUtils.answerList[i][k]);
                buttonList[k][i].setVisibility(View.VISIBLE);
            }
            return view;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
