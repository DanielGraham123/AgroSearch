package com.irad.cm.agri_tech.cropDetail;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.irad.cm.agri_tech.R;
import com.irad.cm.agri_tech.RetrofitClientInstance;
import com.irad.cm.agri_tech.Utilities;
import com.irad.cm.agri_tech.crops.CropList;
import com.irad.cm.agri_tech.crops.CropListAdapter;
import com.irad.cm.agri_tech.crops.CropsService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VarietiesFragment extends Fragment {

    RecyclerView mRecyclerView;
    ProgressDialog progressDialog;
    VarietyAdapter varietyAdapter;


    public VarietiesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_varieties, container, false);

        mRecyclerView = view.findViewById(R.id.varieties_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressDialog = new ProgressDialog(getContext());

        setSeedList();

        return view;
    }

    private void setSeedList() {
        CropDetailService service = RetrofitClientInstance.getRetrofitInstance().create(CropDetailService.class);
        Call<CropDetailList> call = service.getCropDetailList(CropDetailActivity.slug, Utilities.LANGUAGE);
        call.enqueue(new Callback<CropDetailList>() {
            @Override
            public void onResponse(Call<CropDetailList> call, Response<CropDetailList> response) {
                progressDialog.dismiss();
                generateSeedDetail(response.body().getSeed());
            }

            @Override
            public void onFailure(Call<CropDetailList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void generateSeedDetail(List<Seed> seed) {
        varietyAdapter = new VarietyAdapter(getActivity(), seed);
        mRecyclerView.setAdapter(varietyAdapter);
    }

}
