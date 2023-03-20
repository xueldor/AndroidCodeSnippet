package com.xueldor.views.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class DrawView extends View{
	//1、创建画笔
	private Paint mPaint = new Paint();
	
	//2.初始化画笔
	private void initPaint() {
		mPaint.setColor(Color.BLACK);
		mPaint.setStyle(Style.FILL);
		mPaint.setStrokeWidth(10f);//画笔宽度
	}

	public DrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}

	RectF rectF = new RectF(20, 40, 200, 150);
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.argb(100, 100, 100, 0));
		canvas.drawPoint(200, 200, mPaint);
		canvas.drawLines(new float[] {40,40,200,150}, mPaint);
		canvas.save();
		canvas.translate(0, 200);
		
		RectF rectF = new RectF(20, 40, 200, 150);
		canvas.drawRoundRect(rectF, 40, 20, mPaint);
		canvas.translate(0, 200);
		
		mPaint.setAntiAlias(true);//抗锯齿
		canvas.drawRoundRect(rectF, 40, 20, mPaint);//跟上面对比,更加平滑
		System.out.println(getHeight());
		
		canvas.restore();
		canvas.translate(200, 0);
		mPaint.setStyle(Style.FILL_AND_STROKE);
		canvas.drawCircle(100, 100, 60, mPaint);
		
		//画一张椭圆的饼图
		int width = 160,height = 100;
		canvas.translate(160, 300);
		mPaint.setStyle(Style.FILL);
		float startAngle = 0;
		rectF.set(-width, -height, width, height);
		for(int i = 0;i< percentage.length;i++) {
			mPaint.setColor(mColors[i]);
			canvas.drawArc(rectF, startAngle, percentage[i], true, mPaint);
			startAngle += percentage[i];
		}
		
		//画一个环
		canvas.translate(320, -140);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeWidth(5f);
		canvas.skew((float)Math.tan(30 * Math.PI/360),0); //倾斜30度
		canvas.drawCircle(0, 0, 100, mPaint);
		canvas.drawCircle(0, 0, 120, mPaint);
		for(int i=0;i<18;i++) {
			canvas.drawLine(100, 0, 120, 0, mPaint);
			canvas.rotate(10f);
		}
	}
	
	private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000};
	private float[] percentage = {0.1f * 360, 0.2f * 360, 0.3f * 360, 0.4f * 360};
	
	
}
