package com.example.dipansh.the_boring_blog_android;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getName();
    private static final String REQUEST_URL =
            "http://mukulkyadav.pythonanywhere.com/blog/api/";

    private String urltext;
    private int lastPost;
    private ListView listView;
    private PostAdapter adapter;
    private ArrayList<Post> posts;
    private MediaPlayer media;
    private ProgressBar progressBar;
    private Handler handler;
    private Runnable position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        posts = new ArrayList<>();
        listView =  findViewById(R.id.listView);
        progressBar = findViewById(R.id.progressBar);

        media = new MediaPlayer();
        media.setAudioStreamType(AudioManager.STREAM_MUSIC);

        GetPosts request = new GetPosts();
        request.execute(REQUEST_URL);

        lastPost = -5;
        handler = new Handler();
        position = new Runnable() {
            @Override
            public void run() {
                int presentPost;
                handler.postDelayed(this, 4000);

                int first = listView.getFirstVisiblePosition();
                int last = listView.getLastVisiblePosition();

                if(last - first == 1){
                    if(first == 0){
                        presentPost = 0;
                    }else if(last == listView.getCount()-1){
                        presentPost = last;
                    }else{
                        presentPost = (first + last)/2;
                    }
                }else{
                    presentPost = (first + last)/2;
                }
                playMusicAt(presentPost);
            }
        };
        MainActivity.this.runOnUiThread(position);
    }

    private void playMusicAt(int presentPost) {

        if(lastPost != presentPost){
            if(listView.getCount() != 0){
                lastPost = presentPost;
                Post post = (Post)listView.getItemAtPosition(presentPost);
                Toast.makeText(MainActivity.this, String.valueOf(post.getTitle()), Toast.LENGTH_SHORT).show();

                try {
                    urltext = post.getMusicLink();
                    media.reset();
                    media.setDataSource(urltext);
                    new PrepareMusicPlayer().execute(urltext);
                    progressBar.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
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
            Toast.makeText(MainActivity.this, "Playing", Toast.LENGTH_SHORT).show();
        }
    }

    public void update(ArrayList<Post> list){
        adapter = new PostAdapter(MainActivity.this ,R.layout.post_item,list);
        listView.setAdapter(adapter);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private class GetPosts extends AsyncTask<String , Void , String> {

        @Override
        protected String doInBackground(String... params) {

            if (params.length < 1 || params[0] == null) {
                return null;
            }

            URL url = QueryUtils.createUrl(params[0]);
            String jsonResponse = "";
            try {
                jsonResponse = QueryUtils.makeHttpRequest(url);
            } catch (IOException e) {
                // TODO Handle the IOException
            }

            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s == null) {
                return;
            }

            posts = QueryUtils.extractPosts(s);
            update(posts);
        }
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
    protected void onResume() {
        handler.postDelayed(position, 4000);
        super.onResume();
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(position);
        if(media!=null){
            media.release();
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        if(media!=null){
            media.release();
        }
        super.onStop();
    }

}
