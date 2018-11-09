package com.example.abdulhanan.forecastweather;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Splash_Screen extends AppCompatActivity {

    private ProgressBar progress;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
               WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash__screen);

       ImageView img1 = findViewById(R.id.img1);
       ImageView img2 = findViewById(R.id.img2);
       ImageView img3 = findViewById(R.id.img3);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.fade);
        Animation animation1 = AnimationUtils.loadAnimation(this,R.anim.fade2);
        Animation animation2 = AnimationUtils.loadAnimation(this,R.anim.fade3);
        img1.startAnimation(animation);
        img2.startAnimation(animation1);
        img3.startAnimation(animation2);


        progress = findViewById(R.id.progress);

       // Typeface typeface = Typeface.createFromAsset(this.getAssets(),"ostrich-sans/OstrichSans-Heavy.otf");
       // splashtitle.setTypeface(typeface);

        Thread splash = new Thread()
        {
            @Override
            public void run() {



                try {

                    for(int i=0; i < 100; i++) {
                        sleep(50);
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
