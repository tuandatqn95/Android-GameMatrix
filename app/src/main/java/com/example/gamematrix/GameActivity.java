package com.example.gamematrix;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    public static final String LEVEL = "LEVEL";
    public static final String TARGET = "TARGET";
    public static final int REQUEST_CODE = 9;


    private int level;
    private int target;
    private int clicked = 0;
    private GridView gvGameBoard;
    ImageView imageResult;


    private boolean isPlayed = false;

    List<Integer> sample;
    List<Integer> listResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gvGameBoard = (GridView) findViewById(R.id.gvGameBoard);
        imageResult = (ImageView) findViewById(R.id.imageResult);

        // Get target
        Intent intent = getIntent();
        level = intent.getExtras().getInt(LEVEL);
        target = intent.getExtras().getInt(TARGET);


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int gridItemHeight = metrics.heightPixels / level;

        gvGameBoard.setNumColumns(level);

        // Create a random sample
        sample = new ArrayList<>();
        for (int i = 0; i < level * level; i++) {
            if (i < target)
                sample.add(1);
            else
                sample.add(0);
        }
        randomArray(sample);

        // Create list to save current state
        listResult = new ArrayList<>();
        for (int i = 0; i < level * level; i++) {
            listResult.add(sample.get(i));
        }

        // Adapter to load data to gridview
        final GameAdapter adapter = new GameAdapter(this, listResult, gridItemHeight);
        gvGameBoard.setAdapter(adapter);

        // Time to show the sample
        int time = (level - 1) * 500;
        new CountDownTimer(time, 500) {
            @Override
            public void onTick(long l) {

            }

            // Hide the sample
            @Override
            public void onFinish() {
                adapter.resetItem();
                adapter.notifyDataSetChanged();
                isPlayed = true;
            }
        }.start();


        gvGameBoard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (isPlayed) {
                    if ((int) adapter.getItem(i) == 0) {
                        // Selected a grid
                        adapter.setItem(i);
                        adapter.notifyDataSetChanged();
                        clicked++;
                        if (clicked >= target) {
                            // Win - Show result and finish
                            if (CheckResult()) {
                                setResult(Activity.RESULT_OK);
                                isPlayed = false;
                                imageResult.setImageResource(R.drawable.correct);
                            } else {
                                setResult(Activity.RESULT_CANCELED);
                                isPlayed = false;
                                imageResult.setImageResource(R.drawable.incorrect);

                            }


                            imageResult.setVisibility(View.VISIBLE);
                            new CountDownTimer(1000, 500) {
                                @Override
                                public void onTick(long l) {

                                }

                                @Override
                                public void onFinish() {
                                    finish();
                                }
                            }.start();

                        }
                    } else {
                        // Unselected a grid
                        adapter.setItem(i);
                        adapter.notifyDataSetChanged();
                        clicked--;
                    }
                }
            }
        });


    }


    private boolean CheckResult() {
        // Compare to result
        for (int i = 0; i < level * level; i++) {
            if (sample.get(i) != listResult.get(i))
                return false;
        }
        return true;
    }

    private void randomArray(List<Integer> list) {
        // Create random array
        Random rd = new Random();

        for (int i = list.size() - 1; i > 0; i--) {
            int pos = rd.nextInt(i + 1);
            int temp = list.get(pos);
            list.set(pos, list.get(i));
            list.set(i, temp);
        }
    }
}
