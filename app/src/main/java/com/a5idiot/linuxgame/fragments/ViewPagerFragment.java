package com.a5idiot.linuxgame.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a5idiot.linuxgame.R;


public class ViewPagerFragment extends Fragment {

    int position;
    TextView view;
    View rootView;

    public ViewPagerFragment ( ) {
        // Required empty public constructor
    }


    @Override
    public void onAttach ( Context context ) {
        super.onAttach ( context );

    }

    @Override
    public void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );

    }

    @Override
    public View onCreateView ( LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        position = getArguments ().getInt ( "index" );
        rootView = inflater.inflate ( R.layout.fragment_view_pager, container, false );
        initLayout ( rootView );
        scaleImage ( 0.5f );
        return rootView;

    }

    private void initLayout ( View rootView ) {
        view = (TextView) rootView.findViewById ( R.id.textView );
        if (position % 2 == 0) {
            view.setBackgroundColor ( Color.RED );
        }
        else {
            view.setBackgroundColor ( Color.BLUE );

        }
    }

    public void scaleImage ( float scale ) {

        rootView.setScaleY ( scale );
        rootView.setScaleX ( scale );
       rootView.invalidate ();
    }
}
