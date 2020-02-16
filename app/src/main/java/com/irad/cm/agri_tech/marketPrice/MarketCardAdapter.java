package com.irad.cm.agri_tech.marketPrice;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.github.abdularis.civ.AvatarImageView;
import com.irad.cm.agri_tech.R;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MarketCardAdapter extends RecyclerView.Adapter<MarketCardViewHolder> {
    public Context mContext;
    public static List<Price> priceList;

    public static boolean liked = false;
    private boolean cardClicked = false;
    private boolean regionAvailable = false;

    private AlertDialog.Builder dialogBuilder;

    private String region;
    String nopriceMeasure;

    public MarketCardAdapter(List<Price> priceList, Context mContext) {
        this.mContext = mContext;
        this.priceList = priceList;

    }

    @NonNull
    @Override
    public MarketCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.market_price_layout, parent, false);
        return new MarketCardViewHolder(mView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final MarketCardViewHolder holder, final int position) {
//       try {
           holder.cropNameText.setText(StringUtils.capitalize(priceList.get(position).getCulture()));
           holder.avatar.setText(priceList.get(position).getCulture().substring(0, 1).toUpperCase());

           String priceMeasure = "Vendue a ";

//           if (MainActivity.locationInfo.split(", ").length == 2) {
//               region = MainActivity.locationInfo.split(", ")[0];
//               nopriceMeasure = "Pas vendue a ";
//           } else if (MainActivity.locationInfo.split(", ").length == 3){
//               region = MainActivity.locationInfo.split(", ")[2];
//               if (region.contains(" ")) {
//                   region = region.split(" ")[region.split(" ").length-1];
//                   if (region.contains("\'")) {
//                       region = region.replace("\'", " ");
//                       region = region.split(" ")[1];
//                   }
//               }
//               nopriceMeasure = "Pas vendue au ";
//           }

//        Toast.makeText(mContext, "Votre localite est "+region, Toast.LENGTH_LONG).show();

           if (priceList.get(position).getMarketPrice().size() == 0) {

           } else {
               List<MarketPrice> mp = priceList.get(position).getMarketPrice();
               for (int i = 0; i < mp.size(); i++) {
                   if (mp.get(i).getRegion().contains(region)) {
                       regionAvailable = true;
//                       priceMeasure += mp.get(i).getPrice() + "frs/" + mp.get(i).getMeasure();
//                       priceMeasure += " au " + region;
//                       holder.mesureText.setText(priceMeasure);
                       break;
                   } else {
                       regionAvailable =false;
                   }
               }

           }

           TextView[] marketRegionsArray = new TextView[priceList.get(position).getMarketPrice().size()];
           TextView[] marketPricesArray = new TextView[priceList.get(position).getMarketPrice().size()];

           TableRow[] tableRow = new TableRow[priceList.get(position).getMarketPrice().size()];

           for (int i = 0; i < priceList.get(position).getMarketPrice().size(); i++) {
               tableRow[i] = new TableRow(mContext);
               tableRow[i].setId(i + 10);
               TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
               tableRow[i].setLayoutParams(params);

               marketRegionsArray[i] = new TextView(mContext);
               marketRegionsArray[i].setId(i + 20);

               List<String> marketRegions = priceList.get(position).getMarketPrice().get(i).getRegion();
               String regions = "";
               for (int j = 0; j < marketRegions.size(); j++) {
                   regions += (j == (marketRegions.size() - 1) ? marketRegions.get(j) : marketRegions.get(j) + ", ");
               }
               marketRegionsArray[i].setText(regions);

               marketRegionsArray[i].setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
               marketRegionsArray[i].setPadding(10, 10, 10, 10);
               marketRegionsArray[i].setGravity(Gravity.CENTER_HORIZONTAL);
               marketRegionsArray[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f);
               marketRegionsArray[i].setWidth(250);
               TableRow.LayoutParams textParams = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
               marketRegionsArray[i].setLayoutParams(textParams);

               tableRow[i].addView(marketRegionsArray[i]);

               marketPricesArray[i] = new TextView(mContext);
               marketPricesArray[i].setId(i + 30);
               marketPricesArray[i].setText(priceList.get(position).getMarketPrice().get(i).getPrice()+"/"+priceList.get(position).getMarketPrice().get(i).getMeasure());
               marketPricesArray[i].setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
               marketPricesArray[i].setPadding(10, 10, 10, 10);
               marketPricesArray[i].setGravity(Gravity.CENTER_HORIZONTAL);
               marketPricesArray[i].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f);
               marketPricesArray[i].setLayoutParams(textParams);
               marketPricesArray[i].setWidth(80);
               tableRow[i].addView(marketPricesArray[i]);

               holder.marketTable.addView(tableRow[i]);
           }

           holder.mCardView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if (!cardClicked) {
                       holder.showText.setText("SHOW LESS");
                       holder.showIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.show_less));
                       holder.marketTable.setVisibility(View.VISIBLE);
                       cardClicked = true;
                   } else {
                       holder.showText.setText("SHOW MORE");
                       holder.showIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.show_more));
                       holder.marketTable.setVisibility(View.GONE);
                       cardClicked = false;
                   }
               }
           });
//
//        holder.viewMoreBtn.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public void onClick(View view) {
//                viewMoreActivity(holder, position);
//            }
//        });

           holder.startBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if (!liked) {
                       view.setBackgroundResource(R.drawable.star_clicked);
                       liked = true;
                   } else {
                       view.setBackgroundResource(R.drawable.star_border);
                       liked = false;
                   }
               }
           });
//       }

    }

//    public void viewMoreActivity(final MarketCardViewHolder holder, int position) {
//        Intent mIntent = new Intent(mContext, MarketCardDetailActivity.class);
//        mIntent.putExtra("title", StringUtils.capitalize(seedList.get(holder.getAdapterPosition()).getName()));
//        mIntent.putExtra("position", holder.getAdapterPosition());
//        mContext.startActivity(mIntent);
//        customType(mContext, "fadein-to-fadeout");
//    }

    @Override
    public int getItemCount() {
        return priceList.size();
    }

    public void updateList(List<Price> newPriceList) {
        priceList = new ArrayList<>();
        priceList.addAll(newPriceList);
        notifyDataSetChanged();
    }
}

class MarketCardViewHolder extends RecyclerView.ViewHolder {

    AvatarImageView avatar;
    TextView cropNameText, mesureText, showText;
    ImageButton startBtn;
    CardView mCardView;
    ImageView showIcon;
    TableLayout marketTable;

    MarketCardViewHolder(View itemView) {
        super(itemView);
        showIcon = itemView.findViewById(R.id.show_icon);

        avatar = itemView.findViewById(R.id.crop_image);
        cropNameText = itemView.findViewById(R.id.crop_text);
//        mesureText = itemView.findViewById(R.id.price_mesure_text);
        showText = itemView.findViewById(R.id.show_text);
        startBtn = itemView.findViewById(R.id.star_btn);
        marketTable = itemView.findViewById(R.id.market_table);
        showIcon = itemView.findViewById(R.id.show_icon);

        mCardView = itemView.findViewById(R.id.market_price_cardview);


    }
}

