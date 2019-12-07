package com.irad.cm.agri_tech.crops;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.irad.cm.agri_tech.MainActivity;
import com.irad.cm.agri_tech.R;
import com.irad.cm.agri_tech.RetrofitClientInstance;
import com.irad.cm.agri_tech.cropDetail.CropDetailActivity;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static maes.tech.intentanim.CustomIntent.customType;

public class AnnualCropsAdapter extends RecyclerView.Adapter<CultureAnnuelleViewHolder> {

    public Context mContext;
    public static List<CultureAnnuelle> cropList;

    public AnnualCropsAdapter(Context mContext, List<CultureAnnuelle> cropList) {
        this.mContext = mContext;
        this.cropList = cropList;
    }

    @NonNull
    @Override
    public CultureAnnuelleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.crop_layout, parent, false);
        return new CultureAnnuelleViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final CultureAnnuelleViewHolder holder, final int position) {
        holder.mTitle.setText(cropList.get(position).getName());

        Picasso.Builder builder = new Picasso.Builder(mContext);
        builder.downloader(new OkHttp3Downloader(mContext));
        builder.build().load(MainActivity.SITE_NAME+cropList.get(position).getImage())
//                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.mImage);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(mContext, CropDetailActivity.class);
                mIntent.putExtra("title", cropList.get(holder.getAdapterPosition()).getName());
                mIntent.putExtra("slug", cropList.get(holder.getAdapterPosition()).getSlug());
                mContext.startActivity(mIntent);
                customType(mContext, "fadein-to-fadeout");
            }
        });

    }

    @Override
    public int getItemCount() {
        return cropList.size();
    }

    public void updateList(List<CultureAnnuelle> newCropList) {
        cropList = new ArrayList<>();
        cropList.addAll(newCropList);
        notifyDataSetChanged();
    }
}

class CultureAnnuelleViewHolder extends RecyclerView.ViewHolder {

    ImageView mImage;
    TextView mTitle;
    CardView mCardView;

    CultureAnnuelleViewHolder(View itemView) {
        super(itemView);

        mImage = itemView.findViewById(R.id.ivImage);
        mTitle = itemView.findViewById(R.id.tvTitle);

        mCardView = itemView.findViewById(R.id.crop_cardview);


    }
}
