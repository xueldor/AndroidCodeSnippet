package com.xueldor.views.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.PictureDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.xueldor.views.R;


public class DrawPicView extends View{
	
	//1、创建Picture
	private Picture mPicture = new Picture();
	private RectF mRectF = new RectF();
	
	private Bitmap zombieBitmap;
	private Handler mHandler;
	private Rect srcRect;
	private Rect dstRect;
	private int zombieX = 0;
	private int zombieY = 0;
	private int zombieXIndex = 0;
	private int zombieYIndex = 0;
	private int zombieTotalW;
	private int zombieTotalH;
	private int duration = 50;

	private Paint textPaint;
	private void initPaint() {
		textPaint = new Paint();          // 创建画笔
		textPaint.setColor(Color.BLACK);        // 设置颜色
		textPaint.setStyle(Paint.Style.FILL);   // 设置样式
		textPaint.setTextSize(50);              // 设置字体大小
	}
	
	private void recording() {
		Canvas canvas = mPicture.beginRecording(500, 500);
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setStyle(Paint.Style.FILL);
		
		canvas.translate(250, 250);
		canvas.drawCircle(0, 0, 100, paint);
		mPicture.endRecording();
	}
	public DrawPicView(Context context, AttributeSet attrs) {
		super(context, attrs);
		recording();
		zombieInit();
		initPaint();
	}
	
	private void zombieInit() {
		zombieBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.zombies);
		zombieTotalW = zombieBitmap.getWidth();
		zombieTotalH = zombieBitmap.getHeight();
		srcRect = new Rect(zombieX, zombieY,zombieTotalW * zombieXIndex / 11 -1, zombieTotalH * zombieYIndex / 2 -1);
        dstRect = new Rect(-zombieTotalW/22, -zombieTotalH/4, zombieTotalW/22, zombieTotalH/4);

		mHandler = new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				zombieXIndex++;
				if(zombieXIndex >= 11) {
					zombieXIndex = 0;
					zombieYIndex ++;
					if(zombieYIndex >= 2) {
						zombieYIndex = 0;
					}
				}
				zombieX = zombieTotalW * zombieXIndex / 11;
				zombieY = zombieTotalH * zombieYIndex / 2;
				srcRect.set(zombieX, zombieY, zombieX + zombieTotalW / 11, zombieY + zombieTotalH / 2);
				invalidate();
				
				mHandler.sendEmptyMessageDelayed(0, duration);
				return false;
			}
		});
	}

	PictureDrawable pictureDrawable = new PictureDrawable(mPicture);
	@Override
	protected void onDraw(Canvas canvas) {
		//方法一
		//mPicture height=500,这里绘制到height=200的区域，所以会上下压缩
		mRectF.set(0,0,mPicture.getWidth(),200);
		canvas.drawPicture(mPicture,mRectF);
		
		canvas.translate(0, 200);
		
		//方法二
		//设置在canvas上面的绘制区域
		pictureDrawable.setBounds(0, 0, 300, mPicture.getHeight());
		pictureDrawable.draw(canvas);
		
		//画僵尸
		canvas.translate(600, 200);
		canvas.drawBitmap(zombieBitmap, srcRect, dstRect, null);
		
		//画文字
		String string = "abcdefg";
		canvas.drawText(string, 0, 0, textPaint);
	}
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		mHandler.sendEmptyMessageDelayed(0, duration);
	}
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		mHandler.removeMessages(0);
	}
	
	
}
