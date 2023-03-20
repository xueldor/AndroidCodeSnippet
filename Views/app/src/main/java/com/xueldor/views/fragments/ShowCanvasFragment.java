package com.xueldor.views.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

import com.xueldor.views.R;

public class ShowCanvasFragment extends Fragment implements View.OnClickListener{

    FrameLayout framelayout;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button_path_fill;

    public static ShowCanvasFragment newInstance() {
        ShowCanvasFragment fragment = new ShowCanvasFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_canvas, container, false);
        framelayout = view.findViewById(R.id.container);
        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);
        button5 = view.findViewById(R.id.button5);
        button_path_fill = view.findViewById(R.id.button_path_fill);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button_path_fill.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                showView(R.id.drawView);
                break;
            case R.id.button2:
                showView(R.id.drawPicView);
                break;
            case R.id.button3:
                showView(R.id.drawPathView);
                break;
            case R.id.button4:
                showView(R.id.drawBezerView);
                break;
            case R.id.button5:
                showView(R.id.drawBezerBallView);
                break;
            case R.id.button_path_fill:
                showView(R.id.drawwPathFillView);
                break;

            default:
                break;
        }
    }
    private void showView(int id) {
        for(int i = 0;i<framelayout.getChildCount();i++) {
            View view = framelayout.getChildAt(i);
            if(view.getId() == id) {
                view.setVisibility(View.VISIBLE);
            }else {
                view.setVisibility(View.GONE);
            }
        }
    }
}
