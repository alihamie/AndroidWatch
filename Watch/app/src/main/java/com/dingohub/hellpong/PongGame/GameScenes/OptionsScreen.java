package com.dingohub.hellpong.PongGame.GameScenes;

import android.view.Menu;

import com.dingohub.hellpong.AndroidGameFramework.Interface.Game;
import com.dingohub.hellpong.AndroidGameFramework.Interface.Input;
import com.dingohub.hellpong.AndroidGameFramework.Interface.Screen;

import java.util.List;

/**
 * Created by ereio on 3/21/15.
 */
public class OptionsScreen extends Screen {
    enum MenuState {MENU, OPTIONS}

    MenuState state = MenuState.MENU;
    List<Input.TouchEvent> touchEvents;
    public OptionsScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {

        if(game.getInput() !=null)
            touchEvents = game.getInput().getTouchEvents();

        switch(state) {
            case OPTIONS:
                updateOptions(touchEvents);
                break;
            case MENU:
                game.setScreen(new PongMainMenu(game));
                break;
        }
    }

    private void updateOptions(List<Input.TouchEvent> touchEvents) {
        for (int i = 0; i < touchEvents.size(); i++) {
            Input.TouchEvent event = this.touchEvents.get(i);
            if (event.type == Input.TouchEvent.TOUCH_UP) {
                state = MenuState.MENU;

            }
        }
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
