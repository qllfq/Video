package com.example.qiaolulu.video;


import android.media.MediaPlayer;
import android.net.Uri;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mPlayer;
    private VideoView mVideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVideoView = findViewById(R.id.video);
        mPlayer = findViewById(R.id.player);
        mPlayer.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        mPlayer.setVisibility(View.INVISIBLE);
        mVideoView.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mVideoView.setMediaController(new MediaController(MainActivity.this));
                mVideoView.setOnCompletionListener(new MyPlayerOnCompletionListener());
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/raw/"+R.raw.test_video);
                        mVideoView.setVideoURI(uri);
                    }
                });

                mVideoView.start();
            }
        }).start();
    }



    private class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            mPlayer.setVisibility(View.VISIBLE);
            mVideoView.setVisibility(View.INVISIBLE);
        }
    }
}
