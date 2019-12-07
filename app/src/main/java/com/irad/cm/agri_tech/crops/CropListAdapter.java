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

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static maes.tech.intentanim.CustomIntent.customType;

public class CropListAdapter extends RecyclerView.Adapter<AllViewHolder> {

    public Context mContext;
    public static List<All> cropList;

    public CropListAdapter(Context mContext, List<All> cropList) {
        this.mContext = mContext;
        this.cropList = cropList;
    }

    @NonNull
    @Override
    public AllViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.crop_layout, parent, false);
        return new AllViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final AllViewHolder holder, final int position) {
        holder.mTitle.setText(StringUtils.capitalize(cropList.get(position).getName()));

        Picasso.Builder builder = new Picasso.Builder(mContext);
        builder.downloader(new OkHttp3Downloader(mContext));
        builder.build().load(MainActivity.SITE_NAME+cropList.get(position).getImage())
//                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.corn)
                .into(holder.mImage);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(mContext, CropDetailActivity.class);
                mIntent.putExtra("title", cropList.get(position).getName());
                mIntent.putExtra("slug", cropList.get(position).getSlug());
                mContext.startActivity(mIntent);
                customType(mContext, "fadein-to-fadeout");
            }
        });

    }

    @Override
    public int getItemCount() {
        return cropList.size();
    }

    public void updateList(List<All> newCropList) {
        cropList = new ArrayList<>();
        cropList.addAll(newCropList);
        notifyDataSetChanged();
    }
}

class AllViewHolder extends RecyclerView.ViewHolder {

    ImageView mImage;
    TextView mTitle;
    CardView mCardView;

    AllViewHolder(View itemView) {
        super(itemView);

        mImage = itemView.findViewById(R.id.ivImage);
        mTitle = itemView.findViewById(R.id.tvTitle);

        mCardView = itemView.findViewById(R.id.crop_cardview);


    }
}