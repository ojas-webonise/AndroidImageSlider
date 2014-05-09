package com.techipost.imageslider;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class SliderActivity extends Activity {

    public int currentimageindex=0;
    ImageView slidingimage, steadyImage;

    private int[] IMAGE_IDS = {
            R.drawable.splash0, R.drawable.splash1, R.drawable.splash2,
            R.drawable.splash3
    };
    private Animation in_from_right;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mygame);
        final Handler mHandler = new Handler();
        in_from_right = AnimationUtils.loadAnimation(this, R.anim.in_from_right);
        in_from_right.setAnimationListener(new AnimListener());

        // Create runnable for posting
        final Runnable mUpdateResults = new Runnable() {
            @Override
            public void run() {
                AnimateandSlideShow();
            }
        };

        int delay = 2000; // delay for 1 sec.
        int period = 3000; // repeat every 4 sec.
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(mUpdateResults);
            }
        }, delay, period);
    }

    public void onClick(View v) {
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * Helper method to start the animation on the splash screen
     */
    private void AnimateandSlideShow() {

        slidingimage = (ImageView)findViewById(R.id.slidingImage);
        steadyImage  = (ImageView)findViewById(R.id.steadyImage);

        slidingimage.setImageResource(IMAGE_IDS[currentimageindex%IMAGE_IDS.length]);

        slidingimage.startAnimation(in_from_right);
    }

    class AnimListener implements AnimationListener {
        @Override
        public void onAnimationEnd(Animation animation) {
            steadyImage.setImageResource((Integer)steadyImage.getTag());
        }

        @Override
        public void onAnimationRepeat(Animation animation) {}

        @Override
        public void onAnimationStart(Animation animation) {
            steadyImage.setTag(IMAGE_IDS[currentimageindex%IMAGE_IDS.length]);
            currentimageindex++;
        }
    }

}