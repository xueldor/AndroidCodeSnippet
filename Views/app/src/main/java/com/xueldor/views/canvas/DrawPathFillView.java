package com.xueldor.views.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class DrawPathFillView extends View{

	private Paint mDefaultPaint;
	private float mViewWidth;
	private float mViewHeight;



	private void initPaint() {
		mDefaultPaint = new Paint();          // 创建画笔
		mDefaultPaint.setColor(Color.GRAY);        // 设置颜色
		mDefaultPaint.setStyle(Paint.Style.FILL);   //填充模式
		mDefaultPaint.setStrokeWidth(10);             // 边框宽

	}

	public DrawPathFillView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.translate(mViewWidth/2,mViewHeight/2);
		canvas.drawColor(0xFFFFFFFF);
		Path path = new Path();
		path.setFillType(Path.FillType.INVERSE_EVEN_ODD);//反奇偶规则
//		path.setFillType(Path.FillType.EVEN_ODD);//奇偶规则
//		path.setFillType(Path.FillType.INVERSE_WINDING);//非零环绕数规则
//		path.setFillType(Path.FillType.INVERSE_WINDING);//反非零环绕数规则
		path.addRect(-50,-50,50,50,Path.Direction.CW);//顺时针
		path.addRect(-100,-100,100,100,Path.Direction.CCW);//逆时针

		canvas.drawPath(path,mDefaultPaint);

		Path path1 = new Path();
		Path path2 = new Path();
		Path path3 = new Path();
		Path path4 = new Path();
		path1.addCircle(0, 250, 120, Path.Direction.CW);
		path2.addRect(0,130,120,370,Path.Direction.CW);
		path3.addCircle(0, 190, 60, Path.Direction.CW);
		path4.addCircle(0, 310, 60, Path.Direction.CCW);
		path1.op(path2, Path.Op.DIFFERENCE);//大圆与矩形的差集，得到半圆
		path1.op(path3, Path.Op.UNION);//大圆与一个小圆的并集，使得阴阳鱼的上一半形成
		path1.op(path4, Path.Op.DIFFERENCE);//大圆与一个小圆的差，使半圆挖去一块，得到阴阳鱼的下一半

		canvas.drawPath(path1,mDefaultPaint);

	}
	
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mViewWidth = w;
		mViewHeight = h;
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
