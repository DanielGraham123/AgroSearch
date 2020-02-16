package com.irad.cm.agri_tech.cropDetail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.irad.cm.agri_tech.MainActivity;
import com.irad.cm.agri_tech.R;
import com.irad.cm.agri_tech.Utilities;
import com.irad.cm.agri_tech.crops.CropsActivity;

import org.apache.commons.lang3.StringUtils;

import static maes.tech.intentanim.CustomIntent.customType;

public class CropDetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    TabItem descTab, varietiesTab, diseaseTab, papersTab;
    Utilities utilities;
    CropsActivity cropsActivity;
    public static String locationInfo;
    public static String slug;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent;
        switch (menuItem.getItemId()) {
            case R.id.annual:
                intent = new Intent(this, CropsActivity.class);
                intent.putExtra("menu", "annual");
                startActivity(intent);
                break;
            case R.id.perennial:
                intent = new Intent(this, CropsActivity.class);
                intent.putExtra("menu", "perennial");
                startActivity(intent);
                break;
            case R.id.fisheries:
                intent = new Intent(this, CropsActivity.class);
                intent.putExtra("menu", "fisheries");
                startActivity(intent);
                break;
            case R.id.crop_list:
                intent = new Intent(this, CropsActivity.class);
                intent.putExtra("menu", "croplist");
                startActivity(intent);
                break;
            case R.id.home:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                customType(CropDetailActivity.this,"bottom-to-up");
                this.finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        toolbar = findViewById(R.id.crops_toolbar);
        viewPager = findViewById(R.id.crop_viewPager);

        cropsActivity = new CropsActivity();

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            toolbar.setTitle(StringUtils.capitalize(mBundle.getString("title")));
            slug = mBundle.getString("slug");
//            mFlower.setImageResource(mBundle.getInt("Image"));
//            mDescription.setText(mBundle.getString("Description"));
        }
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        tabLayout = findViewById(R.id.crop_tabLayout);
        descTab = findViewById(R.id.crop_description);
        varietiesTab = findViewById(R.id.crop_variety);
        diseaseTab = findViewById(R.id.crop_disease);
        papersTab = findViewById(R.id.crop_papers);

//        final ViewPager viewPager = findViewById(R.id.crop_viewPager);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState(); // take care of rotating the hamburger icon

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.crop_list);
        }

        utilities = new Utilities(this, this);

        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

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

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.header_menu1, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.language:
                utilities.showChangeLanguageDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onPause() {
        super.onPause();
        editor = sharedPref.edit();
        editor.putString(getString(R.string.cropDetail_last_location), MainActivity.locationInfo);
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.locationInfo = sharedPref.getString(getString(R.string.cropDetail_last_location), "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
