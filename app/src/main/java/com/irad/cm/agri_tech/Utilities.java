package com.irad.cm.agri_tech;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.net.Uri;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.io.File;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

@SuppressLint("ValidFragment")
public class Utilities extends DialogFragment {

    private Context context;
    private Activity activity;
    private AlertDialog mDialog;
    private long downloadID;

    public static String LANGUAGE;

    public Utilities(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public Utilities(Context context) {
        this.context = context;
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context.getApplicationContext());
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        mDialog = builder.create();
        mDialog.show();
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
        LANGUAGE = lang;
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
        String language = prefs.getString("My_Lang", "fr");
        setLocale(language);
    }

    public void downloadFile(String url) {
//        activity.getString(R.string.technical_sheet)
//        DownloadManager downloadmanager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
//        Uri uri = Uri.parse(url);

        String strs[] = url.split("/");
        String fileName  = strs[strs.length-1];

        File file=new File(activity.getExternalFilesDir(null), fileName);

//        "http://speedtest.ftp.otenet.gr/files/test10Mb.db"
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url))
                .setTitle(fileName)// Title of the Download Notification
                .setDescription("Downloading")// Description of the Download Notification
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
                .setDestinationUri(Uri.fromFile(file))// Uri of the destination file
                .setAllowedOverRoaming(true);// Set if download is allowed on roaming network

//        DownloadManager.Request request = new DownloadManager.Request(uri);
//        request.setTitle("My File");
//        request.setDescription("Downloading");
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        request.setVisibleInDownloadsUi(false);
//        request.setDestinationUri(Uri.parse("file://" + strs[strs.length-1]));

//        downloadmanager.enqueue(request);
        DownloadManager downloadManager= (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadID = downloadManager.enqueue(request);// enqueue puts the download request in the queue.

    }

    public BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadID == id) {
                Toast.makeText(context, "Download Completed", Toast.LENGTH_SHORT).show();
            }
        }
    };

}
