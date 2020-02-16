package com.irad.cm.agri_tech.diseaseDetail;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class DiseasePageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public DiseasePageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                System.out.println("SYMPTOM CLICKED");
                return new SymptomFragment();
            case 1:
                System.out.println("SOLUTION CLICKED");
                return new SolutionFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
