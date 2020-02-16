package com.irad.cm.agri_tech.diseaseDetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.irad.cm.agri_tech.MainActivity;
import com.irad.cm.agri_tech.R;
import com.irad.cm.agri_tech.Utilities;
import com.irad.cm.agri_tech.cropDetail.Disease;
import com.irad.cm.agri_tech.cropDetail.DiseaseAdapter;
import com.irad.cm.agri_tech.crops.CropsActivity;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static maes.tech.intentanim.CustomIntent.customType;

public class DiseaseDetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar;
    TabLayout tabLayout;
    TabItem symptomTab, solutionTab;
    Utilities utilities;
    TextView diseaseName, agentName;
    ImageView imageView;
    int position;
    List<Disease> diseases;
    DrawerLayout drawerLayout;
    ViewPager viewPager;

    public static List<String> symptoms;
    public static List<String> solutions;
    public static String solutionHTML;
    public static String symptomHTML;

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
                customType(this, "bottom-to-up");
                this.finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_detail);

        diseaseName = findViewById(R.id.disease_name);
        agentName = findViewById(R.id.agent_name);
        imageView = findViewById(R.id.disease_image);

        toolbar = findViewById(R.id.disease_detail_toolbar);
        tabLayout = findViewById(R.id.disease_detail_tabLayout);
        viewPager = findViewById(R.id.disease_detail_viewPager);

        symptomTab = findViewById(R.id.symptom_tab);
        solutionTab = findViewById(R.id.solution_tab);

        diseases = new ArrayList<>();
        utilities = new Utilities(this, this);

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            toolbar.setTitle(mBundle.getString("title"));
//            mFlower.setImageResource(mBundle.getInt("Image"));
//            mDescription.setText(mBundle.getString("Description"));
        }
        position = mBundle.getInt("position");

        diseases = DiseaseAdapter.diseaseList;
        symptoms = new ArrayList<>();
        solutions = new ArrayList<>();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState(); // take care of rotating the hamburger icon

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.crop_list);
        }

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        DiseasePageAdapter pageAdapter = new DiseasePageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

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

        presentDetails();

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

    private void presentDetails() {
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this));
        builder.build().load(MainActivity.SITE_URL + diseases.get(position).getImage())
//                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.carrot)
                .into(imageView);

        diseaseName.setText(diseases.get(position).getDisease().getName());
        agentName.setText(diseases.get(position).getDisease().getAgent());

        String html = diseases.get(position).getDisease().getSymptom();
//        symptomHTML = diseases.get(position).getDisease().getSymptom();

        org.jsoup.nodes.Document doc = Jsoup.parse(html);

        Elements listTags = doc.select("ul li");
        for (Element listTag : listTags) {
            symptoms.add(listTag.text());
        }

//        html = diseases.get(position).getDisease().getSolution();
//
//        doc = Jsoup.parse(html);
//        Elements pTags = doc.select("p");
//        for (Element pTag : pTags) {
//            solutions.add(pTag.text());
//        }
        solutionHTML =  diseases.get(position).getDisease().getSolution();



    }
}
