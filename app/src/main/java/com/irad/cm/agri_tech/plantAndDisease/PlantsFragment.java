package com.irad.cm.agri_tech.plantAndDisease;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.irad.cm.agri_tech.R;
import com.irad.cm.agri_tech.Utilities;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlantsFragment extends Fragment {

    RecyclerView mRecyclerView;
    ProgressDialog progressDialog;
    PlantsAdapter plantsAdapter;
    AlertDialog.Builder dialogBuilder;
    ProgressBar progressBar;
    List<Disease> diseases;
    Utilities utilities;

    DiseasesViewModel diseasesViewModel;

    public PlantsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        utilities = new Utilities(getContext(), getActivity());
        diseasesViewModel = new ViewModelProvider(requireActivity()).get(DiseasesViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plants, container, false);

        mRecyclerView = view.findViewById(R.id.plants_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        progressBar = view.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);

        
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setItems();
    }

    private void setItems() {
        progressDialog = ProgressDialog.show(getContext(), "Please wait", "Loading...");

        new Thread() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        plantsListing();
                        diseasesViewModel.getLiveDiseasesData().observe(requireActivity(), new Observer<Diseases>() {
                            @Override
                            public void onChanged(Diseases diseases) {
                                generatePlantList(diseases.getDisease());
                            }
                        });
                    }
                });
                progressBar.setVisibility(View.GONE);
            }
        }.start();
    }


    private void generatePlantList(List<Disease> diseases) {
        this.diseases = diseases;
//        System.out.println("List of DISEASES: "+ this.diseases.toArray());
        plantsAdapter = new PlantsAdapter(getContext(), this.diseases);
        int resId = R.anim.layout_animation_down_to_up;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        mRecyclerView.setLayoutAnimation(animation);
        mRecyclerView.setAdapter(plantsAdapter);
        progressDialog.dismiss();

    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.market_menu, menu);
//        MenuItem menuItem = menu.findItem(R.id.search_market_crops);
//        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menuItem.getActionView();
//        searchView.setOnQueryTextListener(this);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
//
//        super.onCreateOptionsMenu(menu, inflater);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.language:
                utilities.showChangeLanguageDialog();
                return true;
            case R.id.refresh_market_crops:
                setItems();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
