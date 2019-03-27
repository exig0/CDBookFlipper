package com.exigo.cdbookflipper;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;


public class MainActivity extends AppCompatActivity {


    private float y;
    private float start_angle=0;
    private float end_angle;

    private int iD_front, iD_back;
    private int distance = 8000;
    private repeater = 25;

    private Boolean proceed = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repeatcycle();


    }

    private void repeatcycle() {

        for(int i=1; i < repeater; i++) {

            initializeFrame4();
            initializeFrame3();
            initializeFrame2();
            initializeFrame1();

        }
    }


    // First frame
    @SuppressLint("ClickableViewAccessibility")
    private void initializeFrame1() {

        FrameLayout FrontFrame=(FrameLayout) findViewById(R.id.main_frame);

        final FrameLayout newFrameFront = new FrameLayout(this);
        iD_front = generateViewId();
        newFrameFront.setId(iD_front);
        newFrameFront.setBackgroundColor(Color.parseColor("#FFC107"));
        newFrameFront.setPivotX(0);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        FrameLayout.LayoutParams params_text = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        params.height = 450;
        newFrameFront.setLayoutParams(params);

        ImageView imageViewFront = new ImageView(this);
        imageViewFront.setImageResource(R.drawable.ic_train);
        imageViewFront.setScaleY(0.75f);
        imageViewFront.setScaleX(0.75f);

        TextView textFront = new TextView(this);
        textFront.setText("IMG #0");
        textFront.setTextSize(16);
        textFront.setPadding(20, 0, 0, 20);
        textFront.setLayoutParams(params_text);

        newFrameFront.addView(textFront);
        newFrameFront.addView(imageViewFront);
        newFrameFront.bringToFront();

        FrontFrame.addView(newFrameFront);


        final FrameLayout newFrameBack = new FrameLayout(this);
        iD_back = generateViewId();
        newFrameBack.setId(iD_back);
        newFrameBack.setLayoutParams(params);
        newFrameBack.setPivotX(0);
        newFrameBack.setBackgroundColor(Color.parseColor("#FFEE58"));
        newFrameBack.setVisibility(View.INVISIBLE);

        ImageView imageViewBack = new ImageView(this);
        imageViewBack.setImageResource(R.drawable.ic_airplane);
        imageViewBack.setScaleY(0.75f);
        imageViewBack.setScaleX(0.75f);
        imageViewBack.setRotationX(180);

        FrameLayout.LayoutParams params_text_bot = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.TOP);
        TextView textBack = new TextView(this);
        textBack.setText("IMG #1");
        textBack.setTextSize(16);
        textBack.setRotationX(180);
        textBack.setPadding(20, 0, 0, 20);
        textBack.setLayoutParams(params_text_bot);

        newFrameBack.addView(textBack);
        newFrameBack.addView(imageViewBack);
        FrontFrame.addView(newFrameBack);


        // Setting camera distance
        float scale = getResources().getDisplayMetrics().density * distance;
        newFrameFront.setCameraDistance(scale);
        newFrameBack.setCameraDistance(scale);


        // Front Card listener
        newFrameFront.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_MOVE:

                        y = motionEvent.getRawY();

                        end_angle = -y*y*0.00025f;

                        if(end_angle >-179) end_angle = -180;
                        if(end_angle <-360) end_angle = -360;

                        if(end_angle > -280) newFrameBack.bringToFront();

                        if(end_angle < -270) {
                            newFrameBack.setVisibility(View.INVISIBLE);
                            newFrameFront.setVisibility(View.VISIBLE);
                            //in_eden.setTarget(mCardBackLayout);
                            //in_eden.start();
                        } else {
                            newFrameBack.setVisibility(View.VISIBLE);
                            newFrameFront.setVisibility(View.INVISIBLE);
                        }

                        //if(end_angle < get_angle_last) end_angle = get_angle_last;
                        // get_angle_prim = end_angle;



                        ObjectAnimator up_animation = ObjectAnimator.ofFloat(newFrameFront, "rotationX", start_angle, end_angle);
                        ObjectAnimator down_animator = ObjectAnimator.ofFloat(newFrameBack, "rotationX", start_angle, end_angle);
                        down_animator.setDuration(1200);
                        down_animator.start();


                        up_animation.setDuration(1200);
                        up_animation.start();

                        start_angle = end_angle;


                        return true;


                }

                return true;
            }
        });

        // Back Card listener
        newFrameBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_MOVE:

                        if(end_angle > -280) newFrameBack.bringToFront();


                        y = motionEvent.getRawY();

                        end_angle = -y*y*0.00025f;
                        //start_angle = old_angle;

                        if(end_angle >-179) end_angle = -180;
                        if(end_angle <-360) end_angle = -360;


                        //if(end_angle >= get_angle_prim) end_angle = get_angle_prim;
                        //get_angle_last = end_angle;
                        //if(end_angle <= get_angle_dynamic) end_angle = get_angle_dynamic;



                        if(end_angle < -270) {
                            newFrameBack.setVisibility(View.INVISIBLE);
                            newFrameFront.setVisibility(View.VISIBLE);

                        } else {
                            newFrameBack.setVisibility(View.VISIBLE);
                            newFrameFront.setVisibility(View.INVISIBLE);
                        }



                        ObjectAnimator up_animation = ObjectAnimator.ofFloat(newFrameFront, "rotationX", start_angle, end_angle);
                        ObjectAnimator down_animator = ObjectAnimator.ofFloat(newFrameBack, "rotationX", start_angle, end_angle);
                        down_animator.setDuration(1200);
                        down_animator.start();
                        up_animation.setDuration(1200);
                        up_animation.start();

                        start_angle = end_angle;


                        return true;

                }

                return true;
            }
        });



    }


    // Second frame
    @SuppressLint("ClickableViewAccessibility")
    private void initializeFrame2() {

        FrameLayout FrontFrame=(FrameLayout) findViewById(R.id.main_frame);

        final FrameLayout newFrameFront = new FrameLayout(this);
        iD_front = generateViewId();
        newFrameFront.setId(iD_front);
        newFrameFront.setBackgroundColor(Color.parseColor("#00FFFF"));
        newFrameFront.setPivotX(0);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        FrameLayout.LayoutParams params_text = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        params.height = 450;
        newFrameFront.setLayoutParams(params);

        ImageView imageViewFront = new ImageView(this);
        imageViewFront.setImageResource(R.drawable.ic_planet_earth);
        imageViewFront.setScaleY(0.75f);
        imageViewFront.setScaleX(0.75f);

        TextView textFront = new TextView(this);
        textFront.setText("IMG #2");
        textFront.setTextSize(16);
        textFront.setPadding(20, 0, 0, 20);
        textFront.setLayoutParams(params_text);

        newFrameFront.addView(textFront);
        newFrameFront.addView(imageViewFront);
        newFrameFront.bringToFront();

        FrontFrame.addView(newFrameFront);


        final FrameLayout newFrameBack = new FrameLayout(this);
        iD_back = generateViewId();
        newFrameBack.setId(iD_back);
        newFrameBack.setLayoutParams(params);
        newFrameBack.setPivotX(0);
        newFrameBack.setBackgroundColor(Color.parseColor("#FFFF00"));
        newFrameBack.setVisibility(View.INVISIBLE);

        ImageView imageViewBack = new ImageView(this);
        imageViewBack.setImageResource(R.drawable.ic_sun);
        imageViewBack.setScaleY(0.75f);
        imageViewBack.setScaleX(0.75f);
        imageViewBack.setRotationX(180);

        FrameLayout.LayoutParams params_text_bot = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.TOP);
        TextView textBack = new TextView(this);
        textBack.setText("IMG #3");
        textBack.setTextSize(16);
        textBack.setRotationX(180);
        textBack.setPadding(20, 0, 0, 20);
        textBack.setLayoutParams(params_text_bot);

        newFrameBack.addView(textBack);
        newFrameBack.addView(imageViewBack);
        FrontFrame.addView(newFrameBack);


        // Setting camera distance
        float scale = getResources().getDisplayMetrics().density * distance;
        newFrameFront.setCameraDistance(scale);
        newFrameBack.setCameraDistance(scale);


        // Front Card listener
        newFrameFront.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_MOVE:

                        y = motionEvent.getRawY();

                        end_angle = -y*y*0.00025f;

                        if(end_angle >-179) end_angle = -180;
                        if(end_angle <-360) end_angle = -360;

                        if(end_angle > -280) newFrameBack.bringToFront();

                        if(end_angle < -270) {
                            newFrameBack.setVisibility(View.INVISIBLE);
                            newFrameFront.setVisibility(View.VISIBLE);
                            //in_eden.setTarget(mCardBackLayout);
                            //in_eden.start();
                        } else {
                            newFrameBack.setVisibility(View.VISIBLE);
                            newFrameFront.setVisibility(View.INVISIBLE);
                        }

                        //if(end_angle < get_angle_last) end_angle = get_angle_last;
                        // get_angle_prim = end_angle;



                        ObjectAnimator up_animation = ObjectAnimator.ofFloat(newFrameFront, "rotationX", start_angle, end_angle);
                        ObjectAnimator down_animator = ObjectAnimator.ofFloat(newFrameBack, "rotationX", start_angle, end_angle);
                        down_animator.setDuration(1200);
                        down_animator.start();


                        up_animation.setDuration(1200);
                        up_animation.start();

                        start_angle = end_angle;


                        return true;


                }

                return true;
            }
        });

        // Back Card listener
        newFrameBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_MOVE:

                        if(end_angle > -280) newFrameBack.bringToFront();


                        y = motionEvent.getRawY();

                        end_angle = -y*y*0.00025f;
                        //start_angle = old_angle;

                        if(end_angle >-179) end_angle = -180;
                        if(end_angle <-360) end_angle = -360;


                        //if(end_angle >= get_angle_prim) end_angle = get_angle_prim;
                        //get_angle_last = end_angle;
                        //if(end_angle <= get_angle_dynamic) end_angle = get_angle_dynamic;



                        if(end_angle < -270) {
                            newFrameBack.setVisibility(View.INVISIBLE);
                            newFrameFront.setVisibility(View.VISIBLE);

                        } else {
                            newFrameBack.setVisibility(View.VISIBLE);
                            newFrameFront.setVisibility(View.INVISIBLE);
                        }



                        ObjectAnimator up_animation = ObjectAnimator.ofFloat(newFrameFront, "rotationX", start_angle, end_angle);
                        ObjectAnimator down_animator = ObjectAnimator.ofFloat(newFrameBack, "rotationX", start_angle, end_angle);
                        down_animator.setDuration(1200);
                        down_animator.start();
                        up_animation.setDuration(1200);
                        up_animation.start();

                        start_angle = end_angle;


                        return true;

                }

                return true;
            }
        });



    }



    // Third frame
    @SuppressLint("ClickableViewAccessibility")
    private void initializeFrame3() {

        FrameLayout FrontFrame=(FrameLayout) findViewById(R.id.main_frame);

        final FrameLayout newFrameFront = new FrameLayout(this);
        iD_front = generateViewId();
        newFrameFront.setId(iD_front);
        newFrameFront.setBackgroundColor(Color.parseColor("#9F00FF"));
        newFrameFront.setPivotX(0);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        FrameLayout.LayoutParams params_text = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        params.height = 450;
        newFrameFront.setLayoutParams(params);

        ImageView imageViewFront = new ImageView(this);
        imageViewFront.setImageResource(R.drawable.ic_rocket);
        imageViewFront.setScaleY(0.75f);
        imageViewFront.setScaleX(0.75f);

        TextView textFront = new TextView(this);
        textFront.setText("IMG #4");
        textFront.setTextSize(16);
        textFront.setPadding(20, 0, 0, 20);
        textFront.setLayoutParams(params_text);

        newFrameFront.addView(textFront);
        newFrameFront.addView(imageViewFront);
        newFrameFront.bringToFront();

        FrontFrame.addView(newFrameFront);


        final FrameLayout newFrameBack = new FrameLayout(this);
        iD_back = generateViewId();
        newFrameBack.setId(iD_back);
        newFrameBack.setLayoutParams(params);
        newFrameBack.setPivotX(0);
        newFrameBack.setBackgroundColor(Color.parseColor("#FF4C4C"));
        newFrameBack.setVisibility(View.INVISIBLE);

        ImageView imageViewBack = new ImageView(this);
        imageViewBack.setImageResource(R.drawable.ic_molecule);
        imageViewBack.setScaleY(0.75f);
        imageViewBack.setScaleX(0.75f);
        imageViewBack.setRotationX(180);

        FrameLayout.LayoutParams params_text_bot = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.TOP);
        TextView textBack = new TextView(this);
        textBack.setText("IMG #5");
        textBack.setTextSize(16);
        textBack.setRotationX(180);
        textBack.setPadding(20, 0, 0, 20);
        textBack.setLayoutParams(params_text_bot);

        newFrameBack.addView(textBack);
        newFrameBack.addView(imageViewBack);
        FrontFrame.addView(newFrameBack);


        // Setting camera distance
        float scale = getResources().getDisplayMetrics().density * distance;
        newFrameFront.setCameraDistance(scale);
        newFrameBack.setCameraDistance(scale);


        // Front Card listener
        newFrameFront.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_MOVE:

                        y = motionEvent.getRawY();

                        end_angle = -y*y*0.00025f;

                        if(end_angle >-179) end_angle = -180;
                        if(end_angle <-360) end_angle = -360;

                        if(end_angle > -280) newFrameBack.bringToFront();

                        if(end_angle < -270) {
                            newFrameBack.setVisibility(View.INVISIBLE);
                            newFrameFront.setVisibility(View.VISIBLE);

                        } else {
                            newFrameBack.setVisibility(View.VISIBLE);
                            newFrameFront.setVisibility(View.INVISIBLE);
                        }

                        //if(end_angle < get_angle_last) end_angle = get_angle_last;
                        // get_angle_prim = end_angle;



                        ObjectAnimator up_animation = ObjectAnimator.ofFloat(newFrameFront, "rotationX", start_angle, end_angle);
                        ObjectAnimator down_animator = ObjectAnimator.ofFloat(newFrameBack, "rotationX", start_angle, end_angle);
                        down_animator.setDuration(1200);
                        down_animator.start();


                        up_animation.setDuration(1200);
                        up_animation.start();

                        start_angle = end_angle;


                        return true;


                }

                return true;
            }
        });

        // Back Card listener
        newFrameBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_MOVE:

                        if(end_angle > -280) newFrameBack.bringToFront();


                        y = motionEvent.getRawY();

                        end_angle = -y*y*0.00025f;
                        //start_angle = old_angle;

                        if(end_angle >-179) end_angle = -180;
                        if(end_angle <-360) end_angle = -360;


                        //if(end_angle >= get_angle_prim) end_angle = get_angle_prim;
                        //get_angle_last = end_angle;
                        //if(end_angle <= get_angle_dynamic) end_angle = get_angle_dynamic;



                        if(end_angle < -270) {
                            newFrameBack.setVisibility(View.INVISIBLE);
                            newFrameFront.setVisibility(View.VISIBLE);
                            //in_eden.setTarget(mCardBackLayout);
                            //in_eden.start();
                        } else {
                            newFrameBack.setVisibility(View.VISIBLE);
                            newFrameFront.setVisibility(View.INVISIBLE);
                        }



                        ObjectAnimator up_animation = ObjectAnimator.ofFloat(newFrameFront, "rotationX", start_angle, end_angle);
                        ObjectAnimator down_animator = ObjectAnimator.ofFloat(newFrameBack, "rotationX", start_angle, end_angle);
                        down_animator.setDuration(1200);
                        down_animator.start();
                        up_animation.setDuration(1200);
                        up_animation.start();

                        start_angle = end_angle;


                        return true;

                }

                return true;
            }
        });



    }




    // Fourth frame
    @SuppressLint("ClickableViewAccessibility")
    private void initializeFrame4() {

        FrameLayout FrontFrame=(FrameLayout) findViewById(R.id.main_frame);

        final FrameLayout newFrameFront = new FrameLayout(this);
        iD_front = generateViewId();
        newFrameFront.setId(iD_front);
        newFrameFront.setBackgroundColor(Color.parseColor("#e6e600"));
        newFrameFront.setPivotX(0);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        FrameLayout.LayoutParams params_text = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
        params.height = 450;
        newFrameFront.setLayoutParams(params);

        ImageView imageViewFront = new ImageView(this);
        imageViewFront.setImageResource(R.drawable.ic_bike);
        imageViewFront.setScaleY(0.75f);
        imageViewFront.setScaleX(0.75f);

        TextView textFront = new TextView(this);
        textFront.setText("IMG #6");
        textFront.setTextSize(16);
        textFront.setPadding(20, 0, 0, 20);
        textFront.setLayoutParams(params_text);

        newFrameFront.addView(textFront);
        newFrameFront.addView(imageViewFront);
        newFrameFront.bringToFront();

        FrontFrame.addView(newFrameFront);


        final FrameLayout newFrameBack = new FrameLayout(this);
        iD_back = generateViewId();
        newFrameBack.setId(iD_back);
        newFrameBack.setLayoutParams(params);
        newFrameBack.setPivotX(0);
        newFrameBack.setBackgroundColor(Color.parseColor("#0080ff"));
        newFrameBack.setVisibility(View.INVISIBLE);

        ImageView imageViewBack = new ImageView(this);
        imageViewBack.setImageResource(R.drawable.ic_car);
        imageViewBack.setScaleY(0.75f);
        imageViewBack.setScaleX(0.75f);
        imageViewBack.setRotationX(180);

        FrameLayout.LayoutParams params_text_bot = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.TOP);
        TextView textBack = new TextView(this);
        textBack.setText("IMG #7");
        textBack.setTextSize(16);
        textBack.setRotationX(180);
        textBack.setPadding(20, 0, 0, 20);
        textBack.setLayoutParams(params_text_bot);

        newFrameBack.addView(textBack);
        newFrameBack.addView(imageViewBack);
        FrontFrame.addView(newFrameBack);


        // Setting camera distance
        float scale = getResources().getDisplayMetrics().density * distance;
        newFrameFront.setCameraDistance(scale);
        newFrameBack.setCameraDistance(scale);


        // Front Card listener
        newFrameFront.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_MOVE:

                        y = motionEvent.getRawY();

                        end_angle = -y*y*0.00025f;

                        if(end_angle >-179) end_angle = -180;
                        if(end_angle <-360) end_angle = -360;

                        if(end_angle > -280) newFrameBack.bringToFront();

                        if(end_angle < -270) {
                            newFrameBack.setVisibility(View.INVISIBLE);
                            newFrameFront.setVisibility(View.VISIBLE);

                        } else {
                            newFrameBack.setVisibility(View.VISIBLE);
                            newFrameFront.setVisibility(View.INVISIBLE);
                        }

                        //if(end_angle < get_angle_last) end_angle = get_angle_last;
                        // get_angle_prim = end_angle;



                        ObjectAnimator up_animation = ObjectAnimator.ofFloat(newFrameFront, "rotationX", start_angle, end_angle);
                        ObjectAnimator down_animator = ObjectAnimator.ofFloat(newFrameBack, "rotationX", start_angle, end_angle);
                        down_animator.setDuration(1200);
                        down_animator.start();


                        up_animation.setDuration(1200);
                        up_animation.start();

                        start_angle = end_angle;


                        return true;


                }

                return true;
            }
        });

        // Back Card listener
        newFrameBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_MOVE:

                        if(end_angle > -280) newFrameBack.bringToFront();


                        y = motionEvent.getRawY();

                        end_angle = -y*y*0.00025f;
                        //start_angle = old_angle;

                        if(end_angle >-179) end_angle = -180;
                        if(end_angle <-360) end_angle = -360;


                        //if(end_angle >= get_angle_prim) end_angle = get_angle_prim;
                        //get_angle_last = end_angle;
                        //if(end_angle <= get_angle_dynamic) end_angle = get_angle_dynamic;



                        if(end_angle < -270) {
                            newFrameBack.setVisibility(View.INVISIBLE);
                            newFrameFront.setVisibility(View.VISIBLE);
                            //in_eden.setTarget(mCardBackLayout);
                            //in_eden.start();
                        } else {
                            newFrameBack.setVisibility(View.VISIBLE);
                            newFrameFront.setVisibility(View.INVISIBLE);
                        }



                        ObjectAnimator up_animation = ObjectAnimator.ofFloat(newFrameFront, "rotationX", start_angle, end_angle);
                        ObjectAnimator down_animator = ObjectAnimator.ofFloat(newFrameBack, "rotationX", start_angle, end_angle);
                        down_animator.setDuration(1200);
                        down_animator.start();
                        up_animation.setDuration(1200);
                        up_animation.start();

                        start_angle = end_angle;


                        return true;

                }

                return true;
            }
        });



    }





    public synchronized int generateViewId() {
        Random rand = new Random();
        int id;
        while (findViewById(id = rand.nextInt(Integer.MAX_VALUE) + 1) != null);
        return id;
    }


}
