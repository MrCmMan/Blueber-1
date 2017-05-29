package com.teamber.blueber.blueber;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Администратор on 05.03.2017.
 */
public class MapsFragments extends android.support.v4.app.Fragment {
    private static final String profileFrag = "Maps";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.maps_layout,container,false);
        return view;
    }
}