package com.irad.cm.agri_tech;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.irad.cm.agri_tech.climate.WeatherActivity;

public abstract class OptionsMenuActivity extends AppCompatActivity {

    MainActivity main;
    Utilities utilities;
    EditText searchEditText;
    WeatherActivity weatherActivity;

    public OptionsMenuActivity() {}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.weather_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        main = new MainActivity();
        weatherActivity = new WeatherActivity();
        utilities = new Utilities(this, this);

        switch (item.getItemId()) {
            case R.id.action_translate:
                displayMessage("Translate option selected");
                utilities.showChangeLanguageDialog();
                return true;
            case R.id.action_search:
                displayMessage("Search option selected");
                showSearchDialog();
                return true;
            case R.id.action_about:
                displayMessage("About option selected");
                return true;
            case R.id.action_refresh:
                displayMessage("Refresh option selected");
                refreshWeather();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void refreshWeather() {
    }

    public void displayMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showSearchDialog() {

        View searchDialogView = LayoutInflater.from(this).inflate(R.layout.weather_search_dialog, null);
        searchEditText = searchDialogView.findViewById(R.id.search_city);

        AlertDialog.Builder builder = new AlertDialog.Builder((this));
        builder.setTitle(R.string.city_search);

        builder.setView(searchDialogView);
        builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String city = searchEditText.getText().toString();

                Toast.makeText(getApplicationContext(), city, Toast.LENGTH_LONG).show();
                searchCityData(city);
            }
        })
                .setNegativeButton("Cancel", null)
                .setCancelable(false);

        AlertDialog mDialog = builder.create();
        mDialog.show();


    }

    public void searchCityData(String city) {}

}
