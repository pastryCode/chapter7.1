package com.example.chapter71;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer player;
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
        player = new MediaPlayer();
        player.setOnCompletionListener(mediaPlayer -> setPlay());
//
//        File file = new File("/sdcard/000.mp3");
//        String[] strings = {
//                MediaStore.Audio.Media.TITLE,
//                MediaStore.Audio.Media.ARTIST,
//                MediaStore.Audio.Media.DATA
//        };
//        Cursor cursor = this.getContentResolver().query(Uri.fromFile(file),strings,null,null,null);
//        cursor.moveToNext();
//        musicPath = cursor.getString(2);



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