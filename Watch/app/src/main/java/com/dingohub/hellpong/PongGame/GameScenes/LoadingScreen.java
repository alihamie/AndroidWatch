package com.dingohub.hellpong.PongGame.GameScenes;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;

import com.dingohub.hellpong.AndroidGameFramework.Interface.Game;
import com.dingohub.hellpong.AndroidGameFramework.Interface.Graphics;
import com.dingohub.hellpong.AndroidGameFramework.Interface.Screen;

/**
 * Created by ereio on 3/21/15.
 */
public class LoadingScreen extends Screen {
    Paint p;
    public LoadingScreen(Game game) {
        super(game);
        p = new Paint();
        p.setTextSize(40);
        p.setTextAlign(Paint.Align.CENTER);
        p.setAntiAlias(true);
        p.setColor(Color.WHITE);
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawString("////LOADING////", 450, 450, p);
        g.clearScreen(Color.BLACK);
        if(!game.getFileIO().getSharedPref().contains(game.DIFFICULTY)) {
            SharedPreferences.Editor editor = game.getFileIO().getSharedPref().edit();
            editor.putInt(game.DIFFICULTY, 0);
            editor.commit();
        }
        game.setScreen(new PongMainMenu(game));
    }


    @Override
    public void paint(float deltaTime) {
    }


    @Override
    public void pause() {
    }


    @Override
    public void resume() {
    }


    @Override
    public void dispose() {
    }


    @Override
    public void backButton() {
    }
}
