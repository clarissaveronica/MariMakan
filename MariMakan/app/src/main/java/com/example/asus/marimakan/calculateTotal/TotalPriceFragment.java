package com.example.asus.marimakan.calculateTotal;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.marimakan.R;

/**
 * Created by ASUS on 11/13/2017.
 */

public class TotalPriceFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.place_order_fragment, container, false);
        return rootView;
    }

}