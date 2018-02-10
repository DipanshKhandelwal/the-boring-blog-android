package com.example.dipansh.the_boring_blog_android;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;

public class ScrollingActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private MediaPlayer media;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout mCollapsingToolbarLayout = findViewById(R.id.toolbar_layout);


        progressBar = findViewById(R.id.progressBarPost);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Link to the blog", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent i = getIntent();
        Post post = (Post)i.getSerializableExtra("myPost");
        Toast.makeText(this, post.getTitle(), Toast.LENGTH_SHORT).show();


        mCollapsingToolbarLayout.setTitle(post.getTitle());
        TextView postContent = findViewById(R.id.postContent);
        postContent.setText(post.getContent());

        media = new MediaPlayer();
        media.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            String urlText = post.getMusicLink();
            media.reset();
            media.setDataSource(urlText);
            new PrepareMusicPlayer().execute(urlText);
            progressBar.setVisibility(View.VISIBLE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private class PrepareMusicPlayer extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... strings) {
            try {
                media.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(String string) {
            media.start();
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(ScrollingActivity.this, "Playing", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if(media!=null){
            media.release();
        }
        super.onBackPressed();
    }
}
