package androidtermproject.christmastermproject;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public int screenWidth;
    public int screenHeight;

    final int GIFTA = R.drawable.gift_a;
    final int GIFTB = R.drawable.gift_b;
    final int GIFTC = R.drawable.gift_c;
    final int GIFTD = R.drawable.gift_d;
    final int GIFTE = R.drawable.gift_e;
    final int BOMB = R.drawable.bomb;

    public float position;
    public int point = 0;

    RelativeLayout screen;
    ImageView santa;
    TextView scoreView;

    Sensor sensor;
    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Prevent Lock Screen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Hide Action Bar
        getSupportActionBar().hide();

        // Find Width and Height of Screen
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;

        // Bind
        screen = (RelativeLayout)findViewById(R.id.activity_main);
        santa = (ImageView) findViewById(R.id.santa);
        scoreView = (TextView) findViewById(R.id.score);

        // Call Sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Generate Thread for Animation Gift
        for (int i = 0; i < 50; i++) {
            // Create ImageView for Gift
            final ImageView newImage = new ImageView(this);
            newImage.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            screen.addView(newImage);

            // Create for Running Animation
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    int ran = new Random().nextInt(6);
                    int type;
                    switch (ran) {
                        case 1:
                            type = GIFTA;
                            break;
                        case 2:
                            type = GIFTB;
                            break;
                        case 3:
                            type = GIFTC;
                            break;
                        case 4:
                            type = GIFTD;
                            break;
                        case 5:
                            type = GIFTE;
                            break;
                        default:
                            type = BOMB;
                            break;
                    }
                    startAnimation(type, newImage);
                }
            });
            thread1.start();
        }


    }

    public void onResume() {
        super.onResume();
        sensorManager.registerListener(gyroListener, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    public void onStop() {
        super.onStop();
        sensorManager.unregisterListener(gyroListener);
    }

    public SensorEventListener gyroListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];

            System.out.println("TRACK " + x);

            santa.setX(santa.getX() - x * 10);
            if (santa.getX() < 0) {
                santa.setX(0);
            }
            if (santa.getX() > screenWidth - 200) {
                santa.setX(screenWidth - 200);
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public void startAnimation(final int type, final ImageView imageView) {
        //Random X position of Image
        final float randWidth = new Random().nextFloat() * screenWidth;
        imageView.setImageDrawable(getResources().getDrawable(type));
        imageView.setX(randWidth);

        //Random Duration of falling
        final long duration = new Random().nextInt(2000) + 2000;

        //Move
        final Animation anim = new TranslateAnimation(randWidth, randWidth, 0, screenHeight);
        anim.setDuration(duration);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                position = randWidth;
                showPoint(randWidth, type);

                //Wait for continue
                anim.setStartOffset(duration);
                //Start next round
                imageView.startAnimation(anim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //First Start
        imageView.startAnimation(anim);

    }

    void showPoint(float index, int type) {
        if (index >= santa.getX() - 120 && index <= santa.getX() + 120) {

            if (type == BOMB) {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("score", point);
                startActivity(intent);
            } else {
                point++;
                scoreView.setText(Integer.toString(point));
            }
        }
    }
}
