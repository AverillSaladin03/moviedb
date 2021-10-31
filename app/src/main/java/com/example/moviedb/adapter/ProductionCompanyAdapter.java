package com.example.moviedb.adapter;

import android.content.Context;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Movies;

import java.util.List;

public class ProductionCompanyAdapter extends RecyclerView.Adapter<ProductionCompanyAdapter.CardViewViewHolder> {

    private Context context;
    private List<Movies.ProductionCompanies> productionCompaniesList;

    public List<Movies.ProductionCompanies> getProductionCompaniesList() {
        return productionCompaniesList;
    }

    public void setProductionCompaniesList(List<Movies.ProductionCompanies> productionCompaniesList) {
        this.productionCompaniesList = productionCompaniesList;
    }

    public ProductionCompanyAdapter (Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ProductionCompanyAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_production_company, parent,
                false);
        return new ProductionCompanyAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductionCompanyAdapter.CardViewViewHolder holder, int position) {
        Movies.ProductionCompanies productionCompaniesResult = getProductionCompaniesList().get(position);
        String logoPath = productionCompaniesResult.getLogo_path();
        String name = productionCompaniesResult.getName();
        String originCountry = productionCompaniesResult.getOrigin_country();

        if (logoPath != null){
            Glide.with(context.getApplicationContext()).load(Const.IMG_PATH+logoPath).into(holder.logoProductionComp);
        }else{
            Glide.with(context.getApplicationContext()).load(R.drawable.ic_action_noimage).into(holder.logoProductionComp);
        }

    }

    @Override
    public int getItemCount() {
        return productionCompaniesList.size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        private ImageView logoProductionComp;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            logoProductionComp = itemView.findViewById(R.id.card_productionCompany_logo);
        }
    }
}
