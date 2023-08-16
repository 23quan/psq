package com.example.psq.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.example.psq.R;

public class CustomFramelayout extends FrameLayout {
    private int borderWidth = 0;//边框宽
    private int borderColor = Color.WHITE;//边框颜色
    private int radius = 0;//圆角
    private int backgroundColor = Color.WHITE;//背景颜色
    public float drawWidth = 0;//边线宽度
    public int drawColor = Color.WHITE;//边线颜色
    public boolean drawTop = false;//是否画顶部边线
    public boolean drawBottom = false;//是否画底部边线
    public boolean drawLeft = false;//是否画右边边线
    public boolean drawRight = false;//是否画左边边线

    private Paint paint;

    public CustomFramelayout(Context context) {
        super(context);
    }

    public CustomFramelayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomFramelayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray attrArray = context.obtainStyledAttributes(attrs, R.styleable.CustomFrameLayout);
            borderWidth = attrArray.getDimensionPixelSize(R.styleable.CustomFrameLayout_cflBorderWidth, borderWidth);
            borderColor = attrArray.getColor(R.styleable.CustomFrameLayout_cflBorderColor, borderColor);
            radius = attrArray.getDimensionPixelSize(R.styleable.CustomFrameLayout_cflRadius, radius);
            backgroundColor = attrArray.getColor(R.styleable.CustomFrameLayout_cflBackgroundColor, backgroundColor);
            drawWidth = attrArray.getFloat(R.styleable.CustomFrameLayout_cflDrawWidth, 0);
            drawColor = attrArray.getColor(R.styleable.CustomFrameLayout_cflDrawColor, drawColor);
            drawTop = attrArray.getBoolean(R.styleable.CustomFrameLayout_cflDrawTop, false);
            drawBottom = attrArray.getBoolean(R.styleable.CustomFrameLayout_cflDrawBottom, false);
            drawLeft = attrArray.getBoolean(R.styleable.CustomFrameLayout_cflDrawLeft, false);
            drawRight = attrArray.getBoolean(R.styleable.CustomFrameLayout_cflDrawRight, false);
            attrArray.recycle();

            //背景
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(backgroundColor);
            if (radius > 0) {
                gradientDrawable.setCornerRadius(radius);
            }
            if (borderWidth > 0) {
                gradientDrawable.setStroke(borderWidth, borderColor);
            }
            this.setBackground(gradientDrawable);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (paint == null) {
            paint = new Paint();
        }
        paint.setColor(drawColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth);
        paint.setAntiAlias(true);

        if (drawTop) {
            canvas.drawLine(0, 0, getWidth(), 0, paint);
        }

        if (drawBottom) {
            canvas.drawLine(0, getHeight(), getWidth(), getHeight(), paint);
        }

        if (drawLeft) {
            canvas.drawLine(0, 0, 0, getHeight(), paint);
        }

        if (drawRight) {
            canvas.drawLine(getWidth(), 0, getWidth(), getHeight(), paint);
        }
    }

    public void setDrawStyle(float drawWidth, int drawColor) {
        this.drawWidth = drawWidth;
        this.drawColor = drawColor;
    }

    public void drawBorder(boolean drawTop, boolean drawBottom, boolean drawLeft, boolean drawRight) {
        this.drawTop = drawTop;
        this.drawBottom = drawBottom;
        this.drawLeft = drawLeft;
        this.drawRight = drawRight;
        invalidate();
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(backgroundColor);
        gradientDrawable.setCornerRadius(radius);
        if (borderWidth > 0) {
            gradientDrawable.setStroke(borderWidth, borderColor);
        }
        this.setBackground(gradientDrawable);
    }
}
