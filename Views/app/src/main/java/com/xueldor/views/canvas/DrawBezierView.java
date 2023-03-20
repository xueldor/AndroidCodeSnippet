package com.xueldor.views.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawBezierView extends View{

	private Paint mPaint;
	private int centerX,centerY;

	private PointF start,end,control;//起点，终点，控制点

	private void initPaint() {
		mPaint = new Paint();          // 创建画笔
		mPaint.setColor(Color.BLACK);        // 设置颜色
		mPaint.setStyle(Paint.Style.STROKE);   //填充模式
		mPaint.setStrokeWidth(7);             // 边框宽
		mPaint.setTextSize(60);

		start = new PointF(0,0);
		end = new PointF(0,0);
		control = new PointF(0,0);

	}

	public DrawBezierView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(0xFF00FF00);
		mPaint.setColor(Color.GRAY);
		mPaint.setStrokeWidth(20f);
		//绘制三个点
		canvas.drawPoint(start.x,start.y,mPaint);
		canvas.drawPoint(end.x,end.y,mPaint);
		canvas.drawPoint(control.x,control.y,mPaint);

		//绘制辅助线
		mPaint.setStrokeWidth(4);
		canvas.drawLine(start.x,start.y,control.x,control.y,mPaint);
		canvas.drawLine(end.x,end.y,control.x,control.y,mPaint);

		mPaint.setColor(Color.RED);
		mPaint.setStrokeWidth(8);
		Path path = new Path();
		path.moveTo(start.x,start.y);
		path.quadTo(control.x,control.y,end.x,end.y);

		canvas.drawPath(path,mPaint);

	}
	
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w,h,oldw,oldh);
		centerX = w/2;
		centerY = h/2;//view的中点

		//初始化起点、终点和控制点的位置
		start.x = centerX - 200;//view中点左200像素
		start.y = centerY;
		end.x = centerX + 200;
		end.y = centerY;

		control.x = centerX;
		control.y = centerY - 100;//bezer曲线的控制点初始化为view中心上100像素

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		control.x = event.getX();
		control.y = event.getY();
		invalidate();
		return true;
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
