package org.footballtimeline.footballgraphics.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by adventure on 12/9/16.
 */

public class FootballInfographicsUtils {

public static void attachFragment ( Context c, int layoutId, Fragment f, String tag, boolean putToBackStack ) {

    FragmentManager fm = ( (AppCompatActivity) ( c ) ).getSupportFragmentManager ();
    FragmentTransaction ft = fm.beginTransaction ();
    ft.setCustomAnimations ( android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out );
    ft.add ( layoutId, f, tag );
    if (putToBackStack) ft.addToBackStack ( tag );
    ft.commit ();
}
}
