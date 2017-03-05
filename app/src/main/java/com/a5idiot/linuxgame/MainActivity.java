package com.a5idiot.linuxgame;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.a5idiot.linuxgame.fragments.LinuxPagerAdapter;
import com.a5idiot.linuxgame.fragments.ViewPagerFragment;
import com.yalantis.guillotine.animation.GuillotineAnimation;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final float RATIO_SCALE = 0.5f;
    private static final long RIPPLE_DURATION = 300;
    ViewPager pager;
    LinuxPagerAdapter adapter;

    Toolbar toolbar;
    FrameLayout root;
    View contentHamburger;

    GuillotineAnimation animation;


    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        toolbar = (Toolbar) findViewById ( R.id.toolbar );
        toolbar.setPadding ( 0, 0, 0, 0 );//for tab otherwise give space in tab
        toolbar.setContentInsetsAbsolute ( 0, 0 );
        root = (FrameLayout) findViewById ( R.id.root );
        contentHamburger = findViewById ( R.id.content_hamburger );
        if (toolbar != null) {
            setSupportActionBar ( toolbar );
            getSupportActionBar ().setTitle ( null );
        }

        View guillotineMenu = LayoutInflater.from ( this ).inflate ( R.layout.guillotine, null );
        root.addView ( guillotineMenu );

        animation = new GuillotineAnimation.GuillotineBuilder ( guillotineMenu, guillotineMenu.findViewById ( R.id.guillotine_hamburger ), contentHamburger )
                .setStartDelay ( RIPPLE_DURATION )
                .setActionBarViewForAnimation ( toolbar )
                .setClosedOnStart ( true )
                .build ();

        root.findViewById ( R.id.profile_group ).setOnClickListener ( this );


        pager = (ViewPager) findViewById ( R.id.viewPager );
        adapter = new LinuxPagerAdapter ( getSupportFragmentManager () );
        setPager ();
        pager.setAdapter ( adapter );


    }

    private void setPager ( ) {
        pager.setClipToPadding ( false );
        pager.setPageMargin ( -64 );
        pager.setPadding ( 200, 32, 200, 4 );
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
        switch (v.getId ()){
            case R.id.profile_group : Log.i ( "datatest","profile clicked" ); animation.close ();
        }
    }
}
