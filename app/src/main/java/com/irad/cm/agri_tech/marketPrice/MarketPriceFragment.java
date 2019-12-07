package com.irad.cm.agri_tech.marketPrice;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
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

import com.irad.cm.agri_tech.MainActivity;
import com.irad.cm.agri_tech.R;
import com.irad.cm.agri_tech.RetrofitClientInstance;
import com.irad.cm.agri_tech.Utilities;
import com.irad.cm.agri_tech.crops.All;
import com.irad.cm.agri_tech.crops.CropListAdapter;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.System.exit;

@SuppressLint("NewApi")
public class MarketPriceFragment extends Fragment implements SearchView.OnQueryTextListener {

    RecyclerView mRecyclerView;
    ProgressDialog progressDialog;
    MarketCardAdapter marketCardAdapter;
    AlertDialog.Builder dialogBuilder;
    ProgressBar progressBar;
    List<Price> prices;
    Utilities utilities;

    public MarketPriceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        utilities = new Utilities(getContext(), getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_market_price, container, false);

        mRecyclerView = view.findViewById(R.id.market_price_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        Toolbar toolbar = getActivity().findViewById(R.id.main_toolbar);
//        toolbar.inflateMenu(R.menu.market_menu);
//        toolbar.setOnMenuItemClickListener(this);

        progressBar = view.findViewById(R.id.progressBar);

        setItems();

        return view;
    }

    private void priceListing() {
        PriceService service = RetrofitClientInstance.getRetrofitInstance().create(PriceService.class);
        Call<Prices> pricesCall = service.getPriceList(Utilities.LANGUAGE);
        pricesCall.enqueue(new Callback<Prices>() {
            @Override
            public void onResponse(Call<Prices> call, Response<Prices> response) {
                progressDialog.dismiss();
                generatePrices(response.body().getPrices());
            }

            @Override
            public void onFailure(Call<Prices> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        new ProgressBar(getContext());

    }

    private void generatePrices(List<Price> priceList) {
        prices = priceList;
        if (MainActivity.locationInfo == null) {
                dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setTitle("Error!!!");
                dialogBuilder.setMessage("Problem getting your location\n\nData might be incorrect!");
                dialogBuilder.setPositiveButton("CONTINUE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
        } else {
            marketCardAdapter = new MarketCardAdapter(priceList, getContext());
            int resId = R.anim.layout_animation_down_to_up;
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
            mRecyclerView.setLayoutAnimation(animation);
            mRecyclerView.setAdapter(marketCardAdapter);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.market_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_market_crops);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

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

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Map<Integer, String> map = new HashMap<>();
        List<Price> newPriceList = new ArrayList<>();

        for (int i = 0; i < prices.size(); i++) {
            map.put(i, prices.get(i).getCulture());
        }

        Set<Map.Entry<Integer, String>> set = map.entrySet();

        for (Map.Entry<Integer, String> entry : set) {
            if (entry.getValue().substring(0, s.length()).equalsIgnoreCase(s)) {
                newPriceList.add(prices.get(entry.getKey()));
            }
        }

        marketCardAdapter.updateList(newPriceList);
        return true;
    }
}
