package com.dingohub.hellpong.PongGame.GameScenes;

import com.dingohub.hellpong.AndroidGameFramework.Implementation.AndroidGame;
import com.dingohub.hellpong.AndroidGameFramework.Interface.Screen;



/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see
 */
public class PongActivity extends AndroidGame {

    @Override
    public Screen getInitScreen() {

        return new LoadingScreen(this);
    }
}
