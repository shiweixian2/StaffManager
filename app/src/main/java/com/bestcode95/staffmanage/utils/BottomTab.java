package com.bestcode95.staffmanage.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.bestcode95.staffmanage.R;

/**
 * Created by shiweixian on 2015/8/2.
 */
public class BottomTab extends View {

    private int mColor = getResources().getColor(R.color.light_purple);
    private Bitmap mIconBitmap;
    private String mText = "微信";
    private int mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());

    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Paint mPaint;
    private float mAlpha = 0.0f;
    private Rect mIconRect;
    private Rect mTextBound;
    private Paint mTextPaint;

    private int mIconWidth;
    private int top;

    public BottomTab(Context context) {
        this(context, null, 0);
    }

    public BottomTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 获取自定义属性的值
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public BottomTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ChangeColorIconWithText);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
//                case R.styleable.ChangeColorIconWithText_icons:
//                    BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(attr);
//                    mIconBitmap = drawable.getBitmap();
//                    break;
                case R.styleable.ChangeColorIconWithText_colors:
                    mColor = a.getColor(attr, getResources().getColor(R.color.purple));
                    break;
                case R.styleable.ChangeColorIconWithText_text:
                    mText = a.getString(attr);
                    break;
                case R.styleable.ChangeColorIconWithText_text_size:
                    mTextSize = (int) a.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    break;
            }
        }
        a.recycle();

        mTextBound = new Rect();
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(getResources().getColor(R.color.purple));
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mIconWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - mTextBound.height());
        int left = 4;
        top = getMeasuredHeight() / 2 - mIconWidth / 2;
        mIconRect = new Rect(left, top, left + mIconWidth, top + mIconWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawBitmap(mIconBitmap, null, mIconRect, null);

        int alpha = (int) Math.ceil(255 * mAlpha);
        //内存里去准备mBitmap, setAlpha, 纯色， xfermode, 图标
//        setupTargetBitmap(alpha);

        //1.绘制原文本  2.绘制变色的文本
        drawSourceText(canvas, alpha);
        drawTargetText(canvas, alpha);

//        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    /**
     * 绘制变色的文本(包括位置)
     *
     * @param canvas
     * @param alpha
     */
    private void drawTargetText(Canvas canvas, int alpha) {
        mTextPaint.setColor(getResources().getColor(R.color.purple));
        mTextPaint.setAlpha(alpha);
        int x = 8 + mIconWidth;
        int y = top + mIconRect.bottom / 2 - 2;//mIconRect.bottom/2 + mTextBound.height() / 2;
        canvas.drawText(mText, x, y, mTextPaint);
    }

    /**
     * 绘制原文本(包括位置)
     *
     * @param canvas
     * @param alpha
     */
    private void drawSourceText(Canvas canvas, int alpha) {
        mTextPaint.setColor(getResources().getColor(R.color.light_purple));
        mTextPaint.setAlpha(255 - alpha);
        int x = 8 + mIconWidth;
        int y = top + mIconRect.bottom / 2 - 2;//mIconRect.bottom/2 + mIconWidth/2;
        canvas.drawText(mText, x, y, mTextPaint);
    }

    /**
     * 在内存中绘制可变色的Icon
     */
    private void setupTargetBitmap(int alpha) {
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPaint = new Paint();
//        mPaint.setColor(mColor);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setAlpha(alpha);

        mCanvas.drawRect(mIconRect, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(255);
//        mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);
    }

    public void setIconAlpha(float alpha) {
        this.mAlpha = alpha;
        invalidateView();
    }

    /**
     * 重绘
     */
    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    private static final String INSTANCE_STATUS = "instance_status";
    private static final String STATUS_ALPHA = "status_alphs";

    /**
     * 防止后台程序被停止恢复后界面出现问题
     *
     * @return
     */
    @Override
    protected Parcelable onSaveInstanceState() {

        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATUS, super.onSaveInstanceState());
        bundle.putFloat(STATUS_ALPHA, mAlpha);
        return bundle;
    }

    /**
     * 防止后台程序被停止恢复后界面出现问题
     *
     * @return
     */
    @Override
    protected void onRestoreInstanceState(Parcelable state) {

        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mAlpha = bundle.getFloat(STATUS_ALPHA);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATUS));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
