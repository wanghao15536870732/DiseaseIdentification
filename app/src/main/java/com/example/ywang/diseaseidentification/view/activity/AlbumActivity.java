package com.example.ywang.diseaseidentification.view.activity;

import android.animation.Animator;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cleveroad.fanlayoutmanager.FanLayoutManager;
import com.cleveroad.fanlayoutmanager.FanLayoutManagerSettings;
import com.cleveroad.fanlayoutmanager.callbacks.FanChildDrawingOrderCallback;
import com.example.ywang.diseaseidentification.R;
import com.example.ywang.diseaseidentification.adapter.SportCardsAdapter;
import com.example.ywang.diseaseidentification.utils.SportCardsUtils;

public class AlbumActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FanLayoutManager mFanLayoutManager;
    private SportCardsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        recyclerView = (RecyclerView) findViewById(R.id.rvCards);
        FanLayoutManagerSettings fanLayoutManagerSettings = FanLayoutManagerSettings
                .newBuilder(AlbumActivity.this)
                .withFanRadius(true)
                .withAngleItemBounce(5)
                .withViewHeightDp(230)
                .withViewWidthDp(150)
                .build();
        mFanLayoutManager = new FanLayoutManager(AlbumActivity.this, fanLayoutManagerSettings);

        recyclerView.setLayoutManager(mFanLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new SportCardsAdapter(AlbumActivity.this);
        mAdapter.addAll(SportCardsUtils.generateSportCards());

        mAdapter.setOnItemClickListener(new SportCardsAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, final View view) {
                if (mFanLayoutManager.getSelectedItemPosition() != itemPosition) {
                    mFanLayoutManager.switchItem(recyclerView, itemPosition);
                } else {
                    mFanLayoutManager.straightenSelectedItem(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                onClick(view, mFanLayoutManager.getSelectedItemPosition());
                            } else {
//                                onClick(mFanLayoutManager.getSelectedItemPosition());
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                }
            }
        });
        recyclerView.setAdapter(mAdapter);

        recyclerView.setChildDrawingOrderCallback(new FanChildDrawingOrderCallback(mFanLayoutManager));

        (findViewById(R.id.logo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFanLayoutManager.collapseViews();
            }
        });
    }

    public boolean deselectIfSelected() {
        if (mFanLayoutManager.isItemSelected()) {
            mFanLayoutManager.deselectItem();
            return true;
        } else {
            return false;
        }
    }
}
