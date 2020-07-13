package com.example.ballgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class MyView extends View {
    private Bitmap ball;
    private boolean isInit;
    private int viewW, viewH;
    private float ballW, ballH;
    private float ballX, ballY, dx, dy;
    private Timer timer;

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ball = BitmapFactory.decodeResource(context.getResources(), R.drawable.unnamed);
        timer = new Timer();

    }


    private void init() {
        viewW = getWidth();
        viewH = getHeight();
        ballW = viewW / 8f;
        ballH = viewH / 8f; //將大小縮成手機畫面的1/8
        Matrix matrix = new Matrix();
        matrix.postScale(ballW / ball.getWidth(), ballH / ball.getHeight());
        ball = Bitmap.createBitmap(ball, 0, 0, ball.getWidth(), ball.getHeight(), matrix, false);
        isInit = true;
        timer.schedule(new BallTask(), 1000, 60); //1秒後，每0.06秒動一次球
        dx = 50;
        dy = 50;

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInit)
            init();
        canvas.drawBitmap(ball, ballX, ballY, null);
    }

    private class BallTask extends TimerTask {
        @Override
        public void run() {
            if(ballX <0 || ballX+ballW >viewW){
                dx*=-1;
            }
            if(ballY<0 || ballY+ballH>viewH){
                dy*=-1;
            }
            ballX += dx;
            ballY += dy;
            postInvalidate();
        }
    }
}
