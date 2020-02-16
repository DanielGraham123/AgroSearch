package com.irad.cm.agri_tech.plantAndDisease;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.irad.cm.agri_tech.MainActivity;
import com.irad.cm.agri_tech.R;
import com.irad.cm.agri_tech.Utilities;
import com.irad.cm.agri_tech.fragments.LocateMeFragment;
import com.irad.cm.agri_tech.fragments.SignupFragment;

import static maes.tech.intentanim.CustomIntent.customType;

public class PlantAndDiseaseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    public static TabLayout tabLayout;
    public static ViewPager viewPager;

    MainActivity mainActivity;
    Utilities utilities;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_and_disease);

        toolbar = findViewById(R.id.main_toolbar);
        tabLayout = findViewById(R.id.pltd_tabLayout);

        toolbar.setTitle(getResources().getString(R.string.module4));
        this.setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setBackgroundColor(fetchColor(R.color.colorPrimary, this));
        bottomNav.setItemTextColor(ColorStateList.valueOf(Color.WHITE));
        bottomNav.setItemIconTintList(ColorStateList.valueOf(Color.WHITE));
        bottomNav.setItemTextAppearanceActive(getResources().getColor(R.color.activeAppearance));
        bottomNav.setItemTextAppearanceInactive(Color.WHITE);
        bottomNav.setSelectedItemId(R.id.action_home);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        mainActivity = new MainActivity();
        utilities = new Utilities(this, this);

        viewPager = findViewById(R.id.pltd_viewPager);
        PltdPageAdapter pageAdapter = new PltdPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public int fetchColor(@ColorRes int color, Context context) {
        return ContextCompat.getColor(context, color);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.action_home:
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            customType(PlantAndDiseaseActivity.this,"right-to-left");
                            finish();
                            break;
                        case R.id.action_signup:
                            selectedFragment = new SignupFragment();
                            break;
                        case R.id.action_location:
                            selectedFragment = new LocateMeFragment();
                            break;
                    }
                    return true;
                }
            };

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
