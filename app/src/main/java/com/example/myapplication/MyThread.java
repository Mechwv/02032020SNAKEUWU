package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.Random;

public class MyThread extends Thread {

    //маркер рисования
    private Paint paint;

    //указатель для работы surface view
    private SurfaceHolder holder;

    //флаг для работы с потоком
    //чтобы вывремя его остановить
    private boolean flag;

    public void setRunning(boolean f){
        this.flag = f;
    }
    MyThread(SurfaceHolder h){
        flag = false;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.FILL);
        this.holder = h;
    }
    int x = 0, y = 0;
    boolean move = true;
    boolean movey = true;
    @Override
    public void run() {
        //super.run();
        while(flag){
            //Canvas canvas = null;
            //canvas = holder.lockCanvas();
            Canvas canvas = holder.lockCanvas();
            //canvas.drawColor(Color.WHITE);
            Random r = new Random();
            int color = Color.rgb(r.nextInt(255),r.nextInt(255),r.nextInt(255));
            int height = canvas.getHeight();
            int width = canvas.getWidth();
            int maxradius = Math.min(height,width)/8;
            paint.setColor(color);
            if (x+maxradius>width) {move = false; holder.unlockCanvasAndPost(canvas); canvas = null; canvas = holder.lockCanvas(); canvas.drawColor(Color.WHITE);}
            if (x+maxradius<0) {move = true;  holder.unlockCanvasAndPost(canvas); canvas = null; canvas = holder.lockCanvas(); canvas.drawColor(Color.WHITE);}
            if (y+maxradius>height) {movey = false; holder.unlockCanvasAndPost(canvas);  canvas = null; canvas = holder.lockCanvas(); canvas.drawColor(Color.WHITE);}
            if (y+maxradius<0) {movey = true; holder.unlockCanvasAndPost(canvas); canvas = null; canvas = holder.lockCanvas(); canvas.drawColor(Color.WHITE);}
            if (move){
                x = x + r.nextInt(255);
            }
            if (!move) {
                x = x - r.nextInt(255);
            }
            if (movey){
                y = y + r.nextInt(255);
            }
            if (!movey) {
                y = y - r.nextInt(255);
            }
            Log.i("0000",x+"");
            canvas.drawCircle(x,y,maxradius,paint);
            holder.unlockCanvasAndPost(canvas);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
