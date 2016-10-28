package com.aldoapps.yetanothereventapp;

import com.facebook.drawee.view.SimpleDraweeView;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by aldo on 10/29/16.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private List<RestoMenu> restoMenuList;

    public MenuAdapter(List<RestoMenu> restoMenuList) {
        this.restoMenuList = restoMenuList;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.menu_item, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        RestoMenu restoMenu = restoMenuList.get(position);
        holder.bind(restoMenu);
    }

    @Override
    public int getItemCount() {
        return restoMenuList.size();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;

        @BindView(R.id.tv_description)
        TextView tvDescription;

        @BindView(R.id.rating)
        RatingBar ratingBar;

        @BindView(R.id.iv_menu)
        SimpleDraweeView ivMenu;

        public MenuViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void bind(RestoMenu restoMenu) {
            ivMenu.setImageURI(Uri.parse(restoMenu.getImageUrl()));

            tvName.setText(restoMenu.getMenu());
            tvDescription.setText(restoMenu.getDescription());
            ratingBar.setRating(restoMenu.getRating());
        }
    }
}
