package com.android.lukedmi.niketle.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.lukedmi.niketle.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflinflater.inflate(R.layout.fragment_home, container, false);ate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);;
        TextView tv = (TextView)view.findViewById(R.id.tv1);
        return view;
    }

}
