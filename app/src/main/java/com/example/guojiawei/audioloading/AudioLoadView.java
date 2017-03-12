package com.example.guojiawei.audioloading;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by guojiawei on 2017/3/4.
 */

public class AudioLoadView extends View {

    private int mViewWidth;
    private int mViewHeight;
    private Paint mAudioLinePaint;
    private int mBeatValue = 0;

    int width;
    int color;

    public AudioLoadView(Context context) {
        super(context);
        initPaint();
    }

    public AudioLoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.audio);

        color = ta.getColor(R.styleable.audio_color, 0x000000);
        initPaint();

    }

    private void initPaint() {
        mAudioLinePaint = new Paint();
        mAudioLinePaint.setColor(color);
        mAudioLinePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w < h) {
            w = h;
        }
        mViewWidth = w;
        mViewHeight = h;

        startAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mAudioLinePaint.setStrokeWidth(mViewHeight / 8);


        int startX = 0 + 10;

        canvas.drawLine(startX, mViewHeight / 4 * 2 + mBeatValue, startX, mViewHeight, mAudioLinePaint);
        canvas.drawLine(startX + mViewWidth / 4, mViewHeight / 4 * 3 - mBeatValue, startX + mViewWidth / 4, mViewHeight, mAudioLinePaint);
        canvas.drawLine(startX + mViewWidth / 4 * 2, mViewHeight / 4 * 1 + mBeatValue, startX + mViewWidth / 4 * 2, mViewHeight, mAudioLinePaint);
        canvas.drawLine(startX + mViewWidth / 4 * 3, mViewHeight / 4 * 3 - mBeatValue, startX + mViewWidth / 4 * 3, mViewHeight, mAudioLinePaint);
    }

    private void startAnim() {
        ValueAnimator valueAnim = ValueAnimator.ofInt(mViewHeight / 4, 0, mViewHeight / 4);
        valueAnim.setInterpolator(new LinearInterpolator());
        valueAnim.setDuration(1000);
        valueAnim.setRepeatCount(-1);
        valueAnim.start();
        valueAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBeatValue = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
    }

}
