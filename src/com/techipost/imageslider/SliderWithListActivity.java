package com.techipost.imageslider;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.Timer;
import java.util.TimerTask;

public class SliderWithListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(new listAdapter(this));
    }

    private class listAdapter extends BaseAdapter {

        ImageView slidingImage, steadyImage;
        Context context;
        private LayoutInflater inflater;

        public listAdapter(Context context){
            this.context = context;
            inflater = LayoutInflater.from(this.context);
        }

        @Override
        public int getCount() {
            return 100;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = inflater.inflate(R.layout.mygame, null);
            slidingImage = (ImageView)view.findViewById(R.id.slidingImage);
            steadyImage  = (ImageView)view.findViewById(R.id.steadyImage);
            ImageOperation imageOperation = new ImageOperation(context);
            imageOperation.startAnimation(slidingImage, steadyImage);
            return view;
        }
    }

    public class ImageOperation {

        Handler mHandler;
        public int currentimageindex = 0;
        private int[] IMAGE_IDS = { R.drawable.splash0, R.drawable.splash1, R.drawable.splash2, R.drawable.splash3 };
        Context context;
        public ImageOperation(Context context) {
            this.context = context;
            mHandler = new Handler();
        }

        private void startAnimation(final ImageView slidingImage, final ImageView steadyImage){
            int delay = 2000; // delay for 1 sec.
            int period = 3000; // repeat every 4 sec.
            Timer timer = new Timer();
            steadyImage.setImageResource(IMAGE_IDS[currentimageindex%IMAGE_IDS.length]);
            currentimageindex++;
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    // Create runnable for posting
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            AnimateandSlideShow(slidingImage, steadyImage);
                        }
                    });
                }
            }, delay, period);
        }

        /**
         * Helper method to start the animation on the splash screen
         * @param steadyImage
         * @param slidingImage
         */
        private void AnimateandSlideShow(ImageView slidingImage, ImageView steadyImage) {
            Animation in_from_right = AnimationUtils.loadAnimation(context, R.anim.in_from_right);
            in_from_right.setAnimationListener(new AnimListener(steadyImage));
            slidingImage.setImageResource(IMAGE_IDS[currentimageindex%IMAGE_IDS.length]);
            slidingImage.startAnimation(in_from_right);
        }

        class AnimListener implements AnimationListener {

            ImageView steadyImage;

            public AnimListener(ImageView steadyImage) {
                this.steadyImage = steadyImage;
            }

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

}
