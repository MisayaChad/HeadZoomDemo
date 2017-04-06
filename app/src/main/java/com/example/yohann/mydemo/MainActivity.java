package com.example.yohann.mydemo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import me.everything.android.ui.overscroll.VerticalOverScrollBounceEffectDecorator;
import me.everything.android.ui.overscroll.adapters.ScrollViewOverScrollDecorAdapter;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    public static final String TAG = MainActivity.class.getSimpleName();

    public boolean isFirst = true;
    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;
    ImageView iv_image;

    int[] imagePoint;

    int imageWidth = 0;
    int imageHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_image = (ImageView) findViewById(R.id.iv_image);
        ImageButton ib_set = (ImageButton) findViewById(R.id.ib_set);
//        ib_set.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                changeImage(100);
//            }
//        });
        ib_set.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action=event.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG,"按钮被按下了");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d(TAG,"按钮被释放了");
                        break;
                    case MotionEvent.ACTION_BUTTON_PRESS:{
                        Log.d(TAG,"按钮被按下了!!!!");
                        break;
                    }case MotionEvent.ACTION_BUTTON_RELEASE:{
                        Log.d(TAG,"按钮被释放了!!!!");
                        break;
                    }
                }
                return true;
            }
        });
        ScrollView scrollview = (ScrollView) findViewById(R.id.scrollview);
        scrollview.setOnTouchListener(this);

        imagePoint  = new int[2];
        iv_image.getLocationOnScreen(imagePoint);

//        new VerticalOverScrollBounceEffectDecorator(new ScrollViewOverScrollDecorAdapter(scrollview));
//        OverScrollDecoratorHelper.setUpOverScroll(scrollview);

    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int forward = 0;

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        }

        if(event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();
//            resumeImageView();
            resumeAnimation();
            if(y1 - y2 > 50) {
                Toast.makeText(MainActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
            } else if(y2 - y1 > 50) {
                Toast.makeText(MainActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
            } else if(x1 - x2 > 50) {
                Toast.makeText(MainActivity.this, "向左滑", Toast.LENGTH_SHORT).show();
            } else if(x2 - x1 > 50) {
                Toast.makeText(MainActivity.this, "向右滑", Toast.LENGTH_SHORT).show();
            }


        }
        if(event.getAction() == MotionEvent.ACTION_MOVE) {
            y2 = event.getY();
            LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) iv_image.getLayoutParams();
            int width = ll.width;
            int height = ll.height;
            Log.d(TAG, "MotionEvent.ACTION_MOVE");
            Log.d(TAG, "y2:" +y2 +"y１:" + y1);
            if(y1 - y2 > 50) {
//                Toast.makeText(MainActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
                forward = 0;
            } else if(y2 - y1 > 50&&width<=280&&height<=280) {
//                Toast.makeText(MainActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
                forward = 1;
                changeImage(y2 - y1);
            }

//            changeImage(y2 - y1);

            if (forward == 1) {

            }

        }
        return super.onTouchEvent(event);
    }

    public void changeImage(float ave) {
        if (isFirst) {
            LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) iv_image.getLayoutParams();
            imageWidth = ll.width;
            imageHeight = ll.height;
            isFirst = false;
        }
//        if (ave>0) {
            LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) iv_image.getLayoutParams();
            int width = ll.width;
            int height = ll.height;

            if (width <imageWidth || height <imageHeight) {
                width = imageWidth;
                height = imageHeight;
            }

            ll.width  = (int) (width + ave/30);
            ll.height = (int) (height + ave/30);
            iv_image.setLayoutParams(ll);
//        }
        Log.d(TAG, "ave:" +ave);

    }

    public void resumeImageView() {
        LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) iv_image.getLayoutParams();
        ll.width  = imageWidth;
        ll.height = imageHeight;
        iv_image.setLayoutParams(ll);
    }

    public void resumeAnimation() {

        LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) iv_image.getLayoutParams();
        int width = ll.width;
        int height = ll.height;
        float scale = width/imageWidth;



        ObjectAnimator anim = ObjectAnimator.ofFloat(iv_image,"zhy",1.0f,scale);
        anim.setDuration(200);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();

//                iv_image.setScaleX(cVal);
//                iv_image.setScaleY(cVal);

                LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) iv_image.getLayoutParams();
                int width = ll.width;
                int height = ll.height;
                if (width <imageWidth || height <imageHeight) {
                    width = imageWidth;
                    height = imageHeight;
                }
                ll.width  = (int) (width - 10);
                ll.height = (int) (height - 10);
                iv_image.setLayoutParams(ll);
            }
        });

    }

}
