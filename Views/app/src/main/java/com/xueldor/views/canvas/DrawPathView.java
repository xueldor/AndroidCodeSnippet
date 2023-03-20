package com.xueldor.views.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class DrawPathView extends View{
	
	private Paint mPaint;
	private Path mPath;
	
	private int mWidth;
	private int mHeight;
	
	private void initPaint() {
		mPaint = new Paint();          // 创建画笔
		mPaint.setColor(Color.BLACK);        // 设置颜色
		mPaint.setStyle(Paint.Style.STROKE);   //填充模式
		mPaint.setStrokeWidth(10);             // 边框宽
		
		mPath = new Path();
	}
	
	public DrawPathView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.translate(mWidth / 2, mHeight / 2);
		mPath.lineTo(200, 200);
		//如果放开下行的注释，则close方法无效，原因见mPath.close()后面的注释
//		mPath.moveTo(200, 100);
		mPath.lineTo(200, 0);
		mPath.close();//如果连接了最后一个点和第一个点仍然无法形成封闭图形，则close什么 也不做。
		mPath.addRect(-100, -100, 100, 100, Path.Direction.CCW);
		canvas.drawPath(mPath, mPaint);
		//否则界面重绘时将不以原点为起点
		mPath.reset();
	}
	
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mWidth = w;
		mHeight = h;
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
	}
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
	}
	
	
}
