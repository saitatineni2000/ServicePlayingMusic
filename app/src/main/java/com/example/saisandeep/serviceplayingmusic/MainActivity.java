package com.example.saisandeep.serviceplayingmusic;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    Button playStopButton;
    Intent i;
    boolean MusicPlaying=false;
    String strAudioLink="10.mp3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //i=new Intent(this,MyPlayService.class);
        playStopButton= (Button) findViewById(R.id.button);
        playStopButton.setBackgroundResource(R.drawable.play_button);
        playStopButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        i=new Intent(this,MyPlayService.class);
        buttonPlayStopClick();
    }

    private void buttonPlayStopClick() {

       if(!MusicPlaying)
       {
           playStopButton.setBackgroundResource(R.drawable.button_pause);
           playAudio();
           MusicPlaying=true;
       }
        else if(MusicPlaying)
       {
           playStopButton.setBackgroundResource(R.drawable.play_button);
           stopAudio();
           MusicPlaying=false;
       }
    }

    private void stopAudio() {

        stopService(i);
        MusicPlaying=false;
    }

    private void playAudio() {

        i.putExtra("message",strAudioLink);
        startService(i);
    }
}
