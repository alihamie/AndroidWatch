package com.dingohub.hellpong.PongGame.Ball;

import android.graphics.Color;
import android.graphics.Rect;

import com.dingohub.hellpong.AndroidGameFramework.Interface.Game;
import com.dingohub.hellpong.AndroidGameFramework.Interface.Graphics;

/**
 * Created by ereio on 3/21/15.
 */
public class PongBall implements Ball {

    public boolean active = false;
    private float CENTER_X = 640;
    private float CENTER_Y = 325;
    public float currentX = CENTER_X;
    public float currentY = CENTER_Y;

    Game game;
    Graphics g;
    private float speedX = 0;
    private float speedY = 0;
    private float xdirection = 0;
    private float ydirection = 0;
    public float move_speed;
    public Rect hitbox;

    public PongBall(Game game){
        move_speed = 2.0f;
        this.game = game;
        xdirection = 0.0f;
        ydirection = 0.1f;
    }
    @Override
    public void update(float deltaTime){


        if(active) {
            speedX = xdirection * move_speed * deltaTime;
            speedY = ydirection * move_speed * deltaTime;
        } else {
            speedX = 0;
            speedY = 0;
        }

        // GRAPHICAL IMPLEMENTATIONS
        g = game.getGraphics();

        //g.fill((int)CENTER_X+40, (int)CENTER_Y+75, 325, Color.WHITE);

        g.drawArc(CENTER_X, CENTER_Y,
                75, 80, 360f, 360f, true, Color.BLACK);
        g.drawArc(CENTER_X += speedX, CENTER_Y += speedY,
                75, 80, 360f, 360f, true, Color.RED);
    }

    @Override
    public void setMovement(float xmovement, float ymovement) {
           xdirection = xmovement;
           ydirection = ymovement;
    }

    @Override
    public void bounce(){

    }
    @Override
    public void trajection(){

    }
}
