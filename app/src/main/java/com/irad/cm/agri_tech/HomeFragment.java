package com.irad.cm.agri_tech;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

public class HomeFragment extends Fragment {

    private GridLayout mainGrid;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.home_layout, container, false);

        mainGrid = view.findViewById(R.id.mainGrid);
        setSingleEvent(mainGrid);

        return view;

    }

    private void setSingleEvent(GridLayout mainGrid) {

        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            final int index = i;
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getContext(), "Clicked at index "+index, Toast.LENGTH_SHORT).show();
                    switch (index) {
                        case 1:
//                            MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new WeatherFragment(), "").addToBackStack("").commit();
                            startActivity(new Intent(getActivity(), WeatherActivity.class));
                            break;
                    }
                }
            });
        }
    }
}
