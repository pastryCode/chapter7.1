package com.example.chapter71;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mp;
    private boolean isPause = false;
    private String musicPath;
    private TextView hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button1 = (Button) findViewById(R.id.button1);
        final Button button2 = (Button) findViewById(R.id.button2);
        final Button button3 = (Button) findViewById(R.id.button3);
        hint = (TextView) findViewById(R.id.hint);
        File file = new File("/sdcard/000.mp3");
        String[] strings = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA
        };
        Cursor cursor = this.getContentResolver().query(Uri.fromFile(file),strings,null,null,null);
        cursor.moveToNext();
        musicPath = cursor.getString(2);
        mp = new MediaPlayer();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    mp.start();
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
                if(mp.isPlaying()&&!isPause){
                    mp.pause();
                    isPause = true;
                    button2.setText("Go on");
                    hint.setText("Pause the music");
                    button1.setEnabled(true);
                }else {
                    mp.start();
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
                mp.stop();
                hint.setText("Stop the music");
                button1.setEnabled(true);
                button2.setEnabled(false);
                button3.setEnabled(false);
            }
        });
    }
    private  void play(){
        try{
            mp.reset();
            mp.setDataSource(musicPath);
            mp.prepare();
            mp.start();
            hint.setText("The music is playing");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onDestroy(){
        if (mp.isPlaying()){
            mp.stop();
        }
        mp.release();
        super.onDestroy();
    }
}