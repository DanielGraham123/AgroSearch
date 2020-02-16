package com.irad.cm.agri_tech.diseaseDetail;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.irad.cm.agri_tech.R;

public class SolutionFragment extends Fragment {

    RecyclerView mRecyclerView;
    SolutionAdapter solutionAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_solution, container, false);

        mRecyclerView = view.findViewById(R.id.solution_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        solutionAdapter = new SolutionAdapter(getContext());
        mRecyclerView.setAdapter(solutionAdapter);

        return view;
    }

}

class SolutionAdapter extends RecyclerView.Adapter<SolutionViewHolder> {

    Context mContext;

    SolutionAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SolutionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.disease_solution_cardview, parent, false);
        return new SolutionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SolutionViewHolder holder, int i) {

        holder.webView.setBackgroundColor(Color.parseColor("#F1F5F1"));
        holder.webView.loadData(DiseaseDetailActivity.solutionHTML, "text/html; charset=UTF-8", null);

    }

    @Override
    public int getItemCount() {
        return 1;
    }
}

class SolutionViewHolder extends RecyclerView.ViewHolder {

    WebView webView;

    SolutionViewHolder(@NonNull View itemView) {
        super(itemView);
        webView = itemView.findViewById(R.id.solution_web_view);
    }
}
