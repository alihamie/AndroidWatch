package com.dingohub.hellpong.AndroidGameFramework.Interface;

import android.graphics.Color;
import android.util.Log;

import java.util.Random;

public abstract class Screen {
    protected final Game game;

    public Screen(Game game) {
        this.game = game;
    }

    public abstract void update(float deltaTime);

    public abstract void paint(float deltaTime);

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();

    public abstract void backButton();

    public boolean checkBounds(Input.TouchEvent event, int x, int y, int width, int height){
        Graphics g = game.getGraphics();
        //g.drawRect(x, y, width, height, Color.CYAN);
        if ((event.x < x + width) && (event.y < y + height) && ( event.x > x) && (event.y > y)) {
            Random r = new Random();
            int randColor = Color.argb(255, r.nextInt(255), r.nextInt(255), r.nextInt(255));
            g.drawRect(x, y, width, height, randColor);
                return true;
            } else
                return false;
    }

}