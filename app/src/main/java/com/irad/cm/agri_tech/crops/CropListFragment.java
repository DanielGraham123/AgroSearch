package com.irad.cm.agri_tech.crops;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.irad.cm.agri_tech.R;
import com.irad.cm.agri_tech.RetrofitClientInstance;
import com.irad.cm.agri_tech.Utilities;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CropListFragment extends Fragment implements SearchView.OnQueryTextListener {
    CropListAdapter cropListAdapter;
    private SearchView searchView;
    Utilities utilities;
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    RecyclerView mRecyclerView;
    List<All> crops;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crop_list, container, false);

        ((CropsActivity) getActivity()).getSupportActionBar().setTitle(R.string.crops_list);

        mRecyclerView = view.findViewById(R.id.cropList_recyclerview);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        progressBar = view.findViewById(R.id.progressBar);

        setItems();
        return view;
    }

    private void setItems() {
        progressDialog = ProgressDialog.show(getContext(), "Please Wait", "Downloading data...");

        new Thread() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cropListing();
                    }
                });
                progressBar.setVisibility(View.GONE);
            }
        }.start();
    }

    private void cropListing() {
        CropsService service = RetrofitClientInstance.getRetrofitInstance().create(CropsService.class);
        Call<CropList> call = service.getCropList(Utilities.LANGUAGE);
        call.enqueue(new Callback<CropList>() {
            @Override
            public void onResponse(Call<CropList> call, Response<CropList> response) {
                progressDialog.dismiss();
                generateCropList(response.body().getAll());
            }

            @Override
            public void onFailure(Call<CropList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void generateCropList(List<All> cropList) {
        cropListAdapter = new CropListAdapter(getActivity(), cropList);
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

        final MenuItem searchItem = menu.findItem(R.id.search_crops);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setIconifiedByDefault(true);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean queryTextFocused) {
                if(!queryTextFocused) {
                    searchItem.collapseActionView();
                    setItems();
                    searchView.setQuery("", false);
                }
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.language:
                utilities.showChangeLanguageDialog();
                return true;
            case R.id.refresh_crops:
                setItems();
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
        List<All> newCropList = new ArrayList<>();
        Map<Integer, String> map = new HashMap<>();
        crops = CropListAdapter.cropList;

        for (int i= 0; i< crops.size(); i++) {
            map.put(i, crops.get(i).getName());
        }

        Set<Map.Entry<Integer, String>> set = map.entrySet();

        for (Map.Entry<Integer, String> entry : set) {
            if (entry.getValue().substring(0, s.length()).equalsIgnoreCase(s)) {
                newCropList.add(new All(crops.get(entry.getKey()).getName(), crops.get(entry.getKey()).getImage(), crops.get(entry.getKey()).getSlug()));
            }
        }

        cropListAdapter.updateList(newCropList);

        return true;
    }
}
