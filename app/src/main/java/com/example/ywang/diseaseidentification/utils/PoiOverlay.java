package com.example.ywang.diseaseidentification.utils;

import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.search.poi.PoiResult;
import com.example.ywang.diseaseidentification.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于显示poi的overly
 */

public class PoiOverlay extends OverlayManager {

    private static final int MAX_POI_SIZE = 10;

    private PoiResult mPoiResult = null;

    public PoiOverlay(BaiduMap baiduMap) {
        super(baiduMap);
    }

    public void setData(PoiResult poiResult) {
        this.mPoiResult = poiResult;
    }

    @Override
    public final List<OverlayOptions> getOverlayOptions() {
        if (mPoiResult == null || mPoiResult.getAllPoi() == null) {
            return null;
        }
        List<OverlayOptions> markerList = new ArrayList<OverlayOptions>();
        int markerSize = 0;
        for (int i = 0; i < mPoiResult.getAllPoi().size()
                && markerSize < MAX_POI_SIZE; i++) {
            if (mPoiResult.getAllPoi().get(i).location == null) {
                continue;
            }
            markerSize++;
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            markerList.add(new MarkerOptions()
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.mipmap.icon_gcoding)).extraInfo(bundle)
                    .position(mPoiResult.getAllPoi().get(i).location));

        }
        return markerList;
    }

    public PoiResult getPoiResult() {
        return mPoiResult;
    }

    public boolean onPoiClick(int i) {
        return false;
    }

    @Override
    public final boolean onMarkerClick(Marker marker) {
        if (!mOverlayList.contains(marker)) {
            return false;
        }
        if (marker.getExtraInfo() != null) {
            return onPoiClick(marker.getExtraInfo().getInt("index"));
        }
        return false;
    }

    @Override
    public boolean onPolylineClick(Polyline polyline) {
        // TODO Auto-generated method stub
        return false;
    }
}