package com.irad.cm.agri_tech.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.irad.cm.agri_tech.MainActivity;
import com.irad.cm.agri_tech.R;
import com.irad.cm.agri_tech.climate.WeatherActivity;
import com.irad.cm.agri_tech.cropDetail.MarketPrice;
import com.irad.cm.agri_tech.crops.CropsActivity;
import com.irad.cm.agri_tech.marketPrice.MarketPriceActivity;
import com.irad.cm.agri_tech.plantAndDisease.PlantAndDiseaseActivity;

import static maes.tech.intentanim.CustomIntent.customType;

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
                            startActivity(new Intent(getActivity(), WeatherActivity.class));
                            customType(getActivity(),"left-to-right");
                            break;
                        case 0:
                            startActivity(new Intent(getActivity(), CropsActivity.class));
                            customType(getActivity(),"right-to-left");
                            break;
                        case 3:
                            startActivity(new Intent(getActivity(), MarketPriceActivity.class));
                            customType(getActivity(), "left-to-right");
                            break;
                        case 2:
                            startActivity(new Intent(getActivity(), PlantAndDiseaseActivity.class));
                            customType(getActivity(),"right-to-left");
                            break;
                    }
                }
            });
        }
    }
}
