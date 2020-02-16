package com.irad.cm.agri_tech.cropDetail;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.irad.cm.agri_tech.MainActivity;
import com.irad.cm.agri_tech.R;
import com.irad.cm.agri_tech.diseaseDetail.DiseaseDetailActivity;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static maes.tech.intentanim.CustomIntent.customType;

public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseCardViewHolder> {

    public static List<Disease> diseaseList;
    public Context mContext;

    DiseaseAdapter(List<Disease> diseaseList, Context mContext) {
        this.diseaseList = diseaseList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public DiseaseCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.disease_card_layout, viewGroup, false);

        return new DiseaseCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DiseaseCardViewHolder holder, final int i) {
        holder.diseaseTitle.setText(diseaseList.get(i).getDisease().getName());
        Picasso.Builder builder = new Picasso.Builder(mContext);
        builder.downloader(new OkHttp3Downloader(mContext));
        builder.build().load(MainActivity.SITE_URL+diseaseList.get(i).getImage())
//                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.corn)
                .into(holder.diseaseImage);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewMoreActivity(holder, i);
            }
        });

        holder.viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewMoreActivity(holder, i);
            }
        });
    }

    public void viewMoreActivity(final DiseaseCardViewHolder holder, int position) {
        Intent mIntent = new Intent(mContext, DiseaseDetailActivity.class);
        mIntent.putExtra("title", StringUtils.capitalize(diseaseList.get(holder.getAdapterPosition()).getDisease().getName()));
        mIntent.putExtra("position", position);
        mContext.startActivity(mIntent);
        customType(mContext, "fadein-to-fadeout");
    }

    @Override
    public int getItemCount() {
        return diseaseList.size();
    }


}

class DiseaseCardViewHolder extends RecyclerView.ViewHolder {

    TextView diseaseTitle;
    CardView mCardView;
    ImageView diseaseImage;
    Button viewMore;

    DiseaseCardViewHolder(View itemView) {
        super(itemView);

        diseaseImage = itemView.findViewById(R.id.disease_img);
        diseaseTitle = itemView.findViewById(R.id.disease_title);
        viewMore = itemView.findViewById(R.id.view_more_btn);

        mCardView = itemView.findViewById(R.id.disease_cardview);


    }
}
