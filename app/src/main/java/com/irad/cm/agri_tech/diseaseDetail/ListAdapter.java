package com.irad.cm.agri_tech.diseaseDetail;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.irad.cm.agri_tech.R;

public class ListAdapter extends ArrayAdapter<String> {

    String[] text;
    Activity context;

    public ListAdapter(Activity context, String[] text) {
        super(context, R.layout.disease_listview, text);

        this.context = context;
        this.text = text;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.disease_listview, null,true);

        TextView textView = rowView.findViewById(R.id.disease_text);

        textView.setText(text[position]);

        return rowView;
    }
}
