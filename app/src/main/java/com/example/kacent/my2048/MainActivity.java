package com.example.kacent.my2048;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    public TextView soreView,bestView;
    int tempsore=0;
    public static MainActivity mMainactivity;
    public static AimaLayout anima;
    public Button button;
    public GameView gameView;

    public MainActivity() {
        mMainactivity = this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        soreView = (TextView) findViewById(R.id.sorce);
        bestView = (TextView) findViewById(R.id.best_sore);
        anima = (AimaLayout) findViewById(R.id.animation);
        button = (Button) findViewById(R.id.resent);
        gameView = (GameView) findViewById(R.id.gameView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.startGame();

            }
        });
    }

    public static AimaLayout getAnimaLayout() {
        return anima;
    }

    public void clearSore() {
        int sore = 0;
        showView(sore);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    public void addSore(int insore) {

        tempsore+=insore;
        showView(tempsore);
        //计算分数  当分数 超过 最高分数  就更新
        int maxNum = Math.max(tempsore, getBestSore());
        saveBestSore(maxNum);
        bestView.setText(maxNum+"");
    }

    public void showView(int sore) {
        soreView.setText(sore + "");

    }


    public void saveBestSore(int sore) {
        SharedPreferences.Editor  editor=getPreferences(MODE_PRIVATE).edit();
        editor.putInt("best_Sore", sore);
        editor.commit();
    }

    public int getBestSore() {
        return getPreferences(MODE_PRIVATE).getInt("best_Sore",0);
    }
    public static MainActivity getmMainactivity() {
        return mMainactivity;
    }


}
