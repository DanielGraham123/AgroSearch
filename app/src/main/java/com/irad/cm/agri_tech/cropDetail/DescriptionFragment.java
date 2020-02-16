package com.irad.cm.agri_tech.cropDetail;


import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.irad.cm.agri_tech.MainActivity;
import com.irad.cm.agri_tech.R;
import com.irad.cm.agri_tech.RetrofitClientInstance;
import com.irad.cm.agri_tech.Utilities;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DescriptionFragment extends Fragment {

    ProgressDialog progressDialog;
    ProgressBar progressBar;
    TextView introText, zoneText, campaignText, campaignRegions, campaignFrom, campaignTo, fileText;
    ImageView imageView;
    TableLayout tableLayout;
    Button fileDownloadBtn;
    Utilities utilities;

    public DescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_description, container, false);

        utilities = new Utilities(getContext(), getActivity());
        introText = view.findViewById(R.id.desc_text);
        zoneText = view.findViewById(R.id.zone_text);
        campaignText = view.findViewById(R.id.campaign_text);
        campaignRegions = view.findViewById(R.id.campaign_regions);
        campaignFrom = view.findViewById(R.id.campaign_from);
        campaignTo = view.findViewById(R.id.campaign_to);
//        priceText = view.findViewById(R.id.price_text);
//        fileText = view.findViewById(R.id.file_text);
        tableLayout = view.findViewById(R.id.market_price_table);
        imageView = view.findViewById(R.id.desc_img);
        progressBar = view.findViewById(R.id.progressBar);

        fileDownloadBtn = view.findViewById(R.id.file_download_btn);

        getActivity().registerReceiver(utilities.onDownloadComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        setItems();

        return view;
    }

    private void setItems() {
        progressDialog = ProgressDialog.show(getContext(), "", "Loading...");

        new Thread() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cropDescription();
                    }
                });
                progressBar.setVisibility(View.GONE);
            }
        }.start();
    }

    private void cropDescription() {
        CropDetailService service = RetrofitClientInstance.getRetrofitInstance().create(CropDetailService.class);
        Call<CropDetailList> call = service.getCropDetailList(CropDetailActivity.slug, Utilities.LANGUAGE);
        call.enqueue(new Callback<CropDetailList>() {
            @Override
            public void onResponse(Call<CropDetailList> call, Response<CropDetailList> response) {
                progressDialog.dismiss();
                Description description = response.body().getDescription();
                if (description.toString().contains("{}") || description.toString().contains("[]")) {

                } else {
                    generateCropDetail(description);
                }
            }

            @Override
            public void onFailure(Call<CropDetailList> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void generateCropDetail(final Description description) {
        Picasso.Builder builder = new Picasso.Builder(getActivity());
        builder.downloader(new OkHttp3Downloader(getContext()));
        builder.build().load(MainActivity.SITE_URL+description.getImage())
//                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.corn)
                .into(imageView);
        Spanned convertHTML =  Html.fromHtml(description.getDesc());
        introText.setText(convertHTML);

        String zones = "";

        List<String> favZones = description.getFavorableZone();

        for (int i = 0; i < favZones.size(); i++) {
            zones += (i==(favZones.size()-1) ? favZones.get(i) : favZones.get(i)+", ");
        }

        zoneText.setText(zones);

        List<Campaign> campaigns = description.getCampaigns();
        if (campaigns.size() != 0) {
            campaignText.setText(campaigns.get(0).getCampaignName());

            List<String> campRegions = campaigns.get(0).getRegions();
            String regions = "";
            for (int i = 0; i < campRegions.size(); i++) {
                regions += (i==(campRegions.size()-1) ? campRegions.get(i) : campRegions.get(i)+", ");
            }
            campaignRegions.setText(regions);

            campaignFrom.setText(campaigns.get(0).getDu());
            campaignTo.setText(campaigns.get(0).getAu());
        }

        // Market price
        TextView[] regionArray = new TextView[description.getMarketPrice().size()];
        TextView[] priceArray = new TextView[description.getMarketPrice().size()];

        TableRow[] tableRow = new TableRow[description.getMarketPrice().size()];

        for (int i = 0; i < description.getMarketPrice().size(); i++) {
            if (description.getMarketPrice().size() != 0 || description.getCampaigns().size() != 0) {
                tableRow[i] = new TableRow(getContext());
                tableRow[i].setId(i+10);
                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.FILL_HORIZONTAL;
                tableRow[i].setLayoutParams(params);
                tableRow[i].setGravity(Gravity.FILL);

                regionArray[i] = new TextView(getContext());
                regionArray[i].setId(i+20);
                List<String> marketRegions = description.getMarketPrice().get(i).getRegion();
                String region = "";
                if (marketRegions != null) {
                    for (int j = 0; j < marketRegions.size(); j++) {
                        region += (j==(marketRegions.size()-1) ? marketRegions.get(j) : marketRegions.get(j)+", ");
                    }
                    regionArray[i].setText(region);
                }

                regionArray[i].setTextColor(Color.parseColor("#2B2A2A"));
                regionArray[i].setPadding(15, 15, 15, 15);
                regionArray[i].setWidth(300);
                tableRow[i].addView(regionArray[i]);

                priceArray[i] = new TextView(getContext());
                priceArray[i].setId(i+30);
                priceArray[i].setText(description.getMarketPrice().get(i).getPrice()+"/"+description.getMarketPrice().get(i).getMeasure());
                priceArray[i].setTextColor(Color.parseColor("#2B2A2A"));
                priceArray[i].setTypeface(null, Typeface.BOLD);
                priceArray[i].setPadding(15, 15, 15, 15);
                tableRow[i].addView(priceArray[i]);

                tableLayout.addView(tableRow[i]);
            }
        }

//        fileText.setText(description.getFicheTechnique());
        fileDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        utilities.downloadFile(MainActivity.SITE_URL+description.getFicheTechnique());
                    }
                }).start();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(utilities.onDownloadComplete);
    }

}
