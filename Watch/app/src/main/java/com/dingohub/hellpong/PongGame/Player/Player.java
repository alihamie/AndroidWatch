package com.dingohub.hellpong.PongGame.Player;

import com.dingohub.hellpong.AndroidGameFramework.Interface.Input;

/**
 * Created by ereio on 3/21/15.
 */
public interface Player {
    enum Position {TOP_LEFT, TOP, TOP_RIGHT, RIGHT, BOT_RIGHT,
        BOTTOM, BOT_LEFT, LEFT}

    public void update(float deltaTime);

    public void polarPosition(Input.TouchEvent event, Input.TouchEvent event2);

    public void setPosition(float deltaTime, Position position);

    public void setNegativePosition(float deltaTime, Position position);

    public void transPosition(Position current, Position destination);

    public void lerpPosition(Position current, Position dest);

}
