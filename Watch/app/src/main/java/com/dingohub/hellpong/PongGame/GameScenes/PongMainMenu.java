package com.dingohub.hellpong.PongGame.GameScenes;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.dingohub.hellpong.AndroidGameFramework.Interface.Game;
import com.dingohub.hellpong.AndroidGameFramework.Interface.Graphics;
import com.dingohub.hellpong.AndroidGameFramework.Interface.Input;
import com.dingohub.hellpong.AndroidGameFramework.Interface.Input.*;
import com.dingohub.hellpong.AndroidGameFramework.Interface.Screen;

import java.util.List;
import java.util.Random;

/**
 * Created by ereio on 3/21/15.
 */
public class PongMainMenu extends Screen {
    enum MenuState {MENU, PLAY, OPTIONS}
    Graphics g;

    Paint title_disp, play_disp, options_disp;
    MenuState state = MenuState.MENU;


    public PongMainMenu(Game game) {
        super(game);
        title_disp = new Paint();
        title_disp.setTextSize(150);
        title_disp.setTextAlign(Paint.Align.LEFT);
        title_disp.setAntiAlias(true);
        title_disp.setColor(Color.RED);

        play_disp = new Paint();
        play_disp.setTextSize(50);
        play_disp.setTextAlign(Paint.Align.LEFT);
        play_disp.setAntiAlias(true);
        play_disp.setColor(Color.WHITE);

        options_disp = new Paint();
        options_disp.setTextSize(50);
        options_disp.setTextAlign(Paint.Align.LEFT);
        options_disp.setAntiAlias(true);
        options_disp.setColor(Color.WHITE);

    }

    @Override
    public void update(float deltaTime) {
        g = game.getGraphics();

        List<TouchEvent> touchEvents = null;

        touchEvents = game.getInput().getTouchEvents();

        if(touchEvents != null)
            checkInput(touchEvents, g);

        if(state == MenuState.PLAY) {
            g.clearScreen(Color.BLACK);
            game.setScreen(new PongGameScreen(game));
        }
        if(state == MenuState.OPTIONS) {
            game.setScreen(new OptionsScreen(game));
        }
        state = MenuState.MENU;

    }

    @Override
    public void paint(float deltaTime) {
        g = game.getGraphics();

        Random r = new Random();
        int randColor = Color.argb(255, r.nextInt(255), r.nextInt(255), r.nextInt(255));

        g.clearScreen(randColor);
        g.drawString("Hell Pong", 150, 250, title_disp);
        g.drawString("Play", 200, 400, play_disp);
        g.drawString("Options", 200, 550, options_disp);

        Paint p = new Paint();
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.STROKE);

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

    private void menu_settings(){

    }

    private void checkInput(List<TouchEvent> events, Graphics g){
        int len = events.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = events.get(i);

             if (event.type == TouchEvent.TOUCH_DOWN) {
               if (checkBounds(event, 150, 330, 200, 100)) {
                   Log.i("MainMenu", "PLAY");
                    state = MenuState.PLAY;
                }
               if(checkBounds(event, 150, 480, 300, 100)){
                  Log.i("MainMenu", "OPTIONS" + event.y);
                   state = MenuState.OPTIONS;
                }
             }


        }
    }

    private void check_bounds_plus(Rect rect, TouchEvent event){


    }
}
