package com.example.ywang.diseaseidentification.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.ywang.diseaseidentification.R;

import java.util.ArrayList;
import java.util.List;

public class HorizontalChart extends View {

    private Resources resources;

    private String[] eventText = new String[]{"统计1", "统计2", "统计3", "统计4", "统计5"};

    private ArrayList<Float> monthCountList = new ArrayList<Float>();

    private int max = 0;

    private Paint paintCommon = new Paint();

    private RectF rectCommon = new RectF();
    private boolean isRefresh = false;

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        eventText = null;
        monthCountList.clear();
        monthCountList = null;
        paintCommon = null;
        rectCommon = null;
        resources = null;

    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public HorizontalChart(Context context) {
        super(context);
        init();
    }

    public HorizontalChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        resources = this.getContext().getResources();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureHeight(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                result = specSize;
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                result = getHeight();
                break;
            default:
                break;
        }
        return result;
    }

    public void SetDate(List<Float> monthCountList) {
        this.monthCountList.clear();
        this.monthCountList.addAll(monthCountList);
        max = 0;
        for (Float ft : this.monthCountList) {
            if (max < ft.intValue()) {
                max = ft.intValue();
            }
        }

        // max = max + 10;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isRefresh)
            return;
        float beginX = resources.getDimension(R.dimen.chart_begin_x);
        float spaceY = (getHeight() - (resources.getDimension(R.dimen.chart_begin_y) * 2)) / 5f;
        float beginY = resources.getDimension(R.dimen.chart_begin_y);
        float vertical_line_X = resources.getDimension(R.dimen.vertical_line_x);

        float reckTotalLength = getWidth() - resources.getDimension(R.dimen.vertical_line_x) - resources.getDimension(R.dimen.chart_right_pading);
        float reckRealTotalLength = getWidth() - resources.getDimension(R.dimen.vertical_line_x) - resources.getDimension(R.dimen.chart_right_real_pading);

        // 根据不同的id设置不同的绘制颜色
        for (int i = 0; i < eventText.length; i++) {

            // 绘制事件名称
            // 绘制事件文字的画笔
            paintCommon.reset();
            paintCommon.setAntiAlias(true);
            paintCommon.setColor(Color.parseColor("#4C4C4C"));
            paintCommon.setTextSize(resources.getDimension(R.dimen.road_state_text_size));
            paintCommon.setTextAlign(Align.LEFT);
            canvas.drawText(eventText[i], beginX, beginY + (spaceY / 2f) + resources.getDimension(R.dimen.center_offset) + spaceY * i, paintCommon);

            if (monthCountList.size() > 0) {

                // 生成矩形边界
                rectCommon.left = vertical_line_X + resources.getDimension(R.dimen.line_space);
                rectCommon.top = beginY + (spaceY / 2f) + resources.getDimension(R.dimen.center_offset) + spaceY * i - resources.getDimension(R.dimen.reck_subtractor);
                rectCommon.right = vertical_line_X + reckTotalLength;
                rectCommon.bottom = beginY + (spaceY / 2f) + resources.getDimension(R.dimen.center_offset) + spaceY * i - resources.getDimension(R.dimen.reck_subtractor) + resources.getDimension(R.dimen.reck_height);
                // 绘制圆角矩形
                // 绘制矩形条的画笔，每个条目的背景
                paintCommon.reset();
                paintCommon.setAntiAlias(true);
                paintCommon.setColor(Color.parseColor("#BFC6E8"));
                paintCommon.setAlpha(30);
                paintCommon.setStyle(Paint.Style.FILL);
                canvas.drawRoundRect(rectCommon, 15, 15, paintCommon);

                if (max > 0) {

                    // 生成矩形边界
                    rectCommon.left = vertical_line_X + resources.getDimension(R.dimen.line_space);
                    rectCommon.top = beginY + (spaceY / 2f) + resources.getDimension(R.dimen.center_offset) + spaceY * i - resources.getDimension(R.dimen.reck_subtractor);
                    rectCommon.right = vertical_line_X + monthCountList.get(i) / (float) max * reckRealTotalLength;
                    rectCommon.bottom = beginY + (spaceY / 2f) + resources.getDimension(R.dimen.center_offset) + spaceY * i - resources.getDimension(R.dimen.reck_subtractor) + resources.getDimension(R.dimen.reck_height);
                    // 绘制圆角矩形
                    // 绘制矩形条的画笔
                    paintCommon.reset();
                    paintCommon.setAntiAlias(true);
                    paintCommon.setStyle(Paint.Style.FILL);
                    if (i == 0) {
                        paintCommon.setColor(Color.parseColor("#E31470"));
                    }
                    if (i == 1) {
                        paintCommon.setColor(Color.parseColor("#FD4949"));
                    }
                    if (i == 2) {
                        paintCommon.setColor(Color.parseColor("#FD8708"));
                    }
                    if (i == 3) {
                        paintCommon.setColor(Color.parseColor("#F7BF12"));
                    }
                    if (i == 4) {
                        paintCommon.setColor(Color.parseColor("#8DC734"));
                    }
                    canvas.drawRoundRect(rectCommon, 15, 15, paintCommon);
                    // 绘制数量值
                    if (monthCountList.get(i).intValue() > 0) {
                        // 绘制事件数量文字的画笔
                        paintCommon.reset();
                        paintCommon.setAntiAlias(true);
                        paintCommon.setColor(Color.parseColor("#000000"));
                        paintCommon.setTextSize(resources.getDimension(R.dimen.count_text_size));
                        paintCommon.setTextAlign(Align.LEFT);
                        canvas.drawText(String.valueOf(monthCountList.get(i).intValue()), vertical_line_X + monthCountList.get(i) / (float) max * reckRealTotalLength + resources.getDimension(R.dimen.count_text_offset), beginY + (spaceY / 2f) + resources.getDimension(R.dimen.center_offset) + spaceY * i, paintCommon);
                    }

                }
            }
        }
        isRefresh = false;
    }
}
