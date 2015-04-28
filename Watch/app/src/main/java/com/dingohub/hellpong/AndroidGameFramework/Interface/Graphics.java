package com.dingohub.hellpong.AndroidGameFramework.Interface;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public interface Graphics {
    public static enum ImageFormat {
        ARGB8888, ARGB4444, RGB565
    }

    public Image newImage(String fileName, ImageFormat format);

    public void clearScreen(int color);

    public void drawLine(int x, int y, int x2, int y2, int color);

    public void drawRect(int x, int y, int width, int height, int color);

    public void drawRect(int x, int y, int width, int height, Paint paint);

    public void drawImage(Image image, int x, int y, int srcX, int srcY,
                          int srcWidth, int srcHeight);

    public void DEBUG_RECT(Rect rect);

    public void fill(int x, int y, int radius, int color);

    public void drawArc(float x, float y, float width, float height, float startAngle,
                        float sweepAngle, boolean useCenter, int color);

    public void drawImage(Image Image, int x, int y);

    public void fillArc(RectF rec, float fAngle, float sweepAngle, boolean useCenter, int color);

    void drawString(String text, int x, int y, Paint paint);

    public int getWidth();

    public int getHeight();

    public void drawARGB(int i, int j, int k, int l);

}
