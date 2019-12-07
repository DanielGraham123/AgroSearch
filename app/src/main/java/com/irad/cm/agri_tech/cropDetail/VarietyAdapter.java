package com.irad.cm.agri_tech.cropDetail;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.irad.cm.agri_tech.MainActivity;
import com.irad.cm.agri_tech.R;
import com.irad.cm.agri_tech.varietyDetail.VarietyDetailActivity;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static maes.tech.intentanim.CustomIntent.customType;

public class VarietyAdapter extends RecyclerView.Adapter<VarietyViewHolder> {
    public Context mContext;
    public static List<Seed> seedList;
    String region;
    AlertDialog.Builder dialogBuilder;

    public VarietyAdapter(Context mContext, List<Seed> seedList) {
        this.mContext = mContext;
        this.seedList = seedList;
        dialogBuilder = new AlertDialog.Builder(this.mContext);
    }

    @NonNull
    @Override
    public VarietyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.varieties_layout, parent, false);
        return new VarietyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final VarietyViewHolder holder, final int position) {
        holder.varietyName.setText(seedList.get(position).getName());
        holder.otherName.setText(seedList.get(position).getOtherName());

        try {
            if (MainActivity.locationInfo.split(", ").length == 2) {
                region = MainActivity.locationInfo.split(", ")[0];
            } else if (MainActivity.locationInfo.split(", ").length == 3){
                region = MainActivity.locationInfo.split(", ")[2];
                if (region.contains(" ")) {
                    region = region.split(" ")[region.split(" ").length-1];
                }
            }
        } catch (Exception ex) {
            dialogBuilder.setMessage("Unable to get your location\n\nPlease try restarting the app...");
            dialogBuilder.setPositiveButton("RESTART", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);
                }
            });

            dialogBuilder.setNegativeButton("CONTINUE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            AlertDialog dialog = dialogBuilder.create();
            dialog.show();

        }

        if (!seedList.get(position).getAdaptationZone().contains(region)) {
            holder.adaptedText.setText(mContext.getString(R.string.not_adapted));
        } else {
            holder.adaptedText.setText(mContext.getString(R.string.adapted));
        }

        Picasso.Builder builder = new Picasso.Builder(mContext);
        builder.downloader(new OkHttp3Downloader(mContext));
        builder.build().load(MainActivity.SITE_NAME + seedList.get(position).getImage())
//                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.carrot)
                .into(holder.mImage);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewMoreActivity(holder, position);
            }
        });

        holder.viewMoreBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                viewMoreActivity(holder, position);
            }
        });

    }

    public void viewMoreActivity(final VarietyViewHolder holder, int position) {
        Intent mIntent = new Intent(mContext, VarietyDetailActivity.class);
        mIntent.putExtra("title", StringUtils.capitalize(seedList.get(holder.getAdapterPosition()).getName()));
        mIntent.putExtra("position", holder.getAdapterPosition());
        mContext.startActivity(mIntent);
        customType(mContext, "fadein-to-fadeout");
    }

    @Override
    public int getItemCount() {
        return seedList.size();
    }

    public void updateList(List<Seed> newCropList) {
        seedList = new ArrayList<>();
        seedList.addAll(newCropList);
        notifyDataSetChanged();
    }
}

class VarietyViewHolder extends RecyclerView.ViewHolder {

    ImageView mImage;
    TextView varietyName, otherName, adaptedText;
    Button viewMoreBtn;
    CardView mCardView;

    VarietyViewHolder(View itemView) {
        super(itemView);

        mImage = itemView.findViewById(R.id.seed_image);
        varietyName = itemView.findViewById(R.id.variety_text);
        otherName = itemView.findViewById(R.id.other_name_text);
        adaptedText = itemView.findViewById(R.id.adapted_text);
        viewMoreBtn = itemView.findViewById(R.id.view_more_btn);

        mCardView = itemView.findViewById(R.id.variety_cardview);


    }
}

