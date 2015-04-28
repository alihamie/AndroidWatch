package com.dingohub.hellpong.AndroidGameFramework.Implementation;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import com.dingohub.hellpong.AndroidGameFramework.Interface.Input;
import com.dingohub.hellpong.AndroidGameFramework.Interface.TouchHandler;

import java.util.List;

public class AndroidInput implements Input {

    public static TouchHandler touchHandler;

    public AndroidInput(Context context, View view, float scaleX, float scaleY) {
            touchHandler = new MultiTouchHandler(view, scaleX, scaleY);
    }

    @Override
    public boolean isTouchDown(int pointer) {
        return touchHandler.isTouchDown(pointer);
    }

    @Override
    public int getTouchX(int pointer) {
        return touchHandler.getTouchX(pointer);
    }

    @Override
    public int getTouchY(int pointer) {
        return touchHandler.getTouchY(pointer);
    }


    @Override
    public List<TouchEvent> getTouchEvents() {
        return touchHandler.getTouchEvents();
    }

    @Override
    public TouchHandler getTouchHandler() {
        if(touchHandler != null) {
            return touchHandler;
        } else {
            return null;
        }
    }

}