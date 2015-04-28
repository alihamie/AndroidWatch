package com.dingohub.hellpong.AndroidGameFramework.Interface;

import android.view.View.OnTouchListener;

import java.util.List;

public interface TouchHandler extends OnTouchListener {

    public boolean isTouchDown(int pointer);

    public int getTouchX(int pointer);

    public int getTouchY(int pointer);

    public List<Input.TouchEvent> getTouchEvents();

    public void addWatchTouch(int move);
}