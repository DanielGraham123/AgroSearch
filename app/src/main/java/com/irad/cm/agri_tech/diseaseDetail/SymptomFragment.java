package com.irad.cm.agri_tech.diseaseDetail;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.irad.cm.agri_tech.R;

public class SymptomFragment extends Fragment {

    RecyclerView mRecyclerView;
    String[] symptomsText;
    SymptomsListAdapter symptomsListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_symptom, container, false);

        mRecyclerView = view.findViewById(R.id.symptoms_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setItems();


//        ListAdapter adapter = new ListAdapter(getActivity(), text);

//        ListView listView = view.findViewById(R.id.disease_listView);
//        listView.setAdapter(adapter);

//        WebView webView = view.findViewById(R.id.symptom_web_view);
//        webView.setBackgroundColor(Color.parseColor("#F1F5F1"));
//        webView.loadData(DiseaseDetailActivity.symptomHTML, "text/html; charset=UTF-8", null);


        return view;
    }

    private void setItems() {
        new Thread() {
            @Override
            public void run() {
                generateSymptomsList();
            }
        }.start();
    }

    private void generateSymptomsList() {
        symptomsText = new String[DiseaseDetailActivity.symptoms.size()];
        for (int i = 0; i< DiseaseDetailActivity.symptoms.size(); i++) {
            symptomsText[i] = DiseaseDetailActivity.symptoms.get(i);
        }

        for (String txt : symptomsText) {
            System.out.println("LISTING: "+txt);
        }

        symptomsListAdapter = new SymptomsListAdapter(getContext(), symptomsText);
        int resId = R.anim.layout_animation_down_to_up;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        mRecyclerView.setLayoutAnimation(animation);
        mRecyclerView.setAdapter(symptomsListAdapter);
    }

}


