package com.albert.androidatlas.ui_core_125;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PointFEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.albert.androidatlas.R;

/**
 * Filename : DragBubbleView.java
 *
 * @author : Mihawk
 * @since : 2019-06-08
 */

public class DragBubbleView extends View {

    private static final String TAG = DragBubbleView.class.getCanonicalName();
    /**
     * 气泡默认状态--静止
     */
    private final int BUBBLE_STATE_DEFAULT = 0;
    /**
     * 气泡相连
     */
    private final int BUBBLE_STATE_CONNECT = 1;
    /**
     * 气泡分离
     */
    private final int BUBBLE_STATE_APART = 2;
    /**
     * 气泡消失
     */
    private final int BUBBLE_STATE_DISMISS = 3;
    /**
     * 手指触摸偏移量,增加点击区域
     */
    private float MOVE_OFFSET;
    /**
     * 气泡半径
     */
    private float mBubbleRadius;
    /**
     * 气泡颜色
     */
    private int mBubbleColor;
    /**
     * 气泡消息文字
     */
    private String mTextStr;
    /**
     * 气泡消息文字颜色
     */
    private int mTextColor;
    /**
     * 气泡消息文字大小
     */
    private float mTextSize;
    /**
     * 不动气泡的半径
     */
    private float mBubFixedRadius;
    /**
     * 可动气泡的半径
     */
    private float mBubMovableRadius;
    /**
     * 不动气泡的圆心
     */
    private PointF mBubFixedCenter;
    /**
     * 可动气泡的圆心
     */
    private PointF mBubMovableCenter;
    /**
     * 气泡的画笔
     */
    private Paint mBubblePaint;
    /**
     * 贝塞尔曲线path
     */
    private Path mBezierPath;
    private Paint mTextPaint;
    //文本绘制区域
    private Rect mTextRect;
    private Paint mBurstPaint;
    //爆炸绘制区域
    private Rect mBurstRect;
    /**
     * 气泡状态标志
     */
    private int mBubbleState = BUBBLE_STATE_DEFAULT;
    /**
     * 两气泡圆心距离
     */
    private float mDist;
    /**
     * 气泡相连状态最大圆心距离
     */
    private float mMaxDist;
    /**
     * 气泡爆炸的bitmap数组
     */
    private Bitmap[] mBurstBitmapsArray;
    /**
     * 是否在执行气泡爆炸动画
     */
    private boolean mIsBurstAnimStart = false;

    /**
     * 当前气泡爆炸图片index
     */
    private int mCurDrawableIndex;

    /**
     * 气泡爆炸的图片id数组
     */
    private int[] mBurstDrawablesArray = {R.drawable.burst_1, R.drawable.burst_2, R.drawable.burst_3, R.drawable.burst_4, R.drawable.burst_5};


    public DragBubbleView(Context context) {
        this(context, null);
    }

