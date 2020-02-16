package com.irad.cm.agri_tech.marketPrice;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.github.abdularis.civ.CircleImageView;
import com.github.chrisbanes.photoview.PhotoView;
import com.irad.cm.agri_tech.MainActivity;
import com.irad.cm.agri_tech.R;
import com.irad.cm.agri_tech.marketPrice.seedPriceDetail.SeedPriceDetialActivity;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<SeedPrice> seedPriceList;
    private int holderPosition;

    private boolean liked = false;
    private boolean cardClicked = false;

    public ItemRecyclerViewAdapter(Context mContext, int holderPosition, ArrayList<SeedPrice> seedPriceList) {
        this.mContext = mContext;
        this.seedPriceList = seedPriceList;
        this.holderPosition = holderPosition;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.seed_price_row_layout, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        holder.seedNameText.setText(StringUtils.capitalize(seedPriceList.get(position).getName()));
        if (seedPriceList.get(position).getOtherName() == null) {
            holder.seedOtherText.setVisibility(View.GONE);
        } else {
            holder.seedOtherText.setText(seedPriceList.get(position).getOtherName());
        }

        final Picasso.Builder picassoBuilder = new Picasso.Builder(mContext);
        picassoBuilder.downloader(new OkHttp3Downloader(mContext));
        picassoBuilder.build().load(MainActivity.SITE_URL + seedPriceList.get(position).getImage())
//                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.carrot)
                .into(holder.imageView);

        holder.starButton.setOnClickListener(new View.OnClickListener() {
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

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cardClicked) {
                    holder.showText.setText("SHOW LESS");
                    holder.showIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.show_less));
                    holder.hiddenlayout.setVisibility(View.VISIBLE);
                    cardClicked = true;
                } else {
                    holder.showText.setText("SHOW MORE");
                    holder.showIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.show_more));
                    holder.hiddenlayout.setVisibility(View.GONE);
                    cardClicked = false;
                }
            }
        });

        holder.hiddenlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SeedPriceDetialActivity.class);
                SeedPrice seedPrice = seedPriceList.get(position);
                seedPrice.setName(seedPriceList.get(position).getName());
                seedPrice.setOtherName(seedPriceList.get(position).getOtherName());
                seedPrice.setDistribution(seedPriceList.get(position).getDistribution());
                seedPrice.setImage(seedPriceList.get(position).getImage());
                seedPrice.setSeedPrice(seedPriceList.get(position).getSeedPrice());
                seedPrice.setSlug(seedPriceList.get(position).getSlug());


                intent.putExtra("seedPrice", seedPrice);
                mContext.startActivity(intent);
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the transition name from the string
//                String transitionName = mContext.getResources().getString(R.string.transition_string);

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                View mView = LayoutInflater.from(mContext).inflate(R.layout.dialog_custom_layout, null);
                PhotoView photoView = mView.findViewById(R.id.expandedImageView);
//                photoView.setImageResource();
                picassoBuilder.build().load(MainActivity.SITE_URL + seedPriceList.get(position).getImage())
                        .error(R.drawable.carrot)
                        .into(photoView);
                builder.setView(mView);

                TextView seedNameDialog = mView.findViewById(R.id.seed_name_dialog);
                seedNameDialog.setText(seedPriceList.get(position).getOtherName());

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });

    }

    @Override
    public int getItemCount() {
        return seedPriceList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        TextView seedNameText, seedOtherText, showText;
        LinearLayout hiddenlayout;
        CardView mCardView;
        ImageButton starButton;
        ImageView showIcon;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.seed_image);
            seedNameText = itemView.findViewById(R.id.seed_name_text);
            seedOtherText = itemView.findViewById(R.id.seed_other_text);
            hiddenlayout = itemView.findViewById(R.id.details_view);
            mCardView = itemView.findViewById(R.id.seed_price_cardview);
            starButton = itemView.findViewById(R.id.star_btn);
            showIcon = itemView.findViewById(R.id.show_icon);
            showText = itemView.findViewById(R.id.show_text);

        }
    }

}
