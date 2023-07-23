package com.lethsmarketenterprise.lethsmarket.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lethsmarketenterprise.lethsmarket.R;
import com.lethsmarketenterprise.lethsmarket.models.Product;

import java.util.List;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }
    public void setData(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_product, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.textViewName.setText(product.getName());
        holder.textViewBrand.setText(product.getBrand());
        holder.textViewPriceSell.setText(product.getPriceSell());
    }

    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewName;
        TextView textViewBrand;
        TextView textViewPriceSell;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewBrand = itemView.findViewById(R.id.textViewBrand);
            textViewPriceSell = itemView.findViewById(R.id.textViewPriceSell);
        }
    }


}
