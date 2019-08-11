package com.irad.cm.agri_tech;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

@SuppressLint("ValidFragment")
public class Utilities extends DialogFragment {

    private Context context;
    private Activity activity;
    private AlertDialog mDialog;


    public Utilities(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }


    public void showChangeLanguageDialog() {
        final String[] listItems = {"Français", "English"};

        AlertDialog.Builder builder = new AlertDialog.Builder((context));
        builder.setTitle(R.string.choose_language);

        builder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    setLocale("fr");
                    activity.recreate();
                } else if (which == 1) {
                    setLocale("en");
                    activity.recreate();
                }
                dialog.dismiss();
            }
        });

        mDialog = builder.create();
        mDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;
        activity.getResources().updateConfiguration(config, activity.getResources().getDisplayMetrics());

        // save data to shared preferences
        SharedPreferences.Editor editor = activity.getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    // load language saved in shared preferences
    public void loadLocale() {
        SharedPreferences prefs = activity.getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);
    }



}
