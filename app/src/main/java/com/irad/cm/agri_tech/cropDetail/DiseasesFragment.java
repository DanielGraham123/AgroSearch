package com.irad.cm.agri_tech.cropDetail;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.irad.cm.agri_tech.R;
import com.irad.cm.agri_tech.RetrofitClientInstance;
import com.irad.cm.agri_tech.Utilities;
import com.irad.cm.agri_tech.marketPrice.MarketCardAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiseasesFragment extends Fragment {

    RecyclerView mRecyclerView;
    ProgressDialog progressDialog;
    AlertDialog.Builder dialogBuilder;
    DiseaseAdapter diseaseAdapter;
    ProgressBar progressBar;
    public DiseasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diseases, container, false);

        mRecyclerView = view.findViewById(R.id.disease_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar = view.findViewById(R.id.progressBar);

        setDiseaseItems();
        
        return view;
    }

    private void setDiseaseItems() {
        progressDialog = ProgressDialog.show(getContext(), "Please wait", "Loading...");

        new Thread() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        diseaseListing();
                    }
                });
                progressBar.setVisibility(View.GONE);
            }
        }.start();
    }

    private void diseaseListing() {
        CropDetailService service = RetrofitClientInstance.getRetrofitInstance().create(CropDetailService.class);
        Call<CropDetailList> call = service.getCropDetailList(CropDetailActivity.slug, Utilities.LANGUAGE);
        call.enqueue(new Callback<CropDetailList>() {
            @Override
            public void onResponse(Call<CropDetailList> call, Response<CropDetailList> response) {
                progressDialog.dismiss();
                List<Disease> diseases = response.body().getDisease();
                if (diseases.size() == 0) {

                } else {
                    generateCropDiseases(diseases);
                }
            }

            @Override
            public void onFailure(Call<CropDetailList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        new ProgressBar(getContext());

    }

    private void generateCropDiseases(List<Disease> diseases) {
        diseaseAdapter = new DiseaseAdapter(diseases, getContext());
        int resId = R.anim.layout_animation_down_to_up;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        mRecyclerView.setLayoutAnimation(animation);
        mRecyclerView.setAdapter(diseaseAdapter);
    }

}
