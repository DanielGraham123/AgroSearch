package com.irad.cm.agri_tech.marketPrice;

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
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.irad.cm.agri_tech.R;
import com.irad.cm.agri_tech.RetrofitClientInstance;
import com.irad.cm.agri_tech.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeedPriceFragment extends Fragment implements SearchView.OnQueryTextListener {

    RecyclerView mRecyclerView;
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    ArrayList<Price> prices;
    Utilities utilities;
    SectionRecyclerViewAdapter adapter;

    public SeedPriceFragment() {
        // Required empty public constructor    SeedCardsAdapter seedCardsAdapter;

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
        View view = inflater.inflate(R.layout.fragment_seed_price, container, false);

        mRecyclerView = view.findViewById(R.id.sectioned_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressBar = view.findViewById(R.id.progressBar);

        setItems();

        return view;
    }

    private void priceListing() {
        PriceService service = RetrofitClientInstance.getRetrofitInstance().create(PriceService.class);
        final Call<Prices> pricesCall = service.getPriceList(Utilities.LANGUAGE);
        pricesCall.enqueue(new Callback<Prices>() {
            @Override
            public void onResponse(Call<Prices> call, Response<Prices> response) {
                progressDialog.dismiss();
                populateRecyclerView((ArrayList<Price>) response.body().getPrices());
            }

            @Override
            public void onFailure(Call<Prices> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void populateRecyclerView(ArrayList<Price> prices) {
        this.prices = prices;
        ArrayList<SeedSectionModel> seedSectionModelList = new ArrayList<>();
        System.out.println("SIZE: "+this.prices.size());
        for (int i = 0; i < this.prices.size(); i++) {
            ArrayList<SeedPrice> seedPriceList = new ArrayList<>();
            for (int j = 0; j < this.prices.get(i).getSeedPrice().size(); j++) {
                seedPriceList.add(this.prices.get(i).getSeedPrice().get(j));
            }
            seedSectionModelList.add(new SeedSectionModel(this.prices.get(i).getCulture(), seedPriceList));
        }
        adapter = new SectionRecyclerViewAdapter(getContext(), seedSectionModelList);
        int resId = R.anim.layout_animation_down_to_up;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        mRecyclerView.setLayoutAnimation(animation);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.market_menu, menu);
        final MenuItem menuItem = menu.findItem(R.id.search_market_crops);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(true);

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean queryTextFocused) {
                if(!queryTextFocused) {
                    menuItem.collapseActionView();
                    setItems();
                    searchView.setQuery("", false);
                }
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

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

    private void setItems() {
        progressDialog = ProgressDialog.show(getContext(), "Please wait", "Loading...");

        new Thread() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        priceListing();
                    }
                });
                progressBar.setVisibility(View.GONE);
            }
        }.start();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Map<Integer, String> map = new HashMap<>();
        ArrayList<SeedSectionModel> newSectionModels = new ArrayList<>();

        for (int i = 0; i < prices.size(); i++) {
            map.put(i, prices.get(i).getCulture());
        }

        Set<Map.Entry<Integer, String>> set = map.entrySet();

        for (Map.Entry<Integer, String> entry : set) {
            if (entry.getValue().substring(0, s.length()).equalsIgnoreCase(s)) {
                newSectionModels.add(new SeedSectionModel(prices.get(entry.getKey()).getCulture(), (ArrayList<SeedPrice>) prices.get(entry.getKey()).getSeedPrice()));
            }
        }
        adapter.updateList(newSectionModels);

        return true;
    }
}
