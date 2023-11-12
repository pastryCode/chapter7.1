package com.example.chapter71;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private MediaPlayer player;
    private boolean isPause = false;
    private TextView hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button button1 = (Button) findViewById(R.id.button1);
        final Button button2 = (Button) findViewById(R.id.button2);
        final Button button3 = (Button) findViewById(R.id.button3);
        hint = (TextView) findViewById(R.id.hint);
        player = new MediaPlayer();
        player.setOnCompletionListener(mediaPlayer -> setPlay());


        /**********************调整音量*****************************************************/
        final AudioManager am = (AudioManager) MainActivity.this.getSystemService(Context.AUDIO_SERVICE);
        MainActivity.this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        SeekBar seekbar = (SeekBar)findViewById(R.id.seekBar1);
        seekbar.setMax(am.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        int progress  = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        seekbar.setProgress(progress);

        final TextView tv = (TextView) findViewById(R.id.volume);
        tv.setText("Volume:"+progress);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv.setText("Volume:"+progress);
                am.setStreamVolume(AudioManager.STREAM_MUSIC,progress,AudioManager.FLAG_PLAY_SOUND);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        /************************************************************************/



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    setPlay();
                    if(isPause){
                        button2.setText("Pause");
                        isPause = false;
                    }
                    button2.setEnabled(true);
                    button3.setEnabled(true);
                    button1.setEnabled(false);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(player.isPlaying()&&!isPause){
                    player.pause();
                    isPause = true;
                    button2.setText("Go on");
                    hint.setText("Pause the music");
                    button1.setEnabled(true);
                }else {
                    player.start();
                    button2.setText("Pause");
                    hint.setText("Go on the music");
                    isPause = false;
                    button1.setEnabled(false);
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.stop();
                hint.setText("Stop the music");
                button1.setEnabled(true);
                button2.setEnabled(false);
                button3.setEnabled(false);
            }
        });
    }
    private  void setPlay(){
        try{
            player.reset();
            player = MediaPlayer.create(this,R.raw.so_far_away);
            player.prepare();
            player.start();
            hint.setText("The music is playing");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onDestroy(){
        if (player.isPlaying()){
            player.stop();
        }
        player.release();
        super.onDestroy();
    }
}