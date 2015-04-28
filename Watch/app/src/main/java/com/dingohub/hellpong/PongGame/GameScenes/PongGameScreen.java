package com.dingohub.hellpong.PongGame.GameScenes;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.dingohub.hellpong.AndroidGameFramework.BaseClasses.BaseConnectActivity;
import com.dingohub.hellpong.AndroidGameFramework.Interface.Game;
import com.dingohub.hellpong.AndroidGameFramework.Interface.Graphics;
import com.dingohub.hellpong.AndroidGameFramework.Interface.Input;
import com.dingohub.hellpong.AndroidGameFramework.Interface.Input.*;
import com.dingohub.hellpong.AndroidGameFramework.Interface.Screen;
import com.dingohub.hellpong.PongGame.Ball.PongBall;
import com.dingohub.hellpong.PongGame.Player.Player;
import com.dingohub.hellpong.PongGame.Player.PongPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ereio on 3/21/15.
 */
public class PongGameScreen extends Screen {
    final static boolean DEBUG = true;
    enum GameState {READYING, RUNNING, PAUSED, GAME_OVER}

    private int LEVEL=0, OPTIONS=1, INSTR=2, GAMEOVER=3, PAUSE=4;
    private int HUD_ELEMENTS = 5;
    private ArrayList<Paint> hud;
    private boolean redraw = false;
    private final int optionWidth = 1250;
    private final int optionHeight = 50;

    GameState state = GameState.READYING;
    private Graphics g;
    private Input in;

    PongPlayer player;
    PongBall ball;
    private long game_time = 0;
    int player_passes = 0;
    int level = 0;
    int localDifficulty = 0;


    public PongGameScreen(Game game) {
        super(game);

        hud = new ArrayList<Paint>();
        // In case I want to change specific elements
        // currently, they are the same.
        for(int i=0; i < HUD_ELEMENTS; i++){
            Paint temp = new Paint();
            if(GAMEOVER == i) temp.setTextSize(200);
            else temp.setTextSize(30);
            if(OPTIONS == i) temp.setTextAlign(Paint.Align.RIGHT);
            else if(LEVEL == i) temp.setTextAlign(Paint.Align.LEFT);
            else temp.setTextAlign(Paint.Align.CENTER);
            temp.setAntiAlias(true);
            if(GAMEOVER == i) temp.setColor(Color.RED);
            else temp.setColor(Color.WHITE);
            hud.add(temp);
        }

        changeDifficulty(0);
        player = new PongPlayer(game);
        ball = new PongBall(game);
        game_time = System.currentTimeMillis();

    }

    @Override
    public void update(float deltaTime) {
        g = game.getGraphics();
        List<Input.TouchEvent> touchEvents = null;

        if(game.getInput() != null)
            touchEvents = game.getInput().getTouchEvents();

        switch(state) {
            case READYING:
                updateReadying(touchEvents);
                break;
            case RUNNING:
                updateRunning(touchEvents, deltaTime);
                checkPlayerGame();
                break;
            case PAUSED:
                updatePaused(touchEvents);
                break;
            case GAME_OVER:
                updateGameOver(touchEvents);
                break;
        }

        player.update(deltaTime);
        ball.update(deltaTime);
       // checkCollision();

    }

    @Override
    public void paint(float deltaTime) {
        switch(state) {
            case READYING:
                drawReadyingUI();
                break;
            case RUNNING:
                drawRunningUI();
                break;
            case PAUSED:
                drawPauseUI();
                break;
            case GAME_OVER:
                drawGameOverUI();
                break;
        }
    }

    private void updateReadying(List<TouchEvent> touchEvents){
        if (touchEvents.size() > 1) {
            game.getGraphics().clearScreen(Color.BLACK);
            resume();
        }
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime){
        for (int i = 0; i < touchEvents.size(); i++) {
            TouchEvent event = touchEvents.get(i);
            checkHudEvents(event);
            checkPlayerEvents(event);
            float mAngleDown = cartesianToPolar(1 - event.x, 2 - event.y);
            Log.i("Game", "Angle:" + mAngleDown);
        }
    }

