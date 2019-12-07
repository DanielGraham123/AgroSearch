package com.irad.cm.agri_tech.marketPrice;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.irad.cm.agri_tech.R;
import com.irad.cm.agri_tech.cropDetail.Seed;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SectionRecyclerViewAdapter extends RecyclerView.Adapter<SectionRecyclerViewAdapter.SectionViewHolder> {

    private Context mContext;
    private ArrayList<SeedSectionModel> seedSectionModelList;

    public SectionRecyclerViewAdapter(Context mContext, ArrayList<SeedSectionModel> seedSectionModelList) {
        this.mContext = mContext;
        this.seedSectionModelList = seedSectionModelList;
    }

    @NonNull
    @Override
    public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.seed_single_row_layout, viewGroup, false);
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionViewHolder holder, int position) {
        final SeedSectionModel seedSectionModel = seedSectionModelList.get(position);

        holder.headerText.setText(StringUtils.capitalize(seedSectionModel.getHeader()));

        holder.itemRecyclerView.setHasFixedSize(true);
        holder.itemRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        holder.itemRecyclerView.setLayoutManager(layoutManager);

        ItemRecyclerViewAdapter adapter = new ItemRecyclerViewAdapter(mContext, position, seedSectionModel.getSeedPrice());
        holder.itemRecyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return seedSectionModelList.size();
    }

    class SectionViewHolder extends RecyclerView.ViewHolder {
        TextView headerText;
        RecyclerView itemRecyclerView;

        public SectionViewHolder(@NonNull View itemView) {
            super(itemView);
            headerText = itemView.findViewById(R.id.seed_header);
            itemRecyclerView = itemView.findViewById(R.id.item_recyclerview);
        }
    }

    public void updateList(ArrayList<SeedSectionModel> newSeedSectionModel) {
        seedSectionModelList = new ArrayList<>();
        seedSectionModelList.addAll(newSeedSectionModel);
        notifyDataSetChanged();
    }

}