    public DragBubbleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragBubbleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs, defStyleAttr);
    }

    //初始状态
    private void initView(AttributeSet attrs, int defStyleAttr) {

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.DragBubbleView, defStyleAttr, 0);
        mBubbleRadius = array.getDimension(R.styleable.DragBubbleView_bubble_radius, mBubbleRadius);
        mBubbleColor = array.getColor(R.styleable.DragBubbleView_bubble_color, Color.RED);
        mTextStr = array.getString(R.styleable.DragBubbleView_bubble_text);
        mTextSize = array.getDimension(R.styleable.DragBubbleView_bubble_textSize, mTextSize);
        mTextColor = array.getColor(R.styleable.DragBubbleView_bubble_textColor, Color.WHITE);
        array.recycle();

        //两个圆半径大小一致
        mBubFixedRadius = mBubbleRadius;
        mBubMovableRadius = mBubFixedRadius;
        mMaxDist = 8 * mBubbleRadius;

        MOVE_OFFSET = mMaxDist / 4;

        //抗锯齿
        mBubblePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBubblePaint.setColor(mBubbleColor);
        mBubblePaint.setStyle(Paint.Style.FILL);
        mBezierPath = new Path();

        //文本画笔
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mTextRect = new Rect();

        //爆炸画笔
        mBurstPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBurstPaint.setFilterBitmap(true);
        mBurstRect = new Rect();
        mBurstBitmapsArray = new Bitmap[mBurstDrawablesArray.length];
        for (int i = 0; i < mBurstDrawablesArray.length; i++) {
            //将气泡爆炸的drawable转为bitmap
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mBurstDrawablesArray[i]);
            mBurstBitmapsArray[i] = bitmap;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //不动气泡圆心
        if (mBubFixedCenter == null) {
            mBubFixedCenter = new PointF(w / 2, h / 2);
        } else {
            mBubFixedCenter.set(w / 2, h / 2);
        }

        //可动气泡圆心
        if (mBubMovableCenter == null) {
            mBubMovableCenter = new PointF(w / 2, h / 2);
        } else {
            mBubMovableCenter.set(w / 2, h / 2);
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBubbleState != BUBBLE_STATE_DISMISS) {
            canvas.drawCircle(mBubMovableCenter.x, mBubMovableCenter.y, mBubMovableRadius, mBubblePaint);
            mTextPaint.getTextBounds(mTextStr, 0, mTextStr.length(), mTextRect);
            canvas.drawText(mTextStr, mBubMovableCenter.x - mTextRect.width() / 2,
                    mBubMovableCenter.y + mTextRect.height() / 2, mTextPaint);

        }

        if (mBubbleState == BUBBLE_STATE_CONNECT) {
            //绘制不动气泡
            canvas.drawCircle(mBubFixedCenter.x, mBubFixedCenter.y, mBubFixedRadius, mBubblePaint);

            //绘制贝塞尔曲线

            //控制点坐标
            int iAnchorX = (int) ((mBubFixedCenter.x + mBubMovableCenter.x) / 2);
            int iAnchorY = (int) ((mBubFixedCenter.y + mBubMovableCenter.y) / 2);

            //三阶控制点坐标1
            int iAnchorX1 = (int) ((mBubFixedCenter.x - mBubMovableCenter.x) / 3 + mBubMovableCenter.x);
            int iAnchorY1 = (int) ((mBubFixedCenter.y - mBubMovableCenter.y) / 3 + mBubMovableCenter.y);
            //三阶控制点坐标2
            int iAnchorX2 = (int) ((mBubFixedCenter.x - mBubMovableCenter.x) / 3 * 2 + mBubMovableCenter.x);
            int iAnchorY2 = (int) ((mBubFixedCenter.y - mBubMovableCenter.y) / 3 * 2 + mBubMovableCenter.y);

            float sinTheta = (mBubMovableCenter.y - mBubFixedCenter.y) / mDist;
            float cosTheta = (mBubMovableCenter.x - mBubFixedCenter.x) / mDist;

            //B
            float iBubMovableStartX = mBubMovableCenter.x + sinTheta * mBubMovableRadius;
            float iBubMovableStartY = mBubMovableCenter.y - cosTheta * mBubMovableRadius;

            //A
            float iBubFixedEndX = mBubFixedCenter.x + sinTheta * mBubFixedRadius;
            float iBubFixedEndY = mBubFixedCenter.y - cosTheta * mBubFixedRadius;

            //D
            float iBubFixedStartX = mBubFixedCenter.x - mBubFixedRadius * sinTheta;
            float iBubFixedStartY = mBubFixedCenter.y + mBubFixedRadius * cosTheta;
            //C
            float iBubMovableEndX = mBubMovableCenter.x - mBubMovableRadius * sinTheta;
            float iBubMovableEndY = mBubMovableCenter.y + mBubMovableRadius * cosTheta;

            mBezierPath.reset();
            mBezierPath.moveTo(iBubFixedStartX, iBubFixedStartY);
//            mBezierPath.quadTo(iAnchorX, iAnchorY, iBubMovableEndX, iBubMovableEndY);
            mBezierPath.cubicTo(iAnchorX2, iAnchorY2, iAnchorX1, iAnchorY1, iBubMovableEndX, iBubMovableEndY);

            mBezierPath.lineTo(iBubMovableStartX, iBubMovableStartY);
//            mBezierPath.quadTo(iAnchorX, iAnchorY, iBubFixedEndX, iBubFixedEndY);
            mBezierPath.cubicTo(iAnchorX1, iAnchorY1, iAnchorX2, iAnchorY2, iBubFixedEndX, iBubFixedEndY);
            mBezierPath.close();
            canvas.drawPath(mBezierPath, mBubblePaint);

            Paint p1 = new Paint();
            p1.setColor(Color.BLUE);
            Paint p2 = new Paint();
            p2.setColor(Color.GREEN);
            canvas.drawCircle(iAnchorX1, iAnchorY1, 3, p1);
            canvas.drawCircle(iAnchorX2, iAnchorY2, 3, p2);
            canvas.drawCircle(iAnchorX, iAnchorY, 5, p1);
            canvas.drawCircle(mBubMovableCenter.x, mBubMovableCenter.y, 5, p2);

//            Log.d(TAG, "mBubMovableCenter: " + mBubMovableCenter.x + "~~~" + mBubMovableCenter.y);
//            Log.d(TAG, "mBubFixedCenter: " + mBubFixedCenter.x + "~~~" + mBubFixedCenter.y);
//            Log.d(TAG, "iAnchor: " + iAnchorX + "~~~" + iAnchorY);
//            Log.d(TAG, "iAnchor1: " + iAnchorX1 + "~~~" + iAnchorY1);
//            Log.d(TAG, "iAnchor2: " + iAnchorX2 + "~~~" + iAnchorY2);

        }

        if (mBubbleState == BUBBLE_STATE_DISMISS && mCurDrawableIndex < mBurstDrawablesArray.length) {
            mBurstRect.set(
                    (int) (mBubMovableCenter.x - mBubMovableRadius),
                    (int) (mBubMovableCenter.y - mBubMovableRadius),
                    (int) (mBubMovableCenter.x + mBubMovableRadius),
                    (int) (mBubMovableCenter.y + mBubMovableRadius)
            );
            canvas.drawBitmap(mBurstBitmapsArray[mCurDrawableIndex], null, mBurstRect, mBurstPaint);
        }


        //1.静止状态 一个气泡加消息数

        //2.连接状态，一个气泡加消息数，贝塞尔曲线，本身位置上的小球，而且大小变化

        //3.分离状态，气泡加消息数据

        //4.消失状态，爆炸效果


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mBubbleState != BUBBLE_STATE_DISMISS) {
                    mDist = (float) Math.hypot(event.getX() - mBubFixedCenter.x, event.getY() - mBubFixedCenter.y);
                    if (mDist < mBubbleRadius + MOVE_OFFSET) {
                        mBubbleState = BUBBLE_STATE_CONNECT;
                    } else {
                        mBubbleState = BUBBLE_STATE_DEFAULT;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mBubbleState != BUBBLE_STATE_DEFAULT) {
                    mDist = (float) Math.hypot(event.getX() - mBubFixedCenter.x, event.getY() - mBubFixedCenter.y);
                    mBubMovableCenter.x = event.getX();
                    mBubMovableCenter.y = event.getY();
                    if (mBubbleState == BUBBLE_STATE_CONNECT) {
                        if (mDist < mMaxDist - MOVE_OFFSET) {
                            //当拖拽的距离在指定范围内，那么调整不动气泡的半径
                            mBubFixedRadius = mBubMovableRadius - mDist / 8;
                        } else {
                            //当拖拽的距离超过范围，改为分离状态
                            mBubbleState = BUBBLE_STATE_APART;
                        }
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mBubbleState == BUBBLE_STATE_CONNECT) {
                    // 橡皮筋动画效果
                    startBubbleRestAnim();
                } else if (mBubbleState == BUBBLE_STATE_APART) {
                    if (mDist < mBubbleRadius * 2) {
                        startBubbleRestAnim();
                    } else {
                        //爆炸效果
                        startBubbleBurstAnim();
                    }
                }
                break;

        }
        return true;
    }

    private void startBubbleBurstAnim() {

        mBubbleState = BUBBLE_STATE_DISMISS;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, mBurstBitmapsArray.length);
        valueAnimator.setDuration(500);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurDrawableIndex = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    private void startBubbleRestAnim() {
        final ValueAnimator anim = ValueAnimator.ofObject(new PointFEvaluator(),
                new PointF(mBubMovableCenter.x, mBubMovableCenter.y),
                new PointF(mBubFixedCenter.x, mBubFixedCenter.y));
        anim.setDuration(200);
        anim.setInterpolator(new OvershootInterpolator(5f));
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBubMovableCenter = (PointF) animation.getAnimatedValue();
                invalidate();
            }
        });

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mBubbleState = BUBBLE_STATE_DEFAULT;
            }
        });
        anim.start();
    }

    public void reset() {
        mBubbleState = BUBBLE_STATE_DEFAULT;
        requestLayout();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}
