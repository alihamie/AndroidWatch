package com.dingohub.hellpong.AndroidGameFramework.Interface;

import java.util.List;

public interface Input {

    public static TouchHandler touchHandler = null;

    public static class TouchEvent {
        public static final int TOUCH_DOWN = 0;
        public static final int TOUCH_UP = 1;
        public static final int TOUCH_DRAGGED = 2;
        public static final int TOUCH_HOLD = 3;

        public int type;
        public int x, y;
        public int pointer;

    }

    public boolean isTouchDown(int pointer);

    public int getTouchX(int pointer);

    public int getTouchY(int pointer);

    public List<TouchEvent> getTouchEvents();

    public TouchHandler getTouchHandler();
}