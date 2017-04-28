package com.example.guojiawei.audioloading.tcview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by guojiawei on 2017/4/27.
 */

public class GeometryView extends View {

    private final static int CIRCLE = 0;
    private final static int SQUARE = 1;
    private final static int TRIANG = 2;

    private Paint circlePaint;
    private Paint squarePaint;
    private Paint trianglePaint;
    private int circleColor = 0xffa2b4fa;
    private int squareColor = 0xffed8882;
    private int triangleColor = 0xffa0dfa2;
    private int viewWidth = 100;
    private int viewHeight = 100;

    private int switchShape = 0;

    public GeometryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GeometryView(Context context) {
        super(context);
        init();
    }

    private void init() {
        circlePaint = new Paint();
        circlePaint.setColor(circleColor);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.FILL);

        squarePaint = new Paint();
        squarePaint.setColor(squareColor);
        squarePaint.setAntiAlias(true);
        squarePaint.setStyle(Paint.Style.FILL);

        trianglePaint = new Paint();
        trianglePaint.setColor(triangleColor);
        trianglePaint.setAntiAlias(true);
        trianglePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (switchShape == CIRCLE) {
            drawCircle(canvas);
        }
        if (switchShape == SQUARE) {
            drawSquare(canvas);
        }
        if (switchShape == TRIANG) {
            drawTriangle(canvas);
        }
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(viewWidth / 2, viewHeight / 2, viewWidth / 2 - 5, circlePaint);
    }

    private void drawSquare(Canvas canvas) {
        int left = 0;
        int top = 0;
        int right = viewWidth;
        int bottom = viewHeight;
        canvas.drawRect(left, top, right, bottom, squarePaint);

    }

    private void drawTriangle(Canvas canvas) {
        Path path = new Path();
        path.moveTo(viewWidth / 2, 0);
        path.lineTo(0, viewHeight);
        path.lineTo(viewWidth, viewHeight);
        path.close();
        canvas.drawPath(path, trianglePaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        viewHeight = h;
        viewWidth = w;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(w, viewWidth), Math.min(h, viewHeight));
    }

    public void switchShape(int tag) {
        switch (tag) {
            case CIRCLE:
                switchShape = CIRCLE;
                invalidate();
                break;
            case TRIANG:
                switchShape = TRIANG;
                invalidate();
                break;
            case SQUARE:
                switchShape = SQUARE;
                invalidate();
                break;
        }
    }
}
