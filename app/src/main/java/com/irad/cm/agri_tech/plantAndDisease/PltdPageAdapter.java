package com.irad.cm.agri_tech.plantAndDisease;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PltdPageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    PltdPageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }


    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new PlantsFragment();
            case 1:
                return new PltDiseasesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
