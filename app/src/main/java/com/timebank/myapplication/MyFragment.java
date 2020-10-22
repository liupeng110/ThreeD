package com.timebank.myapplication;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.unity3d.player.UnityPlayer;

public class MyFragment  extends Fragment {
    View view;
    UnityPlayer unityPlayer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_layout, null);
//        unityPlayer=new UnityPlayer(getActivity());
//        FrameLayout frameLayout=view.findViewById(R.id.framelayout);
//        frameLayout.addView(unityPlayer);
//        UnityPlayer unityPlayer=view.findViewById(R.id.framelayout);

        unityPlayer = new UnityPlayer(this.getActivity());

        if (unityPlayer.getSettings ().getBoolean ("hide_status_bar",true))
            getActivity().getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        int glesMode = unityPlayer.getSettings().getInt("gles_mode",1);
        boolean trueColor8888 = false;
        unityPlayer.init(glesMode,trueColor8888);
        View playerView = unityPlayer.getView();
//        View v=unityPlayer.getView();
        if (playerView!=null){
            try {
                ViewGroup viewGroup= (ViewGroup) playerView.getParent();
                viewGroup.removeAllViews();
            }catch (Throwable t){t.printStackTrace();}
        }

        FrameLayout layout = view.findViewById(R.id.framelayout);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(750,630);
        layout.addView(playerView,lp);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    public boolean dispatchKeyEvent(KeyEvent event)
    {
        unityPlayer.injectEvent(event);
     return true;
    }

    // Pass any events not handled by (unfocused) views straight to UnityPlayer

    public boolean onKeyUp(int keyCode, KeyEvent event)     { return unityPlayer.injectEvent(event); }

    public boolean onKeyDown(int keyCode, KeyEvent event)   { return unityPlayer.injectEvent(event); }

    public boolean onTouchEvent(MotionEvent event)          { return unityPlayer.injectEvent(event); }
    /*API12*/ public boolean onGenericMotionEvent(MotionEvent event)  { return unityPlayer.injectEvent(event); }



    public void onPause()
    {
        super.onPause();
        unityPlayer.pause();
    }


    public void onResume()
    {
        super.onResume();
        unityPlayer.resume();
    }


    public void onStart()
    {
        super.onStart();
        unityPlayer.start();
    }


    public void onStop()
    {
        super.onStop();
        unityPlayer.stop();
    }

    @Override
    public void onDestroy ()
    {
        unityPlayer.destroy();
    }


    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        unityPlayer.configurationChanged(newConfig);
    }


    public void onWindowFocusChanged(boolean hasFocus)
    {
        unityPlayer.windowFocusChanged(hasFocus);
    }



}
