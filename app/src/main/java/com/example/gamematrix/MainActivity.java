package com.example.gamematrix;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity  {

    private int level;
    private int target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Beginning target
        level = 3;
        target = 1;


    }

    public void btnPlay_Click(View view) {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra(GameActivity.LEVEL, level);
        intent.putExtra(GameActivity.TARGET, target);
        startActivityForResult(intent, GameActivity.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GameActivity.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Increase target
                target++;
                // Level up
                if (target > level * level / 2) {
                    target = 1;
                    level++;
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // fail - play again
            }

            // Play game
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            intent.putExtra(GameActivity.LEVEL, level);
            intent.putExtra(GameActivity.TARGET, target);
            startActivityForResult(intent, GameActivity.REQUEST_CODE);
        }
    }

}
