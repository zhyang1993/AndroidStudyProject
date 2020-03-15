package com.example.myapplication.audiofocus;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.os.Build;
import android.util.Log;

public class AudioFocusManager implements AudioManager.OnAudioFocusChangeListener{
    private Context context;
    private AudioManager audioManager;
    private AudioFocusManager INSTANCE;
    private OnAudioFocusChangeListener afChangeListener;
    private onRequestFocusResultListener mOnRequestFocusResultListener;
    //8.0及以后请求焦点需要AudioFocusRequest参数
    private AudioFocusRequest mFocusRequest;
    private AudioAttributes mAudioAttributes;
    public AudioFocusManager(Context context){
        this.context = context;
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public AudioManager getAudioManager(){
        return audioManager;
    }

    public void requestAudioFocus(){
        int result;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Log.e("zhyang","--------当前大于8.0--------");
            //大于等于8.0
            if(mFocusRequest == null){
                if (mAudioAttributes == null) {
                    //音频流属性，说明这个音频流是干嘛的要干嘛
                    mAudioAttributes = new AudioAttributes.Builder()
                            //此音频流是音乐电影（17个。。。闹钟，阅读器，游戏，智能语音，导航，聊天语音，视频会议，电话，电话嘟嘟提示音）
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            //内容类型表示内容的一般类别。(4个。。。CONTENT_TYPE_MOVIE用于电影流媒体服务,CONTENT_TYPE_MUSIC用于音乐播放应用程序,游戏提示音，speech讲话）
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build();
                }
                /*
                * AUDIOFOCUS_GAIN_TRANSIENT:只是短暂获得，一会就释放焦点，比如你只是想发个notification时用下一秒不到的铃声。
                * AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK：只是背景获得，之前的音频焦点使用者无需释放焦点给，可与其共同使用。
                * AUDIOFOCUS_GAIN：我要求完全获得焦点，其他人需要释放焦点。比如我要播放音乐了，这时就要抢占整个音频焦点。
                * AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE:表示对音频的临时聚焦请求，预计持续时间较短,其他应用或系统不可以播放，例如语音。
                * */
                mFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)//音频焦点的用途 -- 每个请求中都必须包含此字段。此字段的值与 Android 8.0 之前的 requestAudioFocus() 调用中所使用的 durationHint 值相同：AUDIOFOCUS_GAIN、AUDIOFOCUS_GAIN_TRANSIENT、AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK 或 AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE。
                        .setAudioAttributes(mAudioAttributes)
                        //当其他应用使用 AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK 请求焦点时，持有焦点的应用通常不会收到 onAudioFocusChange() 回调，因为系统可以自行降低音量。
                        // 如果您需要暂停播放而不是降低音量，请调用 setWillPauseWhenDucked(true)，然后创建并设置 OnAudioFocusChangeListener，具体如自动降低音量中所述。
                        .setWillPauseWhenDucked(true)
                       //当焦点被其他应用锁定时，对音频焦点的请求可能会失败。此方法可实现延迟获取焦点，即在焦点可用时异步获取焦点。
                        .setAcceptsDelayedFocusGain(true)
                        //只有当您在请求中还指定了 willPauseWhenDucked(true) 或 setAcceptsDelayedFocusGain(true) 时，才需要 OnAudioFocusChangeListener。
                        //请注意，要使“延迟获取焦点”起作用，您还必须在音频请求中指定 AudioManager.OnAudioFocusChangeListener，因为您的应用必须收到回调才能知道自己获取了焦点。
                        .setOnAudioFocusChangeListener(this)
                        .build();
            }
            result = audioManager.requestAudioFocus(mFocusRequest);
        }else{
            //小于8.0
            result = audioManager.requestAudioFocus(this,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
        }
        if(mOnRequestFocusResultListener != null){
            mOnRequestFocusResultListener.onHandleResult(result);
        }
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        if(afChangeListener != null){
            afChangeListener.onAudioFocusChange(focusChange);
        }
    }

    /**
     * Same as AudioManager.OnAudioFocusChangeListener.
     * 向外部提供一个监听焦点变化的接口
     */
    public interface OnAudioFocusChangeListener {
        void onAudioFocusChange(int focusChange);
    }

    public void setOnAudioFocusChangeListener(OnAudioFocusChangeListener listener) {
        afChangeListener = listener;
    }

    /**
     * Handle the result of audio focus.
     * 处理请求焦点返回的结果
     */
    public interface onRequestFocusResultListener {
        void onHandleResult(int result);
    }
    
    
    public void setOnHandleResultListener(onRequestFocusResultListener listener) {
        mOnRequestFocusResultListener = listener;
    }

    /**
     * Release audio focus.
     * 释放焦点
     */
    public void releaseAudioFocus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioManager.abandonAudioFocusRequest(null);
        } else {
            audioManager.abandonAudioFocus(this);
        }
    }

    public void lowStreamVolume(){
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER , AudioManager.FLAG_PLAY_SOUND );
    }
    public void eaiseStreamVolume(){
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE  , AudioManager.FLAG_PLAY_SOUND );
    }


}
