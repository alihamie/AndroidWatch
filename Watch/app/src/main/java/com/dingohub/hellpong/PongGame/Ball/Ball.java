package com.dingohub.hellpong.PongGame.Ball;

/**
 * Created by ereio on 3/21/15.
 */
public interface Ball {

    public void update(float deltaTime);

    public void setMovement(float xmove, float ymove);

    public void bounce();

    public void trajection();

}
