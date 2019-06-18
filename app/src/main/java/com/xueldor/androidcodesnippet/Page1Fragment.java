package com.xueldor.androidcodesnippet;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.xueldor.androidcodesnippet.fragments.ShowGifFragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Page1Fragment extends Fragment {

    private Button takePhotoButtion;
    private Button fileProviderButtion;
    private Button showGifButtion;

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

        takePhotoButtion = view.findViewById(R.id.take_photo_beforeN_button);
        takePhotoButtion.setOnClickListener(this::onClickTakePhotoButton);
        fileProviderButtion = view.findViewById(R.id.take_photo_sinceN_button);
        fileProviderButtion.setOnClickListener(this::onClickFileProviderButton);

        showGifButtion = view.findViewById(R.id.show_gif_button);
        showGifButtion.setOnClickListener(v -> {
            startSecondActivity(ShowGifFragment.class);
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


    int REQUEST_CODE_TAKE_PHOTO = 0;
    int REQUEST_CODE_FILE_PROVIDER = 1;
    String mCurrentPhotoPath;

    //在7.0以上版本会报FileUriExposedException异常
    public void onClickTakePhotoButton(View v){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //有相机应用
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            String filename = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA)
                    .format(new Date()) + ".png";
            File file = new File(Environment.getExternalStorageDirectory(), filename);
            mCurrentPhotoPath = file.getAbsolutePath();

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
        }
    }
    public void onClickFileProviderButton(View v){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //有相机应用
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            String filename = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA)
                    .format(new Date()) + ".png";
            File file = new File(Environment.getExternalStorageDirectory(), filename);
            mCurrentPhotoPath = file.getAbsolutePath();

            //因为本质是一个provider，所以需要在xml里添加provider声明
            Uri fileUri = FileProvider.getUriForFile(this.getActivity(),"com.xue.android7.photo.fileprovider",file);

            //7.0不需要，5.0之前报SecurityException: Permission Denial，需要以下代码授权
            List<ResolveInfo> resInfoList = getActivity().getPackageManager()
                    .queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                getActivity().grantUriPermission(packageName, fileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                        | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            //或者直接校验版本,这样不要授权
            /*
            if (Build.VERSION.SDK_INT >= 24) {
                fileUri = FileProvider.getUriForFile(getActivity(), "com.xue.android7.photo.fileprovider", file);
            } else {
                fileUri = Uri.fromFile(file);
            }*/

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(takePictureIntent, REQUEST_CODE_FILE_PROVIDER);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && requestCode == REQUEST_CODE_TAKE_PHOTO) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mCurrentPhotoPath,options);
            Toast.makeText(getActivity(),"width " + options.outWidth
                    + ",height " + options.outHeight,Toast.LENGTH_SHORT);
        }else if (resultCode == getActivity().RESULT_OK && requestCode == REQUEST_CODE_FILE_PROVIDER) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mCurrentPhotoPath,options);
            Toast.makeText(getActivity(),"width " + options.outWidth
                    + ",height " + options.outHeight,Toast.LENGTH_SHORT);
        }

    }

    private void startSecondActivity(Class<? extends Fragment> clz){
        Intent intent = SecondActivity.newIntent(getContext(), clz);
        startActivity(intent);
    }

}