    private float cartesianToPolar(float x, float y) {
        return (float) -Math.toDegrees(Math.atan2(x - 0.5f, y - 0.5f));
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                // Resume
                if (checkBounds(event, 740 - 100, 300-33, 150, 75))
                    resume();

                //I'm subtracting to accomodate the offset that strings use
                // later will calculate the offset in an object for easy declaration
                // menu
                // strings when they render appear upwards from the cords declared
                // rectangles are the opposite
                if (checkBounds(event, 740 - 75, 400 - 50, 100, 75)) {
                    nullify();
                    game.setScreen(new PongMainMenu(game));
                }
                //Difficulty
                if (checkBounds(event, 740 - 100, 500 - 50, 150, 75)) {
                    changeDifficulty(localDifficulty++);
                }
                // Toggle Watch Input
                if (checkBounds(event, 740 - 250, 600 - 50, 400, 75)) {
                    BaseConnectActivity.toggleWatchInput = !BaseConnectActivity.toggleWatchInput;
                }

            }
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents){
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_DOWN) {
                if (checkBounds(event, 0, 0, 1200, 800)) {
                    nullify();

                    game.setScreen(new PongMainMenu(game));
                    return;
                }
            }
        }
    }

    private void checkPlayerEvents(TouchEvent event){
        if (event.type == TouchEvent.TOUCH_DOWN || event.type == TouchEvent.TOUCH_DRAGGED) {
            if(checkBounds(event,300,0,250,250))
                player.current_position = Player.Position.TOP_LEFT;
            else if(checkBounds(event,550,0,250,250))
                player.current_position = Player.Position.TOP;
            else if(checkBounds(event,800,0,250,250))
                player.current_position = Player.Position.TOP_RIGHT;
            else if(checkBounds(event,800,250,250,250))
                player.current_position = Player.Position.RIGHT;
            else if(checkBounds(event,800,500,250,250))
                player.current_position = Player.Position.BOT_RIGHT;
            else if(checkBounds(event,550,500,250,250))
                player.current_position = Player.Position.BOTTOM;
            else if(checkBounds(event,300,500,250,250))
                player.current_position = Player.Position.BOT_LEFT;
            else if(checkBounds(event,300,250,250,250))
                player.current_position = Player.Position.LEFT;

        }
    }

    private void checkCollision(){
        Random r = new Random();
        int random = r.nextInt(10);

        if(ball.hitbox.intersect(player.top_rect)){
            ball.setMovement(0.1f * random, 0.1f * random);
        }
        if(ball.hitbox.intersect(player.top_lft_rect)){
            ball.setMovement(0.1f * random, 0.1f * random );
        }
        if(ball.hitbox.intersect(player.top_rgt_rect)){
            ball.setMovement(0.1f * -random, 0.1f * random );
        }
        if(ball.hitbox.intersect(player.lft_rect)){
            ball.setMovement(0.1f * random, 0.0f * random );
        }
        if(ball.hitbox.intersect(player.btm_lft_rect)){
            ball.setMovement(0.1f * random, 0.1f * -random );
        }
        if(ball.hitbox.intersect(player.btm_rect)){
            ball.setMovement(0.1f * random, 0.1f * -random );
        }
        if(ball.hitbox.intersect(player.btm_rgt_rect)){
            ball.setMovement(0.1f * -random, 0.1f * -random );
        }
        if(ball.hitbox.intersect(player.rgt_rect)){
            ball.setMovement(0.1f * random, 0.1f * -random );
        }
    }

    private void changeDifficulty(int newDiff){
        localDifficulty %= 5;
       if(newDiff == localDifficulty) {
           localDifficulty = game.getFileIO().getSharedPref().getInt(game.DIFFICULTY, 0);
       } else {
           SharedPreferences.Editor editor = game.getFileIO().getSharedPref().edit();
           editor.putInt(game.DIFFICULTY, newDiff);
           editor.commit();
       }
    }
    private void checkHudEvents(TouchEvent event){
        if(event.type == TouchEvent.TOUCH_DOWN)
            if(checkBounds(event, optionWidth-150, optionHeight-50, 200, 100))
                pause();
        //if(checkBounds(event, ))
    }

    private void checkPlayerGame(){
        if((System.currentTimeMillis() - game_time) /
                1000000 % 60 > 58){
            Log.i("Level", Integer.toString(level));
            level++;
            game_time = System.currentTimeMillis();
            redraw = true;
        }
    }

    private void drawReadyingUI(){
        Graphics g = game.getGraphics();

        g.drawARGB(155, 0, 0, 0);
        g.drawString("Double Tap To Start", 650, 300, hud.get(INSTR));
    }
    private void drawRunningUI(){
        Graphics g = game.getGraphics();

        if(redraw) {
            g.clearScreen(Color.BLACK);
            hud.get(LEVEL).setColor(Color.BLACK);
            g.drawString("Level: " + level, 100, 50, hud.get(LEVEL));
            redraw = false;
            hud.get(LEVEL).setColor(Color.WHITE);
        }

        g.drawString("Level: " + level, 100, 50 , hud.get(LEVEL));
        g.drawString("Options", optionWidth, optionHeight, hud.get(OPTIONS));

    }

    private void drawPauseUI(){
        Graphics g = game.getGraphics();
        g.drawARGB(155, 0, 0, 0);
        g.drawString("Continue", 700, 300,hud.get(PAUSE));
        g.drawString("Menu", 700, 400, hud.get(PAUSE));
        g.drawString("Difficulty:" + localDifficulty, 700, 500, hud.get(PAUSE));
        g.drawString("Toggle Watch Controller", 700, 600, hud.get(PAUSE));
    }
    private void drawGameOverUI(){
        Graphics g = game.getGraphics();
        g.drawRect(0, 0, 1281, 801, Color.BLACK);
        g.drawString("GAME OVER.", 640, 300, hud.get(GAMEOVER));
    }

    private void nullify(){
        hud = null;
        g = game.getGraphics();
        g.clearScreen(Color.BLACK);
        in = null;
        player_passes = 0;
        level = 0;
    }


    @Override
    public void pause() {
        if(state == GameState.RUNNING) {
            ball.active = false;
            state = GameState.PAUSED;
        }
    }

    @Override
    public void resume() {
        if(state == GameState.PAUSED || state == GameState.READYING) {
            ball.active = true;
            redraw = true;
            state = GameState.RUNNING;
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {

        pause();
    }

    public PongBall getBall() {
        return ball;
    }
    public PongPlayer getPlayer() {
        return player;
    }
}
