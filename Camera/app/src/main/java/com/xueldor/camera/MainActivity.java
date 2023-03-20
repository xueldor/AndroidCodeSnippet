package com.xueldor.camera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button takePhotoButtion;
    private Button fileProviderButtion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        takePhotoButtion = findViewById(R.id.take_photo_beforeN_button);
        takePhotoButtion.setOnClickListener(this::onClickTakePhotoButton);
        fileProviderButtion = findViewById(R.id.take_photo_sinceN_button);
        fileProviderButtion.setOnClickListener(this::onClickFileProviderButton);
    }

    int REQUEST_CODE_TAKE_PHOTO = 0;
    int REQUEST_CODE_FILE_PROVIDER = 1;
    String mCurrentPhotoPath;

    //在7.0以上版本会报FileUriExposedException异常
    public void onClickTakePhotoButton(View v){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //有相机应用
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
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
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            String filename = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA)
                    .format(new Date()) + ".png";
            File file = new File(Environment.getExternalStorageDirectory(), filename);
            mCurrentPhotoPath = file.getAbsolutePath();

            //因为本质是一个provider，所以需要在xml里添加provider声明
            Uri fileUri = FileProvider.getUriForFile(this,"com.xue.android7.photo.fileprovider",file);

            //7.0不需要，5.0之前报SecurityException: Permission Denial，需要以下代码授权
            List<ResolveInfo> resInfoList = getPackageManager()
                    .queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                grantUriPermission(packageName, fileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
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
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_TAKE_PHOTO) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mCurrentPhotoPath,options);
            Toast.makeText(this,"width " + options.outWidth
                    + ",height " + options.outHeight,Toast.LENGTH_SHORT);
        }else if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_FILE_PROVIDER) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mCurrentPhotoPath,options);
            Toast.makeText(this, "width " + options.outWidth
                    + ",height " + options.outHeight,Toast.LENGTH_SHORT);
        }

    }

}