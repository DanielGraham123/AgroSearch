package com.irad.cm.agri_tech.plantAndDisease;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.irad.cm.agri_tech.R;
import com.irad.cm.agri_tech.Utilities;
import com.irad.cm.agri_tech.marketPrice.SectionRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PltDiseasesFragment extends Fragment {
    
    RecyclerView mRecyclerView;
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    ArrayList<Disease> diseases;
    Utilities utilities;
    SectionRecyclerViewAdapter adapter;
    
    public PltDiseasesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        utilities = new Utilities(getContext(), getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plt_diseases, container, false);

//        mRecyclerView = view.findViewById(R.id.plt_diseases_recyclerview);
//        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        progressBar = view.findViewById(R.id.progressBar);

//        setItems();

        return view;
    }

//    private void setItems() {
//        progressDialog = ProgressDialog.show(getContext(), "Please wait", "Loading...");
//
//        new Thread() {
//            @Override
//            public void run() {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        diseaseListing();
//                    }
//                });
//                progressBar.setVisibility(View.GONE);
//            }
//        }.start();
//    }

//    private void diseaseListing() {
//        DiseaseService service = RetrofitClientInstance.getRetrofitInstance().create(DiseaseService.class);
//        final Call<Diseases> diseasesCall = service.getDiseaseList(Utilities.LANGUAGE);
//        diseasesCall.enqueue(new Callback<Diseases>() {
//            @Override
//            public void onResponse(Call<Diseases> call, Response<Diseases> response) {
//                progressDialog.dismiss();
//                populateRecyclerView((ArrayList<Disease>) response.body().getDisease());
//            }
//
//            @Override
//            public void onFailure(Call<Diseases> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
//    }

//    private void populateRecyclerView(ArrayList<Disease> diseases) {
//        this.diseases = diseases;
//        ArrayList<DiseaseSectionModel> diseaseSectionModelList = new ArrayList<>();
//        System.out.println("SIZE: "+ this.diseases.size());
//        for (int i = 0; i < this.diseases.size(); i++) {
//            ArrayList<Disease> diseaseList = new ArrayList<>();
//            for (int j = 0; j < this.diseases.size(); j++) {
//                diseaseList.add(this.diseases.get(j));
//            }
//            diseaseSectionModelList.add(new DiseaseSectionModel(this.diseases.get(i).getCulture(), diseaseList));
//        }
//        adapter = new SectionRecyclerViewAdapter(getContext(), diseaseSectionModelList);
//        int resId = R.anim.layout_animation_down_to_up;
//        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
//        mRecyclerView.setLayoutAnimation(animation);
//        mRecyclerView.setAdapter(adapter);
//    }

}
