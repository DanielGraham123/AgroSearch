package com.irad.cm.agri_tech.crops;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.irad.cm.agri_tech.MainActivity;
import com.irad.cm.agri_tech.R;
import com.irad.cm.agri_tech.Utilities;

import static maes.tech.intentanim.CustomIntent.customType;

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
                customType(CropsActivity.this,"bottom-to-up");
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
