package com.example.abdulhanan.forecastweather;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Splash_Screen extends AppCompatActivity {

    TextView splashtitle;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash__screen);

        splashtitle = findViewById(R.id.splashtitle);
        progress = findViewById(R.id.progress);

        Typeface typeface = Typeface.createFromAsset(this.getAssets(),"ostrich-sans/OstrichSans-Heavy.otf");
        splashtitle.setTypeface(typeface);

        Thread splash = new Thread()
        {
            @Override
            public void run() {



                try {

                    for(int i=0; i < 100; i++) {
                        sleep(30);
                        progress.setProgress(i);
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        splash.start();
    }
}
