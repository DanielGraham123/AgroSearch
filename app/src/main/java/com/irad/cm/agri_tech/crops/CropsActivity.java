package com.irad.cm.agri_tech.crops;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.irad.cm.agri_tech.MainActivity;
import com.irad.cm.agri_tech.R;
import com.irad.cm.agri_tech.Utilities;

public class CropsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    Utilities utilities;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.annual:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AnnualCropsFragment()).commit();
                break;
            case R.id.perennial:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PerennialCropsFragment()).commit();
                break;
            case R.id.fisheries:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FisheriesFragment()).commit();
                break;
            case R.id.crop_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CropListFragment()).commit();
                break;
            case R.id.home:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                this.finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    MainActivity mainActivity;

    DrawerLayout drawerLayout;

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops);

        toolbar = findViewById(R.id.crops_toolbar);
        toolbar.setTitle(R.string.crops_title);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState(); // take care of rotating the hamburger icon

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CropListFragment()).commit();
            navigationView.setCheckedItem(R.id.crop_list);
        }
        mainActivity = new MainActivity();
        utilities = new Utilities(this, this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("menu").equals("croplist")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CropListFragment()).commit();
            }
            if (bundle.getString("menu").equals("fisheries")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FisheriesFragment()).commit();
            }
            if (bundle.getString("menu").equals("annual")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AnnualCropsFragment()).commit();
            }
            if (bundle.getString("menu").equals("perennial")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PerennialCropsFragment()).commit();
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
