package com.irad.cm.agri_tech.crops;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.irad.cm.agri_tech.R;
import com.irad.cm.agri_tech.RetrofitClientInstance;
import com.irad.cm.agri_tech.Utilities;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnnualCropsFragment extends Fragment implements SearchView.OnQueryTextListener {
    AnnualCropsAdapter cropListAdapter;
    private SearchView searchView;
    Utilities utilities;
    ProgressDialog progressDialog;
    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_annual_crops, container, false);

        ((CropsActivity) getActivity()).getSupportActionBar().setTitle(R.string.annual_crops);

        mRecyclerView = view.findViewById(R.id.annualcrops_recyclerview);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        progressDialog = new ProgressDialog(getContext());

        CropsService service = RetrofitClientInstance.getRetrofitInstance().create(CropsService.class);
        Call<AnnualCropList> call = service.getAnnualCropList(Utilities.LANGUAGE);
        call.enqueue(new Callback<AnnualCropList>() {
            @Override
            public void onResponse(Call<AnnualCropList> call, Response<AnnualCropList> response) {
                progressDialog.dismiss();
                generateCropList(response.body().getCultureAnnuelle());
            }

            @Override
            public void onFailure(Call<AnnualCropList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    private void generateCropList(List<CultureAnnuelle> cropList) {
        cropListAdapter = new AnnualCropsAdapter(getActivity(), cropList);
        mRecyclerView.setAdapter(cropListAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        utilities = new Utilities(getContext(), getActivity());

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.crops_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search_crops);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
//        if (searchView != null) {
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

//        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.language:
                utilities.showChangeLanguageDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String userInput = StringUtils.capitalize(s);

        List<CultureAnnuelle> newCropList = new ArrayList<>();
        List<String> cropNames = new ArrayList<>();

        for (CultureAnnuelle cropData : AnnualCropsAdapter.cropList) {
            cropNames.add(cropData.getName());
        }

        for (String cropName : cropNames) {
            if (cropName.contains(userInput)) {
                String res = cropName.toLowerCase();

                newCropList.add(new CultureAnnuelle(cropName, res));

            }
        }

        cropListAdapter.updateList(newCropList);

        return true;
    }
}
