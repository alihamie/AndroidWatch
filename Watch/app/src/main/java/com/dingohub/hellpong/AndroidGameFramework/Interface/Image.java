package com.dingohub.hellpong.AndroidGameFramework.Interface;

import com.dingohub.hellpong.AndroidGameFramework.Interface.Graphics.ImageFormat;

public interface Image {
    public int getWidth();
    public int getHeight();
    public ImageFormat getFormat();
    public void dispose();
}