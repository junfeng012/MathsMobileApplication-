package com.example.assignment1;

import android.app.Application;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;

import java.io.IOException;

public class Assignment1ApplicationClass extends Application {
    SoundPool globalSoundPool;
    SoundPool.Builder globalSoundPoolBuilder;

    AudioAttributes globalAttributes;
    AudioAttributes.Builder globalAttributesBuilder;

    AudioManager audioManager;
    float gCurVolume, gMaxVolume, gVolume;
    int globalSoundID_coin;

    protected void playGSoundCoin(){

        if(sFX_state==true){
            globalSoundPool.play(globalSoundID_coin, 1, 1, 0, 0, 1);
        }
    }

    protected void gLoadSounds(){
        globalSoundID_coin = globalSoundPool.load(this, R.raw.sfx, 1);
    }
    protected void gCreateSoundPool () {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            globalAttributesBuilder = new AudioAttributes.Builder();
            globalAttributesBuilder.setUsage(AudioAttributes.USAGE_GAME);
            globalAttributesBuilder.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION);
            globalAttributes = globalAttributesBuilder.build();

            globalSoundPoolBuilder = new SoundPool.Builder();
            globalSoundPoolBuilder.setAudioAttributes(globalAttributes);
            globalSoundPool = globalSoundPoolBuilder.build();
        } else {
            globalSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }

    }
    public Boolean getsFX_state(){
        return sFX_state;
    }
    public void setsFX_state(Boolean sFX_state){
        this.sFX_state = sFX_state;
    }

    Boolean sFX_state;

    public Boolean getBGM_state() {
        return BGM_state;
    }

    public void setBGM_state(Boolean BGM_state) {
        this.BGM_state = BGM_state;
    }

    Boolean BGM_state;

    @Override
    public void onCreate() {
        super.onCreate();
        gCreateSoundPool();
        gLoadSounds();
        setsFX_state(true);
        setBGM_state(false);
        mp = new MediaPlayer();

    }
    MediaPlayer mp;

    protected void musicPlay(){
        mp.reset();
        mp.setLooping(true);
        try{
            mp.setDataSource(getApplicationContext(), Uri.parse("android.resource://com.example.assignment1/"+R.raw.bgm));
            mp.prepare();
            mp.start();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    protected void musicStop(){
        mp.stop();
    }
}
