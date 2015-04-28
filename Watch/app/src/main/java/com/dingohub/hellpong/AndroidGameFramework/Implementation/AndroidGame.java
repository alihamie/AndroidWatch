package com.dingohub.hellpong.AndroidGameFramework.Implementation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.dingohub.hellpong.AndroidGameFramework.BaseClasses.BaseConnectActivity;
import com.dingohub.hellpong.AndroidGameFramework.Interface.Audio;
import com.dingohub.hellpong.AndroidGameFramework.Interface.FileIO;
import com.dingohub.hellpong.AndroidGameFramework.Interface.Game;
import com.dingohub.hellpong.AndroidGameFramework.Interface.Graphics;
import com.dingohub.hellpong.AndroidGameFramework.Interface.Input;
import com.dingohub.hellpong.AndroidGameFramework.Interface.Screen;
import com.dingohub.hellpong.AndroidGameFramework.Utilities.DataListenerService;

public abstract class AndroidGame extends BaseConnectActivity implements Game {
    final static boolean DEBUG = true;
    private final static String TAG = "AndroidGame";

    AndroidFastRenderView renderView;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    float scaleX;
    float scaleY;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //List<Node> connectedNodes =
        //        Wearable.NodeApi.getConnectedNodes(googleApiClient).await().getNodes();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        // Forcing Landscape rendering only
        boolean isPortrait = false;
        int frameBufferWidth = isPortrait ? 800: 1280;
        int frameBufferHeight = isPortrait ? 1280: 800;

        // uses the 4 byte Bitmap Config for added precision and rendering
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
                frameBufferHeight, Bitmap.Config.ARGB_8888);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);


       if(displayMetrics != null){
             //scaleX = (float) frameBufferWidth / displayMetrics.widthPixels;
             //scaleY = (float) frameBufferWidth / displayMetrics.heightPixels;
       // } else {
             scaleX = (float) frameBufferWidth
                    / getWindowManager().getDefaultDisplay().getWidth();
             scaleY = (float) frameBufferHeight
                    / getWindowManager().getDefaultDisplay().getHeight();
        }


        renderView = new AndroidFastRenderView(this, frameBuffer);
        graphics = new AndroidGraphics(getAssets(), frameBuffer);
        fileIO = new AndroidFileIO(this);
        audio = new AndroidAudio(this);
       // input = connectedNodes == null
       // ? new AndroidInput(this, renderView, scaleX, scaleY) :
        input = new AndroidInput(this, renderView, scaleX, scaleY);
        screen = getInitScreen();

        watchReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    int move = bundle.getInt(DataListenerService.PLAYERMOVE);
                    input.getTouchHandler().addWatchTouch(move);
                    //Toast.makeText(getApplicationContext(), "Data Received: " + move, Toast.LENGTH_LONG).show();
                }
            }

        };
        setContentView(renderView);

    }

    @Override
    public void onResume() {
        super.onResume();
        screen.resume();
        renderView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        renderView.pause();
        screen.pause();

        if (isFinishing())
            screen.dispose();
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    @Override
    public Graphics getGraphics() {
        return graphics;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    public Screen getCurrentScreen() {

        return screen;
    }
}
