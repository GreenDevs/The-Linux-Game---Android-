package com.a5idiot.linuxgame.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
 * Created by adventure on 3/4/17.
 * erlaxmanbhattarai@gmail.com
 * itzluxman@gurkhatech.com
 * copyright reserved
 * gurkhatech.com
 */

public class LinuxPagerAdapter extends FragmentStatePagerAdapter {
    private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment> ();


    public LinuxPagerAdapter ( FragmentManager fm ) {
        super ( fm );
    }

    @Override
    public Fragment getItem ( int position ) {
        ViewPagerFragment fragment = new ViewPagerFragment ();
        Bundle args = new Bundle ();
        args.putInt ( "index", position );
        fragment.setArguments ( args );
        return fragment;
    }

    @Override
    public int getCount() {
        return 25;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem( ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}