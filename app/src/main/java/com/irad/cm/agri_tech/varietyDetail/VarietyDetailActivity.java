package com.irad.cm.agri_tech.varietyDetail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.irad.cm.agri_tech.MainActivity;
import com.irad.cm.agri_tech.R;
import com.irad.cm.agri_tech.Utilities;
import com.irad.cm.agri_tech.cropDetail.Seed;
import com.irad.cm.agri_tech.cropDetail.VarietyAdapter;
import com.irad.cm.agri_tech.crops.CropsActivity;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static maes.tech.intentanim.CustomIntent.customType;

public class VarietyDetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    Utilities utilities;
    TextView otherNameText, seedPriceText, yieldText, maturityText, particularityText, zonesText, varietyDescText;
    ImageView imageView;
    TableLayout tableLayout;
    int position;
    List<Seed> varietyDetails;

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
                customType(VarietyDetailActivity.this, "bottom-to-up");
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
        setContentView(R.layout.activity_variety_detail);

        otherNameText = findViewById(R.id.other_variety_name);
        imageView = findViewById(R.id.variety_image);
        seedPriceText = findViewById(R.id.seed_price_text);
        yieldText = findViewById(R.id.variety_yield_text);
        maturityText = findViewById(R.id.variety_maturity_text);
        particularityText = findViewById(R.id.variety_particularity_text);
        zonesText = findViewById(R.id.variety_zone_text);
        varietyDescText = findViewById(R.id.variety_desc_text);
        tableLayout = findViewById(R.id.distribution_table);

        toolbar = findViewById(R.id.variety_detial_toolbar);

        varietyDetails = new ArrayList<>();
        utilities = new Utilities(this, this);

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            toolbar.setTitle(mBundle.getString("title"));
//            mFlower.setImageResource(mBundle.getInt("Image"));
//            mDescription.setText(mBundle.getString("Description"));
        }
        position = mBundle.getInt("position");

        varietyDetails = VarietyAdapter.seedList;

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

        presentDetails();
    }

    private void presentDetails() {
        String zones = "";

        if (varietyDetails.get(position).getOtherName() == null) {
            otherNameText.setText(varietyDetails.get(position).getName());
        } else {
            otherNameText.setText(varietyDetails.get(position).getOtherName());
        }
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this));
        builder.build().load(MainActivity.SITE_URL + varietyDetails.get(position).getImage())
//                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.carrot)
                .into(imageView);
        seedPriceText.setText(varietyDetails.get(position).getSeedPrice().getPrice() + " FCFA/" + varietyDetails.get(position).getSeedPrice().getMeasure());

        yieldText.setText(varietyDetails.get(position).getRendement() == null ? getResources().getString(R.string.unspecified) : varietyDetails.get(position).getRendement());
        maturityText.setText(varietyDetails.get(position).getMaturity() == null ? getResources().getString(R.string.unspecified) : varietyDetails.get(position).getMaturity());
        particularityText.setText(varietyDetails.get(position).getParticularity() == null ? getResources().getString(R.string.unspecified) : varietyDetails.get(position).getParticularity());
        Spanned convertHtml = Html.fromHtml(varietyDetails.get(position).getDesc() == null ? getResources().getString(R.string.unspecified) : varietyDetails.get(position).getDesc());
        varietyDescText.setText(convertHtml);

        if (varietyDetails.get(position).getAdaptationZone().size() == 0) {
            zonesText.setText(getResources().getString(R.string.unspecified));
        } else {
            List<String> favZones = varietyDetails.get(position).getAdaptationZone();

            for (int i = 0; i < favZones.size(); i++) {
                zones += (i == (favZones.size() - 1) ? favZones.get(i) : favZones.get(i) + ", ");
            }
            zonesText.setText(zones);
        }



        TextView[] locationsArray = new TextView[varietyDetails.get(position).getDistribution().size()];
        TextView[] availArray = new TextView[varietyDetails.get(position).getDistribution().size()];

        TableRow[] tableRow = new TableRow[varietyDetails.get(position).getDistribution().size()];

        for (int i = 0; i < varietyDetails.get(position).getDistribution().size(); i++) {
            tableRow[i] = new TableRow(this);
            tableRow[i].setId(i + 10);
            TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            tableRow[i].setLayoutParams(params);
            tableRow[i].setGravity(Gravity.LEFT);

            locationsArray[i] = new TextView(this);
            locationsArray[i].setId(i + 20);

            String locations = "";
            locations += varietyDetails.get(position).getDistribution().get(i).getLocation().getName() + ", " + varietyDetails.get(position).getDistribution().get(i).getLocation().getAddress() + ", " + varietyDetails.get(position).getDistribution().get(i).getLocation().getPhoneNumber();

            locationsArray[i].setText(locations);
            locationsArray[i].setWidth(370);

            locationsArray[i].setTextColor(Color.parseColor("#2B2A2A"));
            locationsArray[i].setPadding(5, 5, 5, 5);
            locationsArray[i].setGravity(Gravity.LEFT);
            TableRow.LayoutParams textParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
            locationsArray[i].setLayoutParams(textParams);

            tableRow[i].addView(locationsArray[i]);

            availArray[i] = new TextView(this);
            availArray[i].setId(i + 30);
            if (varietyDetails.get(position).getDistribution().get(i).getAvailable()) {
                availArray[i].setText(getResources().getString(R.string.available));
            } else {
                availArray[i].setText(getResources().getString(R.string.unavailable));
            }
            availArray[i].setTextColor(Color.parseColor("#2B2A2A"));
            availArray[i].setPadding(5, 5, 5, 5);
            availArray[i].setGravity(Gravity.LEFT);
            availArray[i].setLayoutParams(textParams);
            tableRow[i].addView(availArray[i]);

            tableLayout.addView(tableRow[i]);
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
