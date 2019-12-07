package com.irad.cm.agri_tech.climate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.irad.cm.agri_tech.R;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<WeatherForeCast.ForeCastList> list;
    private Context context;

    String desc, wind, pressure, humidity, temp, dateStr, cloudIcon;
    Date date;
    SimpleDateFormat sdf;

    public RecyclerAdapter(List<WeatherForeCast.ForeCastList> list, Context context) {
        this.list = list;
        this.context = context;

        sdf = new SimpleDateFormat("E dd.MM.yy - HH:mm");
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_view_layout, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {

        WeatherForeCast.ForeCastList itemList = list.get(i);

        date = new Date((long) itemList.getDt() * 1000);

        dateStr = StringUtils.capitalize(sdf.format(date));
        desc = StringUtils.capitalize(itemList.getWeather().get(0).getDescription());
        wind = context.getResources().getString(R.string.wind) + " " + itemList.getWind().getSpeed() + " m/s";
        pressure = context.getResources().getString(R.string.pressure) + " " + itemList.getMain().getPressure() + " hPa";
        humidity = context.getResources().getString(R.string.humidity) + " " + itemList.getMain().getHumidity() + " %";
        temp = itemList.getMain().getTemp() + " °C";
        cloudIcon = itemList.getWeather().get(0).getIcon();

        viewHolder.dateTextView.setText(dateStr);
//        viewHolder.cloudImageView.setImageResource(R.drawable.cloud_test);
        viewHolder.temperatureView.setText(temp);
        viewHolder.humidityTextView.setText(humidity);
        viewHolder.pressureTextView.setText(pressure);
        viewHolder.windTextView.setText(wind);
        viewHolder.descTextView.setText(desc);

        String iconUrl = "http://openweathermap.org/img/w/" +cloudIcon + ".png";
        Picasso.get().load(iconUrl)
                .resize(200, 250)
                .centerCrop()
                .into(viewHolder.cloudImageView);
    }

    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView descTextView;
        TextView windTextView;
        TextView pressureTextView;
        TextView humidityTextView;
        TextView temperatureView;
        ImageView cloudImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.date_text);
            descTextView = itemView.findViewById(R.id.desc_text);
            windTextView = itemView.findViewById(R.id.wind_text);
            pressureTextView = itemView.findViewById(R.id.pressure_text);
            humidityTextView = itemView.findViewById(R.id.humidity_text);
            temperatureView = itemView.findViewById(R.id.temp_text);
            cloudImageView = itemView.findViewById(R.id.cloud_image_view);
        }
    }

    public void updateList(List<WeatherForeCast.ForeCastList> newList) {
        list = new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
    }
}
