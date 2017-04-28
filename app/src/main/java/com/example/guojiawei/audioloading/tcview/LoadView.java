package com.example.guojiawei.audioloading.tcview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by guojiawei on 2017/4/27.
 */

public class LoadView extends ViewGroup {
    private int height;
    private int width;

    private int childWidth;
    private GeometryView geometryView;
    private View lineView;
    private int lineColor = 0xffd6d6db;
    private int padding = 10;

    private int bottomJump;
    private int topJump;
    int i = 0;

    public LoadView(Context context) {
        super(context);

    }

    public LoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        startDropAnim();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() > 0) {

            if (getChildAt(0) != null) {
                View childGeometry = getChildAt(0);
                childGeometry.layout(width / 2 - childWidth / 2, 0 + padding, width / 2 + childWidth / 2, childWidth);
            }
            if (getChildAt(1) != null) {
                View childLine = getChildAt(1);
                childLine.layout(width / 2 - childLine.getMeasuredWidth() / 4, height - childLine.getMeasuredHeight() - padding, width / 2 + childLine.getMeasuredWidth() / 4, height - padding);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;
        initChildView();
        topJump = 0 + padding;
        bottomJump = height - childWidth - 10 - padding;
    }

    private void initChildView() {
        childWidth = width / 2;
        geometryView = new GeometryView(getContext());
        geometryView.setLayoutParams(new LayoutParams(childWidth, childWidth));
        addView(geometryView);

        lineView = new View(getContext());
        lineView.setLayoutParams(new LayoutParams(childWidth, 8));
        lineView.setBackgroundColor(lineColor);
        addView(lineView);
    }

    private void startJumpAnim() {
        final AnimatorSet set = new AnimatorSet();

        ObjectAnimator jump = ObjectAnimator.ofFloat(geometryView, "translationY", bottomJump, topJump);
        jump.setInterpolator(new DecelerateInterpolator(1.2f));

        ObjectAnimator scalex = ObjectAnimator.ofFloat(lineView, "scaleX", 1.5f, 0.5f);
        scalex.setInterpolator(new DecelerateInterpolator());

        ObjectAnimator rotat = ObjectAnimator.ofFloat(geometryView, "rotation", 0f, 180f);
        rotat.setInterpolator(new DecelerateInterpolator(1.2f));


        set.play(jump).with(scalex).with(rotat);
        set.setDuration(500);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startDropAnim();
            }
        });
    }

    private void startDropAnim() {
        final ObjectAnimator drop = ObjectAnimator.ofFloat(geometryView, "translationY", topJump, bottomJump);
        drop.setDuration(500);
        drop.setInterpolator(new AccelerateInterpolator(1.2f));
        drop.start();
        drop.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                switchShape();
                startJumpAnim();
            }
        });
    }


    private void switchShape() {
        if (i < 3) {
            geometryView.switchShape(i);
            i++;
        } else {
            i = 1;
            geometryView.switchShape(0);
        }
    }


}
