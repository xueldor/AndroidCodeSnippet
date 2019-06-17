package com.xueldor.androidcodesnippet;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup {
    private static final String TAG = "FlowLayout";

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        //wrap_content的宽度，取每行lineWidth的最大值
        int width = 0;
        int height = 0;
        //当前行的宽度
        int lineWidth = 0;
        int lineHeight = 0;

        int cCount = getChildCount();
        for (int i = 0;i < cCount;i++){
            View child =getChildAt(i);
            if(child.getVisibility() != View.GONE) {//可见性不是GONE的view都需要参与计算
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                //重写generateLayoutParams方法返回一个MarginLayoutParams
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                //child的宽和高
                int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

                if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                    //新起一行或遍历结束时更新width的值(新起一行)
                    width = Math.max(width, lineWidth);
                    lineWidth = childWidth;
                    height += lineHeight;
                    lineHeight = childHeight;
                } else {
                    lineWidth += childWidth;
                    lineHeight = Math.max(lineHeight, childHeight);
                }
            }
            if(i == cCount -1){//新起一行或遍历结束时更新width的值(遍历结束)
                width = Math.max(lineWidth,width);
                height += lineHeight;
            }
        }
        //如果模式是EXACTLY，使用父控件传递过来的size；否则使用自己计算的size
        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop()
        );
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i(TAG,"onLayout " + changed + " " + l + " " + t + " " + r + " " + b);
        int width = getWidth();
        int lineHeight = 0;

        int left = getPaddingLeft();
        int top = getPaddingTop();
        int cCount = getChildCount();
        for(int i = 0; i<cCount; i++){
            View child = getChildAt(i);
            if(child.getVisibility() == View.GONE)continue;
            MarginLayoutParams lp  = (MarginLayoutParams)child.getLayoutParams();

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            int nextLeft = left + childWidth + lp.leftMargin + lp.rightMargin;
            if(nextLeft > width - getPaddingRight()){
                //当前行容纳不下该view时，移到下一行，即left移到左边，top加上当前行的高度
                left = getPaddingLeft();
                nextLeft = left + childWidth + lp.leftMargin + lp.rightMargin;
                top += lineHeight;
                lineHeight = 0;//已经移到新行，行高重置为0
            }else {
                lineHeight = Math.max(lineHeight,childHeight + lp.topMargin + lp.bottomMargin);
            }
            child.layout(left + lp.leftMargin,top + lp.topMargin,
                    nextLeft - lp.rightMargin,top + lp.topMargin + childHeight);
            left = nextLeft;
        }

    }

    //viewgroup执行addView时使用的LayoutParams
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    }
}
