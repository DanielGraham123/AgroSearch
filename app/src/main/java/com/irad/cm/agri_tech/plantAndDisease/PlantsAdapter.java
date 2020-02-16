package com.irad.cm.agri_tech.plantAndDisease;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.github.abdularis.civ.AvatarImageView;
import com.irad.cm.agri_tech.R;

import org.apache.commons.lang3.StringUtils;

import java.util.List;


public class PlantsAdapter extends RecyclerView.Adapter<PlantsViewHolder>{
    public Context mContext;
    public static List<Disease> diseasesList;

    public PlantsAdapter(Context mContext, List<Disease> diseasesList) {
        this.mContext = mContext;
        this.diseasesList = diseasesList;
    }

    @NonNull
    @Override
    public PlantsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plant_row_layout, viewGroup, false);
        return new PlantsViewHolder(mView);
    }

    @Override
    public int getItemCount() {
        return diseasesList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull PlantsViewHolder holder, int i) {
        holder.imageView.setText(diseasesList.get(i).getCulture().substring(0, 1).toUpperCase());
        holder.cropNameText.setText(StringUtils.capitalize(diseasesList.get(i).getCulture()));

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent diseaseFragment = new Intent(mContext, PltDiseasesFragment.class);
//                mContext.startActivity(diseaseFragment);
                PlantAndDiseaseActivity.viewPager.setCurrentItem(2);
                Toast.makeText(mContext, "You clicked me", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

class PlantsViewHolder extends RecyclerView.ViewHolder {

    TextView cropNameText, otherNameText;
    AvatarImageView imageView;
    CardView mCardView;

    PlantsViewHolder(View view) {
        super(view);

        cropNameText = view.findViewById(R.id.seed_name_text);
//        otherNameText = view.findViewById(R.id.seed_other_text);
        imageView = view.findViewById(R.id.seed_avatar);
        mCardView = view.findViewById(R.id.plant_cardview);
    }

}

