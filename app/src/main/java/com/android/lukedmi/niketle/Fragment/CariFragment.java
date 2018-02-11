package com.android.lukedmi.niketle.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.lukedmi.niketle.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CariFragment extends Fragment {


    public CariFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cari, container, false);
    }

}
