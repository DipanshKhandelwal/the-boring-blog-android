package com.example.dipansh.the_boring_blog_android;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private PostAdapter adapter;
    private ArrayList<Post> list;
    private MediaPlayer media;
    private ProgressBar progessBar;
    private Handler handler;
    private Runnable position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        list = new ArrayList<>();
        String content = "Lobsja ahisha ashia has amsh amsahis sashis amshias amhais amanljdm dnd ad,nod haod d mdadh ahdad aan ajjojao hsiahsah jdod s dsh hsidsd hisdhs dls dsnosnd sdsnods sdhsb dsmdbisds sdbsdbnsdbs";
        for(int i=0;i<10;i++){
            list.add(new Post("published","title",content,"slug","tech","seotitle","seo descp","author","date","date","link"));
        }
        adapter = new PostAdapter(MainActivity.this ,R.layout.post_item,list);
        listView =  findViewById(R.id.listView);
        listView.setAdapter(adapter);

        handler = new Handler();
        position = new Runnable() {
            @Override
            public void run() {
                String showText ="";
                handler.postDelayed(this, 4000);

                int first = listView.getFirstVisiblePosition();
                int last = listView.getLastVisiblePosition();

                if(last - first == 1){
                    if(first == 0){
                        showText = "0";
                    }else if(last == listView.getCount()-1){
                        showText = String.valueOf(last);
                    }else{
                        showText = (String.valueOf((first + last)/2));
                    }
                }else{
                    showText = (String.valueOf((first + last)/2));
                }
                Toast.makeText(MainActivity.this, showText, Toast.LENGTH_SHORT).show();
            }
        };
        MainActivity.this.runOnUiThread(position);
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
}
