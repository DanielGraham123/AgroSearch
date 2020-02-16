package com.irad.cm.agri_tech.climate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.irad.cm.agri_tech.R;

public class PagerFragment extends Fragment {

    public PagerFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        Bundle bundle = this.getArguments();
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        WeatherActivity weatherActivity = (WeatherActivity) getActivity();
//        recyclerView.setAdapter(weatherActivity.getAdapter(bundle.getInt("day")));

        return view;
    }

}
