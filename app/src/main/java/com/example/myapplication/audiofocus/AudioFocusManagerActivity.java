package com.example.myapplication.audiofocus;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
/*
* MediaPlayer通过setDataSource传入url播放音乐,在真机10.0上无法运行报错，-1或者-38，0
* MediaPlayer通过MediaPlayer.create(this,R.raw.years)，可以在真机10.0上运行
* 具体原因：1.怀疑外部的歌曲网络链接url,可能无法访问
*           2.待追查
* */
public class AudioFocusManagerActivity extends AppCompatActivity implements AudioFocusManager.OnAudioFocusChangeListener,AudioFocusManager.onRequestFocusResultListener,MyMediaPlayer.OnMediaPlayerListener{
    private ImageButton btnLast,btnPlayOrPause,btnNext;
    private SeekBar progressBar;
    private Timer timer;
    private TimerTask timerTask;
    private TextView tvWords,tvCurrentTime,tvTotalTime;
    private MyMediaPlayer myMediaPlayer;
    private AudioFocusManager audioFocusManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        initView();
        initMediaPlay();
        initAudioFocus();
        btnPlayOrPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myMediaPlayer.isPlay() == false){
                    if(myMediaPlayer.isPause() == false){
                        String songUrl = "http://m7.music.126.net/20200315235112/fb424dbd5b6923557f34eb805a26b4e8/ymusic/e801/2c76/4a91/9c4194a49ce9aaf631c807f010438fd2.mp3?bitrate=128000";
                        myMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        try {
                            myMediaPlayer.setDataSource(songUrl);
                            myMediaPlayer.prepareAsync(); // might take long! (for buffering, etc)
                            btnPlayOrPause.setEnabled(false);
                            Log.e("zhyang","--------准备播放--------");
                        } catch (IOException e) {
                            e.printStackTrace();
                    }

                    }else {
                        //播放之前要获取音频焦点
                        Log.e("zhyang","--------请求了焦点--------");
                        audioFocusManager.requestAudioFocus();
                    }

                }else{
                    myMediaPlayer.pause();
                    setPlayStatusText();
                    //暂停要释放音频焦点
                }
            }
        });
    }
    public void initView(){
        btnLast = findViewById(R.id.btn_last);
        btnPlayOrPause = findViewById(R.id.btn_play_or_pause);
        btnNext = findViewById(R.id.btn_next);
        tvWords = findViewById(R.id.tv_words);
        progressBar = findViewById(R.id.progressBar);
        tvCurrentTime = findViewById(R.id.tv_current_time);
        tvTotalTime = findViewById(R.id.tv_total_time);
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                  if(fromUser){
                      seekBar.setProgress(progress);
                      myMediaPlayer.seekTo(progress);

                  }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                myMediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                myMediaPlayer.play();
            }
        });
    }

    public void initMediaPlay(){
        myMediaPlayer = MyMediaPlayer.getInstance();
        myMediaPlayer.setOnMediaCompletionListener(this);
    }

    public void initAudioFocus(){
        audioFocusManager = new AudioFocusManager(this);
        audioFocusManager.setOnAudioFocusChangeListener(this);
        audioFocusManager.setOnHandleResultListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myMediaPlayer.stop();
        myMediaPlayer.release();
    }

    public void setPlayStatusText(){
        if(myMediaPlayer.isPlay()){
            btnPlayOrPause.setEnabled(true);
            btnPlayOrPause.setBackground(getResources().getDrawable(R.drawable.pause));
            tvWords.setText("正在播放");
        }else{
            tvWords.setText("暂停中");
            btnPlayOrPause.setBackground(getResources().getDrawable(R.drawable.play));
        }
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange){
            //获得焦点，暂时性失去焦点之后获得还是会播放音乐的
            case AudioManager.AUDIOFOCUS_GAIN:
                tvWords.setText("AudioManager.AUDIOFOCUS_GAIN 获得焦点，马上播放！");
                Log.e("zhyang","--------AudioManager.AUDIOFOCUS_GAIN 获得焦点，马上播放！--------");
                if(!myMediaPlayer.isPlay()){
                    myMediaPlayer.play();
                }
                break;
            //永久性失去焦点
            case AudioManager.AUDIOFOCUS_LOSS:
                tvWords.setText("永久性失去焦点，播放暂停！");
                Log.e("zhyang","--------永久性失去焦点，播放暂停！--------");
                myMediaPlayer.pause();
                break;
             //后两种属于暂时失去焦点
             //暂停播放
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                tvWords.setText("暂时失去焦点，播放暂停！");
                Log.e("zhyang","--------暂时失去焦点，播放暂停！--------");
                myMediaPlayer.pause();
                break;
            //降低音量继续播放
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                tvWords.setText("降低音量继续播放！");
                audioFocusManager.lowStreamVolume();
                Log.e("zhyang","--------降低音量继续播放！--------");
                break;
        }
    }

    @Override
    public void onHandleResult(int result) {
     switch (result){
         //请求失败
         case AudioManager.AUDIOFOCUS_REQUEST_FAILED:
             myMediaPlayer.pause();
             tvWords.setText("未获取到音频焦点，无法播放");
             Log.e("zhyang","--------未获取到音频焦点，无法播放--------");
             break;
         //请求成功
         case AudioManager.AUDIOFOCUS_REQUEST_GRANTED:
             tvWords.setText("焦点请求成功，马上播放！");
             Log.e("zhyang","--------焦点请求成功，马上播放！--------");
             tvTotalTime.setText(timeParse(myMediaPlayer.getDuration()));
             myMediaPlayer.play();
             audioProgress();
             setPlayStatusText();
             break;
         //请求延迟
         case AudioManager.AUDIOFOCUS_REQUEST_DELAYED:
             tvWords.setText("被其他应用占用，无法播放，请等候！");
             Log.e("zhyang","--------被其他应用占用，无法播放，请等候！--------");
             break;
     }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    //播放完成以后回调
    @Override
    public void OnCompletion(MediaPlayer mp) {
          Log.e("zhyang","----OnErrorListener返回false，然后回调OnCompletion-----");
          myMediaPlayer.pause();
          progressBar.setProgress(0);
          tvCurrentTime.setText("00:00");
          tvTotalTime.setText("00:00");
          setPlayStatusText();

    }

    @Override
    public boolean OnErrorListener(MediaPlayer mp, int what, int extra) {
        Log.e("zhyang","----播放错误---错误代码："+what+"-----Extra："+extra);
        //返回false 没有处理错误，会回调OnCompletion;如果返回true,不会回调OnCompletion！
        //myMediaPlayer.play();
        return true;
    }
   //异步缓存完成以后回调
    @Override
    public void OnPreparedListener(MediaPlayer mp) {
        Log.e("zhyang","--------异步缓存完成以后回调--------");
        progressBar.setMax(myMediaPlayer.getDuration());
        //播放之前要获取音频焦点
        audioFocusManager.requestAudioFocus();
    }
    //当使用seekTo()设置播放位置的时候回调
    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }
    /*
    * 计时器上涨时间
    * */
    public void audioProgress(){
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (myMediaPlayer.isPlay()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress((myMediaPlayer.getCurrentPosition()));
                            tvCurrentTime.setText(timeParse(myMediaPlayer.getCurrentPosition()));
                        }
                    });
                }
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }
    /*
    * 转换格式00:00
    * */
    public String timeParse(long duration) {
        String time = "" ;
        long minute = duration / 60000 ;
        long seconds = duration % 60000 ;
        long second = Math.round((float)seconds/1000) ;
        if( minute < 10 ){
            time += "0" ;
        }
        time += minute+":" ;
        if( second < 10 ){
            time += "0" ;
        }
        time += second ;
        return time ;
    }
}
