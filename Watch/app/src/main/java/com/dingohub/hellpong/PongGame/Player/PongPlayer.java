package com.dingohub.hellpong.PongGame.Player;


import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;

import com.dingohub.hellpong.AndroidGameFramework.Interface.Game;
import com.dingohub.hellpong.AndroidGameFramework.Interface.Graphics;
import com.dingohub.hellpong.AndroidGameFramework.Interface.Input;

import java.util.Random;

/**
 * Created by ereio on 3/21/15.
 */
public class PongPlayer implements Player {
    private final static String TAG = "PongPlayer";

    final public int LIVES = 3;
    public Position current_position;

    //private int CENTER_X = 625;
    //private int CENTER_Y = 350;
    //private int xOffset = 0;
    //private int yOffset = 250;

    private float CENTER_X = 275;
    private float CENTER_Y = 0;
    private float rCenterX = CENTER_X + 800/2;
    private float rCenterY = CENTER_Y + 800/2;

    public static Rect top_rect = new Rect(0,0,0,0);
    public static Rect top_lft_rect = new Rect(0,0,0,0);
    public static Rect top_rgt_rect = new Rect(0,0,0,0);
    public static Rect lft_rect = new Rect(0,0,0,0);
    public static Rect rgt_rect = new Rect(0,0,0,0);
    public static Rect btm_rgt_rect = new Rect(0,0,0,0);
    public static Rect btm_lft_rect = new Rect(0,0,0,0);
    public static Rect btm_rect = new Rect(0,0,0,0);

    private Game game;
    private Random r;
    float angle = 0;
    float speed = 1.5f;

    public PongPlayer(Game game) {
        current_position = Position.BOTTOM;
        this.game = game;
        r = new Random();
        angle=1.0f;
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        int randColor = Color.argb(255, r.nextInt(255), r.nextInt(255), r.nextInt(255));

        g.drawArc(CENTER_X, CENTER_Y,
                800, 800, angle, 90, false, randColor);

        if(angle <= 0)
            angle = 360;
        //angle = angle % 360;

        // g.drawRect(CENTER_X + xOffset, CENTER_Y + yOffset, 175, 50, Color.RED);



        if(angle >= 0)
            setPosition(deltaTime, current_position);
        else
            setNegativePosition(deltaTime, current_position);

    }

    @Override
    public void polarPosition(Input.TouchEvent event, Input.TouchEvent event2){

    }

    private float cartesianToPolar(float x, float y) {
        return (float) -Math.toDegrees(Math.atan2(x - 0.5f, y - 0.5f));
    }

    @Override
    public void transPosition(Position current, Position destination) {

    }

    @Override
    public void lerpPosition(Position current, Position dest) {

    }

    private float speed_down(float speed){
        return speed;
    }

    @Override
    public void setPosition(float deltaTime, Position position) {
        switch(position) {
            case TOP_LEFT:
                if(angle < 182)
                    angle += speed * deltaTime;
                else if(angle > 178)
                    angle -= speed * deltaTime;
                break;
            case TOP:
                if(angle < 226)
                    angle += speed * deltaTime;
                else if(angle > 224)
                    angle -= speed * deltaTime;
                break;
            case TOP_RIGHT:
                if(angle < 271)
                    angle += speed * deltaTime;
                else if(angle > 269)
                    angle -= speed * deltaTime;
                break;
            case RIGHT: // RIGHT DOENS'T FOLLOW TO BOT
                if(angle > 317 )
                    angle += speed * deltaTime;
                else if (angle < 313)
                    angle -= speed * deltaTime;
                break;
            case BOT_RIGHT: // BOT RIGHT RESETS 360
                if(angle > 5 && angle < 180)
                    angle -= speed * deltaTime;
                else if(angle < 359 && angle > 180)
                    angle += speed * deltaTime;
                break;
            case BOTTOM:    // BOT DOESN'T FOLLOW TO RIGHT
                if(angle < 46 || (angle > 310))
                    angle += speed * deltaTime;
                else if(angle > 44)
                    angle -= speed * deltaTime;
                break;
            case BOT_LEFT:
                if(angle < 92)
                    angle += speed * deltaTime;
                else if(angle > 87)
                    angle -= speed * deltaTime;
                break;
            case LEFT:
                if(angle < 137)
                    angle += speed * deltaTime;
                else if(angle > 133)
                    angle -= speed * deltaTime;
                break;
            default:
                Log.e(TAG, "Error Occured on Position handling");
                angle -= 0.4;
                break;
        }

    }

    @Override
    public void setNegativePosition(float deltaTime, Position position) {
        switch(position) {
            case TOP_LEFT:
                if(angle < -182)
                    angle += speed * deltaTime;
                else if(angle > -178)
                    angle -= speed * deltaTime;
                break;
            case TOP:
                if(angle < -226)
                    angle += speed * deltaTime;
                else if(angle > -224)
                    angle -= speed * deltaTime;
                break;
            case TOP_RIGHT:
                if(angle < -271)
                    angle += speed * deltaTime;
                else if(angle > -269)
                    angle -= speed * deltaTime;
                break;
            case RIGHT: // RIGHT DOENS'T FOLLOW TO BOT
                if(angle > -317 )
                    angle += speed * deltaTime;
                else if (angle < -313)
                    angle -= speed * deltaTime;
                else
                    angle = 315;
                break;
            case BOT_RIGHT: // BOT RIGHT RESETS 360
                if(angle > -5 && angle < -180)
                    angle -= speed * deltaTime;
                else if(angle < -359 && angle > -180)
                    angle += speed * deltaTime;
                break;
            case BOTTOM:    // BOT DOESN'T FOLLOW TO RIGHT
                if(angle < -46 || (angle > -310))
                    angle += speed * deltaTime;
                else if(angle > -44)
                    angle -= speed * deltaTime;
                break;
            case BOT_LEFT:
                if(angle < -91.5)
                    angle += speed * deltaTime;
                else if(angle > -88.5)
                    angle -= speed * deltaTime;
                break;
            case LEFT:
                if(angle < -136)
                    angle += speed * deltaTime;
                else if(angle > -134)
                    angle -= speed * deltaTime;
                break;
            default:
                Log.e(TAG, "Error Occured on Position handling");
                angle -= 0.4;
                break;
        }
    }

}
