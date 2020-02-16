package com.irad.cm.agri_tech.marketPrice;

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

public class MarketPriceActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;

//    public static ProgressDialog progressDialog;

    MainActivity mainActivity;
    Utilities utilities;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_price);

        toolbar = findViewById(R.id.main_toolbar);
        tabLayout = findViewById(R.id.market_tabLayout);

        toolbar.setTitle(getResources().getString(R.string.market_price));
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

        final ViewPager viewPager = findViewById(R.id.market_viewPager);
        MarketPageAdapter pageAdapter = new MarketPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    tab.setIcon(getResources().getDrawable(R.drawable.moneybag));
                } else if (tab.getPosition() == 1) {
                    tab.setIcon(getResources().getDrawable(R.drawable.sproutmoney));
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    tab.setIcon(getResources().getDrawable(R.drawable.moneybag2));
                } else if (tab.getPosition() == 1) {
                    tab.setIcon(getResources().getDrawable(R.drawable.sproutmoney2));
                }
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
                            customType(MarketPriceActivity.this,"right-to-left");
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.market_menu, menu);
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.language:
//                utilities.showChangeLanguageDialog();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

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
