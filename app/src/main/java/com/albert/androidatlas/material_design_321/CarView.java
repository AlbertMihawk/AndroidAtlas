package com.albert.androidatlas.material_design_321;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.albert.androidatlas.R;

/**
 * Filename : CarView.java
 *
 * @author : Mihawk
 * @since : 2019-06-30
 */

public class CarView extends View {
    private Bitmap carBitmap;

    private Path path;
    private PathMeasure pathMeasure;//路径计算
    private float distanceRatio = 0;//进度
    private Paint circlePaint;//画圆圈画笔
    private Paint carPaint;//画小车的画笔
    private Matrix carMatrix;//针对 car Bitmap图片操作的矩阵

    public CarView(Context context) {
        this(context, null);

    }

    public CarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(Color.BLACK);
        circlePaint.setStrokeWidth(10);
        carBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_car);
        path = new Path();
        path.moveTo(0, 480);
        path.cubicTo(0, 480, 270, 480, 270, 0);
        path.cubicTo(270, 0, 270, -480, 0, -480);
        path.cubicTo(0, -480, -270, -480, -270, 0);
        path.cubicTo(-270, 0, -270, 480, 0, 480);
        path.close();


        carPaint = new Paint();

        pathMeasure = new PathMeasure(path, true);

        carMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        canvas.translate(width / 2, height / 2);
        canvas.drawPath(path, circlePaint);


        carMatrix.reset();
        distanceRatio += 0.006f;
        if (distanceRatio >= 1) {
            distanceRatio = 0;
        }
        float[] pos = new float[2];
        float[] tan = new float[2];
        float distance = pathMeasure.getLength() * distanceRatio;
        pathMeasure.getPosTan(distance, pos, tan);


        float degree = (float) (Math.atan2(tan[1], tan[0]) * 180 / Math.PI);
        //汽车朝向,偏移中心为小车中心
        carMatrix.postRotate(180+degree, carBitmap.getWidth() / 2,
                carBitmap.getHeight() / 6*5);

        //设置移动小车的中心点
        carMatrix.postTranslate(pos[0] - carBitmap.getWidth() / 2,
                pos[1] - carBitmap.getHeight() / 6*5);


        canvas.drawBitmap(carBitmap, carMatrix, carPaint);

        invalidate();
    }
}
