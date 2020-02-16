package com.irad.cm.agri_tech.diseaseDetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.irad.cm.agri_tech.R;

public class SymptomsListAdapter extends RecyclerView.Adapter<SymptomsListViewHolder> {

    Context mContext;
    String[] symptoms;

    public SymptomsListAdapter(Context mContext, String[] symptoms) {
        this.mContext = mContext;
        this.symptoms = symptoms;
    }

    @NonNull
    @Override
    public SymptomsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.disease_listview, parent, false);
        return new SymptomsListViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull SymptomsListViewHolder holder, int i) {

        holder.symptomsText.setText(symptoms[i]);

    }

    @Override
    public int getItemCount() {
        return symptoms.length;
    }
}

class SymptomsListViewHolder extends RecyclerView.ViewHolder {

    TextView symptomsText;

    public SymptomsListViewHolder(@NonNull View itemView) {
        super(itemView);
        symptomsText = itemView.findViewById(R.id.disease_text);
    }
}