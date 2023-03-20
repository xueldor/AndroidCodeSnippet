package com.xueldor.views;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.xueldor.views.fragments.ShowCanvasFragment;
import com.xueldor.views.fragments.ShowGifFragment;

public class Page1Fragment extends Fragment {

    private Button showGifButtion;
    private Button showCanvasButtion;

    public Page1Fragment() {
    }

    public static Page1Fragment newInstance() {
        Page1Fragment fragment = new Page1Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page1, container, false);

        showGifButtion = view.findViewById(R.id.show_gif_button);
        showGifButtion.setOnClickListener(v -> {
            startSecondActivity(ShowGifFragment.class);
        });
        showCanvasButtion = view.findViewById(R.id.show_canvas_button);
        showCanvasButtion.setOnClickListener(v -> {
            startSecondActivity(ShowCanvasFragment.class);
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void startSecondActivity(Class<? extends Fragment> clz){
        Intent intent = SecondActivity.newIntent(getContext(), clz);
        startActivity(intent);
    }

}
