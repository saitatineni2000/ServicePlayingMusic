package com.example.saisandeep.serviceplayingmusic;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by saisandeep on 3/30/2015.
 */
public class MyPlayService extends Service implements MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener,
        MediaPlayer.OnSeekCompleteListener,MediaPlayer.OnInfoListener,MediaPlayer.OnBufferingUpdateListener{

    MediaPlayer mediaPlayer=new MediaPlayer();
    String sntAudioLink;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
        mediaPlayer.setOnInfoListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.reset();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sntAudioLink=intent.getStringExtra("mesaage");
        mediaPlayer.reset();

        if(!mediaPlayer.isPlaying())
        {
            try {
                mediaPlayer.setDataSource("http://licensing.glowingpigs.com/Audio/"+sntAudioLink);
                mediaPlayer.prepareAsync();
                //this will prepare media player
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(mediaPlayer != null)
        {
            if(mediaPlayer.isPlaying())
            {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

        stopMedia();
        stopSelf();
    }

    private void stopMedia() {
        if(mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
        }

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        switch(what)
                {

                    case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK :
                        Toast.makeText(this,"MEDIA ERROR NOT VALID FOR PROGRESSIVE PLAYBACK"+extra,Toast.LENGTH_SHORT).show();
                        break;

                    case MediaPlayer.MEDIA_ERROR_SERVER_DIED :
                        Toast.makeText(this,"MEDIA ERROR SERVER DIED"+extra,Toast.LENGTH_SHORT).show();
                        break;

                    case MediaPlayer.MEDIA_ERROR_UNKNOWN :
                        Toast.makeText(this,"MEDIA ERROR "+extra,Toast.LENGTH_SHORT).show();
                        break;

                }

        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        playMedia();
    }

    private void playMedia() {
        if(!mediaPlayer.isPlaying())
        {
            mediaPlayer.start();
        }
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }
}
