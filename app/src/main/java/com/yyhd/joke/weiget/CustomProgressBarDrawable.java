package com.yyhd.joke.weiget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import com.facebook.drawee.drawable.CloneableDrawable;
import com.facebook.drawee.drawable.DrawableUtils;

/**
 * 功能:
 * 作者: 崔兴旺
 * 日期: 2017/9/25
 * 备注:
 */
public class CustomProgressBarDrawable extends Drawable implements CloneableDrawable {


    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path mPath = new Path();
    private final RectF mRect = new RectF();
    private int mBackgroundColor = 0x80000000;
    private int mColor = 0x800080FF;
    private int mBarWidth = 20;
    private int mLevel = 0;
    private int mRadius = 0;
    private boolean mHideWhenZero = false;
    private boolean mIsVertical = false;
//    private int mPadding = 0;

    private int leftPadding;
    private int rightPadding;
    private int topPadding;
    private int bottomPadding;

    /**
     * Sets the progress bar color.
     */
    public void setColor(int color) {
        if (mColor != color) {
            mColor = color;
            invalidateSelf();
        }
    }

    /**
     * Gets the progress bar color.
     */
    public int getColor() {
        return mColor;
    }

    /**
     * Sets the progress bar background color.
     */
    public void setBackgroundColor(int backgroundColor) {
        if (mBackgroundColor != backgroundColor) {
            mBackgroundColor = backgroundColor;
            invalidateSelf();
        }
    }

    /**
     * Gets the progress bar background color.
     */
    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    /**
     * Sets the progress bar padding.
     */
    public void setPadding(int padding) {
        if (leftPadding != padding || topPadding != padding || rightPadding != padding || bottomPadding != padding) {
            leftPadding = padding;
            topPadding = padding;
            rightPadding = padding;
            bottomPadding = padding;
            invalidateSelf();
        }
    }

    /**
     * Gets the progress bar padding.
     */
    @Override
    public boolean getPadding(Rect padding) {
        padding.set(leftPadding, topPadding, rightPadding, bottomPadding);
        return leftPadding != 0 && topPadding != 0 && rightPadding != 0 && bottomPadding != 0;
    }

    /**
     * Sets the progress bar width.
     */
    public void setBarWidth(int barWidth) {
        if (mBarWidth != barWidth) {
            mBarWidth = barWidth;
            invalidateSelf();
        }
    }

    /**
     * Gets the progress bar width.
     */
    public int getBarWidth() {
        return mBarWidth;
    }

    /**
     * Sets whether the progress bar should be hidden when the progress is 0.
     */
    public void setHideWhenZero(boolean hideWhenZero) {
        mHideWhenZero = hideWhenZero;
    }

    /**
     * Gets whether the progress bar should be hidden when the progress is 0.
     */
    public boolean getHideWhenZero() {
        return mHideWhenZero;
    }

    /**
     * The progress bar will be displayed as a rounded corner rectangle, sets the radius here.
     */
    public void setRadius(int radius) {
        if (mRadius != radius) {
            mRadius = radius;
            invalidateSelf();
        }
    }

    /**
     * Gets the radius of the progress bar.
     */
    public int getRadius() {
        return mRadius;
    }

    /**
     * Sets if the progress bar should be vertical.
     */
    public void setIsVertical(boolean isVertical) {
        if (mIsVertical != isVertical) {
            mIsVertical = isVertical;
            invalidateSelf();
        }
    }

    /**
     * Gets if the progress bar is vertical.
     */
    public boolean getIsVertical() {
        return mIsVertical;
    }

    @Override
    protected boolean onLevelChange(int level) {
//        BLog.i("下载进度:"+level);
        mLevel = level;
        invalidateSelf();
        return true;
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return DrawableUtils.getOpacityFromColor(mPaint.getColor());
    }

    @Override
    public void draw(Canvas canvas) {
        if (mHideWhenZero && mLevel == 0) {
            return;
        }
        if (mIsVertical) {
            drawVerticalBar(canvas, 10000, mBackgroundColor);
            drawVerticalBar(canvas, mLevel, mColor);
        } else {
            drawHorizontalBar(canvas, 10000, mBackgroundColor);
            drawHorizontalBar(canvas, mLevel, mColor);
        }
    }

    @Override
    public Drawable cloneDrawable() {
        final CustomProgressBarDrawable copy = new CustomProgressBarDrawable();
        copy.mBackgroundColor = mBackgroundColor;
        copy.mColor = mColor;
        copy.leftPadding = leftPadding;
        copy.topPadding = topPadding;
        copy.rightPadding = rightPadding;
        copy.bottomPadding = bottomPadding;
        copy.mBarWidth = mBarWidth;
        copy.mLevel = mLevel;
        copy.mRadius = mRadius;
        copy.mHideWhenZero = mHideWhenZero;
        copy.mIsVertical = mIsVertical;
        return copy;
    }

    private void drawHorizontalBar(Canvas canvas, int level, int color) {
        Rect bounds = getBounds();
        bounds.bottom=mBarWidth;
        int length = (bounds.width() - leftPadding - rightPadding) * level / 10000;
        int xpos = bounds.left + leftPadding;
        int ypos = bounds.bottom - bottomPadding - mBarWidth;
        mRect.set(xpos, ypos, xpos + length, ypos + mBarWidth);
        drawBar(canvas, color);
    }

    /**
     * 这个方法没用过,不保证没问题
     * @param canvas
     * @param level
     * @param color
     */
    private void drawVerticalBar(Canvas canvas, int level, int color) {
        Rect bounds = getBounds();
        int length = (bounds.height() - topPadding - bottomPadding) * level / 10000;
        int xpos = bounds.left + leftPadding;
        int ypos = bounds.top + topPadding;
        mRect.set(xpos, ypos, xpos + mBarWidth, ypos + length);
        drawBar(canvas, color);
    }

    private void drawBar(Canvas canvas, int color) {
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPath.reset();
        mPath.setFillType(Path.FillType.EVEN_ODD);
        mPath.addRoundRect(
                mRect,
                Math.min(mRadius, mBarWidth / 2),
                Math.min(mRadius, mBarWidth / 2),
                Path.Direction.CW);
        canvas.drawPath(mPath, mPaint);
    }


    public void setLeftPadding(int leftPadding) {
        this.leftPadding = leftPadding;
    }

    public void setRightPadding(int rightPadding) {
        this.rightPadding = rightPadding;
    }

    public void setTopPadding(int topPadding) {
        this.topPadding = topPadding;
    }

    public void setBottomPadding(int bottomPadding) {
        this.bottomPadding = bottomPadding;
    }
}
