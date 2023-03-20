package com.xueldor.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = createFragment();
            if(fragment != null) {
                Bundle bundle = getIntent().getBundleExtra("FRAGMENT_BUNDLE");
                if (bundle != null) {
                    fragment.setArguments(bundle);
                }
                fm.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();
            }
        }
    }
    private<T extends Fragment> T createFragment() {
        String fragmentName = getIntent().getStringExtra("FRAGMENT_NAME");
        try {
            Class<?> aClass = Class.forName(fragmentName);
            Method newInstance = aClass.getMethod("newInstance");
            return (T)newInstance.invoke(null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Intent newIntent(Context packageContext, Class<? extends Fragment> clz){
        Intent intent = new Intent(packageContext,SecondActivity.class);
        intent.putExtra("FRAGMENT_NAME",clz.getName());
        return intent;
    }
    public static Intent newIntent(Context packageContext, Class<Fragment> clz,Bundle fragmentBundle){
        Intent intent = new Intent(packageContext,SecondActivity.class);
        intent.putExtra("FRAGMENT_NAME",clz.getName());
        intent.putExtra("FRAGMENT_BUNDLE",fragmentBundle);
        return intent;
    }
}
