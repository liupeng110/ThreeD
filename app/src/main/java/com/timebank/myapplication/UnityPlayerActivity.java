package com.timebank.myapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;

import com.unity3d.player.*;

public class UnityPlayerActivity extends Activity
{
    MyFragment myFragment;
    protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code

    // Override this in your custom UnityPlayerActivity to tweak the command line arguments passed to the Unity Android Player
    // The command line arguments are passed as a string, separated by spaces
    // UnityPlayerActivity calls this from 'onCreate'
    // Supported: -force-gles20, -force-gles30, -force-gles31, -force-gles31aep, -force-gles32, -force-gles, -force-vulkan
    // See https://docs.unity3d.com/Manual/CommandLineArguments.html
    // @param cmdLine the current command line arguments, may be null
    // @return the modified command line string or null
    protected String updateUnityCommandLineArguments(String cmdLine)
    {
        return cmdLine;
    }

    // Setup activity layout
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        String cmdLine = updateUnityCommandLineArguments(getIntent().getStringExtra("unity"));
        getIntent().putExtra("unity", cmdLine);

//        mUnityPlayer = new UnityPlayer(this);
//        setContentView(mUnityPlayer);
//        mUnityPlayer.requestFocus();
//        MyFragment fragment=new MyFragment();
     setContentView(R.layout.activity_unity);
     myFragment=new MyFragment();
    replaceFragment(myFragment);
    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = this.getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();   // 开启一个事务
        transaction.replace(R.id.contents, fragment);
        transaction.commit();
    }
    @Override
    protected void onNewIntent(Intent intent)
    {
        // To support deep linking, we need to make sure that the client can get access to
        // the last sent intent. The clients access this through a JNI api that allows them
        // to get the intent set on launch. To update that after launch we have to manually
        // replace the intent with the one caught here.
//        setIntent(intent);
//        mUnityPlayer.newIntent(intent);
    }

    // Quit Unity
    @Override
    protected void onDestroy ()
    {
        myFragment.onDestroy();
        super.onDestroy();
    }

    // Pause Unity
    @Override
    protected void onPause()
    {
        super.onPause();
        myFragment.onPause();
    }

    // Resume Unity
    @Override
    protected void onResume()
    {
        super.onResume();
     myFragment.onResume();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        myFragment.onStart();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        myFragment.onStop();
    }

    // Low Memory Unity
    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
    }

    // Trim Memory Unity
    @Override
    public void onTrimMemory(int level)
    {
        super.onTrimMemory(level);
//        if (level == TRIM_MEMORY_RUNNING_CRITICAL)
//        {
//            mUnityPlayer.lowMemory();
//        }
    }

    // This ensures the layout will be correct.
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        myFragment.onConfigurationChanged(newConfig);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        myFragment.onWindowFocusChanged(hasFocus);
    }

    // For some reason the multiple keyevent type is not supported by the ndk.
    // Force event injection by overriding dispatchKeyEvent().
    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
//        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
//            return mUnityPlayer.injectEvent(event);
        myFragment.dispatchKeyEvent(event);
        return super.dispatchKeyEvent(event);
    }

    // Pass any events not handled by (unfocused) views straight to UnityPlayer
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)     {
        myFragment.onKeyUp(keyCode,event);
        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)   {
        myFragment.onKeyDown(keyCode,event);
        return true;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)          {
        myFragment.onTouchEvent(event);
        return true;
    }
//    /*API12*/ public boolean onGenericMotionEvent(MotionEvent event)  { return mUnityPlayer.injectEvent(event); }
}
