package com.irad.cm.agri_tech.marketPrice.seedPriceDetail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.abdularis.civ.CircleImageView;
import com.irad.cm.agri_tech.MainActivity;
import com.irad.cm.agri_tech.R;
import com.irad.cm.agri_tech.marketPrice.Distribution;
import com.irad.cm.agri_tech.marketPrice.SeedPrice;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class SeedPriceDetialActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView seedNameText, otherNameText, priceText, measureText;
    private CircleImageView imageView;
    private LinearLayout layout;
    private TableLayout distributionTable, seedPriceTable;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seed_price_detial);

        toolbar = findViewById(R.id.main_toolbar);
        seedNameText = findViewById(R.id.seed_detail_name);
        otherNameText = findViewById(R.id.seed_detail_other);
        imageView = findViewById(R.id.seed_detail_image);
        distributionTable = findViewById(R.id.distribution_detail_table);
        seedPriceTable = findViewById(R.id.price_detail_table);
        layout = findViewById(R.id.seed_detail_layout);

        SeedPrice seedPrice = (SeedPrice) getIntent().getSerializableExtra("seedPrice");

        toolbar.setTitle(StringUtils.capitalize(seedPrice.getName()) + " Price");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        seedNameText.setText(StringUtils.capitalize(seedPrice.getName()));
        if (seedPrice.getOtherName() != null) {
            otherNameText.setText("Aussi appele " + seedPrice.getOtherName());
        } else {
            LinearLayout.LayoutParams newLayout =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            newLayout.setMargins(0, 35, 0, 0);
            seedNameText.setLayoutParams(newLayout);
            otherNameText.setVisibility(View.GONE);
        }
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this));
        builder.build().load(MainActivity.SITE_NAME + seedPrice.getImage())
//                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.carrot)
                .into(imageView);
        TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams textParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if (seedPrice.getSeedPrice() != null) {
            int id = 0;
            priceText = new TextView(this);
            measureText = new TextView(this);
            priceText.setId(id+1);
            priceText.setText(seedPrice.getSeedPrice().getPrice()==null ? "":seedPrice.getSeedPrice().getPrice().toString());
            measureText.setId(id+2);
            measureText.setText(seedPrice.getSeedPrice().getMeasure());

            TableRow priceTableRow = new TableRow(this);
            priceTableRow.setId(id+11);
            priceTableRow.setLayoutParams(params);

            priceText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            priceText.setPadding(10, 10, 10, 10);
            priceText.setGravity(Gravity.CENTER_HORIZONTAL);
            priceText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f);
            priceText.setLayoutParams(textParams);
            priceTableRow.addView(priceText);

            measureText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            measureText.setPadding(10, 10, 10, 10);
            measureText.setGravity(Gravity.CENTER_HORIZONTAL);
            measureText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f);
            measureText.setLayoutParams(textParams);
            priceTableRow.addView(measureText);

            seedPriceTable.addView(priceTableRow);

        }
        List<Distribution> distributions = seedPrice.getDistribution();
        TextView[] locationsArray = new TextView[distributions.size()];
        TextView[] availArray = new TextView[distributions.size()];

        TableRow[] tableRow = new TableRow[distributions.size()];

        for (int i = 0; i < distributions.size(); i++) {
            tableRow[i] = new TableRow(this);
            tableRow[i].setId(i+10);
            tableRow[i].setLayoutParams(params);

            locationsArray[i] = new TextView(this);
            locationsArray[i].setId(i+20);

            String locations = "";
            locations += distributions.get(i).getLocation().getName()+", "+distributions.get(i).getLocation().getAddress()+", "+distributions.get(i).getLocation().getPhoneNumber();

            locationsArray[i].setText(locations);

            locationsArray[i].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            locationsArray[i].setPadding(10, 10, 10, 10);
            locationsArray[i].setGravity(Gravity.CENTER_HORIZONTAL);
            locationsArray[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f);
            locationsArray[i].setLayoutParams(textParams);
            tableRow[i].addView(locationsArray[i]);

            availArray[i] = new TextView(this);
            availArray[i].setId(i+30);
            if (distributions.get(i).getAvailable()) {
                availArray[i].setText(getResources().getString(R.string.available));
            } else {
                availArray[i].setText(getResources().getString(R.string.unavailable));
            }
            availArray[i].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            availArray[i].setPadding(10, 10, 10, 10);
            availArray[i].setGravity(Gravity.CENTER_HORIZONTAL);
            availArray[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f);
            availArray[i].setLayoutParams(textParams);
            tableRow[i].addView(availArray[i]);

            distributionTable.addView(tableRow[i]);
        }

    }
}
