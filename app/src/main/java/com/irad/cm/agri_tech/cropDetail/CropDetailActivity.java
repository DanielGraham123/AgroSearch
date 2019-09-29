package com.irad.cm.agri_tech.cropDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.irad.cm.agri_tech.MainActivity;
import com.irad.cm.agri_tech.R;
import com.irad.cm.agri_tech.Utilities;
import com.irad.cm.agri_tech.crops.AnnualCropsFragment;
import com.irad.cm.agri_tech.crops.CropListFragment;
import com.irad.cm.agri_tech.crops.CropsActivity;
import com.irad.cm.agri_tech.crops.FisheriesFragment;
import com.irad.cm.agri_tech.crops.PerennialCropsFragment;

public class CropDetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    TabItem descTab, varietiesTab, diseaseTab, papersTab;
    Utilities utilities;
    CropsActivity cropsActivity;

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

        cropsActivity = new CropsActivity();

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            toolbar.setTitle(mBundle.getString("title"));
//            mFlower.setImageResource(mBundle.getInt("Image"));
//            mDescription.setText(mBundle.getString("Description"));
        }

        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        tabLayout = findViewById(R.id.crop_tabLayout);
        descTab = findViewById(R.id.crop_description);
        varietiesTab = findViewById(R.id.crop_variety);
        diseaseTab = findViewById(R.id.crop_disease);
        papersTab = findViewById(R.id.crop_papers);

        ViewPager viewPager = findViewById(R.id.crop_viewPager);

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
}
