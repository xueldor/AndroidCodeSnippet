package com.xueldor.views.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawBezierBallView extends View{

	private Paint mPaint;

	//球的半径
	private int radius = 60;
	//球的当前坐标
	private Point ballPoint;

	private final float magic = 0.551915024494f;

	//形变的球的上下左右坐标
	private Point top,right,buttom,left;

	//只实现水平移动。水平向的拉升倍数
	private float leftRadius = 1.0f;
	private float rightRadius = 1.0f;

	private Point toPoint;

	private void initPaint() {
		mPaint = new Paint();          // 创建画笔
		mPaint.setColor(Color.RED);        // 设置颜色
		mPaint.setStyle(Paint.Style.FILL);   //填充模式
		mPaint.setStrokeWidth(3);             // 边框宽
		mPaint.setTextSize(60);

		ballPoint = new Point(0,0);
		top = new Point(0,0);
		right = new Point(0,0);
		buttom = new Point(0,0);
		left = new Point(0,0);
        toPoint = new Point(0,0);
	}

	public DrawBezierBallView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(0xFF00FF00);

		//根据中心点，计算出上下左右点的坐标
		top.x = ballPoint.x;
		top.y = ballPoint.y - radius;
		right.x = ballPoint.x + radius;
		right.y = ballPoint.y;
		buttom.x = ballPoint.x;
		buttom.y = ballPoint.y + radius;
		left.x = ballPoint.x - radius;
		left.y = ballPoint.y;

        //暂时只实现水平移动.根据当前位置与目标位置的距离，让小球发生形变
        if(toPoint.x - ballPoint.x - radius > 0){//向右
            rightRadius = (toPoint.x - ballPoint.x)*1.0f/radius;
            if(rightRadius > 2.0f) rightRadius = 2.0f;
            right.x = (int)(radius * rightRadius + ballPoint.x);
        }else if(toPoint.x - ballPoint.x + radius < 0){//向左
            leftRadius = (toPoint.x - ballPoint.x)/radius;
            if(leftRadius < -2.0f) leftRadius = -2.0f;
            left.x = (int)(radius * leftRadius + ballPoint.x);
        }

		Path path = new Path();
		CubicElem[] elements = new CubicElem[4];
		elements[0] = calcCubicElem(top,right);
		elements[1] = calcCubicElem(right,buttom);
		elements[2] = calcCubicElem(buttom,left);
		elements[3] = calcCubicElem(left,top);
		path.moveTo(elements[0].start.x,elements[0].start.y);
		for (CubicElem elem:elements) {
			path.cubicTo(elem.control1.x,elem.control1.y,elem.control2.x,elem.control2.y,
					elem.end.x,elem.end.y);
		}
        canvas.drawPath(path,mPaint);

		if(ballPoint.x < toPoint.x-2){
		    ballPoint.x +=2;
		    invalidate();

        }else if(ballPoint.x > toPoint.x + 2){
		    ballPoint.x -=2;
		    invalidate();
        }
	}

	/**
	 * 根据起点和终点计算两个控制点
	 * 设起点坐标(x1,y1),终点坐标(x2,y2),magic=a,中心为(0,0)
	 * 则控制点1坐标为(x1+a*x2,y1+a*y2)
	 * 控制点2坐标为(a*x1+x2,a*y1+y2)
	 * @param start
	 * @param end
	 * @return
	 */
	private CubicElem calcCubicElem(Point start,Point end){
		CubicElem elem = new CubicElem();
		elem.start = start;
		elem.end = end;
		int x,y;
		x = (int)(start.x + magic * (end.x - ballPoint.x));
		y = (int)(start.y + magic * (end.y-ballPoint.y));
		elem.control1 = new Point(x,y);
		x = (int)(end.x + magic * (start.x - ballPoint.x));
		y = (int)(end.y + magic * (start.y - ballPoint.y));
		elem.control2 = new Point(x,y);
		return elem;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w,h,oldw,oldh);
		ballPoint.x = radius;
		ballPoint.y = h/2;
		toPoint.x = ballPoint.x;
		toPoint.y = ballPoint.y;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
		    float x = event.getX();
		    float y = event.getY();
		    toPoint.x = (int)x;
		    toPoint.y = (int)y;
		    invalidate();
		}
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


	static class CubicElem{
		Point start;
		Point control1,control2;
		Point end;
	}
	
}
