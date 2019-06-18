package com.xueldor.androidcodesnippet.fragments;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xueldor.androidcodesnippet.R;

public class ShowGifFragment extends Fragment {

    View firstView;

    public ShowGifFragment() {
    }

    public static ShowGifFragment newInstance() {
        ShowGifFragment fragment = new ShowGifFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_show_gif, container, false);
        firstView = view.findViewById(R.id.first_gif_view);


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public static class GifView extends View{

        Movie movie;
        long movieStart;
        int frameCount;

        public GifView(Context context) {
            super(context);
        }

        public GifView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.GifView);
            int resourceId = array.getResourceId(R.styleable.GifView_src, 0);
            //因为没法知道gif一共多少帧，故人工指定
            frameCount = array.getInteger(R.styleable.GifView_frames,0);
            array.recycle();
            int attributeResourceValue = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res-auto","src", 0);
            //两种方法等价
            if(resourceId != attributeResourceValue){
                throw new RuntimeException("resourceId not equal attributeResourceValue");
            }
            movie = getResources().getMovie(resourceId);
        }

        public GifView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int duration = movie.duration();//获取gif一周期时间
            int eachFrame = 0;
            if(frameCount > 0) {
                eachFrame = duration / frameCount;//每一帧停留时间
            }
            long now = SystemClock.elapsedRealtime();
            if(movieStart == 0){
                movieStart = now;
            }
            Log.i("onDraw","gif " + (now));
            int real = (int)(now - movieStart)%duration;
            movie.setTime(real);
            movie.draw(canvas,0,0);
            if(eachFrame > 0) {
                postInvalidateDelayed(eachFrame);
            }else {
                invalidate();
            }
        }
    }
}
