package com.a5idiot.linuxgame;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.a5idiot.linuxgame.fragments.LinuxPagerAdapter;
import com.a5idiot.linuxgame.fragments.ViewPagerFragment;
import com.yalantis.guillotine.animation.GuillotineAnimation;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final float RATIO_SCALE = 0.5f;
    private static final long RIPPLE_DURATION = 100;
    ViewPager pager;
    LinuxPagerAdapter adapter;

    Toolbar toolbar;
    FrameLayout root;
    View contentHamburger;

    GuillotineAnimation animation;


    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView ( R.layout.activity_main );
        toolbar = (Toolbar) findViewById ( R.id.toolbar );
        toolbar.setPadding ( 0, 0, 0, 0 );//for tab otherwise give space in tab
        toolbar.setContentInsetsAbsolute ( 0, 0 );
        root = (FrameLayout) findViewById ( R.id.root );
        contentHamburger = findViewById ( R.id.content_hamburger );
        if (toolbar != null) {
            setSupportActionBar ( toolbar );
            //noinspection ConstantConditions
            getSupportActionBar ().setTitle ( null );
        }

        @SuppressLint("InflateParams")
        View guillotineMenu = LayoutInflater.from ( this ).inflate ( R.layout.the_linux_game_menu, null );
        root.addView ( guillotineMenu );

        animation = new GuillotineAnimation.GuillotineBuilder ( guillotineMenu, guillotineMenu.findViewById ( R.id.menu_hamburger ), contentHamburger )
                .setStartDelay ( RIPPLE_DURATION )
                .setActionBarViewForAnimation ( toolbar )
                .setClosedOnStart ( true )
                .build ();

        root.findViewById ( R.id.profile_group ).setOnClickListener ( this );
        root.findViewById ( R.id.the_linux_game_menu ).setOnClickListener ( this );
        registerSwipe ( root );


        pager = (ViewPager) findViewById ( R.id.viewPager );
        LinearLayout linearLayout = (LinearLayout)pager.getParent ();
        registerSwipe ( linearLayout );
        registerSwipe ( pager );
        adapter = new LinuxPagerAdapter ( getSupportFragmentManager () );
        setPager ();
        pager.setAdapter ( adapter );


    }

    private void setPager ( ) {
        pager.setClipToPadding ( false );
        pager.setPageMargin ( -64 );
        pager.setPadding ( 200, 32, 200, 32 );
        pager.setOffscreenPageLimit ( 6 );
        pager.addOnPageChangeListener ( new ViewPager.OnPageChangeListener () {

            @Override
            public void onPageScrolled ( int position, float positionOffset, int positionOffsetPixels ) {
                Log.i ( "", "onPageScrolled: " + position );

                ViewPagerFragment sampleFragment = (ViewPagerFragment) ( (LinuxPagerAdapter) pager.getAdapter () ).getRegisteredFragment ( position );

                float scale = 1 - ( positionOffset * RATIO_SCALE );

                // Just a shortcut to findViewById(R.id.image).setScale(scale);
                sampleFragment.scaleImage ( scale );


                if (position + 1 < pager.getAdapter ().getCount ()) {
                    sampleFragment = (ViewPagerFragment) ( (LinuxPagerAdapter) pager.getAdapter () ).getRegisteredFragment ( position + 1 );
                    scale = positionOffset * RATIO_SCALE + ( 1 - RATIO_SCALE );
                    sampleFragment.scaleImage ( scale );
                }
            }

            @Override
            public void onPageSelected ( int position ) {
                Log.i ( "", "onPageSelected: " + position );
            }


            @Override
            public void onPageScrollStateChanged ( int state ) {
                Log.i ( "", "onPageScrollStateChanged: " + state );
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    ViewPagerFragment fragment = (ViewPagerFragment) ( (LinuxPagerAdapter) pager.getAdapter () ).getRegisteredFragment ( pager.getCurrentItem () );
                    fragment.scaleImage ( 1 );
                    if (pager.getCurrentItem () > 0) {
                        fragment = (ViewPagerFragment) ( (LinuxPagerAdapter) pager.getAdapter () ).getRegisteredFragment ( pager.getCurrentItem () - 1 );
                        fragment.scaleImage ( 1 - RATIO_SCALE );

                    }

                    if (pager.getCurrentItem () + 1 < pager.getAdapter ().getCount ()) {
                        fragment = (ViewPagerFragment) ( (LinuxPagerAdapter) pager.getAdapter () ).getRegisteredFragment ( pager.getCurrentItem () + 1 );
                        fragment.scaleImage ( 1 - RATIO_SCALE );

                    }
                }
            }
        } );

    }
    @Override
    public void onClick ( View v ) {
        switch (v.getId ()) {
            case R.id.profile_group:
                Toast.makeText ( getApplicationContext (),"profile clicked",Toast.LENGTH_SHORT ).show ();

        }
        animation.close ();

    }



    private void registerSwipe ( final View rootView ) {
        final GestureDetector gesture = new GestureDetector (
                this,
                new GestureDetector.SimpleOnGestureListener () {

                    @Override
                    public boolean onFling ( MotionEvent e1, MotionEvent e2, float velocityX,
                                             float velocityY ) {
                        final int SWIPE_MIN_DISTANCE = 125;
                        final int SWIPE_MAX_OFF_PATH = 250;
                        final int SWIPE_THRESHOLD_VELOCITY = 100;
                        try {
                            if (Math.abs ( e1.getX () - e2.getX () ) > SWIPE_MAX_OFF_PATH)
                                return false;
                            if (e1.getY () - e2.getY () > SWIPE_MIN_DISTANCE
                                    && Math.abs ( velocityY ) > SWIPE_THRESHOLD_VELOCITY) {
                                //DO Next
                                //Toast.makeText ( getApplicationContext (),"swipte up",Toast.LENGTH_LONG ).show ();
                                if(rootView instanceof FrameLayout){
                                    animation.close ();
                                }
                            }
                            else if (e2.getY () - e1.getY () > SWIPE_MIN_DISTANCE
                                    && Math.abs ( velocityY ) > SWIPE_THRESHOLD_VELOCITY) {
                                //DO Previous
                                //Toast.makeText ( getApplicationContext (),"swipte down",Toast.LENGTH_LONG ).show ();

                                if(rootView instanceof ViewPager||rootView instanceof LinearLayout){
                                    animation.open ();
                                }
                            }
                        } catch (Exception e) {
                            // nothing
                        }
                        return super.onFling ( e1, e2, velocityX, velocityY );
                    }

                    @Override
                    public boolean onDown ( MotionEvent e ) {
                        return true;
                    }
                }
        );

        rootView.setOnTouchListener ( new View.OnTouchListener () {
            @Override
            public boolean onTouch ( View v, MotionEvent event ) {
                return gesture.onTouchEvent ( event );
            }
        } );
    }
}
