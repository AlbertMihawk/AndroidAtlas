package com.albert.androidatlas.ui_123;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.albert.androidatlas.R;

import java.util.ArrayList;
import java.util.List;

/**
 * FileName : SplitView.java
 *
 * @author : Mihawk
 * @since : 2019-06-07
 */

public class SplitView extends View {

    private Bitmap mBitmap;
    private final float Diameter = 3;
    //    private final float Radius = Diameter/2;
    private List<Particle> mParticles = new ArrayList<>();
    private Paint mPaint;
    private ValueAnimator mAnimator;

    public SplitView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        mPaint = new Paint();

        BitmapFactory.Options options = new BitmapFactory.Options();

        //仅仅绘制边框
        options.inJustDecodeBounds = true;
        Bitmap des_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mihawk, options);

        //缩放绘制图片
        options.inJustDecodeBounds = false;
        int height = options.outHeight;
        int width = options.outWidth;
        int scale;
        if (height > width) {//以高为准
            scale = (int) (height / 40.0f);//该200说明目标大小为200PX
        } else {
            scale = (int) (width / 40.0f);//该200说明目标大小为200PX
        }
        if (scale <= 0) {
            scale = 1;
        }
        options.inSampleSize = scale;

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mihawk, options);
        for (int i = 0; i < mBitmap.getWidth(); i++) {
            for (int j = 0; j < mBitmap.getHeight(); j++) {
                Particle p = new Particle();
                p.color = mBitmap.getPixel(i, j);
                if (p.color == 0) continue;
                p.r = Diameter / 2;
                p.x = i * Diameter + Diameter / 2;
                p.y = j * Diameter + Diameter / 2;

                //velocity
//                p.vx = (float) (Math.random() * 20 * Math.pow(-1, Math.ceil(Math.random() * 1000)));
                p.vx = (float) (Math.random() * 20 * ((Math.random() * 2 - 1) > 0 ? 1 : -1));
                p.vy = (float) (-16 + Math.ceil(Math.random() * (51)));
//                p.vy = rangInt(-15, 35);

                //accelerate
                p.ax = 0;
                p.ay = 0.98f;

                mParticles.add(p);
            }
        }
        //属性动画
        mAnimator = ValueAnimator.ofFloat(0, 1);
        mAnimator.setRepeatCount(-1);
        mAnimator.setDuration(2000);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateParticle();
                invalidate();
            }
        });


    }

    private void updateParticle() {
        for (Particle particle : mParticles) {
            particle.x += particle.vx;
            particle.y += particle.vy;
            particle.vx += particle.ax;
            particle.vy += particle.ay;
        }
    }

    //简化了计算公式
    @Deprecated
    private int rangInt(int i, int j) {
        int max = Math.max(i, j);
        int min = Math.min(i, j) - 1;
        //在0到(max - min)范围内变化，取大于x的最小整数 再随机
        return (int) (min + Math.ceil(Math.random() * (max - min)));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //得到window尺寸
        Point point = new Point();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getSize(point);

        //测量用
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < 19; i++) {
            canvas.drawRect(0 * 100, i * 100, (1) * 100, (i + 1) * 100, mPaint);

        }

        canvas.translate(point.x / 2 - mBitmap.getWidth() * Diameter / 2, point.y / 2 - mBitmap.getHeight() * Diameter / 2 - 400);
        for (Particle particle : mParticles) {
            mPaint.setColor(particle.color);
            //绘制图像的圆点
//            canvas.drawCircle(particle.x, particle.y, particle.r, mPaint);
            canvas.drawPoint(particle.x,particle.y,mPaint);
        }
//        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!mAnimator.isStarted() || mAnimator.isPaused()) mAnimator.start();
//            if (mAnimator.isRunning()) mAnimator.pause();
        }
        return super.onTouchEvent(event);
    }

}
