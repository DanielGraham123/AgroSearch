package com.irad.cm.agri_tech.marketPrice;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.irad.cm.agri_tech.cropDetail.DescriptionFragment;
import com.irad.cm.agri_tech.cropDetail.DiseasesFragment;
import com.irad.cm.agri_tech.cropDetail.PapersFragment;
import com.irad.cm.agri_tech.cropDetail.VarietiesFragment;

public class MarketPageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public MarketPageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new MarketPriceFragment();
            case 1:
                return new SeedPriceFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
