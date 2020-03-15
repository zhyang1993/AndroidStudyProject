package com.example.myapplication.audiofocus;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import androidx.databinding.library.baseAdapters.BR;

import java.io.IOException;

import kotlin.jvm.internal.PropertyReference0Impl;

public class MyMediaPlayer {
    private static MyMediaPlayer INSTANCE;
    private MediaPlayer mediaPlayer;
    public final int SINGLE_CYCLE = 1;
    public final int RANDOM_PLAY = 2;
    public final int ORDER_PLAY = 3;
    public final int LIST_LOOP = 4;
    private boolean isPause = false;
    private String songPath = "";
    //默认顺序播放
    private int recordMode = ORDER_PLAY;
    private OnMediaPlayerListener onMediaListener;
    public MyMediaPlayer (){
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(onMediaListener != null){
                    onMediaListener.OnCompletion(mp);
                }
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                //返回false 没有处理错误，会回调OnCompletion;如果返回true,不会回调OnCompletion！
                if(onMediaListener != null){
                    onMediaListener.OnErrorListener(mp,what,extra);
                }
                return true;
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if(onMediaListener != null){
                    Log.e("zhyang","----------MyMediaPlayer_onPrepared---------");
                    onMediaListener.OnPreparedListener(mp);
                }

            }
        });
        mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
                if(onMediaListener != null){
                    onMediaListener.onSeekComplete(mp);
                }

            }
        });
    }
    public static MyMediaPlayer getInstance(){
        if (INSTANCE==null){
            synchronized (MyMediaPlayer.class){//在创建对象时再进行同步锁定
                if (INSTANCE == null){
                    INSTANCE = new MyMediaPlayer();
                }
            }
        }
        return INSTANCE;
    }

    public void setAudioStreamType(int audioStreamType){
        mediaPlayer.setAudioStreamType(audioStreamType);
    }

    public void setDataSource(String path) throws IOException {
        mediaPlayer.setDataSource(path);
    }
    //播放
    public void play(){
//        String songUrl = "http://link.hhtjim.com/163/27678655.mp3";
//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        try {
//            mediaPlayer.setDataSource(songUrl);
//            mediaPlayer.prepare(); // might take long! (for buffering, etc)
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        mediaPlayer.start();
        isPause = false;
    }
    //暂停
    public void pause(){
        mediaPlayer.pause();
        isPause = true;
    }
    //停止
    public void stop(){
        mediaPlayer.stop();
        isPause = true;
    }
    //释放
    public void release(){
        if(mediaPlayer != null){
            mediaPlayer.release();
        }
    }
    //当前播放状态
    public boolean isPlay(){
       return mediaPlayer.isPlaying();
    }
    //当前是否为暂停
    public boolean isPause(){
        return isPause;
    }
    //获取流媒体的总播放时长，单位是毫秒。
    public int getDuration(){
        return mediaPlayer.getDuration();
    };

    //获取当前流媒体的播放的位置，单位是毫秒。
    public int getCurrentPosition(){
        return mediaPlayer.getCurrentPosition();
    };

    //设置当前MediaPlayer的播放位置，单位是毫秒。
    public void seekTo(int msec){
        mediaPlayer.seekTo(msec);
    };

    //设置播放模式
    public void setPlayMode(int mode){
        switch (mode){
            case SINGLE_CYCLE:
                recordMode = SINGLE_CYCLE;
                mediaPlayer.setLooping(true);
                break;
            case RANDOM_PLAY:
                recordMode = RANDOM_PLAY;

                break;
            case LIST_LOOP:
                recordMode = LIST_LOOP;

                break;
            case ORDER_PLAY:
                recordMode = ORDER_PLAY;

                break;
        }

    };
    public void setSongPath(String  path){
        songPath = path;
    }

    public String getSongPath(){
       return songPath;
    }
    public int getCurrentPlayMode(){
      return recordMode;
    };

    //判断是否循环播放。
    public boolean isLooping(){
        return mediaPlayer.isLooping();
    };
    //同步的方式装载流媒体文件
    public void prepare() throws IOException{
        mediaPlayer.prepare();
    };
    //异步的方式装载流媒体文件。
    public void prepareAsync(){
        mediaPlayer.prepareAsync();
    };
    //设置当前流媒体播放完毕，下一个播放的MediaPlayer
    public void setNextMediaPlayer(MediaPlayer next){
        mediaPlayer.setNextMediaPlayer(next);
    };

    public  interface  OnMediaPlayerListener{
         void OnCompletion(MediaPlayer mp);
         boolean OnErrorListener(MediaPlayer mp, int what, int extra);
         void OnPreparedListener(MediaPlayer mp);
         void onSeekComplete(MediaPlayer mp);
    }

    public void setOnMediaCompletionListener(OnMediaPlayerListener listener){
        this.onMediaListener = listener;
    }

}
