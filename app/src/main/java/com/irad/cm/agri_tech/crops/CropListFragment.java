package com.irad.cm.agri_tech.crops;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.irad.cm.agri_tech.MainActivity;
import com.irad.cm.agri_tech.R;
import com.irad.cm.agri_tech.Utilities;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CropListFragment extends Fragment implements SearchView.OnQueryTextListener {
    MyAdapter myAdapter;
    List<FlowerData> mFlowerList;
    private SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;
    Utilities utilities;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crop_list, container, false);

        RecyclerView mRecyclerView = view.findViewById(R.id.cropList_recyclerview);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mFlowerList = new ArrayList<>();
        FlowerData mFlowerData = new FlowerData("Barley", "Barley",
                R.drawable.barley);
        mFlowerList.add(mFlowerData);
        mFlowerData = new FlowerData("Oranges", "Oranges",
                R.drawable.orange);
        mFlowerList.add(mFlowerData);
        mFlowerData = new FlowerData("Banana", "Banana",
                R.drawable.banana);
        mFlowerList.add(mFlowerData);
        mFlowerData = new FlowerData("Pear", "Pear",
                R.drawable.pear);
        mFlowerList.add(mFlowerData);
        mFlowerData = new FlowerData("Pawpaw", "Pawpaw",
                R.drawable.pawpaw);
        mFlowerList.add(mFlowerData);
        mFlowerData = new FlowerData("Pineapple", "Pineapple",
                R.drawable.pineapple);
        mFlowerList.add(mFlowerData);
        mFlowerData = new FlowerData("Corn", "Maize",
                R.drawable.corn);
        mFlowerList.add(mFlowerData);
        mFlowerData = new FlowerData("Carrot", "Carrot",
                R.drawable.carrot);
        mFlowerList.add(mFlowerData);
        mFlowerData = new FlowerData("Watermelon", "Watermelon",
                R.drawable.watermelon);
        mFlowerList.add(mFlowerData);
        mFlowerData = new FlowerData("Apple", "Apple",
                R.drawable.apple);
        mFlowerList.add(mFlowerData);
        mFlowerData = new FlowerData("Cocoa", "Cocoa",
                R.drawable.cocoa);
        mFlowerList.add(mFlowerData);
        mFlowerData = new FlowerData("Fish", "Fish",
                R.drawable.fish);
        mFlowerList.add(mFlowerData);
        mFlowerData = new FlowerData("Carrot", "Carrot",
                R.drawable.carrot);
        mFlowerList.add(mFlowerData);
        mFlowerData = new FlowerData("Watermelon", "Watermelon",
                R.drawable.watermelon);
        mFlowerList.add(mFlowerData);
        mFlowerData = new FlowerData("Apple", "Apple",
                R.drawable.apple);
        mFlowerList.add(mFlowerData);

        myAdapter = new MyAdapter(getActivity(), mFlowerList);
        mRecyclerView.setAdapter(myAdapter);

        return view;
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

        List<FlowerData> newFlowerList = new ArrayList<>();
        List<String> flowerNames = new ArrayList<>();

        for (FlowerData flowerData : mFlowerList) {
            flowerNames.add(flowerData.getFlowerName());
        }

        for (String flowerName : flowerNames) {
            if (flowerName.contains(userInput)) {
                String res = flowerName.toLowerCase();

                newFlowerList.add(new FlowerData(flowerName, flowerName, getContext().getResources().getIdentifier(res, "drawable", getContext().getPackageName())));

            }
        }

        myAdapter.updateList(newFlowerList);

        return true;
    }
}
