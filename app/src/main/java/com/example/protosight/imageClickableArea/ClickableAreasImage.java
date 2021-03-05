package com.example.protosight.imageClickableArea;


import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


import uk.co.senab.photoview.PhotoViewAttacher;





/**
 * Created by Lukas on 10/22/2015.
 */
public class ClickableAreasImage implements PhotoViewAttacher.OnViewTapListener{

    private PhotoViewAttacher attacher;
    private OnClickableAreaClickedListener listener;

    private List<ClickableArea> clickableAreas;


    public ClickableAreasImage(PhotoViewAttacher attacher, OnClickableAreaClickedListener listener){
        this.attacher = attacher;
        attacher.setScaleType(ImageView.ScaleType.CENTER);
        init(listener);
    }

    private void init(OnClickableAreaClickedListener listener) {
        this.listener = listener;

        attacher.setOnViewTapListener(this);
    }



    private List<ClickableArea> getClickAbleAreas(int x, int y){

        List<ClickableArea> clickableAreas= new ArrayList<>();
        for(ClickableArea ca : getClickableAreas()){
            if(isBetween(ca.getX(),(ca.getX()+ca.getW()),x)){
                if(isBetween(ca.getY(),(ca.getY()+ca.getH()),y)){
                    clickableAreas.add(ca);
                }
            }
        }
        return clickableAreas;
    }

    private boolean isBetween(int start, int end, int actual){
        return (start <= actual && actual <= end);
    }

    public void setClickableAreas(List<ClickableArea> clickableAreas) {
        this.clickableAreas = clickableAreas;
    }

    public List<ClickableArea> getClickableAreas() {
        return clickableAreas;
    }

    @Override
    public void onViewTap(View view, float x, float y) {
        int px = (int) x;
        int py = (int) y;
        List<ClickableArea> clickableAreas = getClickAbleAreas(px, py);
        for(ClickableArea ca : clickableAreas){
            listener.onClickableAreaTouched(ca.getItem());
        }
    }


}
